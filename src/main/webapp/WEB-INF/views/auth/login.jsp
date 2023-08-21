<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <!-- Required Meta Tags Always Come First -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <!-- Title -->
  <title>Basic Log In | Front - Admin &amp; Dashboard Template</title>

  <!-- Favicon -->
  <link rel="shortcut icon" href="${pageContext.servletContext.contextPath}/resources/assets/img/favicon.ico">

  <!-- Font -->
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap" rel="stylesheet">

  <!-- CSS Implementing Plugins -->
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/assets/vendor/bootstrap-icons/font/bootstrap-icons.css">

  <!-- CSS Front Template -->

<%--  <link rel="preload" href="${pageContext.servletContext.contextPath}/resources/assets/css/theme.min.css" data-hs-appearance="default" as="style">--%>
  <link rel="preload" href="${pageContext.servletContext.contextPath}/resources/assets/css/theme-dark.min.css" data-hs-appearance="default" as="style">

  <style data-hs-appearance-onload-styles>
    *
    {
      transition: unset !important;
    }

    body
    {
      opacity: 0;
    }
  </style>

  <script>
    window.hs_config = {"autopath":"@@autopath","deleteLine":"hs-builder:delete","deleteLine:build":"hs-builder:build-delete","deleteLine:dist":"hs-builder:dist-delete","previewMode":false,"startPath":"/index.html","vars":{"themeFont":"https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap","version":"?v=1.0"},"layoutBuilder":{"extend":{"switcherSupport":true},"header":{"layoutMode":"default","containerMode":"container-fluid"},"sidebarLayout":"default"},"themeAppearance":{"layoutSkin":"default","sidebarSkin":"default","styles":{"colors":{"primary":"#377dff","transparent":"transparent","white":"#fff","dark":"132144","gray":{"100":"#f9fafc","900":"#1e2022"}},"font":"Inter"}},"languageDirection":{"lang":"en"},"skipFilesFromBundle":{"dist":["assets/js/hs.theme-appearance.js","assets/js/hs.theme-appearance-charts.js","assets/js/demo.js"],"build":["assets/css/theme.css","assets/vendor/hs-navbar-vertical-aside/dist/hs-navbar-vertical-aside-mini-cache.js","assets/js/demo.js","assets/css/theme-dark.css","assets/css/docs.css","assets/vendor/icon-set/style.css","assets/js/hs.theme-appearance.js","assets/js/hs.theme-appearance-charts.js","node_modules/chartjs-plugin-datalabels/dist/chartjs-plugin-datalabels.min.js","assets/js/demo.js"]},"minifyCSSFiles":["assets/css/theme.css","assets/css/theme-dark.css"],"copyDependencies":{"dist":{"*assets/js/theme-custom.js":""},"build":{"*assets/js/theme-custom.js":"","node_modules/bootstrap-icons/font/*fonts/**":"assets/css"}},"buildFolder":"","replacePathsToCDN":{},"directoryNames":{"src":"./src","dist":"./dist","build":"./build"},"fileNames":{"dist":{"js":"theme.min.js","css":"theme.min.css"},"build":{"css":"theme.min.css","js":"theme.min.js","vendorCSS":"vendor.min.css","vendorJS":"vendor.min.js"}},"fileTypes":"jpg|png|svg|mp4|webm|ogv|json"}
    window.hs_config.gulpRGBA = (p1) => {
      const options = p1.split(',')
      const hex = options[0].toString()
      const transparent = options[1].toString()

      var c;
      if(/^#([A-Fa-f0-9]{3}){1,2}$/.test(hex)){
        c= hex.substring(1).split('');
        if(c.length== 3){
          c= [c[0], c[0], c[1], c[1], c[2], c[2]];
        }
        c= '0x'+c.join('');
        return 'rgba('+[(c>>16)&255, (c>>8)&255, c&255].join(',')+',' + transparent + ')';
      }
      throw new Error('Bad Hex');
    }
    window.hs_config.gulpDarken = (p1) => {
      const options = p1.split(',')

      let col = options[0].toString()
      let amt = -parseInt(options[1])
      var usePound = false

      if (col[0] == "#") {
        col = col.slice(1)
        usePound = true
      }
      var num = parseInt(col, 16)
      var r = (num >> 16) + amt
      if (r > 255) {
        r = 255
      } else if (r < 0) {
        r = 0
      }
      var b = ((num >> 8) & 0x00FF) + amt
      if (b > 255) {
        b = 255
      } else if (b < 0) {
        b = 0
      }
      var g = (num & 0x0000FF) + amt
      if (g > 255) {
        g = 255
      } else if (g < 0) {
        g = 0
      }
      return (usePound ? "#" : "") + (g | (b << 8) | (r << 16)).toString(16)
    }
    window.hs_config.gulpLighten = (p1) => {
      const options = p1.split(',')

      let col = options[0].toString()
      let amt = parseInt(options[1])
      var usePound = false

      if (col[0] == "#") {
        col = col.slice(1)
        usePound = true
      }
      var num = parseInt(col, 16)
      var r = (num >> 16) + amt
      if (r > 255) {
        r = 255
      } else if (r < 0) {
        r = 0
      }
      var b = ((num >> 8) & 0x00FF) + amt
      if (b > 255) {
        b = 255
      } else if (b < 0) {
        b = 0
      }
      var g = (num & 0x0000FF) + amt
      if (g > 255) {
        g = 255
      } else if (g < 0) {
        g = 0
      }
      return (usePound ? "#" : "") + (g | (b << 8) | (r << 16)).toString(16)
    }
  </script>
