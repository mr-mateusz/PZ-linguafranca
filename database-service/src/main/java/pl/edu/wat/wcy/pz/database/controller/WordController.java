package pl.edu.wat.wcy.pz.database.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.pz.database.entity.Word;
import pl.edu.wat.wcy.pz.database.service.WordService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("linguafranca/words")
public class WordController {

    private WordService wordService;

    @PostMapping("/{id}/report")
    public ResponseEntity<Object> reportWord(@PathVariable("id") Long wordId) {
        return wordService.changeWordStatus(wordId, true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/reported")
    public List<Word> getReportedWords() {
        return wordService.getReportedWords();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{id}/ok")
    public ResponseEntity<Object> markWordAsOk(@PathVariable("id") Long wordId) {
        return wordService.changeWordStatus(wordId, false);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWord(@PathVariable("id") Long wordId) {
        return wordService.deleteWord(wordId);
    }
}
