$(document).ready(function() {
    $('input[type=checkbox],input[type=file]').uniform();
    
    $('select').select2();

    // Form Validation
    $("#frmSaveAll").validate({
        rules:{
        	cargoName:{
                required:true,
                minlength:2,
                maxlength:25
            },
            ton:{
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
            demurrage:{
                required:true,
                number:true,
                checkPrice:true
            },
            startDate:{
                required:true
            },
            endDate:{
                required:true
            },
            upDay:{
                required:true,
                digits:true,
            },
            upHour:{
                required:true,
                digits:true,
            },
            downDay:{
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
        }
    });
    
    // 货类改变时显示相应的货名
    $("#cargoType").live("change",function(){
    	var v = $("#cargoType").find("option:selected").text();
        var prevName = v.substring(0,v.indexOf("."));
        var nextName = v.substring(v.indexOf(".")+1);
        $("#cargoName").val(nextName);
    });
    
    function calc(){
        var t = $("#ton").val() * $("#price").val();
        $("#transFee").val(t);
    }

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

      $("#ton").live("blur",function (){ calc(); });
      $("#price").live("blur",function (){ calc(); });
});
