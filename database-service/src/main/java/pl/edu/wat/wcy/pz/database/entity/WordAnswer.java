package pl.edu.wat.wcy.pz.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WORD_ANSWER")
public class WordAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORD_ANSWER_ID")
    private Long wordAnswerId;

    @ManyToOne
    @JoinColumn(name = "WORD_ID")
    private Word word;

    @ManyToOne
    @JoinColumn(name = "COLLECTION_ID")
    private Collection collection;

    @Column(name = "CORRECT_ANSWERS")
    private int correctAnswers;

    @Column(name = "INCORRECT_ANSWERS")
    private int incorrectAnswers;

    @Column(name = "COUNTER")
    private int counter;

    public WordAnswer(Word word, Collection collection, int correctAnswers, int incorrectAnswers, int counter) {
        this.word = word;
        this.collection = collection;
        this.correctAnswers = correctAnswers;
        this.incorrectAnswers = incorrectAnswers;
        this.counter = counter;
    }

    public void incrementCorrectAnswers() {
        correctAnswers++;
        if (counter < 5)
            counter++;
    }

    public void incrementIncorrectAnswers() {
        incorrectAnswers++;
        if (counter > 0)
            counter--;
    }

    public boolean isKnown() {
        return counter > 3;
    }

}
