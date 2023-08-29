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
            <button class="btn btn-primary w-100 mx-2" onclick="send_funcV1_01()" type="button"><i class="bi bi-download"></i> Saqlash</button>
            <button class="btn btn-success w-100 mx-2" onclick="clear_form1_1()" type="button"><i class="bi bi-arrow-clockwise"></i> Yangilash</button>
          </div>
        </div>
        <hr class="my-2">
      </form>
    </div>
  </div>
  <table id="datatable1" class="table table-striped table-bordered responsive" style="width: 100%!important;">
  </table>

  <script src="${pageContext.servletContext.contextPath}/resources/assets/custom/custom.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/3.3.4/jquery.inputmask.bundle.min.js"></script>

  <script>
    $(document).ready(function () {
        job_start_funcV1_01();
        selectingSelectionByUserLocation(userLocation)
    })

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
              '<"row"<"col-sm-6 col-md-6 text-start"l><"col-sm-5 col-md-5 text-end"i>>',
      lengthMenu: [[10, 50, 100, -1], [10, 50, 100, 'Barcha']],
      columns: [
        {title: '№', className: 'text-center', sortable: false, searchable: false, orderable: false, name: 'column0', data: null, render: function (data, type, row, meta) {return meta.row + meta.settings._iDisplayStart + 1 +'.'}},
        {title: 'F.I.O', className: 'text-left', name: 'column1', data: 'fullName'},
        {title: 'Summa', className: 'text-left', name: 'column2', data: 'id', render: (_, __, row) => {
            return formatNumberWithThousandsSeparator(row.paymentCost)+' (' + row.paymentCostType + ')</span>';
          }},
        {title: 'Tel', className: 'text-left', name: 'column3', data: 'phone'},
        {title: 'Chiqim', className: 'text-center', name: 'column4', data: 'outTime', render: function (data, type, row, meta) {
            const dateS = data ? new Date(data) : null;
            if (dateS) {
              const options = {year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit'};
              return new Intl.DateTimeFormat('uz-UZ', options).format(dateS);
            } else return '<button class="btn btn-sm btn-outline-success w-100" onclick="send_funcV1_02('+"'"+row.id+"'"+', '+"'"+row.fullName+"'"+')"><i class="bi bi-clipboard-check-fill"></i></button>';
          }},
        {title: 'Kirim', className: 'text-center', name: 'column5', data: 'inTime', render: function (data, type, row, meta) {
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
        {title: 'Xizmat', className: 'text-left', name: 'column6', data: 'serviceUzs', render: function (data, type, row, meta) {
            return formatNumberWithThousandsSeparator(data) + ' (uzs)';
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
      /**Kirimni saqlash**/
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

      Swal.fire({
        title: params_send_funcV1_01.fullName + ' маблағини қабул қилишни тасдиқлайсизми?',
        showDenyButton: true,
        confirmButtonText: 'Xa',
        denyButtonText: `Yo'q`,
      }).then((result) => {
        if (result.isConfirmed) {
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
    function send_funcV1_02(value, fullName) {
      /**Chiqimni tasdiqlash**/
      var params = {
        "value1" : value
      }
      Swal.fire({
        title: fullName + ' маблағини беришни тасдиқлайсизми?',
        showDenyButton: true,
        confirmButtonText: 'Xa',
        denyButtonText: `Yo'q`,
      }).then((result) => {
        if (result.isConfirmed) {
          $.ajax({
          type: "POST",
          url: '${pageContext.servletContext.contextPath}/route_v2/dataV4',
          data: params,
          // dataType: "json",
          async: true,
          // contentType: 'application/json',
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
                clear_form1_1()
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
      });
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
    function selectingSelectionByUserLocation(locationId) {
      var loc95To01 = '<option value="01">Toshkent</option>';
      var loc95To01Code = '01';
      var loc01To95 = '<option value="95">Mangit</option>';
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

    $(document).ready(function() {
      const table = $('#datatable1').DataTable();

      let clickStartTime;
      let clickTimeout;

      // Add a click event handler to rows
      $('#datatable1 tbody').on('mousedown', 'tr', function() {
        const row = table.row(this);
        clickStartTime = new Date().getTime();

        clickTimeout = setTimeout(function() {
          // Display an alert with the row ID
          alert('Row ID: ' + row.data()[0]);
        }, 3000);
      }).on('mouseup', 'tr', function() {
        clearTimeout(clickTimeout);
        const clickDuration = new Date().getTime() - clickStartTime;
        if (clickDuration < 3000) {
          // Handle normal click or release
        }
      });
    });
  </script>
</body>
</html>
