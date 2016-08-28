package jcn.deduce.server.endpoints;

import jcn.deduce.server.util.DeduceWords;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@Path("/listWords")
public class ListWords
{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> createDeduceMatch()
    {
        return DeduceWords.getAllWords();
    }
}