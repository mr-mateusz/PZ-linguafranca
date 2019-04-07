package pl.edu.wat.wcy.pz.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WORD")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORD_ID")
    private Long wordId;

    @Column(name = "PL_TRANSLATION")
    private String plTranslation;

    @Column(name = "ENG_TRANSLATION")
    private String engTranslation;

    @Column(name = "DIFFICULTY")
    private int difficulty;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "word"
    )
    private List<Example> engExamples;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    @JsonIgnore
    private Category category;

    @Column(name = "IS_REPORTED")
    @JsonIgnore
    private Boolean isReported;
}
