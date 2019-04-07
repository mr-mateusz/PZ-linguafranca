package pl.edu.wat.wcy.pz.database.service;

import org.springframework.http.ResponseEntity;
import pl.edu.wat.wcy.pz.database.dto.CollectionDto;
import pl.edu.wat.wcy.pz.database.dto.WordDto;
import pl.edu.wat.wcy.pz.database.entity.Collection;

import java.security.Principal;
import java.util.List;

public interface CollectionService {
    List<Collection> getUserCollections(Principal principal);

    ResponseEntity<Object> updateCollectionAndCategory(Long collectionId, CollectionDto collectionDto, Principal principal);

    List<WordDto> getCollectionWords(Long id, Principal principal);

    WordDto addNewWord(Long collectionId, WordDto wordDto, Principal principal);
}
