'use strict';

$('#provider-table-container').on('click', 'details', function() {
    if ($(this).find('div').is(':empty')) {
        showLoading(this);
        ajaxRequest(this);
    }
});

function showLoading(element) {
    $(element).find('div').empty().append('<div class="waiting"></div>');
}

function ajaxRequest(element) {
  $.ajax({
    type: "GET",
    url: $("#searchForm").attr('action') + "/ajaxProvider?providerName=" + $(element).find('.summary').text(),
    success: function(result) {
        $(element).find('div').empty().append(result);
    },
    error: function(e) {
        $(element).find('div').empty().append('<div>Provider lookup failure.</div>');
    }
  });
}