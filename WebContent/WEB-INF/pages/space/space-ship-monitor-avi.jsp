<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>会员空间</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/css/adminia.css" />
<link rel="stylesheet" href="${ctx}/css/adminia-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/pages/plans.css" />
<link rel="stylesheet" href="${ctx}/css/fullcalendar.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-helper.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-icon.css" />
<link rel="stylesheet" href="${ctx}/css/datepicker.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
	class="skin-color" />

<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/unicorn.tables.js"></script>
<script type="text/javascript">
	var _rootPath = "${ctx}";
</script>

<style>

#content {
	margin-left: 0px;
}

#dlgAdd .user-info {
	list-style: none;
}

#dlgAdd .account-container {
	padding: 3px;
}

#dlgAdd .user-info>li {
	float: left;
	margin: 10px;
}

#dlgAdd .account-container:hover {
	padding: 3px;
	background: #00CCFF;
	cursor: pointer
}

.addBack {
	background: #00CCFF;
}

.widget-content {
	height: 472px;
}

div {
	font-size: 12px;
}

button {
	margin-top: 4px;
}

#imageResult {
	position: fixed;
	margin-top: -475px;
	margin-left: 846px;
	display: none;
}

.close {
	cursor: pointer;
	float: left;
	margin-left: -1px;
	"
}
</style>
</head>

<body onload="createPlayer();">
	<jsp:include page="./space-head.jsp"></jsp:include>

	<div class="span10">
		<h1 class="page-title">
			<i class="icon-star icon-white"></i> 船舶监控
		</h1>
		<div class="row">
			<div class="span10">
				<div class="widget-box">
					<div class="widget-title">
						<h5>航次回放图</h5>
					</div>

					<div id="container" class="widget-content">
						<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
							codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0"
							width="300" height="200">
							<param name="movie" value="${ctx}/js/Flvplayer.swf" />
							<param name="quality" value="high" />
							<param name="allowFullScreen" value="true" />
							<param name="FlashVars" 
							value="vcastr_file=${ctx}/space/monitor/aviDownload&LogoText=www.eyd98.com&BufferTime=3&autoPlay=false&autoRewind=false" />
							<embed src="${ctx}/js/Flvplayer.swf" allowfullscreen="true"
								flashvars="vcastr_file=${ctx}/space/monitor/aviDownload&LogoText=www.eyd98.com&autoPlay=false&autoRewind=false"
								quality="high"
								pluginspage="http://www.macromedia.com/go/getflashplayer"
								type="application/x-shockwave-flash" width="300" height="200"></embed>
						</object>
					</div>
				</div>
			</div>
			<!-- /span9 -->
		</div>
		<!-- /row -->
	</div>
	<!-- /row -->
	</div>
	<!-- /content -->
	<jsp:include page="./space-foot.jsp"></jsp:include>

</body>
<!-- <script type="text/javascript">  
$(function() {  
    $('a.media').media();  
});  
</script> -->
<!-- <script type="text/javascript">
	var myPlayer = videojs('example_video_1');
	videojs("example_video_1").ready(function() {
		var myPlayer = this;
		myPlayer.play();
	});
</script> -->
</html>