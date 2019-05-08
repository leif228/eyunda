<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>易运达 - 首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="guoqiang" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/common.css" rel="stylesheet" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>

<script type="text/javascript">
	var _rootPath = "${ctx}";
</script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->
<style>
#scrollDivCargo .btn{
	background-image: linear-gradient(to bottom, #e6e6e6, #e6e6e6);
}
</style>

</head>

<body>
	<!-- section header -->
	<jsp:include page="portal-head-cargo.jsp"></jsp:include>
	<nav class="breadcrumb" style="margin-bottom:10px;background: transparent;">
		<i class="fa-television fa"></i>
		<a class="crumbs" title="返回首页" href="${ctx }/portal/home/shipHome">首页</a>
		<i class="fa fa-angle-right"></i>
		货盘
	</nav>
	<!-- section content -->
	<div id="content" class="site-content">
		<div class="clear"></div>
		<!-- content-area -->
		<div id="primary" class="content-area">
			<div class="line-one">
				<div class="cat-box">
					<h3 class="cat-title">
						<a href="###" title="最新货物信息"><i class="fa fa-bars"></i>货盘</a>
					</h3>
					<div class="clear"></div>
					<div class="cat-site" id="scrollDivCargo" style="margin:0px">
					 <c:forEach varStatus="status" var="cargoData" items="${pageData.result}">
		              <c:if test="${status.index != fn:length(pageData.result) - 1}">
		                <div class="one-line">
		              </c:if>
		              <c:if test="${status.index == fn:length(pageData.result) - 1}">
		                <div class="one-line no-border">
		              </c:if>
		                <div class="row-fluid">
		                	<div class="span2" style="overflow:hidden">
		                    	<a href="${ctx}/portal/home/cargoInfo?cargoId=${cargoData.id}" target="_blank"> 
		                          <c:choose>
		                            <c:when test="${!empty cargoData.cargoImage}">
		                              <img
		                                src="${ctx}/download/imageDownload?url=${cargoData.cargoImage}"
		                                style="margin-top:10px;width: 80px; height: 60px;" alt=""
		                                class="thumbnail" />
		                            </c:when>
		                            <c:otherwise>
		                              <img
		                                src="${ctx}/img/cargo/${cargoData.cargoType}.jpg"
		                                style="margin-top:10px;width: 80px; height: 60px;" alt=""
		                                class="thumbnail" />
		                            </c:otherwise>
		                          </c:choose>
		                        </a>
		                    </div>
		                    <div class="one-line-info span10 fa-pull-right" style="padding-top:5px;">
			                    <div class="one-line-title row-fluid">
			                        <div class="span10 adj-height-40" style="line-height:30px;">
			                        <a href="${ctx }/portal/home/cargoInfo?cargoId=${cargoData.id}">
			                         <span style="color:#1c9b9a;font-weight:bold">${cargoData.startPortData.fullName}</span><img width="61" height="14" class="startimg" src="${ctx}/img/indImg.png"><span style="color:#1c9b9a;font-weight:bold">${cargoData.endPortData.fullName},总运价：${cargoData.transFee }</span>
			                        </a>
			                        </div>
			                        <div class="span2 to-right adj-height-40" style="line-height:30px;">
			                        </div>
			                    	
			                    </div>
			                    <c:choose>
		                            <c:when test="${cargoData.cargoType == 'container20e'}">
		                             <div class="row-fluid" style="margin-top:10px">
		                             	<table style="width:100%;color:red">
											<tr>
												<td style="font-weight:bold">规格</td>
												<c:forEach varStatus="status" var="mapCargoName" items="${cargoData.mapCargoNames}">
												<td>${mapCargoName.value}</td>
												</c:forEach>	
											</tr>
											<tr>
												<td style="font-weight:bold">货量(个)</td>
												<c:forEach varStatus="status" var="mapTonTeu" items="${cargoData.mapTonTeus}">
												<td>
													<c:choose>
			                            				<c:when test="${mapTonTeu.value gt 0 }">
															<fmt:formatNumber var="mapValue" value="${mapTonTeu.value}" pattern="#"/>
															${mapValue}
														</c:when>
														<c:otherwise>
														- -
														</c:otherwise>
													</c:choose>
												</td>
												</c:forEach>	
											</tr>
											<tr>
												<td style="font-weight:bold">报价(元/个)</td>
												<c:forEach varStatus="status" var="mapPrice" items="${cargoData.mapPrices}">
												<td>
													<c:choose>
			                            				<c:when test="${mapPrice.value gt 0 }">
															<fmt:formatNumber var="mapValue" value="${mapPrice.value}" pattern="#.##"/>
															${mapValue}
														</c:when>
														<c:otherwise>
														- -
														</c:otherwise>
													</c:choose>
												</td>
												</c:forEach>	
											</tr>
										</table>
		                             </div>
		                            </c:when>
		                            <c:otherwise>
		                                <div class="row-fluid" style="line-height:60px">
											<span class="span4">
											货名：${cargoData.cargoNames}
											</span>
											<span class="span4" style="text-align:center">
											货量：${cargoData.tonTeus}吨
											</span>
											<span class="span4" style="text-align:right">报价：${cargoData.prices}元/吨</span>
										</div>
		                            </c:otherwise>
		                          </c:choose>
			                 </div>
		                </div>
		                </div>
		             </c:forEach>
		             <div class="clear"></div>
					</div>
				</div>
			</div>
			<jsp:include page="pager.jsp"></jsp:include>
		</div>
		<!-- widget-area -->
		<div id="sidebar" class="widget-area">
			<jsp:include page="./notice.jsp"></jsp:include>

			<jsp:include page="./customerDownload.jsp"></jsp:include>
		</div>
		<div class="clear"></div>
	</div>
	<!-- section footer -->
	<jsp:include page="portal-foot.jsp"></jsp:include>
	<!-- javascript
    ================================================== -->
	<script src="${ctx}/js/jquery.divSelect.js"></script>
</body>
</html>