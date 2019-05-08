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
<link rel="stylesheet" href="${ctx}/css/page-tabs.css" />

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
</style>

</head>

<body>
  <!-- section header -->
  <jsp:include page="portal-head.jsp"></jsp:include>
  <nav class="breadcrumb"
    style="margin-bottom: 10px; background: transparent;">
    <i class="fa-television fa"></i> <a class="crumbs" title="返回首页"
      href="${ctx}/portal/home/shipHome">首页</a> <i
      class="fa fa-angle-right"></i> <a class="crumbs" title="船盘"
      href="${ctx}/portal/home/shipListx">船盘</a> <i
      class="fa fa-angle-right"></i> ${myShipData.shipName}
  </nav>
  <!-- section content -->
  <div id="content" class="site-content">
    <div class="clear"></div>
    <!-- content-area -->
    <div id="primary" class="content-area">
      <div class="line-one">
        <div class="cat-box">
          <h3 class="cat-title">
            <a href="###" title="${myShipData.shipName}"><i
              class="fa fa-bars"></i>船盘详情</a>
              <a href="http://www.eyd98.com/space/orderCommon/orderEdit?orderId=0&amp;orderType=wrap" class="btn btn-info" style="color:#ffffff;float:right;margin:5px 5px">我要下单</a>
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
            <div class="span10">
              <div id="tabs" class="tabs">
                <nav>
                  <ul>
                    <li class="tab-current" style="margin: 0 0.25em 0 0"><a
                      href="#section-1"><span>主要信息</span></a></li>
                    <li class=""><a href="#section-2"><span>概况</span></a></li>
                    <li class=""><a href="#section-3"><span>证书</span></a></li>
                    <li class=""><a href="#section-4"><span>动态</span></a></li>
                  </ul>
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
                          <!-- 
                          <tr>
                            <td style="background: #eeeeee;" class="bold">经度:</td>
                            <td>${shipCooorData.longitude}</td>
                            <td style="background: #eeeeee;" class="bold">纬度:</td>
                            <td>${shipCooorData.latitude}</td>
                          </tr>
                           -->
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
                          <!-- <tr>
                            <td style="background: #eeeeee;" class="bold">航速:</td>
                            <td>节</td>
                            <td style="background: #eeeeee;" class="bold">航向:</td>
                            <td>度</td>
                          </tr> -->

                          <tr>
                            <td style="background: #eeeeee;" class="bold">待货信息:</td>
                            <td colspan="3">${myShipData.cabinDesc}</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>

                  </section>
                  <section id="section-2">

                    <div class="mediabox">
                      <table class="">
                        <tbody>
                          <tr>
                            <td colspan="4" class="bold"
                              style="font-size: 16px; text-align: center;">船舶属性</td>
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

                          <tr>
                            <td colspan="4" class="bold"
                              style="font-size: 16px; text-align: center;">接货类别</td>
                          </tr>

                          <tr>
                            <td colspan="4"><c:forEach varStatus="status"
                                var="cargoType" items="${myShipData.shipCargoTypes}">
                                <div class="span2" style="margin-left: 10px;">${cargoType.cargoBigType.description }</div>
                              </c:forEach></td>
                          </tr>

                          <tr>
                            <td colspan="4" class="bold"
                              style="font-size: 16px; text-align: center;">接货港口</td>
                          </tr>
                          <tr>
                            <td colspan="4"><c:forEach var="shipPortData"
                                items="${myShipData.shipPortDatas}">
                                <div class="span3" style="margin-left: 10px;">${shipPortData.cityName}</div>
                              </c:forEach></td>
                          </tr>

                          <tr>
                            <td colspan="4" class="bold"
                              style="font-size: 16px; text-align: center;">船舶评价</td>
                          </tr>
                          <c:forEach var="evaluateData" items="${evaluateDatas}">
                            <tr>
                              <td colspan="4">
                                <div class="media">
                                  <a class="pull-left" href="#"> <img
                                    src="${ctx}/download/imageDownload?url=${evaluateData.userData.userLogo}"
                                    class="thumbnail" style="width: 48px; height: 48px;" />
                                  </a>
                                  <div class="media-body">
                                    <h4 class="media-heading">
                                      <a href="#">${evaluateData.evalType.description}</a>
                                      <small class="helper-font-small">
                                        ${evaluateData.userData.trueName}
                                        ${evaluateData.createTime} </small>
                                    </h4>
                                    <p>${evaluateData.evalContent}</p>
                                  </div>
                                </div>
                              </td>
                            </tr>
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
                        <c:when test="${myShipData.shipArvlftDatas!=null && fn:length(myShipData.shipArvlftDatas)>0}">
                          <c:forEach var="shipArvlftData" items="${myShipData.shipArvlftDatas}">
                            <c:if test="${shipArvlftData.arvlft=='left'}">
                              <div class="one-dt" style="padding: 10px 0px; border-bottom: 1px dashed #ddd">
                                <a href="${ctx}/space/state/routePlay?shipId=${myShipData.id}&arvlftId=${shipArvlftData.id}" target="_blank">${shipArvlftData.arvlftDesc}</a>
                              </div>
                            </c:if>
                          </c:forEach>
                        </c:when>
                        <c:otherwise>
                          <div style="text-align: center; height: 40px; width: 100％; margin: 0 auto;">暂无记录</div>
                        </c:otherwise>
                      </c:choose>
                    </div>
                    <!-- 
                    <nav class="navigation pagination">
                      <div class="nav-links">
                        <a class="prev page-numbers" href="javascript:jumpPage(0)"><i
                          class="fa fa-angle-left"></i></a> <span
                          class="page-numbers current"><span
                          class="screen-reader-text">第 </span>1<span
                          class="screen-reader-text"> 页</span></span> <a
                          class="next page-numbers" href="javascript:jumpPage(2)"><i
                          class="fa fa-angle-right"></i></a>
                      </div>
                    </nav>
                     -->
                  </section>
                </div>
                <!-- /content -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- widget-area -->
    <div id="sidebar" class="widget-area">
      <aside class="widget">
      <c:if test="${!empty contact}">
        <h3 class="widget-title">
          <i class="icofont-comment color-teal"></i>承运人
        </h3>
        <div class="widget-content">
          <div class="widget-item">
            <a
              href="${ctx}/space/chat/show?toUserId=${contact.id}&shipId=${myShipData.id}"
              target="_blank">
              <div
                class="uhead <c:if test='${contact.onlineStatus == "ofline"}'>opacity</c:if>">
                <img
                  src="${ctx}/download/imageDownload?url=${contact.userLogo}"
                  alt="" class="thumbnail" />
              </div>
              <div class="infomation">
                <p>${contact.trueName}</p>
                <p class="phone">${contact.mobile}</p>
              </div>
              <div class="flag">
                <i class="fa fa-angle-right"></i>
              </div>
            </a>
          </div>
        </div>
        <div class="clear"></div>
        </c:if>
      </aside>

      <jsp:include page="./customerDownload.jsp"></jsp:include>
    </div>
    <div class="clear"></div>
  </div>
  <!-- section footer -->
  <jsp:include page="portal-foot.jsp"></jsp:include>
  <jsp:include page="portal-favorite.jsp"></jsp:include>
  <!-- javascript
    ================================================== -->
  <script src="${ctx}/js/jquery.divSelect.js"></script>
  <script src="${ctx}/js/CBPFWTabs.js"></script>
  <script src="${ctx}/js/new/portal-favorite.js"></script>
  <script>
    new CBPFWTabs(document.getElementById('tabs'));
  </script>
  <!--  -->
</body>
</html>