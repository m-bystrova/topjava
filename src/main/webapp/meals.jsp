<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %>
<%@ page import="static ru.javawebinar.topjava.util.MealsUtil.filteredByStreamsWithoutTimeLimits" %>
<%@ page import="static ru.javawebinar.topjava.util.MealsUtil.getMeals" %>
<%@ page import="static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY" %>

<html>
<head>
    <title>Title</title>

    <style>
        TABLE {
            border-collapse: collapse;
        }

        TH, TD {
            border: 1px solid black;
            padding: 4px;
        }
    </style>
</head>


<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<% List<MealTo> meals = filteredByStreamsWithoutTimeLimits(getMeals(), DEFAULT_CALORIES_PER_DAY); %>

<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
<c:forEach items='<%= meals%>' var="meal">
    <javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH:mm" var="dateTime"/>

    <tr style="color:${meal.excess ?  'red': 'green'}">
        <td>${dateTime}</td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        <td>${"update"}</td>
        <td>${"delete"}</td>
    </tr>
</>
</c:forEach>

</body>
</html>
