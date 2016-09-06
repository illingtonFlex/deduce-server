package jcn.deduce.server.endpoints;

import jcn.deduce.server.model.DeduceMatch;
import jcn.deduce.server.model.DeduceResponseEntity;
import jcn.deduce.server.mongo.DeduceMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Component
@Path("/{match_id}/details")
public class Details
{
    @Autowired
    private DeduceMatchRepository repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchDetails(@PathParam("match_id") String id)
    {
        Response.Status status = Response.Status.NOT_FOUND;
        DeduceMatch entity = null;
        String msg = "Match not found";

        Optional<DeduceMatch> match = Optional.ofNullable(repository.findById(id));

        if(match.isPresent())
        {
            status = Response.Status.OK;
            entity = match.get();
            msg = "Success";
        }

        return Response.status(status)
                .entity(new DeduceResponseEntity(status, entity, msg))
                .build();
    }
}