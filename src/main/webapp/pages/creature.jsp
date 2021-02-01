<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>
        ${requestScope.creature.name}
    </title>
</head>
<body>
<jsp:include page="/pages/module/header.jsp"/>
<div class="card mb-3 mx-auto shadow p-3 mb-5 bg-white rounded">
    <div class="row g-0">
        <div class="col-md-4">
            <img src="${pageContext.request.contextPath}/uploadController?url=${requestScope.creature.picture}" alt="..." style=" width: 100%;object-fit: cover;height: 15vw">
        </div>
        <div class="col-md-8">
            <div class="card-body">
                <h5 class="card-title">${requestScope.creature.name}</h5>
                <p class="card-text">${requestScope.creature.description}</p>
                <p class="card-text"><small class="text-muted">
                    <fmt:message key="home.creature.lastUpdated"/> ${requestScope.creature.lastUpdated}
                </small></p>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>
