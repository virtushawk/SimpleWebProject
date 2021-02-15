<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property.text"/>
<c:set var="userReview" value="${requestScope.userReview}"/>
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
                <h5 class="card-title">
                    ${requestScope.creature.name}
                </h5>
                <p class="card-text">
                    ${requestScope.creature.description}
                </p>
                <p class="card-text"><small class="text-muted">
                    <fmt:message key="home.creature.lastUpdated"/> ${requestScope.creature.lastUpdated}
                </small></p>
                <c:if  test="${sessionScope.user.role.name().equals('USER') || sessionScope.user.role.name().equals('ADMIN')}">
                    <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#bodyModal">
                        Edit
                    </button>
                </c:if>
                <div class="modal fade" id="bodyModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-xl">
                        <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=suggest_correction&id=${requestScope.creature.id}" method="post" novalidate>
                            <div class="modal-header">
                                <h5 class="modal-title">Edit Creature</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <label for="name" class="form-label">
                                    <fmt:message key="createCreature.name.label"/>
                                </label>
                                <input type="text" name="name" class="form-control" id="name" value="${requestScope.creature.name}" required>
                                <div class="valid-feedback">
                                    <fmt:message key="createCreature.valid"/>
                                </div>
                                <div class="invalid-feedback">
                                    <fmt:message key="createCreature.name.invalid"/>
                                </div>
                                <label for="description" class="form-label">
                                    <fmt:message key="createCreature.description.label"/>
                                </label>
                                <textarea class="form-control" name="description" id="description" rows="3" required>${requestScope.creature.description}</textarea>
                                <div class="valid-feedback">
                                    <fmt:message key="createCreature.valid"/>
                                </div>
                                <div class="invalid-feedback">
                                    <fmt:message key="createCreature.description.invalid"/>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Save changes</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<c:choose>
    <c:when test="${(sessionScope.user.role.name().equals('USER') || sessionScope.user.role.name().equals('ADMIN')) && (empty requestScope.userReview)}">
        <div class="container-sm shadow p-3 mb-5 bg-white rounded w-50">
            <form class="row g-1" action="${pageContext.request.contextPath}/controller?command=create_review&id=${requestScope.creature.id}" method="post">
                <div class="col-md-3">
                    <img src="${pageContext.request.contextPath}/uploadController?url=${sessionScope.user.avatar}" alt="..."  class="img-thumbnail" style="height: 100px; width: 100px">
                    <p class="fs-3">
                            ${sessionScope.user.name}
                    </p>
                </div>
                <div class="col-md-6">
                    <label for="exampleFormControlTextarea1" class="form-label">
                        Description
                    </label>
                    <textarea class="form-control" name="review" id="exampleFormControlTextarea1" rows="3"></textarea>
                </div>
                <div class="col-md-2 ms-2">
                    <p class="fs-4">score</p>
                    <select class="form-select" aria-label="Default select example" name="rating">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Publish</button>
                </div>
            </form>
        </div>
    </c:when>
    <c:when test="${(sessionScope.user.role.name().equals('USER') || sessionScope.user.role.name().equals('ADMIN')) && (not empty requestScope.userReview)}">
        <div class="container-sm shadow p-3 mb-5 bg-white rounded w-50">
            <div class="row g-1">
                <div class="col-md-3">
                    <img src="${pageContext.request.contextPath}/uploadController?url=${userReview.avatar}" alt="..."  class="img-thumbnail" style="height: 100px; width: 100px">
                    <p class="fs-3">
                            ${userReview.accountName}
                    </p>
                </div>
                <div class="col-md-6">
                    <p class="text-break">${userReview.text}</p>
                </div>
                <div class="col-md-2 ms-2">
                    <p class="fs-4">score</p>
                    <p class="fs-2">${userReview.rating}</p>
                    <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#reviewModal">
                        Edit
                    </button>
                    <a class="btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=delete_review&id=${userReview.reviewId}">
                        delete
                    </a>
                    <div class="modal fade" id="reviewModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-xl">
                            <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=edit_review" method="post" novalidate>
                                <input type="hidden" name="id" value="${userReview.reviewId}">
                                <input type="hidden" name="creature" value="${userReview.creatureId}">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Edit review</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="row g-1">
                                        <div class="col-md-3">
                                            <img src="${pageContext.request.contextPath}/uploadController?url=${userReview.avatar}" alt="..."  class="img-thumbnail" style="height: 100px; width: 100px">
                                            <p class="fs-3">
                                                    ${userReview.accountName}
                                            </p>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="exampleFormControlTextarea1" class="form-label">
                                                Review
                                            </label>
                                            <textarea class="form-control" name="review" rows="3" required>${userReview.text}</textarea>
                                            <div class="valid-feedback">
                                                <fmt:message key="createCreature.valid"/>
                                            </div>
                                            <div class="invalid-feedback">
                                                <fmt:message key="createCreature.description.invalid"/>
                                            </div>
                                        </div>
                                        <div class="col-md-2 ms-2">
                                            <p class="fs-4">score</p>
                                            <select class="form-select" aria-label="Default select example" name="rating" required>
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
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    <button type="submit" class="btn btn-primary">Save changes</button>
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
                    <img src="${pageContext.request.contextPath}/uploadController?url=${review.avatar}" alt="..."  class="img-thumbnail" style="height: 100px; width: 100px">
                    <p class="fs-3">
                        <a href="${pageContext.request.contextPath}/controller?command=profile&id=${review.accountId}" class="text-decoration-none">
                                ${review.accountName}
                        </a>
                    </p>
                </div>
                <div class="col-md-6">
                    <p class="text-break">${review.text}</p>
                </div>
                <div class="col-md-2 ms-2">
                    <p class="fs-4">score</p>
                    <p class="fs-2">${review.rating}</p>
                </div>
            </div>
            <hr/>
        </c:forEach>
    </div>
    <div class="container text-center">
        <a href="${pageContext.request.contextPath}/controller?command=${param.command}&id=${requestScope.creature.id}&start=${pageStart - perPage}"><<</a>${pageStart + 1} - ${pageStart + perPage}
        <a href="${pageContext.request.contextPath}/controller?command=${param.command}&id=${requestScope.creature.id}&start=${pageStart + perPage}">>></a>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>
