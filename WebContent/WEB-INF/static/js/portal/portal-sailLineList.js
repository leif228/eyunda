$(".startPortNo").live("click",function(){
	$(this).parent().siblings().removeClass("selectClass");
	$(this).parent().addClass("selectClass");
	var data = $(this).attr("idVal");
	$("#startPortNo").val(data);
});

$(".endPortNo").live("click",function(){
	$(this).parent().siblings().removeClass("selectClass");
	$(this).parent().addClass("selectClass");
	var data = $(this).attr("idVal");
	$("#endPortNo").val(data);
});

// 校验是否输入搜索信息
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

// 起运港改变
$("#startPortCity").change(function(){
	var startCode=$(this).val();
	$("#startPortCityCode").val(startCode);
	
	//如果状态改变为'请选择',则应该将之前内容清空
	if(startCode == ""){
		 $("#startPortName").html("");
		 $("#startPortNo").val("");
		 return;
	}
		
  $.ajax({
      method : "GET",
      data : {portCityCode : startCode},
      url : _rootPath+"/portal/home/getCityCodePorts",
      datatype : "json",
      success : function(data) {
        var returnCode = $(data)[0].returnCode;
        var message = $(data)[0].message;
        if (returnCode == "Failure") {
          alert(message);
          return false;
        } else {
            var s = "";
            //港口城市每改变一次都应将上次选中的港口清空
            $("#startPortNo").val("");
            
            s += "<dd class=\"fl fil-item\">";
            s += "	<div class=\"fil-name-wrap clearfix\">";
            $.each($(data)[0].portDatas, function(i, portData) {
        	s += " <span>";
        	s += "	 <a class=\"fil-name startPortNo\" idVal=\""+portData.portNo+"\" href=\"javascript:void(0);\">"+portData.portName+"</a>";
        	s += " </span>";
            });
            s += "		<div class=\"bk\"></div>";
            s += "	</div>";
            s += "	<div class=\"bk\"></div>";
            s += "</dd>";
              $("#startPortName").html("");
              $("#startPortName").html(s);
              
              return true;
        }
      }
    });

  });

//到达港改变
$("#endPortCity").change(function(){
	var endCode=$(this).val();
	$("#endPortCityCode").val(endCode);
	//如果状态改变为'请选择',则应该将之前内容清空
	if(endCode == ""){
		$("#endPortName").html("");
		$("#endPortNo").val("");
		return;
	}
		
  $.ajax({
      method : "GET",
      data : {portCityCode : endCode},
      url : _rootPath+"/portal/home/getCityCodePorts",
      datatype : "json",
      success : function(data) {
        var returnCode = $(data)[0].returnCode;
        var message = $(data)[0].message;
        if (returnCode == "Failure") {
          alert(message);
          return false;
        } else {
            var s = "";
          //港口城市每改变一次都应将上次选中的港口清空
            $("#endPortNo").val("");
            
            s += "<dd class=\"fl fil-item\">";
            s += "	<div class=\"fil-name-wrap clearfix\">";
            $.each($(data)[0].portDatas, function(i, portData) {
        	s += " 		<span>";
        	s += "			 <a class=\"fil-name endPortNo\" idVal=\""+portData.portNo+"\" href=\"javascript:void(0);\">"+portData.portName+"</a>";
        	s += " 		</span>";
            });
            s += "		<div class=\"bk\"></div>";
            s += "	</div>";
            s += "	<div class=\"bk\"></div>";
            s += "</dd>";
              $("#endPortName").html("");
              $("#endPortName").html(s);
              
              return true;
        }
      }
    });

  });

