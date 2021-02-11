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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css"/>
    <title>
        ${sessionScope.user.username}
    </title>
</head>
<body>
<jsp:include page="/pages/module/header.jsp"/>
<div class="container">
    <div class="main-body">
        <div class="row gutters-sm">
            <div class="col-md-4 mb-3">
                <div class="card">
                    <div class="card-body">
                        <div class="d-flex flex-column align-items-center text-center">
                            <img src="${pageContext.request.contextPath}/uploadController?url=${requestScope.user.avatar}" alt="Admin" class="rounded-circle" width="150" height="150">
                            <!-- Button trigger modal -->
                            <c:if test="${sessionScope.user.id == requestScope.user.id}">
                                <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                    Change avatar
                                </button>
                            </c:if>

                            <!-- Modal -->
                            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=change_avatar" method="post" enctype="multipart/form-data" novalidate>
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel">Change avatar</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
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
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            <button type="submit" class="btn btn-primary">Save changes</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="mt-3">
                                <h4>${requestScope.user.username}</h4>
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
                                <h6 class="mb-0">Full Name</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                Roman
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Email</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${requestScope.user.email}
                            </div>
                        </div>
                        <hr>
                    </div>
                </div>
                <div class="col-sm-12 mb-3 shadow-sm p-3 mb-5 bg-white rounded">
                    <ul class="nav nav-tabs justify-content-center" id="myTab" role="tablist">
                        <li class="nav-item" role="presentation">
                            <a class="nav-link active" id="home-tab" data-bs-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">Reviews</a>
                        </li>
                        <li class="nav-item" role="presentation">
                            <a class="nav-link" id="profile-tab" data-bs-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Creatures</a>
                        </li>
                    </ul>
                    <div class="tab-content" id="myTabContent">
                        <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                            <c:forEach var="review" items="${requestScope.reviews}">
                                <div class="row g-0">
                                    <div class="col-md-4">
                                        <img src="${pageContext.request.contextPath}/uploadController?url=${requestScope.user.avatar}" alt="..." class="rounded-circle" height="150" width="150">
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <h5 class="card-title">
                                                    ${requestScope.user.username}
                                            </h5>
                                            <p class="card-text">
                                                ${review.text}
                                            </p>
                                            <p class="card-text"><small class="text-muted">
                                                <fmt:message key="home.creature.lastUpdated"/> ${review.time}
                                            </small></p>
                                        </div>
                                    </div>
                                </div>
                                <hr>
                            </c:forEach>
                        </div>
                        <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                            <c:forEach var="creature" items="${requestScope.creatures}">
                                <div class="row g-0">
                                    <div class="col-md-4">
                                        <img src="${pageContext.request.contextPath}/uploadController?url=${creature.picture}" alt="..." height="150" width="150">
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <h5 class="card-title">
                                                    ${creature.name}
                                            </h5>
                                            <p class="card-text text-truncate">
                                                ${creature.description}
                                            </p>
                                            <p class="card-text"><small class="text-muted">
                                                <fmt:message key="home.creature.lastUpdated"/> ${creature.lastUpdated}
                                            </small></p>
                                        </div>
                                    </div>
                                </div>
                                <hr>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/form-validation.js"></script>
</body>
</html>
