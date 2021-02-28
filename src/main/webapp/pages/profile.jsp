<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="e" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property.text"/>
<c:set var="ctotalCount" scope="session" value="${requestScope.creatures.size()}"/>
<c:set var="rtotalCount" scope="session" value="${requestScope.reviews.size()}"/>
<c:set var="perPage" scope="session" value="${5}"/>
<c:set var="cpageStart" value="${param.cstart}"/>
<c:set var="rpageStart" value="${param.rstart}"/>
<c:if test="${empty cpageStart or cpageStart < 0}">
    <c:set var="cpageStart" value="0"/>
</c:if>
<c:if test="${empty rpageStart or rpageStart < 0}">
    <c:set var="rpageStart" value="0"/>
</c:if>
<c:if test="${ctotalCount < cpageStart}">
    <c:set var="cpageStart" value="${cpageStart - perPage}"/>
</c:if>
<c:if test="${rtotalCount < rpageStart}">
    <c:set var="rpageStart" value="${rpageStart - perPage}"/>
</c:if>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css"/>
    <title>
        ${sessionScope.user.username}
    </title>
</head>
<body>
<jsp:include page="/pages/module/header.jsp"/>
<c:if test="${sessionScope.avatarChanged}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="profile.avatarChanged"/>
        </div>
    </div>
    <c:remove var="avatarChanged" scope="session"/>
</c:if>
<c:if test="${sessionScope.avatarError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
    <c:remove var="avatarError" scope="session"/>
</c:if>
<c:if test="${sessionScope.nameChanged}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="profile.nameChanged"/>
        </div>
    </div>
    <c:remove var="nameChanged" scope="session"/>
</c:if>
<c:if test="${sessionScope.nameError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
    <c:remove var="nameError" scope="session"/>
</c:if>
<c:if test="${sessionScope.emailChanged}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="profile.emailChanged"/>
        </div>
    </div>
    <c:remove var="emailChanged" scope="session"/>
</c:if>
<c:if test="${sessionScope.emailError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
    <c:remove var="emailError" scope="session"/>
</c:if>
<c:if test="${sessionScope.passwordChanged}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="profile.passwordChanged"/>
        </div>
    </div>
    <c:remove var="passwordChanged" scope="session"/>
</c:if>
<c:if test="${sessionScope.passwordError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
    <c:remove var="passwordError" scope="session"/>
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
<c:if test="${sessionScope.correctionEdited}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="profile.correctionEdited"/>
        </div>
    </div>
    <c:remove var="correctionEdited" scope="session"/>
</c:if>
<c:if test="${sessionScope.correctionEditError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="profile.correctionEditError"/>
        </div>
    </div>
    <c:remove var="correctionEditError" scope="session"/>
</c:if>
<c:if test="${sessionScope.changeStatus}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="profile.statusChange"/>
        </div>
    </div>
    <c:remove var="changeStatus" scope="session"/>
</c:if>
<c:if test="${sessionScope.changeStatusError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="general.errorMessage"/>
        </div>
    </div>
    <c:remove var="changeStatusError" scope="session"/>
</c:if>
<c:if test="${sessionScope.errorMessageDB || requestScope.errorMessageDB}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="home.errorMessageDB"/>
        </div>
    </div>
    <c:remove var="errorMessageDB" scope="session"/>
