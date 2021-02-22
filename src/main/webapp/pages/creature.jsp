<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="e" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project"%>
<%@taglib prefix="custom" uri="/WEB-INF/tld/custom.tld"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property.text"/>
<c:set var="userReview" value="${requestScope.userReview}"/>
<c:set var="creature" value="${requestScope.creature}"/>
<c:set var="user" value="${sessionScope.user}"/>
<c:set var="totalCount" scope="session" value="${requestScope.reviews.size()}"/>
<c:set var="perPage" scope="session" value="${5}"/>
<c:set var="pageStart" value="${param.start}"/>
<c:if test="${empty pageStart or pageStart < 0}">
    <c:set var="pageStart" value="0"/>
</c:if>
<c:if test="${totalCount < pageStart}">
    <c:set var="pageStart" value="${pageStart - perPage}"/>
</c:if>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <title>
        <e:forHtml value="${creature.name}"/>
    </title>
</head>
<body>
<jsp:include page="/pages/module/header.jsp"/>
<c:if test="${requestScope.errorMessageDB or sessionScope.errorMessageDB}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="home.errorMessageDB"/>
        </div>
    </div>
    <c:remove var="errorMessageDB" scope="session"/>
</c:if>
<c:if test="${sessionScope.correctionCreated}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="creature.correctionCreated"/>
        </div>
    </div>
    <c:remove var="correctionCreated" scope="session"/>
</c:if>
<c:if test="${sessionScope.correctionError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="creature.correctionError"/>
        </div>
    </div>
    <c:remove var="correctionError" scope="session"/>
</c:if>
<c:if test="${sessionScope.reviewCreated}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="creature.reviewCreated"/>
        </div>
    </div>
    <c:remove var="reviewCreated" scope="session"/>
</c:if>
<c:if test="${sessionScope.reviewError}">
    <div class="container">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="creature.reviewError"/>
        </div>
    </div>
    <c:remove var="reviewError" scope="session"/>
