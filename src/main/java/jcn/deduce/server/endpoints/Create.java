package jcn.deduce.server.endpoints;

import com.google.gson.Gson;
import jcn.deduce.server.model.DeduceMatch;
import jcn.deduce.server.mongo.DeduceMatchRepository;
import jcn.deduce.server.util.RandomWord;
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
    public Response consumeSubmission()
    {
        DeduceMatch match = new DeduceMatch(RandomWord.getRandomWord());
        match = repository.save(match);

        return Response.status(200).entity(new Gson().toJson(match)).build();
    }
}