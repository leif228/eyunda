<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>前台浏览 - 搜索船舶结果</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="stilearning" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-helper.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-icon.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/animate.css" rel="stylesheet" />
<link href="${ctx}/css/uniform.default.css" rel="stylesheet" />

<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

<script src="${ctx}/js/jquery.js"></script>
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->

<style>
/* this use for demo knob only, you can delete this if you want*/
.demo-knob {
  text-align: center;
  margin-top: 10px;
  margin-bottom: 10px;
}

body {
  font-size: 12px;
}

.table th {
  color: #333;
  text-align: center;
}

.table td {
  font-size: 14px;
}
</style>

</head>

<body>
  <!-- section header -->
  <jsp:include page="portal-head.jsp"></jsp:include>

  <!-- section content -->
  <section class="section" style="overflow-y: hidden">
    <div class="row-fluid">
      <!-- span side-left -->
      <div class="span1">
        <!--side bar-->
        <jsp:include page="portal-sideleft.jsp"></jsp:include>
        <!--/side bar -->
      </div>
      <!-- span side-left -->

      <!-- span content -->
      <div class="span9">
        <!-- content -->
        <div class="content">
          <!-- content-breadcrumb -->
          <div class="content-breadcrumb" style="padding-top: 10px;">
            <!--breadcrumb-->
            <ul class="breadcrumb bold" style="font-size: 12px;"><li>热门搜索：</li>
              <c:forEach var="searchHot" items="${searchHots}">

                <c:choose>
                  <c:when
                    test="${!empty selectSearchHot && selectSearchHot == searchHot.searchWords }">
                    <li><a
                      href="${ctx}/portal/home/searchShip?keyWords=${searchHot.searchWords}"><span
                        class="color-teal">${searchHot.searchWords}</span></a> <span
                      class="divider"></span></li>
                  </c:when>
                  <c:otherwise>
                    <li><a
                      href="${ctx}/portal/home/searchShip?keyWords=${searchHot.searchWords}">${searchHot.searchWords}</a>
                      <span class="divider"></span></li>
                  </c:otherwise>
                </c:choose>

              </c:forEach>

            </ul>
            <!--/breadcrumb-->
          </div>
          <!-- /content-breadcrumb -->
          <!-- content-body -->
          <div class="content-body" style="padding-top: 20px;">
            <!--row-fluid-->
            <div class="row-fluid">
              <div class="span12">
                <!--box body-->
                <div class="box-body">
                  <div class="box corner-all">
                    <div class="widget-box">
                      <div class="widget-title">
                        <h5>船舶列表</h5>
                      </div>
                      <div class="widget-content nopadding">
                        <table class="table table-bordered data-table"
                          style="word-break: break-all;">
                          <thead>
                            <tr>
                              <th style="width: 10%">船舶图片</th>
                              <th style="width: 10%">承运人</th>
                              <th style="width: 10%">船名</th>
                              <th style="width: 10%">MMSI编号</th>
                              <th style="width: 10%">船类</th>
                              <th style="width: 8%">载货(吨)</th>
                              <th style="width: 8%">载箱(个)</th>
                              <th>动态</th>
                            </tr>
                          </thead>

                          <tbody>
                            <c:forEach var="myShipData" items="${pageData.result}">
                              <tr>
                                <td>
                                  <a href="${ctx}/portal/home/shipDetail?shipId=${myShipData.id}" target="_blank"> <c:choose>
                                     <c:when test="${!empty myShipData.shipLogo}">
                                        <img src="${ctx}/download/imageDownload?url=${myShipData.shipLogo}"
                                          style="width: 80px; height: 60px;" alt="" class="thumbnail" />
                                      </c:when>
                                      <c:otherwise>
                                        <img
                                          src="${ctx}/img/shipImage/${myShipData.shipType}.jpg"
                                          style="width: 80px; height: 60px;" alt=""
                                          class="thumbnail" />
                                      </c:otherwise>
                                    </c:choose>
                                </a></td>
                                <td><c:choose>
                                    <c:when test="${!empty myShipData.broker.trueName }">${myShipData.broker.trueName}</c:when>
                                    <c:otherwise>${myShipData.broker.loginName}</c:otherwise>
                                  </c:choose></td>
                                <td><a
                                  href="${ctx}/portal/home/shipDetail?shipId=${myShipData.id}"
                                  target="_blank"> ${myShipData.shipName}</a></td>
                                <td>${myShipData.mmsi}</td>
                                <td>${myShipData.typeData.typeName}</td>
                                <td>${myShipData.aTons}</td>
                                <td>${myShipData.fullContainer}</td>
                                <td>${myShipData.arvlftDesc}</td>
                              </tr>
                            </c:forEach>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!--/content-body -->
        </div>
        <!-- /content -->
      </div>
      <!-- /span content -->

      <!-- span side-right -->
      <div class="span2">
        <!-- side-right -->
        <aside class="side-right">
          <!-- sidebar-right -->
          <div class="sidebar-right">

            <!-- sidebar-right-content -->
            <div class="sidebar-right-content">
              <div class="tab-content">
                <!--contact-->
                <div class="tab-pane fade active in" id="contact"
                  style="font-size: 12px;">
                  <div class="side-contact">

                    <!--contact-control-->
                    <div class="contact-control">
                      <div class="btn-group pull-right">
                        <button class="dropdown-toggle bg-transparent no-border"
                          data-toggle="dropdown">
                          <i class="icofont-caret-down"></i>
                        </button>
                        <ul class="dropdown-menu">
                          <li><a href="#"><i
                              class="icofont-certificate color-green"></i>内河</a></li>
                          <li><a href="#"><i
                              class="icofont-certificate color-silver-dark"></i>沿海</a></li>
                          <li><a href="#"><i
                              class="icofont-certificate color-red"></i>远洋</a></li>
                          <li><a href="#"><i
                              class="icofont-certificate color-orange"></i>陆路</a></li>
                        </ul>
                      </div>
                      <h5>
                        <i class="icofont-comment color-teal"></i> 联系人
                      </h5>
                    </div>
                    <!--/contact-control-->
                    <!--contact-search-->
                    <div class="contact-search">
                      <div class="input-icon-prepend">
                        <div class="icon">
                          <button type="submit">
                            <i class="typicn-message color-silver-dark"></i>
                          </button>
                        </div>
                        <input class="input-block-level grd-white" maxlength="11"
                          type="text" name="contact-search"
                          placeholder="chat with..." />
                      </div>
                    </div>
                    <!--/contact-search-->
                    <!--contact-list, we set this max-height:380px, you can set this if you want-->
                    <ul class="contact-list">
                      <c:forEach var="releaseOperatorData"
                        items="${releaseOperatorDatas}">
                        <li class="contact-alt grd-white"><a
                          href="${ctx}/space/chat/show?toUserId=${releaseOperatorData.operatorData.id}"
                          target="_blank">
                            <div class="contact-item">
                              <div class="pull-left">
                                <img class="contact-item-object"
                                  style="width: 32px; height: 32px;"
                                  src="${ctx}/download/imageDownload?url=${releaseOperatorData.operatorData.userLogo}" />
                              </div>
                              <div class="contact-item-body">
                                <div class="status" title="ofline">
                                  <i class="icofont-certificate color-silver-dark"></i>
                                </div>
                                <p class="contact-item-heading bold">${releaseOperatorData.operatorData.trueName} ${releaseOperatorData.operatorData.mobile}</p>
                                <p class="contact-item-heading">${releaseOperatorData.operatorData.unitName}</p>
                              </div>
                            </div>
                        </a></li>
                      </c:forEach>

                    </ul>
                    <!--/contact-list-->

                  </div>
                </div>
                <!--/contact-->

              </div>
            </div>
            <!-- /sidebar-right-content -->
          </div>
          <!-- /sidebar-right -->
        </aside>
        <!-- /side-right -->
      </div>
      <!-- /span side-right -->
    </div>
  </section>

  <!-- javascript
        ================================================== -->
  <script src="${ctx}/js/bootstrap.js"></script>
  <script src="${ctx}/js/uniform/jquery.uniform.js"></script>

  <script src="${ctx}/js/knob/jquery.knob.js"></script>
  <script src="${ctx}/js/peity/jquery.peity.js"></script>
  <script src="${ctx}/js/holder.js"></script>
  <script src="${ctx}/js/jquery.form-2.63.js"></script>
  <script src="${ctx}/js/portal/portal-search.js"></script>
  <script type="text/javascript">
    var _rootPath = "${ctx}";
    $(document).ready(function() {
      $('#example').dataTable({
        "sScrollY" : "200px",
        "bPaginate" : false
      });
    });
  </script>

</body>
</html>