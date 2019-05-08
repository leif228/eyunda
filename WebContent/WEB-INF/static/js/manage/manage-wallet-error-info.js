$(document).ready(function() {
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
    // 查看
//	$(".btnShow").live("click", function() {
//		$.ajax({
//			method : "GET",
//			data : {logNo : $(this).attr("data-logno")},
//			url : _rootPath+"/manage/wallet/getBankZjjzInfo",
//			datatype : "json",
//			success : function(data) {
//				var returnCode = $(data)[0].returnCode;
//				var message = $(data)[0].message;
//				if (returnCode == "Failure") {
//					alert(message);
//					return false;
//				} else {
//					//读入数据，并填入
//					
//					$("#showDialog").modal("show");
//				}
//			}
//		});
//		return true;
//	});
	
	// 重新发送
	$(".resend").live("click", function() {
		$("#logNo").val($(this).attr("data-logno"))
		$("#resendDialog").modal("show");
	});
	
	$(".btnSend").live("click", function() {
		$.ajax({
			type : "POST",
			data : {logNo : $("#logNo").val()},
			url : _rootPath+"/manage/wallet/resend",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				$("#resendDialog").modal("hide");
				alert(message);
				if (returnCode == "Failure") {
					return false;
				} else {
					//读入数据，并填入
					window.location.reload;
				}
			}
		});
		return true;
	});
	
	// 重新发送
	$(".update").live("click", function() {
		$("#ulogNo").val($(this).attr("data-logno"))
		$("#updateDialog").modal("show");
	});
	
	$(".btnUpdate").live("click", function() {
		$.ajax({
			type : "POST",
			data : {logNo : $("#ulogNo").val()},
			url : _rootPath+"/manage/wallet/update",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				$("#updateDialog").modal("hide");
				alert(message);
				if (returnCode == "Failure") {
					return false;
				} else {
					//读入数据，并填入
					window.location.reload;
				}
			}
		});
		return true;
	});


});
