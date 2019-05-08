$(document).ready(function(){
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	// 删除
	$(".btnDeleteCargo").live("click", function() {
		$("#delCargoId").val($(this).attr("idVal"));
		$("#searchKeyWords").val($("#keyWords").val());
		$("#delPageNo").val($("#pageNo").val());
		
		$("#deleteCargoDialog").modal("show");
		
		return true;
	});

});
