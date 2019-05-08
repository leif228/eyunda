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

//我要代理
$(".iWillOper").live("click", function() {
	$("#operCargoId").val($(this).attr("idVal"));
	
	$("#operCargoDialog").modal("show");
	
	return true;
});

//提交我要代理form
$(".btnOperCargo").click(function() {
	$("#operCargoDialog").modal("hide");
	$("#operCargoDialogForm").ajaxSubmit({
	     method : "GET",
	     data : {cargoId : $("#operCargoId").val()},
	     url : _rootPath+"/space/cargo/myCargo/saveOperCargo",
	     datatype : "json",
	     success : function(data) {
	       var returnCode = $(data)[0].returnCode;
	       var message = $(data)[0].message;
	       if (returnCode == "Failure") {
	         alert(message);
	         return false;
	       } else {
	    	   
	    	   alert(message);
	    	   window.location.href = _rootPath+"/portal/home/sortCargoList?pageNo="+pageNo+"&cargoTypeCode="+cargoTypeCode+"&bigAreaCode="+bigAreaCode+"";
				return true;
		       }
		     }
		   });
});
