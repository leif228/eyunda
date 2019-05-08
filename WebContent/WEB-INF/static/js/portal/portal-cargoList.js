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

// 校验、搜索货物信息
$(".searchCargos").click(function() {
   //每次点击查询按钮时,都应将页码参数设为默认值
  $("#pageNoAjax").val(1);
	
  $("#searchCargoForm").ajaxSubmit({
    method : "POST",
    url : _rootPath+"/portal/home/getRelsCargos",
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
    	 
    	  s += "	<div class=\"box-header grd-white color-silver-dark corner-top\">";
    	  s += "		<span>货源按地区列表</span>";
    	  s += "	</div>";
    	  s += "	<div class=\"box-body\">";
    	  
    	  s += "	<table class=\"table table-bordered data-table table-striped\" style=\"word-break:break-all;\">";
    	  s += "	<thead>";
    	  s += "		<tr>";
    	  s += "			<th>起运港</th>";
    	  s += "			<th>到达港</th>";
    	  s += "			<th>货物图片</th>";
    	  s += "			<th>货号</th>";
    	  s += "			<th>货类</th>";
    	  s += "			<th>货名</th>";
    	  s += "			<th>货量</th>";
    	  s += "			<th>价格</th>";
    	  s += "			<th>总价</th>";
    	  s += "			<th>截止日期</th>";
    	  s += "            <th>联系电话</th>";
    	  s += "            <th>在线咨询</th>";
    	  s += "		</tr>";
    	  s += "	</thead>";
    	  s += "	<tbody>";
    	  $.each(redata.pageData.result, function(i, cargoSailLineData) {
		  s += "			<tr>";
		  s += "				<td>"+cargoSailLineData.cargoData.startPortData.fullName+"</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.endPortData.fullName+"</td>";
		  s += "				<td>";
		  s += "					<a href=\""+_rootPath+"/portal/home/cargoDetail?id="+cargoSailLineData.cargoData.id+"\" target=\"_blank\">";
		  s += "						<img src=\""+_rootPath+"/download/imageDownload?url="+cargoSailLineData.cargoData.cargoImage+"\"";
		  s += "             			 style=\"width: 80px; height: 60px;\" alt=\"\" class=\"thumbnail\" />";
		  s += "					</a>";
		  s += "				</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.id+"</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.cargoTypeName+"</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.cargoName+"</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.tonTeuDes+"</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.priceDes+"</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.transFeeDes+"</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.periodTime+"</td>";
		  s += "              	<td>"+cargoSailLineData.cargoData.consigner.mobile+"</td>";
		  s += "                <td>";
		  s += "                    <a href=\""+_rootPath+"/space/chat/show?toUserId="+cargoSailLineData.cargoData.consigner.id+"\" target=\"mychat\">";
		  if(cargoSailLineData.cargoData.consigner.userLogo != ""){
		  s += "						<img src=\""+_rootPath+"/download/imageDownload?url="+cargoSailLineData.cargoData.consigner.userLogo+"\"";
		  s += "             			 style=\"width: 32px; height: 32px;\" alt=\"\" class=\"contact-item-object\" />";
		  	  s += "				<br/>托运人<br/>";
			  if(cargoSailLineData.cargoData.consigner.trueName != "")
			  s += "				"+cargoSailLineData.cargoData.consigner.trueName+"";
			  else
			  s += "				"+cargoSailLineData.cargoData.consigner.nickName+"";
		  }else{
		  s += "						<img src=\""+_rootPath+"/img/user.jpg";
		  s += "             			 style=\"width: 32px; height: 32px;\" alt=\"\" class=\"contact-item-object\" />";
			  s += "				<br/>托运人<br/>";
			  if(cargoSailLineData.cargoData.consigner.trueName != "")
			  s += "				"+cargoSailLineData.cargoData.consigner.trueName+"";
			  else
			  s += "				"+cargoSailLineData.cargoData.consigner.nickName+"";
		  }
		  s += "                    </a>";
		  s += "                </td>";
		  s += "			</tr>";
    	  });
    	  s += "	</tbody>";
    	  s += "	</table>";
    	  
    	  s += "	</div>";	
    	  s += "	<div class=\"gallery-controls bottom\">";
    	  s += "		<div class=\"gallery-category\">";
    	  s += "			<div class=\"pull-right\">";
    	  s += "				<span>页号 "+redata.pageData.pageNo+" of "+redata.totalPages+"</span>";
    	  s += "			</div>";
    	  s += "		</div>";
    	  s += "		<div>";
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
    		  s += "          <li><a style=\"cursor:pointer;\" onclick=\"javascript:jumpPageAjax("+0+");\">首页</a></li>";
    	  }
    	 if(redata.pageData.pageNo == 1){
    		 s += "          <li class=\"disabled\"><a href=\"#\">上一页</a></li>";
    	 }else{
    		 s += "          <li><a style=\"cursor:pointer;\" onclick=\"javascript:jumpPageAjax("+i+");\">上一页</a></li>";
    	 }
    	 for(var i=begin;i<=end;i++){
    		 if(i == redata.pageData.pageNo){
    			 s += "            <li class=\"disabled\">"+i+"</li>";
    		 }else{
    			 s += "            <li><a style=\"cursor:pointer;\" onclick=\"javascript:jumpPageAjax("+i+");\">"+i+"</a></li>";
    		 }
    	 }
    	 if(end < redata.totalPages)
    		 	s += "        <li>...</li>";
    	 if(redata.pageData.pageNo == redata.totalPages){
    		 	s += "          <li class=\"disabled\"><a href=\"#\">下一页</a></li>";
    	 }else{
    		 	s += "          <li><a style=\"cursor:pointer;\" onclick=\"javascript:jumpPageAjax("+(redata.pageData.pageNo+1)+");\">下一页</a></li>";
    	 }
    	 if(redata.pageData.pageNo == redata.totalPages){
   		  		s += "          <li class=\"disabled\"><a href=\"#\">尾页</a></li>";
	   	  }else{
	   		  	s += "	          <li><a style=\"cursor:pointer;\" onclick=\"javascript:jumpPageAjax("+redata.totalPages+");\">尾页</a></li>";
	   	  }
    	  s += "      <li>转到：";
    	  s += "        <input type=\"text\" style=\"width:3em;margin-top:8px;\" id=\"goPageNo\" value=\"\" />页 ";
    	  s += "        <input type=\"button\" style=\"cursor:pointer;\" id=\"searchGoPage\" value=\"Go\" onclick=\"javascript:jumpPageAjax($('#goPageNo').val());\" />";
    	  s += "      </li>";
    	  s += "    </ul>";
    	  s += "  </div>";
    	  s += "</div>";
    	  s += "		</div> ";
    	  s += "	</div>";
    	  
    	  $("#cargoList").html("");
          $("#cargoList").html(s);
            return true;
      }
    }
  });
});