</head>
<body>

<script src="${pageContext.servletContext.contextPath}/resources/assets/js/hs.theme-appearance.js"></script>

<!-- ========== MAIN CONTENT ========== -->
<main id="content" role="main" class="main">
  <div class="position-fixed top-0 end-0 start-0 bg-img-start" style="height: 32rem; background-image: url(${pageContext.servletContext.contextPath}/resources/assets/svg/components/card-6.svg);">
    <!-- Shape -->
    <div class="shape shape-bottom zi-1">
      <svg preserveAspectRatio="none" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" viewBox="0 0 1921 273">
        <polygon fill="#fff" points="0,273 1921,273 1921,0 " />
      </svg>
    </div>
    <!-- End Shape -->
  </div>

  <!-- Content -->
  <div class="container py-5 py-sm-7">
    <a class="d-flex justify-content-center mb-5" href="${pageContext.servletContext.contextPath}/">
      <img class="zi-2" src="${pageContext.servletContext.contextPath}/resources/assets/svg/logos/logo.svg" alt="Image Description" style="width: 8rem;">
    </a>

    <div class="mx-auto" style="max-width: 30rem;">
      <!-- Card -->
      <div class="card card-lg mb-5">
        <div class="card-body">
          <!-- Form -->
          <form class="js-validate needs-validation" action="${pageContext.servletContext.contextPath}/enter_app/sign_in" method="post" novalidate>
            <div class="text-center">
              <div class="mb-5">
                <h1 class="display-5">Тизимга кириш</h1>
                <p>Профилингиз бўлмаса <a class="link" href="./authentication-signup-basic.html">Рўйхатдан ўтинг</a></p>
              </div>

              <div class="d-grid mb-4">
                <a class="btn btn-white btn-lg" href="#">
                      <span class="d-flex justify-content-center align-items-center">
                        <img class="avatar avatar-xss me-2" src="${pageContext.servletContext.contextPath}/resources/assets/svg/brands/google-icon.svg" alt="Image Description">
                        Google орқали кириш
                      </span>
                </a>
              </div>

              <span class="divider-center text-muted mb-4">Ёки</span>
            </div>

            <!-- Form -->
            <div class="mb-4">
              <label class="form-label" for="signinSrEmail">Логин</label>
              <input type="text" class="form-control form-control-lg" name="username" id="signinSrEmail" tabindex="1" placeholder="username" aria-label="user" required>
              <span class="invalid-feedback">Please enter a valid email address.</span>
            </div>
            <!-- End Form -->

            <!-- Form -->
            <div class="mb-4">
              <label class="form-label w-100" for="signupSrPassword" tabindex="0">
                    <span class="d-flex justify-content-between align-items-center">
                      <span>Парол</span>
                      <a class="form-label-link mb-0" href="./authentication-reset-password-basic.html">Паролингизни унутдингизми?</a>
                    </span>
              </label>

              <div class="input-group input-group-merge" data-hs-validation-validate-class>
                <input type="password" class="js-toggle-password form-control form-control-lg" name="password" id="signupSrPassword" placeholder="8+ characters required" aria-label="8+ characters required" required minlength="8" data-hs-toggle-password-options='{
                             "target": "#changePassTarget",
                             "defaultClass": "bi-eye-slash",
                             "showClass": "bi-eye",
                             "classChangeTarget": "#changePassIcon"
                           }'>
                <a id="changePassTarget" class="input-group-append input-group-text" href="javascript:;">
                  <i id="changePassIcon" class="bi-eye"></i>
                </a>
              </div>

              <span class="invalid-feedback">Please enter a valid password.</span>
            </div>
            <!-- End Form -->

            <!-- Form Check -->
            <div class="form-check mb-4">
              <input class="form-check-input" type="checkbox" value="" id="termsCheckbox">
              <label class="form-check-label" for="termsCheckbox">
                Сақлаб қолиш
              </label>
            </div>
            <!-- End Form Check -->

            <div class="d-grid">
              <button type="submit" class="btn btn-primary btn-lg">кириш</button>
            </div>
          </form>
          <!-- End Form -->
        </div>
      </div>
      <!-- End Card -->

      <!-- Footer -->
      <div class="position-relative text-center zi-1">
        <small class="text-cap text-body mb-4">Интеграциялашган тизимлар</small>

        <div class="w-85 mx-auto">
          <div class="row justify-content-between">
            <div class="col">
              <img class="img-fluid" src="${pageContext.servletContext.contextPath}/resources/assets/svg/brands/gitlab-gray.svg" alt="Logo">
            </div>
            <!-- End Col -->

            <div class="col">
              <img class="img-fluid" src="${pageContext.servletContext.contextPath}/resources/assets/svg/brands/fitbit-gray.svg" alt="Logo">
            </div>
            <!-- End Col -->

            <div class="col">
              <img class="img-fluid" src="${pageContext.servletContext.contextPath}/resources/assets/svg/brands/flow-xo-gray.svg" alt="Logo">
            </div>
            <!-- End Col -->

            <div class="col">
              <img class="img-fluid" src="${pageContext.servletContext.contextPath}/resources/assets/svg/brands/layar-gray.svg" alt="Logo">
            </div>
            <!-- End Col -->
          </div>
          <!-- End Row -->
        </div>
      </div>
      <!-- End Footer -->
    </div>
  </div>
  <!-- End Content -->
