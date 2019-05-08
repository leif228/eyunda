$(document).ready(function(){
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	$(".account-container").live("click",function(){
		$(this).parent().siblings().removeClass("addBack");
		$(this).parent().addClass("addBack");
		var data = $(this).attr("idVal");
		
		$("#shipId").val(data);
	});
	
	// 添加船舶发布信息
	$("#addShipRelease").click(function() {
		$("#shipId").val("");
		// 提前给栏目赋值防止错误操作
		$("#rlsColumnRelease").val($("#rlsColumn").val());
		
		$("#dlgAddShipRelease").modal("show");
		
		return true;
	});
	
	// 添加船舶发布信息
	$("#serachShipRelease").click(function() {
		$.ajax({
			method : "GET",
			data : {shipKeyWords : $("#shipKeyWords").val()},
			url : _rootPath+"/manage/ship/release/findShip",
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
			        $.each($(data)[0].ships, function(i, ship) {
			        s += "                <li style=\"width:200px;overflow:hidden;\">";
			        s += "                  <div class=\"account-container\" idVal=\""+ship.id+"\">";
			        s += "                    <div class=\"account-avatar\">";
			        s += "                      <a href=\"#\">";
			        s += "                        <img id=\"img\""+ship.id+" src=\""+_rootPath+"/download/imageDownload?url="+ship.shipLogo+"\" class=\"thumbnail\" />";
			        s += "                      </a>";
			        s += "                    </div>";
			        s += "                    <div class=\"account-details\">";
			        s += "                      <span class=\"account-name\">"+ship.shipName+"</span>";
			        s += "                    </div>";
			        s += "                  </div>";
			        s += "                </li>";
			        });
			        s += "              </ul>";
			        s += "            </div>";
			        $("#shipList").html(s);
			        return true;
				}
			}
		});
		
	});
	
	// 修改船舶发布信息
	$(".editShipRelease").live("click",function() {
		// alert($(this).attr("idVal"));
		$.ajax({
			method : "GET",
			data : {id : $(this).attr("idVal")},
			url : _rootPath+"/manage/ship/release/edit",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					
					//读取数据，并填入
					$("#shipIdRelease").val($(data)[0].releaseData.shipData.id);
					$("#rlsColumnReleaseShip").val($("#rlsColumn").val());
					$("#releaseId").val($(data)[0].releaseData.id);
					$("#releaseNo").val($(data)[0].releaseData.no);
					$("#shipReleaseName").html($(data)[0].releaseData.shipData.shipName);
					var imageShipLogo=_rootPath+"/download/imageDownload?url="+$(data)[0].releaseData.shipData.shipLogo;
					$("#imageShipLogo").attr("src", imageShipLogo);
					$("#dlgEditShipRelease").modal("show");
					
					return true;
				}
			}
		});
		
	});
	
	// 删除
	$(".delShipRelease").live("click", function() {
		$("#delId").val($(this).attr("idVal"));
		$("#rlsColumnShipDel").val($("#rlsColumn").val());
		
		$("#deleteReleaseDialog").modal("show");
		
		return true;
	});
	
	// 栏目改变
	$("#rlsColumn").change(function(){
		window.location.href = _rootPath+"/manage/ship/release?rlsColumn="+$("#rlsColumn").val();

		});
	
});