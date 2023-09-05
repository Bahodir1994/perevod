<%--
  Created by IntelliJ IDEA.
  User: Bahodir
  Date: 23.08.2023
  Time: 12:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Ishni boshlash</title>
    </head>
    <body>
        <div>
            <h1 class="text-body-emphasis">Kunlik boshlang'ich mablag'ni tasdiqlash</h1>
            <hr class="col-12 col-md-12 mb-5">
            <div class="row row-cols-1 row-cols-md-1 mb-3 text-center">
                <div class="col">
                    <div class="card mb-4 rounded-3 border-warning shadow-sm">
                        <div class="card-header py-3">
                            <h4 class="my-0 fw-normal text-warning">Hisobda mavjud</h4>
                        </div>
                        <div class="card-body">
                            <h2 class="card-title pricing-card-title currency-mask"><span class="currencyDisplaySom"></span><small class="text-body-secondary fw-light">/ uzs (so'm)</small></h2>
                            <h2 class="card-title pricing-card-title currency-mask"><span class="currencyDisplayDollar"></span><small class="text-body-secondary fw-light">/ usd (dollar)</small></h2>
                            <ul class="list-unstyled mt-3 mb-4">
                                <li>Kassa: <span class="text-success region"></span></li>
                               <i class="bi-timer"></i> <span class="timerAndDate">00:00:00</span>
                            </ul>
                            <button type="button" class="w-100 btn btn-lg btn-outline-primary" onclick="job_start_funcV1_02()">Ishni boshlash</button>
                        </div>
                    </div>
                </div>
            </div>

            <hr class="col-3 col-md-2 mb-3">

            <div class="row">
                <div class="col-md-6">
                    <h2 class="text-body-emphasis">Ishni boshlash</h2>
                    <p>Sizning hisobingizda (hududiy kassada) mablag' bo'lishi lozim. Aks holda ishni boshlay olmaysiz, hisobni to'ldirish adminga murojaat qiling.</p>
                </div>
            </div>
        </div>
    <script>
        $(document).ready(function (data, key) {
            job_start_funcV1_01()
        })

        function job_start_funcV1_01() {
            var formattedNumberUzs = 0.00;
            var formattedNumberUsd = 0.00;
            $.ajax({
                type: "GET",
                url: "${pageContext.servletContext.contextPath}/route_v1/data_v2/job_page_data",
                beforeSend: function () {
                },
                accept: function () {
                    $('.currencyDisplayDollar').text(0.00)
                    $('.currencyDisplaySom').text(0.00)
                },
                success: function (response) {
                    formattedNumberUzs = formatNumberWithThousandsSeparator(response.totalUzs);
                    formattedNumberUsd = formatNumberWithThousandsSeparator(response.totalUsd);
                    $('.currencyDisplaySom').text(formattedNumberUzs)
                    $('.currencyDisplayDollar').text(formattedNumberUsd)
                    $('.region').text(response.insLocationName + ';  kod: ' + response.insLocationCode)
                },
                error: function () {
                    $('.currencyDisplayDollar').text(0.00)
                    $('.currencyDisplaySom').text(0.00)
                }
            });
        }
        function job_start_funcV1_02() {
            $.ajax({
                type: "GET",
                url: "${pageContext.servletContext.contextPath}/route_v1/data_v3/job_page_starting",
                beforeSend: function () {
                },
                success: function (response) {
                    if (response.success){
                        $(location).attr('href', '${pageContext.servletContext.contextPath}/');
                    }else {
                        Swal.fire({
                            position: 'center',
                            icon: 'info',
                            title: response.message,
                            showConfirmButton: false,
                            timer: 1500
                        })
                    }
                },
                error: function () {
                    alert("xatolik!")
                }
            });
        }
    </script>
    </body>
</html>
