package jcn.deduce.server.endpoints;

import jcn.deduce.server.RandomWordNotFoundException;
import jcn.deduce.server.db.DeduceWordsRepository;
import jcn.deduce.server.model.DeduceMatch;
import jcn.deduce.server.model.DeduceResponseEntity;
import jcn.deduce.server.db.DeduceMatchRepository;
import jcn.deduce.server.model.DeduceWordsList;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
public class Create extends DeduceMatchResource
{
    @Context
    UriInfo uriInfo;

    DeduceWordsRepository wordsRepo;

    public Create(DeduceMatchRepository repo, DeduceWordsRepository wordsRepo)
    {
        super(repo);
        this.wordsRepo = wordsRepo;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createMatch")
    public Response createDeduceMatch(@Context HttpServletResponse response)
    {
        Response retVal;

        try
        {
            DeduceMatch match = repository.save(new DeduceMatch(getRandomWord()));
            URI matchUri = uriInfo.getBaseUriBuilder().path(String.format("deduceMatch/%s/details", match.getId())).build();

            retVal = Response.created(matchUri)
                    .entity(new DeduceResponseEntity(Response.Status.CREATED, match, "Match created: " + matchUri))
                    .build();

        }
        catch(RandomWordNotFoundException de)
        {
            retVal = Response.serverError()
                    .entity(new DeduceResponseEntity(Response.Status.NOT_FOUND, null,
                            String.format("Error while creating new match: %s", de.toString())))
                    .build();
        }

        return retVal;
    }

    private String getRandomWord() throws RandomWordNotFoundException
    {
        String randomWord;

        Optional<List<DeduceWordsList>> optResList = Optional.of(wordsRepo.findAll());

        if(optResList.isPresent())
        {
            List<DeduceWordsList> list = optResList.get();

            if(list.size() > 0)
            {
                DeduceWordsList dwl = list.get(0);

                if(dwl != null)
                {
                    randomWord = dwl.getRandomWord();
                }
                else
                {
                    throw new RandomWordNotFoundException("Null words list in db.");
                }
            }
            else
            {
                throw new RandomWordNotFoundException("Empty words list in db.");
            }
        }
        else
        {
            throw new RandomWordNotFoundException("No words list found in db.");
        }

        return randomWord;
    }
}