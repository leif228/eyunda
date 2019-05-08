<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>

<!--<script src="${ctx}/js/unicorn.js"></script> <script src="${ctx}/js/unicorn.form_common.js"></script> -->
</head>

<body>
	<jsp:include page="./space-head.jsp"></jsp:include>

	<div class="span10">

		<h1 class="page-title">
			<i class="icon-star icon-white"></i> 通知管理
		</h1>

		<div class="row">

			<div class="span10">

				<div class="widget">

					<div class="widget-header">
						<h3>通知查看</h3>
					</div>
					<!-- /widget-header -->
					<div class="widget-content">
						<h3>${message.title}</h3>
						<p>${message.message}</p>
						
						<c:choose>
							<c:when test="${message.message.contains('发送者:')}">
								<p align="right"></p>
							</c:when>
							<c:when test="${fn:startsWith(message.message, '货号')}">
							<p align="right">
								<a class="btn btn-primary " target="_blank"
									href='${ctx}/portal/home/cargoInfo?cargoId=${fn:substring(message.message, 2, fn:indexOf(message.message, ","))}'>货物详情</a>
							</p>
							</c:when>
							<c:when test="${fn:startsWith(message.message, '船舶')}">
							<p align="right">
								<a class="btn btn-primary " target="_blank"
									href='${ctx}/space/ship/myShip'>船舶列表</a>
							</p>
							</c:when>
							<c:otherwise>
								<p align="right">
								点击
								<button class="btn btn-primary "
									onclick="window.location.href='${ctx}/space/order/myOrder';">合同</button>
								进入处理
							</p>
							</c:otherwise>
						</c:choose>
						<p align="left">
							<a class="btn btn-warning" href='javascript:history.go(-1);'>返回</a>
						</p>
					</div>
					<!-- /widget-content -->
				</div>
				<!-- /widget -->
			</div>
			<!-- /span9 -->
		</div>
		<!-- /row -->
	</div>
	<!-- /content -->
	
	</div>
	</div>
	</div>

	<jsp:include page="./space-foot.jsp"></jsp:include>

</body>
</html>
