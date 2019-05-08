$(document).ready(function() {

	$('input[type=checkbox],input[type=file]').uniform();

	$('select').select2();
	
	// 提交审核
	$(".btnCommit").live("click", function() {
		$("#commitId").val($(this).attr("idVal"));

		$("#commitDialog").modal("show");

		return true;
	});

	// 发布
	$(".btnRelease").live("click", function() {
		$("#pubId").val($(this).attr("idVal"));

		$("#publishDialog").modal("show");

		return true;
	});

	// 取消发布
	$(".btnUnRelease").live("click", function() {
		$("#unpubId").val($(this).attr("idVal"));

		$("#unpublishDialog").modal("show");

		return true;
	});

	// 删除船舶
	$(".btnDelete").live("click", function() {
		$("#delId").val($(this).attr("idVal"));

		$("#deleteDialog").modal("show");

		return true;
	});
	
	// 权限
	$(".btnPower").live("click", function() {
		var shipId = $(this).attr("idVal");
		var shipName = $(this).attr("nameVal");
		window.location.href = _rootPath + "/space/ship/monitorAll?shipId="+shipId+"&shipName="+shipName;
		
		return true;
	});
	
	// 服务费
	$(".btnDues").live("click", function() {
		var shipId = $(this).attr("idVal");
		var shipName = $(this).attr("nameVal");
		window.location.href = _rootPath + "/space/ship/myShip/dues?shipId="+shipId+"&shipName="+shipName;
		
		return true;
	});
	
	// 加油费
	$(".btnGasOrder").live("click", function() {
		var shipId = $(this).attr("idVal");
		var shipName = $(this).attr("nameVal");
		window.location.href = _rootPath + "/space/ship/myShip/gasOrder?shipId="+shipId+"&shipName="+shipName;
		
		return true;
	});

	// 删除收藏的船舶
	$(".btnDeleteCollect").live("click", function() {
		$("#delColId").val($(this).attr("idVal"));
		$("#deleteCollectDialog").modal("show");
		return true;
	});
	
	$(".deleteCollect").live("click", function(){
		var shipId = $("#delColId").val();
		var userId = $("#userId").val();
		var keyWords = $("#keyWords").val();
		var pageNo = $("#pageNo").val();
		var deptId = $("#deptId option:selected").val();
		$.ajax({
			type: 'GET',
			url : _rootPath + "/space/ship/myShip/deleteCollect",
			data : {shipId : shipId, userId : userId},
			datatype : "json",
			success : function(data){
				var data = $(data)[0];
				var returnCode = data.returnCode;
		        var message = data.message;
		        if (returnCode == "Failure") {
		        	alert(message);
		        	return false;
		        } else {
		        	window.location.href = _rootPath + 
		        	"/space/ship/myShip?pageNo=" + pageNo + "&keyWords=" + keyWords + "&deptId=" + deptId;
		        }
			}
		})
	})
	
	// 导入船舶EXCEL文件
	$("#importExcel").live("click", function() {

		$("#importExcelDialog").modal("show");

		return true;
	});
	
	// 设置数据平台
	$(".btnSet").live("click", function() {
		var shipId = $(this).attr("idVal");
		
		$.ajax({
			method : "GET",
			url : _rootPath + "/space/ship/myShip/getPlant",
			data : {shipId : shipId},
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
		        var message = $(data)[0].message;
		        if (returnCode == "Failure") {
		        	alert(message);
		        	return false;
		        } else {
		        	var s = "";
		        	$.each($(data)[0].plantCode, function(key){
	        			if ($(data)[0].MDS == key)
	        				s += "<input type=\"radio\" name=\"plantName\" value=\"" + key + "\" checked/>" + $(data)[0].plantCode[key] + " ";
	        			else
	        				s += "<input type=\"radio\" name=\"plantName\" value=\"" + key + "\"/> " + $(data)[0].plantCode[key] + " ";
		        	});		 
		        	
		        	$("#shipId").val(shipId);
		        	$("#dataSource").html(s);
		        	$("#setDataSourceDialog").modal("show");
		        }
			}
		})
		
		return true;
	});
	
	// 设置船舶坐标数据来源
	$(".btnSetDataSouce").live("click", function() {
		var shipId = $("#shipId").val();
		var plantName = $("input[type='radio']:checked").val();
		
		$.ajax({
			method : "GET",
			url : _rootPath + "/space/ship/myShip/setPlant",
			data : {shipId : shipId, plantName : plantName},
			datatype : "json",
			success : function(data){
				var returnCode = $(data)[0].returnCode;
		        var message = $(data)[0].message;
		        $("#setDataSourceDialog").modal("hide");
		        if (returnCode == "Failure") {
		        	alert(message);
		        	return false;
		        } else {
		        	alert(message);
		        }
			}
		})
		
		return true;
	});
	
});
