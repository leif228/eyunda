<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx"
	value="${pageContext.request.contextPath}" />
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
<link rel="stylesheet"
	href="http://www.eyd98.com/css/scfship/common.css">
	<script>
		var _state = "${state }";
	</script>

</head>
<body>
	<div class="page-group">
		<div id="page-first" class="page page-current">
			<header class="bar bar-nav">
				<a class="button button-link button-nav pull-left external"
					href="${loginUrl }"> <span class="icon icon-left"></span> 返回
				</a>
				<h1 class="title">用户注册</h1>
				<a class="button button-link button-nav pull-right external"
					href="${loginUrl }"> 登录 </a>
			</header>
			<div class="content native-scroll" style="background: #fff">
				<div class="card-content-inner"
					style="min-height: 10rem; text-align: center; vertical-align: middle; padding: 3rem 0">
					<h1>${title }</h1>
					<p>真实货源，保障收款，一键接单</p>
				</div>
				<div class="list-block">
					<ul>
						<!-- Text inputs -->
						<li>
							<div class="item-content">
								<div class="item-media">
									<i class="icon icon-phone"></i>
								</div>
								<div class="item-inner">
									<div class="item-input">
										<input type="text" id="loginName" name="loginName" placeholder="输入手机号码" />
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-media">
									<i class="icon icon-me"></i>
								</div>
								<div class="item-inner">
									<div class="item-input">
										<input type="text" id="trueName" name="trueName" placeholder="输入真实姓名" />
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-media">
									<i class="icon icon-emoji"></i>
								</div>
								<div class="item-inner">
									<div class="item-input">
										<input type="text" id="nickName" name="nickName" placeholder="输入网站显示名称" />
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
										<input type="text" id="idCardNo" name="idCardNo" placeholder="请输入身份证号码" />
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-media">
									<i class="icon icon-star"></i>
								</div>
								<div class="item-inner">
									<div class="item-input">
										<input type="password" id="password" name="password" placeholder="输入登录密码">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-media">
									<i class="icon icon-refresh"></i>
								</div>
								<div class="item-inner">
									<div class="item-input">
										<input type="password" id="repassword" name="repassword" placeholder="重复输入登录密码">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-media">
									<i class="icon icon-code"></i>
								</div>
								<div class="item-inner">
									<div class="item-input">
										<div class="row no-gutter">
									      <div class="col-60"><input type="text" id="checkCode" name="checkCode" placeholder="手机验证码"></div>
									      <div class="col-40" style="margin-top:10px;"><button class="button button-small" id="sendCheckCode">获取验证码</button></div>
									    </div>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="content-block" style="margin-bottom: 0">
					<a href="#"
						class="button button-big button-fill button-success" id="regFirstBtn">注册请船易</a>
				</div>
				<div style="text-align: right; width: 100%; padding: 0.75rem">
					<a href="#"> 成为请船易船东，我同意服务条款</a>
				</div>

			</div>
		</div>

		<div id="page-second" class="page">
			<header class="bar bar-nav">
				<a class="button button-link button-nav pull-left" href="#"> <span
					class="icon icon-left"></span> 返回
				</a>
				<h1 class="title">填写资料</h1>
				<a class="button button-link button-nav pull-right"
					href="./login.html"> 登录 </a>
			</header>
			<div class="content native-scroll" style="background: #fff">
				<div class="card-content-inner"
					style="text-align: center; vertical-align: middle;">
					<p>以下问题，是以后忘记密码的时候需资料，请真实填写资料</p>
				</div>
				<div class="list-block">
					<ul>
						<!-- Text inputs -->
						<li>
							<div class="item-content">
								<div class="item-media">
									<i class="icon icon-me"></i>
								</div>
								<div class="item-inner">
									<div class="item-input">
										<input type="text" placeholder="输入真实姓名">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-media">
									<i class="icon icon-emoji"></i>
								</div>
								<div class="item-inner">
									<div class="item-input">
										<input type="text" placeholder="输入网站显示昵称">
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="content-block">
					<div class="row">
						<div class="col-50">
							<a href="./register.html"
								class="button button-big button-fill button-danger">上一步</a>
						</div>
						<div class="col-50">
							<a href="./register-second.html"
								class="button button-big button-fill button-success">下一步</a>
						</div>
					</div>
				</div>

			</div>
		</div>
		<div id="page-third" class="page">
			<header class="bar bar-nav">
				<a class="button button-link button-nav pull-left" href="#"> <span
					class="icon icon-left"></span> 返回
				</a>
				<h1 class="title">可装载货物</h1>
				<a class="button button-link button-nav pull-right"
					href="./login.html"> 登录 </a>
			</header>
			<div class="content native-scroll">
				<div class="list-block">
					<ul>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title">可承载危险品</div>
									<div class="item-after">
										<label class="label-switch"> <input type="checkbox">
											<div class="checkbox"></div>
										</label>
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title">是否有帆布</div>
									<div class="item-after">
										<label class="label-switch"> <input type="checkbox"
											checked>
											<div class="checkbox"></div>
										</label>
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title">是否有自卸装备</div>
									<div class="item-after">
										<label class="label-switch"> <input type="checkbox"
											checked>
											<div class="checkbox"></div>
										</label>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="content-block-title">载货类型［单选］</div>
				<div class="list-block media-list">
					<ul>
						<li><label class="label-checkbox item-content"> <input
								type="radio" name="my-radio" checked>
								<div class="item-media">
									<i class="icon icon-form-checkbox" checked></i>
								</div>
								<div class="item-inner">
									<div class="item-title">干散货</div>
								</div>
						</label></li>
						<li><label class="label-checkbox item-content"> <input
								type="radio" name="my-radio">
								<div class="item-media">
									<i class="icon icon-form-checkbox"></i>
								</div>
								<div class="item-inner">
									<div class="item-title-row">
										<div class="item-title">港澳线集装箱</div>
									</div>
								</div>
						</label></li>
						<li><label class="label-checkbox item-content"> <input
								type="radio" name="my-radio">
								<div class="item-media">
									<i class="icon icon-form-checkbox"></i>
								</div>
								<div class="item-inner">
									<div class="item-title-row">
										<div class="item-title">内贸集装箱</div>
									</div>
								</div>
						</label></li>
					</ul>
					<div class="content-block-title">干散货具体类型［可多选］</div>
					<div class="list-block media-list inset sublist">
						<ul>
							<li><label class="label-checkbox item-content"> <input
									type="checkbox" name="checkbox" checked>
									<div class="item-media">
										<i class="icon icon-form-checkbox"></i>
									</div>
									<div class="item-inner">
										<div class="item-title-row">
											<div class="item-title">钢材</div>
										</div>
									</div>
							</label></li>
							<li><label class="label-checkbox item-content"> <input
									type="checkbox" name="checkbox" checked>
									<div class="item-media">
										<i class="icon icon-form-checkbox"></i>
									</div>
									<div class="item-inner">
										<div class="item-title-row">
											<div class="item-title">矿石</div>
										</div>
									</div>
							</label></li>
							<li><label class="label-checkbox item-content"> <input
									type="checkbox" name="checkbox">
									<div class="item-media">
										<i class="icon icon-form-checkbox"></i>
									</div>
									<div class="item-inner">
										<div class="item-title-row">
											<div class="item-title">煤炭</div>
										</div>
									</div>
							</label></li>
							<li><label class="label-checkbox item-content"> <input
									type="checkbox" name="checkbox">
									<div class="item-media">
										<i class="icon icon-form-checkbox"></i>
									</div>
									<div class="item-inner">
										<div class="item-title-row">
											<div class="item-title">水泥</div>
										</div>
									</div>
							</label></li>
						</ul>
					</div>
				</div>
				<div class="content-block">
					<div class="row">
						<div class="col-50">
							<a href="./register-one.html"
								class="button button-big button-fill button-danger">上一步</a>
						</div>
						<div class="col-50">
							<a href="./register-third.html"
								class="button button-big button-fill button-success">下一步</a>
						</div>
					</div>
				</div>
			</div>			
		</div>
		<div id="page-finished" class="page">
			<header class="bar bar-nav">
				<a class="button button-link button-nav pull-left" href="#"> <span
					class="icon icon-left"></span> 返回
				</a>
				<h1 class="title">完善船舶信息</h1>
			</header>
	
			<div class="content native-scroll">
				<div class="list-block">
					<ul>
						<!-- Text inputs -->
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">船舶名称</div>
									<div class="item-input">
										<input type="text" placeholder="">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">MMSI编号</div>
									<div class="item-input">
										<input type="text" placeholder="">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">船舶识别码</div>
									<div class="item-input">
										<input type="text" placeholder="">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">建造日期</div>
									<div class="item-input">
										<input type="text" placeholder="">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">船舶名称</div>
									<div class="item-input">
										<input type="text" placeholder="">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">船籍港</div>
									<div class="item-input">
										<input type="text" placeholder="">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">船长(米)</div>
									<div class="item-input">
										<input type="text" placeholder="">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">船宽(米)</div>
									<div class="item-input">
										<input type="text" placeholder="">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">型深(米)</div>
									<div class="item-input">
										<input type="text" placeholder="">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">满载吃水(米)</div>
									<div class="item-input">
										<input type="text" placeholder="">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">过孔高度(米)</div>
									<div class="item-input">
										<input type="text" placeholder="">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">主机功率(KW)</div>
									<div class="item-input">
										<input type="text" placeholder="">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">总吨</div>
									<div class="item-input">
										<input type="text" placeholder="">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">净吨</div>
									<div class="item-input">
										<input type="text" placeholder="">
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">船舶证书</div>
									<div class="item-input">
										<div class="pull-left">
											<img
												src="http://www.eyd98.com/download/imageDownload?url=/default/ship/0101.jpg"
												style="margin-top: 10px; width: 80px; height: 60px;" alt=""
												class="thumbnail">
										</div>
										<div class="pull-right" style="width: 100px; margin-top: 25px;">
											<a href="#" class="button button-fill">选择图片</a>
										</div>
										<div class="clear-both"></div>
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="item-content">
								<div class="item-inner">
									<div class="item-title label">船舶侧面图</div>
									<div class="item-input">
										<div class="pull-left">
											<img
												src="http://www.eyd98.com/download/imageDownload?url=/default/ship/0101.jpg"
												style="margin-top: 10px; width: 80px; height: 60px;" alt=""
												class="thumbnail">
										</div>
										<div class="pull-right" style="width: 100px; margin-top: 25px;">
											<a href="#" class="button button-fill">选择图片</a>
										</div>
										<div class="clear-both"></div>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="content-block">
					<div class="row">
						<div class="col-50">
							<a href="./register-second.html"
								class="button button-big button-fill button-danger">上一步</a>
						</div>
						<div class="col-50">
							<a href="./login.html"
								class="button button-big button-fill button-success">完成注册</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type='text/javascript'
		src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
	<script type='text/javascript'
		src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
	<script type='text/javascript'
		src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
	<script>
			function validRegister(){
				var trueName = $.trim($("#trueName").val());
				var nickName = $.trim($("#nickName").val());
				var idCardNo = $.trim($("#idCardNo").val());
				var repassword = $.trim($("#repassword").val());
				var loginName = $.trim($("#loginName").val());
				var password = $.trim($("#password").val());
				var checkCode = $.trim($("#checkCode").val());
				if (!loginName) {
	                $.alert("请输入手机号码！");
	                return false;
	            } else if (!/^1[3|4|5|7|8]\d{9}$/.test(loginName)) {
	                $.alert("你输入的手机号码不正确！");
	                return false;
	            }
				if(!trueName){
					$.alert("请输入真实姓名！");
	                return false;
				}else if(!/^[\u4E00-\u9FA5]+$/.test(trueName)){
					$.alert("请输入中文真实姓名！");
	                return false;
				}
				
				if(!nickName){
					$.alert("请输入昵称");
	                return false;
				}
				
				if(!idCardNo){
					$.alert("请请输入身份证号码！");
	                return false;
				}else if(!/^\d{14}\d{3}?\w$/.test(idCardNo)){
					$.alert("请输入正确的身份证号码！");
	                return false;
				}
				
				if(!password || password == null){
					$.alert("请输入登陆密码！");
	                return false;
				}else if(password.length < 6){
					$.alert("密码长度不得小于六位！");
	                return false;
				}/*else if(!/^(?![^a-zA-Z]+$)(?!\D+$)/.test(password)){
					$.alert("密码必须包含一个字母和一个数字！");
	                return false;
				}*/else if(repassword != password){
					$.alert("两次输入的密码不一致！");
	                return false;
				}
				return true;
			}
			function validCheckSend(){
				var phone = $.trim($("#loginName").val());
				if (!phone) {
	                $.alert("请输入手机号码！");
	                return false;
	            } else if (!/^1[3|4|5||7|8]\d{9}$/.test(phone)) {
	                $.alert("你输入的手机号码不正确！");
	                return false;
	            }
				return true;
			}
			var countdown=60;
			function setTimeCount(o){
			    if (countdown == 0) {   
			    	o.removeAttr("disabled");      
			    	o.html("获取验证码");   
			        countdown = 60;   
			        return;  
			    } else {   
			    	o.attr("disabled", true);   
			    	o.html("(" + countdown + ")s后重新发送");   
			        countdown--;
			        setTimeout(function(){
			        	setTimeCount(o);
	                },1000);
			    }
			}
			$(function() {
				$(document).on("pageInit", "#page-first", function(e, id, page) {
					var $content = $(page).find('.content');
					$content.on('click','#regFirstBtn',function(){
						if(validRegister()){
							$.post('${ctx}/scfreight/login/register',{'trueName':$("#trueName").val(),'nickName':$("#nickName").val(),'idCardNo':$("#idCardNo").val(),'mobile':$("#loginName").val(),'role':_state,'loginName':$("#loginName").val(),'password':$("#password").val(),'checkCode':$("#checkCode").val()}, function(response){
								if(response.returnCode=="Success"){
								    $.showPreloader('注册成功，请登陆系统！')
								    setTimeout(function () {
								        $.hidePreloader();
								        window.location.href="${loginUrl}";
								    }, 1000);
								}else{
									$.alert(response.returnCode);
								}
							});
						}
					});
 
					$content.on('click','#sendCheckCode',function(){
						if(validCheckSend()){
							setTimeCount($("#sendCheckCode"));
							$.post('${ctx}/scfreight/login/checkCode',{'checkType':'register','mobile':$("#loginName").val()}, function(response){
								if(response.returnCode=="Success"){
								    $.showPreloader('短信发送成功！');
								    setTimeout(function () {
								        $.hidePreloader();
								    }, 2000);
								}else{
									$.alert(response.returnCode);
								}
							});
						}
					});
				});

				$.init();
			});
		</script>
</body>
</html>