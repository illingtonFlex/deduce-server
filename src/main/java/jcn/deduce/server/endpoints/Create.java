package jcn.deduce.server.endpoints;

import jcn.deduce.server.model.DeduceMatch;
import jcn.deduce.server.model.DeduceResponseEntity;
import jcn.deduce.server.db.DeduceMatchRepository;
import jcn.deduce.server.model.DeduceWords;
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

@Component
public class Create extends DeduceMatchResource
{
    @Context
    UriInfo uriInfo;

    public Create(DeduceMatchRepository repo)
    {
        super(repo);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createMatch")
    public Response createDeduceMatch(@Context HttpServletResponse response)
    {
        DeduceMatch match = new DeduceMatch(DeduceWords.getRandomWord());
        match = repository.save(match);
        URI matchUri = uriInfo.getBaseUriBuilder().path(String.format("deduceMatch/%s/details", match.getId())).build();

        return Response.created(matchUri)
                .entity(new DeduceResponseEntity(Response.Status.CREATED, match, "Match created: " + matchUri))
                .build();
    }
}