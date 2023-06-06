<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>

    <style>
        TABLE {
            border-collapse: collapse;
            width: 300px;
        }
        TH {
            text-align: center;
        }
        TD {
            text-align: center;
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

<table>
<tr>
    <th>Date</th>
    <th>Description</th>
    <th>Calories</th>
    <th></th>
    <th></th>
</tr>
    <tr><td>34,5</td><td>3,5</td><td>36</td><td>21</td><td>21</td></tr>

<%--    <tr><th>Заголовок 1</th><th>Заголовок 2</th></tr>--%>
<%--    <tr><td>Ячейка 3</td><td>Ячейка 4</td></tr>--%>
</table>
</body>
</html>
