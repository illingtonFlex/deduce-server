package jcn.deduce.server.model;


import lombok.*;

import javax.ws.rs.core.Response;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class DeduceResponseEntity
{
    @Getter @Setter @NonNull private Response.Status status;
    @Getter @Setter private Object entity;
    @Getter @Setter @NonNull private String message;
}
