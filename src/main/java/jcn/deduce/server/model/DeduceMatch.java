package jcn.deduce.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DeduceMatches")
public class DeduceMatch
{
    @Id
    private String id;
    private String word;
    private Boolean isSolved;

    public DeduceMatch() {}

    public DeduceMatch(String word)
    {
        this.word = word;
        this.isSolved = Boolean.FALSE;
    }

    public boolean getIsSolved()
    {
        return this.isSolved;
    }

    public void setIsSolved(boolean isSolved)
    {
        this.isSolved = isSolved;
    }

    public String getWord()
    {
        return this.word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

}