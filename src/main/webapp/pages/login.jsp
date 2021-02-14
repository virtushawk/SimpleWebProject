<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property.text"/>
<fmt:message key="login.username.placeHolder" var="usernamePlaceHolder"/>
<fmt:message key="login.password.placeHolder" var="passwordPlaceHolder"/>
<!DOCTYPE html>
<html lang=${sessionScope.lang}>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css"/>
    <title>
        <fmt:message key="login.title"/>
    </title>
</head>
<body>
<jsp:include page="/pages/module/header.jsp"/>
<div>
    <form id="login_form" action="${pageContext.request.contextPath}/controller?command=login" method="post">
        <div class="form-group">
            <label for="username">
                <fmt:message key="login.username.label"/>
            </label>
            <input type="text" name="username" class="form-control" id="username" placeholder="${usernamePlaceHolder}" required>
        </div>
        <div class="form-group">
            <label for="password">
                <fmt:message key="login.password.label"/>
            </label>
            <input type="password" name="password" class="form-control" id="password" placeholder="${passwordPlaceHolder}" required>
        </div>
        <button type="submit" class="btn btn-primary">
            <fmt:message key="login.login"/>
        </button>
    </form>
    <div id="formFooter">
        <a class="underlineHover" href="${pageContext.request.contextPath}/pages/register.jsp">
            <fmt:message key="login.register"/>
        </a>
    </div>
    <div id="formFooter">
        <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#exampleModal">
            Forgot password?
        </button>
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=forgot_password" method="post" novalidate>
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Forgot password</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <label for="name">
                            Enter your username
                        </label>
                        <input type="text" name="name" class="form-control" id="name" required/>
                        <div class="valid-feedback">
                            <fmt:message key="createCreature.valid"/>
                        </div>
                        <div class="invalid-feedback">
                            <fmt:message key="createCreature.image.invalid"/>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Save changes</button>
                    </div>
                </form>
            </div>
        </div>
        <div class="mt-3">
            <h4>${requestScope.user.username}</h4>
        </div>
    </div>
    <c:if test="${sessionScope.errorMessage}">
    <div id="error" class="p-3 mb-2 bg-danger text-white">
        <fmt:message key="login.errorMessage"/>
    </div>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>
    <c:if test="${sessionScope.errorMessageDB}">
        <div id="error" class="p-3 mb-2 bg-danger text-white">
            <fmt:message key="login.errorMessageDB"/>
        </div>
        <c:remove var="errorMessageDB" scope="session"/>
    </c:if>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>
