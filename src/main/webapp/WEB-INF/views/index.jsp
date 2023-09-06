<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html lang="en" data-bs-theme="auto">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
        <meta name="generator" content="Hugo 0.115.4">
        <title>Pul o'zkazmalari</title>

<%--        <link href="${pageContext.servletContext.contextPath}/resources/assets/css/jquery.dataTables.min.css" rel="stylesheet">--%>
        <link href="${pageContext.servletContext.contextPath}/resources/assets/css/navbar-fixed.css" rel="stylesheet">

        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/css/app.css">
        <link href="${pageContext.servletContext.contextPath}/resources/assets/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <link href="${pageContext.servletContext.contextPath}/resources/assets/css/dropdowns.css" rel="stylesheet">

        <link href="${pageContext.servletContext.contextPath}/resources/assets/css/dataTables.bootstrap4.min.css" rel="stylesheet">
        <link href="${pageContext.servletContext.contextPath}/resources/assets/css/responsive.bootstrap4.min.css" rel="stylesheet">
        <link href="${pageContext.servletContext.contextPath}/resources/assets/css/fixedHeader.bootstrap4.min.css" rel="stylesheet">
        <link href="${pageContext.servletContext.contextPath}/resources/assets/css/select.dataTables.min.css" rel="stylesheet">
        <link href="${pageContext.servletContext.contextPath}/resources/assets/css/buttons.dataTables.min.css" rel="stylesheet">
        <link href="${pageContext.servletContext.contextPath}/resources/assets/css/dataTables.dateTime.min.css" rel="stylesheet">
        <link href="${pageContext.servletContext.contextPath}/resources/assets/css/sweetalert2.min.css" rel="stylesheet">

        <style>
            .bd-placeholder-img {
                font-size: 1.125rem;
                text-anchor: middle;
                -webkit-user-select: none;
                -moz-user-select: none;
                user-select: none;
            }

            @media (min-width: 768px) {
                .bd-placeholder-img-lg {
                    font-size: 3.5rem;
                }
            }

            .b-example-divider {
                width: 100%;
                height: 3rem;
                background-color: rgba(0, 0, 0, .1);
                border: solid rgba(0, 0, 0, .15);
                border-width: 1px 0;
                box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
            }

            .b-example-vr {
                flex-shrink: 0;
                width: 1.5rem;
                height: 100vh;
            }

            .bi {
                vertical-align: -.125em;
                fill: currentColor;
            }

            .nav-scroller {
                position: relative;
                z-index: 2;
                height: 2.75rem;
                overflow-y: hidden;
            }

            .nav-scroller .nav {
                display: flex;
                flex-wrap: nowrap;
                padding-bottom: 1rem;
                margin-top: -1px;
                overflow-x: auto;
                text-align: center;
                white-space: nowrap;
                -webkit-overflow-scrolling: touch;
            }

            .btn-bd-primary {
                --bd-violet-bg: #712cf9;
                --bd-violet-rgb: 112.520718, 44.062154, 249.437846;

                --bs-btn-font-weight: 600;
                --bs-btn-color: var(--bs-white);
                --bs-btn-bg: var(--bd-violet-bg);
                --bs-btn-border-color: var(--bd-violet-bg);
                --bs-btn-hover-color: var(--bs-white);
                --bs-btn-hover-bg: #6528e0;
                --bs-btn-hover-border-color: #6528e0;
                --bs-btn-focus-shadow-rgb: var(--bd-violet-rgb);
                --bs-btn-active-color: var(--bs-btn-hover-color);
                --bs-btn-active-bg: #5a23c8;
                --bs-btn-active-border-color: #5a23c8;
            }
            .bd-mode-toggle {
                z-index: 1500;
            }

            .table.dataTable[class*="table-"] thead th {
                background: rgba(60, 148, 232, 0.66) !important;
                height: 5px !important;
                vertical-align: middle!important;
                color: #000!important;
                font-size: 14px!important;
            }

        </style>
    </head>
    <body>
    <svg xmlns="http://www.w3.org/2000/svg" class="d-none">
        <symbol id="check2" viewBox="0 0 16 16">
            <path d="M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z"/>
        </symbol>
        <symbol id="circle-half" viewBox="0 0 16 16">
            <path d="M8 15A7 7 0 1 0 8 1v14zm0 1A8 8 0 1 1 8 0a8 8 0 0 1 0 16z"/>
        </symbol>
        <symbol id="moon-stars-fill" viewBox="0 0 16 16">
            <path d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278z"/>
            <path d="M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z"/>
        </symbol>
        <symbol id="sun-fill" viewBox="0 0 16 16">
            <path d="M8 12a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z"/>
        </symbol>
    </svg>
    <div class="dropdown position-fixed bottom-0 end-0 mb-3 me-3 bd-mode-toggle">
        <button class="btn btn-bd-primary py-2 dropdown-toggle d-flex align-items-center"
                id="bd-theme"
                type="button"
                aria-expanded="false"
                data-bs-toggle="dropdown"
                aria-label="Toggle theme (auto)">
            <svg class="bi my-1 theme-icon-active" width="1em" height="1em"><use href="#circle-half"></use></svg>
            <span class="visually-hidden" id="bd-theme-text">Toggle theme</span>
        </button>
        <ul class="dropdown-menu dropdown-menu-end shadow" aria-labelledby="bd-theme-text">
            <li>
                <button type="button" class="dropdown-item d-flex align-items-center" data-bs-theme-value="light" aria-pressed="false">
                    <svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em"><use href="#sun-fill"></use></svg>
                    Light
                    <svg class="bi ms-auto d-none" width="1em" height="1em"><use href="#check2"></use></svg>
                </button>
            </li>
            <li>
                <button type="button" class="dropdown-item d-flex align-items-center" data-bs-theme-value="dark" aria-pressed="false">
                    <svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em"><use href="#moon-stars-fill"></use></svg>
                    Dark
                    <svg class="bi ms-auto d-none" width="1em" height="1em"><use href="#check2"></use></svg>
                </button>
            </li>
            <li>
                <button type="button" class="dropdown-item d-flex align-items-center active" data-bs-theme-value="auto" aria-pressed="true">
                    <svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em"><use href="#circle-half"></use></svg>
                    Auto
                    <svg class="bi ms-auto d-none" width="1em" height="1em"><use href="#check2"></use></svg>
                </button>
            </li>
        </ul>
    </div>

    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-black border-dark-subtle shadow">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.servletContext.contextPath}/">Pul o'tkazma</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="navbar-nav me-auto mb-2 mb-md-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" onclick="app_funcV1_02()" href="#">AdminPanel</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" onclick="app_funcV1_03()" href="#">Qarzlar</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Hisobot</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" onclick="informer1()" href="#">Yangiliklar</a>
                    </li>
                </ul>
                <div class="btn-group">
                    <button type="button" class="btn btn-outline-primary dropdown-toggle" data-bs-toggle="dropdown" data-bs-display="static" aria-expanded="false">
                        <img src="https://tpksklad.ru/static/img/avatar-2.png" alt="mdo" width="32" height="32" class="rounded-circle text-warning"> ${user.fullName}
                    </button>
                    <ul class="dropdown-menu dropdown-menu-lg-end shadow-lg border border-primary">
                        <li>
                            <a class="dropdown-item d-flex gap-2 align-items-center" href="#">
                                <i class="bi bi-person-vcard-fill"></i>
                                ${user.fullName}
                            </a>
                        </li>
                        <li><hr class="dropdown-divider"></li>
                        <li>
                            <a class="dropdown-item d-flex gap-2 align-items-center" href="#">
                                <i class="bi bi-people-fill"></i>
                                Rollar
                            </a>
                        </li>
                        <li><hr class="dropdown-divider"></li>
                        <li>
                            <a class="dropdown-item dropdown-item-danger d-flex gap-2 align-items-center" href="${pageContext.servletContext.contextPath}/logout">
                                <i class="bi bi-box-arrow-in-left"></i>
                                Chiqish
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>

    <main class="container" id="mainApps">
        <!--Content put here-->
    </main>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.1/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@7.0.0/bundles/stomp.umd.min.js"></script>

    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/color-modes.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/jquery-3.7.0.js"></script>

    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/dataTables.bootstrap4.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/dataTables.responsive.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/dataTables.fixedHeader.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/dataTables.colReorder.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/dataTables.select.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/dataTables.buttons.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/dataTables.dateTime.min.js"></script>

    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/jquery.spring-friendly.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/data/imask/dist/imask.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/js/sweetalert2.all.min.js"></script>
    <script src="${pageContext.servletContext.contextPath}/resources/assets/custom/custom.js"></script>
    <script>
        const userLocation = (${userLocation});

        $(document).ready(function () {
            app_funcV1_01();
            connect()
        })
        function app_funcV1_01() {
            $.ajax({
                type: "GET",
                url: "${pageContext.servletContext.contextPath}/route_v1/data_v1/job_status",
                beforeSend: function () {
                },
                accept: function () {
                },
                success: function (response) {
                    $("#mainApps").html(response);
                },
                error: function () {
                }
            });
        }
        function app_funcV1_02() {
            $.ajax({
                type: "GET",
                url: "${pageContext.servletContext.contextPath}/route_v3/data_v1/admin_panel",
                beforeSend: function () {
                },
                complete: function (xhr, status, error) {
                    if (xhr.status === 400) {
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            title: 'Ma\'lumotlarda xatolik bor!',
                            showConfirmButton: false,
                            timer: 1500
                        })
                    }
                    else if (xhr.status === 200) {
                        Swal.fire({
                            position: 'top-end',
                            icon: 'success',
                            title: 'Admin oynasi',
                            showConfirmButton: false,
                            timer: 1500
                        });
                        $("#mainApps").html(xhr.responseText);
                    }
                    else if (xhr.status === 403){
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            title: 'Taqiqlangan!',
                            showConfirmButton: false,
                            timer: 1500
                        })
                    }
                },
            });
        }
        function app_funcV1_03() {
            $.ajax({
                type: "GET",
                url: "${pageContext.servletContext.contextPath}/route_v4/data_v1/debt_page",
                beforeSend: function () {
                },
                complete: function (xhr, status, error) {
                    if (xhr.status === 400) {
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            title: 'Ma\'lumotlarda xatolik bor!',
                            showConfirmButton: false,
                            timer: 1500
                        })
                    }
                    else if (xhr.status === 200) {
                        Swal.fire({
                            position: 'top-end',
                            icon: 'success',
                            title: 'Qarzdorliklar oynasi',
                            showConfirmButton: false,
                            timer: 1500
                        });
                        $("#mainApps").html(xhr.responseText);
                    }
                    else if (xhr.status === 403){
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            title: 'Taqiqlangan!',
                            showConfirmButton: false,
                            timer: 1500
                        })
                    }
                },
            });
        }

        function informer1() {
            Swal.fire({
                title: '<strong>Markaziy bank <u>informeri</u></strong>',
                icon: 'info',
                html: '<a href="https://cbu.uz/" target="_blank" title="Ўзбекистон Республикаси Марказий банки"><img src="https://cbu.uz/oz/informer/?txtclr=212121&brdclr=FFC700&bgclr=FFE27D&r_choose=USD_EUR_RUB" alt=""></a>',
                showCloseButton: true,
                showCancelButton: true,
                focusConfirm: false,
                confirmButtonText:
                    '<i class="bi bi-hand-thumbs-up"></i> Zo\'r!',
                confirmButtonAriaLabel: 'Thumbs up, great!',
                cancelButtonText:
                    '<i class="bi bi-hand-thumbs-down"></i>',
                cancelButtonAriaLabel: 'Thumbs down'
            })
        }
    </script>
    <script>
        var currentURL = window.location.href.replace(/^https?:\/\//, '');
        var port = window.location.port;

        var urls = 'wss://'+currentURL.replace('?', '')+'gs-guide-websocket'
        if (currentURL.indexOf("localhost") !== -1) {
            urls = 'ws://'+currentURL.replace('?', '')+'gs-guide-websocket'
        }

        console.log("full url--> "+urls)
        const stompClient = new StompJs.Client({
            brokerURL: urls
        });

        <%--var socket = new SockJS('${pageContext.servletContext.contextPath}/gs-guide-websocket');--%>
        <%--var stompClient = Stomp.over(socket);--%>

        stompClient.onConnect = (frame) => {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('${pageContext.servletContext.contextPath}/topic/greetings', (greeting) => {
                showGreeting(JSON.parse(greeting.body).content);
            });
        };

        stompClient.onWebSocketError = (error) => {
            console.error('Error with websocket', error);
        };

        stompClient.onStompError = (frame) => {
            console.error('Broker reported error: ' + frame.headers['message']);
            console.error('Additional details: ' + frame.body);
        };

        function setConnected(connected) {
            $("#connect").prop("disabled", connected);
            $("#disconnect").prop("disabled", !connected);
            if (connected) {
                $("#conversation").show();
            }
            else {
                $("#conversation").hide();
            }
            $("#greetings").html("");
        }

        function connect() {
            stompClient.activate();
        }

        function disconnect() {
            stompClient.deactivate();
            setConnected(false);
            console.log("Disconnected");
        }

        function sendName() {
            stompClient.publish({
                destination: "${pageContext.servletContext.contextPath}/app/hello",
                body: JSON.stringify({'name': 'update-success'})
            });
        }

        function showGreeting(message) {
            if (message === '1') { //success
                if (typeof dt1 !== 'undefined' && dt1 !== null && typeof dt1.draw === 'function') {
                    dt1.draw()
                }
                if (typeof dtD1 !== 'undefined' && dtD1 !== null && typeof dtD1.draw === 'function') {
                    dtD1.draw();
                }
                // Повторите аналогичную проверку для dtD2 и job_start_funcV1_01
                if (typeof dtD2 !== 'undefined' && dtD2 !== null && typeof dtD2.draw === 'function') {
                    dtD2.draw();
                }
                if (typeof job_start_funcV1_01 === 'function') {
                    job_start_funcV1_01();
                }
                if (typeof debt_funcV1_01 === 'function') {
                    var checkStatus = $('.seeAll').prop('checked');
                    debt_funcV1_01(checkStatus);
                }
            }
        }

        $(function () {
            $( "#connect" ).click(() => connect());
            $( "#disconnect" ).click(() => disconnect());
        });
    </script>
    </body>
</html>

