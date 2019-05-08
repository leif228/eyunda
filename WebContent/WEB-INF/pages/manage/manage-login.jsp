<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>登录</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${ctx}/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.login.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div id="logo">
		<img src="${ctx}/img/logo.png" alt="" />
	</div>
	<div id="loginbox">
		<form id="loginform" class="form-vertical" action="${ctx}/manage/login/login" method="post">
		<p>输入用户登录名及密码：</p>
		<div class="control-group">
			<div class="controls">
				<div class="input-prepend">
					<span class="add-on"><i class="icon-user"></i></span><input
						type="text" id="username" name="username" placeholder="用户登录名" />
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<div class="input-prepend">
					<span class="add-on"><i class="icon-lock"></i></span><input
						type="password" id="password" name="password" placeholder="密码" />
				</div>
			</div>
		</div>
		<div class="form-actions">
			<span class="pull-left"><a href="#" class="flip-link"
				id="to-recover">忘记密码？</a></span> <span class="pull-right"><input
				type="submit" class="btn btn-inverse" value="登录" /></span>
		</div>
		</form>
		<form id="recoverform" action="#" class="form-vertical">
		<p>输入邮件地址，我们将发邮件通知你如何找回密码。</p>
		<div class="control-group">
			<div class="controls">
				<div class="input-prepend">
					<span class="add-on"><i class="icon-envelope"></i></span><input
						type="text" id="email" name="email" placeholder="邮件地址" />
				</div>
			</div>
		</div>
		<div class="form-actions">
			<span class="pull-left"><a href="#" class="flip-link"
				id="to-login">&lt; 返回登录</a></span> <span class="pull-right"><input
				type="submit" class="btn btn-inverse" value="获取密码" /></span>
		</div>
		</form>
	</div>

	<script src="${ctx}/js/jquery.min.js"></script>
	<script src="${ctx}/js/unicorn.login.js"></script>
</body>
</html>
