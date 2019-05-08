<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>${noticeData.title }</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="stilearning" />

<%-- <link rel="stylesheet" href="${ctx}/css/reset.css" media="screen" /> --%>
<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-helper.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-icon.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/animate.css" rel="stylesheet" />
<link href="${ctx}/css/uniform.default.css" rel="stylesheet" />

<script src="${ctx}/js/jquery.js"></script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->
<script type="text/javascript">
	var _rootPath = "${ctx}";
</script>

<style>
/* this use for demo knob only, you can delete this if you want*/
@media (max-width: 767px)
body {
    padding-right: 0px;
    padding-left: 0px;
}
.demo-knob {
	text-align: center;
	margin-top: 10px;
	margin-bottom: 10px;
}

.article-title, .article-time {
	margin: 10px 0px;
	text-align: center;
}

.mt20 {
	margin-top: 20px;
}

.img-responsive {
	width: 100%;
}

.info {
	text-align: left;
	margin: 20px 0px;
	line-height: 24px;
}

.centerc {
	margin: 0 auto;
}
.mlr20{
	margin-left:0px;
	margin-right:0px;
}
.content{
	margin-top:0px;
}
.content > .content-body{
	padding:40px 20px 20px 20px;
}	
</style>

</head>

<body>


	<!-- section content -->
	<section class="section">
		<div class="row-fluid">
			<!-- span content -->
			<div class="span12">
				<!-- content -->
				<div class="content">
					<!-- content-body -->
					<div class="content-body">
						<div class="mlr20">
							<div class="article-title">
								<h3>${noticeData.title }</h3>
							</div>
							<div class="article-time help-block">发表时间:${noticeData.publishTime }</div>
							<div class="mt20">

								<c:choose>
									<c:when test="${!empty noticeData.source}">
										<div class="img-responsive" align="center">
											<img
												src="${ctx}/download/imageDownload?url=${noticeData.source}" style="width:100%;height:auto">
										</div>
									</c:when>
									<c:otherwise>
										<%--  <div class="img-responsive" align="center">
											<img src="${ctx}/img/notice/notice.png" alt=""
												class="thumbnail" />
										</div>  --%>
									</c:otherwise>
								</c:choose>
								<div style="text-align: center">
									<div class="info centerc">${noticeData.content}</div>
								</div>
							</div>
						</div>

					</div>
					<!--/content-body -->
				</div>
				<!-- /content -->
			</div>

			<!-- /span content -->

		</div>
	</section>

	<!-- javas cript
        ================================================== -->
	<script src="${ctx}/js/bootstrap.js"></script>
	<script src="${ctx}/js/uniform/jquery.uniform.js"></script>

	<script src="${ctx}/js/knob/jquery.knob.js"></script>
	<script src="${ctx}/js/peity/jquery.peity.js"></script>

	<!-- required stilearn template js, for full feature-->
	<script src="${ctx}/js/holder.js"></script>
	<script src="${ctx}/js/jquery.form-2.63.js"></script>
	<script type="text/javascript">
		var _rootPath = "${ctx}";
	</script>

</body>
</html>