package pl.edu.wat.wcy.pz.database.service;

import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface WordAnswerService {
    ResponseEntity<Object> updateAnswer(Long collectionId, Long wordId, boolean correct, Principal principal);
}
