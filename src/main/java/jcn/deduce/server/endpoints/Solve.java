package jcn.deduce.server.endpoints;

import jcn.deduce.server.model.DeduceMatch;
import jcn.deduce.server.model.DeduceResponseEntity;
import jcn.deduce.server.mongo.DeduceMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Component
@Path("/{match_id}/solve/{solution}")
public class Solve
{
    @Autowired
    private DeduceMatchRepository repository;

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLetterAtIndex(@PathParam("match_id") String id, @PathParam("solution") String solution)
    {
        Response.Status status = Response.Status.NOT_FOUND;
        String msg = String.format("Match id %s not found.", id);
        DeduceMatch entity = null;

        Optional<DeduceMatch> match = Optional.ofNullable(repository.findById(id));

        if(match.isPresent())
        {
            if(!match.get().getIsSolved())
            {
                entity = match.get();
                status = Response.Status.OK;
                msg = "Success";

                entity.addEvent("SOLUTION_ATTEMPT", solution);

                if (entity.getWord().equalsIgnoreCase(solution))
                {
                    entity.setIsSolved(true);
                    entity.setSolution(solution);
                }

                repository.save(entity);
            }
            else
            {
                status = Response.Status.UNAUTHORIZED;
                msg = String.format("Match id %s already solved.", id);
            }
        }

        return Response.status(status)
                .entity(new DeduceResponseEntity(status, entity, msg))
                .build();
    }
}