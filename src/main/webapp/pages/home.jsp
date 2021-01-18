<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css"/>
    <title>Home</title>
</head>
<body>
<jsp:include page="/pages/module/header.jsp"/>
<h1>
    <fmt:message key="home.welcomeMessage"/> ${sessionScope.username}
</h1>
<c:if test="${requestScope.confirmedMessage}">
    <div id="success" class="alert alert-success" role="alert">
        <h4 class="alert-heading text-center"><fmt:message key="home.successMessage.label"/> </h4>
        <p><fmt:message key="home.successMessage.body"/> </p>
        <hr>
        <p class="mb-0"><fmt:message key="home.successMessage.next"/> </p>
    </div>

</c:if>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>