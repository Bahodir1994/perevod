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
      <title>JobPage</title>
      <style>
      </style>
    </head>
    <body>
      <div class="row">
        <div class="col-md-5 col-lg-4 order-sm-last">
          <h4 class="d-flex justify-content-between align-items-center mb-3">
            <span class="text-primary">Hisob raqam</span>
            <span class="text-danger">Qarz <span class="badge bg-primary rounded-pill debtCount">0</span></span>
          </h4>
          <ul class="list-group mb-3">
            <li class="list-group-item d-flex justify-content-between lh-sm">
              <div>
                <h6 class="my-0 text-warning">Hisobda mavjud</h6>
                <small class="text-body-secondary">(UZS) O'zbek so'mi</small>
              </div>
              <span class="text-body-secondary totalActiveUzs">0</span>
            </li>
            <li class="list-group-item d-flex justify-content-between lh-sm">
              <div>
                <h6 class="my-0 text-warning">Hisobda mavjud</h6>
                <small class="text-body-secondary">(USD) Aqsh dollari</small>
              </div>
              <span class="text-body-secondary text-success totalActiveUsd">0</span>
            </li>
            <li class="list-group-item d-flex justify-content-between lh-sm">
              <div>
                <h6 class="my-0 text-success">Aylanma mablag'</h6>
                <small class="text-body-secondary">(UZS) O'zbek so'mi</small>
              </div>
              <span class="text-body-secondary totalActiveUzsUsage">0</span>
            </li>
            <li class="list-group-item d-flex justify-content-between">
              <div class="text-success">
                <h6 class="my-0">Aylanma mablag'</h6>
                <small>(USD) Aqsh dollari</small>
              </div>
              <span class="text-body-secondary text-success totalActiveUsdUsage">0</span>
            </li>
            <li class="list-group-item d-flex justify-content-between">
              <div class="align-middle">
                <h6 class="my-0 text-danger">Kunlik start:</h6>
              </div>
              <div class="justify-content-between">
                <div>
                  <strong class="moneyDayStartUzs">0 (UZS)</strong>
                </div>
                <div>
                  <strong class="moneyDayStartUsd">0 (USD)</strong>
                </div>
              </div>
            </li>
          </ul>

          <form class="d-flex justify-content-between p-2">
            <button type="button" class="btn btn-secondary w-100 mx-2">Calculator</button>
            <button type="button" class="btn btn-secondary w-100 mx-2" onclick="send_funcV1_03()">Ishni yakunlash</button>
          </form>
        </div>
        <div class="col-md-7 col-lg-8 ">
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
                <input type="text" class="form-control phoneInput telNumber" id="telNumber" required>
                <div class="invalid-feedback">
                  Tel raqam to'g'ri to'ldirilmagan!
                </div>
              </div>

              <div class="col-sm-6">
                <label for="moneyCost" class="form-label">Tranzaksiya miqdori</label>
                <div class="input-group has-validation">
                  <span class="input-group-text currencyIcon">@</span>
                  <input type="text" class="form-control format-currency currency-mask moneyCost" id="moneyCost" required>
                  <div class="invalid-feedback">
                    Tranzaksiya puli to'ldirilmagan yoki xato!
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
                  <input type="text" class="form-control format-currency currency-mask serviceMoney" id="serviceMoney" required>
                  <div class="invalid-feedback">
                    Xizmat puli to'ldirilmagan yoki xato!
                  </div>
                </div>
              </div>

              <div class="col-sm-6">
                <label for="sendToAddress" class="form-label">Jo'natma manzili</label>
                <select class="form-select sendToAddress" id="sendToAddress" required>
                </select>
                <div class="invalid-feedback">
                  Jo'natma manzili tanlanmagan!
                </div>
              </div>

              <div class="col-sm-6">
                <div class="form-check">
                  <input type="checkbox" class="form-check-input isDebt" id="isDebt" required>
                  <label class="form-check-label" for="isDebt">Qarzga berilmoqda</label>
                </div>
              </div>

              <div class="col-12">
                <label for="comment" class="form-label">Izox</label>
                <input type="text" class="form-control comment" id="comment" required>
              </div>

            </div>

            <div class="row py-2">
              <div class="col-sm-12 d-flex justify-content-between">
                <button class="btn btn-primary w-100 mx-2" onclick="send_funcV1_01('POST')" type="button"><i class="bi bi-download"></i> Saqlash</button>
                <button class="btn btn-success w-100 mx-2" onclick="clear_form1_1()" type="button"><i class="bi bi-arrow-clockwise"></i> Yangilash</button>
              </div>
            </div>
            <hr class="my-2">
          </form>
        </div>
      </div>
      <table id="datatable1" class="table table-striped table-bordered responsive" style="width: 100%!important;">
      </table>

      <!-- Modal filter -->
      <div class="modal fade" id="exampleModalJobFilter" tabindex="-1" aria-labelledby="exampleModalJobFilterLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="exampleModalJobFilterLabel">Qo'shimcha filter</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <div class="row g-3">

                <div class="col-sm-6">
                  <label for="outOrIn" class="form-label">Chiqim/Kirim</label>
                  <select class="form-select outOrIn" id="outOrIn" required>
                    <option value="10">Barchasi</option>
                    <option value="11" selected>Faqat chiqim</option>
                    <option value="12">Faqat kirim</option>
                  </select>
                </div>


                <div class="col-sm-6">
                  <label for="giveType" class="form-label">Chiqim holati</label>
                  <select class="form-select giveType" id="giveType" required>
                    <option value="10">Barchasi</option>
                    <option value="11">Berilgan</option>
                    <option value="12">Berilmagan</option>
                    <option value="13">Qisman berilgan</option>
                    <option value="14" selected>Berilmagan va Qisman berilgan</option>
                  </select>
                </div>

                <div class="col-sm-6">
                  <label for="costType" class="form-label">Valyuta</label>
                  <select class="form-select costType" id="costType" required>
                    <option value="">Barchasi</option>
                    <option value="uzs">(UZS) So'm</option>
                    <option value="usd">(USD) Dollar</option>
                  </select>
                </div>

                <div class="col-sm-6 dateHidden visually-hidden">
                  <label for="inTime" class="form-label">Kirim sanasi</label>
                  <input type="date" class="form-control inTime" id="inTime"/>
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
      <div class="modal fade" id="staticBackdropJobPage" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropJobPageLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="staticBackdropJobPageLabel">Qisman/To'liq chiqim tarixi</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <table id="datatable2" class="table table-striped table-bordered responsive" style="width: 100%!important;">
              </table>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Yopish</button>
            </div>
          </div>
        </div>
      </div>
      <!-- Modal edit -->
      <div class="modal fade" id="exampleModalEdit" tabindex="-1" aria-labelledby="exampleModalEditLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="exampleModalEditLabel">O'zgartirish/O'chirish</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <form class="needs-validation" novalidate>
                <div class="row g-3">
                  <input type="hidden" class="mainId">
                  <div class="col-sm-6">
                    <label for="fullNameEdit" class="form-label">F.I.O</label>
                    <input type="text" class="form-control fullNameEdit" id="fullNameEdit" required>
                    <div class="invalid-feedback">
                      F.I.O to'ldirilmagan!
                    </div>
                  </div>

                  <div class="col-sm-6">
                    <label for="telNumberEdit" class="form-label">Tel.</label>
                    <input type="text" class="form-control phoneInput telNumberEdit" id="telNumberEdit" required>
                    <div class="invalid-feedback">
                      Tel raqam to'g'ri to'ldirilmagan!
                    </div>
                  </div>

                  <div class="col-sm-6">
                    <label for="moneyCostEdit" class="form-label">Tranzaksiya miqdori</label>
                    <div class="input-group has-validation">
                      <span class="input-group-text currencyIcon">@</span>
                      <input type="text" class="form-control format-currency currency-mask moneyCostEdit" id="moneyCostEdit" required>
                      <div class="invalid-feedback">
                        Tranzaksiya puli to'ldirilmagan yoki xato!
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <label for="moneyTypeEdit" class="form-label">Valyuta</label>
                    <select class="form-select moneyTypeEdit" id="moneyTypeEdit" required>
                      <option value="uzs">So'm (uzs)</option>
                      <option value="usd">Dollar (usd)</option>
                    </select>
                    <div class="invalid-feedback">
                      Valyuta tanlanmagan!
                    </div>
                  </div>

                  <div class="col-sm-6">
                    <label for="serviceMoneyEdit" class="form-label">Xizmat uchun</label>
                    <div class="input-group has-validation">
                      <span class="input-group-text">uzs</span>
                      <input type="text" class="form-control format-currency currency-mask serviceMoneyEdit" id="serviceMoneyEdit" required>
                      <div class="invalid-feedback">
                        Xizmat puli to'ldirilmagan yoki xato!
                      </div>
                    </div>
                  </div>

                  <div class="col-sm-6">
                    <label for="sendToAddressEdit" class="form-label">Jo'natma manzili</label>
                    <select class="form-select sendToAddressEdit" id="sendToAddressEdit" required disabled>
                    </select>
                    <div class="invalid-feedback">
                      Jo'natma manzili tanlanmagan!
                    </div>
                  </div>

                  <div class="col-sm-6">
                    <div class="form-check">
                      <input type="checkbox" class="form-check-input isDebtEdit" id="isDebtEdit" required>
                      <label class="form-check-label" for="isDebtEdit">Qarzga berilmoqda</label>
                    </div>
                  </div>

                  <div class="col-12">
                    <label for="commentEdit" class="form-label">Izox</label>
                    <input type="text" class="form-control commentEdit" id="commentEdit" required>
                  </div>

                </div>

                <div class="row py-2">
                  <div class="col-sm-12 d-flex justify-content-between">
                    <div class="btn-group" role="group" aria-label="Button group with nested dropdown">
                      <button type="button" onclick="send_funcV1_01('PUT')" class="btn btn-primary">Saqlash</button>
                      <div class="btn-group" role="group">
                        <button type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        </button>
                        <ul class="dropdown-menu">
                          <li><a class="btn btn-danger dropdown-item" onclick="send_funcV1_01('DELETE')" href="#">O'chirish</a></li>
                        </ul>
                      </div>
                    </div>
                    <button class="btn btn-success w-100 mx-2" onclick="clear_form2_1()" type="button"><i class="bi bi-arrow-clockwise"></i> Yangilash</button>
                  </div>
                </div>
                <hr class="my-2">
              </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Yopish</button>
            </div>
          </div>
        </div>
      </div>
      <div class="m-4"></div>

      <script src="${pageContext.servletContext.contextPath}/resources/assets/custom/custom.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/3.3.4/jquery.inputmask.bundle.min.js"></script>

      <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.1/sockjs.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
      <script src="https://cdn.jsdelivr.net/npm/@stomp/stompjs@7.0.0/bundles/stomp.umd.min.js"></script>

      <script>
        $(document).ready(function () {
            setterDate()
            job_start_funcV1_01();
            selectingSelectionByUserLocation(userLocation)
            send_funcV1_05()
        })
        $('.outOrIn, .giveType, .costType, .inTime').change(function () {
            if ($(this).hasClass('outOrIn')) {
               if ($('.outOrIn').val() === '12'){
                 $('.dateHidden').removeClass('visually-hidden')
               }else if($('.outOrIn').val() !== '12'){
                $('.dateHidden').removeClass('visually-hidden').addClass('visually-hidden')
              }
            }
            send_funcV1_05()
        });

        var dt1 = $('#datatable1').DataTable({
          retrieve: false,
          deferLoading: true,
          paging: true,
          scrollCollapse: true,
          processing: true,
          responsive: true,
          colReorder: true,
          fixedHeader: true,
          ajax: {
            url: '${pageContext.servletContext.contextPath}/route_v2/dataV1',
            type: 'GET'
          },
          serverSide: true,
          buttons: [
            {
              className: 'btn-sm',
              text: 'Filter',
              action: function () {
                $("#exampleModalJobFilter").modal("show");
              }
            }
          ],
          dom: '<"d-flex justify-content-between"flB>rt<"justify-content-between"pi>',
          lengthMenu: [[10, 50, 100, -1], [10, 50, 100, 'Barcha']],
          columns: [
            {title: '№', className: 'text-center', sortable: false, searchable: false, orderable: false, name: 'column0', data: null, render: function (data, type, row, meta) {return meta.row + meta.settings._iDisplayStart + 1 +'.'}},
            {title: 'F.I.O', className: 'text-left', name: 'column1', data: 'fullName'},
            {title: 'Summa', className: 'text-left', name: 'column2', data: 'id', render: (_, __, row) => {
                return formatNumberWithThousandsSeparator(row.paymentCost)+' (' + row.paymentCostType + ')</span>';
              }},
            {title: 'Tel', className: 'text-left', name: 'column3', data: 'phone'},
            {title: 'Chiqim', className: 'text-left', name: 'column4', data: 'outTime', render: (data, type, row, meta) => {
                const dateS = data ? new Date(data) : null;
                var paramDate;
                if (dateS) {
                  const options = {year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit'};
                  paramDate = new Intl.DateTimeFormat('uz-UZ', options).format(dateS);
                }else {
                  paramDate = 'qisman';
                }
                if (row.paymentCost > row.giveCost && row.giveCost === 0) {
                  return '<button class="btn btn-sm w-100 btn-outline-warning" onclick="send_funcV1_02('+"'"+row.id+"'"+', '+"'"+row.fullName.replaceAll("'", "`")+"'"+')"><i class="bi bi-geo"></i></button>';
                }else if (row.paymentCost > row.giveCost && row.giveCost > 0){
                  return '' +
                          '<div class="btn-group w-100">                                                                                                                        '+
                          '  <button type="button" onclick="send_funcV1_02('+"'"+row.id+"'"+', '+row.fullName.replaceAll("'", "`") +')" class="btn btn-sm w-100 btn-outline-success">'+paramDate+'</button>                                                     '+
                          '  <button type="button" class="btn btn-sm w-100  btn-outline-success dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false"> '+
                          '    <span class="visually-hidden">Qo\'shimcha</span>                                                                                            '+
                          '  </button>                                                                                                                                    '+
                          '  <ul class="dropdown-menu">                                                                                                                   '+
                          '    <li><a class="dropdown-item" onclick="send_funcV1_04('+"'"+row.id+"'"+')" href="#">Tarixi</a></li>                                                                                      '+
                          '  </ul>                                                                                                                                        '+
                          '</div>                                                                                                                                         ';
                } else {
                  paramDate = 'to\'liq';
                  return '' +
                          '<div class="dropdown w-100">                                                                                             ' +
                          '  <button class="btn btn-sm w-100 btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false"> ' +
                          '    ' + paramDate + '                                                                                                ' +
                          '  </button>                                                                                                        ' +
                          '  <ul class="dropdown-menu">                                                                                       ' +
                          '    <li><a class="dropdown-item" onclick="send_funcV1_04(' + "'" + row.id + "'" + ')" href="#">Tarixi</a></li>                                                                                      ' +
                          '  </ul>                                                                                                            ' +
                          '</div>                                                                                                             ';
                }
              }},
            {title: 'Kirim', className: 'text-left', name: 'column5', data: 'inTime', render: function (data, type, row, meta) {
                const dateS = data ? new Date(data) : null;
                if (dateS) {
                  const options = {year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit'};
                  return new Intl.DateTimeFormat('uz-UZ', options).format(dateS);
                } else return '';
              }},
            {title: 'Qarzdor', className: 'text-left', name: 'column6', data: 'debt', render: (data, type, row, meta) => {
                if (data) {
                   return 'qarz';
                } else return 'yo\'q';
              }},
            {title: 'Xizmat', className: 'text-left', name: 'column7', data: 'serviceUzs', render: function (data, type, row, meta) {
                return formatNumberWithThousandsSeparator(data) + ' (uzs)';
              }},
            {title: 'Yo\'nalish', className: 'text-left', name: 'column8', data: 'insLocationCode', render: function (data, type, row, meta) {
                if (data === '01'){
                  return 'Mang\'it --> Toshkent'
                }else { //else 95
                  return 'Toshkent --> Mang\'it'
                }
              }},
            {title: 'Izox', className: 'text-left', name: 'column9', data: 'comment'},
            {
              data: null,
              className: 'text-left editor-edit',
              orderable: false,
              searchable: false,
              sortable: false,
              name: 'column10',
              render: function (_, __, row) {// type 1 is edit, if 0 to delete
                  const rowDataJson = JSON.stringify(row).replace(/"/g, '&quot;');
                  return '<div style="cursor: pointer"><i onclick="send_funcV1_06('+rowDataJson+')" class="bi bi-pen-fill"></i></div>';
                // <i onclick="send_funcV1_06('+"'"+rowDataJson+"'"+', 0)" class="bi bi-trash3-fill"></i>
              }
            }
          ],
          language: {url: '${pageContext.servletContext.contextPath}/resources/assets/json/package_oz.json'},
        });
        var dt2 = $('#datatable2').DataTable({
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
            url: '${pageContext.servletContext.contextPath}/route_v2/dataV5',
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

        function job_start_funcV1_01() {
          /**Xisob raqam ma'lumotlarini olish**/
          $.ajax({
            type: "GET",
            url: "${pageContext.servletContext.contextPath}/route_v2/dataV2",
            beforeSend: function () {
            },
            success: function (response) {
              $('.debtCount').text(response.transactionalMoneyList.filter((record) => record.paymentCost > record.payedCost).length)

              $('.totalActiveUzs').text(formatNumberWithThousandsSeparator(response.totalMoneyLogs[0].totalMoneyUzs))
              $('.totalActiveUzsUsage').text(formatNumberWithThousandsSeparator(response.totalMoneyLogs[0].totalMoneyUzsGive))

              $('.totalActiveUsd').text(formatNumberWithThousandsSeparator(response.totalMoneyLogs[0].totalMoneyUsd))
              $('.totalActiveUsdUsage').text(formatNumberWithThousandsSeparator(response.totalMoneyLogs[0].totalMoneyUsdGive))

              $('.moneyDayStartUzs').text(formatNumberWithThousandsSeparator(response.totalUzs) + '(UZS)')
              $('.moneyDayStartUsd').text(formatNumberWithThousandsSeparator(response.totalUsd) + '(USD)')
            },
            error: function () {

            }
          });
        }

        function send_funcV1_01(type) {
          /**Kirimni saqlash**/
          var params_send_funcV1_01;
          if (type === 'POST'){
            params_send_funcV1_01 = {
              "fullName" : $('.fullName').val(),
              "telNumber" : $('.telNumber').val().replaceAll('_', ''),
              "moneyCost" : $('.moneyCost').val().replace(/\s/g, ''),
              "moneyType" : $('.moneyType').val(),
              "serviceMoney" : $('.serviceMoney').val().replace(/\s/g, ''),
              "sendToAddress" : $('.sendToAddress').val(),
              "isDebt" : $('.isDebt').prop("checked"),
              "comment" : $('.comment').val(),
            };
          }
          if (type === 'PUT' || type === 'DELETE'){
            params_send_funcV1_01 = {
              "id" : $('.mainId').val(),
              "fullName" : $('.fullNameEdit').val(),
              "telNumber" : $('.telNumberEdit').val().replaceAll('_', ''),
              "moneyCost" : $('.moneyCostEdit').val().replace(/\s/g, ''),
              "moneyType" : $('.moneyTypeEdit').val(),
              "serviceMoney" : $('.serviceMoneyEdit').val().replace(/\s/g, ''),
              "sendToAddress" : $('.sendToAddressEdit').val(),
              "isDebt" : $('.isDebtEdit').prop("checked"),
              "comment" : $('.commentEdit').val(),
            };
          }
          let title = "";
          if (type==='POST') {
            title = " mablag'ini qabul qilishni tasdiqlaysizmi?";
          }
          else if (type === 'PUT' || type === 'DELETE') {
            title = "ga kiritilgan o'zgartirishni tasdiqlaysizmi?"
          }

          Swal.fire({
            title: params_send_funcV1_01.fullName + title,
            showDenyButton: true,
            confirmButtonText: 'Xa',
            denyButtonText: `Yo'q`,
          }).then((result) => {
            if (result.isConfirmed) {
              $.ajax({
                type: type,
                url: '${pageContext.servletContext.contextPath}/route_v2/dataV3',
                data: JSON.stringify(params_send_funcV1_01),
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
                    if (type === 'POST'){
                      handleValidationErrors(xhr);
                    }else {
                      handleValidationEditErrors(xhr);
                    }
                  }
                  else if (xhr.status === 200) {
                    if (!xhr.responseJSON.success){
                        Swal.fire({
                          position: 'center',
                          icon: 'info',
                          title: xhr.responseJSON.message,
                          showConfirmButton: false,
                          timer: 2000
                        })
                    }
                    else {
                      Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: xhr.responseJSON.message,
                        showConfirmButton: false,
                        timer: 2000
                      })
                      if (type === 'POST'){
                        handleValidationErrors(xhr);
                        clear_form1_1()
                      }else {
                        handleValidationEditErrors(xhr);
                        $("#exampleModalEdit").modal("hide");
                      }

                      job_start_funcV1_01()
                      dt1.draw();
                      sendName()
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
        function send_funcV1_02(transactionalMoneyId, fullName) {
          /**Chiqimni tasdiqlash**/
          var param_send_funcV1_02 = {
            "transactionalMoneyId" : transactionalMoneyId,
            "moneyCost" : 0,
            "payAll" : true,
          }
          Swal.fire({
            title: fullName + " mablag'ini berishni tasdiqlaysizmi?",
            showDenyButton: true,
            confirmButtonText: 'Xa',
            denyButtonText: `Yo'q`,
            showCancelButton: true,
            cancelButtonText: 'Qaytish'
          }).then((result) => {
            if (result.isConfirmed) {
              $.ajax({
                  type: "POST",
                  url: '${pageContext.servletContext.contextPath}/route_v2/dataV4',
                  data: JSON.stringify(param_send_funcV1_02),
                  dataType: "json",
                  async: true,
                  contentType: 'application/json',
                  beforeSend: function(xhr) {},
                  complete: function (xhr, status, error) {
                    if (xhr.status === 400) {
                      handleValidationErrors(xhr);
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
                          timer: 1500
                        })
                      } else { //success true
                        Swal.fire({
                          position: 'center',
                          icon: 'success',
                          title: 'Saqlandi!',
                          text: xhr.responseJSON.message,
                          showConfirmButton: false,
                          timer: 2000
                        })
                        job_start_funcV1_01()
                        dt1.draw();
                      }
                    }
                    else {
                      Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: error,
                        showConfirmButton: false,
                        timer: 1500
                      })
                    }
                  }
              });
            }
            else if (result.isDenied) {
              Swal.fire({
                title: 'Chiqim mablag\'ni kiriting',
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

                  var param_send_funcV1_021 = {
                    "transactionalMoneyId": transactionalMoneyId,
                    "moneyCost": numberValue,
                    "comment": textValue,
                    "payAll": false,
                  };
                  $.ajax({
                    type: "POST",
                    url: '${pageContext.servletContext.contextPath}/route_v2/dataV4',
                    data: JSON.stringify(param_send_funcV1_021),
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
                        job_start_funcV1_01()
                        dt1.draw();
                      }
                      else {
                        Swal.fire('Ko\'zda tutilmagan xatolik!', '', 'info')
                      }
                    }
                  });
                }
              })
            }
          });
        }
        function send_funcV1_03() {
          /**Ishni yakunlash**/
          Swal.fire({
            title: 'Ishni yakunlashni tasdiqlaysizmi?',
            showDenyButton: true,
            confirmButtonText: 'Xa',
            denyButtonText: `Yo'q`,
          }).then((result) => {
            if (result.isConfirmed) {
              $.ajax({
                type: "GET",
                url: '${pageContext.servletContext.contextPath}/route_v1/data_v4/job_page_finishing',
                async: true,
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
                        timer: 1500
                      })
                    } else { //success true
                      Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Bajarildi!',
                        text: xhr.responseJSON.message,
                        showConfirmButton: false,
                        timer: 2000
                      })
                      setTimeout(() => {
                        window.location.reload();
                      }, 2000);
                    }
                  }
                  else {
                    Swal.fire({
                      position: 'center',
                      icon: 'error',
                      title: error,
                      showConfirmButton: false,
                      timer: 1500
                    })
                  }
                }
              });
            }
          });
        }
        function send_funcV1_04(transactionalMoneyId) {
          $("#staticBackdropJobPage").modal("show");
          setTimeout(function() {
            dt2.responsive.recalc();
            dt2.column('columnId:name').search(transactionalMoneyId).draw();
          }, 500);
        }
        function send_funcV1_05() {
          dt1.columns('column8:name').search($('.outOrIn').val())
                  .columns('column4:name').search($('.giveType').val())
                  .columns('column2:name').search($('.costType').val())
                  .columns('column5:name').search($('.inTime').val())
                  .draw();
        }
        function send_funcV1_06(rowData) {
          $("#exampleModalEdit").modal("show");

            $('.mainId').val(rowData.id);
            $('.fullNameEdit').val(rowData.fullName);
            $('.telNumberEdit').val(rowData.phone);
            $('.moneyCostEdit').val(formatNumberWithThousandsSeparator(rowData.paymentCost));
            $('.moneyTypeEdit').val(rowData.paymentCostType);
            $('.serviceMoneyEdit').val(rowData.serviceUzs);
            var secondOptionLoc;
            if (rowData.insLocationCode === '01'){
                secondOptionLoc = '<option value="95">Mang\'it</option>'
            } else { //else 95
                secondOptionLoc = '<option value="01" selected>Toshkent</option>'
            }  ;
            $('.sendToAddressEdit').html('<option value="'+rowData.insLocationCode+'" selected>'+rowData.insLocationName+'</option>'+secondOptionLoc+'');
            $('.isDebtEdit').prop("checked", rowData.debt);
            $('.commentEdit').val(rowData.comment);
          console.log(rowData)
        }

        function handleValidationErrors(errors) {
          /**Kirim ma'lumotlari validatsiyasi**/
          ![undefined, null, ''].includes(errors.responseJSON.fullName) ? $('.fullName').addClass('is-invalid') : $('.fullName').removeClass('is-invalid');
          ![undefined, null, ''].includes(errors.responseJSON.telNumber) ? $('.telNumber').addClass('is-invalid') : $('.telNumber').removeClass('is-invalid');
          ![undefined, null, ''].includes(errors.responseJSON.moneyCost) ? $('.moneyCost').addClass('is-invalid') : $('.moneyCost').removeClass('is-invalid');
          ![undefined, null, ''].includes(errors.responseJSON.moneyType) ? $('.moneyType').addClass('is-invalid') : $('.moneyType').removeClass('is-invalid');
          ![undefined, null, ''].includes(errors.responseJSON.serviceMoney) ? $('.serviceMoney').addClass('is-invalid') : $('.serviceMoney').removeClass('is-invalid');
          ![undefined, null, ''].includes(errors.responseJSON.sendToAddress) ? $('.sendToAddress').addClass('is-invalid') : $('.sendToAddress').removeClass('is-invalid');
          ![undefined, null, ''].includes(errors.responseJSON.isDebt) ? $('.isDebt').addClass('is-invalid') : $('.isDebt').removeClass('is-invalid');
          ![undefined, null, ''].includes(errors.responseJSON.comment) ? $('.comment').addClass('is-invalid') : $('.comment').removeClass('is-invalid');
        }
        function handleValidationEditErrors(errors) {
          /**Kirim ma'lumotlari validatsiyasi**/
          ![undefined, null, ''].includes(errors.responseJSON.fullName) ? $('.fullNameEdit').addClass('is-invalid') : $('.fullNameEdit').removeClass('is-invalid');
          ![undefined, null, ''].includes(errors.responseJSON.telNumber) ? $('.telNumberEdit').addClass('is-invalid') : $('.telNumberEdit').removeClass('is-invalid');
          ![undefined, null, ''].includes(errors.responseJSON.moneyCost) ? $('.moneyCostEdit').addClass('is-invalid') : $('.moneyCostEdit').removeClass('is-invalid');
          ![undefined, null, ''].includes(errors.responseJSON.moneyType) ? $('.moneyTypeEdit').addClass('is-invalid') : $('.moneyTypeEdit').removeClass('is-invalid');
          ![undefined, null, ''].includes(errors.responseJSON.serviceMoney) ? $('.serviceMoneyEdit').addClass('is-invalid') : $('.serviceMoneyEdit').removeClass('is-invalid');
          ![undefined, null, ''].includes(errors.responseJSON.sendToAddress) ? $('.sendToAddressEdit').addClass('is-invalid') : $('.sendToAddressEdit').removeClass('is-invalid');
          ![undefined, null, ''].includes(errors.responseJSON.isDebt) ? $('.isDebtEdit').addClass('is-invalid') : $('.isDebtEdit').removeClass('is-invalid');
          ![undefined, null, ''].includes(errors.responseJSON.comment) ? $('.commentEdit').addClass('is-invalid') : $('.commentEdit').removeClass('is-invalid');
        }
        function clear_form1_1() {
          $('.fullName').removeClass('is-invalid').val('');
          $('.telNumber').removeClass('is-invalid').val('');
          $('.moneyCost').removeClass('is-invalid').val('');
          $('.moneyType').removeClass('is-invalid');
          $('.serviceMoney').removeClass('is-invalid').val('');
          $('.sendToAddress').removeClass('is-invalid');
          $('.isDebt').removeClass('is-invalid').prop('checked', false);
          $('.comment').removeClass('is-invalid').val('');
        }
        function clear_form2_1() {
          $('.fullNameEdit').removeClass('is-invalid');
          $('.telNumberEdit').removeClass('is-invalid');
          $('.moneyCostEdit').removeClass('is-invalid');
          $('.moneyTypeEdit').removeClass('is-invalid');
          $('.serviceMoneyEdit').removeClass('is-invalid');
          $('.sendToAddressEdit').removeClass('is-invalid');
          $('.isDebtEdit').removeClass('is-invalid');
          $('.commentEdit').removeClass('is-invalid');
        }
        function selectingSelectionByUserLocation(locationId) {
          var loc95To01 = '<option value="01">Toshkent</option>';
          var loc95To01Code = '01';
          var loc01To95 = '<option value="95">Mang`it</option>';
          var loc01To95Code = '95';
          if (locationId === "01"){
            $('.sendToAddress').html(loc01To95);
            $('.sendToAddress option[value="'+loc01To95Code+'"]').prop('selected', true)
          }
          if (locationId === "95"){
            $('.sendToAddress').html(loc95To01);
            $('.sendToAddress option[value="'+loc95To01Code+'"]').prop('selected', true)
          }
        }
        function setterDate() {
          let currentDate = new Date();
          let formattedDate = currentDate.toISOString().split('T')[0];
          $('.inTime').val(formattedDate);
        }
      </script>
    </body>
</html>
