package pl.edu.wat.wcy.pz.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "EXAMPLE")
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXAMPLE_ID")
    private Long exampleId;

    @Column(name = "EXAMPLE")
    private String example;

    @ManyToOne
    @JoinColumn(name = "WORD_ID")
    @JsonIgnore
    private Word word;

    public Example(String example, Word word) {
        this.example = example;
        this.word = word;
    }
}
