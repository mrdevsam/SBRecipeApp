package app.sbrecipeapp.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.Setter;
import lombok.Data;

//@Getter
//@Setter
@Data
@EqualsAndHashCode(exclude = "recipes")
@Entity
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;//here, "categories" is property name of the Recipe entity which handle other side of the relation.

}
