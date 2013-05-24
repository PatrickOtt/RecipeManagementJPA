<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Genau, mit dem @Page import Statement holen wir uns den fehlenden Recipe-Import -->
<%@page import="de.professional_webworkx.jee6.recipemanagementjpa.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Speicherung war erfolgreich!</title>
</head>
<body>
<%
	Recipe recipe = (Recipe) session.getAttribute("currentRecipe");
%>
<h2 style="color: green;">Das Rezept <%=recipe.getRecipeName() %> wurde erfolgreich gespeichert.</h2>

</body>
</html>