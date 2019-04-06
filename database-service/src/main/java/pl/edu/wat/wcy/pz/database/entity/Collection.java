package pl.edu.wat.wcy.pz.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COLLECTION")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COLLECTION_ID")
    private Long collectionId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "IS_MODIFIABLE")
    private boolean isModifiable;

    @Column(name = "LEARNING_PROGRESS")
    private int learningProgress;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
}
