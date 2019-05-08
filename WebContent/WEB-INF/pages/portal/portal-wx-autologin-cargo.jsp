<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>易运达</title>
<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<link rel="shortcut icon" href="/favicon.ico">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link rel="stylesheet"
	href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
<link rel="stylesheet"
	href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
<link rel="stylesheet" href="../assets/common.css">
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
			<div class="content native-scroll" style="background: #fff">
			

			</div>
		</div>
	</div>
		<script type='text/javascript'
			src='//g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
		<script type='text/javascript'
			src='//g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
		<script type='text/javascript'
			src='//g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js'
			charset='utf-8'></script>
		<script>
			$(function() {
				$(document).on("pageInit", "#page-login", function(e, id, page) {
					$.showPreloader('页面加载中');
					setTimeout(function () {
				        $.hidePreloader();
				        
				        window.location.href="${ctx}/scfreight/view/cargo/cargoList";
				    }, 1000);
				});
				$.init();
			});
		</script>
</body>
</html>