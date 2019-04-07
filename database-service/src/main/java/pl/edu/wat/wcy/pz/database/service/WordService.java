package pl.edu.wat.wcy.pz.database.service;

import org.springframework.http.ResponseEntity;
import pl.edu.wat.wcy.pz.database.entity.Word;

import java.util.List;

public interface WordService {
    List<Word> getReportedWords();

    ResponseEntity<Object> changeWordStatus(Long wordId, boolean report);

    ResponseEntity<Object> deleteWord(Long wordId);
}
