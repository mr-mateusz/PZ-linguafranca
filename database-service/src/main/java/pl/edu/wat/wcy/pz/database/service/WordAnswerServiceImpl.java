package pl.edu.wat.wcy.pz.database.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.pz.database.entity.Collection;
import pl.edu.wat.wcy.pz.database.entity.User;
import pl.edu.wat.wcy.pz.database.entity.Word;
import pl.edu.wat.wcy.pz.database.entity.WordAnswer;
import pl.edu.wat.wcy.pz.database.repository.CollectionRepository;
import pl.edu.wat.wcy.pz.database.repository.UserRepository;
import pl.edu.wat.wcy.pz.database.repository.WordAnswerRepository;
import pl.edu.wat.wcy.pz.database.repository.WordRepository;

import java.security.Principal;

@Service
@AllArgsConstructor
public class WordAnswerServiceImpl implements WordAnswerService {

    private UserRepository userRepository;
    private CollectionRepository collectionRepository;
    private WordRepository wordRepository;
    private WordAnswerRepository wordAnswerRepository;

    @Override
    public ResponseEntity<Object> updateAnswer(Long collectionId, Long wordId, boolean correct, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User: " + principal.getName() + " not found"));

        Collection collection = collectionRepository.findById(collectionId).orElseThrow(() -> new IllegalArgumentException("Collection: " + collectionId + " not found"));
        if (!collection.getUser().equals(user))
            throw new IllegalArgumentException("This collection belongs to another user");

        Word word = wordRepository.findById(wordId).orElseThrow(() -> new IllegalArgumentException("Word: " + wordId + " not found"));
        if (!word.getCategory().equals(collection.getCategory()))
            throw new IllegalArgumentException("This word belongs to another category");

        WordAnswer wordAnswer = wordAnswerRepository.findByWordAndCollection(word, collection).orElse(new WordAnswer(word, collection, 0, 0, 0));

        if (correct)
            wordAnswer.incrementCorrectAnswers();
        else
            wordAnswer.incrementIncorrectAnswers();
        wordAnswerRepository.save(wordAnswer);

        int wordsInCategory = wordRepository.findAllByCategory(collection.getCategory()).size();
        long learnedWords = wordAnswerRepository.findAllByCollection(collection).stream().filter(WordAnswer::isKnown).count();

        collection.setLearningProgress(Math.toIntExact(learnedWords * 100 / wordsInCategory));
        collectionRepository.save(collection);

        return ResponseEntity.ok().build();
    }
}
