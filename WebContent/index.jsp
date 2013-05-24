<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="de.professional_webworkx.jee6.recipemanagementjpa.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RezeptManagementJPA</title>
</head>
<body>
<!-- 
	Holen wir uns direkt mal das aktuelle Rezept vom Session-Object 
	Uns fehlt hier natürlich noch der Import der Recipe-Klasse, diesen
	machen mir mit Hilfe einer @page Derektive
-->
<%
	Recipe recipe = (Recipe) session.getAttribute("currentRecipe");
%>

<!-- 
	Nun überprüfen wir, ob es schon ein Rezept gibt, wenn nein, dann bieten wir dem Benutzer 
	ein Formular an, mit dem er ein neues Rezept erstellen kann, hier brauchen wir aber 
	lediglich einen Rezeptnamen und die Dauer in Minuten, die es zur Zubereitung braucht.
 -->
 
 <% if(recipe == null) { %>
 	<h3>Neues Rezept anlegen</h3>
 	<form action="recipeServlet" method="post">
 	<strong>Rezeptbezeichnug: </strong><br/>
 	<input type="text" name="recipeName"/><br/>
 	<strong>Zubereitungszeit in Minuten: </strong><br/>
 	<input type="text" name="recipeTime"/><br/>
 	<input type="submit" value="Rezept erstellen"/>
 	</form>
 <% } else { %>
 	<h3>Zutaten zum Rezept <%=recipe.getRecipeName() %> hinzufügen</h3>
 	<!-- 
 		Hier füge ich nun noch das Formular zum Hinzufügen der Zutaten ein
 	 -->
 	 <form method="post" action="recipeServlet">
 	 <strong>Zutat: </strong><br/>
 	 <input type="text" name="ingredient"/><br/>
 	 <input type="submit" value="Zutat hinzufügen"/>
 	 </form>
 	<% if(recipe.getIngredients() == null) { %>
 	Bisher hast du noch keine Zutaten hinzugefügt<br/>
 	<% } else { %>
 	<ul>
 		<% for(Ingredient ingredient : recipe.getIngredients()) { %>
 			<li><%=ingredient.getIngredientName() %></li>
 		<% } %>
 	</ul>
 	<br/><br/>
 	<!-- Den Formular-Knopf zum Speichern des gesamten Rezepts habe ich schon vorbereitet, das ist
 	ja inzwischen auch nichts wildes mehr. -->
 	<form method="post" action="recipeServlet">
 	<!-- 
 		Interessant ist dieses "hidden" input-Feld, denn in unserem Servlet
 		können wir den Button nicht direkt indentifizieren, daher übergeben wir
 		beim Klick auf den Button einen Wert "save" (siehe value-Attribut im input type="hidden"..)
 		und so wissen wir nachher im Servlet, dass das Rezept jetzt in der Datenbank gespeichert
 		werden soll.
 	 -->
 	<input type="hidden" name="saveRecipe" value="save"/>
 	<input type="submit" value="Rezept speichern"/>
 	</form>
 	<% }
 	}
 	%>
</body>
</html>