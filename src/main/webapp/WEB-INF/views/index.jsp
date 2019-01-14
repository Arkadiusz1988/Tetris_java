<%--@elvariable id="user" type="pl.coderslab.entity.User"--%>
<%--
  Created by IntelliJ IDEA.
  User: wierz
  Date: 12/9/2018
  Time: 3:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Hello ${user.username}
<br>

<%--<a href="http://localhost:8080/task/showAllTaskOfProject">Wyświetl liste zadan do swojego projektu</a><br>--%>

<a href="http://localhost:8080/start1">Zagraj w tetrisa</a><br>

<a href="http://localhost:8080/logout">Wyloguj sie</a><br>

</body>
</html>
