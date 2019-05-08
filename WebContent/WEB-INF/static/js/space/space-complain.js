$(document).ready(function() {
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	// 添加对话框
	$(".addCopmlain").live("click", function() {
		
		$("#addContent").val("");
		$("#addDialog").modal("show");
		
		return true;
	});
	
	// 提交添加
	$(".btnAdd").live("click", function() {
		if($("#addContent").val().trim() == ''){
			alert("内容不能为空！");
			return ;
		}
		
		if($("#addContent").val().length > 1000){
			alert("超过字数限制(500字以内)！")
			return ;
		}
		
		$("#AddComplainForm").ajaxSubmit({
			method : "POST",
			url : _rootPath + "/space/complain/myComplain/content",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "FAILURE") {
					alert(message);
					return false;
				} else {
					window.location.href=_rootPath + "/space/complain/myComplain";
				}
			}
		});
		return true;
	});
	
	// 查看
	$(".showComplain").live("click", function() {
		$.ajax({
			method : "GET",
			data :  {id : $(this).attr("idval")},
			url : _rootPath + "/space/complain/myComplain/show",
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
					
					if(complainData.opinion == 'no')
						$("#opinionType").val("投诉");
					else
						$("#opinionType").val("建议");
					$("#createTime").val(complainData.createTime);
					$("#contentInfo").html(complainData.content);
					$("#replyTime").val(complainData.replyTime);
					$("#reply").val(complainData.reply);
					
					$("#showComplainDialog").modal("show");
				}
			}
		});
		return true;
	});
	
	// 修改
	$(".editComplain").live("click", function() {
		$.ajax({
			method : "GET",
			data :  {id : $(this).attr("idval")},
			url : _rootPath + "/space/complain/myComplain/show",
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
					
					if(complainData.opinion == 'no')
						$("#opinionInfo").val("投诉");
					else
						$("#opinionInfo").val("建议");
					
					$("#complainContent").val(complainData.content);
					
					$("#complainId").val(complainData.id);
					
					$("#editComplainDialog").modal("show");
				}
			}
		});
		return true;
	});
	
	// 修改
	$(".btnEdit").live("click", function() {
		if($("#complainContent").val().trim() == ''){
			alert("内容不能为空！");
			return ;
		}
		
		if($("#complainContent").val().length > 1000){
			alert("超过字数限制(500字以内)！")
			return ;
		}
		
		var id=$("#complainId").val();
		
		$("#editComplainForm").ajaxSubmit({
			method : "POST",
			url : _rootPath + "/space/complain/myComplain/edit",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "FAILURE") {
					alert(message);
					return false;
				} else {
					var content = $(data)[0].content;
					if(content.length < 15)
						$("#showContent" + id).html(content);
					else
						$("#showContent" + id).html(content.substring(0,15) + "...");
					
					$("#editComplainDialog").modal("hide");
				}
			}
		});
		return true;
	});
	
	// 添加
	$(".deleteComplain").live("click", function() {
		
		$("#id").val($(this).attr("idVal"));
		
		$("#deleteDialog").modal("show");
		
		return true;
	});
	
});