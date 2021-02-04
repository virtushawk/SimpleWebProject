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
        <fmt:message key="catalog.button.create"/>
    </title>
</head>
<body>
<jsp:include page="/pages/module/header.jsp"/>
<div class="container shadow p-3 mb-5 bg-white rounded">
    <form class="row g-3 justify-content-md-center needs-validation" action="${pageContext.request.contextPath}/controller?command=create_creature" method="post" enctype="multipart/form-data" novalidate>
        <div class="col-md-6">
            <label class="form-label" for="image">
                <fmt:message key="createCreature.image.label"/>
            </label>
            <input type="file" name="image" class="form-control" id="image"  accept="image/png,image/jpeg,image/jpg" required/>
            <div class="valid-feedback">
                <fmt:message key="createCreature.valid"/>
            </div>
            <div class="invalid-feedback">
                <fmt:message key="createCreature.image.invalid"/>
            </div>
        </div>
        <div class="col-md-6">
            <label for="name" class="form-label">
                <fmt:message key="createCreature.name.label"/>
            </label>
            <input type="text" name="name" class="form-control" id="name" required>
            <div class="valid-feedback">
                <fmt:message key="createCreature.valid"/>
            </div>
            <div class="invalid-feedback">
                <fmt:message key="createCreature.name.invalid"/>
            </div>
        </div>
        <div class="col-12">
            <label for="description" class="form-label">
                <fmt:message key="createCreature.description.label"/>
            </label>
            <textarea class="form-control" name="description" id="description" rows="3" required></textarea>
            <div class="valid-feedback">
                <fmt:message key="createCreature.valid"/>
            </div>
            <div class="invalid-feedback">
                <fmt:message key="createCreature.description.invalid"/>
            </div>
        </div>
        <div class="col-12">
            <button type="submit" class="btn btn-primary">
                <fmt:message key="createCreature.button.add"/>
            </button>
        </div>
    </form>
</div>
<c:if test="${sessionScope.errorMessage}">
    <div id="error" class="p-3 mb-2 bg-danger text-white">
        <fmt:message key="general.errorMessage"/>
    </div>
    <c:remove var="errorMessage" scope="session"/>
</c:if>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/form-validation.js"></script>
</body>
</html>