package pl.edu.wat.wcy.pz.database.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.wcy.pz.database.service.WordAnswerService;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/linguafranca/collections")
public class WordAnswerController {

    private WordAnswerService wordAnswerService;

    @PostMapping("/{collectionId}/words/{wordId}/answer/{correct}")
    public ResponseEntity<Object> updateAnswer(@PathVariable("collectionId") Long collectionId, @PathVariable("wordId") Long wordId, @PathVariable("correct") boolean correct, Principal principal) {
        return wordAnswerService.updateAnswer(collectionId, wordId, correct, principal);
    }
}
