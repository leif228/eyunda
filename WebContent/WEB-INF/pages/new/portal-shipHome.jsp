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
</style>

</head>

<body>
  <!-- section header -->
  <jsp:include page="portal-head.jsp"></jsp:include>

  <nav class="breadcrumb" style="height: 35px"></nav>
  <!-- section content -->
  <div id="content" class="site-content">
    <div class="clear"></div>
    <!-- content-area -->
    <div id="primary" class="content-area">

      <!-- broker start -->
      <div class="line-one">
        <div class="row-fluid">
          <c:forEach varStatus="status" items="${operPage.result}" var="userData">
            <div class="card-one-container">
              <div class="card-one mr5">
                <a href="${ctx }/portal/home/userInfo">
                  <c:if test="${userData.onlineStatus == 'online'}">
	                <div class="avator">
	              </c:if>
	              <c:if test="${userData.onlineStatus == 'ofline'}">
	                <div class="avator opacity">
	              </c:if>
                    <c:if test="${empty userData.userLogo}">
                      <img src="${ctx}/img/user.jpg" />
                    </c:if>
                    <c:if test="${!empty userData.userLogo}">
                      <img src="${ctx}/download/imageDownload?url=${userData.userLogo}" />
                    </c:if>
                  </div>
                  <div class="user-info">
                    <div class="user-name">${userData.trueName}(${userData.onlineStatus.description})</div>
                    <div class="user-phone">${userData.mobile}</div>
                    <div class="front_usi_lem">船舶 20 | 合同 30</div>
                  </div>
                </a>
              </div>
              <c:if test="${userData.onlineStatus == 'online'}">
                <div class="arrow-right-yellow"></div>
              </c:if>
              <c:if test="${userData.onlineStatus == 'ofline'}">
                <div class="arrow-right-gray"></div>
              </c:if>
            </div>
            <c:if test="${status.index % 5 == 0 && status.index > 0}">
              <div class="row-fluid">
            </c:if>
          </c:forEach>
          
        </div>
      </div>
      </div>
      <!-- broker end -->

      <!-- ship start -->
      <div class="line-one">
        <div class="cat-box">
          <h3 class="cat-title">
            <a href="${ctx}/portal/home/shipListx" title="最新船舶信息"><i
              class="fa fa-bars"></i>船盘</a><span class="more"><a
              href="${ctx }/portal/home/shipListx">更多</a></span>
          </h3>

          <div class="clear"></div>
          <div class="cat-site">
            <c:forEach varStatus="status" var="shipData" items="${shipPage.result}">
              <c:if test="${status.index != fn:length(shipPage.result) - 1}">
                <div class="one-line">
              </c:if>
              <c:if test="${status.index == fn:length(shipPage.result) - 1}">
                <div class="one-line no-border">
              </c:if>
                <div class="row-fluid">
                  <a href="${ctx}/portal/home/shipInfo?shipId=${shipData.id}" class="row-fluid">
                    <div class="s-head span1">
                      <c:if test="${empty shipData.shipLogo}">
                        <img src="${ctx}/img/shipImage/${myShipData.shipType}.jpg" alt="" class="thumbnail"/>
                      </c:if>
                      <c:if test="${!empty shipData.shipLogo}">
                        <img
                          src="${ctx}/download/imageDownload?url=${shipData.shipLogo}" alt="" class="thumbnail"/>
                      </c:if>
                    </div>
                    <div class="one-line-info span10 fa-pull-right">
                      <div class="one-line-title row-fluid">
                        <div class="span6 adj-height-40">${shipData.shipName}</div>
                        <div class="span6 to-right adj-height-40">承运人：${shipData.broker.trueName}</div>
                      </div>
                      <div class="row-fluid">
                        <div class="span4 adj-height-30">代理人：6个</div>
                        <div class="span4 adj-height-30">签订合同：${shipData.orderCount}</div>
                        <div class="span4 to-right adj-height-30">联系电话：${shipData.broker.mobile}</div>
                      </div>
                      <div class="row-fluid">
                        <div class="span12">动态：2016-07-05 04:39离 珠海.九洲港 去 广州.南沙新港码头</div>
                      </div>
                    </div>
                  </a>
                </div>
              </div>
              <div class="clear"></div>
            </c:forEach>
          </div>
        </div>
      </div>
      <!-- ship end -->

      <!-- cargo start -->
      <div class="line-one">
        <div class="cat-box">
          <h3 class="cat-title">
            <a href="${ctx}/portal/home/cargoListx" title="最新热门货物">
              <i class="fa fa-bars"></i>货盘</a><span class="more"><a
              href="${ctx}/portal/home/cargoListx">更多</a></span>
          </h3>
          <div class="clear"></div>
          <div class="cat-site">
            <c:forEach varStatus="status" items="${cargoPage.result}" var="cargoData">
              <c:if test="${status.index != fn:length(cargoPage.result) - 1}">
                <div class="one-line">
              </c:if>
              <c:if test="${status.index == fn:length(cargoPage.result) - 1}">
                <div class="one-line no-border">
              </c:if>
	            <a href="${ctx}/portal/home/cargoInfo?cargoId=${cargoData.id}" class="row-fluid">
                  <div class="u-head span1">
                    <c:if test="${empty cargoData.cargoImage}">
                      <img src="${ctx}/img/cargoImage/${cargoData.cargoType}.jpg" alt="" class="thumbnail"/>
                    </c:if>
                    <c:if test="${!empty cargoData.cargoImage}">
                      <img src="${ctx}/download/imageDownload?url=${cargoData.cargoImage}" alt="" class="thumbnail"/>
                    </c:if>
	                </div>
	                <div class="one-line-info span10 fa-pull-right">
	                  <div class="one-line-title row-fluid">
	                    <div class="span6 adj-height-40">${cargoData.cargoName}</div>
	                    <div class="span6 to-right adj-height-40">托运人：
	                      <c:if test="${!empty cargoData.agent}">${cargoData.agent.trueName}</c:if>
	                      <c:if test="${empty cargoData.agent}">${cargoData.owner.trueName}</c:if>
	                    </div>
	                  </div>
	                  <div class="row-fluid">
	                    <div class="span4 adj-height-30">货量：${cargoData.tonTeu}</div>
	                    <div class="span4 adj-height-30">运价：${cargoData.price}</div>
	                    <div class="span4 adj-height-30 to-right">运费：${cargoData.transFee}</div>
	                  </div>
	                  <div class="row-fluid">
	                    <div class="span8">${cargoData.startFullName} 到 ${cargoData.endFullName}</div>
	                    <div class="span4 to-right">电话：
						  <c:if test="${!empty cargoData.agent}">${cargoData.agent.mobile}</c:if>
	                      <c:if test="${empty cargoData.agent}">${cargoData.owner.mobile}</c:if>
						</div>
	                  </div>
	                </div>
	              </a>
	            </div>
	          <div class="clear"></div>
            </c:forEach>
          </div>
        </div>
      </div>
      <!-- cargo end -->
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