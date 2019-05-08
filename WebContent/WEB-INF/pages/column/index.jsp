<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	
	function loadMore(){
		var columnCodes = [];
		var htmls = "";
		<c:forEach var="columnCode" items="${columnCodes}" varStatus="n">
			columnCodes.push({
				"columnCode" : "${columnCode}",
				"description" : "${columnCode.description}"
			});
  	 	</c:forEach>
		for(var i=0;i<columnCodes.length;i++){
			if(i==0||i==columnCodes.length-1)
				continue;
			
			$.ajax({
				method : "GET",
				url : _rootPath + "/portal/site/loadmore?c=" + columnCodes[i].columnCode,
				async : false,
				datatype : "json",
				success : function(data) {
					var returnCode = $(data)[0].returnCode;
					if (returnCode == "Failure") {
						return false;
					} else {
						htmls +='<div class="span3">';
						htmls +='<div class="one-block">';
						htmls +='<div class="block-title-container">';
						htmls +='<span class="pull-left block-title">'+columnCodes[i].description+'</span>';
						htmls +='<span class="pull-right more"><a href="'+_rootPath+'/portal/site/index?c='+columnCodes[i].columnCode+'">更多</a></span>';
						htmls +='<div class="clear"></div>';
						htmls +='</div>';
						htmls +='<div class="block-detail">';
						if("CGAL"==columnCodes[i].columnCode||"WXZL"==columnCodes[i].columnCode||"HTTW"==columnCodes[i].columnCode){
							if($(data)[0].pageData.result[0].title.length>=20)
								htmls +='<h4>'+$(data)[0].pageData.result[0].title.substring(0, 17)+'...</h4>';
							else
								htmls +='<h4>'+$(data)[0].pageData.result[0].title+'</h4>';
							if($(data)[0].pageData.result[0].content.length>=20)
								htmls +='<p><a href="'+_rootPath+'/portal/site/index?c='+columnCodes[i].columnCode+'">'+$(data)[0].pageData.result[0].content.substring(0, 17)+'...</a></p>';
							else
								htmls +='<p><a href="'+_rootPath+'/portal/site/index?c='+columnCodes[i].columnCode+'">'+$(data)[0].pageData.result[0].content+'</a></p>';
						}else{
							if($(data)[0].pageData.result[0].smallImage!='')
								htmls +='<img src="'+_rootPath+'/download/imageDownload?url='+$(data)[0].pageData.result[0].smallImage+'" class="pull-left block-img">';
							htmls +='<ul class="pull-left">';
							if($(data)[0].pageData.result[0].title.length>=10)
								htmls +='<li><a href="'+_rootPath+'/portal/site/index?c='+columnCodes[i].columnCode+'">'+$(data)[0].pageData.result[0].title.substring(0, 8)+'...</a></li>';
							else
								htmls +='<li><a href="'+_rootPath+'/portal/site/index?c='+columnCodes[i].columnCode+'">'+$(data)[0].pageData.result[0].title+'</a></li>';
							htmls +='</ul>';
						}
						htmls +='<div class="clear"></div></div></div></div>';
						$('#row').append(htmls);
						htmls = "";
						return true;
					}
				}
			});
		}
	}
</script>

<style>
.logo img {
	width: 100%;
}


</style>
</head>

<body onLoad="javascript:loadMore();">
	<jsp:include page="./head.jsp"></jsp:include>

	<div class="row">
		<div class="span12">
				<c:choose>
		             <c:when test="${!empty ciData.bigImage}">
						<div class="logo">
		                 <img src="${ctx}/download/imageDownload?url=${ciData.bigImage}" />
						</div>
		              </c:when>
		               <c:otherwise>
		                    <div></div>
		                </c:otherwise>
		          </c:choose>	
				
			<div class="info">${ciData.content }</div>

			<div class="index-content">
				<div class="row" id="row">
			<!-- 		<div class="span3">
						<div class="one-block">
							<div class="block-title-container">
								<span class="pull-left block-title">版块一</span> 
								<span class="pull-right more"><a href="">更多</a></span>
								<div class="clear"></div>
							</div>
							<div class="block-detail">
								<img src="" class="pull-left block-img">
								<ul class="pull-left">
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
								</ul>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<div class="span3">
						<div class="one-block">
							<div class="block-title-container">
								<span class="pull-left block-title">版块二</span> 
								<span class="pull-right more"><a href="">更多</a></span>
								<div class="clear"></div>
							</div>
							<div class="block-detail">
								<img src="" class="pull-left block-img">
								<ul class="pull-left">
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
								</ul><div class="clear"></div></div></div></div>
					<div class="span3">
						<div class="one-block">
							<div class="block-title-container">
								<span class="pull-left block-title">版块三</span> 
								<span class="pull-right more"><a href="">更多</a></span>
								<div class="clear"></div>
							</div>
							<div class="block-detail">
								<img src="" class="pull-left block-img">
								<ul class="pull-left">
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
								</ul>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<div class="span3">
						<div class="one-block">
							<div class="block-title-container">
								<span class="pull-left block-title">版块四</span> 
								<span class="pull-right more"><a href="">更多</a></span>
								<div class="clear"></div>
							</div>
							<div class="block-detail">
								<img src="" class="pull-left block-img">
								<ul class="pull-left">
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
									<li><a href="" target="_blank">消息消息消息消息消息框</a></li>
								</ul>
								<div class="clear"></div>
							</div>
						</div>
					</div> -->
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="./foot.jsp"></jsp:include>
</body>
</html>

