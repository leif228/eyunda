$(document).ready(function() {
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	// Form Validation
	$("#editDialogForm").validate({
		rules:{
			cargoName:{
				required:true,
				minlength:2,
				maxlength:25
			},
			tonTeu:{
				required:true,
				digits:true
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
	
	$(".account-container").live("click",function(){
		$(this).parent().siblings().removeClass("addBack");
		$(this).parent().addClass("addBack");
		var data = $(this).attr("data-src");
		$("#userId").val(data);
	});

	// 装货港改变
	$("#startPortCity").live("change",function(){
		$.ajax({
			method : "GET",
			data : { portCityCode : $(this).val() },
			url : _rootPath + "/port/getCityCodePorts",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var s = "";
					s += "<select id=\"startPortNo\" name=\"startPortNo\" style=\"width: 220px;\">";
					$.each($(data)[0].portDatas, function(i, portData) {
						s += "<option value=\"" + portData.portNo + "\"> " + portData.portName + "</option>";
					});
					s += "</select>";

					$("#startPortName").html("");
					$("#startPortName").html(s);
					$('select').select2();
					return true;
				}
			}
		});
	});
	
	// 卸货港改变
	$("#endPortCity").live("change", function(){
		$.ajax({
			method : "GET",
			data : { portCityCode : $(this).val() },
			url : _rootPath + "/port/getCityCodePorts",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					var s = "";
					s += "<select id=\"endPortNo\" name=\"endPortNo\" style=\"width: 220px;\">";
					$.each($(data)[0].portDatas,function(i, portData) { 
						s += "<option value=\"" + portData.portNo + "\"> " + portData.portName + "</option>";
					});
					s += "</select>"; 
					$("#endPortName").html("");
					$("#endPortName").html(s);
					$('select').select2();
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
			s += "<label class=\"control-label\">港口城市：</label>";
			s += "<div class=\"controls\">";
			s += "<select id=\"portCityCode\" name=\"portCityCode\" style=\"width:280px;\">";
			$.each($(data)[0].bigAreas, function(i, bigArea) {
			s += "<optgroup label=\""+bigArea.description+"\">";
			$.each(bigArea.portCities, function(i, portCity) {
				s += "<option value=\""+portCity.code+"\"> "+portCity.description+"</option>";
			});
			s += "</optgroup>";
			});
			
			s += "</select>";
			s += "</div>";
			s += "</div>";

			$("#selContent").html(s);
			
			$("#addPortDialog").modal("show");
				}
			}
		});
		return true;
	});

	// 保存新港口信息
	$(".btnSaveNewPortInfo").live("click", function() {
		if ($("#frmNewPortInfo").valid());
		$.ajax({
			method : "get",
			data : $("#frmNewPortInfo").formSerialize(),
			url : _rootPath + "/port/saveNewPort",
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
	
	// 返回
	$(".btnBack").click(function() {
		window.location.href = _rootPath+"/space/cargo/cargoList";
	});
	
	$(".saveMyCargo").live("click", function(){
		if($("#editDialogForm").valid()){
			$("#editDialogForm").ajaxSubmit({
				method : "POST",
				url : _rootPath+"/space/cargo/save",
				datatype : "json",
				success : function(data) {
					var redata = eval('('+data+')');
					var returnCode = redata.returnCode;
					var message = redata.message;
					if (returnCode == "Failure") {
						alert(message);
						return false;
					} else {
						alert(message);
						window.location.href = _rootPath+"/space/cargo/cargoList";
						return false;
					}
				}
			})
		}
	})
	
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