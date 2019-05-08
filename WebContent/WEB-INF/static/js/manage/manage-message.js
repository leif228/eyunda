$(document).ready(function(){
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	$(".btnSerachMessage").live("click",function(){
		$("#userId").val("");
		$("#dlgFindUser").modal("show");
	});
	
	$(".account-container").live("click",function(){
		$(this).parent().siblings().removeClass("addBack");
		$(this).parent().addClass("addBack");
		var data = $(this).attr("idVal");
		$("#userId").val(data);
	});
	
	// 查找用户
	$("#btnSerachUser").click(function() {
		$.ajax({
			method : "GET",
			data : {keyWords : $("#keyWords").val()},
			url : _rootPath+"/manage/message/findUser",
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
			        $.each($(data)[0].users, function(i, user) {
			        s += "                <li style=\"width:200px;overflow:hidden;\">";
			        s += "                  <div class=\"account-container\" idVal=\""+user.id+"\">";
			        s += "                    <div class=\"account-avatar\">";
			        s += "                      <a href=\"#\">";
			        s += "                        <img id=\"img\""+user.id+" src=\""+_rootPath+"/download/imageDownload?url="+user.userLogo+"\" class=\"thumbnail\" />";
			        s += "                      </a>";
			        s += "                    </div>";
			        s += "                    <div class=\"account-details\">";
			        s += "                      <span class=\"account-name\">"+user.loginName+"</span>";
			        s += "                      <span class=\"account-name\">"+user.trueName+"</span>";
			        s += "                      <span class=\"account-name\">"+user.nickName+"</span>";
			        s += "                      <span class=\"account-name\">"+user.email+"</span>";
			        s += "                    </div>";
			        s += "                  </div>";
			        s += "                </li>";
			        });
			        s += "              </ul>";
			        s += "            </div>";
			        $("#userList").html(s);
			        $("#dlgFindUser").modal("show");
				}
			}
		});
		return true;
	});
	
	// 查看
	$(".btnShow").live("click", function() {
		$.ajax({
			method : "GET",
			data : {showId : $(this).attr("idVal")},
			url : _rootPath+"/manage/message/show/"+$(this).attr("idVal"),
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;	
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {	
					//读入数据，并填入
					$("#readStatus"+$(data)[0].messageData.id).html("");
					$("#title").html($(data)[0].messageData.title);	
					$("#message").html($(data)[0].messageData.message);	
					$("#readStatus"+$(data)[0].messageData.id).html("已读");
					$("#showDialog").modal("show");
				}
			}
		});
		return true;
	});
	
	//删除
	$(".btnDelete").live("click",function(){
		$("#deleteId").val($(this).attr("idVal"));
		$("#delPageNo").val($("#pageNo").val());
		$("#deleteDialog").modal("show");	
		return true ;
	});

});