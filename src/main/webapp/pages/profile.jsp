<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<div class="container row">
    <c:if test="${sessionScope.user.id == requestScope.user.id}">
        <div class="d-flex align-items-start col-1">
            <div class="nav flex-column nav-pills me-3" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                <button class="btn btn-outline-primary active" id="v-pills-profile-tab" data-bs-toggle="pill" data-bs-target="#v-pills-profile" type="button" role="tab" aria-controls="v-pills-profile" aria-selected="true">profile</button>
                <button class="btn btn-outline-primary" id="v-pills-settings-tab" data-bs-toggle="pill" data-bs-target="#v-pills-settings" type="button" role="tab" aria-controls="v-pills-settings" aria-selected="false">Settings</button>
            </div>
        </div>
    </c:if>
    <div class="tab-content col-11" id="v-pills-tabContent">
        <div class="tab-pane fade show active" id="v-pills-profile" role="tabpanel" aria-labelledby="v-pills-profile-tab">
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
                                        <div class="col-sm-8 text-secondary">
                                            ${requestScope.user.name}
                                        </div>
                                        <c:if test="${sessionScope.user.id == requestScope.user.id}">
                                            <div class="col-sm-1">
                                                <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#userNameModal">
                                                    <i class="bi bi-pencil"></i>
                                                </button>
                                            </div>
                                        </c:if>
                                        <div class="modal fade" id="userNameModal" tabindex="-1" aria-labelledby="userNameModal" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=edit_name" method="post" novalidate>
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">Change Full name</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class= "modal-body">
                                                        <input type="text" name="name" class="form-control" required value="${requestScope.user.name}"/>
                                                        <div class="valid-feedback">
                                                            <fmt:message key="createCreature.valid"/>
                                                        </div>
                                                        <div class="invalid-feedback">
                                                            <fmt:message key="createCreature.name.invalid"/>
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
                                        <c:forEach var="review" items="${requestScope.reviews}" begin="${rpageStart}" end="${rpageStart + perPage - 1}">
                                            <div class="row g-0">
                                                <div class="col-md-4">
                                                    <img src="${pageContext.request.contextPath}/uploadController?url=${requestScope.user.avatar}" alt="..." class="rounded-circle" height="150" width="150">
                                                </div>
                                                <div class="col-md-8">
                                                    <div class="card-body">
                                                        <h5 class="card-title">
                                                                Review to : <a href="${pageContext.request.contextPath}/controller?command=creature&id=${review.creatureId}" class="text-decoration-none">
                                                                ${review.creatureName}
                                                            </a>
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
                                        <div class="container text-center">
                                            <a href="${pageContext.request.contextPath}/controller?command=profile&id=${param.id}&rstart=${rpageStart - perPage}"><<</a>${rpageStart + 1} - ${rpageStart + perPage}
                                            <a href="${pageContext.request.contextPath}/controller?command=profile&id=${param.id}&rstart=${rpageStart + perPage}">>></a>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                                        <c:forEach var="creature" items="${requestScope.creatures}" begin="${cpageStart}" end="${cpageStart + perPage - 1}">
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
                                        <div class="container text-center">
                                            <a href="${pageContext.request.contextPath}/controller?command=profile&id=${param.id}&cstart=${cpageStart - perPage}"><<</a>${cpageStart + 1} - ${cpageStart + perPage}
                                            <a href="${pageContext.request.contextPath}/controller?command=profile&id=${param.id}&cstart=${cpageStart + perPage}">>></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="tab-pane fade" id="v-pills-settings" role="tabpanel" aria-labelledby="v-pills-settings-tab">
            <div class="container shadow-sm p-3 mb-5 bg-white rounded">
                <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#emailModal">
                    Change email
                </button>
                <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#passwordModal">
                    Change Password
                </button>
                <div class="modal fade" id="emailModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=edit_email" method="post" novalidate>
                            <div class="modal-header">
                                <h5 class="modal-title">Change email</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <input type="text" name="email" class="form-control" required value="${requestScope.user.email}"/>
                                <div class="valid-feedback">
                                    <fmt:message key="createCreature.valid"/>
                                </div>
                                <div class="invalid-feedback">
                                    <fmt:message key="createCreature.name.invalid"/>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Save changes</button>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="modal fade" id="passwordModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=change_password" method="post" novalidate>
                            <div class="modal-header">
                                <h5 class="modal-title">Change password</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <label for="password">
                                    Password :
                                </label>
                                <input type="password" class="form-control" id="password" required/>
                                <div class="valid-feedback">
                                    <fmt:message key="createCreature.valid"/>
                                </div>
                                <div class="invalid-feedback">
                                    <fmt:message key="createCreature.name.invalid"/>
                                </div>
                                <label for="confirm_password">
                                    Confirm password :
                                </label>
                                <input type="password" name="password" id="confirm_password" class="form-control" required/>
                                <div class="valid-feedback">
                                    <fmt:message key="createCreature.valid"/>
                                </div>
                                <div class="invalid-feedback">
                                    <fmt:message key="createCreature.name.invalid"/>
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