// 校验、搜索航线信息
$(".searchSailLine").click(function() {
	//每次点击查询按钮时,都应将类别参数和页码参数设为默认值
	$("#shipSortAjax").val("POINTER");
	$("#pageNoAjax").val(1);
  $("#searchSailLineForm").ajaxSubmit({
    method : "POST",
    url : _rootPath+"/portal/home/getSailLines",
    datatype : "json",
    success : function(data) {
      var redata = eval('('+data+')');
      var returnCode = redata.returnCode;
      var message = redata.message;
      if (returnCode == "Failure") {
        alert(message);
        return false;
      } else {
    	  var s = "";
    	  var n = 0;
    	  var pointer="POINTER";
    	  var order="ORDER";
    	  var time="TIME";
    	  var price="PRICE";
    	  var tons="TONS";
    	  
    	  s += "	<div class=\"box-header grd-white color-silver-dark corner-top\">";
    	  s += "		<span>船舶航次列表</span>";
    	  s += "	</div>";
    	  s += "	<div class=\"box-body\">";
    	  s += "		<div class=\"row-fluid\">";
    	  $.each(redata.pageData.result, function(i, shipData) {
		  s += "				<div class=\"span3 demo-knob\">";
		  n++;
		  var shipLogo = shipData.shipLogo;
	      var b = true;
	      if (null === shipLogo || shipLogo.length === 0)
	        	b = false;
	      if (b){
	    	  s += "					<a href=\""+_rootPath+"/mobile/ship/show?id="+shipData.id+"\" target=\"_blank\"> ";
	    	  s += "						<img style=\"width: 220px; height: 150px;\" src=\""+_rootPath+"/download/imageDownload?url="+shipLogo+"\" title=\"profile\" alt=\"profile\" />";
	    	  s += "					</a>";
	      }
    	  s += "					<div>";
    	  s += "						<span class=\"bold\">"+shipData.shipName+"</span>";
    	  s += "						<span style=\"float:right\">载货量:"+shipData.tons+"吨</span>";
    	  s += "					</div>";
    	  s += "					<div class=\"shipKeyWords\">";
    	  s += "						<span title=\""+shipData.keyWords+"\">"+shipData.keyWords+"</span>";
    	  s += "					</div>";
    	  var myShipPriceDatas = shipData.myShipPriceDatas;
	      var f = true;
	      if (myShipPriceDatas == undefined || null === myShipPriceDatas || myShipPriceDatas.length === 0)
	        	f = false;
	      if (f){
	    	  s += "					<div class=\"shipKeyWords\">";
	    	  s += "						<span title=\""+myShipPriceDatas[0].startFullName+" 到 "+myShipPriceDatas[0].endFullName+"\">";
	    	  s += "						"+myShipPriceDatas[0].startFullName+" 到 "+myShipPriceDatas[0].endFullName+"</span></div>";
	    	  s += "					<div>";
	    	  s += "						报价:<span class=\"color-red\">"+shipData.myShipPriceDatas[0].intTransFee+"元</span>";
	    	  s += "						<span style=\"float:right\">截止:"+shipData.myShipPriceDatas[0].periodTime+"</span>";
	    	  s += "					</div>";
	      }
    	  s += "		  </div>";
    	  if((n)%4 == 0){
		  s += "		</div>";
    	  s += "		<div class=\"divider-content\">";
    	  s += "			<span></span>";
    	  s += "		</div>";
    	  s += "		<div class=\"row-fluid\">";
    	  }
    	  });
    	  s += "		</div>";
    	  s += "	</div>";
    	  s += "	<div class=\"gallery-controls bottom\">";
    	  s += "		<div class=\"gallery-category\">";
    	  s += "			<div class=\"pull-right\">";
    	  s += "				<span>页号 "+redata.pageData.pageNo+" of "+redata.totalPages+"</span>";
    	  s += "			</div>";
    	  //航线排序
    	  s += "<div class=\"portfolio-filter\">";
    	  s += "<div class=\"parentDiv\">";
    	  s += "	<input type=\"hidden\" id=\"shipSortCode\" name=\"shipSortCode\"";
    	  s += "		value=\"${shipSortCode }\" />";
    	  s += "	<div class=\"floatcom pagebody\">";
    	  s += "		<span>排序：</span>";
    	  if(redata.shipSortCode == pointer){
    		  s += "				<span class=\"label color-teal shape\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+pointer+"');\" rel=\"popularity\">人气</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+order+"');\" rel=\"sales\">销量</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+time+"');\" rel=\" newly\">最新</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+price+"');\" rel=\"all\">报价</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+tons+"');\" rel=\"portofolio\">载货量</a>";
        	  s += "				</span>";
    	  }else if(redata.shipSortCode == order){
    		  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+pointer+"');\" rel=\"popularity\">人气</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label color-teal\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+order+"');\" rel=\"sales\">销量</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+time+"');\" rel=\" newly\">最新</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+price+"');\" rel=\"all\">报价</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+tons+"');\" rel=\"portofolio\">载货量</a>";
        	  s += "				</span>";
    	  }else if(redata.shipSortCode == time){
    		  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+pointer+"');\" rel=\"popularity\">人气</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+order+"');\" rel=\"sales\">销量</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label color-teal\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+time+"');\" rel=\" newly\">最新</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+price+"');\" rel=\"all\">报价</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+tons+"');\" rel=\"portofolio\">载货量</a>";
        	  s += "				</span>";
    	  }else if(redata.shipSortCode == price){
    		  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+pointer+"');\" rel=\"popularity\">人气</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax("+order+");\" rel=\"sales\">销量</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+time+"');\" rel=\" newly\">最新</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label color-teal\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+price+"');\" rel=\"all\">报价</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+tons+"');\" rel=\"portofolio\">载货量</a>";
        	  s += "				</span>";
    	  }else{
    		  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+pointer+"');\" rel=\"popularity\">人气</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+order+"');\" rel=\"sales\">销量</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+time+"');\" rel=\" newly\">最新</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+price+"');\" rel=\"all\">报价</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label color-teal\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+tons+"');\" rel=\"portofolio\">载货量</a>";
        	  s += "				</span>";
    	  }
    	  s += "	</div>";
    	  s += "</div>";
    	  s += "</div>";
    	  s += "		</div>";
    	  s += "		<div>";
    	  //航线分页
    	  s += "<div class=\"parentDiv\">";
    	  s += "  <div class=\"floatcom pagebody\">";
    	  var startIndex=2;
    	  var begin=redata.pageData.pageNo-startIndex;
    	  var end=redata.pageData.pageNo+startIndex;
    	  if(begin<1){
    		  begin=1;
    		  end=begin+4;
    	  }
    	  if(end >= redata.totalPages){
    		  end=redata.totalPages;
    		  begin=end-4;
    		  if(begin<1)
    			  begin=1;
    	  }
    	  s += "    <ul class=\"pager helper-font-small\">";
    	  s += "      <li>共"+redata.pageData.totalCount+"条纪录 页次:"+redata.pageData.pageNo+"/"+redata.totalPages+"页</li>";
    	  if(redata.pageData.pageNo == 1){
    		  s += "          <li class=\"disabled\"><a href=\"#\">首页</a></li>";
    	  }else{
    		  s += "          <li><a onclick=\"javascript:jumpPageAjax("+0+");\">首页</a></li>";
    	  }
    	 if(redata.pageData.pageNo == 1){
    		 s += "          <li class=\"disabled\"><a href=\"#\">上一页</a></li>";
    	 }else{
    		 s += "          <li><a onclick=\"javascript:jumpPageAjax("+i+");\">上一页</a></li>";
    	 }
    	 for(var i=begin;i<=end;i++){
    		 if(i == redata.pageData.pageNo){
    			 s += "            <li class=\"disabled\">"+i+"</li>";
    		 }else{
    			 s += "            <li><a onclick=\"javascript:jumpPageAjax("+i+");\">"+i+"</a></li>";
    		 }
    	 }
    	 if(end < redata.totalPages)
    	  s += "        <li>...</li>";
    	 if(redata.pageData.pageNo == redata.totalPages){
    		 s += "          <li class=\"disabled\"><a href=\"#\">下一页</a></li>";
    	 }else{
    		 s += "          <li><a onclick=\"javascript:jumpPageAjax("+(redata.pageData.pageNo+1)+");\">下一页</a></li>";
    	 }
    	 if(redata.pageData.pageNo == redata.totalPages){
   		  s += "          <li class=\"disabled\"><a href=\"#\">尾页</a></li>";
	   	  }else{
	   	  s += "	          <li><a onclick=\"javascript:jumpPageAjax("+redata.totalPages+");\">尾页</a></li>";
	   	  }
    	  s += "      <li>转到：";
    	  s += "        <input type=\"text\" style=\"width:3em;margin-top:8px;\" id=\"goPageNo\" value=\"\" />页 ";
    	  s += "        <input type=\"button\" style=\"\" value=\"Go\" onclick=\"javascript:jumpPageAjax($('#goPageNo').val());\" />";
    	  s += "      </li>";
    	  s += "    </ul>";
    	  s += "  </div>";
    	  s += "</div>";
    	  s += "		</div> ";
    	  s += "	</div>";
    	  
    	  $("#sailLineList").html("");
          $("#sailLineList").html(s);
            return true;
      }
    }
  });
});

