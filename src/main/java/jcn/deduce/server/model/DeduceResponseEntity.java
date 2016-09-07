package jcn.deduce.server.model;

import javax.ws.rs.core.Response;

public class DeduceResponseEntity
{
    private Response.Status status;
    private String message;
    private Object entity;

    public DeduceResponseEntity()
    {

    }

    public DeduceResponseEntity(Response.Status httpStatus, Object entity, String message)
    {
        this.entity = entity;
        this.status = httpStatus;
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String msg)
    {
        this.message = msg;
    }

    public Object getEntity()
    {
        return entity;
    }

    public void setEntity(Object entity)
    {
        this.entity = entity;
    }

    public Response.Status getStatus()
    {
        return status;
    }

    public void setStatus(Response.Status status)
    {
        this.status = status;
    }
}
