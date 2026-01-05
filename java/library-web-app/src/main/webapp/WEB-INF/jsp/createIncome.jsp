<%--
  Created by IntelliJ IDEA.
  User: ignas
  Date: 13/12/2020
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>kazkas</title>
</head>
<body>
<h1> Create income</h1>
<div>
<form action="save" method="post">
    <table border="0" cellpadding="5">
        <tr>
            <td>Category name:</td>
            <td><input type="text" name="catName"/></td>
        </tr>
        <tr>
            <td>Name:</td>
            <td><input type="text" name="name"/></td>
        </tr>
        <tr>
            <td>Amount:</td>
            <td><input type="text" name="amount"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Save"/></td>
        </tr>
    </table>
</form>
</div>
</body>
</html>
