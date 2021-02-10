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
        Admin Panel
    </title>
</head>
<body>
<jsp:include page="/pages/module/header.jsp"/>
<div class="container">
    <div class="col-sm-12 mb-3">
        <ul class="nav nav-tabs justify-content-center" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
                <a class="nav-link active" id="home-tab" data-bs-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">Users</a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="profile-tab" data-bs-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Reviews</a>
            </li>
        </ul>
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                <ul class="list-group">
                    <c:forEach var="user" items="${requestScope.users}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <div class="d-flex justify-content-start align-items-center">
                                <img src="${pageContext.request.contextPath}/uploadController?url=${user.avatar}" class="rounded-circle" height="150" width="150">
                            </div>
                            <div class="d-flex">
                                <a class="d-flex ms-2" href="${pageContext.request.contextPath}/controller?command=profile&id=${user.id}">
                                        ${user.username}
                                </a>
                            </div>
                            <div class="d-flex">
                                <h6 class="d-flex ms-2">${user.email}</h6>
                            </div>
                            <div class="d-flex">
                                <h6 class="d-flex ms-2">${user.role}</h6>
                            </div>
                            <div class="d-flex justify-content-end">
                                <c:choose>
                                    <c:when test="${user.role.name().equals('INACTIVE')}">
                                        <a class="d-flex ms-2" href="${pageContext.request.contextPath}/controller?command=unblock_user&id=${user.id}">
                                            unblock
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="d-flex ms-2" href="${pageContext.request.contextPath}/controller?command=block_user&id=${user.id}">
                                            block
                                        </a>
                                        <a class="d-flex ms-2" href="${pageContext.request.contextPath}/controller?command=make_admin&id=${user.id}">
                                            make admin
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                <ul class="list-group">
                    <c:forEach var="review" items="${requestScope.reviews}">
                        <li class="list-group-item">
                            <div class="row g-0">
                                <div class="col-md-4">
                                    <img src="${pageContext.request.contextPath}/uploadController?url=${review.avatar}" alt="..." class="rounded-circle" height="150" width="150">
                                </div>
                                <div class="col-md-4">
                                    <div class="card-body">
                                        <h5 class="card-title">
                                                ${review.accountName}
                                        </h5>
                                        <p class="card-text">
                                            ${review.text}
                                        </p>
                                        <p class="card-text"><small class="text-muted">
                                            ${review.time}
                                        </small></p>
                                    </div>
                                </div>
                                <div class="col-md-2 my-auto">
                                    <h4>score</h4>
                                    <h4>${review.rating}</h4>
                                </div>
                                <div class="col-md-1 my-auto">
                                    <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#exampleModal${review.reviewId}">
                                        edit
                                    </button>
                                </div>
                                <div class="col-md-1 my-auto">
                                    <a class="btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=delete_review&id=${review.reviewId}">
                                        delete
                                    </a>
                                </div>
                                <div class="modal fade" id="exampleModal${review.reviewId}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=edit_review&id=${review.reviewId}" method="post" novalidate>
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Edit review</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="row g-1">
                                                    <div class="col-md-3">
                                                        <img src="${pageContext.request.contextPath}/uploadController?url=${review.avatar}" alt="..."  class="img-thumbnail" style="height: 100px; width: 100px">
                                                        <p class="fs-3">
                                                                ${review.accountName}
                                                        </p>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <label for="exampleFormControlTextarea1" class="form-label">
                                                            Review
                                                        </label>
                                                        <textarea class="form-control" name="review" id="exampleFormControlTextarea1" rows="3" required>${review.text}</textarea>
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
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/form-validation.js"></script>
</body>
</html>
