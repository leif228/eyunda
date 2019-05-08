$(document).ready(function() {
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	// 删除
	$(".btnDelete").live("click", function() {
		$("#deleteId").val($(this).attr("idVal"));

		$("#deleteDialog").modal("show");

		return true;
	});
	
	// 激活
	$(".btnActivity").live("click", function() {
		$("#activityId").val($(this).attr("idVal"));
		$("#activityDialog").modal("show");

		return true;
	});
	
	// 取消激活
	$(".btnUnActivity").live("click", function() {
		$("#unActivityId").val($(this).attr("idVal"));
		$("#unActivityDialog").modal("show");

		return true;
	});
	
	// 查看
	$(".btnShow").live("click", function() {
		$.ajax({
			method : "GET",
			data : {userId : $(this).attr("idVal")},
			url : _rootPath+"/manage/member/hyqmember/"+$(this).attr("idVal"),
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					$("#loginName").val($(data)[0].user.loginName);
					$("#trueName").val($(data)[0].user.trueName);	
					$("#nickName").val($(data)[0].user.nickName);
					$("#createTime").val($(data)[0].user.createTime);
					$("#email").val($(data)[0].user.email);
					$("#mobile").val($(data)[0].user.mobile);
					
					var imgStamp=_rootPath+"/download/imageDownload?url="+$(data)[0].user.stamp;
					$("#imgStamp").attr("src", imgStamp);
					var imgUserLogo=_rootPath+"/download/imageDownload?url="+$(data)[0].user.userLogo;
					$("#imgUserLogo").attr("src", imgUserLogo);
					var imgSignature=_rootPath+"/download/imageDownload?url="+$(data)[0].user.stamp;
					$("#imgSignature").attr("src", imgSignature);
					
					$("#showDialog").modal("show");
				}
			}
		});
		return true;
	});
	
	// 激活
	$(".activity").live("click", function() {
		var id = $("#activityId").val();
		$.ajax({
			method : "GET",
			data : {id : id},
			url : _rootPath+"/manage/member/hyqmember/activity",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					$("#activityDialog").modal("hide");

                    $("#status" + id).html("激活");

					var s = "";
					s += "<a class=\"btn btn-warning btnUnActivity\" idVal=\"" + id + "\">";
                    s += "<i class=\"icon-trash icon-white\"></i> 取消</a>";
                    $("#act" + id).html(s);
				}
			}
		});
		return true;
	});
	
	// 取消激活
	$(".unActivity").live("click", function() {
		var id = $("#unActivityId").val();
		$.ajax({
			method : "GET",
			data : {id : id},
			url : _rootPath+"/manage/member/hyqmember/unActivity",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					$("#unActivityDialog").modal("hide");
					
					$("#status" + id).html("未激活");

					var s = "";
					s += "<a class=\"btn btn-success btnActivity\" idVal=\"" + id + "\">";
					s += "<i class=\"icon-trash icon-check\"></i> 激活</a>";
                    $("#act" + id).html(s);
				}
			}
		});
		return true;
	});

});