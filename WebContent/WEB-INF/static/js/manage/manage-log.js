$(document).ready(function(){
	var btnDelete = $(".btnDelete");// 删除按纽
	// 删除
	btnDelete.live("click", function() {
		$("#delLogId").val($(this).attr("idVal"));
		
		$("#deleteDialog").modal("show");
		
		return true;
	});
});
