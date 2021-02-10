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
            </div>
        </div>
    </div>
</div>
<c:if test="${sessionScope.authorised}" >
<div class="container-sm shadow p-3 mb-5 bg-white rounded w-50">
    <form class="row g-1" action="${pageContext.request.contextPath}/controller?command=create_review&id=${requestScope.creature.id}" method="post">
        <div class="col-md-3">
            <img src="${pageContext.request.contextPath}/uploadController?url=${sessionScope.user.avatar}" alt="..."  class="img-thumbnail" style="height: 100px; width: 100px">
            <p class="fs-3">
                    ${sessionScope.user.username}
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
</c:if>
<div class="container shadow p-3 mb-5 bg-white rounded w-50">
    <div class="list-group">
        <c:forEach var="review" items="${requestScope.reviews}">
            <div class="row g-1">
                <div class="col-md-3">
                    <img src="${pageContext.request.contextPath}/uploadController?url=${review.avatar}" alt="..."  class="img-thumbnail" style="height: 100px; width: 100px">
                    <p class="fs-3">
                            ${review.accountName}
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
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>
