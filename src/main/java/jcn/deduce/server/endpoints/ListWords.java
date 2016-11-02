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
        DeduceResponseEntity de =
                new DeduceResponseEntity(Response.Status.NOT_FOUND, new DeduceWordsList(), "ERROR: Words list not found.");

        Optional<List<DeduceWordsList>> optResList = Optional.of(repository.findAll());

        if(optResList.isPresent())
        {
            List<DeduceWordsList> dwl = optResList.get();

            if(dwl.size() > 0)
            {
                de = new DeduceResponseEntity(Response.Status.OK, dwl.get(0), "SUCCESS: Words list found.");
            }
        }

        return Response.status(de.getStatus())
                .entity(de)
                .build();
    }
}
