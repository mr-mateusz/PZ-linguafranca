package pl.edu.wat.wcy.pz.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.wcy.pz.database.entity.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
