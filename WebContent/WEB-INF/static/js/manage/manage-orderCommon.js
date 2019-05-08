$(document).ready(function(){
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	$(".btnDelete").live("click", function() {	
		$("#deleteId").val($(this).attr("idVal"));		
		$("#deleteDialog").modal("show");
		return true;
	});
	
});