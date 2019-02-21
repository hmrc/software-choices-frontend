var typingTimer;
var doneTypingInterval = 500;
var $input = $('#term');

$input.on('keyup', function () {
  clearTimeout(typingTimer);
  typingTimer = setTimeout(submitAjaxForm, doneTypingInterval);
});

$input.on('keydown', function () {
  clearTimeout(typingTimer);
});


$('input[type="checkbox"]').on('click', function () {
  submitAjaxForm();
});


function submitAjaxForm() {

  var form = $("#searchForm");
  var url = form.attr('action') + "/ajax";

  $.ajax({
         type: "POST",
         url: url,
         data: form.serialize(), // serializes the form's elements.
         success: function(response)
         {
             $('#provider-results').replaceWith(response);
         }
       });
}
