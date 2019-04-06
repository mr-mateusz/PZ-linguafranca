package pl.edu.wat.wcy.pz.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    @Column(name = "NAME")
    @Length(min = 3, max = 50)
    private String name;

    @Column(name = "DESCRIPTION")
    @Length(max = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    @JsonIgnore
    private User owner;

    @Column(name = "IS_PUBLIC")
    private boolean isPublic;

    @Column(name = "DIFFICULTY")
    private int difficulty;

}

