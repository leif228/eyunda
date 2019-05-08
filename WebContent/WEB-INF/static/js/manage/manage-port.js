$(document).ready(function(){
    
    $('input[type=checkbox],input[type=radio],input[type=file]').uniform();

    // Form Validation
    $("#frmSavePort").validate({
        rules:{
            latitude:{
                required:false,
                number:true
            },
            longitude:{
                required:false,
                number:true
            },
            latitude1:{
                required:false,
                number:true
            },
            longitude1:{
                required:false,
                number:true
            },
            latitude2:{
                required:false,
                number:true
            },
            longitude2:{
                required:false,
                number:true
            },
            portName:{
                required:true,
                minlength:2,
                maxlength:10
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
    
    // 添加
    $("#btnAdd").live("click", function() {
        $.ajax({
            method : "GET",
            data : {para : 1},
            url : _rootPath+"/manage/ship/port/add",
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

                    var s = "";
                    s += "<div class=\"control-group\">";
                    s += "  <label class=\"control-label\">港口城市：</label>";
                    s += "  <div class=\"controls\">";
                    s += "    <select id=\"portCityCode\" name=\"portCityCode\" style=\"width:280px;\">";
                    $.each($(data)[0].bigAreas, function(i, bigArea) {
                    s += "      <optgroup label=\""+bigArea.description+"\">";
                    $.each(bigArea.portCities, function(i, portCity) {
                    if ($(data)[0].portData && portCity.code==$(data)[0].portData.portCity.code) {
                    s += "      <option value=\""+portCity.code+"\" selected> "+portCity.description+"</option>";
                    } else {
                    s += "      <option value=\""+portCity.code+"\"> "+portCity.description+"</option>";
                    }
                    });
                    s += "      </optgroup>";
                    });
                    
                    s += "    </select>";
                    s += "  </div>";
                    s += "</div>";
                    s += "<div class=\"control-group\">";
                    s += "  <label class=\"control-label\">港口名称：</label>";
                    s += "  <div class=\"controls\">";
                    s += "    <input type=\"text\" name=\"portName\" id=\"portName\" value=\"\" style=\"width:264px;\" />";
                    s += "  </div>";
                    s += "</div>";
                    s += "";
                    s += "<div class=\"control-group\">";
                    s += "  <label class=\"control-label\">坐标点一：</label>";
                    s += "  <div class=\"controls\">";
                    s += "        经度<input type=\"text\" name=\"longitude\" id=\"longitude\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                    s += "        纬度<input type=\"text\" name=\"latitude\" id=\"latitude\" value=\"\" style=\"width:80px;\" />";
                    s += "  </div>";
                    s += "</div>";
                    s += "";
                    s += "<div class=\"control-group\">";
                    s += "  <label class=\"control-label\">坐标点二：</label>";
                    s += "  <div class=\"controls\">";
                    s += "        经度<input type=\"text\" name=\"longitude1\" id=\"longitude1\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                    s += "        纬度<input type=\"text\" name=\"latitude1\" id=\"latitude1\" value=\"\" style=\"width:80px;\" />";
                    s += "  </div>";
                    s += "</div>";
                    s += "";
                    s += "<div class=\"control-group\">";
                    s += "  <label class=\"control-label\">坐标点三：</label>";
                    s += "  <div class=\"controls\">";
                    s += "        经度<input type=\"text\" name=\"longitude2\" id=\"longitude2\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                    s += "        纬度<input type=\"text\" name=\"latitude2\" id=\"latitude2\" value=\"\" style=\"width:80px;\" />";
                    s += "  </div>";
                    s += "</div>";

                    $("#selContent").html(s);
                    
                    $("#editDialog").modal("show");
                    
                    return true;
                }
            }
        });
    });

    // 修改
    $(".btnEdit").live("click", function() {
        $.ajax({
            method : "GET",
            data : {portNo : $(this).attr("idVal")},
            url : _rootPath+"/manage/ship/port/edit",
            datatype : "json",
            success : function(data) {
                var returnCode = $(data)[0].returnCode;
                var message = $(data)[0].message;
                if (returnCode == "Failure") {
                    alert(message);
                    return false;
                } else {
                    
                    //读入数据，并填入
                    $("#portNo").val($(data)[0].portData.portNo);

                    var s = "";
                    s += "<div class=\"control-group\">";
                    s += "  <label class=\"control-label\">港口城市：</label>";
                    s += "  <div class=\"controls\">";
                    s += "    <select id=\"portCityCode\" name=\"portCityCode\" style=\"width:280px;\">";

                    $.each($(data)[0].bigAreas, function(i, bigArea) {
                    s += "      <optgroup label=\""+bigArea.description+"\">";
                    $.each(bigArea.portCities, function(i, portCity) {
                    if ($(data)[0].portData && portCity.code==$(data)[0].portData.portCity.code) {
                    s += "      <option value=\""+portCity.code+"\" selected> "+portCity.description+"</option>";
                    } else {
                    s += "      <option value=\""+portCity.code+"\"> "+portCity.description+"</option>";
                    }
                    });
                    s += "      </optgroup>";
                    });
                    
                    s += "    </select>";
                    s += "  </div>";
                    s += "</div>";
                    
                    s += "<div class=\"control-group\">";
                    s += "  <label class=\"control-label\">港口名称：</label>";
                    s += "  <div class=\"controls\">";
                    s += "    <input type=\"text\" name=\"portName\" id=\"portName\" value=\""+$(data)[0].portData.portName+"\" style=\"width:264px;\" />";
                    s += "  </div>";
                    s += "</div>";
                    s += "";
                    var size = 0;
                    if($(data)[0].portCooordDatas)
                        size = $(data)[0].portCooordDatas.length;
                    
                    if(size == 0){
                        s += "<div class=\"control-group\">";
                        s += "  <label class=\"control-label\">坐标点一：</label>";
                        s += "  <div class=\"controls\">";
                        s += "        经度<input type=\"text\" name=\"longitude\" id=\"longitude\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                        s += "        纬度<input type=\"text\" name=\"latitude\" id=\"latitude\" value=\"\" style=\"width:80px;\" />";
                        s += "  </div>";
                        s += "</div>";
                        s += "";
                        s += "<div class=\"control-group\">";
                        s += "  <label class=\"control-label\">坐标点二：</label>";
                        s += "  <div class=\"controls\">";
                        s += "        经度<input type=\"text\" name=\"longitude1\" id=\"longitude1\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                        s += "        纬度<input type=\"text\" name=\"latitude1\" id=\"latitude1\" value=\"\" style=\"width:80px;\" />";
                        s += "  </div>";
                        s += "</div>";
                        s += "";
                        s += "<div class=\"control-group\">";
                        s += "  <label class=\"control-label\">坐标点三：</label>";
                        s += "  <div class=\"controls\">";
                        s += "        纬度<input type=\"text\" name=\"latitude2\" id=\"latitude2\" value=\"\" style=\"width:80px;\" />";
                        s += "        经度<input type=\"text\" name=\"longitude2\" id=\"longitude2\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                        s += "  </div>";
                        s += "</div>";
                    }else if(size == 1){
                        s += "<div class=\"control-group\">";
                        s += "  <label class=\"control-label\">坐标点一：</label>";
                        s += "  <div class=\"controls\">";
                        s += "        经度<input type=\"text\" name=\"longitude\" id=\"longitude\" value=\""+$(data)[0].portCooordDatas[0].longitude+"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                        s += "        纬度<input type=\"text\" name=\"latitude\" id=\"latitude\" value=\""+$(data)[0].portCooordDatas[0].latitude+"\" style=\"width:80px;\" />";
                        s += "  </div>";
                        s += "</div>";
                        s += "";
                        s += "<div class=\"control-group\">";
                        s += "  <label class=\"control-label\">坐标点二：</label>";
                        s += "  <div class=\"controls\">";
                        s += "        经度<input type=\"text\" name=\"longitude1\" id=\"longitude1\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                        s += "        纬度<input type=\"text\" name=\"latitude1\" id=\"latitude1\" value=\"\" style=\"width:80px;\" />";
                        s += "  </div>";
                        s += "</div>";
                        s += "";
                        s += "<div class=\"control-group\">";
                        s += "  <label class=\"control-label\">坐标点三：</label>";
                        s += "  <div class=\"controls\">";
                        s += "        经度<input type=\"text\" name=\"longitude2\" id=\"longitude2\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                        s += "        纬度<input type=\"text\" name=\"latitude2\" id=\"latitude2\" value=\"\" style=\"width:80px;\" />";
                        s += "  </div>";
                        s += "</div>";
                    }else if(size == 2){
                        s += "<div class=\"control-group\">";
                        s += "  <label class=\"control-label\">坐标点一：</label>";
                        s += "  <div class=\"controls\">";
                        s += "        经度<input type=\"text\" name=\"longitude\" id=\"longitude\" value=\""+$(data)[0].portCooordDatas[0].longitude+"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                        s += "        纬度<input type=\"text\" name=\"latitude\" id=\"latitude\" value=\""+$(data)[0].portCooordDatas[0].latitude+"\" style=\"width:80px;\" />";
                        s += "  </div>";
                        s += "</div>";
                        s += "";
                        s += "<div class=\"control-group\">";
                        s += "  <label class=\"control-label\">坐标点二：</label>";
                        s += "  <div class=\"controls\">";
                        s += "        经度<input type=\"text\" name=\"longitude1\" id=\"longitude1\" value=\""+$(data)[0].portCooordDatas[1].longitude+"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                        s += "        纬度<input type=\"text\" name=\"latitude1\" id=\"latitude1\" value=\""+$(data)[0].portCooordDatas[1].latitude+"\" style=\"width:80px;\" />";
                        s += "  </div>";
                        s += "</div>";
                        s += "";
                        s += "<div class=\"control-group\">";
                        s += "  <label class=\"control-label\">坐标点三：</label>";
                        s += "  <div class=\"controls\">";
                        s += "        经度<input type=\"text\" name=\"longitude2\" id=\"longitude2\" value=\"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                        s += "        纬度<input type=\"text\" name=\"latitude2\" id=\"latitude2\" value=\"\" style=\"width:80px;\" />";
                        s += "  </div>";
                        s += "</div>";
                    }else if(size == 3){
                        s += "<div class=\"control-group\">";
                        s += "  <label class=\"control-label\">坐标点一：</label>";
                        s += "  <div class=\"controls\">";
                        s += "        经度<input type=\"text\" name=\"longitude\" id=\"longitude\" value=\""+$(data)[0].portCooordDatas[0].longitude+"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                        s += "        纬度<input type=\"text\" name=\"latitude\" id=\"latitude\" value=\""+$(data)[0].portCooordDatas[0].latitude+"\" style=\"width:80px;\" />";
                        s += "  </div>";
                        s += "</div>";
                        s += "";
                        s += "<div class=\"control-group\">";
                        s += "  <label class=\"control-label\">坐标点二：</label>";
                        s += "  <div class=\"controls\">";
                        s += "        经度<input type=\"text\" name=\"longitude1\" id=\"longitude1\" value=\""+$(data)[0].portCooordDatas[1].longitude+"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                        s += "        纬度<input type=\"text\" name=\"latitude1\" id=\"latitude1\" value=\""+$(data)[0].portCooordDatas[1].latitude+"\" style=\"width:80px;\" />";
                        s += "  </div>";
                        s += "</div>";
                        s += "";
                        s += "<div class=\"control-group\">";
                        s += "  <label class=\"control-label\">坐标点三：</label>";
                        s += "  <div class=\"controls\">";
                        s += "        经度<input type=\"text\" name=\"longitude2\" id=\"longitude2\" value=\""+$(data)[0].portCooordDatas[2].longitude+"\" style=\"width:80px;\" />&nbsp;&nbsp;";
                        s += "        纬度<input type=\"text\" name=\"latitude2\" id=\"latitude2\" value=\""+$(data)[0].portCooordDatas[2].latitude+"\" style=\"width:80px;\" />";
                        s += "  </div>";
                        s += "</div>";
                    }


                    $("#selContent").html(s);
                    
                    $("#editDialog").modal("show");
                    
                    return true;
                }
            }
        });
        
    });
    
    // 删除
    $(".btnDelete").live("click", function() {
        $("#delPortNo").val($(this).attr("idVal"));
        
        $("#deleteDialog").modal("show");
        
        return true;
    });
    

    // 保存港口信息
    $(".btnSavePort").live("click", function() {
    	if($("#frmSavePort").valid())
        $.ajax({
          method : "GET",
          data : $("#frmSavePort").formSerialize(),
          url : _rootPath+"/manage/ship/port/save",
          datatype : "json",
          success : function(data) {
            var returnCode = $(data)[0].returnCode;
            var message = $(data)[0].message;
            if (returnCode == "Failure") {
              alert(message);
              return false;
            } else {
              var params = "?nonsense＝0";
              var t = $("#frmSavePort").serializeArray();
              $.each(t, function() {
                params+="&"+this.name+"="+this.value;
              });
              window.location.href = _rootPath + "/manage/ship/port" + params;
            }
          }
        });
    });

    // 删除港口信息
    $(".btnDeletePort").live("click", function() {
        $.ajax({
          method : "GET",
          data : $("#frmDeletePort").formSerialize(),
          url : _rootPath+"/manage/ship/port/delete",
          datatype : "json",
          success : function(data) {
            var returnCode = $(data)[0].returnCode;
            var message = $(data)[0].message;
            if (returnCode == "Failure") {
              alert(message);
              return false;
            } else {
              var params = "?nonsense＝0";
              var t = $("#frmDeletePort").serializeArray();
              $.each(t, function() {
                params+="&"+this.name+"="+this.value;
              });
              window.location.href = _rootPath + "/manage/ship/port" + params;
            }
          }
        });
    });

});
