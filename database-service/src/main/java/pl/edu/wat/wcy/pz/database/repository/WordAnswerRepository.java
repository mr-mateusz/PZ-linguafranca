package pl.edu.wat.wcy.pz.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.wcy.pz.database.entity.Collection;
import pl.edu.wat.wcy.pz.database.entity.Word;
import pl.edu.wat.wcy.pz.database.entity.WordAnswer;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordAnswerRepository extends JpaRepository<WordAnswer, Long> {
    List<WordAnswer> findAllByWord(Word word);

    List<WordAnswer> findAllByCollection(Collection collection);

    Optional<WordAnswer> findByWordAndCollection(Word word, Collection collection);
}
