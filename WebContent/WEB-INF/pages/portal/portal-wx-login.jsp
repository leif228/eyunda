<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>请船易</title>
<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<link rel="shortcut icon" href="/favicon.ico">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link rel="stylesheet"
	href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
<link rel="stylesheet"
	href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
<style>
.about-img {
	margin: 0 auto;
	width: 20rem;
	height: 20rem;
	boder: 1px solid #ccc;
}
</style>
</head>
<body>
	<div class="page-group">
		<div id="page-login" class="page">
			<header class="bar bar-nav">
				<h1 class="title">用户登录</h1>
				<a class="button button-link button-nav pull-right external"
					href="${ctx }/scfreight/wx/register?state=${state}"> 注册用户 </a>
			</header>
			<div class="content native-scroll" style="background: #fff">
				<div class="buttons-tab">
					<a href="#tab1" class="tab-link active button">账号登录</a> <a
						href="#tab2" class="tab-link button">扫码登录</a>

				</div>
				<div class="tabs">
					<div class="tab active" id="tab1">
						<div class="content-block">
							<div class="card-content-inner"
								style="min-height: 10rem; text-align: center; vertical-align: middle; padding: 3rem 0">
								<h1>${title }</h1>
								<p>真实货源，保障收款，一键接单</p>
							</div>
							<div class="list-block">
								<ul>
									<li>
										<div class="item-content">
											<div class="item-media">
												<i class="icon icon-phone"></i>
											</div>
											<div class="item-inner">
												<div class="item-input">
													<input type="text" name="loginName" id="loginName" placeholder="手机号码" value="">
												</div>
											</div>
										</div>
									</li>
									<li>
										<div class="item-content">
											<div class="item-media">
												<i class="icon icon-card"></i>
											</div>
											<div class="item-inner">
												<div class="item-input">
													<input type="password" placeholder="登录密码" name="password" id="password">
													<input type="hidden" id="oid" name="oid" value="${openId }"/>
													<input type="hidden" id="state" value="${state }"/>
												</div>
											</div>
										</div>
									</li>
									<li style="display:none">
										<div class="item-content">
											<div class="item-media">
												<i class="icon icon-code"></i>
											</div>
											<div class="item-inner">
												<div class="item-input">
													<div class="pull-left">
														<input type="text" placeholder="验证码">
													</div>
													<div class="pull-right"
														style="width: 120px; padding-top: 10px">
														<a href="#" class="button button-small">495312</a>
													</div>
													<div style="clear: both"></div>
												</div>
											</div>
										</div>
									</li>
								</ul>
							</div>
							<div class="content-block" style="margin-bottom: 0">
								<a href="#"
									class="button button-big button-fill button-success" data-no-cache="true" id="login">登录请船易</a>
							</div>
							<div style="text-align: right; width: 100%; padding: 0.75rem">
								<div class="pull-left">
									<a href="${ctx }/scfreight/wx/forgetpwd" class="external"> 忘记密码</a>
								</div>
								<div class="pull-right">
									<a href="${ctx }/scfreight/wx/register?state=${state}" class="external"> 注册请船易</a>
								</div>
								<div class="clean-both"></div>
							</div>

						</div>
					</div>
					<div class="tab" id="tab2">
						<div class="content-block">
							<div style="margin: 0 auto; text-align: center">
								<img id="qcode" class="about-img"
									src="http://www.eyd98.com/portal/login/getQcode?uid=litJbfMowBXajweerkLJ1UT9OXhmwPdP">
							</div>
						</div>
					</div>
				</div>

			</div>

		</div>
		<script type='text/javascript'
			src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
		<script type='text/javascript'
			src='http://www.eyd98.com/js/zepto-cookie.js' charset='utf-8'></script>
		<script type='text/javascript'
			src='http://www.eyd98.com/js/zepto-url.js' charset='utf-8'></script>
		<script type='text/javascript'
			src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
		<script type='text/javascript'
			src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js'
			charset='utf-8'></script>
		<script>
			$(function() {
				$(document).on("pageInit", "#page-login", function(e, id, page) {
					var $content = $(page).find('.content');
					$content.on('click','#login',function(){
						$.post('${ctx}/scfreight/login/wxLogin',{'openId':$("#oid").val(),'loginName':$("#loginName").val(),'password':$("#password").val()}, function(response){
							if(response.returnCode=="Success"){
								ss = $("#state").val();
								if(ss == 'ship'){
									window.location.href="${ctx}/scfreight/view/freight/freightList?type=ship&caller=weixin&scfFreightCode=sended";
								}else if (ss == 'cargo'){
									window.location.href="${ctx}/scfreight/view/cargo/cargoList";
								}
							}else{
								$.alert(response.returnCode);
							}
						});
						
					});
				});

				$.init();
			});
		</script>
</body>
</html>