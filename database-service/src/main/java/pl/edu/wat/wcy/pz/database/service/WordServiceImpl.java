package pl.edu.wat.wcy.pz.database.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.pz.database.entity.Word;
import pl.edu.wat.wcy.pz.database.entity.WordAnswer;
import pl.edu.wat.wcy.pz.database.repository.WordAnswerRepository;
import pl.edu.wat.wcy.pz.database.repository.WordRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class WordServiceImpl implements WordService {

    private WordRepository wordRepository;
    private WordAnswerRepository wordAnswerRepository;

    @Override
    public List<Word> getReportedWords() {
        return wordRepository.findAllByIsReported(true);
    }

    public ResponseEntity<Object> changeWordStatus(Long wordId, boolean report) {
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new IllegalArgumentException("Word: " + wordId + " not found!"));
        word.setIsReported(report);
        wordRepository.save(word);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Object> deleteWord(Long wordId) {
        Word word = wordRepository.findById(wordId).orElseThrow(() -> new IllegalArgumentException("Word: " + wordId + " not found!"));

        List<WordAnswer> answers = wordAnswerRepository.findAllByWord(word);
        wordAnswerRepository.deleteAll(answers);

        wordRepository.delete(word);
        return ResponseEntity.ok().build();
    }
}
