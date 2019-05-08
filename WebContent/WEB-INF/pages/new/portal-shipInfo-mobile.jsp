<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
          <h3 class="cat-title">
            <a href="###" title="${myShipData.shipName}(MMSI:${myShipData.mmsi})"><i
              class="fa fa-bars"></i>船盘详情</a>
          </h3>
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
              <div>
                <div class="tab-info" style="border:1px solid #ddd">
                  <section id="section-1" class="content-current">
                    <div class="mediabox">
                      <table style="width: 100%">
                        <tbody>
                          <tr>
                            <td colspan="4" class="bold"
                              style="font-size: 16px; text-align: center;">${myShipData.shipName}（${myShipData.mmsi}）</td>
                          </tr>
                          <tr>
                            <td style="background: #eeeeee; width: 80px;" class="bold">船舶动态:</td>
                            <td colspan="3"><c:choose>
                                <c:when test="${!empty myShipData.shipArvlftDatas}">
                                  <c:set var="datasLen"
                                    value="${fn:length(myShipData.shipArvlftDatas)}" />
                                ${myShipData.shipArvlftDatas[datasLen-1].arvlftDesc}
                              </c:when>
                                <c:otherwise>
                                未上报
                              </c:otherwise>
                              </c:choose></td>
                          </tr>
                          <tr>
                            <td style="background: #eeeeee;" class="bold">位置信息:</td>
                            <td colspan="3">时间:${shipCooorData.posTime}, <br />
                              经度:${shipCooorData.longitude}, 纬度:${shipCooorData.latitude}, <br />
                              航速:${shipCooorData.speed}节, 航向:${shipCooorData.course}度</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </section>
                  

                  <section id="section-2" class="content-current">
					<div class="mediabox">
					  <table style="width: 100%">
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
                            <td style="background: #eeeeee;" class="bold">载重(吨):</td>
                            <td>${myShipData.aTons}</td>
                            <td style="background: #eeeeee;" class="bold">载箱(TEU):</td>
                            <td>${myShipData.fullContainer}</td>
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

	              <section id="section-3" class="content-current">
	              <div class="mediabox">
					<table style="width: 100%">
                       <tbody>
                         <tr>
                           <td colspan="4" class="bold"
                             style="font-size: 16px; text-align: center;">船舶介绍</td>
                         </tr></tbody></table></div>
	                <div class="mediabox" style="padding-left: 0px;text-align: center;">
	                  <c:choose>
	                    <c:when
	                      test="${myShipData.myShipAttaDatas!=null && fn:length(myShipData.myShipAttaDatas)>0}">
	                      <c:forEach var="myShipAttaData" items="${myShipData.myShipAttaDatas}">
	                        <c:if test="${!empty myShipAttaData.url}">
	                          <div class="detail-descrip-info" style="text-align: center;">
	                            <div>
	                              <img src="${ctx}/download/imageDownload?url=${myShipAttaData.url}" width="100%" />
	                            </div>
	                          </div>
	                          <c:if test="${!empty myShipAttaData.titleDes}">
		                        <div class=" detail-descrip-info" style="font-size: 10px;font-weight: bold; text-align: center;">
		                          (${myShipAttaData.titleDes})
		                        </div>
		                      </c:if>
	                        </c:if>
	                        <c:if test="${empty myShipAttaData.url}">
	                        <c:if test="${!empty myShipAttaData.titleDes}">
	                        <div class=" detail-descrip-info" style="text-align: left;">
	                          ${myShipAttaData.titleDes}
	                        </div>
	                        </c:if>
	                        </c:if>
	                      </c:forEach>
	                    </c:when>
	                    <c:otherwise>
	                      <div
	                        style="text-align: center; height: 40px; width: 100％; margin: 0 auto;">暂无记录</div>
	                    </c:otherwise>
	                  </c:choose>
	                </div>
	              </section>

                  <!-- <section id="section-4"  class="content-current">
                    <div class="mediabox">
						<table style="width: 100%">
                        <tbody>
                          <tr>
                            <td colspan="4" class="bold"
                              style="font-size: 16px; text-align: center;">合同模版</td>
                          </tr>
                          <tr>
                            <td colspan="4">
                            	<ul>
                            		<li>1.</li>
                            		<li>2.</li>
                            		<li>3.</li>
                            	</ul>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </section> -->

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
  </div>
</body>
</html>