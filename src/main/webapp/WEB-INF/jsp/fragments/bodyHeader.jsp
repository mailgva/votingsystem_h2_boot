<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<nav class="navbar navbar-expand-md navbar-dark bg-dark py-0">
    <div class="container">
        <a href="voting" class="navbar-brand"><img src="resources/images/icon-hand.png"> <spring:message code="app.title"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <sec:authorize access="isAuthenticated()">
                        <form:form class="form-inline my-2" action="logout" method="post">
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <%--<a class="btn btn-info mr-1" href="users"><spring:message code="user.title"/></a>--%>
                                <div class="dropdown" style=" margin-right: 5px">
                                    <button class="btn btn-success dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <span class="navbar-toggler-icon" style="height: 20px;"></span>
                                    </button>
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <a class="dropdown-item" href="users"><spring:message code="user.title"/></a>
                                        <a class="dropdown-item" href="dishes"><spring:message code="common.dishes"/></a>
                                        <a class="dropdown-item" href="restaurants"><spring:message code="common.restaurants"/></a>
                                        <a class="dropdown-item" href=""><spring:message code="common.dailyMenu"/></a>
                                        <a class="dropdown-item" href="votes"><spring:message code="common.votes"/></a>
                                    </div>
                                </div>
                            </sec:authorize>
                            <a class="btn btn-info mr-1" href="profile"><sec:authentication property="principal.userTo.name"/> <spring:message code="app.profile"/></a>
                            <button class="btn btn-primary" type="submit">
                                <span class="fa fa-sign-out"></span>
                            </button>
                        </form:form>
                    </sec:authorize>
                    <sec:authorize access="isAnonymous()">
                        <form:form class="form-inline my-2" id="login_form" action="spring_security_check" method="post">
                            <input class="form-control mr-1" type="text" placeholder="Email" name="username">
                            <input class="form-control mr-1" type="password" placeholder="Password" name="password">
                            <button class="btn btn-success" type="submit">
                                <span class="fa fa-sign-in"></span>
                            </button>
                        </form:form>
                    </sec:authorize>
                </li>
                <li class="nav-item dropdown">
                    <a class="dropdown-toggle nav-link my-1 ml-2" data-toggle="dropdown">${pageContext.response.locale}</a>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_uri']}?lang=en">English</a>
                        <a class="dropdown-item" href="${requestScope['javax.servlet.forward.request_uri']}?lang=ru">Русский</a>
                    </div>
                </li>
            </ul>
        </div>

    </div>
</nav>
<script type="text/javascript">
    const localeCode = "${pageContext.response.locale}";
</script>