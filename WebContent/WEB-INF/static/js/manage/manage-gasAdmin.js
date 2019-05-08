$(document).ready(function() {
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();

	$("#addGasAdminForm").validate({
		rules:{
			loginName:{
				required:true,
				minlength:2,
				maxlength:25
			},
			stationName:{
				required:true,
				minlength:2,
				maxlength:25
			}
		},
		errorClass: "help-inline",
		errorElement: "span",
		highlight:function(element, errorClass, validClass) {
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight: function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
			$(element).parents('.control-group').addClass('success');
		}
	});

	
	// 删除
	$(".btnDelete").live("click", function() {
		$("#deleteId").val($(this).attr("idVal"));
		$("#deleteDialog").modal("show");

		return true;
	});
	
	// 删除
	$(".delete").live("click", function() {
		var id = $("#deleteId").val();
		$.ajax({
			method : "GET",
			data : {id : id},
			url : _rootPath+"/manage/gas/gasAdmin/delete",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					window.location.href=_rootPath + "/manage/gas/gasAdmin?pageNo="
						+ $("#delPageNo").val() + "&keyWords=" + $("#delKeyWords").val();
				}
			}
		});
		return true;
	});

	// 修改
	$(".btnEdit").live("click", function() {
		var id = $(this).attr("idVal");
		$.ajax({
			method : "GET",
			data : {id : id},
			url : _rootPath+"/manage/gas/gasAdmin/edit",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var adminData = $(data)[0].adminData;
					var companyDatas = $(data)[0].companyDatas;
					var s = "";
					$("#addId").val(id);
					$("#loginName").val(adminData.adminData.loginName);
					
					$.each(companyDatas, function(i, companyData){
						if(adminData.companyId == companyData.id)
							s += "  <option value=\"" + companyData.id + "\" selected>" + companyData.companyName + "</option>";
						else
							s += "  <option value=\"" + companyData.id + "\">" + companyData.companyName + "</option>";
					});
					$("#companyId").html(s);
					
					$("#stationName").val(adminData.stationName);
					$("#addGasAdminDialog").modal("show");
					return true;
				}
			}
		});
		
		return true;
	});
	
	// 添加
	$(".btnAdd").live("click", function() {
		$(".modal-body input").val("");
		$("#addGasAdminDialog").modal("show");
		return true;
	});
	
	// 添加
	$(".addGasAdmin").live("click", function() {
		if($("#addGasAdminForm").valid()){
			$("#addGasAdminForm").ajaxSubmit({
				type : "POST",
				url : _rootPath+"/manage/gas/gasAdmin/save",
				datatype : "json",
				success : function(data) {
					var returnCode = $(data)[0].returnCode;
					var message = $(data)[0].message;
					if (returnCode == "Failure") {
						alert(message);
						return false;
					} else {
						window.location.href=_rootPath + "/manage/gas/gasAdmin?pageNo="
							+ $("#addPageNo").val() + "&keyWords=" + $("#addKeyWords").val();
						return true;
					}
				}
			});
		}
	});
	
});