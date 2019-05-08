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
  <nav class="breadcrumb" style="margin-bottom: 10px;">
    <i class="fa-television fa"></i> <a class="crumbs" title="返回首页"
      href="${ctx }/portal/home/shipHome">首页</a> <i
      class="fa fa-angle-right"></i> 代理人列表
  </nav>
  <!-- section content -->
  <div id="content" class="site-content">
    <div class="clear"></div>
    <!-- content-area -->
    <div id="primary" class="content-area">
      <div class="line-one">
        <c:forEach varStatus="status" items="${pageData.result}"
          var="operatorData">
          <div class="row-fluid">
            <article class="post">
              <figure class="thumbnail">
                <div class="load">
                  <a href="javascript:void(0);"><img
                    src="${ctx}/download/imageDownload?url=${operatorData.userData.userLogo}"
                    alt="${operatorData.userData.trueName}"
                    style="display: block;"></a>
                </div>
              </figure>
              <header class="entry-header">
                <h2 class="entry-title">
                  <a href="http://${operatorData.userData.loginName}.eyd98.com/portal/site/index?c=GSJJ"
                    target="_blank">${operatorData.userData.trueName}</a>
                </h2>
              </header>
              <div class="entry-content">
                <div class="archive-content">
                  ${operatorData.userData.trueName}
                </div>
                <span class="title-l"></span>
                <span class="entry-meta">
                  <span class="date">&nbsp;</span>
                  <span class="views">
                    <a href="javascript:void(0);">
                      <i class="fa fa-group"></i>业务经理：${fn:length(operatorData.handlerDatas)}人</a></span>
                </span>
                <div class="clear"></div>
              </div>
              <span class="entry-more">
                <a href="http://${operatorData.userData.loginName}.eyd98.com/portal/site/index?c=GSJJ"
                  target="_blank">代理人主页</a>
              </span>
            </article>
          </div>
          <div class="clear"></div>
          <div class="row-fluid" style="margin-top: -15px">
            <div class="link-left">|</div>
            <div class="link-right">|</div>
          </div>
          <div class="row-fluid">
            <article class="post user-card-container">
              <c:forEach varStatus="vstatus"
                items="${operatorData.handlerDatas}" var="handlerData">
                <div class="user-card-small">
                  <a href="${ctx}/space/chat/show?toUserId=${handlerData.id}"
                    target="_blank">
                    <div class="uhead  <c:if test='${handlerData.onlineStatus == "ofline"}'>opacity</c:if>">
                      <img src="${ctx}/download/imageDownload?url=${handlerData.userLogo}">
                    </div>
                    <div class="name">${handlerData.trueName}</div>
                    <div class="phone">${handlerData.mobile}</div>
                  </a>
                </div>
              </c:forEach>
            </article>
          </div>
        </c:forEach>
      </div>
      <form action="${ctx}/portal/home/userListx" id="pageform"
        name="pageform" method="get">
        <input type="hidden" id="pageNo2" name="pageNo"
          value="${pageData.pageNo}" />
        <jsp:include page="pager-operator.jsp"></jsp:include>
      </form>
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
  <script src="${ctx}/js/jquery.textSlider.js"></script>
  <script src="${ctx}/js/jquery.divSelect.js"></script>
</body>
</html>