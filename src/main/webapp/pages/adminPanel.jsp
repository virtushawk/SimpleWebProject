<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="custom" uri="/WEB-INF/tld/custom.tld"%>
<%@taglib prefix="e" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property.text"/>
<c:set var="ctotalCount" scope="session" value="${requestScope.creatures.size()}"/>
<c:set var="rtotalCount" scope="session" value="${requestScope.reviews.size()}"/>
<c:set var="utotalCount" scope="session" value="${requestScope.users.size()}"/>
<c:set var="uctotalCount" scope="session" value="${requestScope.uncheckedCreatures.size()}"/>
<c:set var="crtotalCount" scope="session" value="${requestScope.corrections.size()}"/>
<c:set var="perPage" scope="session" value="${10}"/>
<c:set var="cpageStart" value="${param.cstart}"/>
<c:set var="rpageStart" value="${param.rstart}"/>
<c:set var="upageStart" value="${param.ustart}"/>
<c:set var="ucpageStart" value="${param.ucstart}"/>
<c:set var="crpageStart" value="${param.ucstart}"/>
<c:if test="${empty cpageStart or cpageStart < 0}">
    <c:set var="cpageStart" value="0"/>
</c:if>
<c:if test="${empty rpageStart or rpageStart < 0}">
    <c:set var="rpageStart" value="0"/>
</c:if>
<c:if test="${empty upageStart or upageStart < 0}">
    <c:set var="upageStart" value="0"/>
</c:if>
<c:if test="${empty ucpageStart or ucpageStart < 0}">
    <c:set var="ucpageStart" value="0"/>
</c:if>
<c:if test="${empty crpageStart or crpageStart < 0}">
    <c:set var="crpageStart" value="0"/>
</c:if>
<c:if test="${ctotalCount < cpageStart}">
    <c:set var="cpageStart" value="${cpageStart - perPage}"/>
</c:if>
<c:if test="${rtotalCount < rpageStart}">
    <c:set var="rpageStart" value="${rpageStart - perPage}"/>
</c:if>
<c:if test="${utotalCount < upageStart}">
    <c:set var="upageStart" value="${upageStart - perPage}"/>
</c:if>
<c:if test="${uctotalCount < ucpageStart}">
    <c:set var="ucpageStart" value="${ucpageStart - perPage}"/>
</c:if>
<c:if test="${crtotalCount < crpageStart}">
    <c:set var="crpageStart" value="${crpageStart - perPage}"/>
</c:if>
<c:set var="users" value="${requestScope.users}"/>
<c:set var="reviews" value="${requestScope.reviews}"/>
<c:set var="creatures" value="${requestScope.creatures}"/>
<c:set var="usersCreatures" value="${requestScope.uncheckedCreatures}"/>
<c:set var="corrections" value="${requestScope.corrections}"/>
<c:if test="${not empty param.filter}">
    <c:choose>
        <c:when test="${param.filter == 'username'}">
            <c:set var="users" value="${custom:sortByUsername(users)}"/>
        </c:when>
        <c:when test="${param.filter == 'role'}">
            <c:set var="users" value="${custom:sortByRole(users)}"/>
        </c:when>
        <c:when test="${param.filter == 'user'}">
            <c:set var="reviews" value="${custom:sortByUser(reviews)}"/>
        </c:when>
        <c:when test="${param.filter == 'rate'}">
            <c:set var="reviews" value="${custom:sortByRate(reviews)}"/>
        </c:when>
        <c:when test="${param.filter == 'name'}">
            <c:set var="creatures" value="${custom:sortByName(creatures)}"/>
        </c:when>
        <c:when test="${param.filter == 'date'}">
            <c:set var="creatures" value="${custom:sortByDate(creatures)}"/>
        </c:when>
        <c:when test="${param.filter == 'rating'}">
            <c:set var="creatures" value="${custom:sortByRating(creatures)}"/>
        </c:when>
        <c:when test="${param.filter == 'usersName'}">
            <c:set var="creatures" value="${custom:sortByName(usersCreatures)}"/>
        </c:when>
        <c:when test="${param.filter == 'usersDate'}">
            <c:set var="creatures" value="${custom:sortByDate(usersCreatures)}"/>
        </c:when>
        <c:when test="${param.filter eq 'correctionName'}">
            <c:set var="corrections" value="${custom:sortByCorrectionName(corrections)}"/>
        </c:when>
        <c:when test="${param.filter eq 'correctionDate'}">
            <c:set var="corrections" value="${custom:sortByCorrectionDate(corrections)}"/>
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
        <fmt:message key="header.adminPanel"/>
    </title>
