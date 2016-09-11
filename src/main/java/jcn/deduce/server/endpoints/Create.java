package jcn.deduce.server.endpoints;

import jcn.deduce.server.model.DeduceMatch;
import jcn.deduce.server.model.DeduceResponseEntity;
import jcn.deduce.server.mongo.DeduceMatchRepository;
import jcn.deduce.server.util.DeduceWords;
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
@Path("/createMatch")
public class Create
{
    @Context
    UriInfo uriInfo;

    private DeduceMatchRepository repository;

    public Create(DeduceMatchRepository repo)
    {
        this.repository = repo;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDeduceMatch(@Context HttpServletResponse response)
    {
        DeduceMatch match = new DeduceMatch(DeduceWords.getRandomWord());
        match = repository.save(match);
        URI matchUri = uriInfo.getBaseUriBuilder().path(String.format("/%s/details", match.getId())).build();

        return Response.created(matchUri)
                .entity(new DeduceResponseEntity(Response.Status.CREATED, match, "Match created: " + matchUri))
                .build();
    }
}