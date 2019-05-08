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
<link href="${ctx}/css/new/portal-index.css" rel="stylesheet" />

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
#scrollDivCargo .btn,#scrollDivCabin .btn{
	background-image: linear-gradient(to bottom, #e6e6e6, #e6e6e6);
}
</style>

</head>

<body>
	<!-- section header -->
	<jsp:include page="portal-head-index.jsp"></jsp:include>

	<nav class="breadcrumb" style="height: 16px"></nav>
	<!-- section content -->
	<div id="content" class="site-content">
		<div class="clear"></div>
		<!-- content-area -->
		<div id="primary" class="content-area">
			<!-- ship and cargo start -->
			<div class="line-one">
				<div class="row-fluid">
					<div class="span12 cat-box">
						<h3 class="cat-title">
							<a href="${ctx}/portal/home/cabinHome" target="_blank" title="最新船盘信息"><i
								class="fa fa-bars"></i>船盘</a><span class="more"><a
								href="${ctx }/portal/home/cabinHome" target="_blank">更多</a></span>
						</h3>
						<div class="clear"></div>
						<div class="cat-site" id="scrollDivCabin" style="margin:0px;">      
						  <c:forEach varStatus="status" var="cabinData" items="${cabinPage.result}">
			              <c:if test="${status.index != fn:length(cabinPage.result) - 1}">
			                <div class="one-line">
			              </c:if>
			              <c:if test="${status.index == fn:length(cabinPage.result) - 1}">
			                <div class="one-line no-border">
			              </c:if>
			                <div class="row-fluid">
			                    <div class="span2" style="overflow:hidden">
			                    	<a href="${ctx}/portal/home/cabinInfo?cabinId=${cabinData.id}&selPortNos=<c:if test="${cabinData.waresBigType eq 'voyage' }">${cabinData.currSailLineData.sailLineNo}</c:if>
			                    	" target="_blank"> 
			                          <c:choose>
			                            <c:when test="${!empty cabinData.shipData.shipLogo}">
			                              <img
			                                src="${ctx}/download/imageDownload?url=${cabinData.shipData.shipLogo}"
			                                style="margin-top:10px;width: 80px; height: 60px;" alt=""
			                                class="thumbnail" />
			                            </c:when>
			                            <c:otherwise>
			                              <img
			                                src="${ctx}/img/shipImage/${cabinData.shipData.shipType}.jpg"
			                                style="margin-top:10px;width: 80px; height: 60px;" alt=""
			                                class="thumbnail" />
			                            </c:otherwise>
			                          </c:choose>
			                        </a>
			                        <p>${cabinData.shipData.shipName}</p>
			                    </div>
			                    <c:if test="${cabinData.waresBigType eq 'voyage' }"> <!-- 航租 -->
								<div class="one-line-info span10 fa-pull-right" style="padding-top:5px;">
				                    <div class="one-line-title row-fluid">
				                        <div class="span10 adj-height-40" style="line-height:30px;">
				                        <a href="${ctx }/portal/home/cabinInfo?cabinId=${cabinData.id}&selPortNos=${cabinData.currSailLineData.sailLineNo}">
				                         <span style="color:#1c9b9a;font-weight:bold">${cabinData.currSailLineData.startPortData.fullName}</span><img width="61" height="14" class="startimg" src="${ctx}/img/indImg.png"><span style="color:#1c9b9a;font-weight:bold">${cabinData.currSailLineData.endPortData.fullName},里程:${cabinData.currSailLineData.distance}公里,载重量:${cabinData.currSailLineData.weight}吨</span>
				                        </a>
				                        </div>
				                        <div class="span2 to-right adj-height-40" style="line-height:30px;"><a href="${ctx }/space/orderCommon/orderEdit?cabinId=${cabinData.id }&selPortNos=${cabinData.currSailLineData.sailLineNo}" class="btn btn-infomation"  target="_blank">下订单</a></div>
				                    </div>
									<div class="row-fluid" style="line-height:40px">
										<span class="span4">
										<fmt:formatNumber var="fmtDemurrage" value="${cabinData.demurrage}" pattern="#.##"/>
										<c:if test="${cabinData.waresType != 'nmszh'}">
				                        滞期费:${fmtDemurrage}元/船.天
			                            </c:if>
			                            <c:if test="${cabinData.waresType == 'nmszh'}">
				                        滞期费:${fmtDemurrage}元/吨.天
				                        </c:if>
										</span>
										<span class="span4" style="text-align:center">
										<c:if test="${cabinData.waresType eq 'nmszh' }"> 
										载货量：${cabinData.currSailLineData.weight}吨
										</c:if>
										<c:if test="${cabinData.waresType eq 'gaxjzx' }"> 
										载箱量：${cabinData.containerCount}TEU
										</c:if>
										<c:if test="${cabinData.waresType eq 'nmjzx' }"> 
										载箱量：${cabinData.containerCount}TEU
										</c:if>
										</span>
										<span class="span4" style="text-align:right">受载日期：${cabinData.startDate}</span>
									</div>
									<div class="row-fluid">
			                          <c:choose>
			                            <c:when test="${cabinData.waresType eq 'nmszh' }">
			                              <span class="span12" style="color:red;font-weight:bold">航租报价：
												<c:forEach varStatus="status" var="mapPrice" items="${cabinData.currSailLineData.mapPrices}">
												${mapPrice.value}元/吨
												</c:forEach>	
										  </span>
			                            </c:when>
			                            <c:otherwise>
			                             <table style="width:100%;color:red">
											<tr>
												<td rowspan="2" style="font-weight:bold">航租报价<br/>元/个</td>
												<c:forEach varStatus="status" var="containerCode" items="${containerCodes}">
												<td>${containerCode.shortName}</td>
												</c:forEach>	
											</tr>
											<tr>
												<c:forEach varStatus="status" var="mapPrice" items="${cabinData.currSailLineData.mapPrices}">
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
			                            </c:otherwise>
			                          </c:choose>
										
									</div> 
			                    </div>
			                    </c:if>
	
			                    <c:if test="${cabinData.waresBigType eq 'daily' }"><!-- 期租 -->
								<div class="one-line-info span10 fa-pull-right" style="padding-top:5px;">
				                    <div class="one-line-title row-fluid">
				                        <div class="span10 adj-height-40" style="line-height:30px;">
				                        <a href="${ctx }/portal/home/cabinInfo?cabinId=${cabinData.id}&selPortNos=">
				                        	<span class="span12" style="color:#1c9b9a;font-weight:bold;line-height:40px">
											<c:forEach items="${cabinData.upDownPortDatas}" var="upDownPortData" varStatus="status">
												<c:if test="${status.index lt 2}">
						                            <c:if test="${upDownPortData.gotoThisPort}">
						                            ${upDownPortData.startPortData.fullName} : 载重量${upDownPortData.weight}吨,
						                            </c:if>
					                            </c:if>
					                        </c:forEach>
					                        ......
											</span>
				                        </a>
				                        </div>
				                        <div class="span2 to-right adj-height-40" style="line-height:30px;"><a href="${ctx }/space/orderCommon/orderEdit?cabinId=${cabinData.id }" class="btn btn-infomation"  target="_blank">下订单</a></div>
				                    </div>
				                   
									<div class="row-fluid" style="line-height:40px">
									  <c:choose>
			                            <c:when test="${cabinData.waresType eq 'nmszh' }">
										<span class="span6">燃油费：${cabinData.oilPrice}元/公里</span>
										<span class="span6" style="text-align:right">受载日期：${cabinData.startDate}</span>
			                            </c:when>
			                            <c:otherwise>
			                            <span class="span4">载箱量:${cabinData.containerCount}TEU</span>
										<span class="span4" style="text-align:center">燃油费：${cabinData.oilPrice}元/公里</span>
										<span class="span4" style="text-align:right">受载日期：${cabinData.startDate}</span>
			                            </c:otherwise>
			                          </c:choose>
									 </div>
									 <div class="row-fluid" > <span style="color:red;font-weight:bold">日租报价:${cabinData.prices}元/天</span></div>
			                    </div>
			                    </c:if>
			                </div>
			              </div>
			              <div class="clear"></div>
			              </c:forEach>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12 cat-box">
						<h3 class="cat-title">
							<a href="${ctx}/portal/home/cargoListx" target="_blank" title="最新货盘信息"><i
								class="fa fa-bars"></i>货盘</a><span class="more"><a
								href="${ctx }/portal/home/cargoListx" target="_blank">更多</a></span>
						</h3>
						<div class="clear"></div>
						<div class="cat-site" id="scrollDivCargo" style="margin:0px">
						 <c:forEach varStatus="status" var="cargoData" items="${cargoPage.result}">
			              <c:if test="${status.index != fn:length(cargoPage.result) - 1}">
			                <div class="one-line">
			              </c:if>
			              <c:if test="${status.index == fn:length(cargoPage.result) - 1}">
			                <div class="one-line no-border">
			              </c:if>
			                <div class="row-fluid">
			                	<div class="span2" style="overflow:hidden" >
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
			                                style="margin-top:10px;	width: 80px; height: 60px;" alt=""
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
			</div>
			<!-- ship and cargo end -->
			<!-- agent start -->
			<div class="line-one" id="agentList" style="display:none">
				<div class="row-fluid">
					<div class="span12 cat-box">
					<h3 class="cat-title">
					  <a href="http://${operatorData.userData.loginName}.eyd98.com/portal/site/index?c=GSJJ" title="金牌代理">
					    <i class="fa fa-bars"></i>${operatorData.userData.trueName}</a>
					  <span class="more">
					    <a href="http://${operatorData.userData.loginName}.eyd98.com/portal/site/index?c=GSJJ">更多</a>
					  </span>
					</h3>

					<div class="clear"></div>

					<article class="post user-card-container">
						<c:forEach varStatus="status" items="${operatorData.handlerDatas}" var="handlerData">
						<div class="user-card-small">
							<a href="${ctx}/space/chat/show?toUserId=${handlerData.id}" target="_blank">
								<div class="uhead <c:if test='${handlerData.onlineStatus == "ofline"}'>opacity</c:if>">
								  <img src="${ctx}/download/imageDownload?url=${handlerData.userLogo}"></div>
								<div class="name">${handlerData.trueName}</div>
								<div class="phone">${handlerData.mobile}</div>
							</a>
						</div>
						</c:forEach>
					</article>

					</div>
				</div>
			</div>
			<!-- agent end -->
		</div>
		<!-- content end -->

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
	<script src="${ctx}/js/jquery.textSlider.js"></script>
	<script src="${ctx}/js/new/portal-index.js"></script>
</body>
</html>