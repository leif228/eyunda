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
		货盘
	</nav>
	<!-- section content -->
	<div id="content" class="site-content">
		<div class="clear"></div>
		<!-- content-area -->
		<div id="primary" class="content-area">
			<div class="line-one">
				<div class="row-fluid">

				</div>
			</div>
		</div>
		<!-- widget-area -->
		<div id="sidebar" class="widget-area">
			<aside class="widget">
				<h3 class="widget-title">
					<i class="fa fa-bars"></i>通知公告
				</h3>
				<div id="hot_post_widget">
					<ul>
						<li><span class="li-icon li-icon-1">1</span><a href="###">易运达Android客户端正式发布！</a></li>
						<li><span class="li-icon li-icon-2">2</span><a href="###">易运达电商平台推广有奖活动进行中</a></li>
						<li><span class="li-icon li-icon-3">3</span><a href="###">最新船舶货运行业准则解读</a></li>
						<li><span class="li-icon li-icon-4">4</span><a href="###">积极发展内河水运 培育新增长极</a></li>
						<li><span class="li-icon li-icon-5">5</span><a href="###">财政部、发改委：7项水运涉企收费将取消</a></li>
						<li><span class="li-icon li-icon-6">6</span><a href="###">关于取消有关水运涉企行政事业性收费项目的通知</a></li>
						<li><span class="li-icon li-icon-7">7</span><a href="###">第四届中国西部国际物流产业博览会</a></li>
						<li><span class="li-icon li-icon-8">8</span><a href="###">第六届釜山国际铁道及物流产业展</a></li>
					</ul>
				</div>
				<div class="clear"></div>
			</aside>

			<aside id="about-2" class="widget widget_about wow animated animated"
				data-wow-delay="0.3s"
				style="visibility: visible; animation: 0.3s; -webkit-animation: 0.3s;">
				<h3 class="widget-title">
					<i class="fa fa-bars"></i>软件下载
				</h3>
				<div id="feed_widget">
					<div class="feed-about">
						<div class="about-main">
							<div class="about-img">
								<img src="http://www.eyd98.com/img/two-dimensional-code.png">
							</div>
							<div class="about-name">Android客户端下载</div>
							<div class="about-the">你托运，我承运，平台担保！</div>
						</div>
						<div class="clear"></div>
						<ul>
							<li class="weixin"><span class="tipso_style" id="tip-w"
								data-tipso="#"><a
									title="微信"><i class="fa fa-weixin mtop5"></i></a></span></li>
							<li class="tqq"><a target="blank" rel="external nofollow"
								href="#"
								title="QQ在线"><i class="fa fa-qq mtop5"></i></a></li>
							<li class="tsina"><a title=""
								href="###" target="_blank"
								rel="external nofollow"><i class="fa fa-weibo mtop5"></i></a></li>
							<li class="feed"><a title="订阅"
								href="http://zmingcx.com/feed/" target="_blank"
								rel="external nofollow"><i class="fa fa-rss mtop5"></i></a></li>
						</ul>
						<div class="clear"></div>
					</div>
				</div>
				<div class="clear"></div>
			</aside>
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