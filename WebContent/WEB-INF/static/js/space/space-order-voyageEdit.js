$(document).ready(function() {
    $('input[type=checkbox],input[type=file]').uniform();
    
    $('select').select2();

    // Form Validation
    $("#frmSaveAll").validate({
        rules:{
            transFee:{
                required:true,
                number:true,
                checkPrice:true
            },
            demurrage:{
                required:true,
                number:true,
                checkPrice:true
            },
            upDate:{
                required:true
            },
            downDate:{
                required:true
            },
            upDay:{
                required:true,
                digits:true,
            },
            downDay:{
                required:true,
                digits:true,
            },
            upHour:{
                required:true,
                digits:true,
            },
            downHour:{
                required:true,
                digits:true,
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
    
    $(".account-container").live("click",function(){
        $(this).parent().siblings().removeClass("addBack");
        $(this).parent().addClass("addBack");
        var data = $(this).attr("idVal");
        $("#selShipId").val(data);
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
    $("#sailLine").change(function(){
    	var distance=$(this).find("option:selected").attr("distanceVal");
    	var weight=$(this).find("option:selected").attr("weightVal");
    	
    	$("#distance").val(distance);
    	$("#weight").val(weight);
    	
    	if (_cargoType == 'container20e') {
    		$("#price-0").val($(this).find("option:selected").attr("price-0Val"));
    		$("#price-1").val($(this).find("option:selected").attr("price-1Val"));
    		$("#price-2").val($(this).find("option:selected").attr("price-2Val"));
    		$("#price-3").val($(this).find("option:selected").attr("price-3Val"));
    		$("#price-4").val($(this).find("option:selected").attr("price-4Val"));
    		$("#price-5").val($(this).find("option:selected").attr("price-5Val"));
    		calc();
    	} else {
    		$("#prices").val($(this).find("option:selected").attr("pricesVal"));
    		calc2();
    	}
    	
    	str=$(this).val();
    	var strs= new Array();
    	strs=str.split("-");
    	
    	$("#startPortNo").val(strs[0]);
    	$("#endPortNo").val(strs[1]);
    });

    // 起运港改变
    $("#startPortCity").change(function(){
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
                      s += "    <select id=\"startPortNo\" name=\"startPortNo\" >";
                    $.each($(data)[0].portDatas, function(i, portData) {
                    if (_startPortNo==portData.portNo) {
                    s += "      <option value=\""+portData.portNo+"\" selected > "+portData.portName+"</option>";
                    } else {
                    s += "      <option value=\""+portData.portNo+"\" > "+portData.portName+"</option>";
                    }
                    });
                    s += "    </select>";
                    
                    $("#startPortName").html("");
                    $("#startPortName").html(s);
                    
                    $("#startPortNo").select2();
                    
                    return true;
                }
            }
        });

    });
    
    // 终止港改变
    $("#endPortCity").change(function(){
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
                      s += "    <select id=\"endPortNo\" name=\"endPortNo\" >";
                    $.each($(data)[0].portDatas, function(i, portData) {
                    if (_endPortNo==portData.portNo) {
                    s += "      <option value=\""+portData.portNo+"\" selected > "+portData.portName+"</option>";
                    } else {
                    s += "      <option value=\""+portData.portNo+"\" > "+portData.portName+"</option>";
                    }
                    });
                    s += "    </select>";
                    
                    $("#endPortName").html("");
                    $("#endPortName").html(s);
                    
                    $("#endPortNo").select2();

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
        $("#objUserRole").val("owner");
        $("#dlgUserSelector").modal("show");
        return true;
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

      //更新托运人或更新承运人
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

	function calc2(){
		var tt = Number($("#tonTeus").val());
		var ww = Number($("#weight").val());
		
		if (tt > ww) {
			alert("警告：货量不能超过载重量！");
			$("#tonTeus").val($("#weight").val());
		}
		var t = $("#tonTeus").val() * $("#prices").val();
		$("#transFee").val((Number(t)).toFixed(2));
	}
	$("#tonTeus").live("blur",function (){ calc2(); });
	$("#prices").live("blur",function (){ calc2(); });
	
	function calc(){
		var t0 = $("#tonTeu-0").val() * $("#price-0").val();
		var t1 = $("#tonTeu-1").val() * $("#price-1").val();
		var t2 = $("#tonTeu-2").val() * $("#price-2").val();
		var t3 = $("#tonTeu-3").val() * $("#price-3").val();
		var t4 = $("#tonTeu-4").val() * $("#price-4").val();
		var t5 = $("#tonTeu-5").val() * $("#price-5").val();
		
		var tt = Number($("#tonTeu-0").val())+Number($("#tonTeu-1").val())+2*Number($("#tonTeu-2").val())
					+2*Number($("#tonTeu-3").val())+2*Number($("#tonTeu-4").val())+2*Number($("#tonTeu-5").val());
		var ww = Number($("#containerCount").val());
		// console.log(tt);
		// console.log(ww);
		if (tt > ww) {
			alert("警告：集装箱数量不能超过船舶载箱量！");
			$("#tonTeu-0").val(0);
			$("#tonTeu-1").val(0);
			$("#tonTeu-2").val(0);
			$("#tonTeu-3").val(0);
			$("#tonTeu-4").val(0);
			$("#tonTeu-5").val(0);
		}
		
		$("#transFee").val((Number(t0+t1+t2+t3+t4+t5)).toFixed(2));
	}
	//失去焦点
	$("#tonTeu-0").live("blur",function (){ calc(); });
	$("#tonTeu-1").live("blur",function (){ calc(); });
	$("#tonTeu-2").live("blur",function (){ calc(); });
	$("#tonTeu-3").live("blur",function (){ calc(); });
	$("#tonTeu-4").live("blur",function (){ calc(); });
	$("#tonTeu-5").live("blur",function (){ calc(); });
	$("#price-0").live("blur",function (){ calc(); });
	$("#price-1").live("blur",function (){ calc(); });
	$("#price-2").live("blur",function (){ calc(); });
	$("#price-3").live("blur",function (){ calc(); });
	$("#price-4").live("blur",function (){ calc(); });
	$("#price-5").live("blur",function (){ calc(); });
});
