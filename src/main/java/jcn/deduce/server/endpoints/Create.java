package jcn.deduce.server.endpoints;

import com.google.gson.Gson;
import jcn.deduce.server.model.DeduceMatch;
import jcn.deduce.server.mongo.DeduceMatchRepository;
import jcn.deduce.server.util.DeduceWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Component
@Path("/createMatch")
public class Create
{
    @Autowired
    private DeduceMatchRepository repository;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public DeduceMatch createDeduceMatch()
    {
        DeduceMatch match = new DeduceMatch(DeduceWords.getRandomWord());
        match = repository.save(match);
        return match;
    }
}