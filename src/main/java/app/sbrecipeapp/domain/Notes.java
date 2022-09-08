package app.sbrecipeapp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "recipe")
@Entity
public class Notes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//if we delete a recipe, the note regarding that recipe
	//will be deleted. But if we delete a recipe-note, the recipe
	//will not get deleted.
	//That is why this Property does not need any casecadeTypes
	@OneToOne
	private Recipe recipe;

	@Lob
	private String recipeNotes;
	
}
