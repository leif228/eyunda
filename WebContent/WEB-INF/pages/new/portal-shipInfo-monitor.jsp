<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx"
  value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>易运达 - 首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
  content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta name="description" content="" />
<meta name="author" content="guoqiang" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/common.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/css/page-tabs.css" />
<style>
.tabs nav {
  text-align: center;
}
#content{
	padding-top: 10px;
	border-top-left-radius:0px;
}

</style>

</head>

<body>
  <!-- section header -->
  <div id="content" class="site-content" style="width: 750px;">
    <div class="clear"></div>
    <!-- content-area -->

    <div class="line-one">
      <div class="row-fluid">
        <div class="span12">
          <div id="tabs" class="tabs">
            <nav>
              <nav>
                <ul>
                  <li class="tab-current"><a href="#section-1"><span>主要信息</span></a></li>
                  <li class=""><a href="#section-2"><span>概况</span></a></li>
                  <li class=""><a href="#section-3"><span>证书</span></a></li>
                  <li class=""><a href="#section-4"><span>动态</span></a></li>
                </ul>
              </nav>
            </nav>
            <div class="tab-info">
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
              <section id="section-2">
                <div class="mediabox">
                  <table style="width: 100%">
                    <tbody>
                      <tr>
                        <td colspan="4" class="bold"
                          style="font-size: 16px; text-align: center;">船舶属性</td>
                      </tr>
                      <tr>
                        <td style="background: #eeeeee;" class="bold">承运人:</td>
                        <td>
                        <c:if test="${!empty contact}">
                          <c:choose>
                            <c:when test="${!empty contact.userLogo}">
                              <img class="contact-item-object"
                                style="width: 32px; height: 32px;"
                                src="${ctx}/download/imageDownload?url=${contact.userLogo}"
                                alt="" />
                            </c:when>
                            <c:otherwise>
                              <img class="contact-item-object"
                                style="width: 32px; height: 32px;"
                                src="${ctx}/img/user.jpg" alt="" />
                            </c:otherwise>
                          </c:choose> <br />${contact.trueName}
                          ${contact.mobile}
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
                            <c:when test="${fn:length(myShipData.attrNameDatas) == 1}">
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
              <section id="section-3">
                <div class="mediabox" style="padding-left: 0px;text-align: center;">
                  <c:choose>
                    <c:when
                      test="${myShipData.myShipAttaDatas!=null && fn:length(myShipData.myShipAttaDatas)>0}">
                      <c:forEach var="myShipAttaData"
                        items="${myShipData.myShipAttaDatas}">
                        <div class=" detail-descrip-info"
                          style="text-align: center;">
                          <c:if test="${!empty myShipAttaData.url}">
                            <div>
                              <img
                                src="${ctx}/download/imageDownload?url=${myShipAttaData.url}"
                                style="margin: 10px auto 0px; border: 1px solid #ccc; padding: 2px;"
                                width="100%" />
                            </div>
                          </c:if>
                          <div
                            style="width: 100%; text-align: center; margin: 0px auto 10px;"
                            class="detail-descrip-text">${myShipAttaData.titleDes}
                          </div>
                        </div>
                      </c:forEach>
                    </c:when>
                    <c:otherwise>
                      <div
                        style="text-align: center; height: 40px; width: 100％; margin: 0 auto;">暂无记录</div>
                    </c:otherwise>
                  </c:choose>
                </div>
              </section>

              <section id="section-4">
                <div class="mediabox">
                  <c:choose>
                    <c:when
                      test="${myShipData.shipArvlftDatas!=null && fn:length(myShipData.shipArvlftDatas)>0}">
                      <c:forEach var="shipArvlftData"
                        items="${myShipData.shipArvlftDatas}">
                        <c:if test="${shipArvlftData.arvlft=='left'}">
                          <div class="one-dt"
                            style="padding: 10px 0px; border-bottom: 1px dashed #ddd">
                            <a href="${ctx}/space/state/routePlay?shipId=${myShipData.id}&arvlftId=${shipArvlftData.id}" target="_blank">${shipArvlftData.arvlftDesc}</a>
                          </div>
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
            </div>
            <!-- /content -->
          </div>
        </div>
      </div>
    </div>
  </div>
  <script src="${ctx}/js/CBPFWTabs.js"></script>
  <script>
    new CBPFWTabs(document.getElementById('tabs'));
  </script>
</body>
</html>