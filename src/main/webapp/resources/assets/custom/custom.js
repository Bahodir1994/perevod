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
