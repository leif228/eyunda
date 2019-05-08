$(document).ready(function() {
    $('input[type=checkbox],input[type=file]').uniform();
    
    $('select').select2();

    // Form Validation
    $("#frmSaveAll").validate({
        rules:{
        	startDate:{
        		required:true
        	},
        	endDate:{
        		required:true
        	},
        	rcvDate:{
        		required:true
        	},
        	rcvPortNo:{
        		required:true
        	},
        	retDate:{
        		required:true
        	},
        	retPortNo:{
        		required:true
        	},
        	oilPrice:{
        		required:true,
        		number:true
        	},
        	price:{
        		required:true,
        		number:true,
        		checkPrice:true
        	},
        	transFee:{
        		required:true,
        		number:true,
        		checkPrice:true
        	},
        	paySteps:{
        		required:true,
                digits:true,
             	min:1,
             	max:12
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

    $(".account-container").live("click",function(){
        $(this).parent().siblings().removeClass("addBack");
        $(this).parent().addClass("addBack");
        var data = $(this).attr("idVal");
        $("#selShipId").val(data);
    });
    
    // 返回
    $(".btnBack").click(function() {
        window.location.href = _rootPath+"/space/orderCommon/orderList";
    });
    
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
            data : {shipKeyWords : $("#shipKeyWords").val()},
            url : _rootPath+"/space/orderCommon/findShips",
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
                    if(ship.shipLogo == "" || typeof(ship.shipLogo) == undefined)
                    s += "                        <img id=\"img\""+ship.id+" src=\""+_rootPath+"/img/shipImage/"+ship.shipType+".jpg\" class=\"thumbnail\" />";
                    else
                    s += "                        <img id=\"img\""+ship.id+" src=\""+_rootPath+"/download/imageDownload?url="+ship.shipLogo+"\" class=\"thumbnail\" />";
                    s += "                      </a>";
                    s += "                    </div>";
                    s += "                    <div class=\"account-details\">";
                    s += "                      <span class=\"account-name\">"+ship.shipName+"</span>";
                    s += "                      <span class=\"account-name\">"+ship.master.trueName+ship.master.loginName+"</span>";
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
            url : _rootPath+"/space/orderCommon/getOneShip" ,
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
                    
                    $("#containerCount").html("重箱:"+$(data)[0].ship.fullContainer+"TEU,半重箱:"+$(data)[0].ship.halfContainer+"TEU,吉箱:"+$(data)[0].ship.spaceContainer+"TEU");
                    $("#weight").html("载重A级:"+$(data)[0].ship.aTons+"吨,载重A级:"+$(data)[0].ship.bTons+"吨");
                    
                    $("select").select2();

                    $("#dlgAddShip").modal("hide");
                }
            }
        });
        return true;
    });
    
    // 移除船舶
    $("#btnRemoveShip").live("click", function(){
    	$("#shipId").val(0);
    	$("#imgShipLogo").attr("src", "");
        $("#shipTypeName").html("");
        $("#shipName").html("");

        $("#masterId").val(0);
        $("#masterTrueName").html("");
        $("#masterUserLogo").attr("src", "");

        $("select").select2();
        return true;
    });
    
    // 起运港改变
    $("#rcvPortCity").change(function(){
        $.ajax({
              method : "GET",
              data : {portCityCode : $(this).val()},
              url : _rootPath+"/port/getCityCodePorts",
              datatype : "json",
              success : function(data) {
                  var returnCode = $(data)[0].returnCode;
                  var message = $(data)[0].message;
                  if (returnCode == "Failure") {
                      alert(message);
                      return false;
                  } else {
                        var s = "";
                        s += "    <select id=\"rcvPortNo\" name=\"rcvPortNo\" >";
                      $.each($(data)[0].portDatas, function(i, portData) {
                      if (_rcvPortNo==portData.portNo) {
                      s += "      <option value=\""+portData.portNo+"\" selected > "+portData.portName+"</option>";
                      } else {
                      s += "      <option value=\""+portData.portNo+"\" > "+portData.portName+"</option>";
                      }
                      });
                      s += "    </select>";
                      
                      $("#rcvPortName").html("");
                      $("#rcvPortName").html(s);
                      
                      $("#rcvPortNo").select2();
                      
                      return true;
                  }
              }
        });

    });
    
    // 终止港改变
    $("#retPortCity").change(function(){
        $.ajax({
              method : "GET",
              data : {portCityCode : $(this).val()},
              url : _rootPath+"/port/getCityCodePorts",
              datatype : "json",
              success : function(data) {
                  var returnCode = $(data)[0].returnCode;
                  var message = $(data)[0].message;
                  if (returnCode == "Failure") {
                      alert(message);
                      return false;
                  } else {
                      var s = "";
                        s += "    <select id=\"retPortNo\" name=\"retPortNo\" >";
                      $.each($(data)[0].portDatas, function(i, portData) {
                      if (_retPortNo==portData.portNo) {
                      s += "      <option value=\""+portData.portNo+"\" selected > "+portData.portName+"</option>";
                      } else {
                      s += "      <option value=\""+portData.portNo+"\" > "+portData.portName+"</option>";
                      }
                      });
                      s += "    </select>";
                      
                      $("#retPortName").html("");
                      $("#retPortName").html(s);
                      
                      $("#retPortNo").select2();

                      return true;
                  }
              }
        });

    });
    
    // 添加新港口
    $(".btnAddPort").click(function() {
        $.ajax({
            method : "GET",
            data : {para : 1},
            url : _rootPath+"/port/addNewPort",
            datatype : "json",
            success : function(data) {
                var returnCode = $(data)[0].returnCode;
                var message = $(data)[0].message;
                if (returnCode == "Failure") {
                    alert(message);
                    return false;
                } else {
                    //读入数据，并填入
                    $("#portNo").val("");
                    $("#portName").val("");

                    var s = "";
                    s += "<div class=\"control-group\">";
                    s += "  <label class=\"control-label\">港口城市：</label>";
                    s += "  <div class=\"controls\">";
                    s += "    <select id=\"portCityCode\" name=\"portCityCode\" style=\"width:280px;\">";
                    $.each($(data)[0].bigAreas, function(i, bigArea) {
                    s += "      <optgroup label=\""+bigArea.description+"\">";
                    $.each(bigArea.portCities, function(i, portCity) {
                    s += "      <option value=\""+portCity.code+"\"> "+portCity.description+"</option>";
                    });
                    s += "      </optgroup>";
                    });
                    
                    s += "    </select>";
                    s += "  </div>";
                    s += "</div>";

                    $("#selContent").html(s);
                    
                    $("#editDialog").modal("show");
                }
            }
        });
        return true;
    });

    // 保存起运港新港口信息
    $(".btnSaveNewPortInfo").live("click", function() {
        $.ajax({
          method : "GET",
          data : $("#frmNewPortInfo").formSerialize(),
          url : _rootPath+"/port/saveNewPort",
          datatype : "json",
          success : function(data) {
            var returnCode = $(data)[0].returnCode;
            var message = $(data)[0].message;
            if (returnCode == "Failure") {
              alert(message);
              return false;
            } else {
              window.location.reload();
              return true;
            }
          }
        });
    });
    
    // 保存托运人信息
    $(".btnSaveAll").click(function() {
    	if($("#shipId").val()==0 || $("#shipId").val()==""){
    		$("html,body").animate({scrollTop:0}, 500);
    		alert("请选择船舶");
    		return false;
    	}
        if($("#frmSaveAll").valid()){
            $("#frmSaveAll").ajaxSubmit({
                type : "POST",
                url : _rootPath+"/space/orderCommon/orderSave",
                datatype : "json",
                success : function(data) {
                    var returnCode = $(data)[0].returnCode;
                    var message = $(data)[0].message;
                    var id = $(data)[0].id;
                    if (returnCode == "Failure") {
                        alert(message);
                        return false;
                    } else {
                        alert(message);
                        window.location.href = _rootPath+"/space/orderCommon/orderList";
                        return false;
                    }
                }
            });
        }else{
        	alert("错误，请完善合同条款！");
        	$("html,body").animate({scrollTop:0}, 500);
        }
    });

      // 显示查找用户对话框
      $("#btnChangeOwner").click(function() {
        $("#dlgUserSelector-title").html("查找承租人");
        $("#objUserRole").val("owner");
        $("#dlgUserSelector").modal("show");
        return true;
      });
      $("#btnChangeMaster").click(function() {
        $("#dlgUserSelector-title").html("查找出租人");
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

  	function calc(){
  		var t = $("#days").val() * $("#price").val();
  		$("#transFee").val((Number(t)).toFixed(2));
  	}
  	$("#days").live("blur",function (){ calc(); });
  	$("#price").live("blur",function (){ calc(); });

});
