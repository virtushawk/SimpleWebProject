<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="custom" uri="/WEB-INF/tld/custom.tld"%>
<%@taglib prefix="e" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property.text"/>
<c:set var="totalCount" scope="session" value="${requestScope.creatures.size()}"/>
<c:set var="perPage" scope="session" value="${10}"/>
<c:set var="pageStart" value="${param.start}"/>
<c:if test="${empty pageStart or pageStart < 0}">
    <c:set var="pageStart" value="0"/>
</c:if>
<c:if test="${totalCount < pageStart}">
    <c:set var="pageStart" value="${pageStart - perPage}"/>
</c:if>
<c:set var="creatures" value="${requestScope.creatures}"/>
<c:if test="${not empty param.filter}">
    <c:choose>
        <c:when test="${param.filter == 'name'}">
            <c:set var="creatures" value="${custom:sortByName(creatures)}"/>
        </c:when>
        <c:when test="${param.filter == 'date'}">
            <c:set var="creatures" value="${custom:sortByDate(creatures)}"/>
        </c:when>
        <c:when test="${param.filter == 'rating'}">
            <c:set var="creatures" value="${custom:sortByRating(creatures)}"/>
        </c:when>
    </c:choose>
</c:if>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <title>
        <fmt:message key="catalog.title"/>
    </title>
</head>
<body>
<jsp:include page="/pages/module/header.jsp"/>
<c:if test="${sessionScope.creatureCreated}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="catalog.creatureCreated"/>
        </div>
    </div>
    <c:remove var="creatureCreated" scope="session"/>
</c:if>
<c:if test="${sessionScope.creatureCreateError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="catalog.creatureCreateError"/>
        </div>
    </div>
    <c:remove var="creatureCreateError" scope="session"/>
</c:if>
<c:if test="${requestScope.errorMessageDB or sessionScope.errorMessageDB}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="home.errorMessageDB"/>
        </div>
    </div>
    <c:remove var="errorMessageDB" scope="session"/>
</c:if>
<c:if test="${sessionScope.user.role.name().equals('USER') || sessionScope.user.role.name().equals('ADMIN')}">
    <button type="button" class="btn btn-outline-primary mt-1 ms-2" data-bs-toggle="modal" data-bs-target="#createModal">
        <fmt:message key="catalog.button.create"/>
    </button>
    <div class="modal fade" id="createModal" tabindex="-1">
        <div class="modal-dialog modal-xl">
            <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=create_creature" method="post" enctype="multipart/form-data" novalidate>
                <div class="modal-header">
                    <h5 class="modal-title">
                        <fmt:message key="catalog.button.create"/>
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label" for="image">
                                <fmt:message key="createCreature.image.label"/>
                            </label>
                            <input type="file" name="image" class="form-control" id="image" accept="image/png,image/jpeg,image/jpg" required/>
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
                            <input type="text" name="name" class="form-control" id="name" required pattern="(^([ a-z,A-Z]){1,30}$)">
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
                            <textarea class="form-control" name="description" id="description" rows="10" required></textarea>
                            <div class="valid-feedback">
                                <fmt:message key="createCreature.valid"/>
                            </div>
                            <div class="invalid-feedback">
                                <fmt:message key="createCreature.description.invalid"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        <fmt:message key="catalog.creatureModal.button.close"/>
                    </button>
                    <button type="submit" class="btn btn-primary">
                        <fmt:message key="catalog.creatureModal.button.add"/>
                    </button>
                </div>
            </form>
        </div>
    </div>
</c:if>
<div class="container">
    <form class="d-flex justify-content-end g-1 me-5" action="${pageContext.request.contextPath}/controller?command=${param.command}">
        <input type="hidden" name="command" value="${param.command}"/>
        <c:if test="${not empty param.text}">
            <input type="hidden" name="text" value="<e:forHtml value="${param.text}"/>"/>
        </c:if>
        <select name="filter">
            <option value="name">
                <fmt:message key="catalog.filter.byName"/>
            </option>
            <option value="date" selected>
                <fmt:message key="catalog.filter.byDate"/>
            </option>
            <option value="rating">
                <fmt:message key="catalog.filter.byRating"/>
            </option>
        </select>
        <button type="submit" class="btn btn-primary">
            <fmt:message key="catalog.filter.button"/>
        </button>
    </form>
</div>
<div class="row row-cols-1 row-cols-md-5 g-4 mx-auto shadow p-3 mb-5 bg-white rounded mt-0" style="width: 75rem;">
    <c:forEach var="creature" items="${requestScope.creatures}" begin="${pageStart}" end="${pageStart + perPage - 1}">
        <div class="col">
            <div class="card h-100 border-0">
                <img src="${pageContext.request.contextPath}/uploadController?url=${creature.picture}" class="card-img-top" alt="..." style=" width: 100%;object-fit: cover;height: 15vw">
                <div class="card-body">
                    <a href="${pageContext.request.contextPath}/controller?command=creature&id=${creature.id}" class="text-decoration-none stretched-link">
                        <e:forHtml value="${creature.name}"/>
                    </a>
                    <p class="card-text text-truncate">
                        <e:forHtml value="${creature.description}"/>
                    </p>
                    <p class="card-text">
                            <fmt:message key="catalog.rating"/> <fmt:formatNumber type="number" maxFractionDigits="1" value="${creature.averageRating}"/>
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
<div class="container text-center">
    <a href="${pageContext.request.contextPath}/controller?command=${param.command}&start=${pageStart - perPage}<c:if test="${not empty param.text}">&text=<e:forHtml value="${param.text}"/></c:if><c:if test="${not empty param.filter}">&filter=${param.filter}</c:if>">
        <<
    </a>${pageStart + 1} - ${pageStart + perPage}
    <a href="${pageContext.request.contextPath}/controller?command=${param.command}&start=${pageStart + perPage}<c:if test="${not empty param.text}">&text=<e:forHtml value="${param.text}"/></c:if><c:if test="${not empty param.filter}">&filter=${param.filter}</c:if>">
        >>
    </a>
</div>
<jsp:include page="module/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/form-validation.js"></script>
</body>
</html>