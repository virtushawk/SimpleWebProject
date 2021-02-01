<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbar">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=home">
                    <fmt:message key="header.homePage"/>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=catalog">
                    Catalog
                </a>
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
                    <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_url']}?sessionLocale=en_US&command=${param.command}&id=${param.id}">
                        <fmt:message key="header.language.en"/>
                    </a>
                    <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_url']}?sessionLocale=ru_RU&command=${param.command}&id=${param.id}">
                        <fmt:message key="header.language.ru"/>
                    </a>
                </div>
            </li>
           <c:choose>
               <c:when test="${sessionScope.authorised}">
                   <li class="nav-item ">
                       <a href="#" class="nav-link m-2 menu-item ">
                           ${sessionScope.username}
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