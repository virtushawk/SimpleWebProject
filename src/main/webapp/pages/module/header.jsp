<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/>
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm p-3 mb-5 bg-white rounded">
    <div class="collapse navbar-collapse" id="navbar">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=home">
                    <fmt:message key="header.homePage"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=catalog">
                    <fmt:message key="header.catalog"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    ${sessionScope.user.role}
                </a>
            </li>
            <c:if test="${sessionScope.user.role.name().equals('ADMIN')}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=admin_panel">
                        Admin panel
                    </a>
                </li>
            </c:if>

        </ul>
    </div>
    <div class="collapse navbar-collapse text-center ms-5">
        <ul class="navbar-nav">
            <li class="nav-item">
                <form action="${pageContext.request.contextPath}/controller" method="get">
                    <div class="input-group">
                        <input type="hidden" name="command" value="search" />
                        <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search" name="text"
                               aria-describedby="search-addon" style="width: 300px;">
                        <button type="submit" class="btn btn-outline-primary">search</button>
                </div>
                </form>
            </li>
        </ul>
    </div>
    <div class="collapse navbar-collapse flex-grow-1 text-right" id="login">
        <ul class="navbar-nav ms-auto flex-nowrap">
            <li class="nav-item dropdown dropstart" id="language">
                <button class="btn btn-light btn-sm dropdown-toggle  " type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                    <fmt:message key="header.language"/>
                </button>
                <div class="dropdown-menu " aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_url']}?sessionLocale=en_US&command=${param.command}&id=${param.id}&text=${param.text}">
                        <fmt:message key="header.language.en"/>
                    </a>
                    <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_url']}?sessionLocale=ru_RU&command=${param.command}&id=${param.id}&text=${param.text}">
                        <fmt:message key="header.language.ru"/>
                    </a>
                </div>
            </li>
           <c:choose>
               <c:when test="${sessionScope.authorised}">
                   <li class="nav-item ">
                       <a href="${pageContext.request.contextPath}/controller?command=profile&id=${sessionScope.user.id}" class="nav-link m-2 menu-item ">
                           ${sessionScope.user.username}
                       </a>
                   </li>
                   <li class="nav-item">
                       <a href="${pageContext.request.contextPath}/controller?command=logout" class="nav-link m-2 menu-item">
                           <fmt:message key="header.logoutPage"/>
                       </a>
                   </li>
               </c:when>
               <c:otherwise>
                   <li class="nav-item">
                       <a href="${pageContext.request.contextPath}/pages/login.jsp" class="nav-link m-2 menu-item">
                           <fmt:message key="header.loginPage"/>
                       </a>
                   </li>
               </c:otherwise>
           </c:choose>
        </ul>
    </div>
</nav>
</html>