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
    <title>Login</title>
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
    <c:if test="${requestScope.errorMessage}">
    <div id="error" class="p-3 mb-2 bg-danger text-white">
        <fmt:message key="login.errorMessage"/>
    </div>
    </c:if>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>
