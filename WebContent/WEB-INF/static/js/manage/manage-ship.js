$(document).ready(function(){
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	// 查看
	$(".btnCheck").live("click", function() {
		var id = $(this).attr("idVal");
		$.ajax({
			method: "GET",
			data : {id : id},
			url : _rootPath + "/manage/ship/ship/check",
			datatype : "json",
			success : function(data){
				var data = $(data)[0];
				var message = data.message;
				var returnCode = data.returnCode;
				if(returnCode == "Failure"){
					alert(message);
					return false;
				}else {
					
					var prefix = _rootPath + "/download/imageDownload?url=";
					var url = _rootPath + "/manage/ship/ship/showImage?id=" + id +"&url="
					 
					var year = data.shipData.createTime.year;
					var month = data.shipData.createTime.month + 1;
					if(month<10) month="0"+month;
					var day = data.shipData.createTime.dayOfMonth;
					if(day<10) day="0"+day;
					
					$("#createTime").val(year + "-" + month + "-" + day);
					
					var warrantStr = "";
					if(data.shipData.warrantType == "personWarrant")
						warrantStr = "个人委托"
					else if (data.shipData.warrantType == "companyWarrant")
						warrantStr = "公司委托"
					
					$("#warrantType").val(warrantStr);
					$("#shipName").val(data.shipData.shipName);
					$("#mmsi").val(data.shipData.mmsi);
					$("#shipMaster").val(data.shipData.master.trueName);
					
					$("#idCardFront").attr("src", prefix + data.shipData.idCardFront);
					$("#idCardFrontLink").attr("href", url + data.shipData.idCardFront);
					
					$("#idCardBack").attr("src", prefix + data.shipData.idCardBack);
					$("#idCardBackLink").attr("href", url + data.shipData.idCardBack);
					
					$("#warrant").attr("src", prefix + data.shipData.warrant);
					$("#warrantLink").attr("href", url + data.shipData.warrant);
					
					$("#certificate").attr("src", prefix + data.shipData.certificate);
					$("#certificateLink").attr("href", url + data.shipData.certificate);
					
					$("#showDialog").modal("show");
				}
			}
		})
		return true;
	});
	
	// 审核
	$(".btnAudit").live("click", function() {
		$("#auditId").val($(this).attr("idVal"));
		
		$("#auditDialog").modal("show");
		
		return true;
	});
	
	// 审核
	$(".audit").live("click", function() {
		var id = $("#auditId").val()
		$.ajax({
			method: "GET",
			data : {id : id},
			url : _rootPath + "/manage/ship/ship/audit",
			datatype : "json",
			success : function(data){
				var data = $(data)[0];
				var message = data.message;
				var returnCode = data.returnCode;
				if(returnCode == "Failure"){
					alert(message);
					return false;
				}else {
					var s = "";
					
					s += "<a class=\"btn btn-success btnPublish\" idVal=\"" + id + "\" >";
					s += "  <i class=\"icon-flag icon-white\"></i>发布";
					s += "</a>&nbsp;";
					s += "<a class=\"btn btn-warning btnUnAudit\" idVal=\"" + id + "\" >";
					s += "  <i class=\"icon-repeat icon-white\"></i>取消审核";
					s += "</a>&nbsp;";
					s += "<a class=\"btn btn-danger btnDelete\" idVal=\"" + id + "\" >";
					s += "  <i class=\"icon-trash icon-white\"></i>删除";
					s += "</a>";
					
					$("#releaseTime" + id).html(data.releaseTime);
					$("#auditDialog").modal("hide");
					$("#desc" + id).html("已审核");
					$("#temp" + id).remove();
					$("#operation" + id).html(s);
				}
			}
		})
		return true;
	});
	
	// 取消审核
	$(".btnUnAudit").live("click", function() {
		$("#unAuditId").val($(this).attr("idVal"));
		
		$("#unAuditDialog").modal("show");
		
		return true;
	});
	
	// 取消审核
	$(".unAudit").live("click", function() {
		var id = $("#unAuditId").val()
		$.ajax({
			method: "GET",
			data : {id : id},
			url : _rootPath + "/manage/ship/ship/unAudit",
			datatype : "json",
			success : function(data){
				var data = $(data)[0];
				var message = data.message;
				var returnCode = data.returnCode;
				if(returnCode == "Failure"){
					alert(message);
					return false;
				}else {
					var s = "";
					s += "<a class=\"btn btn btn-primary btnCheck\" idVal=\"" + id + "\">";
					s += "  <i class=\"icon-eye-open icon-white\"></i>查看";
					s += "</a>&nbsp;";
					s += "<a class=\"btn btn-info btnAudit\" idVal=\"" + id + "\">";
					s += "  <i class=\"icon-ok-circle icon-white\"></i>审核";
					s += "</a>&nbsp;";
					s += "<a class=\"btn btn-danger btnDelete\" idVal=\"" + id + "\" >";
					s += "  <i class=\"icon-trash icon-white\"></i>删除";
					s += "</a>";
					
					$("#releaseTime" + id).html(data.releaseTime);
					$("#unAuditDialog").modal("hide");
					$("#desc" + id).html("已提交");
					$("#temp" + id).remove();
					$("#operation" + id).html(s);
				}
			}
		})
		return true;
	});
	
	// 发布
	$(".btnPublish").live("click", function() {
		$("#publishId").val($(this).attr("idVal"));
		
		$("#publishDialog").modal("show");
		
		return true;
	});
	
	// 发布
	$(".publish").live("click", function() {
		var id = $("#publishId").val()
		$.ajax({
			method: "GET",
			data : {id : id},
			url : _rootPath + "/manage/ship/ship/publish",
			datatype : "json",
			success : function(data){
				var data = $(data)[0];
				var message = data.message;
				var returnCode = data.returnCode;
				if(returnCode == "Failure"){
					alert(message);
					return false;
				}else {
					var s = "";
					s += "<a class=\"btn btn-warning btnUnRelease\" idVal=\"" + id + "\" >";
					s += "  <i class=\"icon-chevron-down icon-white\"></i>取消发布";
					s += "</a>&nbsp;";
					/*s += "<a class=\"btn btn-inverse btnAnalyse\" href=\""+ _rootPath + '/manage/ship/ship/analyseCooords?shipId=' + id + "\">";
					s += "<i class=\"icon-repeat icon-white\"></i>分析";
					s += "</a>";*/
					
					$("#publishDialog").modal("hide");
					$("#desc" + id).html("已发布");
					$("#releaseTime" + id).html(data.releaseTime);
					$("#temp" + id).remove();
					$("#operation" + id).html(s);
				}
			}
		})
		return true;
	});
	
	// 取消发布
	$(".btnUnRelease").live("click", function() {
		$("#unPublishId").val($(this).attr("idVal"));
		
		$("#unpublishDialog").modal("show");
		
		return true;
	});
	
	// 取消发布
	$(".unPublish").live("click", function() {
		var id = $("#unPublishId").val()
		$.ajax({
			method: "GET",
			data : {id : id},
			url : _rootPath + "/manage/ship/ship/unPublish",
			datatype : "json",
			success : function(data){
				var data = $(data)[0];
				var message = data.message;
				var returnCode = data.returnCode;
				if(returnCode == "Failure"){
					alert(message);
					return false;
				}else {
					var s = "";
					
					s += "<a class=\"btn btn-success btnPublish\" idVal=\"" + id + "\" >";
					s += "  <i class=\"icon-chevron-up icon-white\"></i>发布";
					s += "</a>&nbsp;";
					s += "<a class=\"btn btn-warning btnUnAudit\" idVal=\"" + id + "\" >";
					s += "  <i class=\"icon-repeat icon-white\"></i>取消审核";
					s += "</a>&nbsp;";
					s += "<a class=\"btn btn-danger btnDelete\" idVal=\"" + id + "\" >";
					s += "  <i class=\"icon-trash icon-white\"></i>删除";
					s += "</a>";
					
					$("#unpublishDialog").modal("hide");
					$("#desc" + id).html("已审核");
					$("#releaseTime" + id).html(data.releaseTime);
					$("#temp" + id).remove();
					$("#operation" + id).html(s);
				}
			}
		})
		return true;
	});
	
	// 删除
	$(".btnDelete").live("click", function() {
		$("#deleteId").val($(this).attr("idVal"));
		
		$("#deleteDialog").modal("show");
		
		return true;
	});
	
	$(".delete").live("click",function(){
		var id = $("#deleteId").val()
		$.ajax({
			method : "GET",
			data : {id : id},
			url : _rootPath + "/manage/ship/ship/delete",
			success : function(data){
				var data = $(data)[0];
				var message = data.message;
				var returnCode = data.returnCode;
				if(returnCode == "Failure"){
					alert(message);
					return false;
				}else {
					window.location.href = _rootPath + "/manage/ship/ship?pageNo=" + $("#pageNo").val() + 
						"&keyWords=" + $("#keyWords").val() + "&statusCode=" + $("#statusCode").val();
				}
			}
		})
	});
	
});
