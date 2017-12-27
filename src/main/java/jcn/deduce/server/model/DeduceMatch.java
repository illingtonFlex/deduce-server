package jcn.deduce.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "DeduceMatches")
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class DeduceMatch
{
    @Id @Getter @Setter private String id;
    @Getter(onMethod = @__( @JsonIgnore )) @Setter @NonNull private String word;
    @Getter @Setter private String solution = "[UNSOLVED]";
    @Getter @Setter private Boolean isSolved = Boolean.FALSE;
    @Getter @Setter private List<Event> events = new ArrayList<>();

    private static final String LETTER_AT_INDEX_EVENT_KEY = "LETTER_AT_INDEX";
    private static final String SOLUTION_ATTEMPT_EVENT_KEY = "SOLUTION_ATTEMPT";

    public void addSolutionAttemptEvent(String solutionAttempt)
    {
        this.events.add(new Event(SOLUTION_ATTEMPT_EVENT_KEY, solutionAttempt));
    }

    public void addIndexInquiryEvent(String desc)
    {
        this.events.add(new Event(LETTER_AT_INDEX_EVENT_KEY, desc));
    }

    @JsonIgnore
    public boolean isReadyForSolving()
    {
        String lastEventName = events.size() > 0 ? events.get(events.size()-1).getEventName() : "NONE";

        return !SOLUTION_ATTEMPT_EVENT_KEY.equals(lastEventName);
    }
}

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
class Event
{
    @Getter @Setter @NonNull private String eventName;
    @Getter @Setter @NonNull private String details;
    private Date date = new Date();
}