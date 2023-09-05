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
      <title>DebtPage</title>
  </head>
  <body>
    <div class="row">
      <div class="col-md-12 col-lg-12 order-sm-last">
        <h4 class="d-flex justify-content-between align-items-center mb-3">
          <span class="text-primary">Qarzdorliklar hisoboti</span>
          <span class="text-danger">Qarz <span class="badge bg-primary rounded-pill debtCount">0</span></span>
        </h4>
        <ul class="list-group mb-3">
          <li class="list-group-item d-flex justify-content-between lh-sm">
            <div>
              <h6 class="my-0 text-warning">Jami qarzlar</h6>
              <small class="text-body-secondary">(UZS) O'zbek so'mi</small>
            </div>
            <span class="text-body-secondary debtTotalUzs">0</span>
          </li>
          <li class="list-group-item d-flex justify-content-between lh-sm">
            <div>
              <h6 class="my-0 text-warning">Jami qarzlar</h6>
              <small class="text-body-secondary">(USD) Aqsh dollari</small>
            </div>
            <span class="text-body-secondary text-success debtTotalUsd">0</span>
          </li>
          <li class="list-group-item d-flex justify-content-between lh-sm">
            <div>
              <h6 class="my-0 text-success">Undirildi</h6>
              <small class="text-body-secondary">(UZS) O'zbek so'mi</small>
            </div>
            <span class="text-body-secondary debtUsedUzs">0</span>
          </li>
          <li class="list-group-item d-flex justify-content-between">
            <div class="text-success">
              <h6 class="my-0">Undirildi</h6>
              <small class="text-body-secondary">(USD) Aqsh dollari</small>
            </div>
            <span class="text-body-secondary text-success debtUsedUsd">0</span>
          </li>
          <li class="list-group-item d-flex justify-content-between">
            <div class="align-middle">
              <h6 class="my-0 text-danger">Qoldiq:</h6>
            </div>
            <div class="justify-content-between">
              <div>
                <strong class="debtUnUsedUzs">0 (UZS)</strong>
              </div>
              <div>
                <strong class="debtUnUsedUsd">0 (USD)</strong>
              </div>
            </div>
          </li>
        </ul>

        <form class="p-2 d-flex justify-content-between">
          <div class="form-check">
            <input type="checkbox" class="form-check-input seeAll" id="seeAll" required>
            <label class="form-check-label" for="seeAll">Barchasi</label>
          </div>
        </form>
      </div>
    </div>
    <table id="datatableDebt1" class="table table-striped table-bordered responsive" style="width: 100%!important;">
    </table>

    <!-- Modal filter -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">Qo'shimcha filter</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="row g-3">
              <div class="col-sm-6">
              <label for="debtType" class="form-label">Qarz holati</label>
              <select class="form-select debtType" id="debtType" required>
                <option value="10">Barchasi</option>
                <option value="11">Undirilgan</option>
                <option value="12">Undirilmagan</option>
                <option value="13">Qisman undirilgan</option>
              </select>
            </div>

              <div class="col-sm-6">
              <label for="costType" class="form-label">Valyuta</label>
              <select class="form-select costType" id="costType" required>
                <option value="0">Barchasi</option>
                <option value="uzs">(UZS) So'm</option>
                <option value="usd">(USD) Dollar</option>
              </select>
            </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Yopish</button>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal log list-->
    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="staticBackdropLabel">Qisman/To'liq to'lash tarixi</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <table id="datatableDebt2" class="table table-striped table-bordered responsive" style="width: 100%!important;">
            </table>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Yopish</button>
          </div>
        </div>
      </div>
    </div>

    <script src="${pageContext.servletContext.contextPath}/resources/assets/custom/custom.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/3.3.4/jquery.inputmask.bundle.min.js"></script>

    <script>
      $(document).ready(function () {
          debt_funcV1_01(false);
      })
      var dtD1 = $('#datatableDebt1').DataTable({
        // retrieve: false,
        // deferLoading: true,
        paging: true,
        scrollCollapse: true,
        processing: true,
        responsive: true,
        colReorder: true,
        fixedHeader: true,
          buttons: [
              {
                  className: 'btn-sm',
                  text: 'Filter',
                  action: function () {
                    $("#exampleModal").modal("show");
                  }
              }
          ],
        ajax: {
          url: '${pageContext.servletContext.contextPath}/route_v4/data_v3/debt_table',
          type: 'GET',
          data: function (d) {
              d.seeAll = $('.seeAll').prop('checked');
              d.seeAllDebtType = $('.debtType').val()
              d.costType = $('.costType').val()
          }
        },
        serverSide: true,
        dom: '<"d-flex justify-content-between"flB>rt<"justify-content-between"pi>',
        lengthMenu: [[10, 50, 100, -1], [10, 50, 100, 'Barcha']],
        columns: [
          {title: '№', className: 'text-left', sortable: false, searchable: false, orderable: false, name: 'column0', data: null, render: function (data, type, row, meta) {return meta.row + meta.settings._iDisplayStart + 1 +'.'}},
          {title: 'F.I.O', className: 'text-left', name: 'column1', data: 'fullName'},
          {title: 'Summa', className: 'text-left', name: 'column2', data: 'id', render: (_, __, row) => {
              return formatNumberWithThousandsSeparator(row.paymentCost)+' (' + row.paymentCostType + ')</span>';
            }},
          {title: 'Undirish', className: 'text-left', name: 'column6', data: 'id', render: (data, type, row, meta) => {
              if (row.paymentCost > row.payedCost && row.payedCost === 0) {
                return '<button class="btn btn-sm w-100 btn-outline-warning" onclick="debt_funcV1_02('+"'"+row.id+"'"+')"><i class="bi bi-geo"></i></button>';
              }else if (row.paymentCost > row.payedCost && row.payedCost > 0){
                return '' +
                        '<div class="btn-group w-100">                                                                                                                        '+
                        '  <button type="button" onclick="debt_funcV1_02('+"'"+row.id+"'"+')" class="btn btn-sm w-100 btn-outline-success"><i class="bi bi-geo"></i></button>                                                     '+
                        '  <button type="button" class="btn btn-sm w-100  btn-outline-success dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false"> '+
                        '    <span class="visually-hidden">Qo\'shimcha</span>                                                                                            '+
                        '  </button>                                                                                                                                    '+
                        '  <ul class="dropdown-menu">                                                                                                                   '+
                        '    <li><a class="dropdown-item" onclick="debt_funcV1_03('+"'"+row.id+"'"+')" href="#">Tarixi</a></li>                                                                                      '+
                        '  </ul>                                                                                                                                        '+
                        '</div>                                                                                                                                         ';
                } else return '' +
                      '<div class="dropdown w-100">                                                                                             '+
                      '  <button class="btn btn-sm w-100 btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false"> '+
                      '    undirilgan                                                                                                '+
                      '  </button>                                                                                                        '+
                      '  <ul class="dropdown-menu">                                                                                       '+
                      '    <li><a class="dropdown-item" onclick="debt_funcV1_03('+"'"+row.id+"'"+')" href="#">Tarixi</a></li>                                                                                      '+
                      '  </ul>                                                                                                            '+
                      '</div>                                                                                                             ';
            }},
          {title: 'Tel', className: 'text-left', name: 'column3', data: 'phone'},
          {title: 'Chiqim', className: 'text-left', name: 'column4', data: 'outTime', render: function (data, type, row, meta) {
              const dateS = data ? new Date(data) : null;
              if (dateS) {
                const options = {year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit'};
                return new Intl.DateTimeFormat('uz-UZ', options).format(dateS);
              } else return '<i class="bi bi-node-minus"></i>';
            }},
          {title: 'Kirim', className: 'text-left', name: 'column5', data: 'inTime', render: function (data, type, row, meta) {
              const dateS = data ? new Date(data) : null;
              if (dateS) {
                const options = {year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit'};
                return new Intl.DateTimeFormat('uz-UZ', options).format(dateS);
              } else return '';
            }},
          {title: 'Yo\'nalish', className: 'text-left', name: 'column6', data: 'insLocationCode', render: function (data, type, row, meta) {
              if (data === '01'){
                return 'Mang\'it --> Toshkent'
              }else { //else 95
                return 'Toshkent --> Mang\'it'
              }
            }},
          {title: 'Izox', className: 'text-left', name: 'column6', data: 'comment'},
        ],
        language: {url: '${pageContext.servletContext.contextPath}/resources/assets/json/package_oz.json'},
      });
      var dtD2 = $('#datatableDebt2').DataTable({
        retrieve: false,
        deferLoading: 10,
        paging: true,
        processing: true,
        responsive: true,
        colReorder: true,
        fixedHeader: true,
        // buttons: [
        //   {
        //     text: 'Filter',
        //     action: function () {
        //       $("#exampleModal").modal("show");
        //     }
        //   }
        // ],
        ajax: {
          url: '${pageContext.servletContext.contextPath}/route_v4/data_v3/debt_table_log',
          type: 'GET',
        },
        serverSide: true,
        dom: 'Bfrtip',
        lengthMenu: [[10, 50, 100, -1], [10, 50, 100, 'Barcha']],
        columns: [
          {title: 'id', className: 'text-left', name: 'columnId', data: 'transactionalMoneyLogId', visible: false},
          {title: '№', className: 'text-left', sortable: false, searchable: false, orderable: false, name: 'column0', data: null, render: function (data, type, row, meta) {return meta.row + meta.settings._iDisplayStart + 1 +'.'}},
          {title: 'Kim', className: 'text-left', name: 'column1', data: 'insUser'},
          {title: 'Summa', className: 'text-left', name: 'column2', data: 'payedCost', render: (_, __, row) => {
              return formatNumberWithThousandsSeparator(row.payedCost);
            }},
          {title: 'Vaqti', className: 'text-left', name: 'column4', data: 'insTime', render: function (data, type, row, meta) {
              const dateS = data ? new Date(data) : null;
              if (dateS) {
                const options = {year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit'};
                return new Intl.DateTimeFormat('uz-UZ', options).format(dateS);
              } else return '<i class="bi bi-node-minus"></i>';
            }},
          {title: 'Izox', className: 'text-left', name: 'column6', data: 'comment'},
        ],
        language: {url: '${pageContext.servletContext.contextPath}/resources/assets/json/package_oz.json'},
      });

      function debt_funcV1_01(seeAll) {
        /**Qarzdorlik ma'lumotlari**/
        var params = {
          "seeAll" : seeAll
        }
        $.ajax({
          type: "GET",
          url: "${pageContext.servletContext.contextPath}/route_v4/data_v2/debt_info",
          data: params,
          beforeSend: function () {
          },
          success: function (response) {
            $('.debtCount').text(Number(response[0].soni+response[1].soni))

            $('.debtTotalUzs').text(formatNumberWithThousandsSeparator(response[0].jami))
            $('.debtTotalUsd').text(formatNumberWithThousandsSeparator(response[1].jami))

            $('.debtUsedUzs').text(formatNumberWithThousandsSeparator(response[0].undirildi))
            $('.debtUsedUsd').text(formatNumberWithThousandsSeparator(response[1].undirildi))

            $('.debtUnUsedUzs').text(formatNumberWithThousandsSeparator(response[0].qoldiq) + '(UZS)')
            $('.debtUnUsedUsd').text(formatNumberWithThousandsSeparator(response[1].qoldiq) + '(USD)')
          },
          error: function () {

          }
        });
      }
      $('.debtType').change(function() {
        dtD1.draw();
      });
      $('.costType').change(function() {
        dtD1.draw();
      });
      $('.seeAll').change(function() {
        var checkStatus = $(this).prop('checked');
        debt_funcV1_01(checkStatus);
        dtD1.draw();
      });
      function debt_funcV1_02(transactionalMoneyId) {
        var param_debt_funcV1_02 = {
          "transactionalMoneyId" : transactionalMoneyId,
          "moneyCost" : 0,
          "payAll" : true,
        }
        Swal.fire({
          title: 'Qarizni to\'liq yopish?',
          showDenyButton: true,
          confirmButtonText: 'Xa',
          denyButtonText: `Yo'q`,
          showCancelButton: true,
          cancelButtonText: 'Qaytish'
        }).then((result) => {
          if (result.isConfirmed) {
            $.ajax({
              type: "POST",
              url: '${pageContext.servletContext.contextPath}/route_v4/data_v4/debt_usaging',
              data: JSON.stringify(param_debt_funcV1_02),
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
                }
                else if (xhr.status === 200) {
                  if (!xhr.responseJSON.success){
                    Swal.fire({
                      position: 'center',
                      icon: 'info',
                      title: xhr.responseJSON.message,
                      showConfirmButton: false,
                      timer: 2000
                    });
                    return
                  }
                  Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: xhr.responseJSON.message,
                    showConfirmButton: false,
                    timer: 1500
                  })
                  debt_funcV1_01(false)
                  dtD1.draw();
                }
                else {
                  Swal.fire('Ko\'zda tutilmagan xatolik!', '', 'info')
                }
              }
            });
          } else if (result.isDenied) {
            Swal.fire({
              title: 'Undirilayotgan mablag\'ni kiriting',
              // input: 'number',
              // inputAttributes: {
              //   autocapitalize: 'off'
              // },
              customClass: {
                input: 'swal-input',
                confirmButton: 'swal-confirm-button',
                cancelButton: 'swal-cancel-button'
              },
              html:
                      '<input id="swal-input1" class="form-control swal-input my-2 format-currency currency-mask" placeholder="Qiymat" type="number">' +
                      '<input id="swal-input2" class="form-control swal-input my-2" placeholder="Izox" type="text">',
              focusConfirm: false,
              showCancelButton: true,
              cancelButtonText: 'Qaytish',
              confirmButtonText: 'Saqlash',
              showLoaderOnConfirm: true,
              preConfirm: () => {
                const numberValue = Swal.getPopup().querySelector('#swal-input1').value;
                const textValue = Swal.getPopup().querySelector('#swal-input2').value;

                if (!numberValue) {
                  Swal.showValidationMessage('Qiymat kiritilmagan');
                }

                return { numberValue, textValue };
              }
            }).then((result) => {
              if (result.isConfirmed) {
                const { numberValue, textValue } = result.value;

                var param_debt_funcV1_021 = {
                  "transactionalMoneyId": transactionalMoneyId,
                  "moneyCost": numberValue,
                  "comment": textValue,
                  "payAll": false,
                };
                $.ajax({
                  type: "POST",
                  url: '${pageContext.servletContext.contextPath}/route_v4/data_v4/debt_usaging',
                  data: JSON.stringify(param_debt_funcV1_021),
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
                    }
                    else if (xhr.status === 200) {
                      if (!xhr.responseJSON.success){
                        Swal.fire({
                          position: 'center',
                          icon: 'info',
                          title: xhr.responseJSON.message,
                          showConfirmButton: false,
                          timer: 2000
                        });
                        return
                      }
                      Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: xhr.responseJSON.message,
                        showConfirmButton: false,
                        timer: 1500
                      })
                      debt_funcV1_01(false)
                      dtD1.draw();
                    }
                    else {
                      Swal.fire('Ko\'zda tutilmagan xatolik!', '', 'info')
                    }
                  }
                });
              }
            })
          }
        })
      }
      function debt_funcV1_03(transactionalMoneyId) {
        $("#staticBackdrop").modal("show");
        setTimeout(function() {
          dtD2.responsive.recalc();
          dtD2.column('columnId:name').search(transactionalMoneyId).draw();
        }, 500);
      }

      // function handleValidationErrors(errors) {
      //   /**Kirim ma'lumotlari validatsiyasi**/
      //   ![undefined, null, ''].includes(errors.responseJSON.fullName) ? $('.fullName').addClass('is-invalid') : $('.fullName').removeClass('is-invalid');
      //   ![undefined, null, ''].includes(errors.responseJSON.telNumber) ? $('.telNumber').addClass('is-invalid') : $('.telNumber').removeClass('is-invalid');
      //   ![undefined, null, ''].includes(errors.responseJSON.moneyCost) ? $('.moneyCost').addClass('is-invalid') : $('.moneyCost').removeClass('is-invalid');
      //   ![undefined, null, ''].includes(errors.responseJSON.moneyType) ? $('.moneyType').addClass('is-invalid') : $('.moneyType').removeClass('is-invalid');
      //   ![undefined, null, ''].includes(errors.responseJSON.serviceMoney) ? $('.serviceMoney').addClass('is-invalid') : $('.serviceMoney').removeClass('is-invalid');
      //   ![undefined, null, ''].includes(errors.responseJSON.sendToAddress) ? $('.sendToAddress').addClass('is-invalid') : $('.sendToAddress').removeClass('is-invalid');
      //   ![undefined, null, ''].includes(errors.responseJSON.isDebt) ? $('.isDebt').addClass('is-invalid') : $('.isDebt').removeClass('is-invalid');
      //   ![undefined, null, ''].includes(errors.responseJSON.comment) ? $('.comment').addClass('is-invalid') : $('.comment').removeClass('is-invalid');
      // }
      // function clear_form1_1() {
      //   $('.fullName').removeClass('is-invalid').val('');
      //   $('.telNumber').removeClass('is-invalid').val('');
      //   $('.moneyCost').removeClass('is-invalid').val('');
      //   $('.moneyType').removeClass('is-invalid');
      //   $('.serviceMoney').removeClass('is-invalid').val('');
      //   $('.sendToAddress').removeClass('is-invalid');
      //   $('.isDebt').removeClass('is-invalid').prop('checked', false);
      //   $('.comment').removeClass('is-invalid').val('');
      // }
    </script>
  </body>
</html>
