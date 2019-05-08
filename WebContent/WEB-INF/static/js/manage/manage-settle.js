$(document).ready(function(){
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
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
			url : _rootPath+"/manage/settle/findUser",
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
			        s += "                      <span class=\"account-role\">"+user.trueName+"</span>";
			        s += "                      <span class=\"account-role\">"+user.nickName+"</span>";
			        s += "                      <span class=\"account-role\">"+user.email+"</span>";
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
	
});