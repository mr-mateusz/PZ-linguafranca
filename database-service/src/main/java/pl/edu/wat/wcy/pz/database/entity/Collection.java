package pl.edu.wat.wcy.pz.database.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "collections")
public class Collection {

    @Id
    private Long id;
    private Long userId;
    private boolean isModifiable;
    private int learningProgress;
    @DBRef
    private Category category;
}
