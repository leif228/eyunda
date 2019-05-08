$(function() {
	$('#beShareUserId').select2();
	$("#btnFindUser").click(function() {
		$("#dlgAddUser").modal("show");
		return;
	})
	
	// 查找用户
	$("#btnSerachUser").click(function() {
		$.ajax({
			method : "GET",
			data : {userKeyWords : $("#userKeyWords").val()},
			url : _rootPath+"/space/ship/loadUserByKeyWord",
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
			        $.each($(data)[0].userDatas, function(i, userData) {
			        s += "                <li style=\"width:200px;overflow:hidden;\">";
			        s += "                  <div class=\"account-container\" idVal=\""+userData.id+"\">";
			        s += "                    <div class=\"account-avatar\">";
			        s += "                      <a href=\"#\">";
			        s += "                        <img src=\""+_rootPath+"/download/imageDownload?url="+userData.userLogo+"\" class=\"thumbnail\" />";
			        s += "                      </a>";
			        s += "                    </div>";
			        s += "                    <div class=\"account-details\">";
			        s += "                      <span class=\"account-name\">"+userData.trueName+"<br/>"+userData.mobile+"</span>";
			        s += "                    </div>";
			        s += "                  </div>";
			        s += "                </li>";
			        });
			        s += "              </ul>";
			        s += "            </div>";
			        $("#userList").html(s);
				}
			}
		});
		return true;
	});
	
	$("#userList").on("click",".account-container",function(){
		$(this).parent().siblings().removeClass("addBack");
		$(this).parent().addClass("addBack");
		var data = $(this).attr("idVal");
		$("#addShareUserId").val(data);
	});
	
	//添加授权用户
	$("#addShareUserBtn").click(function() {
		window.location.href = _rootPath+"/space/ship/shareShips?choiceFavoriteUserId="+$("#addShareUserId").val();
	});
	
	
	//更新授权船舶
	$("#updateShareShipsBtn").click(function() {
		$("#updateShareShips").ajaxSubmit({
	      method :"POST",
	      url : _rootPath+"/space/ship/updateShareShips",
	      datatype : "json",
	      success : function(data) {
	    	  var redata = eval('(' + data + ')');
              var returnCode = redata.returnCode;
              var message = redata.message;
              if (returnCode == "Failure") {
                alert(message);
                return false;
              } else {
            	alert(message);
            	$('body,html').animate({scrollTop:0},1000); 
                return true;
              }
            }
	    });
	});
	
	$("#beShareUserId").on("change", function(){
		window.location.href = _rootPath+"/space/ship/shareShips?choiceFavoriteUserId="+$("#beShareUserId").val();
	});
	
})