<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="http://${header['host']}${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>错误 - 404</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="stilearning" />

<!-- styles -->
<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-helper.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-icon.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/animate.css" rel="stylesheet" />

<script src="${ctx}/js/jquery.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>
</head>

<body>
	<!-- section header -->
	<header class="header" data-spy="affix" data-offset-top="0">
		<!--nav bar helper-->
		<div class="navbar-helper">
			<div class="row-fluid">
				<!--panel site-name-->
				<div class="span2">
					<div class="panel-sitename">
						<h2>
							<a href="#"><span class="color-teal">请船易</span></a>
						</h2>
					</div>
				</div>
				<!--/panel name-->
			</div>
		</div>
		<!--/nav bar helper-->
	</header>

	<!-- section content -->
	<section class="section">
		<div class="container">
			<div class="error-page">
				<h1 class="error-code color-red">错误 404</h1>
				<p class="error-description muted">
				对不起，你所请求的网页不存在！
				</p>

				<a href="javascript:void(0);" onclick="javascript:window.history.go(-1);" class="btn btn-small btn-primary"><i
					class="icofont-arrow-left"></i> 返回上一页</a> <a href="${ctx}"
					class="btn btn-small btn-success">返回首页 <i
					class="icofont-arrow-right"></i></a>
			</div>
		</div>
	</section>

	<!-- section footer -->
	<footer>
		<a rel="to-top" href="#top"><i class="icofont-circle-arrow-up"></i></a>
	</footer>

	<!-- javascript
        ================================================== -->
</body>
</html>
