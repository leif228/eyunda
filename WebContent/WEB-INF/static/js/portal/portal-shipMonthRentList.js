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

$(".cityPortNo").live("click",function(){
	$(this).parent().siblings().removeClass("selectClass");
	$(this).parent().addClass("selectClass");
	var data = $(this).attr("idVal");
	$("#cityPortNo").val(data);
	//每次查询时将当前页设置为默认值
	$("#pageNo").val(1);
    $("#pageform").submit();
});

//每次查询时将当前页设置为默认值
$(".searchShipSailLine").click(function() {
	$("#pageNo").val(1);
    $("#pageform").submit();
});
