package jcn.deduce.server.endpoints;

import jcn.deduce.server.model.DeduceMatch;
import jcn.deduce.server.mongo.DeduceMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Path("/{match_id}/letterAtIndex/{index}")
public class LetterAtIndex
{
    private static final List<Character> ALPHABET =
            Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                    'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

    @Autowired
    private DeduceMatchRepository repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLetterAtIndex(@PathParam("match_id") String id, @PathParam("index") Integer index)
    {
        Response.Status status = Response.Status.NOT_FOUND;

        Character entity = null;
        Optional<DeduceMatch>  match = Optional.ofNullable(repository.findById(id));

        if(match.isPresent()) {

            status = Response.Status.OK;

            List<Character> filteredAlphabet = ALPHABET.stream()
                    .filter(c -> !c.equals(match.get().getWord().charAt(0)))
                    .filter(c -> !c.equals(match.get().getWord().charAt(1)))
                    .filter(c -> !c.equals(match.get().getWord().charAt(2)))
                    .filter(c -> !c.equals(match.get().getWord().charAt(3)))
                    .filter(c -> !c.equals(match.get().getWord().charAt(4)))
                    .collect(Collectors.toList());

            match.get().addEvent(
                    "LETTER_AT_INDEX",
                    String.format("index: %s - letter: %s", index, filteredAlphabet.get(index)));

            repository.save(match.get());
            entity = filteredAlphabet.get(index);
        }

        return Response.status(status).entity(entity).build();
    }
}
