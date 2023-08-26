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


    /**12**/

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
    var parts = number.toFixed(3).split(".");
    var integerPart = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, " ");
    var formattedNumber = integerPart + "." + parts[1];

    return formattedNumber;
}



