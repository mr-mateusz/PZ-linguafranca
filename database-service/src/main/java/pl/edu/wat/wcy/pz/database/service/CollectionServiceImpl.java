package pl.edu.wat.wcy.pz.database.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.wat.wcy.pz.database.dto.CollectionDto;
import pl.edu.wat.wcy.pz.database.dto.WordDto;
import pl.edu.wat.wcy.pz.database.entity.*;
import pl.edu.wat.wcy.pz.database.repository.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CollectionServiceImpl implements CollectionService {

    private UserRepository userRepository;
    private CollectionRepository collectionRepository;
    private CategoryRepository categoryRepository;
    private WordRepository wordRepository;
    private WordAnswerRepository wordAnswerRepository;

    @Override
    public List<Collection> getUserCollections(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User: " + principal.getName() + " not found"));
        return collectionRepository.findAllByUser(user);
    }

    @Override
    public ResponseEntity<Object> updateCollectionAndCategory(Long collectionId, CollectionDto collectionDto, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User: " + principal.getName() + " not found"));
        Collection collection = collectionRepository.findById(collectionId).orElseThrow(() -> new IllegalArgumentException("Collection: " + collectionId + " not found."));

        if (!collection.isModifiable() || collection.getCategory().isPublic() || !collection.getCategory().getOwner().equals(user))
            throw new IllegalArgumentException("Cannot update this collection!");

        if (collectionDto.getName() != null)
            collection.getCategory().setName(collectionDto.getName());
        if (collectionDto.getDescription() != null)
            collection.getCategory().setDescription(collectionDto.getDescription());
        if (collectionDto.getIsPublic()) {
            collection.getCategory().setPublic(true);
            collection.setModifiable(false);
        }

        categoryRepository.save(collection.getCategory());
        collectionRepository.save(collection);

        return ResponseEntity.ok(collection);
    }

    @Override
    public List<WordDto> getCollectionWords(Long id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User: " + principal.getName() + " not found"));
        Collection collection = collectionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Collection: " + id + " not found."));

        if (!collection.getUser().equals(user))
            throw new IllegalArgumentException("This collection belongs to another user");

        List<Word> words = wordRepository.findAllByCategory(collection.getCategory());

        List<WordDto> wordDtos = words.stream()
                .map(word -> {
                            WordAnswer wordAnswer = wordAnswerRepository.findByWordAndCollection(word, collection).orElse(new WordAnswer(null, null, 0, 0, 0));
                            return new WordDto(
                                    word.getWordId(),
                                    word.getPlTranslation(),
                                    word.getEngTranslation(),
                                    word.getDifficulty(),
                                    word.getEngExamples().stream().map(Example::getExample).collect(Collectors.toList()),
                                    wordAnswer.getCorrectAnswers(),
                                    wordAnswer.getIncorrectAnswers(),
                                    wordAnswer.isKnown()
                            );
                        }
                ).collect(Collectors.toList());
        return wordDtos;
    }

    @Override
    public WordDto addNewWord(Long collectionId, WordDto wordDto, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User: " + principal.getName() + " not found"));
        Collection collection = collectionRepository.findById(collectionId).orElseThrow(() -> new IllegalArgumentException("Collection: " + collectionId + " not found."));

        if (!collection.isModifiable() || collection.getCategory().isPublic() || !collection.getCategory().getOwner().equals(user))
            throw new IllegalArgumentException("Cannot add new word to this collection!");

        Word word = Word.builder()
                .category(collection.getCategory())
                .plTranslation(wordDto.getPlTranslation())
                .engTranslation(wordDto.getEngTranslation())
                .difficulty(wordDto.getDifficulty())
                .build();

        List<Example> examples = wordDto.getEngExamples().stream().map(s -> new Example(s, word)).collect(Collectors.toList());
        word.setEngExamples(examples);

        Word savedWord = wordRepository.save(word);

        int wordsInCategory = wordRepository.findAllByCategory(collection.getCategory()).size();
        long learnedWords = wordAnswerRepository.findAllByCollection(collection).stream().filter(WordAnswer::isKnown).count();
        collection.setLearningProgress(Math.toIntExact(learnedWords * 100 / wordsInCategory));
        collectionRepository.save(collection);

        return new WordDto(
                savedWord.getWordId(),
                savedWord.getPlTranslation(),
                savedWord.getEngTranslation(),
                savedWord.getDifficulty(),
                savedWord.getEngExamples().stream().map(Example::getExample).collect(Collectors.toList())
        );
    }
}
