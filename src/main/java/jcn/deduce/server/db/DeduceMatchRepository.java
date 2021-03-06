package jcn.deduce.server.db;

import jcn.deduce.server.model.DeduceMatch;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

public interface DeduceMatchRepository extends MongoRepository<DeduceMatch, String>
{
    DeduceMatch findById(String id);
}