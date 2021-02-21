<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="e" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <title>
        <fmt:message key="home.title"/>
    </title>
</head>
<body>
<jsp:include page="/pages/module/header.jsp"/>
<c:if test="${sessionScope.confirmedMessage}">
    <div class="alert alert-success col-md-3 text-center mx-auto">
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
<c:if test="${requestScope.errorMessageDB or sessionScope.errorMessageDB}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="home.errorMessageDB"/>
        </div>
    </div>
    <c:remove var="errorMessageDB" scope="session"/>
</c:if>
<p class="fs-1 text-center">
    <fmt:message key="home.label.new"/>
</p>
<div class="row row-cols-1 row-cols-md-3 g-4 mx-auto shadow p-3 mb-5 bg-white rounded" style="width: 60rem">
    <c:forEach var="creature" items="${requestScope.newCreatures}">
        <div class="col">
            <div class="card h-100 border-0">
                <img src="${pageContext.request.contextPath}/uploadController?url=${creature.picture}" class="card-img-top" alt="<fmt:message key="home.image.alt"/>" style="width: 100%;object-fit: cover;height: 15vw">
                <div class="card-body">
                    <a href="${pageContext.request.contextPath}/controller?command=creature&id=${creature.id}" class="text-decoration-none stretched-link">
                        <e:forHtml value="${creature.name}"/>
                    </a>
                    <p class="card-text text-truncate">
                        <e:forHtml value="${creature.description}"/>
                    </p>
                </div>
                <div class="card-footer border-0">
                    <small class="text-muted">
                        <fmt:message key="home.creature.lastUpdated"/> ${creature.lastUpdated}
                    </small>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<p class="fs-1 text-center">
    <fmt:message key="home.label.mostPopular"/>
</p>
<div class="row row-cols-1 row-cols-md-3 g-4 mx-auto shadow p-3 mb-5 bg-white rounded" style="width: 60rem">
    <c:forEach var="creature" items="${requestScope.popularCreatures}">
        <div class="col">
            <div class="card h-100 border-0">
                <img src="${pageContext.request.contextPath}/uploadController?url=${creature.picture}" class="card-img-top" alt="<fmt:message key="home.image.alt"/>" style="width: 100%;object-fit: cover;height: 15vw">
                <div class="card-body">
                    <a href="${pageContext.request.contextPath}/controller?command=creature&id=${creature.id}" class="text-decoration-none stretched-link">
                        <e:forHtml value="${creature.name}"/>
                    </a>
                    <p class="card-text text-truncate">
                        <e:forHtml value="${creature.description}"/>
                    </p>
                    <p class="card-text">
                    </p>
                </div>
                <div class="card-footer border-0">
                    <small class="text-muted">
                        <fmt:message key="home.creature.lastUpdated"/> ${creature.lastUpdated}
                    </small>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>