</c:if>
<div class="container row">
    <c:if test="${sessionScope.user.accountId == requestScope.user.accountId}">
        <div class="d-flex align-items-start col-1">
            <div class="nav flex-column nav-pills me-3" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                <button class="btn btn-outline-primary active" id="v-pills-profile-tab" data-bs-toggle="pill" data-bs-target="#v-pills-profile" type="button" role="tab">
                    <fmt:message key="profile.tab.profile"/>
                </button>
                <button class="btn btn-outline-primary" id="v-pills-settings-tab" data-bs-toggle="pill" data-bs-target="#v-pills-settings" type="button" role="tab">
                    <fmt:message key="profile.tab.settings"/>
                </button>
                <button class="btn btn-outline-primary" id="v-pills-userCreatures-tab" data-bs-toggle="pill" data-bs-target="#v-pills-userCreatures" type="button" role="tab">
                    <fmt:message key="profile.tab.myCreatures"/>
                </button>
                <button class="btn btn-outline-primary" id="v-pills-userCorrections-tab" data-bs-toggle="pill" data-bs-target="#v-pills-userCorrections" type="button" role="tab">
                    <fmt:message key="profile.tab.myCorrections"/>
                </button>
            </div>
        </div>
    </c:if>
    <div class="tab-content col-11" id="v-pills-tabContent">
        <div class="tab-pane fade show active" id="v-pills-profile" role="tabpanel">
            <div class="container">
                <div class="main-body">
                    <div class="row gutters-sm">
                        <div class="col-md-4 mb-3">
                            <div class="card">
                                <div class="card-body">
                                    <div class="d-flex flex-column align-items-center text-center">
                                        <img src="${pageContext.request.contextPath}/uploadController?url=${requestScope.user.avatar}" alt="<fmt:message key="general.userImage.alt"/>" class="rounded-circle" width="150" height="150">
                                        <c:if test="${sessionScope.user.accountId == requestScope.user.accountId}">
                                            <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#avatarModal">
                                                <fmt:message key="profile.button.changeAvatar"/>
                                            </button>
                                        </c:if>
                                        <div class="modal fade" id="avatarModal" tabindex="-1">
                                            <div class="modal-dialog">
                                                <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=change_avatar" method="post" enctype="multipart/form-data" novalidate>
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">
                                                            <fmt:message key="profile.button.changeAvatar"/>
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
                                                            <fmt:message key="creature.editCreatureModal.button.close"/>
                                                        </button>
                                                        <button type="submit" class="btn btn-primary">
                                                            <fmt:message key="creature.editReviewModal.button.save"/>
                                                        </button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                        <div class="mt-3">
                                            <h4>
                                                <e:forHtml value="${requestScope.user.username}"/>
                                            </h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="card mb-3">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <h6 class="mb-0">
                                                <fmt:message key="profile.label.fullName"/>
                                            </h6>
                                        </div>
                                        <div class="col-sm-8 text-secondary">
                                            <e:forHtml value="${requestScope.user.name}"/>
                                        </div>
                                        <c:if test="${sessionScope.user.accountId == requestScope.user.accountId}">
                                            <div class="col-sm-1">
                                                <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#userNameModal">
                                                    <i class="bi bi-pencil"></i>
                                                </button>
                                            </div>
                                        </c:if>
                                        <div class="modal fade" id="userNameModal" tabindex="-1">
                                            <div class="modal-dialog">
                                                <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=edit_name" method="post" novalidate>
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">
                                                            <fmt:message key="profile.usernameModal.title"/>
                                                        </h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                                    </div>
                                                    <div class= "modal-body">
                                                        <input type="text" name="name" class="form-control" required value="<e:forHtml value="${requestScope.user.name}"/>" pattern="(^[ \-a-zA-Z]{1,30}$)"/>
                                                        <div class="valid-feedback">
                                                            <fmt:message key="profile.fullName.valid"/>
                                                        </div>
                                                        <div class="invalid-feedback">
                                                            <fmt:message key="profile.FullName.invalid"/>
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
                                    <hr>
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <h6 class="mb-0">
                                                <fmt:message key="profile.email.title"/>
                                            </h6>
                                        </div>
                                        <div class="col-sm-9 text-secondary">
                                            <e:forHtml value="${requestScope.user.email}"/>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <h6 class="mb-0">
                                                <fmt:message key="profile.status.title"/>
                                            </h6>
                                        </div>
                                        <div class="col-sm-8 text-secondary">
                                            <c:choose>
                                                <c:when test="${requestScope.user.userStatus eq 'BEGINNER'}">
                                                    <fmt:message key="profile.status.beginner"/>
                                                </c:when>
                                                <c:when test="${requestScope.user.userStatus eq 'ADVANCED'}">
                                                    <fmt:message key="profile.status.advanced"/>
                                                </c:when>
                                                <c:when test="${requestScope.user.userStatus eq 'REGULAR'}">
                                                    <fmt:message key="profile.status.regular"/>
                                                </c:when>
                                            </c:choose>
                                            (${requestScope.user.numberReviews})
                                        </div>
                                        <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                                            <div class="col-sm-1">
                                                <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#statusModal">
                                                    <i class="bi bi-pencil"></i>
                                                </button>
                                            </div>
                                        </c:if>
                                        <div class="modal fade" id="statusModal" tabindex="-1">
                                            <div class="modal-dialog">
                                                <form class="modal-content" action="${pageContext.request.contextPath}/controller?command=change_status" method="post">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">
                                                            change status
                                                        </h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                                    </div>
                                                    <div class= "modal-body">
                                                        <input type="hidden" name="id" value="${requestScope.user.accountId}">
                                                        <select class="form-select" name="status" required>
                                                            <option value="BEGINNER"><fmt:message key="profile.status.beginner"/></option>
                                                            <option value="ADVANCED"><fmt:message key="profile.status.advanced"/></option>
                                                            <option value="REGULAR"><fmt:message key="profile.status.regular"/></option>
                                                        </select>
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
                                </div>
                            </div>
                            <div class="col-sm-12 mb-3 shadow-sm p-3 mb-5 bg-white rounded">
                                <ul class="nav nav-tabs justify-content-center" id="myTab" role="tablist">
                                    <li class="nav-item" role="presentation">
                                        <a class="nav-link active" id="reviews-tab" data-bs-toggle="tab" href="#reviews" role="tab">
                                            <fmt:message key="adminPanel.tab.reviews"/>
                                        </a>
                                    </li>
                                    <li class="nav-item" role="presentation">
                                        <a class="nav-link" id="creatures-tab" data-bs-toggle="tab" href="#creatures" role="tab">
                                            <fmt:message key="adminPanel.tab.creatures"/>
                                        </a>
                                    </li>
                                </ul>
                                <div class="tab-content" id="myTabContent">
                                    <div class="tab-pane fade show active" id="reviews" role="tabpanel">
                                        <c:forEach var="review" items="${requestScope.reviews}" begin="${rpageStart}" end="${rpageStart + perPage - 1}">
                                            <div class="row g-0">
                                                <div class="col-md-4">
                                                    <img src="${pageContext.request.contextPath}/uploadController?url=${requestScope.user.avatar}" alt="<fmt:message key="general.userImage.alt"/>" class="rounded-circle" height="150" width="150">
                                                </div>
                                                <div class="col-md-8">
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
                                            </div>
                                            <hr>
                                        </c:forEach>
                                        <div class="container text-center">
                                            <a href="${pageContext.request.contextPath}/controller?command=profile&id=${param.id}&rstart=${rpageStart - perPage}">
                                                <<
                                            </a>${rpageStart + 1} - ${rpageStart + perPage}
                                            <a href="${pageContext.request.contextPath}/controller?command=profile&id=${param.id}&rstart=${rpageStart + perPage}">
                                                >>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="creatures" role="tabpanel">
                                        <c:forEach var="creature" items="${requestScope.creatures}" begin="${cpageStart}" end="${cpageStart + perPage - 1}">
                                            <div class="row g-0">
                                                <div class="col-md-4">
                                                    <img src="${pageContext.request.contextPath}/uploadController?url=${creature.picture}" alt="<fmt:message key="home.image.alt"/> " height="150" width="150">
                                                </div>
                                                <div class="col-md-8">
                                                    <div class="card-body">
                                                        <a href="${pageContext.request.contextPath}/controller?command=creature&id=${creature.id}" class="text-decoration-none">
                                                            <e:forHtml value="${creature.name}"/>
                                                        </a>
                                                        <p class="card-text text-truncate">
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
                                            <hr>
                                        </c:forEach>
                                        <div class="container text-center">
                                            <a href="${pageContext.request.contextPath}/controller?command=profile&id=${param.id}&cstart=${cpageStart - perPage}">
                                                <<
                                            </a>${cpageStart + 1} - ${cpageStart + perPage}
                                            <a href="${pageContext.request.contextPath}/controller?command=profile&id=${param.id}&cstart=${cpageStart + perPage}">
                                                >>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="tab-pane fade" id="v-pills-settings" role="tabpanel">
            <div class="container shadow-sm p-3 mb-5 bg-white rounded ms-5">
                <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#emailModal">
                    <fmt:message key="profile.settings.button.changeEmail"/>
                </button>
                <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#passwordModal">
                    <fmt:message key="profile.settings.button.changePassword"/>
                </button>
                <div class="modal fade" id="emailModal" tabindex="-1">
                    <div class="modal-dialog">
                        <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=edit_email" method="post" novalidate>
                            <div class="modal-header">
                                <h5 class="modal-title">
                                    <fmt:message key="profile.settings.button.changeEmail"/>
                                </h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <div class="modal-body">
                                <input type="email" name="email" class="form-control" required value="<e:forHtml value="${requestScope.user.email}"/>"/>
                                <div class="valid-feedback">
                                    <fmt:message key="createCreature.valid"/>
                                </div>
                                <div class="invalid-feedback">
                                    <fmt:message key="profile.changeEmail.invalid"/>
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
                <div class="modal fade" id="passwordModal" tabindex="-1">
                    <div class="modal-dialog">
                        <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=change_password" method="post" novalidate>
                            <div class="modal-header">
                                <h5 class="modal-title">
                                    <fmt:message key="profile.settings.button.changePassword"/>
                                </h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <div class="modal-body">
                                <label for="password">
                                    <fmt:message key="profile.changePassword.password"/>
                                </label>
                                <input type="password" class="form-control" id="password" required pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$"/>
                                <div class="valid-feedback">
                                    <fmt:message key="createCreature.valid"/>
                                </div>
                                <div class="invalid-feedback">
                                    <fmt:message key="profile.changePassword.invalid"/>
                                </div>
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
        </div>
        <div class="tab-pane fade" id="v-pills-userCreatures" role="tabpanel">
            <div class="container">
                <div class="row row-cols-1 row-cols-md-5 g-4 mx-auto shadow p-3 mb-5 bg-white rounded mt-0" style="width: 75rem;">
                    <c:forEach var="creature" items="${requestScope.uncheckedCreatures}">
                        <div class="col">
                            <div class="card h-100 border-0">
                                <img src="${pageContext.request.contextPath}/uploadController?url=${creature.picture}" class="card-img-top" alt="<fmt:message key="home.image.alt"/>" style=" width: 100%;object-fit: cover;height: 15vw">
                                <div class="card-body">
                                    <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#creatureModal${creature.id}">
                                        <e:forHtml value="${creature.name}"/>
                                    </button>
                                    <div class="modal fade" id="creatureModal${creature.id}" tabindex="-1">
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
                                    <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#imageModal${creature.id}">
                                        <fmt:message key="adminPanel.creatureTab.button.changeImage"/>
                                    </button>
                                    <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#editModal${creature.id}">
                                        <fmt:message key="adminPanel.creatureTab.button.edit"/>
                                    </button>
                                    <a class="btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=delete_unchecked_creature&id=${creature.id}">
                                        <fmt:message key="adminPanel.correctionTab.delete"/>
                                    </a>
                                </div>
                                <div class="modal fade" id="imageModal${creature.id}" tabindex="-1">
                                    <div class="modal-dialog">
                                        <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=change_unchecked_image&id=${creature.id}" method="post" enctype="multipart/form-data" novalidate>
                                            <div class="modal-header">
                                                <h5 class="modal-title">
                                                    <fmt:message key="adminPanel.creatureTab.button.changeImage"/>
                                                </h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                            </div>
                                            <div class="modal-body">
                                                <input type="file" name="image" class="form-control" accept="image/png,image/jpeg,image/jpg" required/>
                                                <div class="valid-feedback">
                                                    <fmt:message key="createCreature.valid"/>
                                                </div>
                                                <div class="invalid-feedback">
                                                    <fmt:message key="createCreature.image.invalid"/>
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
                                <div class="modal fade" id="editModal${creature.id}" tabindex="-1">
                                    <div class="modal-dialog modal-xl">
                                        <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=edit_unchecked_creature&id=${creature.id}" method="post" novalidate>
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
                                                <input type="text" name="name" class="form-control" id="name" value="<e:forHtml value="${creature.name}"/>" required pattern="(^([ a-z,A-Z]){1,30}$)">
                                                <div class="valid-feedback">
                                                    <fmt:message key="createCreature.valid"/>
                                                </div>
                                                <div class="invalid-feedback">
                                                    <fmt:message key="createCreature.name.invalid"/>
                                                </div>
                                                <label for="description" class="form-label">
                                                    <fmt:message key="createCreature.description.label"/>
                                                </label>
                                                <textarea class="form-control" name="description" id="description" rows="3" required><e:forHtml value="${creature.description}"/></textarea>
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
                                <div class="card-footer border-0">
                                    <small class="text-muted">
                                        <fmt:message key="home.creature.lastUpdated"/> ${creature.lastUpdated}
                                    </small>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="tab-pane fade" id="v-pills-userCorrections" role="tabpanel">
            <div class="container">
                <div class="row row-cols-1 row-cols-md-5 g-4 mx-auto shadow p-3 mb-5 bg-white rounded mt-0" style="width: 75rem;">
                    <c:forEach var="correction" items="${requestScope.corrections}">
                        <div class="col">
                            <div class="card h-100 border-0">
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
                                    <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#editCorrectionModal${correction.correctionId}">
                                        <fmt:message key="adminPanel.creatureTab.button.edit"/>
                                    </button>
                                    <a class="btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=delete_correction&id=${correction.correctionId}">
                                        <fmt:message key="adminPanel.correctionTab.delete"/>
                                    </a>
                                </div>
                                <div class="modal fade" id="editCorrectionModal${correction.correctionId}" tabindex="-1">
                                    <div class="modal-dialog modal-xl">
                                        <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=edit_correction&id=${correction.correctionId}" method="post" novalidate>
                                            <div class="modal-header">
                                                <h5 class="modal-title">
                                                    <fmt:message key="adminPanel.correctionModal.correction"/>
                                                </h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                            </div>
                                            <div class="modal-body">
                                                <label for="name" class="form-label">
                                                    <fmt:message key="createCreature.name.label"/>
                                                </label>
                                                <input type="text" name="name" class="form-control" value="<e:forHtml value="${correction.name}"/>" required pattern="(^([ a-z,A-Z]){1,30}$)">
                                                <div class="valid-feedback">
                                                    <fmt:message key="createCreature.valid"/>
                                                </div>
                                                <div class="invalid-feedback">
                                                    <fmt:message key="createCreature.name.invalid"/>
                                                </div>
                                                <label for="description" class="form-label">
                                                    <fmt:message key="createCreature.description.label"/>
                                                </label>
                                                <textarea class="form-control" name="description" rows="10" required><e:forHtml value="${correction.text}"/></textarea>
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
                                <div class="card-footer border-0">
                                    <small class="text-muted">
                                            ${correction.date}
                                    </small>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/form-validation.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
<script>
    $(document).ready(function(){
        $('a[data-bs-toggle="tab"]').on('show.bs.tab', function(e) {
            localStorage.setItem('activeTab', $(e.target).attr('href'));
        });
        var activeTab = localStorage.getItem('activeTab');
        if(activeTab){
            $('#myTab a[href="' + activeTab + '"]').tab('show');
        }
    });
</script>
<script>
    var password = document.getElementById("password")
        , confirm_password = document.getElementById("confirm_password");
    function validatePassword(){
        if(password.value !== confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
        } else {
            confirm_password.setCustomValidity('');
        }
    }
    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;
</script>
</body>
</html>