</head>
<body>
<jsp:include page="/pages/module/header.jsp"/>
<c:if test="${sessionScope.errorMessageDB || requestScope.errorMessageDB}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="home.errorMessageDB"/>
        </div>
    </div>
    <c:remove var="errorMessageDB" scope="session"/>
</c:if>
<c:if test="${requestScope.correctionApproved}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="adminPanel.correctionApproved"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.correctionApproveError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.correctionDeleted}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="profile.correctionDeleted"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.correctionDeleteError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.creatureApproved}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="adminPanel.creatureApproved"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.creatureApproveError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.creatureDeleted}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="profile.creatureDeleted"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.creatureError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.accountUnblocked}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="adminPanel.accountUnblocked"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.accountUnblockError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.accountBlocked}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="adminPanel.accountBlocked"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.accountBlockError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.accountMadeAdmin}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="adminPanel.accountMadeAdmin"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.accountMadeAdminError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
</c:if>
<c:if test="${sessionScope.reviewChanged}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="adminPanel.reviewChanged"/>
        </div>
    </div>
    <c:remove var="reviewChanged" scope="session"/>
</c:if>
<c:if test="${sessionScope.reviewChangeError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="adminPanel.reviewChangeError"/>
        </div>
    </div>
    <c:remove var="reviewChangeError" scope="session"/>
</c:if>
<c:if test="${sessionScope.imageChanged}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="profile.imageChanged"/>
        </div>
    </div>
    <c:remove var="imageChanged" scope="session"/>
</c:if>
<c:if test="${sessionScope.imageError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
    <c:remove var="imageError" scope="session"/>
</c:if>
<c:if test="${sessionScope.creatureEdited}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="profile.creatureEdited"/>
        </div>
    </div>
    <c:remove var="creatureEdited" scope="session"/>
</c:if>
<c:if test="${sessionScope.creatureEditError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="profile.creatureEditError"/>
        </div>
    </div>
    <c:remove var="creatureEditError" scope="session"/>
</c:if>
<c:if test="${requestScope.accountRemoveAdmin}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="adminPanel.removeAdmin"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.accountMadeAdminError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.reviewDeleted}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="adminPanel.reviewDeleted"/>
        </div>
    </div>
