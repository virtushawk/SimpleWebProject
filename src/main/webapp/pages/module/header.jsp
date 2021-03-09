<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="custom" uri="/WEB-INF/tld/custom.tld"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"/>
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm p-3 mb-5 bg-white rounded">
    <div class="collapse navbar-collapse">
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
            <custom:access accessRole="ADMIN">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=admin_panel">
                        <fmt:message key="header.adminPanel"/>
                    </a>
                </li>
            </custom:access>
        </ul>
    </div>
    <div class="collapse navbar-collapse text-center ms-5">
        <ul class="navbar-nav">
            <li class="nav-item">
                <form action="${pageContext.request.contextPath}/controller" method="get">
                    <div class="input-group">
                        <input type="hidden" name="command" value="search"/>
                        <input type="search" class="form-control rounded"
                               placeholder="<fmt:message key="header.search.placeHolder"/>" name="text"
                               style="width: 300px;">
                        <button type="submit" class="btn btn-outline-primary">
                            <fmt:message key="header.search.placeHolder"/>
                        </button>
                    </div>
                </form>
            </li>
        </ul>
    </div>
    <div class="collapse navbar-collapse flex-grow-1 text-right">
        <ul class="navbar-nav ms-auto flex-nowrap">
            <li class="nav-item dropdown dropstart" id="language">
                <button class="btn btn-light btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown">
                    <fmt:message key="header.language"/>
                </button>
                <div class="dropdown-menu ">
                    <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_url']}?sessionLocale=en_US<c:if test="${not empty param.command}">&command=${param.command}</c:if><c:if test="${not empty param.id}">&id=${param.id}</c:if><c:if test="${not empty param.text}">&text=${param.text}</c:if>">
                        <fmt:message key="header.language.en"/>
                    </a>
                    <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_url']}?sessionLocale=ru_RU<c:if test="${not empty param.command}">&command=${param.command}</c:if><c:if test="${not empty param.id}">&id=${param.id}</c:if><c:if test="${not empty param.text}">&text=${param.text}</c:if>">
                        <fmt:message key="header.language.ru"/>
                    </a>
                </div>
            </li>
           <c:choose>
               <c:when test="${sessionScope.authorised}">
                   <li class="nav-item">
                       <a href="${pageContext.request.contextPath}/controller?command=profile&id=${sessionScope.user.accountId}" class="nav-link m-2 menu-item">
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