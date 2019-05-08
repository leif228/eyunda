<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>前台浏览 - 代理人信息</title>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="stilearning" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-helper.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-icon.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/animate.css" rel="stylesheet" />
<link href="${ctx}/css/uniform.default.css" rel="stylesheet" />
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->
<style>
	/* this use for demo knob only, you can delete this if you want*/
	body{font-size:12px;}
	.layout-right, .fr {float: right;}
	.dn{display:none}
	.fl{float:left;}
	.clearfix:after {visibility: hidden;display: block;font-size: 0px;content: " ";clear: both;height: 0px;}
	.user-glory{font-size:12px;font-weight:normal;color:red}
	.box-body>.center{text-align:center}
	.userInfo{height:60px;padding:20px 0px;background:#fff}
	.userLogo{text-align:right;line-height:40px;width:80px}
	.content > .content-body{padding-top:20px;border-top:1px solid #ccc;}
</style>
<script type="text/javascript">
	var _rootPath = "${ctx}";
</script>
</head>

<body>
	<!-- section header -->
	<jsp:include page="portal-head.jsp"></jsp:include>

	<!-- section content -->
	<section class="section">
		<div class="row-fluid">
			<!-- span side-left -->
			<div class="span1">
				<!--side bar-->
				<jsp:include page="portal-sideleft.jsp"></jsp:include>
				<!--/side bar -->
			</div>
			<!-- span side-left -->

			<!-- span content -->
			<div class="span9">
				<!-- content -->
				<div class="content">
					<!-- content-header -->
					<div class="content-header">
						<h3>
							<i class="icofont-home"></i>
							张三
							<span class="user-glory">金牌代理人</span>
						</h3>
					</div>
					<!-- /content-header -->
					<!-- content-breadcrumb -->
					<div class="content-breadcrumb">
						<!--breadcrumb-->
						<div class="row-fluid  userInfo">
							<div class="span12">
								<!-- userLogo -->
								<div class="fl userLogo">
									<img width="60px" height="60px" src="/eyunda/download/imageDownload?url=/user/44/9/Logo-201501291804011226.jpg"/>
								</div>
								<!-- userInfo -->
								<div class="fl" style="padding-left:10px;width:800px">
									<p style="margin:0px">电话：112312321323</p>
									<p style="margin:0px">email：11111@1111.com</p>
									<p style="margin:0px">公司名称:广州易运达船舶运输代理公司,注册时间：2015-1-21</p>
								</div>

							</div>
						</div>
						<!--/breadcrumb-->
					</div>
					<!-- /content-breadcrumb -->
					
					<!-- content-body -->
					<div class="content-body">
						<!--row-fluid-->
						<div class="row-fluid">
							<!--span-->
							<div class="span12">
								<!--box-->
								<div class="box corner-all">
									<!--box header-->
									<div class="box-header grd-white color-silver-dark corner-top">
										<span>列表1</span>
									</div>
									
									<!--box body-->
									<div class="box-body">
										<div class="row-fluid center">
											<div class="span2">列一</div>
											<div class="span2">列二</div>
											<div class="span2">列三</div>
											<div class="span2">列四</div>
											<div class="span2">列五</div>
											<div class="span2">列六</div>
										</div>
										<div class="row-fluid center">
											<div class="span2">列一</div>
											<div class="span2">列二</div>
											<div class="span2">列三</div>
											<div class="span2">列四</div>
											<div class="span2">列五</div>
											<div class="span2">列六</div>
										</div>
									</div>
									<!--box footer-->
									<div class="gallery-controls bottom">
									<!-- 
										<div class="gallery-category">
											<div class="pull-right">
												<span>页号1 of 2</span>
											</div>
										</div>
									 -->
									</div>
								</div>
								<!--/box-->
							</div>
							<!--/span-->
						</div>
						<!--/row-fluid-->
						
						<!--row-fluid-->
						<div class="row-fluid">
							<!--span-->
							<div class="span12">
								<!--box-->
								<div class="box corner-all">
									<!--box header-->
									<div class="box-header grd-white color-silver-dark corner-top">
										<span>列表2</span>
									</div>
									
									<!--box body-->
									<div class="box-body">
										<div class="row-fluid center">
											<div class="span2">列一</div>
											<div class="span2">列二</div>
											<div class="span2">列三</div>
											<div class="span2">列四</div>
											<div class="span2">列五</div>
											<div class="span2">列六</div>
										</div>
										<div class="row-fluid center">
											<div class="span2">列一</div>
											<div class="span2">列二</div>
											<div class="span2">列三</div>
											<div class="span2">列四</div>
											<div class="span2">列五</div>
											<div class="span2">列六</div>
										</div>
									</div>
									<!--box footer-->
									<div class="gallery-controls bottom">
									<!-- 
										<div class="gallery-category">
											<div class="pull-right">
												<span>页号1 of 2</span>
											</div>
										</div>
									 -->
									</div>
								</div>
								<!--/box-->
							</div>
							<!--/span-->
						</div>
						<!--/row-fluid-->
					</div>
					<!--/content-body -->
				</div>
				<!-- /content -->
			</div>
			<!-- /span content -->

			<!-- span side-right -->
			<div class="span2">
				<!-- side-right -->
				<aside class="side-right">
					<!-- sidebar-right -->
					<div class="sidebar-right">

						<!-- sidebar-right-content -->
						<div class="sidebar-right-content">
							<div class="tab-content">
								<!--contact-->
								<div class="tab-pane fade active in" id="contact"
									style="font-size: 12px;">
									<div class="side-contact">

										<!--contact-control-->
										<div class="contact-control">
											<div class="btn-group pull-right">
												<button class="dropdown-toggle bg-transparent no-border"
													data-toggle="dropdown">
													<i class="icofont-caret-down"></i>
												</button>
												<ul class="dropdown-menu">
													<li><a href="#"><i
															class="icofont-certificate color-green"></i>内河</a></li>
													<li><a href="#"><i
															class="icofont-certificate color-silver-dark"></i>沿海</a></li>
													<li><a href="#"><i
															class="icofont-certificate color-red"></i>远洋</a></li>
													<li><a href="#"><i
															class="icofont-certificate color-orange"></i>陆路</a></li>
												</ul>
											</div>
											<h5>
												<i class="icofont-comment color-teal"></i> 联系人
											</h5>
										</div>
										<!--/contact-control-->
										<!--contact-search-->
										<div class="contact-search">
											<div class="input-icon-prepend">
												<div class="icon">
													<button type="submit">
														<i class="typicn-message color-silver-dark"></i>
													</button>
												</div>
												<input class="input-block-level grd-white" maxlength="11"
													type="text" name="contact-search"
													placeholder="chat with..." />
											</div>
										</div>
										<!--/contact-search-->
										<!--contact-list, we set this max-height:380px, you can set this if you want-->
										<ul class="contact-list">
											<c:forEach var="releaseOperatorData" items="${releaseOperatorDatas}">
											<li class="contact-alt grd-white">
												<a href="#chat" data-toggle="tab" data-id="iin@mail.com"> 
													<div class="contact-item">
														<div class="pull-left">
															<img class="contact-item-object"
																style="width: 32px; height: 32px;"
																src="${ctx}/download/imageDownload?url=${releaseOperatorData.operatorData.userLogo}" />
														</div>
														<div class="contact-item-body">
															<div class="status" title="ofline">
																<i class="icofont-certificate color-silver-dark"></i>
															</div>
															<p class="contact-item-heading bold">${releaseOperatorData.operatorData.trueName} ${releaseOperatorData.operatorData.mobile}</p>
															<p class="contact-item-heading">${releaseOperatorData.operatorData.unitName}</p>
														</div>
													</div>
												</a>
											</li>
											</c:forEach>
										</ul>
										<!--/contact-list-->
									</div>
								</div>
								<!--/contact-->

							</div>
						</div>
						<!-- /sidebar-right-content -->
					</div>
					<!-- /sidebar-right -->
				</aside>
				<!-- /side-right -->
			</div>
			
			<!-- /span side-right -->
		</div>
	</section>

	<!-- javascript
        ================================================== -->
	<script src="${ctx}/js/jquery.js"></script>
	<script src="${ctx}/js/bootstrap.js"></script>
	<script src="${ctx}/js/uniform/jquery.uniform.js"></script>

	<script src="${ctx}/js/knob/jquery.knob.js"></script>
	<script src="${ctx}/js/peity/jquery.peity.js"></script>
	<script src="${ctx}/js/flot/jquery.flot.js"></script>
	<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="${ctx}/js/flot/excanvas.js"></script><![endif]-->
	<script src="${ctx}/js/flot/jquery.flot.categories.js"></script>
	<script src="${ctx}/js/flot/jquery.flot.symbol.js"></script>
	<script src="${ctx}/js/flot/jquery.flot.crosshair.js"></script>
	<script src="${ctx}/js/flot/jquery.flot.stack.js"></script>
	<script src="${ctx}/js/flot/jquery.flot.pie.js"></script>
	<script src="${ctx}/js/flot/jquery.flot.resize.js"></script>

	<script src="${ctx}/js/flot/jquery.flot.demo.js"></script>

	<!-- required stilearn template js, for full feature-->
	<script src="${ctx}/js/holder.js"></script>
	<script src="${ctx}/js/stilearn-base.js"></script>
	<script src="${ctx}/js/jquery.form-2.63.js"></script>
	<script src="${ctx}/js/portal/portal-user-detail.js"></script>
	<script type="text/javascript">
		
	
	</script>

</body>
</html>