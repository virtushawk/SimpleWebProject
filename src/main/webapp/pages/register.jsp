<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property.text"/>
<fmt:message key="register.email.placeHolder" var="emailPlaceHolder"/>
<fmt:message key="register.password.placeHolder" var="passwordPlaceHolder"/>
<fmt:message key="register.username.placeHolder" var="usernamePlaceHolder"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css"/>
    <title>
        <fmt:message key="register.label"/>
    </title>
</head>
<body>
<jsp:include page="/pages/module/header.jsp"/>
<div>
    <form id="registration_form" action="${pageContext.request.contextPath}/controller?command=register" method="post">
        <div class="form-group">
            <label for="email">
                <fmt:message key="register.email.label"/>
            </label>
            <input type="email" name="email" class="form-control" id="email" placeholder="${emailPlaceHolder}" value="${sessionScope.email}" required>
        </div>
        <div class="form-group">
            <label for="username">
                <fmt:message key="register.username.label"/>
            </label>
            <input type="text" name="username" class="form-control" id="username" aria-describedby="usernameHelp" placeholder="${usernamePlaceHolder}" value="${sessionScope.username}" required pattern="(^([a-zA-Z]){1,10}$)">
            <small id="usernameHelp" class="form-text text-muted">
                <fmt:message key="register.username.help"/>
            </small>
        </div>
        <div class="form-group">
            <label for="password">
                <fmt:message key="register.password.label"/>
            </label>
            <input type="password" name="password" class="form-control" id="password" aria-describedby="passwordHelp" placeholder="${passwordPlaceHolder}" value="${sessionScope.password}" required  pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$">
            <small id="passwordHelp" class="form-text text-muted">
                <fmt:message key="register.password.help"/>
            </small>
        </div>
        <div class="form-group">
            <label for="confirm_password">
                <fmt:message key="profile.changePassword.confirmPassword"/>
            </label>
            <input type="password" name="password" id="confirm_password" class="form-control" required pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$"/>
            <div class="valid-feedback">
                <fmt:message key="createCreature.valid"/>
            </div>
            <div class="invalid-feedback">
                <fmt:message key="profile.confirmPassword.invalid"/>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">
            <fmt:message key="register.register"/>
        </button>
    </form>
    <c:if test="${sessionScope.errorMessage}">
        <div id="error" class="p-3 mb-2 bg-danger text-white">
            <fmt:message key="register.errorMessage"/>
        </div>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>
    <c:if test="${sessionScope.errorMessageDB}">
        <div id="error" class="p-3 mb-2 bg-danger text-white">
            <fmt:message key="login.errorMessageDB"/>
        </div>
        <c:remove var="errorMessageDB" scope="session"/>
    </c:if>
    <c:remove var="email" scope="session"/>
    <c:remove var="password" scope="session"/>
    <c:remove var="username" scope="session"/>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/passwordConfirmation.js"></script>
</body>
</html>