<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>易运达 - 首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="guoqiang" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/common.css" rel="stylesheet" />
<link href="${ctx}/css/page-tabs.css" rel="stylesheet" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>

<script src="${ctx}/js/jquery.colorbox-min.js"></script>
<script src="${ctx}/js/space/space-ship-monitor-currRoutePlay.js"></script>

<script
	src="http://api.map.baidu.com/api?v=2.0&ak=3dj1jxWYQCN2UCG8MhCPVoYB"
	type="text/javascript"></script>
<script src="${ctx}/js/space/space-monitor-currLushu.js"></script>
<script type="text/javascript">
	var _rootPath = "${ctx}";
	var _points = [];
	var map;
	var _pointArray = new Array();
	var _shipName = "${shipData.shipName}";
	var _mmsi = "${shipData.mmsi}";

	<c:forEach var="shipCooordData" items="${shipCooordDatas}">
	_points.push([ "${shipCooordData.longitude}", "${shipCooordData.latitude}",
			"${shipCooordData.posTime}" ]);
	</c:forEach>
</script>

<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->
<style>
.nav-links a.page-numbers {
  height: 32px;
  width: 32px;
  padding-left: 10px;
  line-height: 30px;
}
.btn-item{
	margin:5px 10px;
}
.btn-item .first{
	margin:5px 10px 5px 0px;
}

#dlgAdd .user-info {
	list-style: none;
}

#dlgAdd .account-container {
	padding: 3px;
}

#dlgAdd .user-info>li {
	float: left;
	margin: 10px;
}

#dlgAdd .account-container:hover {
	padding: 3px;
	background: #00CCFF;
	cursor: pointer
}

.addBack {
	background: #00CCFF;
}

#container {
	height: 472px;
	margin-top:-25px;
}

div {
	font-size: 12px;
}

button {
	margin-top: 4px;
}

#imageResult {
	position: fixed;
	margin-top: -475px;
	margin-left: 846px;
	display: none;
}

.close {
	cursor: pointer;
	float: left;
	margin-left: -1px;
}
.tab-info section{
	padding: 1em 0em;
}
</style>

</head>

