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
    <title>
        <fmt:message key="home.title"/>
    </title>
</head>
<body>
<jsp:include page="/pages/module/header.jsp"/>
<c:if test="${sessionScope.confirmedMessage}">
    <div class="alert alert-success col-md-3 text-center mx-auto" role="alert">
        <h4 class="alert-heading">
            <fmt:message key="home.successMessage.label"/>
        </h4>
        <p>
            <fmt:message key="home.successMessage.body"/>
        </p>
        <hr/>
        <p class="mb-0">
            <fmt:message key="home.successMessage.next"/>
        </p>
    </div>
    <c:remove var="confirmedMessage" scope="session"/>
</c:if>
<p class="fs-1 text-center">
    <fmt:message key="home.label.new"/>
</p>
<div class="row row-cols-1 row-cols-md-3 g-4 mx-auto shadow p-3 mb-5 bg-white rounded" style="width: 60rem">
    <c:forEach var="creature" items="${requestScope.creatureList}">
        <div class="col">
            <div class="card h-100 border-0">
                <img src="${pageContext.request.contextPath}/uploadController?url=${creature.picture}" class="card-img-top" alt="..." style=" width: 100%;object-fit: cover;height: 15vw">
                <div class="card-body">
                    <a href="${pageContext.request.contextPath}/controller?command=creature&id=${creature.id}" class="text-decoration-none stretched-link">
                        ${creature.name}
                    </a>
                    <p class="card-text text-truncate">
                            ${creature.description}
                    </p>
                </div>
                <div class="card-footer border-0">
                    <small class="text-muted">
                        <fmt:message key="home.creature.lastUpdated"/> ${creature.lastUpdated.toString()}
                    </small>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<p class="fs-1 text-center">
    <fmt:message key="home.label.mostPopular"/>
</p>
<div class="card-group mx-auto" style="width: 50rem">
    <div class="card">
        <img src="https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png" class="card-img-top" alt="...">
        <div class="card-body">
            <h5 class="card-title">Card title</h5>
            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
        </div>
        <div class="card-footer">
            <small class="text-muted">Last updated 3 mins ago</small>
        </div>
    </div>
    <div class="card">
        <img src="https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png" class="card-img-top" alt="...">
        <div class="card-body">
            <h5 class="card-title">Card title</h5>
            <p class="card-text">This card has supporting text below as a natural lead-in to additional content.</p>
        </div>
        <div class="card-footer">
            <small class="text-muted">Last updated 3 mins ago</small>
        </div>
    </div>
    <div class="card">
        <img src="https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png" class="card-img-top" alt="...">
        <div class="card-body">
            <h5 class="card-title">Card title</h5>
            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content than the first to show that equal height action.</p>
        </div>
        <div class="card-footer">
            <small class="text-muted">Last updated 3 mins ago</small>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>