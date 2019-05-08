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

//已到达
$(".hasArrived").click(function() {
	$("#pageNo").val(1);
	$("#arvlftStatus").val("arrive");
    $("#pageform").submit();
});

//将要到达
$(".willArrive").click(function() {
	$("#pageNo").val(1);
	$("#arvlftStatus").val("left1");
    $("#pageform").submit();
});

//刚离开
$(".justLeave").click(function() {
	$("#pageNo").val(1);
	$("#arvlftStatus").val("left2");
    $("#pageform").submit();
});

function changeOneTitle(spanId, shipDynamicData) {
	  var shipArvlftDes = "", shipApplyDes = "";
	  if(spanId.substr(0, 6) == "arvlft"){
		  if (shipDynamicData.arvlft == "arrive") {
				shipArvlftDes += "已到港\n"+shipDynamicData.shipArvlftData[0].arvlftTime + "  到  "
						+ shipDynamicData.shipArvlftData[0].portNoFullName + "\n卸  ";
				for (var i = 0; i < shipDynamicData.shipUpdownDatas.length; i++){
					var shipUpdownData = shipDynamicData.shipUpdownDatas[i];
					if (shipUpdownData.cargoBigType == "container")
						shipArvlftDes += (shipUpdownData == shipDynamicData.shipUpdownDatas[shipDynamicData.shipUpdownDatas.length - 1]) == true ? shipUpdownData
								.cargoBigTypeDescrip
								+ "."
								+ shipUpdownData.cargoName
								+ "  "
								+ shipUpdownData.tonTeuDes
								: shipUpdownData.cargoBigTypeDescrip + "."
										+ shipUpdownData.cargoName + "  "
										+ shipUpdownData.tonTeuDes + "\n     ";
					else
						shipArvlftDes += (shipUpdownData == shipDynamicData.shipUpdownDatas[shipDynamicData.shipUpdownDatas.length - 1]) == true ? shipUpdownData
								.cargoTypeDescrip
								+ "."
								+ shipUpdownData.cargoName
								+ "  "
								+ shipUpdownData.tonTeuDes
								: shipUpdownData.cargoTypeDescrip + "."
										+ shipUpdownData.cargoName + "  "
										+ shipUpdownData.tonTeuDes + "\n     ";
				}
			} else if (shipDynamicData.arvlft == "left1") {
				shipArvlftDes += "将到港\n"+shipDynamicData.shipArvlftData[0].arvlftTime + "  离  "+shipDynamicData.shipArvlftData[0].portNoFullName+"  到  "
						+ shipDynamicData.shipArvlftData[0].goPortNoFullName + "\n装  ";
				for (var i = 0; i < shipDynamicData.shipUpdownDatas.length; i++){
					var shipUpdownData = shipDynamicData.shipUpdownDatas[i];
					if (shipUpdownData.cargoBigType == "container")
						shipArvlftDes += (shipUpdownData == shipDynamicData.shipUpdownDatas[shipDynamicData.shipUpdownDatas.length - 1]) == true ? shipUpdownData
								.cargoBigTypeDescrip
								+ "."
								+ shipUpdownData.cargoName
								+ "  "
								+ shipUpdownData.tonTeuDes
								: shipUpdownData.cargoBigTypeDescrip + "."
										+ shipUpdownData.cargoName + "  "
										+ shipUpdownData.tonTeuDes + "\n     ";
					else
						shipArvlftDes += (shipUpdownData == shipDynamicData.shipUpdownDatas[shipDynamicData.shipUpdownDatas.length - 1]) == true ? shipUpdownData
								.cargoTypeDescrip
								+ "."
								+ shipUpdownData.cargoName
								+ "  "
								+ shipUpdownData.tonTeuDes
								: shipUpdownData.cargoTypeDescrip + "."
										+ shipUpdownData.cargoName + "  "
										+ shipUpdownData.tonTeuDes + "\n     ";
				}
			} else if (shipDynamicData.arvlft == "left2") {
				shipArvlftDes += "刚离港\n"+shipDynamicData.shipArvlftData[0].arvlftTime + "  离  "+shipDynamicData.shipArvlftData[0].portNoFullName+"  到  "
						+ shipDynamicData.shipArvlftData[0].goPortNoFullName + "\n装  ";
				for (var i = 0; i < shipDynamicData.shipUpdownDatas.length; i++){
					var shipUpdownData = shipDynamicData.shipUpdownDatas[i];
					if (shipUpdownData.cargoBigType == "container")
						shipArvlftDes += (shipUpdownData == shipDynamicData.shipUpdownDatas[shipDynamicData.shipUpdownDatas.length - 1]) == true ? shipUpdownData
								.cargoBigTypeDescrip
								+ "."
								+ shipUpdownData.cargoName
								+ "  "
								+ shipUpdownData.tonTeuDes
								: shipUpdownData.cargoBigTypeDescrip + "."
										+ shipUpdownData.cargoName + "  "
										+ shipUpdownData.tonTeuDes + "\n     ";
					else
						shipArvlftDes += (shipUpdownData == shipDynamicData.shipUpdownDatas[shipDynamicData.shipUpdownDatas.length - 1]) == true ? shipUpdownData
								.cargoTypeDescrip
								+ "."
								+ shipUpdownData.cargoName
								+ "  "
								+ shipUpdownData.tonTeuDes
								: shipUpdownData.cargoTypeDescrip + "."
										+ shipUpdownData.cargoName + "  "
										+ shipUpdownData.tonTeuDes + "\n     ";
				}
			}
		  
		  	$("#"+spanId).attr("title", shipArvlftDes);
	  }else{
		  for (var i = 0; i < shipDynamicData.shipApplyDatas.length; i++){
			var shipApplyData = shipDynamicData.shipApplyDatas[i];
			if (shipApplyData.cargoBigType == "container")
				shipApplyDes += (shipApplyData == shipDynamicData.shipApplyDatas[shipDynamicData.shipApplyDatas.length - 1]) == true ? shipApplyData
						.startPortFullName
						+ "  "
						+ shipApplyData
						.endPortFullName+",参考货物:  "+shipApplyData.cargoBigTypeDescrip+"."+shipApplyData.cargoName+",载箱量:"+shipApplyData.tonTeuDes+",单价:"+shipApplyData.priceDes+",总价:"+shipApplyData.intTransFeeDes+",截止:"+shipApplyData.periodTime+"。"
						: shipApplyData
						.startPortFullName
						+ "  到  "
						+ shipApplyData
						.endPortFullName+",参考货物:  "+shipApplyData.cargoBigTypeDescrip+"."+shipApplyData.cargoName+",载箱量:"+shipApplyData.tonTeuDes+",单价:"+shipApplyData.priceDes+",总价:"+shipApplyData.intTransFeeDes+",截止:"+shipApplyData.periodTime+"。\n";
				else
					shipApplyDes += (shipApplyData == shipDynamicData.shipApplyDatas[shipDynamicData.shipApplyDatas.length - 1]) == true ? shipApplyData
							.startPortFullName
							+ "  到  "
							+ shipApplyData
							.endPortFullName+",参考货物:  "+shipApplyData.cargoTypeDescrip+"."+shipApplyData.cargoName+",载箱量:"+shipApplyData.tonTeuDes+",单价:"+shipApplyData.priceDes+",总价:"+shipApplyData.intTransFeeDes+",截止:"+shipApplyData.periodTime+"。"
							: shipApplyData
							.startPortFullName
							+ "  到  "
							+ shipApplyData
							.endPortFullName+",参考货物:  "+shipApplyData.cargoTypeDescrip+"."+shipApplyData.cargoName+",载箱量:"+shipApplyData.tonTeuDes+",单价:"+shipApplyData.priceDes+",总价:"+shipApplyData.intTransFeeDes+",截止:"+shipApplyData.periodTime+"。\n";
		  }
		  $("#"+spanId).attr("title", shipApplyDes);
	  }
}

function changeTitles(shipDynamicDatas) {
	  for (var i = 0; i < shipDynamicDatas.length; i++){
		  var shipDynamicData = shipDynamicDatas[i];
	  	  changeOneTitle("arvlft"+shipDynamicData.shipArvlftData[0].shipArvlftId, shipDynamicData)
	  	  changeOneTitle("apply"+shipDynamicData.shipArvlftData[0].shipArvlftId, shipDynamicData)
	  }
}
