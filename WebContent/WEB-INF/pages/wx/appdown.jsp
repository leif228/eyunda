<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="eyunda">
<meta name="author" content="eyunda">
<style>
body, h1, h2, h3, h4, h5, h6, .h1, .h2, .h3, .h4, .h5, .h6 {
	font-family: STXihei, STHeiti, "华文细黑", "Microsoft YaHei", "微软雅黑", SimSun,
		"宋体", Heiti, "黑体", sans-serif;
}

a {
	text-decoration: none;
	color: #ffffff;
}

.container:before, .container:after {
	content: " ";
	display: table;
}

.container:after {
	clear: both;
}

.logo {
	text-align: center;
}

.logo img {
	width: 100px;
}

.title {
	text-align: center;
	margin-top: 15px;
}

.title h1 {
	font-size: 25px;
	font-weight: 500;
	color: #999;
}

.icon_row {
	margin: auto;
	display: table;
}

.icon_row>div {
	float: left;
	margin: 0 5px;
}

.icon_row>div a {
	margin: auto;
	height: 90px;
	overflow: hidden;
}

.app_btn {
	display: block;
	width: 70px;
	text-align: center;
	color: #ffffff;
	padding: 10px;
}

.green_btn {
	background-color: rgb(0, 177, 106);
}

.orange_btn {
	background-color: rgb(247, 148, 30);
}

.green_btn2 {
	background-color: #3bbe01;
}

.orange_btn2 {
	background-color: #f7a900;
}

.gray_btn {
	background-color: #dddddd;
}

.blue_btn {
	background-color: rgb(20, 162, 212);
}

.copyright {
	font-size: 12px;
	text-align: center;
	color: #777;
	border-top: 1px solid #eee;
	margin-top: 20px;
	padding: 10px 0;
}

.copyright img {
	margin-bottom: -4px;
}

#main {
	display: none;
}

#weixin {
	display: none;
}

.weixin-0 {
	color: #555;
	padding: 10px;
}

.weixin-arrow {
	position: absolute;
	right: 30px;
	top: 10px;
}

.weixin-1 {
	font-size: 1.5em;
	position: absolute;
	left: 30px;
	top: 60px;
}

.weixin-2 {
	margin-top: 20px;
	font-size: 1.5em;
	position: absolute;
	left: 30px;
	top: 74px;
}
</style>
<title>APP下载</title>

</head>
<body>
	<div id="main">
		<div class="container logo">
			<img src="${ctx}/img/wx/eyundaapp.png">
		</div>
		<div class="container title">
			<h1>点击下面图标下载</h1>
		</div>
		<div class="container icon_row">
			<div>
				<a href="${url}"
					class="app_btn green_btn"><img
					src="${ctx}/img/wx/android_icon.png"><br>安卓</a>
			</div>
			<!-- 
			<div>
				<a href="javascript:void(0);" onclick="javascript:iosInstall();"
					class="app_btn orange_btn"><img
					src="${ctx}/img/wx/ios_icon.png"><br>苹果</a>
			</div> 
			<div>
				<a href="${ctx}"
					class="app_btn blue_btn"><img
					src="${ctx}/img/wx/webapp-icon.png"><br>手机网页</a>
			</div>
			-->
		</div>
		<div class="container copyright">
			©2015 eyd98.com 版权所有 · Made in <a href="${ctx}"><img
				src="${ctx}/img/wx/logo.png"></a>
		</div>
	</div>
	<div id="weixin">
		<div class="weixin-0">请按提示操作</div>
		<div class="weixin-arrow">
			<img src="${ctx}/img/wx/weixin-arrow.png">
		</div>
		<div class="weixin-1">1. 点击</div>
		<div id="step2" class="weixin-2">2. 在Safari中打开</div>
	</div>
	<script>
		var browser = {
			versions : function() {
				var u = navigator.userAgent, app = navigator.appVersion;
				return {
					trident : u.indexOf('Trident') > -1,
					presto : u.indexOf('Presto') > -1,
					webKit : u.indexOf('AppleWebKit') > -1,
					gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,
					mobile : !!u.match(/AppleWebKit.*Mobile.*/),
					ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
					android : u.indexOf('Android') > -1
							|| u.indexOf('Linux') > -1,
					iPhone : u.indexOf('iPhone') > -1,
					iPad : u.indexOf('iPad') > -1,
					webApp : u.indexOf('Safari') == -1
				}
			}(),
			language : (navigator.browserLanguage || navigator.language)
					.toLowerCase()
		};
		function is_weixin() {
			var ua = navigator.userAgent.toLowerCase();
			if (ua.match(/MicroMessenger/i) == "micromessenger") {
				return true;
			} else {
				return false;
			}
		}
		function iosInstall() {
			if (!browser.versions.mobile) {
				alert("请使用手机访问该网页");
				return;
			}
			url = "${ctx}/wx/appdown";
			window.location.href = url;
		}
		function init() {
			if (browser.versions.ios || browser.versions.iPhone
					|| browser.versions.iPad) {
				document.getElementById("main").style.display = "block";
				return;
			}
			if (is_weixin()) {
				document.getElementById("main").style.display = "none";
				document.getElementById("weixin").style.display = "block";
				if (browser.versions.ios || browser.versions.iPhone
						|| browser.versions.iPad) {
					document.getElementById("step2").innerHTML = "2. 在Safari中打开";
				} else {
					document.getElementById("step2").innerHTML = "2. 在浏览器中打开";
				}
			} else {
				document.getElementById("weixin").style.display = "none";
				document.getElementById("main").style.display = "block";
			}
		}
		init();
	</script>


</body>
</html>