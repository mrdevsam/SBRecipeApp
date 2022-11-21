package app.sbrecipeapp.domain;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// @Data
// @EqualsAndHashCode(exclude = "recipe")
// @Entity
public class Notes {

	@Id
	private String id;
	
	private String recipeNotes;
	
}