</main>
<!-- ========== END MAIN CONTENT ========== -->

<!-- JS Global Compulsory  -->
<script src="${pageContext.servletContext.contextPath}/resources/assets/vendor/jquery/dist/jquery.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/assets/vendor/jquery-migrate/dist/jquery-migrate.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/assets/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

<!-- JS Implementing Plugins -->
<script src="${pageContext.servletContext.contextPath}/resources/assets/vendor/hs-toggle-password/dist/js/hs-toggle-password.js"></script>

<!-- JS Front -->
<script src="${pageContext.servletContext.contextPath}/resources/assets/js/theme.min.js"></script>

<!-- JS Plugins Init. -->
<script>
  function signIn() {
    let username = $('input#username').val();
    let password = $('input#password').val();
    var dataS = {}
    var tipform = "<%=request.getContextPath()%>/enter_app/sign_in/" + username + "/" + password;
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


  (function() {
    window.onload = function () {
      // INITIALIZATION OF BOOTSTRAP VALIDATION
      // =======================================================
      // HSBsValidation.init('.js-validate', {
      //   onSubmit: data => {
      //     data.event.preventDefault()
      //     alert('Submited')
      //   }
      // })


      // INITIALIZATION OF TOGGLE PASSWORD
      // =======================================================
      new HSTogglePassword('.js-toggle-password')
    }
  })()
</script>
<script>
  (function () {
    // STYLE SWITCHER
    // =======================================================
    const $dropdownBtn = document.getElementById('selectThemeDropdown') // Dropdowon trigger
    const $variants = document.querySelectorAll(`[aria-labelledby="selectThemeDropdown"] [data-icon]`) // All items of the dropdown

    // Function to set active style in the dorpdown menu and set icon for dropdown trigger
    const setActiveStyle = function () {
      $variants.forEach($item => {
        if ($item.getAttribute('data-value') === HSThemeAppearance.getOriginalAppearance()) {
          $dropdownBtn.innerHTML = `<i class="bi-moon" />`
          return $item.classList.add('active')
        }

        $item.classList.remove('active')
      })
    }

    // Add a click event to all items of the dropdown to set the style
    $variants.forEach(function ($item) {
      $item.addEventListener('click', function () {
        HSThemeAppearance.setAppearance($item.getAttribute('data-value'))
      })
    })

    // Call the setActiveStyle on load page
    setActiveStyle()

    // Add event listener on change style to call the setActiveStyle function
    window.addEventListener('on-hs-appearance-change', function () {
      setActiveStyle()
    })
  })()
</script>
</body>
</html>