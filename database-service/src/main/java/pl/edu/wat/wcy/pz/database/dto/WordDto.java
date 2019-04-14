package pl.edu.wat.wcy.pz.database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WordDto {
    private Long wordId;
    private String plTranslation;
    private String engTranslation;
    private int difficulty;
    private List<String> engExamples;
    private int correctAnswers;
    private int incorrectAnswers;
    private boolean isLearned;

    public WordDto(Long wordId, String plTranslation, String engTranslation, int difficulty, List<String> engExamples) {
        this.wordId = wordId;
        this.plTranslation = plTranslation;
        this.engTranslation = engTranslation;
        this.difficulty = difficulty;
        this.engExamples = engExamples;
    }
}
