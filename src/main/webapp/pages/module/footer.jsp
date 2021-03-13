<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="custom" uri="/WEB-INF/tld/custom.tld"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<footer class=" container pt-4 my-md-3 pt-md-3 border-top text-center">
    <fmt:message key="footer.title"/>
</footer>
</html>