package jcn.deduce.server.endpoints;

import jcn.deduce.server.db.DeduceMatchRepository;

import javax.ws.rs.Path;

@Path("/deduceMatch")
public abstract class DeduceMatchResource
{
    protected DeduceMatchRepository repository;

    public DeduceMatchResource(DeduceMatchRepository repo)
    {
        this.repository = repo;
    }
}
