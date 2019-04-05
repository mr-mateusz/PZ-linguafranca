package pl.edu.wat.wcy.pz.authorization.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.wcy.pz.authorization.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
