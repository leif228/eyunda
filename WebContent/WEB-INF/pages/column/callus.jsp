<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>${userData.unitName}</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.css" />
<link rel="stylesheet"
	href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen" />
<link rel="stylesheet" href="${ctx}/css/reset.css" media="screen" />
<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/portal/portal-site.js"></script>
<script type="text/javascript">
	var _rootPath = "${ctx}";
</script>
<style>
.contact-info p{
	line-height:24px;
}
.contact-msg{
	text-size:16px;
	line-height: 24px;
	margin:40px 0px 20px 0px;
}
</style>
</head>

<body>
	<jsp:include page="./head.jsp"></jsp:include>
	<div class="row">
		<div class="span12 ">
			<div class="contact-msg">
			${ciData.content }
			</div>
		</div>
	</div>
	<div class="row">
		<div class="span12">
		<form action="${ctx}/portal/site/callform" class="form" id="callform" method="post">
		    <fieldset>
              <legend>建议</legend>
			  <div class="control-group">
              <input id="name" name="name" type="text" class="span12" placeholder="您的姓名:">
			  </div>
			  <div class="control-group">
              <input id="email" name="email" type="text" class="span12" placeholder="您的邮箱:">
			  </div>
			  <div class="control-group">
		      <textarea id="content" name="content" rows="5" class="span12" placeholder="您的建议:"></textarea>
			  </div>
              <a href="javascript:void(0);" class="btn btn-large sendform" style="width:100px">提交</a>
            </fieldset>
		</form>
        </div>
	</div>


	<jsp:include page="./foot.jsp"></jsp:include>
</body>
</html>

