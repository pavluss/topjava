<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<section>
    <h2>Meals</h2>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Дата</th>
            <th>Описание</th>
            <th>Калории</th>
        </tr>
        <jsp:useBean id="mealToList" scope="request" type="java.util.List"/>
        <c:forEach items="${mealToList}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr style="color: <c:out value="${meal.excess ? 'red' : 'green'}"/>">
                <td><%=TimeUtil.format(meal.getDateTime())%>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
