<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx"
	value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>会员空间</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<script src="${ctx}/js/jquery-v.min.js"></script>

<script type="text/javascript">
	var _rootPath = "${ctx}";
</script>
<style>
.pwd-box{
	    width:310px;
	    padding-left: 1px;
	    position: relative;
	    border: 1px solid #9f9fa0;
	    border-radius: 3px;
		over-flow:hidden
	}
	.pwd-box input[type="tel"]{
		width: 99%;
	    height: 45px;
	    color: transparent;
	    position: absolute;
	    top: 0;
	    left: 0;
	    border: none;
	    font-size: 18px;
	    opacity: 0;
	    z-index: 1;
	    letter-spacing: 35px;
	}
	.fake-box input{
		width: 44px;
	    height: 48px;
	    border: none;
	    border-right: 1px solid #e5e5e5;
	    text-align: center;
	    font-size: 30px;
	}
	.fake-box input:nth-last-child(1){
		border:none;
	}

	.btn{
		display: inline-block;
		padding: 6px 12px;
		margin-bottom: 0;
		font-size: 14px;
		font-weight: 400;
		line-height: 1.42857143;
		text-align: center;
		white-space: nowrap;
		vertical-align: middle;
		-ms-touch-action: manipulation;
		touch-action: manipulation;
		cursor: pointer;
		-webkit-user-select: none;
		-moz-user-select: none;
		-ms-user-select: none;
		user-select: none;
		background-image: none;
		border: 1px solid transparent;
		border-radius: 4px
	}
	.btn-success{
		color: #fff;
		background-color: #5cb85c;
		border-color: #4cae4c
	}
	.layout{
		margin: 0px auto;
		width: 320px;
		text-align: center;
	}
	.lay-btn{
		width: 310px;
		margin-top: 40px;
		margin-right: 10px;
		height:48px;
		line-height: 40px;
	}
</style>
</head>
<body>
	<div style="margin: 20px auto; width: 95%">
		<div style="text-align: center; margin: 0 auto; width: 320px">
		<div class="layout">
			<div class="pwd-box">
				<input type="tel" maxlength="6" class="pwd-input" id="pwd-input">
				<div class="fake-box">
					<input type="password" readonly="">
					<input type="password" readonly="">
					<input type="password" readonly="">
					<input type="password" readonly="">
					<input type="password" readonly="">
					<input type="password" readonly="">
				</div>
			</div>
		</div>
		<div class="layout"><button class="btn btn-success lay-btn" id="sure">验证支付密码</button></div>
		</div>
		<div style="display: none">
			<input type="hidden" name="type" id="type" value="${type}" />
			<input type="hidden" name="plantBankId" id="plantBankId" value="${plantBankId}" />
			<input type="hidden" name="sessionId" id="sessionId" value="${sessionId}" />
			<input type="hidden" name="walletId" id="walletId" value="${walletId}" />
			<form id="payForm" method="post" action="">
				<input type="hidden" name="orig" id="orig" value="" /> 
				<input type="hidden" name="sign" id="sign" value="" /> 
				<input type="hidden" name="returnurl" value="${returnurl}" /> 
				<input type="hidden" name="NOTIFYURL" value="${notifyurl}" />
			</form>
		</div>
	</div>
	<script>
		var $input = $(".fake-box input");
		$("#pwd-input").on("input", function() {
			var pwd = $(this).val().trim();
			for (var i = 0, len = pwd.length; i < len; i++) {
				$input.eq("" + i + "").val(pwd[i]);
			}
			$input.each(function() {
				var index = $(this).index();
				if (index >= len) {
					$(this).val("");
				}
			});
			if (len == 6) {
				//执行其他操作，自动验证并跳转
				
			}
		});
		$("#sure").click(function(){
			var res = $("#pwd-input").val().trim();
			var sid = $("#sessionId").val().trim();
			var wid = $("#walletId").val().trim();
			var pbid = $("#plantBankId").val().trim();
			var tp = $("#type").val().trim();
			var that = $(this);
			if (res.length == 6) {
				that.attr("disabled","disabled");
				$.ajax({
					type: "post",
					url: _rootPath + "/mobile/pinganpay/valid",
					data: { walletId: wid, sessionId: sid, pwd: res, type:tp, plantBankId: pbid },
					datatype: "json",
					success: function(data){
						var returnCode = $(data)[0].returnCode;
						if(returnCode == 'Failure'){
							that.removeAttr("disabled");
							alert($(data)[0].message);
						} else {
							$("#payForm").attr("action", $(data)[0].url);
							$("#orig").val($(data)[0].orig);
							$("#sign").val($(data)[0].sign);
							$("#payForm").submit();
						}
						return false;
					}
				});
			}
		});
	</script>
</body>
</html>
