package jcn.deduce.server.endpoints;

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
        DeduceMatch match = new DeduceMatch();

        DeduceResponseEntity de =
                new DeduceResponseEntity(Response.Status.NOT_FOUND, match, "ERROR");

        Optional<List<DeduceWordsList>> optResList = Optional.of(wordsRepo.findAll());

        if(optResList.isPresent())
        {
            List<DeduceWordsList> list = optResList.get();

            if(list.size() > 0)
            {
                DeduceWordsList dwl = list.get(0);

                match = repository.save(new DeduceMatch(dwl.getRandomWord()));
            }
        }

        URI matchUri = uriInfo.getBaseUriBuilder().path(String.format("deduceMatch/%s/details", match.getId())).build();

        return Response.created(matchUri)
                .entity(new DeduceResponseEntity(Response.Status.CREATED, match, "Match created: " + matchUri))
                .build();
    }
}