</c:if>
<c:if test="${requestScope.reviewDeleted}">
    <div class="container">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="creature.reviewDeleted"/>
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
                <custom:access accessRole="USER">
                    <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#editCreatureModal">
                        <fmt:message key="creature.button.edit"/>
                    </button>
                </custom:access>
                <div class="modal fade" id="editCreatureModal" tabindex="-1">
                    <div class="modal-dialog modal-xl">
                        <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=suggest_correction&id=${creature.id}" method="post" novalidate>
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
                                <input type="text" name="name" class="form-control" id="name" value="<e:forHtml value="${creature.name}"/>" required pattern="(^[ a-z,A-Z]{1,30}$)">
                                <div class="valid-feedback">
                                    <fmt:message key="createCreature.valid"/>
                                </div>
                                <div class="invalid-feedback">
                                    <fmt:message key="createCreature.name.invalid"/>
                                </div>
                                <label for="description" class="form-label">
                                    <fmt:message key="createCreature.description.label"/>
                                </label>
                                <textarea class="form-control" name="description" id="description" rows="10" required><e:forHtml value="${creature.description}"/></textarea>
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
                                    <fmt:message key="creature.editCreatureModal.button.save"/>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<c:choose>
    <c:when test="${(user.role.name().equals('USER') || user.role.name().equals('ADMIN')) && (empty requestScope.userReview)}">
        <div class="container-sm shadow p-3 mb-5 bg-white rounded w-50">
            <form class="row g-1  needs-validation" action="${pageContext.request.contextPath}/controller?command=create_review&id=${creature.id}" method="post" novalidate>
                <div class="col-md-3">
                    <img src="${pageContext.request.contextPath}/uploadController?url=${user.avatar}" alt="<fmt:message key="general.userImage.alt"/>"  class="img-thumbnail" style="height: 100px; width: 100px">
                    <p class="fs-3">
                        <e:forHtml value="${user.name}"/>
                    </p>
                </div>
                <div class="col-md-6">
                    <label for="review" class="form-label">
                        <fmt:message key="creature.review.title"/>
                    </label>
                    <textarea class="form-control" name="review" id="review" rows="3" required></textarea>
                    <div class="valid-feedback">
                        <fmt:message key="creature.review.valid"/>
                    </div>
                    <div class="invalid-feedback">
                        <fmt:message key="creature.review.invalid"/>
                    </div>
                </div>
                <div class="col-md-2 ms-2">
                    <p class="fs-4">
                        <fmt:message key="creature.review.score"/>
                    </p>
                    <select class="form-select" name="rating" required>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>
                    <div class="valid-feedback">
                        <fmt:message key="creature.score.valid"/>
                    </div>
                    <div class="invalid-feedback">
                        <fmt:message key="creature.score.invalid"/>
                    </div>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">
                        <fmt:message key="creature.review.button.publish"/>
                    </button>
                </div>
            </form>
        </div>
    </c:when>
    <c:when test="${(user.role.name().equals('USER') || user.role.name().equals('ADMIN')) && (not empty requestScope.userReview)}">
        <div class="container-sm shadow p-3 mb-5 bg-white rounded w-50">
            <div class="row g-1">
                <div class="col-md-3">
                    <img src="${pageContext.request.contextPath}/uploadController?url=${userReview.avatar}" alt="<fmt:message key="general.userImage.alt"/>"  class="img-thumbnail" style="height: 100px; width: 100px">
                    <p class="fs-3">
                        <e:forHtml value="${userReview.accountName}"/>
                    </p>
                </div>
                <div class="col-md-6">
                    <p class="text-break">
                        <e:forHtml value="${userReview.text}"/>
                    </p>
                </div>
                <div class="col-md-2 ms-2">
                    <p class="fs-4">
                        <fmt:message key="creature.review.score"/>
                    </p>
                    <p class="fs-2">
                            ${userReview.rating}
                    </p>
                    <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#reviewModal">
                        <fmt:message key="creature.button.edit"/>
                    </button>
                    <a class="btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=delete_review&id=${userReview.reviewId}&creature=${requestScope.creature.id}">
                        <fmt:message key="creature.review.button.delete"/>
                    </a>
                    <div class="modal fade" id="reviewModal" tabindex="-1">
                        <div class="modal-dialog modal-xl">
                            <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=edit_review" method="post" novalidate>
                                <input type="hidden" name="id" value="${userReview.reviewId}">
                                <input type="hidden" name="creature" value="${creature.id}">
                                <div class="modal-header">
                                    <h5 class="modal-title">
                                        <fmt:message key="creature.editReviewModal.title"/>
                                    </h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="row g-1">
                                        <div class="col-md-3">
                                            <img src="${pageContext.request.contextPath}/uploadController?url=${userReview.avatar}" alt="<fmt:message key="general.userImage.alt"/>"  class="img-thumbnail" style="height: 100px; width: 100px">
                                            <p class="fs-3">
                                                <e:forHtml value="${userReview.accountName}"/>
                                            </p>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="editReview" class="form-label">
                                                <fmt:message key="creature.review.title"/>
                                            </label>
                                            <textarea class="form-control" name="review" id="editReview" rows="3" required><e:forHtml value="${userReview.text}"/></textarea>
                                            <div class="valid-feedback">
                                                <fmt:message key="createCreature.valid"/>
                                            </div>
                                            <div class="invalid-feedback">
                                                <fmt:message key="createCreature.description.invalid"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2 ms-2">
                                            <p class="fs-4">
                                                <fmt:message key="creature.review.score"/>
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
            </div>
        </div>
    </c:when>
</c:choose>
<div class="container shadow p-3 mb-5 bg-white rounded w-50">
    <div class="list-group">
        <c:forEach var="review" items="${requestScope.reviews}" begin="${pageStart}" end="${pageStart + perPage - 1}">
            <div class="row g-1">
                <div class="col-md-3">
                    <img src="${pageContext.request.contextPath}/uploadController?url=${review.avatar}" alt="<fmt:message key="general.userImage.alt"/>"  class="img-thumbnail" style="height: 100px; width: 100px">
                    <p class="fs-3">
                        <a href="${pageContext.request.contextPath}/controller?command=profile&id=${review.accountId}" class="text-decoration-none">
                            <e:forHtml value="${review.accountName}"/>
                        </a>
                    </p>
                </div>
                <div class="col-md-6">
                    <p class="text-break">
                        <e:forHtml value="${review.text}"/>
                    </p>
                    <p class="fs-6 text-muted">
                            ${review.date}
                    </p>
                </div>
                <div class="col-md-2 ms-2">
                    <p class="fs-4">
                        <fmt:message key="creature.review.score"/>
                    </p>
                    <p class="fs-2">
                            ${review.rating}
                    </p>
                </div>
            </div>
            <hr/>
        </c:forEach>
    </div>
    <div class="container text-center">
        <a href="${pageContext.request.contextPath}/controller?command=${param.command}&id=${requestScope.creature.id}&start=${pageStart - perPage}">
            <<
        </a>${pageStart + 1} - ${pageStart + perPage}
        <a href="${pageContext.request.contextPath}/controller?command=${param.command}&id=${requestScope.creature.id}&start=${pageStart + perPage}">
            >>
        </a>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/form-validation.js"></script>
</body>
</html>
