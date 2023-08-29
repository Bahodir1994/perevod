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
                                <option value="95">Mangit</option>
                            </select>
                            <div class="invalid-feedback">
                                Xududiy kassa tanlanmagan!
                            </div>
                        </div>

                        <div class="col-sm-12 d-flex justify-content-center">
                            <div class="form-check mx-2">
                                <input type="checkbox" class="form-check-input noChangeUzs" id="noChangeUzs" required>
                                <label class="form-check-label" for="noChangeUzs">(UZS) ayrish</label>
                            </div>
                            <div class="form-check mx-2">
                                <input type="checkbox" class="form-check-input noChangeUsd" id="noChangeUsd" required>
                                <label class="form-check-label" for="noChangeUsd">(USD) ayrish</label>
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
                </form>
            </div>
        </div>
        <script src="${pageContext.servletContext.contextPath}/resources/assets/custom/custom.js"></script>
        <script>
            function page_admin_funcV1_01() {
                /**Kirimni saqlash**/
                var params_page_admin_funcV1_01 = {
                    "moneyCostUzs" : $('.moneyCostUzs').val().replace(/\s/g, ''),
                    "moneyCostUsd" : $('.moneyCostUsd').val().replace(/\s/g, ''),
                    "noChangeUzs" : $('.noChangeUzs').prop("checked"),
                    "noChangeUsd" : $('.noChangeUsd').prop("checked"),
                    "cashRegister" : $('.cashRegister').val(),
                    "comment" : $('.comment').val(),
                };

                Swal.fire({
                    title: $('.sendToAddress').text() + ' кассага маблағ киритишни тасдиқлайсизми?',
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
                                    Swal.fire({
                                        position: 'center',
                                        icon: 'success',
                                        title: 'Saqlandi!',
                                        showConfirmButton: false,
                                        timer: 1500
                                    })
                                    handleValidationErrors(xhr);
                                    clear_form1_1()
                                    job_start_funcV1_01()
                                    dt1.draw();
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

            function handleValidationErrors(errors) {
                /**Kirim ma'lumotlari validatsiyasi**/
                ![undefined, null, ''].includes(errors.responseJSON.moneyCostUzs) ? $('.moneyCostUzs').addClass('is-invalid') : $('.moneyCostUzs').removeClass('is-invalid');
                ![undefined, null, ''].includes(errors.responseJSON.moneyCostUsd) ? $('.moneyCostUsd').addClass('is-invalid') : $('.moneyCostUsd').removeClass('is-invalid');
                ![undefined, null, ''].includes(errors.responseJSON.noChangeUzs) ? $('.noChangeUzs').addClass('is-invalid') : $('.noChangeUzs').removeClass('is-invalid');
                ![undefined, null, ''].includes(errors.responseJSON.noChangeUsd) ? $('.noChangeUsd').addClass('is-invalid') : $('.noChangeUsd').removeClass('is-invalid');
                ![undefined, null, ''].includes(errors.responseJSON.cashRegister) ? $('.cashRegister').addClass('is-invalid') : $('.cashRegister').removeClass('is-invalid');
                ![undefined, null, ''].includes(errors.responseJSON.comment) ? $('.comment').addClass('is-invalid') : $('.comment').removeClass('is-invalid');
            }
            function clear_form1_2() {
                $('.moneyCostUzs').removeClass('is-invalid').val('');
                $('.moneyCostUsd').removeClass('is-invalid').val('');
                $('.noChangeUzs').removeClass('is-invalid').prop('checked', false);
                $('.noChangeUsd').removeClass('is-invalid').prop('checked', false);
                $('.cashRegister').removeClass('is-invalid');
                $('.comment').removeClass('is-invalid');
            }
        </script>
    </body>
</html>
