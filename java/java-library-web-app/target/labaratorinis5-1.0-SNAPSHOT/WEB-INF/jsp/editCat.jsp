<%--
  Created by IntelliJ IDEA.
  User: ignas
  Date: 13/12/2020
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head>
    <title>kazkas</title>
</head>
<body>
<h1> Edit category</h1>
<div>
    <form:form action="editCat" method="post" modelAttribute="category">
        <table border="0" cellpadding="5">
            <tr><td>Old name:</td>
                <td>${category.name}
                    <form:hidden  path="name"/></td>
                <td>Name:</td>
                <td><input type="text" name="newName"/></td>
            </tr>
            <tr><td>Old description:</td>
                <td>${category.description}
                    <form:hidden path="description"/></td>
                <td>Name:</td>
                <td><input type="text" name="newDescription"/></td>
            </tr>
            <tr><td>Old parent category:</td>
                <td>${category.parentCategory}
                    <form:hidden  path="parentCategory"/></td>
                <td>Name:</td>
                <td><input type="text" name="newParentCategory"/></td>
            </tr>
            <tr>
            <tr>
                <td><input type="submit" value="Edit"/></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>
