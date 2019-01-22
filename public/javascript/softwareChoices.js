$(document).ready($(function () {

  GOVUK.details.init();

  $('[data-metrics]').each(function () {
    var metrics = $(this).attr('data-metrics');
    var parts = metrics.split(':');
    sendGAEvent(parts[0], parts[1], parts[2]);
  });

}));

function clearField(id) {
   var element = document.getElementById(id);
   element.value = "";
   element.focus();
}

function sendGAEvent(category, action, label) {
  ga('send', 'event', category, action, label);
}