package jcn.deduce.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Random;

@EqualsAndHashCode
@ToString
public class DeduceWordsList
{
    @Id
    @Getter(onMethod = @__( @JsonIgnore ))
    @Setter
    private String id;

    @Getter @Setter private String[] words;

    public String getRandomWord()
    {
        int rnd = new Random().nextInt(words.length);
        return words[rnd];
    }
}
