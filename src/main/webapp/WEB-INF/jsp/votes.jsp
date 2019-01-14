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
<script type="text/javascript" src="resources/js/voting.votes.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="common.votes"/></h3>
        <div class="card border-dark">
            <div class="card-body pb-0">
                <form id="filter">
                    <dl>
                        <dt><spring:message code="common.date"/>:</dt>
                        <dd><input type="date" name="date" value="${(param.date != null ? param.date : date)}"
                                   onchange="updateFilteredTable();">
                        </dd>
                    </dl>
                </form>
            </div>
        </div>
        </br>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="resto.name"/></th>
                <th><spring:message code="user.name"/></th>
                <th></th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="common"/>
</jsp:include>
</html>