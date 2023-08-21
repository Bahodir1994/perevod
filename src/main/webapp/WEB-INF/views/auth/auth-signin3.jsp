<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <title>Тизимга хуш келибсиз!</title>
    <!-- HTML5 Shim and Respond.js IE11 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 11]>
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
    <link rel="icon" href="${pageContext.servletContext.contextPath}/resources/assets/images/log.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/main/style.css">
</head>
<body>
<div class="col-sm-10 lira w-auto ">
    <div class="d-flex justify-content-center align-middle mb-2 ml-4">
        <img src="${pageContext.servletContext.contextPath}/resources/assets/images/log.png" alt=""
             class="img-fluid " style="width: 60px; height: 60px">
        <span class=" ml-4  font-weight-bold">E-EXPERT</span>
    </div>
    <h4 class=" font-weight-normal align-content-center ">Тизим ҳақида қисқача маълумот....</h4>
</div>
<div class="auth-wrapper align-items-stretch aut-bg-img">
    <div class="flex-grow-1">
        <div class="auth-side-form">
            <div class=" auth-content">
                <div class="login">
                    <div class="login-container-wrapper clearfix">
                        <div class="welcome"><strong>Тизимга хуш келибсиз!</strong></div>
                        <form:form modelAttribute="auth-signin" method="post"
                                   cssClass="win3PartInfPartF needs-validation form-horizontal login-form"
                                   action="${pageContext.request.contextPath}/user/auth/auth-signin"
                                   id="signin" name="signin" novalidate="true">
                            <div class="form-group relative">
                                <form:input id="login_username" path="username" cssClass="form-control input-lg"
                                            type="text" placeholder="Логин"/>
                                <i class="fa fa-user"></i>
                            </div>
                            <div class="form-group relative password">
                                <form:input id="login_password" path="password" cssClass="form-control input-lg"
                                            type="password" placeholder="Парол"/>
                                <i class="fa fa-lock"></i>
                            </div>
                            <div class="checkbox pull-left mb-2">
                                <label><input type="checkbox"> Маълумотлар сақлансин.</label>
                            </div>
                            <div class="form-group">
                                <button type="submit" class="btn btn-success btn-lg btn-block">Кириш</button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Required Js -->
<script src="${pageContext.servletContext.contextPath}/resources/assets/js/main/vendor-all.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/assets/js/plugins/bootstrap.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/assets/js/main/ripple.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/assets/js/main/pcoded.min.js"></script>
<script>
    function signUp() {
        signin.action = "..${pageContext.request.contextPath}/user/auth/authIn";
        signin.target = '_self';
        signin.submit();
    }

    function signIn() {
        let username = $('input#username').val();
        let password = $('input#password').val();
        var dataS = {}
        var tipform = "<%=request.getContextPath()%>/user/auth/auth-signin/" + username + "/" + password;
        $.ajax({
            type: "POST",
            url: tipform,
            data: dataS,
            dataType: "json",
            success: function (res) {
            },
            error: function (res) {
                alert('ERROR! \n ' + res.statusText);
            }
        });
    }
</script>
</body>

</html>
