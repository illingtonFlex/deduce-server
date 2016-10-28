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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LetterAtIndex extends DeduceMatchResource
{
    private static final List<Character> ALPHABET =
            Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                    'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

    public LetterAtIndex(DeduceMatchRepository repo)
    {
        super(repo);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{match_id}/letterAtIndex/{index}")
    public Response getLetterAtIndex(@PathParam("match_id") String id, @PathParam("index") Integer index)
    {
        DeduceResponseEntity de =
                new DeduceResponseEntity(Response.Status.NOT_FOUND,
                        null,
                        String.format("Match id %s not found.", id));

        Optional<DeduceMatch>  match = Optional.ofNullable(repository.findById(id));

        if(index != null && index >= 0 && index < 20)
        {
            if (match.isPresent())
            {
                if(!match.get().getIsSolved())
                {
                    List<Character> filteredAlphabet = filterAlphabet(match.get().getWord());

                    match.get().addIndexInquiryEvent(
                            String.format("index: %s - letter: %s", index, filteredAlphabet.get(index)));

                    repository.save(match.get());

                    de = new DeduceResponseEntity(Response.Status.OK,
                            filteredAlphabet.get(index),
                            "Success");
                }
                else
                {
                    de = new DeduceResponseEntity(Response.Status.UNAUTHORIZED,
                            match.get(),
                            String.format("Match id %s already solved.", id));
                }
            }
        }
        else
        {
            de = new DeduceResponseEntity(Response.Status.BAD_REQUEST,
                    null,
                    "Index out of bounds");
        }

        return Response.status(de.getStatus())
                .entity(de)
                .build();
    }

    private List<Character> filterAlphabet(String word)
    {
        return ALPHABET.stream()
                .filter(c -> !c.equals(word.charAt(0)))
                .filter(c -> !c.equals(word.charAt(1)))
                .filter(c -> !c.equals(word.charAt(2)))
                .filter(c -> !c.equals(word.charAt(3)))
                .filter(c -> !c.equals(word.charAt(4)))
                .collect(Collectors.toList());
    }
}
