$(document).ready(function(){
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	$(".account-container").live("click",function(){
		$(this).parent().siblings().removeClass("addBack");
		$(this).parent().addClass("addBack");
		var data = $(this).attr("idVal");
		
		$("#operId").val(data);
		
	});
	
	// 添加代理人发布信息
	$("#addOperRelease").click(function() {
		$("#operId").val("");
		// 提前给栏目赋值防止错误操作
		$("#rlsColumnRelease").val($("#rlsColumn").val());
		
		$("#dlgAddOperRelease").modal("show");
		
		return true;
	});
	
	// 添加代理人发布信息
	$("#serachOperRelease").click(function() {
		$.ajax({
			method : "GET",
			data : {operInfo : $("#operInfo").val()},
			url : _rootPath+"/manage/member/releaseOper/findOperators",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					var s = "";

					s += "            <div style=\"text-align: left;\">";
			        s += "              <ul class=\"user-info\">";
			        $.each($(data)[0].operatorDatas, function(i, operatorData) {
			        s += "                <li style=\"width:200px;overflow:hidden;\">";
			        s += "                  <div class=\"account-container\" idVal=\""+operatorData.id+"\">";
			        s += "                    <div class=\"account-avatar\">";
			        s += "                      <a href=\"#\">";
			        s += "                        <img id=\"img\""+operatorData.id+" src=\""+_rootPath+"/download/imageDownload?url="+operatorData.userLogo+"\" class=\"thumbnail\" />";
			        s += "                      </a>";
			        s += "                    </div>";
			        s += "                    <div class=\"account-details\">";
			        s += "                      <span class=\"account-name\">"+operatorData.trueName+"</span>";
			        s += "                    </div>";
			        s += "                  </div>";
			        s += "                </li>";
			        });
			        s += "              </ul>";
			        s += "            </div>";
			        $("#operList").html(s);
			        return true;
				}
			}
		});
		
	});
	
	// 修改代理人发布信息
	$(".editOperRelease").live("click",function() {
		$.ajax({
			method : "GET",
			data : {id : $(this).attr("idVal")},
			url : _rootPath+"/manage/member/releaseOper/editOper",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					
					//读取数据，并填入
					$("#operIdRelease").val($(data)[0].releaseOperatorData.operatorData.id);
					$("#rlsColumnReleaseOper").val($("#rlsColumn").val());
					$("#releaseId").val($(data)[0].releaseOperatorData.id);
					$("#releaseNo").val($(data)[0].releaseOperatorData.no);
					$("#operReleaseName").html($(data)[0].releaseOperatorData.operatorData.trueName);
					var imageOperLogo=_rootPath+"/download/imageDownload?url="+$(data)[0].releaseOperatorData.operatorData.userLogo;
					$("#imageOperLogo").attr("src", imageOperLogo);
					$("#dlgEditOperRelease").modal("show");
					
					return true;
				}
			}
		});
		
	});
	
	// 删除
	$(".delOperRelease").live("click", function() {
		$("#delId").val($(this).attr("idVal"));
		$("#rlsColumnOperDel").val($("#rlsColumn").val());
		
		$("#deleteReleaseDialog").modal("show");
		
		return true;
	});
	
	// 栏目改变
	$("#rlsColumn").change(function(){
		window.location.href = _rootPath+"/manage/member/releaseOper?rlsColumn="+$("#rlsColumn").val();

		});
	
});