<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>User</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="<c:url value='/' />">Departments</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="main container">

    <div class="row">
        <h1>Users </h1>
    </div>

    <div class="container-fluid">

        <div class="row">
            <table class="table table-bordered">
                <tr>
                    <td>Name</td>
                    <td>Surname</td>
                    <td>Email</td>
                    <td>Birthday</td>
                    <td>Salary</td>
                    <td>Created</td>
                    <td>Department</td>
                    <td>Edit User Action</td>
                    <td>Delete User Action</td>

                </tr>

                <c:forEach var="user" items="${requestScope.allUsers}">
                    <tr>
                        <td>${user.name}</td>
                        <td>${user.surname}</td>
                        <td>${user.email}</td>
                        <td>${user.birthday}</td>
                        <td>${user.salary}</td>
                        <td>${user.created}</td>
                        <td>${user.department.name}</td>

                        <td><a class="btn btn-primary" href="/createOrUpdateUser?userId=${user.id}">Edit user</a></td>

                        <td>
                            <form method="post" action="<c:url value='/deleteUser' />">
                                <input type="hidden" name="userId" value="${user.id}">
                                <input class="btn btn-danger" type="submit" value="Delete user"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>

            </table>
        </div>

        <td><a class="btn btn-success" href="/createOrUpdateUser">Add new user</a></td>

    </div>
</div>
</body>
</html>
