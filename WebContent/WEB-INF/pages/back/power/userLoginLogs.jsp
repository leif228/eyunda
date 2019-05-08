<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>- FooTable</title>
<meta name="keywords" content="">
<meta name="description" content="">

<link rel="shortcut icon" href="favicon.ico">
<link href="${ctx}/hyqback/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="${ctx}/hyqback/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="${ctx}/hyqback/css/plugins/footable/footable.core.css" rel="stylesheet">

<link href="${ctx}/hyqback/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">

<link href="${ctx}/hyqback/css/animate.css" rel="stylesheet">
<link href="${ctx}/hyqback/css/style.css?v=4.1.0" rel="stylesheet">
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>安全管理 > 用户日志</h5>
					</div>
					<form name="pageform" id="pageform"
						action="${ctx}/back/power/userLoginLogs" method="post">
						<div class="ibox-content">
							<div class="form-group">
								<div class="col-sm-11">
									开始日期：<input id="startDate" name="startDate" value="${startDate}" class="laydate-icon form-control layer-date" placeholder="YYYY-MM-DD" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" style="width: 120px;" />
									结束日期：<input id="endDate" name="endDate" value="${endDate}" class="laydate-icon form-control layer-date" placeholder="YYYY-MM-DD" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" style="width: 120px;" />
									用户：<input type="text" class="form-control" id="userInfo" name="userInfo" value="${userInfo}" placeholder="用户名或手机" style="width: 120px;" />
									<button class="btn btn-primary" type="submit">查询</button>
								</div>
							</div>
							<table class="footable table table-stripped" data-page-size="20"
								data-filter=#filter>

								<thead>
									<tr>
										<th>用户头像</th>
										<th>登录名</th>
										<th>姓名</th>
										<th>电子邮箱</th>
										<th>使用时间</th>
										<th>使用时长</th>
										<th>客户端IP</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="recordData" items="${pageData.result}">
										<tr class="gradeX">
											<td><img src="${ctx}/download/imageDownload?url=${recordData.userLogo}"
												alt="" class="thumbnail" style="width: 60px; height: 60px;" />
											</td>
											<td>${recordData.loginName}</td>
											<td>${recordData.trueName}</td>
											<td>${recordData.email}</td>
											<td>${recordData.loginTime}- <br />${recordData.logoutTime}</td>
											<td>${recordData.timeSpan}</td>
											<td>${recordData.ipAddress}</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="5"><jsp:include page="../pager.jsp"></jsp:include>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 全局js -->
	<script src="${ctx}/hyqback/js/jquery.min.js?v=2.1.4"></script>
	<script src="${ctx}/hyqback/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="${ctx}/hyqback/js/plugins/footable/footable.all.min.js"></script>
    <script src="${ctx}/hyqback/js/plugins/sweetalert/sweetalert.min.js"></script>
	<!-- jQuery Validation plugin javascript-->
	<script src="${ctx}/hyqback/js/plugins/validate/jquery.validate.min.js"></script>
	<script src="${ctx}/hyqback/js/plugins/validate/messages_zh.min.js"></script>
	<!-- layerDate plugin javascript-->
    <script src="${ctx}/hyqback/js/plugins/layer/laydate/laydate.js"></script>
    <script src="${ctx}/hyqback/js/jquery.form-3.51.js"></script>

</body>
</html>
