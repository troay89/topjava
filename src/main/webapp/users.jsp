<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Add new user</title>
</head>
<body>

<form method="POST" action='meals' name="frmAddUser">
    <input type="hidden" name="id" value="<c:out value='${meal.id}' />"/><br/>
    datetime : <input type="text" name="datatime"
                      value="<fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${ meals.dateTime }"/>"/> <br/>
    DESCRIPTION : <input type="text" name="description1"
                         value="<c:out value="${meals.description}" />"/> <br/>

    CALORIES : <input type="text" name="calories"
                      value="<c:out value="${meals.calories}" />"/> <br/>
    <input type="submit" value="add meal" />
</form>
</body>
</html>