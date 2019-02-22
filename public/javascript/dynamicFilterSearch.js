var typingTimer;
var doneTypingInterval = 500;

var input = $('#term');

input.on('keyup', function () {
  clearTimeout(typingTimer);
  typingTimer = setTimeout(submitAjaxForm, doneTypingInterval);
});

input.on('keydown', function () {
  clearTimeout(typingTimer);
  showLoadingIcon();
});


$('input[type="checkbox"]').on('click', function () {
  submitAjaxForm();
});

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
