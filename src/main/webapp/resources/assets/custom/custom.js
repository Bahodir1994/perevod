document.addEventListener("DOMContentLoaded", function () {
    var currencyMasks = document.getElementsByClassName('currency-mask');

    Array.from(currencyMasks).forEach(function (currencyMaskElement) {
        var currencyMask = IMask(
            currencyMaskElement,
            {
                mask: 'num',
                blocks: {
                    num: {
                        mask: Number,
                        thousandsSeparator: ' '
                    }
                }
            }
        ).on('accept', function() {
            var currencyValueElement = currencyMaskElement.nextElementSibling;
            currencyValueElement.innerHTML = currencyMask.masked.unmaskedValue || '-';
        });
    });
});

/**Setter selected currency Type icon to span on left input form currency**/
$(document).ready(function() {
    var currencyIconValue = $('.moneyType').val();
    $('.currencyIcon').html(currencyIconValue);

    $('.moneyType').change(function() {
        var newCurrencyIconValue = $(this).val();
        $('.currencyIcon').html(newCurrencyIconValue);
    });
});


/**Clock time and Date**/
$(document).ready(function() {
    time();

    $('.format-currency').on('input', function() {
        var inputValue = $(this).val().replace(/\s/g, ''); // Убираем пробелы
        if (!isNaN(inputValue)) {
            var formattedValue = formatNumberWithThousandsSeparator(inputValue);
            $(this).val(formattedValue);
        }
    });
});

function time() {
    var d = new Date();
    var s = d.getSeconds();
    var m = d.getMinutes();
    var h = d.getHours();
    $('.timerAndDate').html(("0" + h).substr(-2) + ":" + ("0" + m).substr(-2) + ":" + ("0" + s).substr(-2));
}

setInterval(time, 1000);


function formatNumberWithThousandsSeparator(number) {
    var integerPart = Math.floor(number).toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
    return integerPart;
}




