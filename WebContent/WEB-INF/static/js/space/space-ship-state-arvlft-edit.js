$(document).ready(function(){

  $('input[type=checkbox],input[type=file]').uniform();
  
  $('select').select2();
  
  $("#frmShipArvlft").validate({
	rules:{
		arriveTime:{
			required:true
		},
		remark:{
			required:false,
			minlength:2,
			maxlength:25
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
  
  $("#frmNewPortInfo").validate({
		rules:{
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
  
  // 返回
  $(".btnBack").click(function() {
    window.location.href = _rootPath+"/space/state/myShip/shipArvlft?mmsi="+$("#mmsi").val()+"";
  });
  
  
  // 保存上报到离信息
  $(".btnSaveShipArvlft").live("click",function() {
	if (_saveFlag != true)
		return;
	_saveFlag = false;
	
	if ($("#frmShipArvlft").valid())
    $("#frmShipArvlft").ajaxSubmit({
      method : "POST",
      url : _rootPath+"/space/state/myShip/shipArvlft/saveShipArvlft",
      datatype : "json",
      success : function(data) {
    	var returnCode = $(data)[0].returnCode;
		var message = $(data)[0].message;
        if (returnCode == "Failure") {
          _saveFlag = true;
          alert(message);
          return false;
        } else {
          window.location.href = _rootPath+"/space/state/myShip/shipArvlft?mmsi="+$(data)[0].mmsi+"";
          alert(message);
          return true;
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
              	s += "    <select id=\"startPortNo\" name=\"portNo\" >";
                $.each($(data)[0].portDatas, function(i, portData) {
                s += "      <option value=\""+portData.portNo+"\"> "+portData.portName+"</option>";
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
  $("#endPortCity").live("change",function(){
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
                s += "    <select id=\"endPortNo\" name=\"goPortNo\" >";
                s += "      <option value=\"0\">请选择</option>";
                $.each($(data)[0].portDatas, function(i, portData) {
                s += "      <option value=\""+portData.portNo+"\"> "+portData.portName+"</option>";
                });
                s += "  </select>";
                
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
	if ($("#frmNewPortInfo").valid())
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


