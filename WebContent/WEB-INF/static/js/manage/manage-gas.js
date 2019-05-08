$(document).ready(function() {
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$("#addGasForm").validate({
		rules:{
			waresName:{
				required:true,
				minlength:2,
				maxlength:25
			},
			stdPrice:{
				required:true,
				number:true
			},
			price:{
				required:true,
				number:true
			},
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
	
	// 添加
	$(".btnAdd").live("click", function() {
		$(".modal-body input").val("");
		$("#addId").val(0);
		$("#waresLogo").attr("src","");
		$("#addGasDialog").modal("show");
		return true;
	});
	
	// 修改
	$(".btnEdit").live("click", function() {
		var id = $(this).attr("idVal");
		$.ajax({
			method : "GET",
			data : {id : id},
			url : _rootPath+"/manage/gas/gas/edit",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var s = "";
					var gasWaresData = $(data)[0].gasWaresData;
					var companyDatas = $(data)[0].companyDatas;
					
					$("#addId").val(id);
					$("#waresLogo").attr("src", _rootPath + 
						"/download/imageDownload?url=" + gasWaresData.waresLogo);

					$("#waresName").val(gasWaresData.waresName);
					$("#subTitle").val(gasWaresData.subTitle);
					$("#description").val(gasWaresData.description);
					$("#stdPrice").val(gasWaresData.stdPrice);
					$("#price").val(gasWaresData.price);
					$("#priceSignal").val(gasWaresData.priceSignal);
					
					$.each(companyDatas, function(i, companyData){
						if(gasWaresData.companyId == companyData.id){
							s += "  <option value=\"" + companyData.id + "\" selected>" + companyData.companyName + "</option>";
						} else {
							s += "  <option value=\"" + companyData.id + "\">" + companyData.companyName + "</option>";
						}
					});
					$("#companyIdEdit").html(s);
					
					$("#addGasDialog").modal("show");
				}
			}
		});
		return true;
	});
	
	// 保存
	$(".saveGas").live("click", function() {
		if($("#addGasForm").valid()){
			$("#addGasForm").ajaxSubmit({
				type : "POST",
				url : _rootPath+"/manage/gas/gas/save",
				datatype : "json",
				success : function(data) {
					var redata = eval('('+data+')');
					var returnCode = redata.returnCode;
					var message = redata.message;
					if (returnCode == "Failure") {
						alert(message);
						return false;
					} else {
						window.location.href=_rootPath + "/manage/gas/gas?pageNo=" + $("#addPageNo").val() 
						+ "&companyId=" + $("#addCompanyId").val() + "&selectCode=" + $("#addStatus").val();
						return true;
					}
				}
			});
		}
	});
	
	// 上架
	$(".btnPublish").live("click", function() {
		$("#publishId").val($(this).attr("idVal"));
		$("#publishDialog").modal("show");

		return true;
	});
	
	// 确认上架
	$(".publish").live("click", function() {
		var id = $("#publishId").val();
		$.ajax({
			method : "GET",
			data : {id : id},
			url : _rootPath+"/manage/gas/gas/publish",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					$("#publishDialog").modal("hide");
					$("#button" + id).remove();
					var myDate = new Date()
					
					year = myDate.getFullYear();
					month = myDate.getMonth() + 1;
					day = myDate.getDate(); 
					
					var s = "";
					s += "<a class=\"btn btn-warning btnUnpublish\" idVal=\"" + id + "\">";
					s += "<i class=\"icon-chevron-down icon-white\"></i> 取消发布";
					s += "</a>";
					$("#desc" + id).html("已发布");
					$("#sellTime" + id).html(year + "-" + month + "-" + day);
                    $("#temp" + id).html(s);
				}
			}
		});
		return true;
	});
	
	// 下架
	$(".btnUnpublish").live("click", function() {
		$("#unpublishId").val($(this).attr("idVal"));
		$("#unpublishDialog").modal("show");

		return true;
	});
	
	// 确认下架
	$(".unpublish").live("click", function() {
		var id = $("#unpublishId").val();
		$.ajax({
			method : "GET",
			data : {id : id},
			url : _rootPath+"/manage/gas/gas/unpublish",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					$("#unpublishDialog").modal("hide");
					$("#button" + id).remove();
					var myDate = new Date()
					
					year = myDate.getFullYear();
					month = myDate.getMonth() + 1;
					day = myDate.getDate(); 
					
					var s = "";
					s += "<a class=\"btn btn-success btnPublish\" idVal=\"" + id + "\">";
					s += "  <i class=\"icon-chevron-up icon-white\"></i> 发布";
					s += "</a> ";
					s += "<a class=\"btn btn-primary btnEdit\" idVal=\"" + id + "\">";
					s += "  <i class=\"icon-pencil icon-white\"></i> 修改";
					s += "</a> ";
					s += "<a class=\"btn btn-danger btnDelete\" idVal=\""+ id +"\">";
					s += "  <i class=\"icon-trash icon-white\"></i> 删除";
            		s += "</a>";
            		$("#desc" + id).html("未发布");
            		$("#sellTime" + id).html(year + "-" + month + "-" + day);
                    $("#temp" + id).html(s);
				}
			}
		});
		return true;
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
			url : _rootPath+"/manage/gas/gas/delete",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					window.location.href=_rootPath + "/manage/gas/gas?pageNo=" + $("#delPageNo").val() 
					+ "&companyId=" + $("#delCompanyId").val() + "&selectCode=" + $("#delStatus").val();
					
					return true;
				}
			}
		});
		return true;
	});

});