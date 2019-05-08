$(document).ready(function() {
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	$("#orderType").live("change",function(){
		var v = $("#orderType").find("option:selected").val();
		if (v == "") {
			return;
		} else {
			window.location.href = _rootPath + "/space/cabin/editCabin?cabinId=0&orderType=" + v;
		}
	});
	
	$(".edit").live("click", function(){
		window.location.href = _rootPath + "/space/cabin/editCabin?cabinId=" + $(this).attr("idVal");
	})
	
	// 共享
	$(".publish").live("click", function() {
		$("#pubId").val($(this).attr("idVal"));
		$("#publishDialog").modal("show");

		return true;
	});
	
	// 共享
	$(".btnpub").live("click", function() {
		var id = $("#pubId").val();
		$.ajax({
			type : "GET",
			url : _rootPath+"/space/cabin/publish",
			data : {cabinId : id},
			datatype : "json",
			success : function(data){
				$("#publishDialog").modal("hide");
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if(returnCode == "Failure"){
					alert(messsage);
				} else {
					$("#status" + id).html("已共享");
					$("#btnShow" + id).html("");
					
					var str = "";
					str += "<a class=\"btn btn-warning unpublish\" idVal=\""+ id + "\">";
					str += "  <i class=\"icon icon-chevron-down icon-white\" title=\"取消共享\"></i>";
					str += "</a>"; 
					
					$("#btnShow" + id).html(str);
				}
			}
		})
	});
	
	// 取消共享
	$(".unpublish").live("click", function() {
		$("#unpubId").val($(this).attr("idVal"));
		$("#unpublishDialog").modal("show");
		return true;
	});
	
	// 取消共享
	$(".btnunpub").live("click", function() {
		var id = $("#unpubId").val();
		$.ajax({
			type : "GET",
			url : _rootPath+"/space/cabin/unpublish",
			data : {cabinId : id},
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				$("#unpublishDialog").modal("hide");
				if(returnCode == "Failure"){
					alert(message);
				} else {
					$("#btnShow" + id).html("");
					
					$("#status" + id).html("未共享");
					
					var str = "";
					str += "<a class=\"btn btn-warning edit\" idVal=\""+ id + "\">";
					str += "<i class=\"icon icon-pencil icon-white\" title=\"修改\"></i>";
					str += "</a> ";
					
					str += "<a class=\"btn btn-success publish\" idVal=\""+ id + "\">";
					str += "  <i class=\"icon icon-chevron-up icon-white\" title=\"共享\"></i>";
					str += "</a> "; 
					
					str += "<a class=\"btn btn-danger delete\" idVal=\""+ id + "\">";
					str += "<i class=\"icon icon-trash icon-white\" title=\"删除\"></i>";
					str += "</a>";
					
					$("#btnShow" + id).html(str);
				}
			}
		})
	});
	
	// 删除
	$(".delete").live("click", function() {
		$("#delId").val($(this).attr("idVal"));
		$("#deleteDialog").modal("show");
	});
	
	$(".btndel").live("click", function() {
		var id = $("#delId").val();
		$.ajax({
			type : "DELETE",
			url : _rootPath+"/space/cabin/deleteCabin?cabinId=" + id,
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				$("#deleteDialog").modal("hide");
				if(returnCode == "Failure"){
					alert(message);
				} else {
					window.location.href = _rootPath + "/space/cabin/myCabin";
				}
			}
		})
	});
	
});