</c:if>
<c:if test="${requestScope.reviewDeletedError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
</c:if>
<div class="container">
    <div class="col-sm-12 mb-3">
        <ul class="nav nav-tabs justify-content-center" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
                <a class="nav-link active" id="users-tab" data-bs-toggle="tab" href="#users" role="tab">
                    <fmt:message key="adminPanel.tab.users"/>
                </a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="reviews-tab" data-bs-toggle="tab" href="#reviews" role="tab">
                    <fmt:message key="adminPanel.tab.reviews"/>
                </a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="creatures-tab" data-bs-toggle="tab" href="#creatures" role="tab">
                    <fmt:message key="adminPanel.tab.creatures"/>
                </a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="userCreatures-tab" data-bs-toggle="tab" href="#userCreatures" role="tab">
                    <fmt:message key="adminPanel.tab.uncheckedCreatures"/>
                </a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="userCorrections-tab" data-bs-toggle="tab" href="#userCorrections" role="tab">
                    <fmt:message key="adminPanel.tab.corrections"/>
                </a>
            </li>
        </ul>
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade" id="userCorrections" role="tabpanel">
                <div class="container">
                    <form class="d-flex justify-content-end g-1 me-5" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="admin_panel"/>
                        <select name="filter">
                            <option value="correctionName">
                                <fmt:message key="catalog.filter.byName"/>
                            </option>
                            <option value="correctionDate" selected>
                                <fmt:message key="catalog.filter.byDate"/>
                            </option>
                        </select>
                        <button type="submit" class="btn btn-primary">
                            <fmt:message key="catalog.filter.button"/>
                        </button>
                    </form>
                </div>
                <ul class="list-group">
                    <c:forEach var="correction" items="${corrections}" begin="${crpageStart}" end="${crpageStart + perPage - 1}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <div class="card mb-3">
                                <div class="row g-4" style="width: 500px">
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#correctionModal${correction.correctionId}">
                                                    <e:forHtml value="${correction.name}"/>
                                            </button>
                                            <div class="modal fade" id="correctionModal${correction.correctionId}" tabindex="-1">
                                                <div class="modal-dialog modal-xl">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title">
                                                                <fmt:message key="adminPanel.correctionModal.correction"/>
                                                            </h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="card mb-3 mx-auto shadow p-3 mb-5 bg-white rounded">
                                                                <div class="row g-0">
                                                                    <div class="col-md-8">
                                                                        <div class="card-body">
                                                                            <h5 class="card-title">
                                                                                <e:forHtml value="${correction.name}"/>
                                                                            </h5>
                                                                            <p class="card-text">
                                                                                <e:forHtml value="${correction.text}"/>
                                                                            </p>
                                                                            <p class="card-text">
                                                                                <small class="text-muted">
                                                                                    ${correction.date}
                                                                                </small>
                                                                            </p>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                                <fmt:message key="creature.editCreatureModal.button.close"/>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <p class="card-text text-truncate">
                                                <e:forHtml value="${correction.text}"/>
                                            </p>
                                            <p class="card-text">
                                                <small class="text-muted">
                                                    ${correction.date}
                                                </small>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-end">
                                <a class="btn btn-outline-success mt-1" href="${pageContext.request.contextPath}/controller?command=approve_correction&id=${correction.correctionId}">
                                    <fmt:message key="adminPanel.correctionTab.approve"/>
                                </a>
                                <a class="btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=disapprove_correction&id=${correction.correctionId}">
                                    <fmt:message key="adminPanel.correctionTab.delete"/>
                                </a>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
                <div class="container text-center">
                    <a href="${pageContext.request.contextPath}/controller?command=${param.command}&crstart=${crpageStart - perPage}<c:if test="${not empty param.text}">&text=<e:forHtml value="${param.text}"/></c:if><c:if test="${not empty param.filter}">&filter=${param.filter}</c:if>">
                        <<
                    </a>${crpageStart + 1} - ${crpageStart + perPage}
                    <a href="${pageContext.request.contextPath}/controller?command=${param.command}&crstart=${crpageStart + perPage}<c:if test="${not empty param.text}">&text=<e:forHtml value="${param.text}"/></c:if><c:if test="${not empty param.filter}">&filter=${param.filter}</c:if>">
                        >>
                    </a>
                </div>
            </div>
            <div class="tab-pane fade" id="userCreatures" role="tabpanel">
                <div class="container">
                    <form class="d-flex justify-content-end g-1 me-5" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="admin_panel"/>
                        <select name="filter">
                            <option value="usersName">
                                <fmt:message key="catalog.filter.byName"/>
                            </option>
                            <option value="usersDate" selected>
                                <fmt:message key="catalog.filter.byDate"/>
                            </option>
                        </select>
                        <button type="submit" class="btn btn-primary">
                            <fmt:message key="catalog.filter.button"/>
                        </button>
                    </form>
                </div>
                <ul class="list-group">
                    <c:forEach var="creature" items="${usersCreatures}" begin="${ucpageStart}" end="${ucpageStart + perPage - 1}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <div class="card mb-3">
                                <div class="row g-4" style="width: 500px">
                                    <div class="col-md-4">
                                        <img src="${pageContext.request.contextPath}/uploadController?url=${creature.picture}" alt="<fmt:message key="home.image.alt"/>" height="150" width="150">
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#creatureModal${creature.creatureId}">
                                                <e:forHtml value="${creature.name}"/>
                                            </button>
                                            <div class="modal fade" id="creatureModal${creature.creatureId}" tabindex="-1">
                                                <div class="modal-dialog modal-xl">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title">
                                                                <fmt:message key="adminPanel.creatureModal.creature"/>
                                                            </h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="card mb-3 mx-auto shadow p-3 mb-5 bg-white rounded">
                                                                <div class="row g-0">
                                                                    <div class="col-md-4">
                                                                        <img src="${pageContext.request.contextPath}/uploadController?url=${creature.picture}" alt="<fmt:message key="home.image.alt"/>" style=" width: 100%;object-fit: cover;height: 15vw">
                                                                    </div>
                                                                    <div class="col-md-8">
                                                                        <div class="card-body">
                                                                            <h5 class="card-title">
                                                                                <e:forHtml value="${creature.name}"/>
                                                                            </h5>
                                                                            <p class="card-text">
                                                                                <e:forHtml value="${creature.description}"/>
                                                                            </p>
                                                                            <p class="card-text">
                                                                                <small class="text-muted">
                                                                                    <fmt:message key="home.creature.lastUpdated"/> ${creature.lastUpdated}
                                                                                </small>
                                                                            </p>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                                <fmt:message key="creature.editCreatureModal.button.close"/>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <p class="card-text text-truncate">
                                                <e:forHtml value="${creature.description}"/>
                                            </p>
                                            <p class="card-text">
                                                <small class="text-muted">
                                                        ${creature.lastUpdated}
                                                </small>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-end">
                                <a class="btn btn-outline-success mt-1" href="${pageContext.request.contextPath}/controller?command=approve_creature&id=${creature.creatureId}">
                                    <fmt:message key="adminPanel.correctionTab.approve"/>
                                </a>
                                <a class="btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=delete_creature&id=${creature.creatureId}">
                                    <fmt:message key="adminPanel.correctionTab.delete"/>
                                </a>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
                <div class="container text-center">
                    <a href="${pageContext.request.contextPath}/controller?command=${param.command}&ucstart=${ucpageStart - perPage}<c:if test="${not empty param.text}">&text=<e:forHtml value="${param.text}"/></c:if><c:if test="${not empty param.filter}">&filter=${param.filter}</c:if>">
                        <<
                    </a>${ucpageStart + 1} - ${ucpageStart + perPage}
                    <a href="${pageContext.request.contextPath}/controller?command=${param.command}&ucstart=${ucpageStart + perPage}<c:if test="${not empty param.text}">&text=<e:forHtml value="${param.text}"/></c:if><c:if test="${not empty param.filter}">&filter=${param.filter}</c:if>">
                        >>
                    </a>
                </div>
            </div>
            <div class="tab-pane fade show active" id="users" role="tabpanel">
                <div class="container">
                    <form class="d-flex justify-content-end g-1 me-5" action="${pageContext.request.contextPath}/controller?command=admin_panel">
                        <input type="hidden" name="command" value="admin_panel"/>
                        <select name="filter">
                            <option value="username">
                                <fmt:message key="catalog.filter.byName"/>
                            </option>
                            <option value="role">
                                <fmt:message key="catalog.filter.byRole"/>
                            </option>
                        </select>
                        <button type="submit" class="btn btn-primary">
                            <fmt:message key="catalog.filter.button"/>
                        </button>
                    </form>
                </div>
                <ul class="list-group">
                    <c:forEach var="user" items="${users}" begin="${upageStart}" end="${upageStart + perPage - 1}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <div class="d-flex justify-content-start align-items-center">
                                <img src="${pageContext.request.contextPath}/uploadController?url=${user.avatar}" class="rounded-circle" height="150" width="150">
                            </div>
                            <div class="d-flex">
                                <a class="d-flex ms-2" href="${pageContext.request.contextPath}/controller?command=profile&id=${user.accountId}">
                                    <e:forHtml value="${user.username}"/>
                                </a>
                            </div>
                            <div class="d-flex">
                                <h6 class="d-flex ms-2">
                                    <e:forHtml value="${user.email}"/>
                                </h6>
                            </div>
                            <div class="d-flex">
                                <h6 class="d-flex ms-2">
                                        ${user.role}
                                </h6>
                            </div>
                            <div class="d-flex justify-content-end">
                                <c:choose>
                                    <c:when test="${user.role.name().equals('BLOCKED')}">
                                        <a class="d-flex ms-2 btn btn-outline-primary mt-1" href="${pageContext.request.contextPath}/controller?command=unblock_user&id=${user.accountId}">
                                            <fmt:message key="adminPanel.userTab.unblock"/>
                                        </a>
                                    </c:when>
                                    <c:when test="${user.role.name().equals('ADMIN')}">
                                        <a class="d-flex ms-2 btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=block_user&id=${user.accountId}">
                                            <fmt:message key="adminPanel.userTab.block"/>
                                        </a>
                                        <a class="d-flex ms-2 btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=remove_admin&id=${user.accountId}">
                                            <fmt:message key="adminPanel.userTab.removeAdmin"/>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="d-flex ms-2 btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=block_user&id=${user.accountId}">
                                            <fmt:message key="adminPanel.userTab.block"/>
                                        </a>
                                        <a class="d-flex ms-2 btn btn-outline-primary mt-1" href="${pageContext.request.contextPath}/controller?command=make_admin&id=${user.accountId}">
                                            <fmt:message key="adminPanel.userTab.makeAdmin"/>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
                <div class="container text-center">
                    <a href="${pageContext.request.contextPath}/controller?command=${param.command}&ustart=${upageStart - perPage}<c:if test="${not empty param.text}">&text=<e:forHtml value="${param.text}"/></c:if><c:if test="${not empty param.filter}">&filter=${param.filter}</c:if>">
                        <<
                    </a>${upageStart + 1} - ${upageStart + perPage}
                    <a href="${pageContext.request.contextPath}/controller?command=${param.command}&ustart=${upageStart + perPage}<c:if test="${not empty param.text}">&text=<e:forHtml value="${param.text}"/></c:if><c:if test="${not empty param.filter}">&filter=${param.filter}</c:if>">
                        >>
                    </a>
                </div>
            </div>
            <div class="tab-pane fade" id="reviews" role="tabpanel">
                <div class="container">
                    <form class="d-flex justify-content-end g-1 me-5" action="${pageContext.request.contextPath}/controller?command=admin_panel">
                        <input type="hidden" name="command" value="admin_panel"/>
                        <select name="filter">
                            <option value="user">
                                <fmt:message key="catalog.filter.byUser"/>
                            </option>
                            <option value="rate">
                                <fmt:message key="catalog.filter.byRating"/>
                            </option>
                        </select>
                        <button type="submit" class="btn btn-primary">
                            <fmt:message key="catalog.filter.button"/>
                        </button>
                    </form>
                </div>
                <ul class="list-group">
                    <c:forEach var="review" items="${reviews}" begin="${rpageStart}" end="${rpageStart + perPage - 1}">
                        <li class="list-group-item">
                            <div class="row g-0">
                                <div class="col-md-2">
                                    <img src="${pageContext.request.contextPath}/uploadController?url=${review.avatar}" alt="<fmt:message key="general.userImage.alt"/>" class="rounded-circle" height="150" width="150">
                                </div>
                                <div class="col-md-2 my-auto">
                                   <p class="card-text">
                                       <a href="${pageContext.request.contextPath}/controller?command=profile&id=${review.accountId}" class="text-decoration-none">
                                           <e:forHtml value="${review.accountName}"/>
                                       </a>
                                   </p>
                                </div>
                                <div class="col-md-4">
                                    <div class="card-body">
                                        <h5 class="card-title">
                                            <fmt:message key="profile.review.title"/> <a href="${pageContext.request.contextPath}/controller?command=creature&id=${review.creatureId}" class="text-decoration-none">
                                            <e:forHtml value="${review.creatureName}"/>
                                        </a>
                                        </h5>
                                        <p class="card-text">
                                            <e:forHtml value="${review.text}"/>
                                        </p>
                                        <p class="card-text">
                                            <small class="text-muted">
                                                    ${review.date}
                                            </small>
                                        </p>
                                    </div>
                                </div>
                                <div class="col-md-2 my-auto">
                                    <h4>
                                        <fmt:message key="catalog.rating"/>
                                    </h4>
                                    <h4>
                                            ${review.rating}
                                    </h4>
                                </div>
                                <div class="col-md-1 my-auto">
                                    <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#editReviewModal${review.reviewId}">
                                        <fmt:message key="creature.button.edit"/>
                                    </button>
                                </div>
                                <div class="col-md-1 my-auto">
                                    <a class="btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=delete_review&id=${review.reviewId}">
                                        <fmt:message key="creature.review.button.delete"/>
                                    </a>
                                </div>
                                <div class="modal fade" id="editReviewModal${review.reviewId}" tabindex="-1">
                                    <div class="modal-dialog modal-lg">
                                        <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=edit_review" method="post" novalidate>
                                            <input type="hidden" name="id" value="${review.reviewId}">
                                            <input type="hidden" name="creature" value="${review.creatureId}">
                                            <div class="modal-header">
                                                <h5 class="modal-title">
                                                    <fmt:message key="creature.editReviewModal.title"/>
                                                </h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="row g-1">
                                                    <div class="col-md-3">
                                                        <img src="${pageContext.request.contextPath}/uploadController?url=${review.avatar}" alt="<fmt:message key="general.userImage.alt"/>" class="img-thumbnail" style="height: 100px; width: 100px">
                                                        <p class="fs-3">
                                                            <e:forHtml value="${review.accountName}"/>
                                                        </p>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <label for="review" class="form-label">
                                                            <fmt:message key="creature.review.title"/>
                                                        </label>
                                                        <textarea class="form-control" name="review" id="review" rows="3" required>${review.text}</textarea>
                                                        <div class="valid-feedback">
                                                            <fmt:message key="createCreature.valid"/>
                                                        </div>
                                                        <div class="invalid-feedback">
                                                            <fmt:message key="createCreature.description.invalid"/>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-2 ms-2">
                                                        <p class="fs-4">
                                                            <fmt:message key="catalog.rating"/>
                                                        </p>
                                                        <select class="form-select" name="rating" required>
                                                            <option value="1">1</option>
                                                            <option value="2">2</option>
                                                            <option value="3">3</option>
                                                            <option value="4">4</option>
                                                            <option value="5">5</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                    <fmt:message key="creature.editCreatureModal.button.close"/>
                                                </button>
                                                <button type="submit" class="btn btn-primary">
                                                    <fmt:message key="creature.editReviewModal.button.save"/>
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
                <div class="container text-center">
                    <a href="${pageContext.request.contextPath}/controller?command=${param.command}&rstart=${rpageStart - perPage}<c:if test="${not empty param.text}">&text=<e:forHtml value="${param.text}"/></c:if><c:if test="${not empty param.filter}">&filter=${param.filter}</c:if>">
                        <<
                    </a>${rpageStart + 1} - ${rpageStart + perPage}
                    <a href="${pageContext.request.contextPath}/controller?command=${param.command}&rstart=${rpageStart + perPage}<c:if test="${not empty param.text}">&text=<e:forHtml value="${param.text}"/></c:if><c:if test="${not empty param.filter}">&filter=${param.filter}</c:if>">
                        >>
                    </a>
                </div>
            </div>
            <div class="tab-pane fade " id="creatures" role="tabpanel">
                <div class="container">
                    <form class="d-flex justify-content-end g-1 me-5" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="admin_panel"/>
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
                <ul class="list-group">
                    <c:forEach var="creature" items="${creatures}" begin="${cpageStart}" end="${cpageStart + perPage - 1}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <div class="card mb-3">
                                <div class="row g-4" style="width: 500px">
                                    <div class="col-md-4">
                                        <img src="${pageContext.request.contextPath}/uploadController?url=${creature.picture}" alt="<fmt:message key="home.image.alt"/>" height="150" width="150">
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <a href="${pageContext.request.contextPath}/controller?command=creature&id=${creature.creatureId}" class="text-decoration-none stretched-link">
                                                <e:forHtml value=" ${creature.name}"/>
                                            </a>
                                            <p class="card-text text-truncate">
                                                <e:forHtml value="${creature.description}"/>
                                            </p>
                                            <p class="card-text">
                                                <small class="text-muted">
                                                    ${creature.lastUpdated}
                                                </small>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-end">
                                <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#imageModal${creature.creatureId}">
                                    <fmt:message key="adminPanel.creatureTab.button.changeImage"/>
                                </button>
                                <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#bodyModal${creature.creatureId}">
                                    <fmt:message key="adminPanel.creatureTab.button.edit"/>
                                </button>
                                <a class="btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=delete_creature&id=${creature.creatureId}">
                                    <fmt:message key="adminPanel.creatureTab.button.delete"/>
                                </a>
                            </div>
                            <div class="modal fade" id="imageModal${creature.creatureId}" tabindex="-1">
                                <div class="modal-dialog">
                                    <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=change_image&id=${creature.creatureId}" method="post" enctype="multipart/form-data" novalidate>
                                        <div class="modal-header">
                                            <h5 class="modal-title">
                                                <fmt:message key="adminPanel.creatureTab.imageModal.title"/>
                                            </h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                        </div>
                                        <div class="modal-body">
                                            <input type="file" name="image" class="form-control" id="image"  accept="image/png,image/jpeg,image/jpg" required/>
                                            <div class="valid-feedback">
                                                <fmt:message key="createCreature.valid"/>
                                            </div>
                                            <div class="invalid-feedback">
                                                <fmt:message key="createCreature.image.invalid"/>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                <fmt:message key="catalog.creatureModal.button.close"/>
                                            </button>
                                            <button type="submit" class="btn btn-primary">
                                                <fmt:message key="creature.editReviewModal.button.save"/>
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="modal fade" id="bodyModal${creature.creatureId}" tabindex="-1">
                                <div class="modal-dialog modal-xl">
                                    <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=edit_creature&id=${creature.creatureId}" method="post" novalidate>
                                        <div class="modal-header">
                                            <h5 class="modal-title">
                                                <fmt:message key="creature.editCreatureModal.title"/>
                                            </h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                        </div>
                                        <div class="modal-body">
                                            <label for="name" class="form-label">
                                                <fmt:message key="createCreature.name.label"/>
                                            </label>
                                            <input type="text" name="name" class="form-control" id="name" value="${creature.name}" required pattern="(^([ a-z,A-Z]){1,30}$)">
                                            <div class="valid-feedback">
                                                <fmt:message key="createCreature.valid"/>
                                            </div>
                                            <div class="invalid-feedback">
                                                <fmt:message key="creature.editCreatureModal.name.invalid"/>
                                            </div>
                                            <label for="description" class="form-label">
                                                <fmt:message key="createCreature.description.label"/>
                                            </label>
                                            <textarea class="form-control" name="description" id="description" rows="10" required>${creature.description}</textarea>
                                            <div class="valid-feedback">
                                                <fmt:message key="createCreature.valid"/>
                                            </div>
                                            <div class="invalid-feedback">
                                                    <fmt:message key="createCreature.description.invalid"/>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                <fmt:message key="creature.editCreatureModal.button.close"/>
                                            </button>
                                            <button type="submit" class="btn btn-primary">
                                                <fmt:message key="creature.editReviewModal.button.save"/>
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
                <div class="container text-center">
                    <a href="${pageContext.request.contextPath}/controller?command=${param.command}&cstart=${cpageStart - perPage}<c:if test="${not empty param.text}">&text=<e:forHtml value="${param.text}"/></c:if><c:if test="${not empty param.filter}">&filter=${param.filter}</c:if>">
                        <<
                    </a>${cpageStart + 1} - ${cpageStart + perPage}
                    <a href="${pageContext.request.contextPath}/controller?command=${param.command}&cstart=${cpageStart + perPage}<c:if test="${not empty param.text}">&text=<e:forHtml value="${param.text}"/></c:if><c:if test="${not empty param.filter}">&filter=${param.filter}</c:if>">
                        >>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="module/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/form-validation.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/activeTab.js"></script>
</body>
</html>
