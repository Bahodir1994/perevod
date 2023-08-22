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
    <title>Fixed top navbar example · Bootstrap v5.3</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.3/examples/navbar-fixed/">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@docsearch/css@3">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<%--    <link href="${pageContext.servletContext.contextPath}/resources/assets/css/jquery.dataTables.min.css" rel="stylesheet">--%>
    <link href="${pageContext.servletContext.contextPath}/resources/assets/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/responsive/2.5.0/css/responsive.dataTables.min.css">
    <link href="https://cdn.datatables.net/responsive/2.5.0/css/responsive.dataTables.min.css">

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

    </style>
    <!-- Custom styles for this template -->
    <link href="${pageContext.servletContext.contextPath}/resources/assets/css/navbar-fixed.css" rel="stylesheet">
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


<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Pul o'tkazma</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav me-auto mb-2 mb-md-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">AdminPanel</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Xisobot</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Yangiliklar</a>
                </li>
            </ul>
            <form class="d-flex" role="search">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success" type="submit"></button>
            </form>
        </div>
    </div>
</nav>

<main class="container">
    <div class="row g-5">
        <div class="col-md-5 col-lg-4 order-md-last">
            <h4 class="d-flex justify-content-between align-items-center mb-3">
                <span class="text-primary">Xisob raqam</span>
                <span class="text-danger">Qarz <span class="badge bg-primary rounded-pill">3</span></span>
            </h4>
            <ul class="list-group mb-3">
                <li class="list-group-item d-flex justify-content-between lh-sm">
                    <div>
                        <h6 class="my-0 text-warning">Xisobda mavjud</h6>
                        <small class="text-body-secondary">(UZS) O'zbek so'mi</small>
                    </div>
                    <span class="text-body-secondary">55 000 000</span>
                </li>
                <li class="list-group-item d-flex justify-content-between lh-sm">
                    <div>
                        <h6 class="my-0 text-warning">Aylanma mablag'</h6>
                        <small class="text-body-secondary">(UZS) O'zbek so'mi</small>
                    </div>
                    <span class="text-body-secondary">24 000 000</span>
                </li>
                <li class="list-group-item d-flex justify-content-between lh-sm">
                    <div>
                        <h6 class="my-0 text-success">Xisobda mavjud</h6>
                        <small class="text-body-secondary">(USD) Aqsh dollari</small>
                    </div>
                    <span class="text-body-secondary text-success">8 000</span>
                </li>
                <li class="list-group-item d-flex justify-content-between">
                    <div class="text-success">
                        <h6 class="my-0">Aylanma mablag'</h6>
                        <small>(USD) Aqsh dollari</small>
                    </div>
                    <span class="text-success">5 000</span>
                </li>
                <li class="list-group-item d-flex justify-content-between">
                    <div>
                        <h6 class="my-0 text-success">Kunlik start:</h6>
                    </div>
                    <span>
                        <strong>70 000 000 000 (UZS)</strong>
                    </span>

                    <span>
                        <strong>18 000 (USD)</strong>
                    </span>
                </li>
            </ul>

            <form class="d-flex justify-content-between p-2">
                    <button type="submit" class="btn btn-secondary w-100 mx-2">Calculator</button>
                    <button type="submit" class="btn btn-secondary w-100 mx-2">Ishni yakunlash</button>
            </form>
        </div>
        <div class="col-md-7 col-lg-8">
            <h4 class="mb-3">Tranzaksiya</h4>
            <form class="needs-validation" novalidate>
                <div class="row g-3">
                    <div class="col-sm-6">
                        <label for="fullName" class="form-label">F.I.O</label>
                        <input type="text" class="form-control fullName" id="fullName" required>
                        <div class="invalid-feedback">
                            F.I.O to'ldirilmagan!
                        </div>
                    </div>

                    <div class="col-sm-6">
                        <label for="telNumber" class="form-label">Tel.</label>
                        <input type="text" class="form-control telNumber" id="telNumber" required>
                        <div class="invalid-feedback">
                            Tel raqam to'ldirilmagan!
                        </div>
                    </div>

                    <div class="col-sm-6">
                        <label for="moneyCost" class="form-label">Tranzaksiya miqdori</label>
                        <div class="input-group has-validation">
                            <span class="input-group-text currencyIcon">@</span>
                            <input type="text" class="form-control currency-mask moneyCost" id="moneyCost" required>
                            <div class="invalid-feedback">
                                Qiymat kiritilmagan!
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <label for="moneyType" class="form-label">Valyuta</label>
                        <select class="form-select moneyType" id="moneyType" required>
                            <option value="uzs">So'm (uzs)</option>
                            <option value="usd">Dollar (usd)</option>
                        </select>
                        <div class="invalid-feedback">
                            Valyuta tanlanmagan!
                        </div>
                    </div>

                    <div class="col-sm-6">
                        <label for="serviceMoney" class="form-label">Xizmat uchun</label>
                        <div class="input-group has-validation">
                            <span class="input-group-text">uzs</span>
                            <input type="text" class="form-control currency-mask serviceMoney" id="serviceMoney" required>
                            <div class="invalid-feedback">
                                Xizmat puli to'ldirilmagan!
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6">
                        <label for="sendToAddress" class="form-label">Jo'natma manzili</label>
                        <select class="form-select sendToAddress" id="sendToAddress" required>
                            <option value="01-toshkent">Toshkent</option>
                            <option value="95-mangit">Mangit</option>
                        </select>
                        <div class="invalid-feedback">
                            Jo'natma manzili tanlanmagan!
                        </div>
                    </div>

                    <div class="col-12">
                        <label for="comment" class="form-label comment">Izox</label>
                        <input type="text" class="form-control" id="comment">
                    </div>

                </div>

                <div class="row py-2">
                    <div class="col-sm-6">
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input isDebt" id="isDebt">
                            <label class="form-check-label" for="isDebt">Qarzga berilmoqda</label>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <button class="btn btn-primary w-75" type="submit">Saqlash</button>
                    </div>
                </div>
                <hr class="my-2">
            </form>
        </div>
    </div>
    <div class="">
        <table id="datatable1" class="table table-striped table-responsive  table-hover" style="width: 100%!important;">
        </table>
    </div>
