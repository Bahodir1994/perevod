<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Божхона қиймати</title>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- Meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="description" content=""/>
    <meta name="keywords" content="">
    <meta name="author" content="Phoenixcoded"/>
    <!-- Favicon icon -->
    <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/images/emblem.png" type="image/x-icon">

    <!-- vendor css -->
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/plugins/select2.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/icons.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/FontawesomePro6/css/all.css">
</head>
<body>
<!-- [ auth-signup ] start -->
<div class="auth-wrapper">
    <div class="auth-content">
        <div class="card">
            <div class="row align-items-center text-center">
                <div class="col-md-12">
                    <div class="card-body">
                        <img src="${pageContext.servletContext.contextPath}/resources/images/emblem.png" alt=""
                             class="img-fluid mb-4" style="width: 80px; height: 80px">
                        <h4 class="mb-3 f-w-400">Рўйхатдан ўтиш</h4>
                        <form:form method="POST" modelAttribute="auth-signup" action="${pageContext.servletContext.contextPath}/user/auth/auth-create" novalidate="true">
                            <div class="form-group mb-4">
                                <form:select class="form-control selectpicker f-16" data-live-search="true" path="post" name="post" id="POST" required="true">
                                    <option value="26_08">Даромадлар ва божхона статистикаси бўлими</option>
                                    <%--                                    <c:forEach items="${postList}" var="val">--%>
                                    <%--                                        <option ${val[0] eq '26002'?'selected':''} value="${val[0]}">${val[0]} - ${val[1]}</option>--%>
                                    <%--                                    </c:forEach>--%>
                                </form:select>
                            </div>
                            <div class="form-group mb-3">
                                <label class="floating-label" for="FULLNAME">ФИО</label>
                                <form:input type="text" class="form-control f-16" path="fullname" name="fullname" id="FULLNAME" placeholder="" required="true" autofocus="true"></form:input>
                            </div>
                            <div class="form-group mb-3">
                                <label class="floating-label" for="USERNAME">Логин</label>
                                <form:input type="text" class="form-control f-16" path="username" id="USERNAME" placeholder="" required="true" autofocus="true"></form:input>
                            </div>
                            <div class="form-group mb-4">
                                <label class="floating-label" for="PASSWORD">Парол</label>
                                <form:input type="password" class="form-control f-16" path="password" id="PASSWORD" placeholder="" required="true"></form:input>
                            </div>
                            <div class="form-group mb-4">
                                <label class="floating-label" for="Password">Паролни тасдиқланг</label>
                                <form:input type="password" class="form-control f-16" path="passwordConfirm" placeholder="" required="true"></form:input>
                            </div>
                            <form:button type="submit" class="btn btn-primary btn-block mb-4">Рўйхатдан ўтиш</form:button>
                        </form:form>
                        <p class="mb-2">Тизимдан рўйхатдан ўтганмисиз?<br><a href="${pageContext.servletContext.contextPath}/user/auth/auth-signin" class="f-w-400">Тизимга кириш</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- [ auth-signup ] end -->
<!-- Required Js -->
<script src="${pageContext.servletContext.contextPath}/resources/js/vendor-all.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/ripple.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/pcoded.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/menu-setting.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/plugins/select2.full.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/pages/form-select-custom.js"></script>
</body>

</html>
