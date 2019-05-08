// 收藏船舶，暂时是任何角色均可收藏，马上改为任何角色均可分享代理人的船舶监控权限
$(".btnFavorite").live("click", function() {
	/*if($(this).attr("flag") == "true"){
		alert("子账号不能收藏船舶！");
		return false;
	}*/
	var shipId = $(this).attr("data-src");
	$.ajax({
		method : "GET",
		data : {shipId : shipId},
		url : _rootPath+"/space/ship/favorite",
		datatype : "json",
		success : function(data) {
			var returnCode = $(data)[0].returnCode;
			var message = $(data)[0].message;
			if (returnCode == "Failure") {
				alert(message);
				return false;
			} else {
				alert(message);
				return true;
			}
		}
	});
});

$(".isICarry").click(function() {
	/*if($(this).attr("flag") == "true"){
		alert("子账号不能进行该操作！");
		return false;
	}*/
	window.location.href = _rootPath + "/space/cargo/myCargo/addMyCargo?id=0&shipId=" + $(this).attr("data-src");
});

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
	    			  // 搜船舶
	    			  window.location.href="searchShip?keyWords="+$(data)[0].keyWords+"&searchRlsCode="+$(data)[0].searchRlsCode+"";
	    		  }else{
	    			 // 搜货物
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
$(document).ready(function(){
	$("#inline").colorbox({inline:true, width:"100%",height:"100%",fixed:true,reposition:true});
	$("#closeDom").live("click",function(){
		//alert("关闭全屏");
		$.colorbox.close();
		$(this).remove();
		$("#searchform").show();
	});
});
$(document).bind('cbox_complete', function(){
	var closeDom = $('<div style="float:left;margin-left:20px" id="closeDom"><div title="全屏" style="box-shadow: rgba(0, 0, 0, 0.34902) 2px 2px 3px; border-left-width: 1px; border-left-style: solid; border-left-color: rgb(139, 164, 220); border-top-width: 1px; border-top-style: solid; border-top-color: rgb(139, 164, 220); border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(139, 164, 220); padding: 2px 6px; font-style: normal; font-variant: normal; font-weight: bold; font-stretch: normal; font-size: 12px; line-height: 1.3em; font-family: arial, sans-serif; text-align: center; white-space: nowrap; border-radius: 3px 0px 0px 3px; color: rgb(255, 255, 255); background: rgb(142, 168, 224);">关闭全屏</div></div>');
	$(".BMap_noprint.anchorTR").append(closeDom); 
	$("#searchform").hide();
});
