<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>会员管理 - 图片浏览</title>

<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.css" />
<link rel="stylesheet"
	href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen" />
<link rel="stylesheet" href="${ctx}/css/reset.css" media="screen" />
<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript">
	var _rootPath = "${ctx}";
	var currIndex = "${currIndex}";
	var imageUrls = new Array();
	
    <c:forEach var="url" items="${urls}">
      imageUrls.push(["${url}"]);
    </c:forEach>
    
	function jumpImage(index) {
		currIndex = parseInt(currIndex) + parseInt(index);
		if(currIndex < 0){
			currIndex = 0;
		}
		if(currIndex > imageUrls.length - 1){
			currIndex = imageUrls.length - 1;
		}
	
		alert(currIndex);
		
		$("#targetImage").attr("src", _rootPath + "/download/imageDownload?url=" + imageUrls[currIndex])
	}
</script>

<style>
	#targetImage {
		width : 50%;
		height: 75%
	}
</style>
</head>

<body>
	<div class="header">
	 <div class="container">
	 	 <div class="top-title">${userData.trueName}</div>
		 <div class="top-menu">
			 <span class="menu"><img src="${ctx}/img/menu.png" alt=""></span>
			 <ul class="nav1">
			 
			 </ul>
		 </div>
	 </div>
</div>
<div id="main">
  <div class="container">
		<div class="row">
			<div class="span12">
				<div class="row">
					<div class="span12">
						<div style="margin: auto;" align="center">
							<img  class="thumbnail" id="targetImage" src="${ctx}/download/imageDownload?url=${currImageUrl}"/>
						</div>
					</div>
					<div class="span12 mt20">
						<div class="w1002">
						  <button onclick="jumpImage((-1))" class="btn btn-large">上一张</button>
						  <button onclick="jumpImage(1)" class="btn btn-large pull-right">下一张</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>

