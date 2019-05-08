$(document).ready(function() {
	$('input[type=radio],input[type=file]').uniform();
	// $('select').select2();

	// Form Validation
	$("#frmSaveAll").validate({
		rules:{
			shipId:{
				required:true,
				digits:true
			},
			demurrage:{
        		required:true,
        		number:true,
        		checkPrice:true
        	},
        	paySteps:{
        		required:true,
                digits:true,
             	min:1,
             	max:12
        	},
        	orderDesc:{
				required:false,
				minlength:0,
				maxlength:2000
			}
		},
		errorClass: "help-inline",
		errorElement: "span",
		highlight:function(element, errorClass, validClass) {
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight: function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
			$(element).parents('.control-group').addClass('success');
		}
	});

	$("#allSailLine").live("change", function(){
		if ($("#allSailLine").attr("checked")){
			$(":checkbox").attr("checked", true); 
		} else {
			$(":checkbox").attr("checked", false); 
		}
	})

	$(".btnSave").live("click", function(){
		if($("#frmSaveAll").valid())
		$("#frmSaveAll").ajaxSubmit({
			method : "POST",
			url : _rootPath+"/space/cabin/saveCabin",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
				} else {
					alert(message);
					var cabinId = $(data)[0].cabinId;
					// window.location.href = _rootPath + "/space/cabin/editCabin?cabinId="+cabinId;
					window.location.href = _rootPath + "/space/cabin/myCabin";
				}
				return false;
			}
		})
	})
	
	$(".btnBack").live("click", function(){
		window.location.href = _rootPath + "/space/cabin/myCabin";
	})

	// 查找船舶
	$("#btnFindShip").click(function() {
		$("#selShipId").val("");
		
		$("#dlgAddShip").modal("show");
		
		return true;
	});

	$("#shipKeyWords").keydown(function(e) {
		if (e.keyCode == 13){
			$("#btnSerachShip").click();
			return false;
		}
	});
	
	// 查找船舶
	$("#btnSerachShip").click(function() {
		$.ajax({
			method : "GET",
			data : {keyWords : $("#keyWords").val()},
			url : _rootPath+"/space/cabin/findShip",
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
			        s += "                      <span class=\"account-name\">"+ship.shipName+"<br />"+ship.mmsi+"<br />船东："+ship.master.trueName;
			        s += "                      </span>";
			        s += "                    </div>";
			        s += "                  </div>";
			        s += "                </li>";
			        });
			        s += "              </ul>";
			        s += "            </div>";
			        $("#shipList").html(s);
				}
			}
		});
		return true;
	});

	// 显示船舶信息
	$("#btnAddShip").click(function() {
		$.ajax({
			method : "GET",
			data : {shipId : $("#selShipId").val()},
			url : _rootPath+"/space/cabin/getShip" ,
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
                    $("#shipId").val($(data)[0].ship.id);
                    $("#imgShipLogo").attr("src", _rootPath+"/download/imageDownload?url="+$(data)[0].ship.shipLogo);
                    $("#shipTypeName").html($(data)[0].ship.typeData.typeName);
                    $("#shipName").html($(data)[0].ship.shipName);

                    $("#masterId").val($(data)[0].ship.master.id);
					$("#masterUserLogo").attr("src", _rootPath+"/download/imageDownload?url="+$(data)[0].ship.master.userLogo);
					$("#masterTrueName").html($(data)[0].ship.master.trueName);
					
					$("#containerCount").val($(data)[0].ship.fullContainer);
					$(".weight").val($(data)[0].ship.aTons);

					$("select").select2();

					$("#dlgAddShip").modal("hide");
				}
			}
		});
		return true;
	});

	$(".account-container").live("click",function(){
		$(this).parent().siblings().removeClass("addBack");
		$(this).parent().addClass("addBack");
		var data = $(this).attr("idVal");
		$("#selShipId").val(data);
	});

      $("#btnChangeMaster").click(function() {
        $("#objUserRole").val("master");
        $("#dlgUserSelector").modal("show");
        return true;
      });

      // 查找用户对话框
      $("#btnFindUsers").click(function() {
        $.ajax({
          method : "GET",
          data : {userinfoKeyWords : $("#userinfoKeyWords").val()},
          url : _rootPath+"/space/orderCommon/findUsers",
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
                  s += "                  <div class=\"account-container obj-user\" idVal=\""+userData.id+"\">";
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
                  $("#objUserList").html(s);
            }
          }
        });
        return true;
      });

      //点击选择用户
      $(".obj-user").live("click",function(){
        $(this).parent().siblings().removeClass("addBack");
        $(this).parent().addClass("addBack");
        var data = $(this).attr("idVal");
        $("#objUserId").val(data);
      });

      //更新托运人
      $("#btnSelectUser").live("click",function(){
        $.ajax({
          method : "GET",
          data : {objUserId : $("#objUserId").val()},
          url : _rootPath+"/space/orderCommon/getOneUser",
          datatype : "json",
          success : function(data) {
            var returnCode = $(data)[0].returnCode;
            var message = $(data)[0].message;
            if (returnCode == "Failure") {
              alert(message);
              return false;
            } else {
              var r = $("#objUserRole").val();
              if (r == "owner") {
                  $("#ownerId").val($(data)[0].objUser.id);
                  $("#ownerUserLogo").attr("src", _rootPath+"/download/imageDownload?url="+$(data)[0].objUser.userLogo);
                  $("#ownerTrueName").html($(data)[0].objUser.trueName);
              } else {
                  $("#masterId").val($(data)[0].objUser.id);
                  $("#masterUserLogo").attr("src", _rootPath+"/download/imageDownload?url="+$(data)[0].objUser.userLogo);
                  $("#masterTrueName").html($(data)[0].objUser.trueName);
              }
              
              $("#dlgUserSelector").modal("hide");
            }
          }
        });
        return true;
      });

});