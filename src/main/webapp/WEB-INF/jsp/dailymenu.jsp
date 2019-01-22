<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://voting.com/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/voting.common.js" defer></script>
<script type="text/javascript" src="resources/js/voting.dailymenu.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-4">
    <div class="container">
        <h3><spring:message code="menu.voting"/></h3>
        <div class="card border-dark">
            <div class="card-body pb-0">
                <form <%--method="post" action="voting"--%> id="filter">
                    <dl>
                        <dt><spring:message code="common.date"/>:</dt>
                        <dd><input type="date" name="date" value="${(param.date != null ? param.date : date)}"
                                   onchange="updateFace();">
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <button type="button" onclick="generateDailyMenu()"><spring:message code="common.generateDailyMenu"/></button>
                            </sec:authorize>

                        </dd>
                    </dl>
                </form>
            </div>
        </div>
        </br>
        <form method="post" action="voting/vote" id="voteForm">
        </form>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="common"/>
</jsp:include>
</html>