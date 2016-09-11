package jcn.deduce.server.endpoints;

import jcn.deduce.server.model.DeduceMatch;
import jcn.deduce.server.model.DeduceResponseEntity;
import jcn.deduce.server.mongo.DeduceMatchRepository;
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
    private DeduceMatchRepository repository;

    public Solve(DeduceMatchRepository repo)
    {
        this.repository = repo;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLetterAtIndex(@PathParam("match_id") String id, @PathParam("solution") String solution)
    {
        DeduceResponseEntity de =
                new DeduceResponseEntity(Response.Status.NOT_FOUND, null, String.format("Match id %s not found.", id));

        Optional<DeduceMatch> match = Optional.ofNullable(repository.findById(id));

        if(match.isPresent())
        {
            DeduceMatch entity = match.get();

            if(!entity.getIsSolved())
            {
                if(entity.isReadyForSolving())
                {
                    entity.addSolutionAttemptEvent(solution);

                    if (entity.getWord().equalsIgnoreCase(solution))
                    {
                        entity.setIsSolved(true);
                        entity.setSolution(solution);
                    }

                    entity = repository.save(entity);
                    de = new DeduceResponseEntity(Response.Status.OK, entity, "Success");
                }
                else
                {
                    de = new DeduceResponseEntity(Response.Status.UNAUTHORIZED,
                            entity,
                            "Incorrect solution attempt must be followed by index query.");
                }
            }
            else
            {
                de = new DeduceResponseEntity(Response.Status.UNAUTHORIZED,
                        entity,
                        String.format("Match id %s already solved.", id));
            }
        }

        return Response.status(de.getStatus())
                .entity(de)
                .build();
    }
}