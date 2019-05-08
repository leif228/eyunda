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
<script type="text/javascript">
	var _rootPath = "${ctx}";
	function jumpPage(currPageNo) {
		if(currPageNo==null || currPageNo==""){
			currPageNo=$("#pageNo").val();
		}
	    $("#pageNo").val(currPageNo);
	    $("#pageform").submit();
	}
</script>

<style>

</style>
</head>

<body>
	<jsp:include page="./head.jsp"></jsp:include>

	<div class="row">
		<div class="span12">
			<c:forEach items="${pageData.result}" var="cid" varStatus="s">
				<div class="article-title"><h3>${cid.title }</h3></div>
				<div class="article-time help-block">发表时间:${cid.releaseTime }</div>
				<div class="img-responsive mt20">
					<img src="${ctx}/download/imageDownload?url=${cid.bigImage}"/>
				</div>
			</c:forEach>
		</div>
		<div class="span12 mt20">
			<form novalidate="novalidate" method="get" id="pageform" action="${ctx}/portal/site/detail">
			<input type="hidden" id="pageNo" name="pageNo" value="${pageData.pageNo}" />
			<input type="hidden" id="c" name="c" value="${currentIndex}" />
			<div class="w1002">
	
				<a href="javascript:jumpPage(${pageData.prePage})" class="btn btn-large">上一页</a>
				<a href="javascript:jumpPage(${pageData.nextPage})" class="btn btn-large pull-right">下一页</a>
	
			</div>
		</form>
		</div>
	</div>


	<jsp:include page="./foot.jsp"></jsp:include>
</body>
</html>