//通过Ajax实现分页
function jumpPageAjax(currPageNo) {
	  $("#pageNoAjax").val(currPageNo);
	  $("#searchCargoForm").ajaxSubmit({
	method : "POST",
	url : _rootPath+"/portal/home/getRelsCargos",
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
		 
		  s += "	<div class=\"box-header grd-white color-silver-dark corner-top\">";
		  s += "		<span>货源按地区列表</span>";
		  s += "	</div>";
		  s += "	<div class=\"box-body\">";
		  
		  s += "	<table class=\"table table-bordered data-table table-striped\" style=\"word-break:break-all;\">";
    	  s += "	<thead>";
    	  s += "		<tr>";
    	  s += "			<th>起运港</th>";
    	  s += "			<th>到达港</th>";
    	  s += "			<th>货物图片</th>";
    	  s += "			<th>货号</th>";
    	  s += "			<th>货类</th>";
    	  s += "			<th>货名</th>";
    	  s += "			<th>货量</th>";
    	  s += "			<th>价格</th>";
    	  s += "			<th>总价</th>";
    	  s += "			<th>截止日期</th>";
    	  s += "            <th>联系电话</th>";
    	  s += "            <th>在线咨询</th>";
    	  s += "		</tr>";
    	  s += "	</thead>";
    	  s += "	<tbody>";
    	  $.each(redata.pageData.result, function(i, cargoSailLineData) {
		  s += "			<tr>";
		  s += "				<td>"+cargoSailLineData.cargoData.startPortData.fullName+"</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.endPortData.fullName+"</td>";
		  s += "				<td>";
		  s += "					<a href=\""+_rootPath+"/portal/home/cargoDetail?id="+cargoSailLineData.cargoData.id+"\" target=\"_blank\">";
		  s += "						<img src=\""+_rootPath+"/download/imageDownload?url="+cargoSailLineData.cargoData.cargoImage+"\"";
		  s += "             			 style=\"width: 80px; height: 60px;\" alt=\"\" class=\"thumbnail\" />";
		  s += "					</a>";
		  s += "				</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.id+"</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.cargoTypeName+"</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.cargoName+"</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.tonTeuDes+"</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.priceDes+"</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.transFeeDes+"</td>";
		  s += "				<td>"+cargoSailLineData.cargoData.periodTime+"</td>";
		  s += "              	<td>"+cargoSailLineData.cargoData.consigner.mobile+"</td>";
		  s += "                <td>";
		  s += "                    <a href=\""+_rootPath+"/space/chat/show?toUserId="+cargoSailLineData.cargoData.consigner.id+"\" target=\"mychat\">";
		  if(cargoSailLineData.cargoData.consigner.userLogo != ""){
		  s += "						<img src=\""+_rootPath+"/download/imageDownload?url="+cargoSailLineData.cargoData.consigner.userLogo+"\"";
		  s += "             			 style=\"width: 32px; height: 32px;\" alt=\"\" class=\"contact-item-object\" />";
			  s += "				<br/>托运人<br/>";
			  if(cargoSailLineData.cargoData.consigner.trueName != "")
			  s += "				"+cargoSailLineData.cargoData.consigner.trueName+"";
			  else
			  s += "				"+cargoSailLineData.cargoData.consigner.nickName+"";
		  }else{
		  s += "						<img src=\""+_rootPath+"/img/user.jpg";
		  s += "             			 style=\"width: 32px; height: 32px;\" alt=\"\" class=\"contact-item-object\" />";
			  s += "				<br/>托运人<br/>";
			  if(cargoSailLineData.cargoData.consigner.trueName != "")
			  s += "				"+cargoSailLineData.cargoData.consigner.trueName+"";
			  else
			  s += "				"+cargoSailLineData.cargoData.consigner.nickName+"";
		  }
		  s += "                    </a>";
		  s += "                </td>";
		  s += "			</tr>";
    	  });
    	  s += "	</tbody>";
    	  s += "	</table>";
    	  
		  s += "	</div>";
		  s += "	<div class=\"gallery-controls bottom\">";
		  s += "		<div class=\"gallery-category\">";
		  s += "			<div class=\"pull-right\">";
		  s += "				<span>页号 "+redata.pageData.pageNo+" of "+redata.totalPages+"</span>";
		  s += "			</div>";
		  s += "		</div>";
		  s += "		<div>";
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
			  s += "          <li><a style=\"cursor:pointer;\" onclick=\"javascript:jumpPageAjax("+0+");\">首页</a></li>";
		  }
		 if(redata.pageData.pageNo == 1){
			 s += "          <li class=\"disabled\"><a href=\"#\">上一页</a></li>";
		 }else{
			 s += "          <li><a style=\"cursor:pointer;\" onclick=\"javascript:jumpPageAjax("+i+");\">上一页</a></li>";
		 }
		 for(var i=begin;i<=end;i++){
			 if(i == redata.pageData.pageNo){
				 s += "            <li class=\"disabled\">"+i+"</li>";
			 }else{
				 s += "            <li><a style=\"cursor:pointer;\" onclick=\"javascript:jumpPageAjax("+i+");\">"+i+"</a></li>";
			 }
		 }
		 if(end < redata.totalPages)
		  s += "        <li>...</li>";
		 if(redata.pageData.pageNo == redata.totalPages){
			 s += "          <li class=\"disabled\"><a href=\"#\">下一页</a></li>";
		 }else{
			 s += "          <li><a style=\"cursor:pointer;\" onclick=\"javascript:jumpPageAjax("+(redata.pageData.pageNo+1)+");\">下一页</a></li>";
		 }
		 if(redata.pageData.pageNo == redata.totalPages){
		  s += "          <li class=\"disabled\"><a href=\"#\">尾页</a></li>";
	   	  }else{
	   	  s += "	          <li><a style=\"cursor:pointer;\" onclick=\"javascript:jumpPageAjax("+redata.totalPages+");\">尾页</a></li>";
	   	  }
		  s += "      <li>转到：";
		  s += "        <input type=\"text\" style=\"width:3em;margin-top:8px;\" id=\"goPageNo\" value=\"\" />页 ";
		  s += "        <input type=\"button\" style=\"cursor:pointer;\" id=\"searchGoPage\" value=\"Go\" onclick=\"javascript:jumpPageAjax($('#goPageNo').val());\" />";
		  s += "      </li>";
		  s += "    </ul>";
		  s += "  </div>";
		  s += "</div>";
		  s += "		</div> ";
		  s += "	</div>";
		  
		  $("#cargoList").html("");
	      $("#cargoList").html(s);
	            return true;
	      }
	    }
	  });
}

$("#goPageNo").live("keydown",function(e) {
	if (e.keyCode == 13){
		$("#searchGoPage").click();
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
	    	   window.location.href = _rootPath+"/portal/home/cargoList?pageNo="+pageNo+"&cityPortNo="+cityPortNo+"&bigAreaCode="+bigAreaCode+"";
				return true;
		       }
		     }
		   });
});
