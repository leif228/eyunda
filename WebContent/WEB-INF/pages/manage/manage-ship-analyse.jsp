<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>管理控制台</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/unicorn.tables.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script type='text/javascript'>
  var intervalId;

  function analyse(){
	$.ajax({
		method : "GET",
		data : { mmsi : "${mmsi}" },
		url : "${ctx}/manage/ship/ship/analyseStart",
		datatype : "json",
		success : function(data){
			var data = $(data)[0];
			var returnCode = data.returnCode;
			var message = data.message;
			if(returnCode == "Failure"){
				alert(message);	
				clearInterval(intervalId);
				$("#messageInfo").html(message);
				return ;
			} else {
				$("#messageInfo").html(message);
			}
		}
	})
  }
	
  $(document).ready(function(){
	intervalId = setInterval(function () {
		$.ajax({
			method : "GET",
			url : "${ctx}/manage/ship/ship/getAnalyseMessage",
			datatype : "json",
			success : function(data){
				var data = $(data)[0];
				var returnCode = data.returnCode;
				var message = data.message;
				if(returnCode == "Failure"){
					alert(message);	
					clearInterval(intervalId);
					$("#messageInfo").html(message);
					return ;
				} else {
					$.each(data.list, function(index, item){
						if(typeof(item.message) != "undefined"){
							$("#messageInfo").html(item.message);
						}
						
						if(typeof(item.endMessage) != "undefined"){
							$("#messageInfo").html(item.endMessage);
							clearInterval(intervalId);	
						}
					});
				}
			}
		})
	}, 300);
	
  })
</script>
</head>

<body onload="analyse();">

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="current">信息提示</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">

          <div class="error-page">
            <h3>正在进行航次分析</h3>
  			<div id="messageInfo" style="width: 40%"> </div>
          </div>
        </div>
      </div>

      <jsp:include page="./manage-foot.jsp"></jsp:include>

    </div>
  </div>

</body>

</html>
