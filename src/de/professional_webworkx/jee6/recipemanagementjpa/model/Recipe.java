package de.professional_webworkx.jee6.recipemanagementjpa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Recipe
 *
 */
@Entity
@Table(name="recipe")
public class Recipe implements Serializable {

	
	private static final long serialVersionUID = 1L;

	/*
	 * Die später in der Datenbank vorhandenen Spalten sind in der Java Welt 
	 * die Eigenschaften der Klasse (Entity)
	 */
	private int recipeID;
	private String recipeName;
	private int recipeTime;
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	
	
	public Recipe() {
		super();
	}


	/*
	 * Wir haben nun zwei Möglichkeiten dem Entity-Manager zu sagen, 
	 * welche Namen die Attribute später die Spalten in der Datenbank haben sollen,
	 * weiterhin muss man z.B. auch einen PrimaryKey definieren und das geht so:
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="recipeID")
	public int getRecipeID() {
		return recipeID;
	}


	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}


	@Column(name="recipeName", length=50)
	public String getRecipeName() {
		return recipeName;
	}


	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}


	@Column(name="recipeTime")
	public int getRecipeTime() {
		return recipeTime;
	}


	public void setRecipeTime(int recipeTime) {
		this.recipeTime = recipeTime;
	}


	/*
	 * Ein Rezept kann 1 oder mehrere Zutaten enthalten, daher mappen wir mittels der recipeID in 
	 * der Klasse Ingredient die Zutaten zu dem entsprechenden Rezept,
	 * drüben in der Ingredient-Klasse sieht das so aus:
	 */
	@OneToMany(mappedBy="recipeID", cascade=CascadeType.ALL)
	public List<Ingredient> getIngredients() {
		if(ingredients == null) {
			ingredients = new ArrayList<Ingredient>();
		}
		return ingredients;
	}


	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}


	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	
   
	/*
	 * Eclipse kann uns dabei unterstützen die Getter- und Setter-Methoden 
	 * zu generieren, das spart uns Tipparbeit und senkt die Fehleranfälligkeit
	 * durch Tippfehler.
	 */
	
	
}
