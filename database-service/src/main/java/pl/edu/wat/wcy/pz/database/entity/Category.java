package pl.edu.wat.wcy.pz.database.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "categories")
public class Category {

    @Id
    private Long id;
    private String name;
    private String description;
    private Long owner;
    private boolean isPublic;
    private int difficulty;

}
