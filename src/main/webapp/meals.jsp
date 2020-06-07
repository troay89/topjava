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
    <table border="1", cellspacing="0", cellpadding="5">
        <thead>
        <tr>
            <th>Date/Time</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        </thead>
        <tbody>
        <jsp:useBean id="wentThroughCalories" scope="request" type="java.util.List"/>
        <c:forEach var="wentThroughCaloriesName" items ="${wentThroughCalories}">
            <tr style="color:${wentThroughCaloriesName.isExcess() ? 'red' : 'green'}">
                <td><fmt:parseDate value="${wentThroughCaloriesName.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                                   type="both"/>
                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/></td>
                <td>${wentThroughCaloriesName.getDescription()}</td>
                <td>${wentThroughCaloriesName.getCalories()}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</ul>
</body>
</html>



