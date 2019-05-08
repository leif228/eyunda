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
#primary .nav-tabs{
	border-bottom:0px;
}
.clear {clear:both;font-size:0;height:0;line-height:0;overflow:hidden;}  
.clearfix:after {clear:both;content:".";display:block;font-size:0;height:0;visibility:hidden;}  
.clearfix{zoom:1;}  
img.startimg{
	width:61px;
	height:14px;
}
.startimg{
	padding:5px 10px 0px 10px;
}
#primary .btn{
	background-image: linear-gradient(to bottom, #e6e6e6, #e6e6e6);;
}
</style>

</head>

<body>
	<!-- section header -->
	<jsp:include page="portal-head.jsp"></jsp:include>
	<nav class="breadcrumb" style="margin-bottom:10px;">
		<i class="fa-television fa"></i>
		<a class="crumbs" href="${ctx}/portal/home/shipHome">首页</a>
		<i class="fa fa-angle-right"></i>
		<a class="crumbs" href="${ctx}/portal/home/cabinHome">船盘</a>
		<i class="fa fa-angle-right"></i>
		${title}
	</nav>
	<!-- section content -->
	<div id="content" class="site-content">
		<div class="clear"></div>
		<!-- content-area -->
		<div id="primary" class="content-area">
			<div class="line-one">
				<div class="cat-box">
					<h3 class="cat-title">
						<a href="#"><i class="fa fa-bars"></i>${title}</a>
						<span style="float:right" id="changeList">
						<ul class="nav nav-tabs">
							<li class="dropdown">
			                	<a class="dropdown-toggle" data-toggle="dropdown" href="#">
									<c:choose> 
									  <c:when test="${waresBigType eq 'voyage'}">   
										航租
									  </c:when> 
									  <c:when test="${waresBigType eq 'daily'}">   
									   期租
									  </c:when> 
									  <c:otherwise>   
										全部
									  </c:otherwise> 
									</c:choose> 
									<b class="caret"></b>
								</a>
				                <ul class="dropdown-menu">
				                  <li><a href="${url_no_waresBigType }&waresBigType=">全部</a></li>
				                  <li><a href="${url_no_waresBigType }&waresBigType=voyage">航租</a></li>
				                  <li><a href="${url_no_waresBigType }&waresBigType=daily">期租</a></li>
				                </ul>
			              </li>
			              <c:choose> 
							  <c:when test="${waresBigType eq 'voyage'}">   
					              <c:if test="${fn:length(sailLineDatas) gt 0}">
					              <li class="dropdown">
						                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
						                  <c:set var="flag" value="0" />
										  <c:forEach varStatus="status" var="sailLineData" items="${sailLineDatas}">
						                  <c:if test="${sailLineData.sailLineNo eq selPortNos}">
						                  	${sailLineData.startPortData.fullName}-${sailLineData.endPortData.fullName}
						                  	<c:set var="flag" value="1" />
						                  </c:if>
						                  </c:forEach>
						                  <c:if test="${flag eq 0 }">
						                  	全部
						                  </c:if>
						                <b class="caret"></b>
						                </a>
						                <ul class="dropdown-menu">
						                  <c:forEach varStatus="status" var="sailLineData" items="${sailLineDatas}">
						                  <li><a href="${url_no_selPortNos }&selPortNos=${sailLineData.sailLineNo }">${sailLineData.startPortData.fullName}-${sailLineData.endPortData.fullName}</a></li>
						                  </c:forEach>
						                </ul>
					              </li>
					              </c:if>
							  </c:when> 
							  <c:when test="${waresBigType eq 'daily'}">   
					              <c:if test="${fn:length(upDownPortDates) gt 0}">
					              <li class="dropdown">
						                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
						                  <c:set var="flag" value="0" />
										  <c:forEach varStatus="status" var="upDownPortDate" items="${upDownPortDates}">
						                  <c:if test="${upDownPortDate.startPortNo eq selPortNos}">
						                  	${upDownPortDate.startPortData.fullName}
						                  	<c:set var="flag" value="1" />
						                  </c:if>
						                  </c:forEach>
						                  <c:if test="${flag eq 0 }">
						                  	全部
						                  </c:if>
						                <b class="caret"></b>
						                </a>
						                <ul class="dropdown-menu">
						                  <c:forEach varStatus="status" var="upDownPortDate" items="${upDownPortDates}">
						                  <li><a href="${url_no_selPortNos }&selPortNos=${upDownPortDate.startPortNo }">${upDownPortDate.startPortData.fullName}</a></li>
						                  </c:forEach>
						                </ul>
					              </li>
					              </c:if>
							  </c:when> 
							  <c:otherwise>   
							   <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">
								全部
								</a></li>
							  </c:otherwise> 
						  </c:choose>
			            </ul>
                    	</span>
					</h3>
					<div class="clear"></div>
					<div class="cat-site" style="margin:0px;">      
						  <c:forEach varStatus="status" var="cabinData" items="${pageData.result}">
			              <c:if test="${status.index != fn:length(pageData.result) - 1}">
			                <div class="one-line">
			              </c:if>
			              <c:if test="${status.index == fn:length(pageData.result) - 1}">
			                <div class="one-line no-border">
			              </c:if>

			                <div class="row-fluid">
			                    <div class="span2" style="overflow:hidden">
			                    	<a href="${ctx}/portal/home/cabinInfo?cabinId=${cabinData.id}&selPortNos=<c:if test="${cabinData.waresBigType eq 'voyage' }">${cabinData.currSailLineData.sailLineNo}</c:if>
			                    	" target="_blank"> 
			                          <c:choose>
			                            <c:when test="${!empty cabinData.shipData.shipLogo}">
			                              <img title="${cabinData.shipData.shipName} - ${cabinData.description}"
			                                src="${ctx}/download/imageDownload?url=${cabinData.shipData.shipLogo}"
			                                style="margin-top:10px;width: 80px; height: 60px;" alt=""
			                                class="thumbnail" />
			                            </c:when>
			                            <c:otherwise>
			                              <img title="${cabinData.shipData.shipName} - ${cabinData.description}"
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
					<div class="clear"></div>
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