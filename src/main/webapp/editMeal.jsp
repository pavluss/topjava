<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<section>
    <h2>${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" name="dateTime" value="${meal.dateTime}" required></dd>

            <dt>Description:</dt>
            <dd><input type="text" name="description" value="${meal.description}" required></dd>

            <dt>Calories:</dt>
            <dd><input type="number" name="calories" value="${meal.calories}" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button type="button" onclick="window.history.back()">Cancel</button>
    </form>
</section>
</body>
</html>
