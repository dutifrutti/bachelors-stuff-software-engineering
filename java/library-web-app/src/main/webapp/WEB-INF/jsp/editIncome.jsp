<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
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
<form:form action="editincome" method="post" modelAttribute="income">
    <table border="0" cellpadding="5">
        <tr>
<%--            <td>Category name:--%>
<%--                ${income.category.name}--%>
<%--            <td><input:hidden  path="category"/></td>--%>
        </tr>
        <tr>
            <td>Name:</td>
            <td><form:input  path="name"/></td>
        </tr>
        <tr>
            <td>Amount:</td>
            <td><form:input path="amount"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Save"/></td>
        </tr>
    </table>
</form:form>
</div>
</body>
</html>
