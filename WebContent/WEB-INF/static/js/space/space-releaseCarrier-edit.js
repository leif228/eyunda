$(document).ready(function() {
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	$('select').select2();
	
	$("#editDialogForm").validate({
	    rules:{
	    	title:{
	        required:true,
	        minlength:2,
	        maxlength:20
	      }
	    },
	    errorClass: "help-inline",
	    errorElement: "span",
	    highlight: function(element, errorClass, validClass) {
	      $(element).parents('.control-group').addClass('error');
	    },
	    unhighlight: function(element, errorClass, validClass) {
	      $(element).parents('.control-group').removeClass('error');
	      $(element).parents('.control-group').addClass('success');
	    }
	});
});