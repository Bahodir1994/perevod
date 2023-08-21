<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--favicon-->
        <link rel="icon" href="${pageContext.request.contextPath}/resources/assets/images/favicon-32x32.png" type="image/png" />

        <!-- loader-->
        <link href="${pageContext.request.contextPath}/resources/assets/css/pace.min.css" rel="stylesheet" />
        <script src="${pageContext.request.contextPath}/resources/assets/js/pace.min.js"></script>
        <!-- Bootstrap CSS -->
        <link href="${pageContext.request.contextPath}/resources/assets/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resources/assets/css/app.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resources/assets/css/icons.css" rel="stylesheet">
        <title>Dashtreme - Multipurpose Bootstrap5 Admin Template</title>
    </head>
    <body class="bg-theme bg-theme2">
        <!--wrapper-->
        <div class="wrapper">
            <header class="login-header shadow">
                <nav class="navbar navbar-expand-lg navbar-light bg-dark rounded fixed-top rounded-0 shadow-sm">
<%--                    <div class="container-fluid">--%>
<%--                        <a class="navbar-brand" href="#">--%>
<%--                            <img src="assets/images/logo-img.png" width="140" alt="" />--%>
<%--                        </a>--%>
<%--                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent1" aria-controls="navbarSupportedContent1" aria-expanded="false" aria-label="Toggle navigation"> <span class="navbar-toggler-icon"></span>--%>
<%--                        </button>--%>
<%--                        <div class="collapse navbar-collapse" id="navbarSupportedContent1">--%>
<%--                            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">--%>
<%--                                <li class="nav-item"> <a class="nav-link text-white" aria-current="page" href="#"><i class='bx bx-home-alt me-1'></i>Home</a>--%>
<%--                                </li>--%>
<%--                                <li class="nav-item"> <a class="nav-link text-white" href="#"><i class='bx bx-user me-1'></i>About</a>--%>
<%--                                </li>--%>
<%--                                <li class="nav-item"> <a class="nav-link text-white" href="#"><i class='bx bx-category-alt me-1'></i>Features</a>--%>
<%--                                </li>--%>
<%--                                <li class="nav-item"> <a class="nav-link text-white" href="#"><i class='bx bx-microphone me-1'></i>Contact</a>--%>
<%--                                </li>--%>
<%--                            </ul>--%>
<%--                        </div>--%>
<%--                    </div>--%>
                </nav>
            </header>
            <div class="d-flex align-items-center justify-content-center my-5">
                <div class="container">
                    <div class="row row-cols-1 row-cols-lg-2 row-cols-xl-2">
                        <div class="col mx-auto">
                            <div class="card mt-5">
                                <div class="card-body">
                                    <div class="border p-4 rounded">
                                        <div class="text-center">
                                            <h3 class="">Рўйхатдан ўтиш</h3>
                                            <p>Электрон калитингиз борми? <a href="http://192.168.214.114:9080/esad/">ЕСАД орқали кириш</a>
                                            </p>
                                        </div>
                                        <div class="login-separater text-center mb-4"> <span>Тизимга кириш</span>
                                            <hr/>
                                        </div>
                                        <div class="form-body">
                                            <form:form modelAttribute="auth-signin" method="post"
                                                       cssClass="win3PartInfPartF needs-validation form-horizontal login-form"
                                                       action="${pageContext.request.contextPath}/user/auth/auth-signin"
                                                       id="signin" name="signin" novalidate="true">
                                            <div class="row g-3">
                                                <div class="col-12">
                                                    <label for="inputEmailAddress" class="form-label">Логин</label>
                                                    <form:input id="login_username" path="username" cssClass="form-control input-lg"
                                                                type="text" placeholder="Логин"/>
                                                </div>
                                                <div class="col-12">
                                                    <label for="inputChoosePassword" class="form-label">Парол</label>
                                                    <div class="input-group" id="show_hide_password">
                                                        <form:input id="login_password" path="password" cssClass="form-control border-end-0"
                                                                    type="password" placeholder="Парол"/>
                                                         <a href="javascript:;" class="input-group-text"><i class='bx bx-hide'></i></a>
                                                    </div>
                                                </div>

                                                <div class="col-12">
                                                    <div class="d-grid">
                                                        <button type="submit" class="btn btn-light"><i class='bx bx-user'></i>Кириш</button>
                                                    </div>
                                                </div>
                                            </div>
                                            </form:form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--end row-->
                </div>
            </div>
            <footer class="bg-dark shadow-sm p-2 text-center fixed-bottom">
                <p class="mb-0">2023 йил</p>
            </footer>
        </div>
        <!--end wrapper-->
        <!--start switcher-->
