$(document).ready(function(){
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	// 删除
	$(".btnDelete").live("click", function() {
		$("#role3").val($("#role1").val());
		$("#delId").val($(this).attr("idVal"));
		
		$("#dlgDelete").modal("show");
		return true;
	});
	
	// 同意加为承运人
	$(".btnFriend").live("click", function() {
		$("#friendId").val($(this).attr("idVal"));
		
		$("#dlgFriend").modal("show");
		
		return true;
	});
	
	// 不同意加为承运人
	$(".btnUnFriend").live("click", function() {
		$("#unFriendId").val($(this).attr("idVal"));
		
		$("#dlgUnFriend").modal("show");
		
		return true;
	});
	
	// 添加
	$("#btnAdd").live("click", function() {
		$("#contactId").val("0");
		
		$("#dlgAdd").modal("show");
		
		return true;
	});

	$(".account-container").live("click",function(){
		$(this).parent().siblings().removeClass("addBack");
		$(this).parent().addClass("addBack");
		var data = $(this).attr("data-src");
		$("#contactId").val(data);
		$("#role2").val("CARRIER");
	});

	$("#keyWords").keydown(function(e) {
		if (e.keyCode == 13){
			$("#searchContact").click();
			return false;
		}
	});
	
	// 用户查找
	$("#searchContact").live("click", function() {
		$.ajax({
			method : "GET",
			data : {keyWords : $("#searchKeyWords").val()},
			url : _rootPath+"/space/contact/myContact/find",
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
			        $.each($(data)[0].contacts, function(i, contact) {
			        s += "                <li style=\"width:200px;overflow:hidden;\">";
			        s += "                  <div class=\"account-container\" data-src=\""+contact.id+"\">";
			        s += "                    <div class=\"account-avatar\">";
			        s += "                      <a href=\"#\">";
			        s += "                        <img id=\"img\""+contact.id+" src=\""+_rootPath+"/download/imageDownload?url="+contact.userLogo+"\" alt=\""+contact.trueName+"\" class=\"thumbnail\" />";
			        s += "                      </a>";
			        s += "                    </div>";
			        s += "                    <div class=\"account-details\">";
			        s += "                      <span class=\"account-name\">"+contact.trueName+"</span>";
			        s += "                      <span class=\"account-role\">"+contact.loginName+"</span>";
			        s += "                      <span class=\"account-role\">"+contact.mobile+"</span>";
			        s += "                      <span class=\"account-role\">"+contact.email+"</span>";
			        s += "                    </div>";
			        s += "                  </div>";
			        s += "                </li>";
			        });
			        s += "              </ul>";
			        s += "            </div>";
			        $("#contactList").html(s);
				}
			}
		});
		return true;
	});
	
	// 角色改变
	$("#role1").change(function(){
		$("#pageNo").val("1");
		//window.location.href = _rootPath+"/space/contact/myContact?role="+$("#role1").val();

		});
	
});
