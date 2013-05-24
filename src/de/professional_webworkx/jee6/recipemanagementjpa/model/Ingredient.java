package de.professional_webworkx.jee6.recipemanagementjpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Ingredient
 *
 */
@Entity
@Table(name="ingredients")
public class Ingredient implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private int ingredientID;
	private String ingredientName;
	private Recipe recipeID;

	public Ingredient() {
		super();
	}

	// Getters und Setters
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getIngredientID() {
		return ingredientID;
	}

	public void setIngredientID(int ingredientID) {
		this.ingredientID = ingredientID;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public Recipe getRecipeID() {
		return recipeID;
	}

	public void setRecipeID(Recipe recipeID) {
		this.recipeID = recipeID;
	}
	
	
	
   
}
