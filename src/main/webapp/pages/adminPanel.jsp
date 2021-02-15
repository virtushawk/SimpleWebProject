<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="custom" uri="/WEB-INF/tld/custom.tld"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property.text"/>
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
        <c:when test="${param.filter == 'correctionName'}">
            <c:set var="creatures" value="${custom:sortByCorrectionName(corrections)}"/>
        </c:when>
        <c:when test="${param.filter == 'correctionDate'}">
            <c:set var="creatures" value="${custom:sortByCorrectionDate(corrections)}"/>
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
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="creatures-tab" data-bs-toggle="tab" href="#creatures" role="tab" aria-controls="creature" aria-selected="false">Creatures</a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="userCreatures-tab" data-bs-toggle="tab" href="#userCreatures" role="tab" aria-controls="userCreatures" aria-selected="false">User's creatures</a>
            </li>
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="userCorrections-tab" data-bs-toggle="tab" href="#userCorrections" role="tab" aria-controls="userCorrections" aria-selected="false">User's corrections</a>
            </li>
        </ul>
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade " id="userCorrections" role="tabpanel" aria-labelledby="creatures-tab">
                <div class="container">
                    <form class="d-flex justify-content-end g-1 me-5" action="${pageContext.request.contextPath}/controller?command=${param.command}">
                        <input type="hidden" name="command" value="${param.command}"/>
                        <select name="filter">
                            <option value="correctionName">By name</option>
                            <option value="correctionDate" selected>By date</option>
                        </select>
                        <button type="submit" class="btn btn-primary">Filter</button>
                    </form>
                </div>
                <ul class="list-group">
                    <c:forEach var="correction" items="${corrections}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <div class="card mb-3">
                                <div class="row g-4" style="width: 500px">
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#creatureModal${correction.correctionId}">
                                                    ${correction.name}
                                            </button>
                                            <div class="modal fade" id="creatureModal${correction.correctionId}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                <div class="modal-dialog modal-xl">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title">Correction</h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="card mb-3 mx-auto shadow p-3 mb-5 bg-white rounded">
                                                                <div class="row g-0">
                                                                    <div class="col-md-8">
                                                                        <div class="card-body">
                                                                            <h5 class="card-title">
                                                                                    ${correction.name}
                                                                            </h5>
                                                                            <p class="card-text">
                                                                                    ${correction.text}
                                                                            </p>
                                                                            <p class="card-text"><small class="text-muted">
                                                                                    ${correction.date}
                                                                            </small></p>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <p class="card-text text-truncate">${correction.text}</p>
                                            <p class="card-text"><small class="text-muted">${correction.date}</small></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-end">
                                <a class="btn btn-outline-success mt-1" href="${pageContext.request.contextPath}/controller?command=approve_correction&id=${correction.correctionId}">
                                    Approve
                                </a>
                                <a class="btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=delete_correction&id=${correction.correctionId}">
                                    delete
                                </a>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="tab-pane fade " id="userCreatures" role="tabpanel" aria-labelledby="creatures-tab">
                <div class="container">
                    <form class="d-flex justify-content-end g-1 me-5" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="admin_panel"/>
                        <select name="filter">
                            <option value="usersName">By name</option>
                            <option value="usersDate" selected>By date</option>
                        </select>
                        <button type="submit" class="btn btn-primary">Filter</button>
                    </form>
                </div>
                <ul class="list-group">
                    <c:forEach var="creature" items="${usersCreatures}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <div class="card mb-3">
                                <div class="row g-4" style="width: 500px">
                                    <div class="col-md-4">
                                        <img src="${pageContext.request.contextPath}/uploadController?url=${creature.picture}" alt="..." height="150" width="150">
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#creatureModal${creature.id}">
                                                ${creature.name}
                                            </button>
                                            <div class="modal fade" id="creatureModal${creature.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                <div class="modal-dialog modal-xl">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title">Creature</h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="card mb-3 mx-auto shadow p-3 mb-5 bg-white rounded">
                                                                <div class="row g-0">
                                                                    <div class="col-md-4">
                                                                        <img src="${pageContext.request.contextPath}/uploadController?url=${creature.picture}" alt="..." style=" width: 100%;object-fit: cover;height: 15vw">
                                                                    </div>
                                                                    <div class="col-md-8">
                                                                        <div class="card-body">
                                                                            <h5 class="card-title">
                                                                                    ${creature.name}
                                                                            </h5>
                                                                            <p class="card-text">
                                                                                    ${creature.description}
                                                                            </p>
                                                                            <p class="card-text"><small class="text-muted">
                                                                                <fmt:message key="home.creature.lastUpdated"/> ${creature.lastUpdated}
                                                                            </small></p>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <p class="card-text text-truncate">${creature.description}</p>
                                            <p class="card-text"><small class="text-muted">${creature.lastUpdated}</small></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-end">
                                <a class="btn btn-outline-success mt-1" href="${pageContext.request.contextPath}/controller?command=approve_creature&id=${creature.id}">
                                        Approve
                                </a>
                                <a class="btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=delete_creature&id=${creature.id}">
                                    delete
                                </a>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                <div class="container">
                    <form class="d-flex justify-content-end g-1 me-5" action="${pageContext.request.contextPath}/controller?command=admin_panel">
                        <input type="hidden" name="command" value="admin_panel"/>
                        <select name="filter">
                            <option value="username">By name</option>
                            <option value="role">By role</option>
                        </select>
                        <button type="submit" class="btn btn-primary">Filter</button>
                    </form>
                </div>
                <ul class="list-group">
                    <c:forEach var="user" items="${users}">
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
                                        <a class="d-flex ms-2 btn btn-outline-primary mt-1" href="${pageContext.request.contextPath}/controller?command=unblock_user&id=${user.id}">
                                            unblock
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a class="d-flex ms-2 btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=block_user&id=${user.id}">
                                            block
                                        </a>
                                        <a class="d-flex ms-2 btn btn-outline-primary mt-1" href="${pageContext.request.contextPath}/controller?command=make_admin&id=${user.id}">
                                            make admin
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
                <div class="text-center">
                </div>
            </div>
            <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                <div class="container">
                    <form class="d-flex justify-content-end g-1 me-5" action="${pageContext.request.contextPath}/controller?command=admin_panel">
                        <input type="hidden" name="command" value="admin_panel"/>
                        <select name="filter">
                            <option value="user">By User</option>
                            <option value="rate">By rating</option>
                        </select>
                        <button type="submit" class="btn btn-primary">Filter</button>
                    </form>
                </div>
                <ul class="list-group">
                    <c:forEach var="review" items="${reviews}">
                        <li class="list-group-item">
                            <div class="row g-0">
                                <div class="col-md-4">
                                    <img src="${pageContext.request.contextPath}/uploadController?url=${review.avatar}" alt="..." class="rounded-circle" height="150" width="150">
                                </div>
                                <div class="col-md-4">
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
                                    <div class="modal-dialog modal-lg">
                                        <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=edit_review" method="post" novalidate>
                                            <input type="hidden" name="id" value="${review.reviewId}">
                                            <input type="hidden" name="creature" value="${review.creatureId}">
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
            <div class="tab-pane fade " id="creatures" role="tabpanel" aria-labelledby="creatures-tab">
                <div class="container">
                    <form class="d-flex justify-content-end g-1 me-5" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="admin_panel"/>
                        <select name="filter">
                            <option value="name">By name</option>
                            <option value="date" selected>By date</option>
                            <option value="rating">By rating</option>
                        </select>
                        <button type="submit" class="btn btn-primary">Filter</button>
                    </form>
                </div>
                <ul class="list-group">
                    <c:forEach var="creature" items="${creatures}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <div class="card mb-3">
                                <div class="row g-4" style="width: 500px">
                                    <div class="col-md-4">
                                        <img src="${pageContext.request.contextPath}/uploadController?url=${creature.picture}" alt="..." height="150" width="150">
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <a href="${pageContext.request.contextPath}/controller?command=creature&id=${creature.id}" class="text-decoration-none stretched-link">
                                                    ${creature.name}
                                            </a>
                                            <p class="card-text text-truncate">${creature.description}</p>
                                            <p class="card-text"><small class="text-muted">${creature.lastUpdated}</small></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-end">
                                <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#imageModal${creature.id}">
                                    Change image
                                </button>
                                <button type="button" class="btn btn-outline-primary mt-1" data-bs-toggle="modal" data-bs-target="#bodyModal${creature.id}">
                                    Edit
                                </button>
                                <a class="btn btn-outline-danger mt-1" href="${pageContext.request.contextPath}/controller?command=delete_creature&id=${creature.id}">
                                    delete
                                </a>
                            </div>
                            <div class="modal fade" id="imageModal${creature.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=change_image&id=${creature.id}" method="post" enctype="multipart/form-data" novalidate>
                                        <div class="modal-header">
                                            <h5 class="modal-title">Change image</h5>
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
                            <div class="modal fade" id="bodyModal${creature.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-xl">
                                    <form class="modal-content needs-validation" action="${pageContext.request.contextPath}/controller?command=edit_creature&id=${creature.id}" method="post" novalidate>
                                        <div class="modal-header">
                                            <h5 class="modal-title">Edit Creature</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <label for="name" class="form-label">
                                                <fmt:message key="createCreature.name.label"/>
                                            </label>
                                            <input type="text" name="name" class="form-control" id="name" value="${creature.name}" required>
                                            <div class="valid-feedback">
                                                <fmt:message key="createCreature.valid"/>
                                            </div>
                                            <div class="invalid-feedback">
                                                <fmt:message key="createCreature.name.invalid"/>
                                            </div>
                                            <label for="description" class="form-label">
                                                <fmt:message key="createCreature.description.label"/>
                                            </label>
                                            <textarea class="form-control" name="description" id="description" rows="3" required>${creature.description}</textarea>
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
                        </li>
                    </c:forEach>
                </ul>
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
</body>
</html>
