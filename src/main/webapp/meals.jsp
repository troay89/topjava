<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<ul>
    <table border="1" , cellspacing="0" , cellpadding="5">
        <thead>
        <tr>
            <th>Date/Time</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Update</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <jsp:useBean id="mealList" scope="request" type="java.util.List"/>
        <c:forEach var="mealName" items="${mealList}">
            <tr style="color:${mealName.excess ? 'red' : 'green'}">
                <td>${mealName.id}</td>
                <td><fmt:parseDate value="${mealName.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                                   type="both"/>
                    <fmt:formatDate pattern="yyyy.MM.dd HH:mm" value="${ parsedDateTime }"/></td>
                <td>${mealName.description}</td>
                <td>${mealName.calories}</td>
                <td><a href="meals?action=update&mealId=<c:out value="${mealName.id}"/>">Update</a></td>
                <td><a href="meals?action=delete&mealId=<c:out value="${mealName.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <p><a href="meals?action=create">Add User</a></p>

</ul>
</body>
</html>



