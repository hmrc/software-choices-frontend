$(document).ready($(function () {

  GOVUK.details.init();

}));

function clearField(id) {
   var element = document.getElementById(id);
   element.value = "";
   element.focus();
}