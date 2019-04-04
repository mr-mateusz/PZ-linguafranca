package pl.edu.wat.wcy.pz.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.wcy.pz.database.entity.Collection;

@Repository
public interface CollectionRepository extends MongoRepository<Collection, Long> {
}