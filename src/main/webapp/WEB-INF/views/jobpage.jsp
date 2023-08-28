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
</head>
<body>
  <div class="row">
    <div class="col-md-5 col-lg-4 order-sm-last">
      <h4 class="d-flex justify-content-between align-items-center mb-3">
        <span class="text-primary">Xisob raqam</span>
        <span class="text-danger">Qarz <span class="badge bg-primary rounded-pill debtCount">0</span></span>
      </h4>
      <ul class="list-group mb-3">
        <li class="list-group-item d-flex justify-content-between lh-sm">
          <div>
            <h6 class="my-0 text-warning">Xisobda mavjud</h6>
            <small class="text-body-secondary">(UZS) O'zbek so'mi</small>
          </div>
          <span class="text-body-secondary totalActiveUzs">0</span>
        </li>
        <li class="list-group-item d-flex justify-content-between lh-sm">
          <div>
            <h6 class="my-0 text-warning">Xisobda mavjud</h6>
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
        <button type="submit" class="btn btn-secondary w-100 mx-2">Calculator</button>
        <button type="submit" class="btn btn-secondary w-100 mx-2">Ishni yakunlash</button>
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
              Tel raqam to'ldirilmagan!
            </div>
          </div>

          <div class="col-sm-6">
            <label for="moneyCost" class="form-label">Tranzaksiya miqdori</label>
            <div class="input-group has-validation">
              <span class="input-group-text currencyIcon">@</span>
              <input type="text" class="form-control format-currency currency-mask moneyCost" id="moneyCost" required>
              <div class="invalid-feedback">
                Xizmat puli to'ldirilmagan yoki xato!
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
              <option value="01-toshkent">Toshkent</option>
              <option value="95-mangit">Mangit</option>
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
          <div class="col-sm-6">
            <button class="btn btn-primary w-75" onclick="send_funcV1_01()" type="button">Saqlash</button>
          </div>
        </div>
        <hr class="my-2">
      </form>
    </div>


  </div>
  <table id="datatable1" class="table table-striped table-bordered responsive" style="width: 100%!important;">
  </table>

  <script src="${pageContext.servletContext.contextPath}/resources/assets/custom/custom.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/google-libphonenumber/8.12.38/libphonenumber.js"></script>

  <script>
    $(document).ready(function () {
        job_start_funcV1_01();
    })
  </script>

  <script>
    var dt1 = $('#datatable1').DataTable({
      // retrieve: false,
      // deferLoading: true,
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
      dom: '<"row"<"col-sm-12 justify-content-end"f>>' +
              '<"row"<"col-sm-12"t>>' +
              '<"row"<"col-sm-12 d-flex justify-content-center"pr>>' +
              '<"row"<"col-sm-6 text-start"l><"col-sm-6 text-end"i>>',
      lengthMenu: [[10, 50, 100, -1], [10, 50, 100, 'Barcha']],
      columns: [
        {title: '№', className: 'text-center', sortable: false, searchable: false, orderable: false, name: 'column0', data: null, render: function (data, type, row, meta) {return meta.row + meta.settings._iDisplayStart + 1 +'.'}},
        {title: 'F.I.O', className: 'text-left', name: 'column1', data: 'fullName'},
        {title: 'Summa', className: 'text-center', name: 'column2', data: 'id', render: (_, __, row) => {
            return row.paymentCost + ' (' + row.paymentCostType + ')'
          }},
        {title: 'Tel', className: 'text-left', name: 'column3', data: 'phone'},
        {title: 'Chiqim', className: 'text-left', name: 'column4', data: 'outTime'},
        {title: 'Kirim', className: 'text-left', name: 'column5', data: 'inTime'},
        {title: 'Qarzdor', className: 'text-left', name: 'column6', data: 'debt'},
        {title: 'Xizmat', className: 'text-left', name: 'column6', data: 'serviceUzs'},
        {title: 'Izox', className: 'text-left', name: 'column6', data: 'comment'},
      ],
      language: {url: '${pageContext.servletContext.contextPath}/resources/assets/json/package_oz.json'},
    });

    function job_start_funcV1_01() {
      $.ajax({
        type: "GET",
        url: "${pageContext.servletContext.contextPath}/route_v2/dataV2",
        beforeSend: function () {
        },
        success: function (response) {
          $('.debtCount').text(response.transactionalMoneyList.length)

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

    function send_funcV1_01() {
      var params_send_funcV1_01 = {
            "fullName" : $('.fullName').val(),
            "telNumber" : $('.telNumber').val(),
            "moneyCost" : $('.moneyCost').val().replace(/\s/g, ''),
            "moneyType" : $('.moneyType').val(),
            "serviceMoney" : $('.serviceMoney').val().replace(/\s/g, ''),
            "sendToAddress" : $('.sendToAddress').val(),
            "isDebt" : $('.isDebt').prop("checked"),
            "comment" : $('.comment').val(),
        };
        $.ajax({
          type: "POST",
          url: '${pageContext.servletContext.contextPath}/route_v2/dataV3',
          data: JSON.stringify(params_send_funcV1_01),
          dataType: "json",
          async: true,
          contentType: 'application/json',
          beforeSend: function(xhr) {},
          complete: function (xhr, status, error) {
            if (xhr.status === 400) {
              handleValidationErrors(xhr);
            }
            else if (xhr.status === 200) {
              handleValidationErrors(xhr);
            }
            else {
              alert('Error: ' + error);
            }
          }
        });
    }

    function handleValidationErrors(errors) {
      ![undefined, null, ''].includes(errors.responseJSON.fullName) ? $('.fullName').addClass('is-invalid') : $('.fullName').removeClass('is-invalid');
      ![undefined, null, ''].includes(errors.responseJSON.telNumber) ? $('.telNumber').addClass('is-invalid') : $('.telNumber').removeClass('is-invalid');
      ![undefined, null, ''].includes(errors.responseJSON.moneyCost) ? $('.moneyCost').addClass('is-invalid') : $('.moneyCost').removeClass('is-invalid');
      ![undefined, null, ''].includes(errors.responseJSON.moneyType) ? $('.moneyType').addClass('is-invalid') : $('.moneyType').removeClass('is-invalid');
      ![undefined, null, ''].includes(errors.responseJSON.serviceMoney) ? $('.serviceMoney').addClass('is-invalid') : $('.serviceMoney').removeClass('is-invalid');
      ![undefined, null, ''].includes(errors.responseJSON.sendToAddress) ? $('.sendToAddress').addClass('is-invalid') : $('.sendToAddress').removeClass('is-invalid');
      ![undefined, null, ''].includes(errors.responseJSON.isDebt) ? $('.isDebt').addClass('is-invalid') : $('.isDebt').removeClass('is-invalid');
      ![undefined, null, ''].includes(errors.responseJSON.comment) ? $('.comment').addClass('is-invalid') : $('.comment').removeClass('is-invalid');
    }
  </script>
</body>
</html>
