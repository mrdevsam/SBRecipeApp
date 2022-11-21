package app.sbrecipeapp.domain;

import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@Getter
@Setter
@Document
public class UnitOfMeasure {

	@Id
	private String id;
	
	private String description;
	
}
