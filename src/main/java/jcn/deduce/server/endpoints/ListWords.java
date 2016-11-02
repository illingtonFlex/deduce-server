package jcn.deduce.server.endpoints;

import jcn.deduce.server.db.DeduceWordsRepository;
import jcn.deduce.server.model.DeduceResponseEntity;
import jcn.deduce.server.model.DeduceWordsList;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Component
@Path("/listValidWords")
public class ListWords
{
    private DeduceWordsRepository repository;

    public ListWords(DeduceWordsRepository repo)
    {
        repository = repo;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeduceWordsList()
    {
        DeduceWordsList dwl = new DeduceWordsList();

        DeduceResponseEntity de =
                new DeduceResponseEntity(Response.Status.NOT_FOUND, dwl, "ERROR: Words list not found.");

        Optional<List<DeduceWordsList>> optResList = Optional.of(repository.findAll());

        if(optResList.isPresent())
        {
            List<DeduceWordsList> dwlList = optResList.get();

            if(dwlList.size() > 0)
            {
                dwl = dwlList.get(0);

                de = new DeduceResponseEntity(Response.Status.OK, dwl, "SUCCESS: Words list found.");
            }
        }

        return Response.status(de.getStatus())
                .entity(de)
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWordsList(String[] words)
    {
        DeduceWordsList dwl = new DeduceWordsList();
        dwl.setWords(words);

        repository.save(dwl);

        return Response.ok().build();
    }
}
