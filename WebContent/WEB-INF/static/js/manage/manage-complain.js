$(document).ready(function(){
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	// 回复对话框
	$(".btnReply").live("click", function() {
		$.ajax({
			method : "GET",
			data :  {id : $(this).attr("idval")},
			url : _rootPath + "/manage/complain/complainInfo/show",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "FAILURE") {
					alert(message);
					return false;
				} else {
					// 显示信息
					var complainData = $(data)[0].complainData;
					
					$("#complainId").val(complainData.id);
					
					if(complainData.userData.trueName == '')
						$("#userName").val(complainData.userData.trueName);
					else
						$("#userName").val(complainData.userData.trueName);
					if(complainData.opinion == 'no')
						$("#opinionType").val("投诉");
					else
						$("#opinionType").val("建议");
					$("#createTime").val(complainData.createTime);
					$("#contentInfo").html(complainData.content);
					
					$("#replyTime").val(complainData.replyTime);
					$("#reply").val(complainData.reply);
					
					$("#replyComplainDialog").modal("show");
				}
			}
		});
		
		return true;
	});
	
	// 回复
	$(".btnSendReply").live("click", function() {
		if($("#reply").val().trim() == ''){
			alert("内容不能为空！");
			return ;
		}
		
		if($("#reply").val().length > 1000){
			alert("超过字数限制(500字以内)！")
			return ;
		}
		
		$.ajax({
			method : "POST",
			data : {id : $("#complainId").val(), reply : $("#reply").val()},
			url : _rootPath + "/manage/complain/complainInfo/reply",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "FAILURE") {
					alert(message);
					return false;
				} else {
					$("#replyComplainDialog").modal("hide");
					window.location.href=_rootPath + "/manage/complain/complainInfo";
				}
			}
		});
		return true;
	});
	
	// 查看
	$(".btnShow").live("click", function() {
		$.ajax({
			method : "GET",
			data :  {id : $(this).attr("idval")},
			url : _rootPath + "/manage/complain/complainInfo/show",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "FAILURE") {
					alert(message);
					return false;
				} else {
					// 显示信息
					var complainData = $(data)[0].complainData;
					
					if(complainData.userData.trueName == '')
						$("#showUserName").val(complainData.userData.trueName);
					else
						$("#showUserName").val(complainData.userData.trueName);
					if(complainData.opinion == 'no')
						$("#showOpinionType").val("投诉");
					else
						$("#showOpinionType").val("建议");
					$("#showCreateTime").val(complainData.createTime);
					$("#showContentInfo").html(complainData.content);
					$("#showReplyTime").val(complainData.replyTime);
					$("#showReply").val(complainData.reply);
					
					$("#showComplainDialog").modal("show");
				}
			}
		});
		
		return true;
	});
	
	// 删除
	$(".btnDelete").live("click", function() {
		
		$("#id").val($(this).attr("idVal"));
		
		$("#deleteDialog").modal("show");
		
		return true;
	});
	
});