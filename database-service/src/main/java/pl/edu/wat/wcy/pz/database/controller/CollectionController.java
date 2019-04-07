package pl.edu.wat.wcy.pz.database.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.pz.database.dto.CollectionDto;
import pl.edu.wat.wcy.pz.database.dto.WordDto;
import pl.edu.wat.wcy.pz.database.entity.Collection;
import pl.edu.wat.wcy.pz.database.service.CollectionService;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/linguafranca/collections")
public class CollectionController {

    private CollectionService collectionService;

    @GetMapping("/")
    public List<Collection> getUserCollections(Principal principal) {
        return collectionService.getUserCollections(principal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCollection(@PathVariable("id") Long id, @RequestBody CollectionDto collectionDto, Principal principal) {
        return collectionService.updateCollectionAndCategory(id, collectionDto, principal);
    }

    @GetMapping("/{id}/words")
    public List<WordDto> getCollectionWords(@PathVariable("id") Long id, Principal principal) {
        return collectionService.getCollectionWords(id, principal);
    }

    @PostMapping("/{id}/words")
    public WordDto addNewWord(@PathVariable("id") Long collectionId, @RequestBody WordDto wordDto, Principal principal) {
        return collectionService.addNewWord(collectionId, wordDto, principal);
    }
}
