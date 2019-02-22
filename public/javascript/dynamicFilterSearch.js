var searchTrigger;
var doneTypingInterval = 250;

var input = $('#term');

input.on('keydown', createSearchTrigger);
input.on('keyup', createSearchTrigger);
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
    },
    error: function(err) {
      form.submit();
    }
  });

}