</main>
<script src="${pageContext.servletContext.contextPath}/resources/assets/js/color-modes.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/assets/js/jquery-3.7.0.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/assets/js/dataTables.bootstrap5.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.5.0/js/dataTables.responsive.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/assets/js/jquery.spring-friendly.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<script src="${pageContext.servletContext.contextPath}/resources/assets/data/imask/dist/imask.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/assets/custom/custom.js"></script>
<script>
    $(document).ready(function () {
    })


    var dt1 = $('#datatable1').DataTable({
        // retrieve: false,
        // deferLoading: true,
        paging: true,
        scrollCollapse: true,
        processing: true,
        responsive: true,
        ajax: {
            url: '${pageContext.servletContext.contextPath}/route_v1/dataV1',
            type: 'GET'
        },
        lengthMenu: [
            [10, 25, 50, -1],
            [10, 25, 50, 'Xammasi']
        ],
        serverSide: true,
        dom: '<"row"<"col-md-12 d-flex justify-content-center"f>>' +
            '<"row"<"col-sm-12"t>>' +
            '<"row"<"col-sm-12 d-flex justify-content-center"pr>>' +
            '<"row"<"col-sm-3 mt-2"l><"col-sm-9 text-end mt-2"i>>',
        lengthMenu: [[10, 50, 100, -1], [10, 50, 100, 'Барчаси']],
        columns: [
            {title: '№', className: 'text-center', sortable: false, searchable: false, orderable: false, name: 'column0', data: null, render: function (data, type, row, meta) {return meta.row + meta.settings._iDisplayStart + 1 +'.'}},
            {title: 'F.I.O', className: 'text-left', name: 'column1', data: 'fullName'},
            {title: 'Summa', className: 'text-center', name: 'column2', data: 'id', render: (_, __, row) => {
                    return row.paymentCost + ' (' + row.paymentCostType + ')'
            }},
            {title: 'Tel', className: 'text-left', name: 'column3', data: 'phone'},
            {title: 'Jo\'natilgan vaqt', className: 'text-left', name: 'column4', data: 'outTime'},
            {title: 'Qabul qilingan vaqt', className: 'text-left', name: 'column5', data: 'inTime'},
            {title: 'Qarzdor', className: 'text-left', name: 'column6', data: 'debt'},
            {title: 'Xizmat', className: 'text-left', name: 'column6', data: 'serviceUzs'},
            {title: 'Izox', className: 'text-left', name: 'column6', data: 'comment'},
        ],
        language: {url: '${pageContext.servletContext.contextPath}/resources/assets/json/package_uz.json'},
    });
</script>
</body>
</html>

