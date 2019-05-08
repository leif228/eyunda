$(document).ready(function(){

	$('input[type=checkbox],input[type=file]').uniform();

	$('select').select2();

	$("#frmUpDownShipArvlft").validate({
		rules:{
			cargoName:{
				required:true,
				minlength:1,
				maxlength:25
			},
			tonTeu:{
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
	// 返回
	$(".btnBack").click(function() {
		window.location.href = _rootPath+"/space/state/myShip/shipArvlft?mmsi="+$("#mmsi").val()+"";
	});


	// 保存上报到离信息
	$(".btnSaveUpDownShipArvlft").live("click",function() {
		if ($("#frmUpDownShipArvlft").valid())
		$("#frmUpDownShipArvlft").ajaxSubmit({
			method : "POST",
			url : _rootPath+"/space/state/myShip/shipArvlft/saveUpdown",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
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

	//货类改变时显示相应的货名
	$(".cargoType").live("change",function(){
		if($(this).val().indexOf("container") >= 0){
			var s = "规格:&nbsp;&nbsp;<input placeholder=\"请输入货名\" style=\"width:125px;\" type=\"text\"" +
					"name=\"cargoName\" value=\""+$(this).find("option:selected").text().substr(4)+"\" />";
			$(this).parent().next("span").html(s);
			
			var ss = "货量(个):&nbsp;&nbsp;<input placeholder=\"请输入货量\" style=\"width:125px;\" type=\"text\" name=\"tonTeu\" value=\"\" />";
			$(this).parent().next("span").next("span").html(ss);
		}else{
			var s = "货名:&nbsp;&nbsp;<input placeholder=\"请输入货名\" style=\"width:125px;\" type=\"text\"" +
					"name=\"cargoName\" value=\""+$(this).find("option:selected").text().substr(4)+"\" />";
			$(this).parent().next("span").html(s);
			
			var ss = "货量(吨):&nbsp;&nbsp;<input placeholder=\"请输入货量\" style=\"width:125px;\" type=\"text\" name=\"tonTeu\" value=\"\" />";
			$(this).parent().next("span").next("span").html(ss);
		}
	});
	
	// 装货
	  $(".upCargos").live("click", function() {
		  var s="";
		  
		  s += "  <div class=\"oneCargo\">";
		  s += "	<div class=\"control-group\">";
		  s += "	<label class=\"control-label\" for=\"cargoType\">货类:</label>";
		  s += "	  <div class=\"controls\">";
		  s += "  	    <span>";
		  s += "     	  <select id=\"cargoType\" name=\"cargoType\" class=\"cargoType\" style=\"width:160px;\">";
		  for(var i=0;i<cargoBigTypes.length;i++){
			  s +=  " 		<optgroup label=\""+cargoBigTypes[i].bigTypeDescription+"\">";
			  for(var j=0;j<cargoBigTypes[i].cargoTypes.length;j++){
				  s += "      <option value=\""+cargoBigTypes[i].cargoTypes[j].cargoType+"\">"+cargoBigTypes[i].cargoTypes[j].description+"</option>";
			  }
			  s +=  " 		</optgroup>";
		  }
		  s += "     	  </select>";
		  s += "  		</span>";
		  s += "    	<span>";
		  s += "        	规格:&nbsp;&nbsp;<input placeholder=\"请输入货名\" style=\"width:125px;\" type=\"text\" name=\"cargoName\" value=\"\" />";
		  s += "    	</span>";
		  s += "		<span>";
		  s += "        	货量(个):&nbsp;&nbsp;<input placeholder=\"请输入货量\" style=\"width:125px;\" type=\"text\" name=\"tonTeu\" value=\"\" />";
		  s += "    	</span>";
		  s += "  		<span>";
		  s += "    	  <a href=\"javascript:void(0);\" class=\"btn btn-danger delCargos\">";
		  s += "            <i class=\"icon-trash icon-white\"></i>删除";
		  s += "          </a>";
		  s += "  	    </span>";
		  s += "	  </div>";
		  s += "	</div>";
		  s += "  <div style=\"width:720px;margin-left:130px;margin-top:10px;\" class=\"divider-content\">";
		  s += "    <span></span>";
		  s += "  </div>";
		  s += "</div>";
		  
		  $(".upDownCargos").append(s);
		  $('select').select2();
	  });
	
	// 删除
	$(".delCargos").live("click", function() {
		$(this).parent().parent().parent().parent().remove();
	});
	
	 // 到离改变
	$("#arvlft").change(function(){
		if($(this).val() == "arrive"){
			$("#goPort").html("");
			var s1="";
			s1 += "<label>";
			s1 += "	<a href=\"javascript:void(0);\" class=\"btn btn-primary\">";
			s1 += "		<i class=\"icon-plus icon-white\"></i>卸货";
			s1 += "	</a>";
			s1 += "</label>";
			$(".upCargos").html("");
			$(".upCargos").html(s1);
		}else{
			var s="";
			
			s += "<div class=\"control-group\">";
			s += "<label class=\"control-label\" for=\"shipName\">将去港口：</label>";
			s += "<div class=\"controls\">";
			s += "<span class=\"endPortCityName\">";
			s += " <select id=\"endPortCity\" name=\"endPortCity\">";
			for(var i=0;i<bigAreas.length;i++){
			s +=" <optgroup label=\""+bigAreas[i].bigAreaDescription+"\">";
				var portCities = bigAreas[i].portCities;
				for(var j=0;j<portCities.length;j++){
			s += "<option value=\""+portCities[j].portCityCode+"\"> "+portCities[j].description+"</option>";
					
				}
			s +=" </optgroup>";
			}
			s += "</select>";
			s += "</span>";
			s += "<span id=\"endPortName\">";
			s += "</span>";
			s += "<a href=\"javascript:void(0);\" class=\"btn btn-danger btnAddPort\">";
			s += "<i class=\"icon-plus icon-white\"></i>新增港口";
			s += "</a>";
			s += "</div>";
			s += "</div>";
			
			$("#goPort").html("");
			$("#goPort").html(s);
			var s1="";
			s1 += "<label>";
			s1 += "	<a href=\"javascript:void(0);\" class=\"btn btn-primary\">";
			s1 += "		<i class=\"icon-plus icon-white\"></i>装货";
			s1 += "	</a>";
			s1 += "</label>";
			$(".upCargos").html("");
			$(".upCargos").html(s1);
			
			$('select').select2();
			$("#endPortCity").trigger("change");
			
		}
		
	});
	
	//数量失去焦点
	$('.wrapCount').live("blur",function (){
		var unitWeightVal=$(this).parent().next("span").children("input").val();
		if(unitWeightVal != "" && typeof(unitWeightVal) != "undefined"){
			var fullWeightVal = unitWeightVal*1*$(this).val();
			$(this).parent().next("span").next("span").children("input").val(fullWeightVal.toFixed(3));
		}
	});
	
	//单重失去焦点
	$('.unitWeight').live("blur",function (){
		var countVal=$(this).parent().prev("span").children("input").val();
		if(countVal != "" && typeof(countVal) != "undefined"){
			var fullWeightVal = countVal*1*$(this).val();
			$(this).parent().next("span").children("input").val(fullWeightVal.toFixed(3));
		}
	});
	
});


