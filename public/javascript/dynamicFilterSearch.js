'use strict';

$('details').show();

var searchTrigger;
var doneTypingInterval = 500;

var input = $('#term');

input.on('input', createSearchTrigger);
$('input[type="checkbox"]').on('click', createSearchTrigger);

function createSearchTrigger() {
  clearTimeout(searchTrigger);
  showLoadingIcon();
  searchTrigger = setTimeout(submitAjaxForm, doneTypingInterval);
}

function clearErrors() {
  $('#error-summary-display').remove();
  $('.error-message').remove();
  $('.form-field--error').removeClass("form-field--error");
  $('title').html($('title').html().split(": ")[1]);
}

function showLoadingIcon() {
  $('#provider-results').empty().addClass("waiting");
}

function submitAjaxForm() {

  var form = $("#searchForm");
  var url = form.attr('action') + "/ajax";

  $.ajax({
    type: "POST",
    url: url,
    beforeSend: function() {
      clearErrors();
      showLoadingIcon();
    },
    data: form.serialize(),
    success: function(response) {
      $('#provider-results').replaceWith(response);

      //this resets the GOVUK details polyfill, prevents creating reduntant listeners and runs it again to update new details
      GOVUK.details.started = false;
      GOVUK.details.addClickEvent = function() {}
      GOVUK.details.addDetailsPolyfill();
      $('details').show();
    },
    error: function(err) {
      form.submit();
    }
  });

}