<%--        <div class="switcher-wrapper">--%>
<%--            <div class="switcher-btn"> <i class='bx bx-cog bx-spin'></i>--%>
<%--            </div>--%>
<%--            <div class="switcher-body">--%>
<%--                <div class="d-flex align-items-center">--%>
<%--                    <h5 class="mb-0 text-uppercase">Theme Customizer</h5>--%>
<%--                    <button type="button" class="btn-close ms-auto close-switcher" aria-label="Close"></button>--%>
<%--                </div>--%>
<%--                <hr/>--%>
<%--                <p class="mb-0">Gaussian Texture</p>--%>
<%--                <hr>--%>
<%--                <ul class="switcher">--%>
<%--                    <li id="theme1"></li>--%>
<%--                    <li id="theme2"></li>--%>
<%--                    <li id="theme3"></li>--%>
<%--                    <li id="theme4"></li>--%>
<%--                    <li id="theme5"></li>--%>
<%--                    <li id="theme6"></li>--%>
<%--                </ul>--%>
<%--                <hr>--%>
<%--                <p class="mb-0">Gradient Background</p>--%>
<%--                <hr>--%>
<%--                <ul class="switcher">--%>
<%--                    <li id="theme7"></li>--%>
<%--                    <li id="theme8"></li>--%>
<%--                    <li id="theme9"></li>--%>
<%--                    <li id="theme10"></li>--%>
<%--                    <li id="theme11"></li>--%>
<%--                    <li id="theme12"></li>--%>
<%--                    <li id="theme13"></li>--%>
<%--                    <li id="theme14"></li>--%>
<%--                    <li id="theme15"></li>--%>
<%--                </ul>--%>
<%--            </div>--%>
<%--            <div class="auth-wrapper align-items-stretch aut-bg-img">--%>
<%--                <div class="flex-grow-1">--%>
<%--                    <div class="auth-side-form">--%>
<%--                        <div class=" auth-content">--%>
<%--                            <div class="login">--%>
<%--                                <div class="login-container-wrapper clearfix">--%>
<%--                                    <div class="welcome"><strong>Тизимга хуш келибсиз!</strong></div>--%>
<%--                                    <form:form modelAttribute="auth-signin" method="post"--%>
<%--                                               cssClass="win3PartInfPartF needs-validation form-horizontal login-form"--%>
<%--                                               action="${pageContext.request.contextPath}/user/auth/auth-signin"--%>
<%--                                               id="signin" name="signin" novalidate="true">--%>
<%--                                        <div class="form-group relative">--%>
<%--                                            <form:input id="login_username" path="username" cssClass="form-control input-lg"--%>
<%--                                                        type="text" placeholder="Логин"/>--%>
<%--                                            <i class="fa fa-user"></i>--%>
<%--                                        </div>--%>
<%--                                        <div class="form-group relative password">--%>
<%--                                            <form:input id="login_password" path="password" cssClass="form-control input-lg"--%>
<%--                                                        type="password" placeholder="Парол"/>--%>
<%--                                            <i class="fa fa-lock"></i>--%>
<%--                                        </div>--%>
<%--                                        <div class="checkbox pull-left mb-2">--%>
<%--                                            <label><input type="checkbox"> Маълумотлар сақлансин.</label>--%>
<%--                                        </div>--%>
<%--                                        <div class="form-group">--%>
<%--                                            <button type="submit" class="btn btn-success btn-lg btn-block">Кириш</button>--%>
<%--                                        </div>--%>
<%--                                    </form:form>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
        <!--end switcher-->
        <!-- Bootstrap JS -->
        <script src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.bundle.min.js"></script>
        <!--plugins-->
        <script src="${pageContext.request.contextPath}/resources/assets/js/jquery.min.js"></script>
        <!--Password show & hide js -->
        <script>
            $(document).ready(function () {
                $("#show_hide_password a").on('click', function (event) {
                    event.preventDefault();
                    if ($('#show_hide_password input').attr("type") == "text") {
                        $('#show_hide_password input').attr('type', 'password');
                        $('#show_hide_password i').addClass("bx-hide");
                        $('#show_hide_password i').removeClass("bx-show");
                    } else if ($('#show_hide_password input').attr("type") == "password") {
                        $('#show_hide_password input').attr('type', 'text');
                        $('#show_hide_password i').removeClass("bx-hide");
                        $('#show_hide_password i').addClass("bx-show");
                    }
                });
            });
        </script>
        <script>
            $(".switcher-btn").on("click", function() {
                $(".switcher-wrapper").toggleClass("switcher-toggled")
            }), $(".close-switcher").on("click", function() {
                $(".switcher-wrapper").removeClass("switcher-toggled")
            }),


                $('#theme1').click(theme1);
            $('#theme2').click(theme2);
            $('#theme3').click(theme3);
            $('#theme4').click(theme4);
            $('#theme5').click(theme5);
            $('#theme6').click(theme6);
            $('#theme7').click(theme7);
            $('#theme8').click(theme8);
            $('#theme9').click(theme9);
            $('#theme10').click(theme10);
            $('#theme11').click(theme11);
            $('#theme12').click(theme12);
            $('#theme13').click(theme13);
            $('#theme14').click(theme14);
            $('#theme15').click(theme15);


            function theme1() {
                $('body').attr('class', 'bg-theme bg-theme1');
            }

            function theme2() {
                $('body').attr('class', 'bg-theme bg-theme2');
            }

            function theme3() {
                $('body').attr('class', 'bg-theme bg-theme3');
            }

            function theme4() {
                $('body').attr('class', 'bg-theme bg-theme4');
            }

            function theme5() {
                $('body').attr('class', 'bg-theme bg-theme5');
            }

            function theme6() {
                $('body').attr('class', 'bg-theme bg-theme6');
            }

            function theme7() {
                $('body').attr('class', 'bg-theme bg-theme7');
            }

            function theme8() {
                $('body').attr('class', 'bg-theme bg-theme8');
            }

            function theme9() {
                $('body').attr('class', 'bg-theme bg-theme9');
            }

            function theme10() {
                $('body').attr('class', 'bg-theme bg-theme10');
            }

            function theme11() {
                $('body').attr('class', 'bg-theme bg-theme11');
            }

            function theme12() {
                $('body').attr('class', 'bg-theme bg-theme12');
            }

            function theme13() {
                $('body').attr('class', 'bg-theme bg-theme13');
            }

            function theme14() {
                $('body').attr('class', 'bg-theme bg-theme14');
            }

            function theme15() {
                $('body').attr('class', 'bg-theme bg-theme15');
            }
        </script>
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