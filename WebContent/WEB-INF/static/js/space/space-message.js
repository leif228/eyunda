$(document).ready(function() {
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	// 删除
	$(".btnDelete").live("click", function() {
		
		$("#delId").val($(this).attr("idVal"));
	
		$("#deleteDialog").modal("show");
		
		return true;
	});
	
	//查寻货物
	$('.findMessage').live("click",function (){
		$("#pageform").submit();
	});
	
	$(".btnShow").live("click", function() {
		window.location.href= _rootPath+'/space/message/show?id=' + $(this).attr("idVal");
		$(this).parent().prev("td").html("已读");
		return true;
	});
	
});