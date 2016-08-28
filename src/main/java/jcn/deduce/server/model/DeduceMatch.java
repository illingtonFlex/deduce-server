package jcn.deduce.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "DeduceMatches")
public class DeduceMatch
{
    @Id
    private String id;
    private String word;
    private String solution;
    private Boolean isSolved;
    private List<Event> events;

    public DeduceMatch() {}

    public DeduceMatch(String word)
    {
        this.word = word;
        this.isSolved = Boolean.FALSE;
        this.solution = "[UNSOLVED]";
        this.events = new ArrayList<>();
    }

    public String getSolution()
    {
        return solution;
    }

    public void setSolution(String solution)
    {
        this.solution = solution;
    }

    public List<Event> getEvents()
    {
        return this.events;
    }

    public void setEvents(List<Event> events)
    {
        this.events = events;
    }

    public void addEvent(String name, String desc)
    {
        this.events.add(new Event(name, desc));
    }

    public boolean getIsSolved()
    {
        return this.isSolved;
    }

    public void setIsSolved(boolean isSolved)
    {
        this.isSolved = isSolved;
    }

    @JsonIgnore
    public String getWord()
    {
        return this.word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

}

class Event
{
    private String name;
    private String details;
    private Date date;

    public Event(String eventName, String details)
    {
        this.name = eventName;
        this.details = details;
        this.date = new Date();
    }
}