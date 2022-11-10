'use strict'

$('#provider-table-container').on('click', 'details', function() {
    if ($(this).find('div').is(':empty')) {

        showLoading(this);
        ajaxRequest(this);

    } else {

         $(this).find('.govuk-details__summary').attr("aria-expanded", false);
         $(this).find('.govuk-details__text').attr("aria-hidden", true);
    }
});

function showLoading(element) {
    $(element).find('div').empty().append('<div class="waiting"></div>');
}

function ajaxRequest(element) {

  $.ajax({
    type: "GET",
    url: $("#searchForm").attr('action') + "/ajaxProvider?providerName=" + encodeURIComponent($(element).find('.govuk-details__summary-text').text()),
    success: function(result) {

        $(element).find('div').empty().append(result);

        $(element).find('.govuk-details__summary').attr("aria-expanded", true);
        $(element).find('.govuk-details__text').attr("aria-hidden", false);

    },
    error: function(e) {
        $(element).find('div').empty().append('<div>Provider lookup failure.</div>');

        $(element).find('.govuk-details__summary').attr("aria-expanded", true);
        $(element).find('.govuk-details__text').attr("aria-hidden", false);
    }
  });
}