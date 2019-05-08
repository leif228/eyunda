<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>${userData.trueName}</title>
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
			<ul>
				<c:forEach items="${pageData.result}" var="cid" varStatus="s">
							          
					<li class="article-one">
						<div class="title"><a href="${ctx}/portal/site/detail?c=${currentIndex}&pageNo=${s.index+1}" ><span class="list-title">${cid.title }</span></a><span class="pull-right">${cid.releaseTime }</span></div>
						<div>
						<c:if test="${fn:length(cid.content) < 100}">${cid.content}</c:if>
		                <c:if test="${fn:length(cid.content) >= 100}">${fn:substring(cid.content,0,100)}...</c:if>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="row">
		<div class="span12">
			<form novalidate="novalidate" method="get" id="pageform" action="${ctx}/portal/site/index">
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