<body>
  <!-- section content -->
  <div id="content" class="site-content">
    <div class="clear"></div>
    <!-- content-area -->
    <div id="primary" class="content-area">
      <div class="line-one">
        <div class="cat-box">
          <div class="cat-title" style="font-weight:bold">
            <a href="#" title="${myShipData.shipName} - ${cabinData.description}"><i
              class="fa fa-bars"></i>${myShipData.shipName} - ${cabinData.description}</a>
          </div>
          <div class="clear"></div>
          <div class="row-fluid cat-site">
            <div class="span2"
              style="min-height: 100px; text-align: center; margin: 0 auto">
              <img
                src="${ctx}/download/imageDownload?url=${myShipData.shipLogo}"
                style="width: 100px; height: 100px;" alt="" class="thumbnail">
              <!-- <input type="button" value="收藏" style="margin-top: 9px;"
                id="loadFavoriteContent"> -->
            </div>
            <div class="span10" >
              <div id="tabs">
                <div class="tab-info">
                	<section id="section-9" class="content-current">
                      <div class="mediabox">
                      <div class="one-line">
			                <div class="row-fluid">
			                    <c:if test="${cabinData.waresBigType eq 'voyage' }"> <!-- 航租 -->
								<div class="one-line-info span12 fa-pull-right">
				                    <div class="one-line-title row-fluid">
				                        <div class="span10 adj-height-40" style="line-height:30px;">
				                        <a href="${ctx }/portal/home/cabinInfo?cabinId=${cabinData.id}&selPortNos=${cabinData.currSailLineData.sailLineNo}">
				                         <span style="color:#1c9b9a;font-weight:bold">${cabinData.currSailLineData.startPortData.fullName}</span><img width="61" height="14" class="startimg" src="${ctx}/img/indImg.png"><span style="color:#1c9b9a;font-weight:bold">${cabinData.currSailLineData.endPortData.fullName},里程:${cabinData.currSailLineData.distance}公里,载重量:${cabinData.currSailLineData.weight}吨</span>
				                        </a>
				                        </div>
				                    </div>
									<div class="row-fluid" style="line-height:40px">
										<span class="span12">
										<fmt:formatNumber var="fmtDemurrage" value="${cabinData.demurrage}" pattern="#.##"/>
										<c:if test="${cabinData.waresType != 'nmszh'}">
				                        滞期费:${fmtDemurrage}元/船.天
			                            </c:if>
			                            <c:if test="${cabinData.waresType == 'nmszh'}">
				                        滞期费:${fmtDemurrage}元/吨.天
				                        </c:if>
										, 
										<c:if test="${cabinData.waresType eq 'nmszh' }"> 
										载货量：${cabinData.currSailLineData.weight}吨
										</c:if>
										<c:if test="${cabinData.waresType eq 'gaxjzx' }"> 
										载箱量：${cabinData.containerCount}TEU
										</c:if>
										<c:if test="${cabinData.waresType eq 'nmjzx' }"> 
										载箱量：${cabinData.containerCount}TEU
										</c:if>
										, 受载日期：${cabinData.startDate}</span>
									</div>
									<div class="row-fluid">
			                          <c:choose>
			                            <c:when test="${cabinData.waresType eq 'nmszh' }">
			                              <span class="span12" style="color:red;font-weight:bold">航租报价：
												<c:forEach varStatus="status" var="mapPrice" items="${cabinData.currSailLineData.mapPrices}">
												${mapPrice.value}元／吨
												</c:forEach>	
										  </span>
			                            </c:when>
			                            <c:otherwise>
			                             <table style="width:100%;color:red">
											<tr>
												<td rowspan="2" style="font-weight:bold">报价<br/>元/个</td>
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
								<div class="one-line-info span12 fa-pull-right">
				                    <div class="one-line-title row-fluid">
				                        <div class="span10 adj-height-40" style="line-height:30px;">
				                        <a href="${ctx }/portal/home/cabinInfo?cabinId=${cabinData.id}&selPortNos=">
				                        	<span class="span12" style="color:#1c9b9a;font-weight:bold;line-height:40px">
				                        	<c:set var="_count" value="0" />
											<c:forEach items="${cabinData.upDownPortDatas}" var="upDownPortData" varStatus="status">
					                            <c:if test="${upDownPortData.gotoThisPort && _count < 2}">
					                            <c:set var="_count" value="${_count+1}" />
					                            ${upDownPortData.startPortData.fullName} : 载重量${upDownPortData.weight}吨,
					                            </c:if>
					                        </c:forEach>
					                        ......
											</span>
				                        </a>
				                        </div>
				                    </div>
				                   
									<div class="row-fluid" style="line-height:40px">
									  <c:choose>
			                            <c:when test="${cabinData.waresType eq 'nmszh' }">
										<span class="span12">燃油费：${cabinData.oilPrice}元/公里, 受载日期：${cabinData.startDate}</span>
			                            </c:when>
			                            <c:otherwise>
			                            <span class="span12">载箱量:${cabinData.containerCount}TEU, 燃油费：${cabinData.oilPrice}元/公里, 受载日期：${cabinData.startDate}</span>
			                            </c:otherwise>
			                          </c:choose>
									 </div>
									 <div class="row-fluid" > <span style="color:red;font-weight:bold">日租报价:${cabinData.prices}元/天</span></div>
			                    </div>
			                    </c:if>
			                </div>
			              </div>
			              <div class="clear"></div>
                      </div>
                  </section>
				  <section class="content-current">
				     <input type="hidden" name="shipId" id="shipId" value="${myShipData.id}" />
					 <div id="container" class="widget-content"></div>
				  </section>
				  <section id="section-3"  class="content-current">
                    <div class="mediabox">
						<table style="width: 100%">
                        <tbody>
                          <tr>
                            <td colspan="7" class="bold" style="font-size: 16px; text-align: center;">
                            <c:if test="${cabinData.waresBigType == 'voyage'}">
                              航线报价
                            </c:if>
                            <c:if test="${cabinData.waresBigType == 'daily'}">
                              装卸港口
                            </c:if>
                            </td>
                          </tr>
	                        <c:if test="${cabinData.waresBigType == 'voyage'}">
	                        <c:if test="${cabinData.waresType != 'nmszh'}">                            
	                        <c:forEach items="${cabinData.sailLineDatas}" var="sailLineData">
	                        <c:if test="${sailLineData.gotoThisLine}">
	                        <tr>	                        	
	                            <td colspan="7" style="font-weight:bold;">
	                              ${sailLineData.startPortData.fullName} - ${sailLineData.endPortData.fullName},
	                              里程：${sailLineData.distance}公里,
	                              载重量：${sailLineData. weight}吨
	                            </td>
	                        </tr>
	                        <tr>
	                        	<td rowspan="2" style="font-weight:bold;vertical-align:middle;">报价<br/>元/个</td>
								<c:forEach varStatus="status" var="containerCode" items="${containerCodes}">
								<td>${containerCode.shortName}</td>
								</c:forEach>	
							</tr>
							<tr>
								<c:forEach varStatus="status" var="mapPrice" items="${sailLineData.mapPrices}">
								<td style="color:red">
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
							</c:if>
	                        </c:forEach>
                            </c:if>
                            <c:if test="${cabinData.waresType == 'nmszh'}"><!-- 散杂货 -->
							<c:forEach items="${cabinData.sailLineDatas}" var="sailLineData">
							<c:if test="${sailLineData.gotoThisLine}">
							<tr><td colspan="3" style="font-weight:bold;">${sailLineData.startPortData.fullName} - ${sailLineData.endPortData.fullName}</td></tr>
							<tr>
							<td style="color:red;font-weight:bold">
							报价：
								<c:forEach varStatus="status" var="mapPrice" items="${sailLineData.mapPrices}">
								${mapPrice.value}元/吨
								</c:forEach>	
							</td><td>里程：${sailLineData.distance}公里</td><td>载重量：${sailLineData. weight}吨</td></tr>
							</c:if>
	                        </c:forEach>
	                        </c:if>
	                        </c:if>
	
	                        <c:if test="${cabinData.waresBigType == 'daily'}">

	                        <c:forEach items="${cabinData.upDownPortDatas}" var="upDownPortData">
	                            <c:if test="${upDownPortData.gotoThisPort}">
	                            <tr><td >
	                            ${upDownPortData.startPortData.fullName} 
	                            </td>
	                            <td> 载重量：${upDownPortData.weight}吨
	                            </td></tr>
	                            </c:if>
	                        </c:forEach>
	                       
	                        </c:if>
                        </tbody>
                      </table>
                    </div>
                  </section>
                  
                  <section id="section-2" class="content-current">
					<div class="mediabox">
                      <table style="width:100%">
                        <tbody>
                          <tr>
                            <td colspan="4" class="bold"
                              style="font-size: 16px; text-align: center;">船舶规范</td>
                          </tr>

                          <tr>
                            <td style="background: #eeeeee;" class="bold">承运人:</td>
                            <td>
                              <c:if test="${!empty contact}">
                              <a href="${ctx}/space/chat/show?toUserId=${contact.id}&shipId=${myShipData.id}" target="mychat"> 
                                <c:choose>
                                  <c:when test="${!empty contact.userLogo}">
                                    <div
                                      class="uhead <c:if test='${contact.onlineStatus == "ofline"}'>opacity</c:if>">
                                      <img
                                        src="${ctx}/download/imageDownload?url=${contact.userLogo}"
                                        style="width: 32px; height: 32px;" alt=""
                                        class="thumbnail" />
                                    </div>
                                  </c:when>
                                  <c:otherwise>
                                    <div
                                      class="uhead <c:if test='${contact.onlineStatus == "ofline"}'>opacity</c:if>">
                                      <img src="${ctx}/img/user.jpg"
                                        style="width: 32px; height: 32px;" alt=""
                                        class="thumbnail" />
                                    </div>
                                  </c:otherwise>
                                </c:choose>${contact.trueName}<br />${contact.mobile}
                              </a>
                              </c:if>
                            </td>
                            <td style="background: #eeeeee;" class="bold">船舶名称:</td>
                            <td>${myShipData.shipName}</td>
                          </tr>
                          <tr>
                            <td style="background: #eeeeee;" class="bold">船类:</td>
                            <td>${myShipData.typeData.typeName}</td>
                            <td style="background: #eeeeee;" class="bold">MMSI编号:</td>
                            <td>${myShipData.mmsi}</td>
                          </tr>
                          <tr>
                            <td style="background: #eeeeee;" class="bold">船长(米):</td>
                            <td>${myShipData.length}</td>
                            <td style="background: #eeeeee;" class="bold">船宽(米):</td>
                            <td>${myShipData.breadth}</td>
                          </tr>
                          <tr>
                            <td style="background: #eeeeee;" class="bold">型深(米):</td>
                            <td>${myShipData.mouldedDepth}</td>
                            <td style="background: #eeeeee;" class="bold">吃水深度(米):</td>
                            <td>${myShipData.draught}</td>
                          </tr>
                          <tr>
                            <td style="background: #eeeeee;" class="bold">总吨(吨):</td>
                            <td>${myShipData.sumTons}</td>
                            <td style="background: #eeeeee;" class="bold">净吨(吨):</td>
                            <td>${myShipData.cleanTons}</td>
                          </tr>
                          <tr>
                            <td style="background: #eeeeee;" class="bold">载箱量:</td>
                            <td>
                              重箱:${myShipData.fullContainer}TEU<br />
                              半重箱:${myShipData.halfContainer}TEU<br />
                              吉箱:${myShipData.spaceContainer}TEU
                            </td>
                            <td style="background: #eeeeee;" class="bold">载重量:</td>
                            <td>
                              载重A级:${myShipData.aTons}吨<br />
                              载重A级:${myShipData.bTons}吨
                            </td>
                          </tr>

                          <c:forEach var="attrNameData"
                            items="${myShipData.attrNameDatas}"
                            varStatus="attrNameStatus">

                            <c:if test="${attrNameStatus.count%2 != 0 }">
                              <tr>
                            </c:if>

                            <c:if test="${attrNameData.attrType == 'booltype'}">
                              <td style="background: #eeeeee;" class="bold">${attrNameData.attrName}：</td>
                              <td><c:forEach var="attrValueData"
                                  items="${attrNameData.attrValueDatas}">
                                  <c:if
                                    test="${attrNameData.currAttrValue.attrValue == attrValueData.attrValueCode }">
                              ${attrValueData.attrValue}
                            </c:if>
                                </c:forEach></td>
                            </c:if>

                            <c:if test="${attrNameData.attrType == 'datetype'}">
                              <td style="background: #eeeeee;" class="bold">${attrNameData.attrName}：</td>
                              <td>${attrNameData.currAttrValue.attrValue}</td>
                            </c:if>

                            <c:if test="${attrNameData.attrType == 'dblnum' }">
                              <td style="background: #eeeeee;" class="bold">${attrNameData.attrName}：</td>
                              <td>${attrNameData.currAttrValue.attrValue}</td>
                            </c:if>

                            <c:if test="${attrNameData.attrType == 'intnum' }">
                              <td style="background: #eeeeee;" class="bold">${attrNameData.attrName}：</td>
                              <td>${attrNameData.currAttrValue.attrValue}</td>
                            </c:if>

                            <c:if test="${attrNameData.attrType == 'charstr'}">
                              <td style="background: #eeeeee;" class="bold">${attrNameData.attrName}：</td>
                              <td>${attrNameData.currAttrValue.attrValue}</td>
                            </c:if>

                            <c:if test="${attrNameData.attrType == 'charcode'}">
                              <td style="background: #eeeeee;" class="bold">${attrNameData.attrName}：</td>
                              <td><c:forEach var="attrValueData"
                                  items="${attrNameData.attrValueDatas}">
                                  <c:if
                                    test="${attrNameData.currAttrValue.attrValue == attrValueData.attrValueCode}">
                              ${attrValueData.attrValue}
                            </c:if>
                                </c:forEach></td>
                            </c:if>

                            <c:if test="${attrNameStatus.count%2 == 0 }">
                              </tr>
                            </c:if>

                            <c:if test="${attrNameStatus.last}">
                              <c:choose>
                                <c:when
                                  test="${fn:length(myShipData.attrNameDatas) == 1}">
                                  <td style="background: #eeeeee;"></td>
                                  <td></td>
                                  </tr>
                                </c:when>
                                <c:when
                                  test="${fn:length(myShipData.attrNameDatas)> 1 && fn:length(myShipData.attrNameDatas)%2 != 0}">
                                  <td style="background: #eeeeee;"></td>
                                  <td></td>
                                  </tr>
                                </c:when>
                                <c:when
                                  test="${fn:length(myShipData.attrNameDatas)> 1 && fn:length(myShipData.attrNameDatas)%2 == 0}">
                                </c:when>
                              </c:choose>
                            </c:if>
                          </c:forEach>
                        </tbody>
                      </table>
                    </div>
                  </section>
				  
                  <section id="section-4"  class="content-current">
                    <div class="mediabox">
						<table style="width: 100%">
                        <tbody>
                          <tr>
                            <td colspan="4" class="bold"
                              style="font-size: 16px; text-align: center;">合同模版</td>
                          </tr>
                          <tr>
                            <td colspan="4">
                            	${cabinData.orderDesc}
                            </td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </section>
                  <section id="section-4"  class="content-current">
                    <div class="mediabox">
						<table style="width: 100%">
                        <tbody>
                          <tr>
                            <td colspan="4" class="bold"
                              style="font-size: 16px; text-align: center;">用户评价</td>
                          </tr>
                          <tr>
                            <td colspan="4">
							<div class="fluid">
					          <c:forEach items="${evaluateDatas}" var="evaluate"
					            varStatus="getIndex">
					            <div class="row-fluid">
					              <!-- 头像 -->
					              <div class="span1 one-comment-logo">
					                <c:choose>
					                  <c:when test="${empty evaluate.userData.userLogo}">
					                  </c:when>
					                  <c:otherwise>
					                    <img
					                      src="${ctx}/download/imageDownload?url=${evaluate.userData.userLogo}" />
					                  </c:otherwise>
					                </c:choose>
					              </div>
					              <div class="span11 one-comment-detail">
					                <!-- 评价结果 -->
					                <p class="one-comment-detail-res">${evaluate.evalType.description}
					                  <span class="one-comment-time">${evaluate.createTime}</span>
					                </p>
					                <!-- 评价内容 -->
					                <p class="one-comment-detail-content">${evaluate.evalContent}</p>
					              </div>
					              <div class="clear"></div>
					            </div>
					            <c:if test="${getIndex.count%2==0}">
					        </div>
					        <!-- 间隔条 -->
					        <div class="divider-content">
					          <span></span>
					        </div>
					        <div class="fluid">
					          </c:if>
					          </c:forEach>
					        </div>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </section>
                </div>
                <!-- /content -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="clear"></div>
  </div>
</body>
</html>