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
        Create Creature
    </title>
</head>
<body>
<jsp:include page="/pages/module/header.jsp"/>
<div class="container shadow p-3 mb-5 bg-white rounded">
    <form class="row g-3 justify-content-md-center" action="${pageContext.request.contextPath}/controller?command=create_creature" method="post" enctype="multipart/form-data">
        <div class="col-md-6">
            <label class="form-label" for="customFile">Image</label>
            <input type="file" name="dsadas" class="form-control" id="customFile"  accept="image/png,image/jpeg,image/jpg"/>
        </div>
        <div class="col-md-6">
            <label for="inputEmail4" class="form-label">Name</label>
            <input type="text" name="name" class="form-control" id="inputEmail4">
        </div>
        <div class="col-12">
            <label for="exampleFormControlTextarea1" class="form-label">Description</label>
            <textarea class="form-control" name="description" id="exampleFormControlTextarea1" rows="3"></textarea>
        </div>
        <div class="col-12">
            <button type="submit" class="btn btn-primary">Create</button>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>
</html>