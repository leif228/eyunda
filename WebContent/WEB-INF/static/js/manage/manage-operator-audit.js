$(document).ready(function() {

	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	// 删除
	$(".btnDelete").live("click", function() {
		$("#deleteId").val($(this).attr("idVal"));

		$("#deleteDialog").modal("show");

		return true;
	});

	// 取消
	$(".btnUnaudit").live("click", function() {

		$("#unauditId").val($(this).attr("idVal"));

		$("#unauditDialog").modal("show");

		return true;
	});

	// 审核
	$(".btnAudit").live("click", function() {

		$("#auditId").val($(this).attr("idVal"));

		$("#auditDialog").modal("show");

		return true;
	});

	//查看
	$(".btnShow").live("click", function() {
		var id = $(this).attr("idVal");
		$.ajax({
			method : "GET",
			url : _rootPath+"/manage/member/operator/" + id,
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					
					var prefix = _rootPath + "/download/imageDownload?url=";
					var url = _rootPath + "/manage/member/operator/showImage?id=" + id +"&url="
					
					var year = $(data)[0].operData.applyTime.year;
					var month = $(data)[0].operData.applyTime.month + 1;
					if(month<10) month="0"+month;
					var day = $(data)[0].operData.applyTime.dayOfMonth;
					if(day<10) day="0"+day;
					
					$("#_status").val($(data)[0].operData.status);
					$("#_applyTime").val(year + "-" + month + "-" + day);

					$("#_loginName").val($(data)[0].operData.userData.loginName);
					$("#_trueName").val($(data)[0].operData.userData.trueName);
					$("#_mobile").val($(data)[0].operData.userData.mobile);
					$("#_email").val($(data)[0].operData.userData.email);
					$("#_unitAddr").val($(data)[0].operData.userData.unitAddr);

					$("#_legalPerson").val($(data)[0].operData.legalPerson);
					
					var imgIdCardFront= $(data)[0].operData.idCardFront;
					$("#idCardFront").attr("src", prefix + imgIdCardFront);
					$("#idCardFrontLink").attr("href", url + imgIdCardFront);
					
					var imgIdCardBack= $(data)[0].operData.idCardBack;
					$("#idCardBack").attr("src", prefix + imgIdCardBack);
					$("#idCardBackLink").attr("href", url + imgIdCardBack);
					
					var imgBusiLicence= $(data)[0].operData.busiLicence;
					$("#busiLicence").attr("src", prefix + imgBusiLicence);
					$("#busiLicenceLink").attr("href", url + imgBusiLicence);
					
					var imgTaxLicence= $(data)[0].operData.taxLicence;
					$("#taxLicence").attr("src", prefix + imgTaxLicence);
					$("#taxLicenceLink").attr("href", url + imgTaxLicence);
					
					$("#showDialog").modal("show");
				}
			}
		});
		
		return true;
	});
	
});