//通过Ajax实现航线类别排序和分页
function jumpPageAjax(currPageNo) {
	  var pointer="POINTER";
	  var order="ORDER";
	  var time="TIME";
	  var price="PRICE";
	  var tons="TONS";
	  
	//判断传入参数为航线类别或分页参数
	if(currPageNo == pointer || currPageNo == order ||currPageNo == time || currPageNo == price || currPageNo == tons){
		$("#shipSortAjax").val(currPageNo);
	}else{
		$("#pageNoAjax").val(currPageNo);
	}
	
  $("#searchSailLineForm").ajaxSubmit({
	method : "POST",
	url : _rootPath+"/portal/home/getSailLines",
	datatype : "json",
	success : function(data) {
	  var redata = eval('('+data+')');
	  var returnCode = redata.returnCode;
	  var message = redata.message;
	  if (returnCode == "Failure") {
	    alert(message);
	    return false;
	  } else {
    	  var s = "";
    	  var n = 0;
    	  var pointer="POINTER";
    	  var order="ORDER";
    	  var time="TIME";
    	  var price="PRICE";
    	  var tons="TONS";
    	  
    	  s += "	<div class=\"box-header grd-white color-silver-dark corner-top\">";
    	  s += "		<span>船舶航次列表</span>";
    	  s += "	</div>";
    	  s += "	<div class=\"box-body\">";
    	  s += "		<div class=\"row-fluid\">";
    	  $.each(redata.pageData.result, function(i, shipData) {
		  s += "				<div class=\"span3 demo-knob\">";
		  n++;
		  var shipLogo = shipData.shipLogo;
	      var b = true;
	      if (null === shipLogo || shipLogo.length === 0)
	        	b = false;
	      if (b){
	    	  s += "					<a href=\""+_rootPath+"/mobile/ship/show?id="+shipData.id+"\" target=\"_blank\"> ";
	    	  s += "						<img style=\"width: 220px; height: 150px;\" src=\""+_rootPath+"/download/imageDownload?url="+shipLogo+"\" title=\"profile\" alt=\"profile\" />";
	    	  s += "					</a>";
	      }
    	  s += "					<div>";
    	  s += "						<span class=\"bold\">"+shipData.shipName+"</span>";
    	  s += "						<span style=\"float:right\">载货量:"+shipData.tons+"吨</span>";
    	  s += "					</div>";
    	  s += "					<div class=\"shipKeyWords\">";
    	  s += "						<span title=\""+shipData.keyWords+"\">"+shipData.keyWords+"</span>";
    	  s += "					</div>";
    	  var myShipPriceDatas = shipData.myShipPriceDatas;
	      var f = true;
	      if (myShipPriceDatas == undefined || null === myShipPriceDatas || myShipPriceDatas.length === 0)
	        	f = false;
	      if (f){
	    	  s += "					<div class=\"shipKeyWords\">";
	    	  s += "						<span title=\""+myShipPriceDatas[0].startFullName+" 到 "+myShipPriceDatas[0].endFullName+"\">";
	    	  s += "						"+myShipPriceDatas[0].startFullName+" 到 "+myShipPriceDatas[0].endFullName+"</span></div>";
	    	  s += "					<div>";
	    	  s += "						报价:<span class=\"color-red\">"+shipData.myShipPriceDatas[0].intTransFee+"元</span>";
	    	  s += "						<span style=\"float:right\">截止:"+shipData.myShipPriceDatas[0].periodTime+"</span>";
	    	  s += "					</div>";
	      }
    	  s += "		  </div>";
    	  if((n)%4 == 0){
		  s += "		</div>";
    	  s += "		<div class=\"divider-content\">";
    	  s += "			<span></span>";
    	  s += "		</div>";
    	  s += "		<div class=\"row-fluid\">";
    	  }
    	  });
    	  s += "		</div>";
    	  s += "	</div>";
    	  s += "	<div class=\"gallery-controls bottom\">";
    	  s += "		<div class=\"gallery-category\">";
    	  s += "			<div class=\"pull-right\">";
    	  s += "				<span>页号 "+redata.pageData.pageNo+" of "+redata.totalPages+"</span>";
    	  s += "			</div>";
    	  //航线排序
    	  s += "<div class=\"portfolio-filter\">";
    	  s += "<div class=\"parentDiv\">";
    	  s += "	<input type=\"hidden\" id=\"shipSortCode\" name=\"shipSortCode\"";
    	  s += "		value=\"${shipSortCode }\" />";
    	  s += "	<div class=\"floatcom pagebody\">";
    	  s += "		<span>排序：</span>";
    	  if(redata.shipSortCode == pointer){
    		  s += "				<span class=\"label color-teal shape\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+pointer+"');\" rel=\"popularity\">人气</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+order+"');\" rel=\"sales\">销量</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+time+"');\" rel=\" newly\">最新</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+price+"');\" rel=\"all\">报价</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+tons+"');\" rel=\"portofolio\">载货量</a>";
        	  s += "				</span>";
    	  }else if(redata.shipSortCode == order){
    		  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+pointer+"');\" rel=\"popularity\">人气</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label color-teal\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+order+"');\" rel=\"sales\">销量</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+time+"');\" rel=\" newly\">最新</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+price+"');\" rel=\"all\">报价</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+tons+"');\" rel=\"portofolio\">载货量</a>";
        	  s += "				</span>";
    	  }else if(redata.shipSortCode == time){
    		  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+pointer+"');\" rel=\"popularity\">人气</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+order+"');\" rel=\"sales\">销量</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label color-teal\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+time+"');\" rel=\" newly\">最新</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+price+"');\" rel=\"all\">报价</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+tons+"');\" rel=\"portofolio\">载货量</a>";
        	  s += "				</span>";
    	  }else if(redata.shipSortCode == price){
    		  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+pointer+"');\" rel=\"popularity\">人气</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax("+order+");\" rel=\"sales\">销量</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+time+"');\" rel=\" newly\">最新</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label color-teal\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+price+"');\" rel=\"all\">报价</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+tons+"');\" rel=\"portofolio\">载货量</a>";
        	  s += "				</span>";
    	  }else{
    		  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+pointer+"');\" rel=\"popularity\">人气</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+order+"');\" rel=\"sales\">销量</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+time+"');\" rel=\" newly\">最新</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+price+"');\" rel=\"all\">报价</a>";
        	  s += "				</span>";
        	  s += "				<span class=\"label color-teal\"> <a class=\"filterable\"";
        	  s += "					href=\"javascript:jumpPageAjax('"+tons+"');\" rel=\"portofolio\">载货量</a>";
        	  s += "				</span>";
    	  }
    	  s += "	</div>";
    	  s += "</div>";
    	  s += "</div>";
    	  s += "		</div>";
    	  s += "		<div>";
    	  //航线分页
    	  s += "<div class=\"parentDiv\">";
    	  s += "  <div class=\"floatcom pagebody\">";
    	  var startIndex=2;
    	  var begin=redata.pageData.pageNo-startIndex;
    	  var end=redata.pageData.pageNo+startIndex;
    	  if(begin<1){
    		  begin=1;
    		  end=begin+4;
    	  }
    	  if(end >= redata.totalPages){
    		  end=redata.totalPages;
    		  begin=end-4;
    		  if(begin<1)
    			  begin=1;
    	  }
    	  s += "    <ul class=\"pager helper-font-small\">";
    	  s += "      <li>共"+redata.pageData.totalCount+"条纪录 页次:"+redata.pageData.pageNo+"/"+redata.totalPages+"页</li>";
    	  if(redata.pageData.pageNo == 1){
    		  s += "          <li class=\"disabled\"><a href=\"#\">首页</a></li>";
    	  }else{
    		  s += "          <li><a onclick=\"javascript:jumpPageAjax("+0+");\">首页</a></li>";
    	  }
    	 if(redata.pageData.pageNo == 1){
    		 s += "          <li class=\"disabled\"><a href=\"#\">上一页</a></li>";
    	 }else{
    		 s += "          <li><a onclick=\"javascript:jumpPageAjax("+i+");\">上一页</a></li>";
    	 }
    	 for(var i=begin;i<=end;i++){
    		 if(i == redata.pageData.pageNo){
    			 s += "            <li class=\"disabled\">"+i+"</li>";
    		 }else{
    			 s += "            <li><a onclick=\"javascript:jumpPageAjax("+i+");\">"+i+"</a></li>";
    		 }
    	 }
    	 if(end < redata.totalPages)
    	  s += "        <li>...</li>";
    	 if(redata.pageData.pageNo == redata.totalPages){
    		 s += "          <li class=\"disabled\"><a href=\"#\">下一页</a></li>";
    	 }else{
    		 s += "          <li><a onclick=\"javascript:jumpPageAjax("+(redata.pageData.pageNo+1)+");\">下一页</a></li>";
    	 }
    	  
    	 if(redata.pageData.pageNo == redata.totalPages){
   		  s += "          <li class=\"disabled\"><a href=\"#\">尾页</a></li>";
	   	  }else{
	   	  s += "	          <li><a onclick=\"javascript:jumpPageAjax("+redata.totalPages+");\">尾页</a></li>";
	   	  }
    	  s += "      <li>转到：";
    	  s += "        <input type=\"text\" style=\"width:3em;margin-top:8px;\" id=\"goPageNo\" value=\"\" />页 ";
    	  s += "        <input type=\"button\" style=\"\" value=\"Go\" onclick=\"javascript:jumpPageAjax($('#goPageNo').val());\" />";
    	  s += "      </li>";
    	  s += "    </ul>";
    	  s += "  </div>";
    	  s += "</div>";
    	  s += "		</div> ";
    	  s += "	</div>";
    	  
    	  $("#sailLineList").html("");
          $("#sailLineList").html(s);
            return true;
	  }
	    }
	  });
}
