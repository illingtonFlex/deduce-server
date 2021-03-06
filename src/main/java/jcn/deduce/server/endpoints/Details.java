package jcn.deduce.server.endpoints;

import jcn.deduce.server.model.DeduceMatch;
import jcn.deduce.server.model.DeduceResponseEntity;
import jcn.deduce.server.db.DeduceMatchRepository;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Component
public class Details extends DeduceMatchResource
{
    public Details(DeduceMatchRepository repo)
    {
        super(repo);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{match_id}/details")
    public Response getMatchDetails(@PathParam("match_id") String id)
    {
        DeduceResponseEntity de =
                new DeduceResponseEntity(Response.Status.NOT_FOUND, null, String.format("Match id %s not found", id));

        Optional<DeduceMatch> match = Optional.ofNullable(repository.findById(id));

        if(match.isPresent())
        {
            de = new DeduceResponseEntity(Response.Status.OK, match.get(), "Success");
        }

        return Response.status(de.getStatus())
                .entity(de)
                .build();
    }
}