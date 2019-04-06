package pl.edu.wat.wcy.pz.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.wcy.pz.database.entity.Collection;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
}
