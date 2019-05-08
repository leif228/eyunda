$(document).ready(function(){
// 搜索信息
	$("#serachRelease").click(function() {
		$("#frmSerachRelease").ajaxSubmit({
			method : "POST",
			url : _rootPath+"/portal/home/searchInfo",
		    datatype : "json",
			success : function(data) {
		    	var returnCode = $(data)[0].returnCode;
		    	var keyWords = $(data)[0].keyWords;
		    	var message = $(data)[0].message;
		    	var searchRlsCode=$(data)[0].searchRlsCode;
		    	  
		    	if (returnCode == "Failure") {
		    		alert(message);
		    		return false;
		    	} else {
		    		if(searchRlsCode=="shipsearch"){
		    			//搜船舶
	    				window.location.href="searchShip?keyWords="+$(data)[0].keyWords+"&searchRlsCode="+$(data)[0].searchRlsCode+"";
		    		}else{
		    			//搜货物
		    			window.location.href="searchRelCargo?keyWords="+$(data)[0].keyWords+"&searchRlsCode="+$(data)[0].searchRlsCode+"";
		    	}
		    	  
		    		return true;
		    	}
			}
	    });
	});

	$("#keyWords").keydown(function(e) {
		if (e.keyCode == 13){
			$("#serachRelease").click();
			return false;
		}
	});
	
	$("#changeRecvCargoType").change(function(){
		var cargoType = $(this).val();
		if(cargoType.indexOf("container") > -1){
			$.ajax({
				method : 'GET',
				url : _rootPath + '/portal/home/containerSelect',
				data : {cargoTypeCode : cargoType},
				datatype : 'json',
				success : function(data){
					var returnCode = $(data)[0].returnCode;
					var message = $(data)[0].message;
					if(returnCode == 'FAILURE'){
						alert(message);
					}else{
						var cargoVolumnCodes = $(data)[0].cargoVolumnCodes;
						var descriptions = $(data)[0].descriptions;
						var s = "<option value=\"\" selected>全部载货/箱量...</option>";
                        $.each(cargoVolumnCodes,function(i, cargoVolumnCode){
                        	s += "<option value=\"" + cargoVolumnCode + "\" >" + descriptions[i] + "</option>";
                        })
                        $("#changeLoadCargo").html(s);
					}
				}
			});
		}else {
			$.ajax({
				method : 'GET',
				url : _rootPath + '/portal/home/containerSelect',
				data : {cargoTypeCode : cargoType},
				datatype : 'json',
				success : function(data){
					var returnCode = $(data)[0].returnCode;
					var message = $(data)[0].message;
					if(returnCode == 'FAILURE'){
						alert(message);
					}else{
						var cargoWeightCodes = $(data)[0].cargoWeightCodes;
						var descriptions = $(data)[0].descriptions;
						var s = "<option value=\"\" selected>全部载货/箱量...</option>";
                        $.each(cargoWeightCodes,function(i, cargoWeightCode){
                        	s += "<option value=\"" + cargoWeightCode + "\" >" + descriptions[i] + "</option>";
                        })
                        $("#changeLoadCargo").html(s);
					}
				}
			});
		}
	});
	
	$("#changeCargoType").change(function(){
		var cargoTypeCode = $(this).val();
		if(cargoTypeCode.indexOf('container') > -1){
			$.ajax({
				method : 'GET',
				url : _rootPath + '/portal/home/containerSelect',
				data : {cargoTypeCode : cargoTypeCode},
				datatype : 'json',
				success : function(data){
					var returnCode = $(data)[0].returnCode;
					var message = $(data)[0].message;
					if(returnCode == 'FAILURE'){
						alert(message);
					}else{
						var cargoVolumnCodes = $(data)[0].cargoVolumnCodes;
						var descriptions = $(data)[0].descriptions;
						var s = "<option value=\"\" selected>全部货/箱量...</option>";
                        $.each(cargoVolumnCodes,function(i, cargoVolumnCode){
                        	s += "<option value=\"" +  cargoVolumnCode + "\" >" + descriptions[i] + "</option>";
                        })
                        $("#changeCargoWeight").html(s);
					}
				}
			});
		}else {
			$.ajax({
				method : 'GET',
				url : _rootPath + '/portal/home/containerSelect',
				data : {cargoTypeCode : cargoTypeCode},
				datatype : 'json',
				success : function(data){
					var returnCode = $(data)[0].returnCode;
					var message = $(data)[0].message;
					if(returnCode == 'FAILURE'){
						alert(message);
					}else{
						var cargoWeightCodes = $(data)[0].cargoWeightCodes;
						var descriptions = $(data)[0].descriptions;
						var s = "<option value=\"\" selected>全部货/箱量...</option>";
                        $.each(cargoWeightCodes,function(i, cargoWeightCode){
                        	s += "<option value=\"" + cargoWeightCode + "\" >" + descriptions[i] + "</option>";
                        })
                        $("#changeCargoWeight").html(s);
					}
				}
			});
		}
	});
	
	$(".searchShips").live("click",function(){
		var s = "";
		if($("#changeRecvCargoType").val().indexOf('container') > -1)
			s = "&cargoVolumn=" + $("#changeLoadCargo").val();
		else 
			s = "&cargoWeight=" + $("#changeLoadCargo").val();
		window.location.href=_rootPath+"/portal/home/shipHome" 
				+ "?recvCargoPort=" + $("#changePortCity").val()
				+ "&cargoType=" + $("#changeRecvCargoType").val() + s;
	});
	
	$(".searchCargo").live("click",function(){
		var s = "";
		if($("#changeCargoType").val().indexOf('container') > -1)
			s = "&cargoVolumn=" + $("#changeCargoWeight").val();
		else 
			s = "&cargoWeight=" + $("#changeCargoWeight").val();
			
		window.location.href=_rootPath+"/portal/home/cargoHome"
			+ "?cargoType=" + $("#changeCargoType").val() + s
			+ "&upPort=" + $("#changeUpPortCity").val()
			+ "&downPort=" + $("#changeDownPortCity").val();
	});
	
});
