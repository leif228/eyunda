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

.text1{
	text-align: left;
	margin-left:5%;
	margin-top:10px;
}

</style>
</head>

<body>
	<jsp:include page="./head.jsp"></jsp:include>

	<div class="row">
	  <div class="gallery">
	  	<c:forEach items="${pageData.result}" var="cid" varStatus="s">
						<c:if test="${s.index%4==0}">
							<div class="grid_3 controls-row">
						</c:if>
						<c:if test="${s.index%4!=0}">
							<div class="grid_3">
						</c:if>
								<div class="pull-left w40">
						          	<a href="${ctx}/portal/site/detail?c=${currentIndex}&pageNo=${s.index+1}"  class="gall_item">
										<c:choose>
		                                <c:when test="${!empty cid.smallImage}">
		                                  <img alt="" src="${ctx}/download/imageDownload?url=${cid.smallImage}"/>
		                                </c:when>
		                                <c:when test="${'CBXX'==currentIndex}">
		                                  <img alt="" src="${ctx}/img/shipImage/${cid.source}.jpg"/>
		                                </c:when>
		                                <c:when test="${'HWXX'==currentIndex}">
		                                  <img alt="" src="${ctx}/img/cargoImage/${cid.source}.jpg"/>
		                                </c:when>
		                                <c:otherwise>
		                                  <img alt="" src="${ctx}/img/example/mrbc-badge.png"/>
		                                </c:otherwise>
		                              </c:choose>			          		
						          	</a>
						          </div>
						          <div class="text1 pull-left w50"><p><a href="${ctx}/portal/site/detail?c=${currentIndex}&pageNo=${s.index+1}" >${cid.title }</a></p>
						          									<p class="mt15"><c:if test="${fn:length(cid.content) < 20}">${cid.content}</c:if>
	                      															<c:if test="${fn:length(cid.content) >= 20}">${fn:substring(cid.content,0,20)}...</c:if>
	                      											</p>
						          </div>
						          <div class="small-clear"></div>
							</div>
						<c:if test="${(s.index+1)%4==0}">
							<div class="clear sep__4"></div>
						</c:if>
		</c:forEach>
      </div>
	</div>
	<div class="row">
		<form novalidate="novalidate" method="get" id="pageform" action="${ctx}/portal/site/index">
		<input type="hidden" id="pageNo" name="pageNo" value="${pageData.pageNo}" />
		<input type="hidden" id="c" name="c" value="${currentIndex}" />
		<div class="w1002">

			<a href="javascript:jumpPage(${pageData.prePage})" class="btn btn-large">上一页</a>
			<a href="javascript:jumpPage(${pageData.nextPage})" class="btn btn-large pull-right">下一页</a>

		</div>
		</form>
	</div>

	<jsp:include page="./foot.jsp"></jsp:include>
</body>
</html>

