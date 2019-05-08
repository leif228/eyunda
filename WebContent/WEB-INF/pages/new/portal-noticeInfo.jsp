<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx"
	value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>易运达 - 首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="guoqiang" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/common.css" rel="stylesheet" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>

<script type="text/javascript">
	var _rootPath = "${ctx}";
</script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->
<style>
</style>

</head>

<body>
	<!-- section header -->
	<jsp:include page="portal-head.jsp"></jsp:include>
	<nav class="breadcrumb" style="margin-bottom:10px;background: transparent;">
		<i class="fa-television fa"></i>
		<a class="crumbs" title="返回首页" href="${ctx }/portal/home/shipHome">首页</a>
		<i class="fa fa-angle-right"></i>
		公告
	</nav>
	<!-- section content -->
	<div id="content" class="site-content">
		<div class="clear"></div>
		<!-- content-area -->
		<div id="primary" class="content-area">
			<div class="line-one">
			  <div class="cat-box">
	            <h3 class="cat-title">
	              <a href="###" title="${myShipData.shipName}"><i class="fa fa-bars"></i>公告详情</a>
	            </h3>
	            <div class="clear"></div>
	              <div class="cat-site">
		            <div class="row-fluid">
		              <div class="span2" style="min-height: 100px; text-align: center; margin: 0 auto">
	                    <img src="${ctx}/download/imageDownload?url=${noticeData.source}"
	                      style="width: 100px; height: 100px;" alt="" class="thumbnail" />
		              </div>
		              <div class="span10">
                       	<div style="font-size: 16px; text-align: center;">${noticeData.title}</div>
                        <div class="info centerc">${noticeData.content}</div>
		              </div>
				  </div>
	            </div>
	          </div>
				
			</div>
		</div>
		<!-- widget-area -->
		<div id="sidebar" class="widget-area">
		  <jsp:include page="./customerDownload.jsp"></jsp:include>
		</div>
		<div class="clear"></div>
	</div>
	<!-- section footer -->
	<jsp:include page="portal-foot.jsp"></jsp:include>
	<!-- javascript
    ================================================== -->
	<script src="${ctx}/js/jquery.divSelect.js"></script>
</body>
</html>