$(document).ready(function() {
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	// 添加对话框
	$("#selectCode").live("change", function() {
		selectCode = $(this).val()
		window.location.href=_rootPath+"/space/release/myRelease?selectCode="+selectCode;
	});
	
	// 提交添加
	$(".btnAdd").live("click", function() {
		selectCode = $("#selectCode").val()
		window.location.href = _rootPath+"/space/release/myRelease/addRelease?id=0&selectCode="+selectCode;
	});
	
	// 发布
	$(".publish").live("click", function() {
		$("#publishId").val($(this).attr("idVal"));
		$("#publishDialog").modal("show");

		return true;
	});
	
	// 发布
	$(".btnpublish").live("click", function() {
		var id = $("#publishId").val();
		$.ajax({
			method : "get",
			url : _rootPath+"/space/release/myRelease/publish",
			data : {id : id},
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == "Failurl"){
					message = $(data)[0].message;
					alert(messsage);
				} else {
					$("#publishDialog").modal("hide");
					$("#btnShow" + id).remove();
					
					var myDate = new Date()
					
					year = myDate.getFullYear();
					month = myDate.getMonth() + 1;
					day = myDate.getDate(); 
					$("#relt" + id).html(year + "-" + p(month) + "-" + p(day));
					$("#desc" + id).html("已发布");
					
					var str = "";
					str += "<a class=\"btn btn-warning unpublish\" idVal=\""+ id + "\">";
					str += "  <i class=\"icon icon-pencil icon-white\"></i>取消发布";
					str += "</a>"; 
					
					$("#btnTemp" + id).html(str);
				}
			}
		})
	});
	
	// 取消发布
	$(".unpublish").live("click", function() {
		$("#unpublishId").val($(this).attr("idVal"));
		
		$("#unpublishDialog").modal("show");
		return true;
	});
	
	// 取消发布
	$(".btnUnpublish").live("click", function() {
		var id = $("#unpublishId").val();
		$.ajax({
			method : "get",
			url : _rootPath+"/space/release/myRelease/unpublish",
			data : {id : id},
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				if(returnCode == "Failurl"){
					message = $(data)[0].message;
					alert(messsage);
				} else {
					$("#unpublishDialog").modal("hide");
					$("#btnShow" + id).remove();
					
					$("#relt" + id).html("");
					$("#desc" + id).html("未发布");
					
					var str = "";
					str += "<a class=\"btn btn-success publish\" idVal=\""+ id + "\">";
					str += "  <i class=\"icon icon-flag icon-white\"></i>发布";
					str += "</a> "; 
					str += "<a class=\"btn btn-warning edit\" idVal=\""+ id + "\">";
					str += "<i class=\"icon icon-pencil icon-white\"></i>修改";
					str += "</a> ";
					str += "<a class=\"btn btn-danger delete\" idVal=\""+ id + "\">";
					str += "<i class=\"icon icon-trash icon-white\"></i>删除";
					str += "</a>";
					
					$("#btnTemp" + id).html(str);
				}
			}
		})
	});
	
	// 删除
	$(".delete").live("click", function() {
		$("#id").val($(this).attr("idVal"));
		$("#deleteDialog").modal("show");
	});
	
	// 修改
	$(".edit").live("click", function() {
		window.location.href = _rootPath+"/space/release/myRelease/addRelease?id="
		+ $(this).attr("idVal") + "&pageNo=" + $("#pageNo").val() + "&selectCode=" + $("#selectCode").val();
	});
	
	// 刷新
	$(".btnRefresh").live("click", function() {
		var selectCode = $("#selectCode").val();
		window.location.href = _rootPath+"/space/release/myRelease/refresh?selectCode=" + selectCode;
	});
	
	function p(s){
		return s < 10 ? "0" + s : s;	
	}
	
});