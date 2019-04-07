package pl.edu.wat.wcy.pz.database.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDto {
    private String name;
    private String description;
    private Boolean isPublic;
}
