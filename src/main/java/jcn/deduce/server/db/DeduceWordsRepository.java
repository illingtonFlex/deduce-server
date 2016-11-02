package jcn.deduce.server.db;

import jcn.deduce.server.model.DeduceWordsList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeduceWordsRepository extends MongoRepository<DeduceWordsList, String>
{
}
