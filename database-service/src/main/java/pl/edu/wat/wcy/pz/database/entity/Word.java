package pl.edu.wat.wcy.pz.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "words")
public class Word {

    @Id
    private Long id;
    private String plTranslation;
    private String engTranslation;
    private int difficulty;
    private List<String> engExamples;
    private Long categoryId;
}
