<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<script type="text/javascript" src="resources/js/voting.common.js" defer></script>
<script type="text/javascript" src="resources/js/voting.dishes.js" defer></script>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="common.dishes"/></h3>
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="common.add"/>
        </button>

        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="dish.name"/></th>
                <th><spring:message code="dish.price"/></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="modalTitle"></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm" enctype="multipart/form-data">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="name" class="col-form-label"><spring:message code="dish.name"/></label>
                        <input type="text" class="form-control" id="name" name="name"
                               placeholder="<spring:message code="dish.name"/>">
                    </div>

                    <div class="form-group">
                        <label for="price" class="col-form-label"><spring:message code="dish.price"/></label>
                        <input type="numeric" class="form-control" id="price" name="price"
                               placeholder="<spring:message code="dish.price"/>">
                    </div>

                    <div class="form-group">
                        <label for="img_file" class="col-form-label"><spring:message code="dish.picture"/></label>
                        <img src="" class="img-thumbnail" id="img_file_pic" name="img_file_pic" />
                        <input type="file" class="form-control" id="img_file" name="img_file" accept="image/*"
                               placeholder="<spring:message code="dish.picture"/>"  onchange="readURL(this)">
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<jsp:include page="fragments/i18n.jsp">
    <jsp:param name="page" value="dish"/>
</jsp:include>
</html>