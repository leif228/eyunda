$(document).ready(function() {
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();

	$("#addGasCompanyForm").validate({
		rules:{
			companyName:{
				required:true,
				minlength:2,
				maxlength:25
			},
			address:{
				required:true,
				minlength:2,
				maxlength:25
			},
			contact:{
				required:true,
				minlength:2,
				maxlength:25
			},
			mobile:{
				required:true,
				number:true,
				minlength: 11,
                maxlength: 11
			},
			accounter:{
				required:true,
				minlength:1,
				maxlength:25
			},
			accountNo:{
				required:true,
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
	
	// 添加
	$(".btnAdd").live("click", function() {
		$(".modal-body input").val("");
		$("#addId").val(0);
		$("#smallImage").attr("src", "");
		$("#bigImage").attr("src", "");
		$("#addGasCompanyDialog").modal("show");
		return true;
	});
	
	// 修改
	$(".btnEdit").live("click", function() {
		var id = $(this).attr("idVal");
		$.ajax({
			type : "GET",
			data : {id : id},
			url : _rootPath+"/manage/gas/gasCompany/edit",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var companyData = $(data)[0].companyData;
					var payStyles = $(data)[0].payStyleList;
					var saleTypes = $(data)[0].saleTypeList;
					var s = "";
					var st = "";

					$("#addId").val(id);
					$("#smallImage").attr("src", _rootPath + 
							"/download/imageDownload?url=" + companyData.smallImage);
					$("#bigImage").attr("src", _rootPath + 
							"/download/imageDownload?url=" + companyData.bigImage);
					
					$("#companyName").val(companyData.companyName);
					$("#address").val(companyData.address);
					$("#contact").val(companyData.contact);
					$("#mobile").val(companyData.mobile);
					$("#accounter").val(companyData.accounter);
					
					$.each(payStyles, function(i, payStyle){
						for(key in payStyle){
							if(key == companyData.payStyle)
								s += "  <option value=\"" + key + "\" selected>" + payStyle[key] + "</option>";
							else
								s += "  <option value=\"" + key + "\">" + payStyle[key] + "</option>";
						}
					});
					$.each(saleTypes, function(i, saleType){
						for(key in saleType){
							if(key == companyData.saleType)
								st += "  <option value=\"" + key + "\" selected>" + saleType[key] + "</option>";
							else
								st += "  <option value=\"" + key + "\">" + saleType[key] + "</option>";
						}
					});
					$("#payStyle").html(s);
					$("#saleType").html(st);
					$("#accountNo").val(companyData.accountNo);
					
					$("#addGasCompanyDialog").modal("show");
				}
			}
		});
		return true;
	});
	
	// 保存
	$(".saveGasCompany").live("click", function() {
		if($("#addGasCompanyForm").valid()){
			$("#addGasCompanyForm").ajaxSubmit({
				type : "POST",
				url : _rootPath+"/manage/gas/gasCompany/save",
				datatype : "json",
				success : function(data) {
					var redata = eval('(' + data + ')');
					var returnCode = redata.returnCode;
					var message = redata.message;
					if (returnCode == "Failure") {
						alert(message);
						return false;
					} else {
						window.location.href=_rootPath + "/manage/gas/gasCompany?pageNo="
							+ $("#addPageNo").val() + "&keyWords=" + $("#addKeyWords").val();
						return true;
					}
				}
			});
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
			url : _rootPath+"/manage/gas/gasCompany/delete",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					window.location.href=_rootPath + "/manage/gas/gasCompany?pageNo="
						+ $("#delPageNo").val() + "&keyWords=" + $("#delKeyWords").val();
				}
			}
		});
		return true;
	});

});