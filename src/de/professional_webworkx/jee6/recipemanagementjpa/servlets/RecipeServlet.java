package de.professional_webworkx.jee6.recipemanagementjpa.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

import de.professional_webworkx.jee6.recipemanagementjpa.model.Ingredient;
import de.professional_webworkx.jee6.recipemanagementjpa.model.Recipe;

/**
 * Servlet implementation class RecipeServlet
 */
@WebServlet({ "/RecipeServlet", "/recipeServlet" })
public class RecipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private List<Ingredient> ingredients;
	private RequestDispatcher dispatcher;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecipeServlet() {
        super();
    }
    
    /**
     * processRequest(HttpServletRequest request, HttpServletResponse response)
     * @throws IOException 
     * @throws ServletException 
     */
    
    @SuppressWarnings("unchecked")
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// hier holen wir uns das HttpSession-Object vom request-Object, dieses erhalten wir
    	// wenn ein Client eine Anfrage an unsere Website schickt
    	HttpSession session = request.getSession();
    	
    	// aus dem Session-Object holen wir uns das Attribut "currentRecipe" raus und
    	// speichern es in einem Object vom Typ Recipe
    	Recipe recipe = (Recipe) session.getAttribute("currentRecipe");

    	// Nun checken wir, ob der Benutzer eventuell das Rezept fertig eingegeben hat und
    	// das ganze Ding jetzt in der Datenbank persisten abspeichern will
    	
    	String saveString = request.getParameter("saveRecipe");
    	// wenn er den Button gedrückt hat und save übermittelt wurde
    	if(saveString != null && saveString.equalsIgnoreCase("save")) {
    		
    		// besorgen wir uns einen Entity-Manager über unserer Persistence-Unit
    		// deren Namen können wir aus der Datei persistence.xml auslesen...
    		// sicherheitshalber gleich kopieren um Tippfehler zu vermeiden.
    		EntityManager em = Persistence.createEntityManagerFactory("RecipeManagementJPA").createEntityManager();
    		
    		// bevor wir das Object persistent machen können, brauchen wir eine Transaktion
    		// Transaktionen sind im Datenbank-Umfeld sehr bekannt, sie folgen dem ACID Prinzip
    		// geht bei solch einer Transaktion etwas schief, dann wird der Anfangszustand (Rollback)
    		// wiederhergestellt
    		// so eine Tranaktion besorgen wir uns ebenfalls vom EntityManager
    		em.getTransaction().begin();
    		if(em.contains(recipe)) {
    			// gibt's das Rezept schon, dann versuchen wir es upzudaten
    			em.merge(recipe);
    		} else {
    			em.persist(recipe);
    		}
    		// falls alles gut ging, speichern wir nun die Änderungen in der Datenbank mit Hilfe der
    		// Transaktion 
    		em.getTransaction().commit();
    		
    		// Am Ende wird der EntityManager wieder geschlossen, die Tür machen wir ja auch zu
    		// wenn wir das Haus / die Wohnung verlassen ;)
    		
    		em.close();
    		
    		// konnte erfolgreich gespeichert werden, dann gehen wir auf die Seite und 
    		// sagen das dem Benutzer..
    		dispatcher = request.getRequestDispatcher("persistentSuccess.jsp");
    		dispatcher.forward(request, response);
    	}
    	
    	// Prüfung, ob das aus der Session geholte Object null ist
    	if(recipe == null) {
    		// wenn ja, dann legen wir ein neues Rezept an und befüllen es mit den
    		// Werten, die uns der Client (Benutzer) übermittelt hat
    		recipe = new Recipe();
    		// diese Werte fragen wir aus dem request-Object mit getParameter() ab
    		// der Name recipeName und eine Zeile weiter unten recipeTime sind die
    		// Namen der HTML Input-Felder, die wir gleich in unserer JSP Seite einbauen
    		recipe.setRecipeName(request.getParameter("recipeName"));
        	recipe.setRecipeTime(Integer.parseInt(request.getParameter("recipeTime")));
        	
    	} else {
    		
    		// wenn es schon ein Rezept gibt, wollen wir ja die Zutaten hinzufügen
    		// die wir für das Rezept brauchen, das machen wir dann in diesem else Zweig
    		
    		Ingredient ingredient = new Ingredient();
    		String ingredientName = request.getParameter("ingredient");
    		// hier noch eine kleine Änderung zum Video Teil 2, ich habe noch
    		// eine Prüfung eingebaut, denn wenn man nun den Speichern-Button drückt
    		// wird ja ein leerer String als Zutat mitgeschickt, um aber zu vermeiden
    		// das NULL auf der Liste unserer Zutaten landet, hier diese kleine
    		// if-Verzweigung..
    		/*
    		 * Nur wenn der Name der Zutat != null ist UND nach entfernen der Leerzeichen
    		 * am Anfang und am Ende der String nicht leer ist, wird eine neue Zutat
    		 * an die Liste angefügt.
    		 */
    		if(ingredientName != null && !ingredientName.trim().isEmpty()) {
    			ingredient.setIngredientName(ingredientName);
    			ingredient.setRecipeID(recipe);
    			recipe.getIngredients().add(ingredient);
    		}

    		
    	}
    	// wenn wir ein neues Rezept erstellt und mit den Werten befüllt haben
    	// hängen wir es mit setAttribute() an das aktuelle Session-Object an
    	session.setAttribute("currentRecipe", recipe);
    	
    	// das dispatcher-Object gibt dann die Zuständigkeit an die angegebene JSP Datei ab
    	dispatcher = request.getRequestDispatcher("index.jsp");
    	// in der JSP Datei greifen wir dann auf die Session zu und holen uns z.B. den Namen
    	// des aktuellen Rezepts raus.
    	dispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
