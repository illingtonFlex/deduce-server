package jcn.deduce.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import java.util.Random;

public class DeduceWordsList
{
    @Id
    private String id;
    private String[] words;

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRandomWord()
    {
        int rnd = new Random().nextInt(words.length);
        return words[rnd];
    }
}
