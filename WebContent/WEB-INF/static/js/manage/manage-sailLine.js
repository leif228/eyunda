$(document).ready(function(){
    
    $('input[type=checkbox],input[type=radio],input[type=file]').uniform();

    // Form Validation
    $("#frmSaveSailLine").validate({
        rules:{
            distance:{
                required:true,
                number:true
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
    	$("#editId").val("0");
    	$("#distance").val("0");
    	$("#weight").val("0");

    	$("#startPortCity").val("11");
    	$("#endPortCity").val("11");

    	$("#startPortCity").trigger("change");
        $("#endPortCity").trigger("change");
    	
        var ctis = "";
        var pis = "";

        var remark = _remark;
        var description = _description;

        var arrRemark = remark.split(",");
    	var arrDesc = description.split(",");

    	for (i=0;i<arrRemark.length;i++) {
    		var r = arrRemark[i];
    		var d = arrDesc[i];
    		
    		var rItems = r.split("^");
    		var dItems = d.split(":");

    		var cargoTypeName = rItems[0];
    		var cargoTypeDesc = dItems[0];
    		var price = rItems[1];
    		
    		ctis += "<input type=\"text\" style=\"width: 80px;\" name=\"cargoType\" readonly=\"true\" value=\""+cargoTypeDesc+"\" />";
    		pis += "<input type=\"text\" placeholder=\"请输入数字\" style=\"width: 80px;\" name=\""+cargoTypeName+"\" value=\""+price+"\" />";
    	}
    	$("#cargoTypeItems").html(ctis);
    	$("#priceItems").html(pis);
    	
    	$("#editDialog").modal("show");
    });
    
    // 导入
    $("#importExcel").live("click", function() {
    	$("#importExcelDialog").modal("show");
    });
    
    // 修改
    $(".btnEdit").live("click", function() {
    	$("#editId").val($(this).attr("idVal"));
    	$("#distance").val($(this).attr("distanceVal"));
    	$("#weight").val($(this).attr("weightVal"));
    	
    	$("#startPortCity").val($(this).attr("startPortCityVal"));
    	$("#endPortCity").val($(this).attr("endPortCityVal"));

    	_stp = $(this).attr("startPortNoVal");
    	_edp = $(this).attr("endPortNoVal");
    	
    	$("#startPortCity").trigger("change");
        $("#endPortCity").trigger("change");
        
    	var ctis = "";
        var pis = "";

        var remark = $(this).attr("remarkVal");
        var description = $(this).attr("descriptionVal");

        var arrRemark = remark.split(",");
     	var arrDesc = description.split(",");

     	for (i=0;i<arrRemark.length;i++) {
     		var r = arrRemark[i];
     		var d = arrDesc[i];
     		
     		var rItems = r.split("^");
     		var dItems = d.split(":");

     		var cargoTypeName = rItems[0];
     		var cargoTypeDesc = dItems[0];
     		var price = rItems[1];
     		
     		ctis += "<input type=\"text\" style=\"width: 80px;\" name=\"cargoType\" readonly=\"true\" value=\""+cargoTypeDesc+"\" />";
     		pis += "<input type=\"text\" placeholder=\"请输入数字\" style=\"width: 80px;\" name=\""+cargoTypeName+"\" value=\""+price+"\" />";
     	}
     	$("#cargoTypeItems").html(ctis);
     	$("#priceItems").html(pis);

        $("#editDialog").modal("show");
    });

    // 删除
    $(".btnDelete").live("click", function() {
        $("#delId").val($(this).attr("idVal"));
        $("#deleteDialog").modal("show");
    });
    
    // 保存港口信息
    $(".btnSaveSailLine").live("click", function() {
    	if($("#frmSaveSailLine").valid())
        $.ajax({
          method : "GET",
          data : $("#frmSaveSailLine").formSerialize(),
          url : _rootPath+"/manage/ship/sailLine/save",
          datatype : "json",
          success : function(data) {
            var returnCode = $(data)[0].returnCode;
            var message = $(data)[0].message;
            if (returnCode == "Failure") {
              alert(message);
              return false;
            } else {
              var params = "?nonsense＝0";
              var t = $("#frmSaveSailLine").serializeArray();
              $.each(t, function() {
                params+="&"+this.name+"="+this.value;
              });
              window.location.href = _rootPath + "/manage/ship/sailLine" + params;
            }
          }
        });
    });

    // 删除港口信息
    $(".btnDeleteSailLine").live("click", function() {
        $.ajax({
          method : "GET",
          data : $("#frmDeleteSailLine").formSerialize(),
          url : _rootPath+"/manage/ship/sailLine/delete",
          datatype : "json",
          success : function(data) {
            var returnCode = $(data)[0].returnCode;
            var message = $(data)[0].message;
            if (returnCode == "Failure") {
              alert(message);
              return false;
            } else {
              var params = "?nonsense＝0";
              var t = $("#frmDeleteSailLine").serializeArray();
              $.each(t, function() {
                params+="&"+this.name+"="+this.value;
              });
              window.location.href = _rootPath + "/manage/ship/sailLine" + params;
            }
          }
        });
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
                    s += "    <select id=\"startPortNo\" name=\"startPortNo\" style=\"width: 120px;\" >";
                    $.each($(data)[0].portDatas, function(i, portData) {
                    	if (portData.portNo==_stp){
                    		s += "      <option value=\""+portData.portNo+"\" selected > "+portData.portName+"</option>";
                    	}else{
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
                    s += "    <select id=\"endPortNo\" name=\"endPortNo\" style=\"width: 120px;\" >";
                    $.each($(data)[0].portDatas, function(i, portData) {
                    	if (portData.portNo==_edp){
                    		s += "      <option value=\""+portData.portNo+"\" selected > "+portData.portName+"</option>";
                    	}else{
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
                    
                    $("#addPortDialog").modal("show");
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

});
