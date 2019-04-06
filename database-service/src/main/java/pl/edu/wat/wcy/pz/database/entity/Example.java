package pl.edu.wat.wcy.pz.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private Word word;
}
