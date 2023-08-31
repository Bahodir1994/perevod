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
<html>
    <head>
        <title>Admin Panel</title>
    </head>
    <body>
        <div class="row">
            <div class="col-md-12 col-lg-12">
                <h4 class="mb-3">Kassaga mablag' qo'shish</h4>
                <form class="needs-validation" novalidate>

                    <div class="row g-3">
                        <div class="col-sm-6">
                            <label for="moneyCostUzs" class="form-label">(UZS) miqdori</label>
                            <div class="input-group has-validation">
                                <span class="input-group-text currencyIcon">uzs</span>
                                <input type="text" class="form-control format-currency currency-mask moneyCostUzs" id="moneyCostUzs" required>
                                <div class="invalid-feedback">
                                    (UZS) to'ldirilmagan yoki xato!
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6">
                            <label for="moneyCostUsd" class="form-label">(USD) miqdori</label>
                            <div class="input-group has-validation">
                                <span class="input-group-text currencyIcon">usd</span>
                                <input type="text" class="form-control format-currency currency-mask moneyCostUsd" id="moneyCostUsd" required>
                                <div class="invalid-feedback">
                                    (USD) to'ldirilmagan yoki xato!
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6">
                            <label for="cashRegister" class="form-label">Xududiy kassa</label>
                            <select class="form-select cashRegister" id="cashRegister" required>
                                <option value="01">Toshkent</option>
                                <option value="95">Mang`it</option>
                            </select>
                            <div class="invalid-feedback">
                                Xududiy kassa tanlanmagan!
                            </div>
                        </div>

                        <div class="col-sm-12 d-flex justify-content-center">
                            <div class="form-check mx-2">
                                <input type="checkbox" class="form-check-input minusUzs" id="minusUzs" required>
                                <label class="form-check-label" for="minusUzs">(UZS) ayrish</label>
                            </div>
                            <div class="form-check mx-2">
                                <input type="checkbox" class="form-check-input minusUsd" id="minusUsd" required>
                                <label class="form-check-label" for="minusUsd">(USD) ayrish</label>
                            </div>
                        </div>
                        <div class="col-12">
                            <label for="comment" class="form-label">Izox</label>
                            <input type="text" class="form-control comment" id="comment" required>
                        </div>
                    </div>

                    <div class="row py-2">
                        <div class="col-sm-12 d-flex justify-content-between">
                            <button class="btn btn-primary w-100 mx-2" onclick="page_admin_funcV1_01()" type="button"><i class="bi bi-download"></i> Saqlash</button>
                            <button class="btn btn-success w-100 mx-2" onclick="clear_form1_2()" type="button"><i class="bi bi-arrow-clockwise"></i> Yangilash</button>
                        </div>
                    </div>
                    <hr class="my-2">
                    <h3 class="text-body-emphasis">Xududiy kassa holati</h3>
                    <hr class="col-12 col-md-12 mb-2">
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
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <script src="${pageContext.servletContext.contextPath}/resources/assets/custom/custom.js"></script>
        <script>
            $(document).ready(function () {
                page_admin_funcV1_02()
            })

            $('.cashRegister').on('change', function () {
                page_admin_funcV1_02()
            })

            function page_admin_funcV1_01() {
                /**Kirimni saqlash**/
                var params_page_admin_funcV1_01 = {
                    "moneyCostUzs" : $('.moneyCostUzs').val().replace(/\s/g, ''),
                    "moneyCostUsd" : $('.moneyCostUsd').val().replace(/\s/g, ''),
                    "minusUzs" : $('.minusUzs').prop("checked"),
                    "minusUsd" : $('.minusUsd').prop("checked"),
                    "cashRegister" : $('.cashRegister').val(),
                    "comment" : $('.comment').val(),
                };

                Swal.fire({
                    title: $('.sendToAddress').text() + " Kassaga mablag' kiritishni tasdiqlaysizmi?",
                    showDenyButton: true,
                    confirmButtonText: 'Xa',
                    denyButtonText: `Yo'q`,
                }).then((result) => {
                    if (result.isConfirmed) {
                        $.ajax({
                            type: "POST",
                            url: '${pageContext.servletContext.contextPath}/route_v3/data_v2/update_cash',
                            data: JSON.stringify(params_page_admin_funcV1_01),
                            dataType: "json",
                            async: true,
                            contentType: 'application/json',
                            beforeSend: function(xhr) {},
                            complete: function (xhr, status, error) {
                                if (xhr.status === 400) {
                                    Swal.fire({
                                        position: 'center',
                                        icon: 'error',
                                        title: 'Ma\'lumotlarda xatolik bor!',
                                        showConfirmButton: false,
                                        timer: 1500
                                    })
                                    handleValidationErrors(xhr);
                                }
                                else if (xhr.status === 200) {
                                    if (!xhr.responseJSON.success){
                                        Swal.fire({
                                            position: 'center',
                                            icon: 'info',
                                            title: 'Taqiqlangan!',
                                            text: xhr.responseJSON.message,
                                            showConfirmButton: false,
                                            timer: 2000
                                        });
                                    }else {
                                        Swal.fire({
                                            position: 'center',
                                            icon: 'success',
                                            title: 'Saqlandi!',
                                            showConfirmButton: false,
                                            timer: 1500
                                        })
                                        page_admin_funcV1_02();
                                        handleValidationErrors(xhr);
                                        clear_form1_2()
                                    }
                                }
                                else {
                                    Swal.fire('Ko\'zda tutilmagan xatolik!', '', 'info')
                                }
                            }
                        });
                    } else if (result.isDenied) {
                        // only close modal
                        // Swal.fire('Saqlash bekor qilindi!', '', 'info')
                    }
                })
            }
            function page_admin_funcV1_02() {
                var formattedNumberUzs = 0.00;
                var formattedNumberUsd = 0.00;
                var params = {
                    "locationId" : $('.cashRegister').val()
                }
                $.ajax({
                    type: "GET",
                    url: "${pageContext.servletContext.contextPath}/route_v3/data_v3/chash_status_by_loc",
                    data: params,
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

            function handleValidationErrors(errors) {
                /**Kirim ma'lumotlari validatsiyasi**/
                ![undefined, null, ''].includes(errors.responseJSON.moneyCostUzs) ? $('.moneyCostUzs').addClass('is-invalid') : $('.moneyCostUzs').removeClass('is-invalid');
                ![undefined, null, ''].includes(errors.responseJSON.moneyCostUsd) ? $('.moneyCostUsd').addClass('is-invalid') : $('.moneyCostUsd').removeClass('is-invalid');
                ![undefined, null, ''].includes(errors.responseJSON.minusUzs) ? $('.minusUzs').addClass('is-invalid') : $('.minusUzs').removeClass('is-invalid');
                ![undefined, null, ''].includes(errors.responseJSON.minusUsd) ? $('.minusUsd').addClass('is-invalid') : $('.minusUsd').removeClass('is-invalid');
                ![undefined, null, ''].includes(errors.responseJSON.cashRegister) ? $('.cashRegister').addClass('is-invalid') : $('.cashRegister').removeClass('is-invalid');
                ![undefined, null, ''].includes(errors.responseJSON.comment) ? $('.comment').addClass('is-invalid') : $('.comment').removeClass('is-invalid');
            }
            function clear_form1_2() {
                $('.moneyCostUzs').removeClass('is-invalid').val('');
                $('.moneyCostUsd').removeClass('is-invalid').val('');
                $('.minusUzs').removeClass('is-invalid').prop('checked', false);
                $('.minusUsd').removeClass('is-invalid').prop('checked', false);
                $('.cashRegister').removeClass('is-invalid');
                $('.comment').removeClass('is-invalid');
            }
        </script>
    </body>
</html>
