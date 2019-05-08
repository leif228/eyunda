<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>前台浏览 - 船舶类别列表</title>
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

<script src="${ctx}/js/jquery.js"></script>
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

.layout-right, .fr {
  float: right;
}

.dn {
  display: none
}

.fl {
  float: left;
}

.clearfix:after {
  visibility: hidden;
  display: block;
  font-size: 0px;
  content: " ";
  clear: both;
  height: 0px;
}

.arrow, .arrow .s {
  display: block;
  font-size: 0;
  line-height: 0;
  width: 0;
  height: 0;
  border: 4px dashed transparent;
}

.arrow, .arrow .s {
  display: block;
  font-size: 0px;
  line-height: 0;
  width: 0px;
  height: 0px;
  border: 4px dashed transparent;
}

.arrowT, .arrowT .s {
  border-bottom-color: #BF3019;
  border-bottom-style: solid;
}

.arrowT .s {
  position: absolute;
  left: -5px;
  top: -3px;
}

.arrowB, .arrowB .s {
  border-top-color: #bf3019;
  border-top-style: solid;
  border-width: 5px;
}

a:link, a:visited {
  text-decoration: none;
}

.show-choosed, .fil-item-list {
  margin-bottom: 10px;
}

.goods-infos {
  padding: 20px;
}

.goods-filter {
  margin-bottom: 10px;
  font-size: 13px;
}

.goods-filter .fil-item-list dt.label {
  min-width: 78px;
  _width: 78px;
  text-align: right;
  line-height: 20px;
}

.choose-filist {
  border-bottom: 1px solid #f5f5f5;
  padding-bottom: 12px;
}

.choose-filist .label, #act-reset .act-btn {
  height: 30px;
  line-height: 30px;
}

.choose-filist .choose-items {
  width: 555px;
  padding-top: 5px;
}

.choose-filist .choose-items a {
  background: #ffecdf;
  color: #bf3019;
  padding: 2px 10px;
  margin: 0 10px 8px 0;
}

.choose-filist .choose-items .sign {
  color: #d04d31;
}

#act-reset .act-btn {
  width: 118px;
}

.fil-item-list .fil-selected {
  background: #444;
  color: #fff !important;
}

.fil-item-list .fil-name {
  float: left;
  padding: 2px 5px;
  height: 16px;
  line-height: 16px;
  min-width: 42px;
  _width: 42px;
  text-align: left;
  margin: 0 10px 10px 0;
  white-space: nowrap;
  display: block;
}
/* .colors-list .fil-name-wrap .fil-name{padding:0;width:18px;min-width:18px;height:18px;line-height:18px;border:1px solid #aeaeae;text-indent:-9999px;} */
/* 颜色块 */
.colors-list .color2 {
  background: #00ced1;
}

.fil-item-list .fil-name-wrap {
  float: left;
  min-width: 540px;
  width: 800px;
  overflow: hidden;
}

a.more-btn {
  width: 52px;
  padding-right: 8px;
  text-align: center;
  height: 18px;
  position: relative;
  line-height: 18px;
  background: #7c7c7c;
  color: #fff !important;
}

.open-more-filter {
  border-top: 1px solid #f5f5f5;
  padding-top: 12px;
}

a.more-btn:hover {
  text-decoration: none;
}

.arrowT, .arrowT .s {
  border-width: 5px;
}

.more-btn .arrowB {
  position: absolute;
  border-top-color: #fff;
  right: 8px;
  top: 7px;
}

.more-btn .arrowB .s {
  position: absolute;
  right: -5px;
  top: -7px;
  border-top-color: #7c7c7c;
}

.more-btn .arrowT {
  position: absolute;
  right: 8px;
  top: 1px;
  border-bottom-color: #fff;
}

.more-btn .arrowT .s {
  position: absolute;
  left: -5px;
  top: -3px;
  border-bottom-color: #7c7c7c;
}

.open-more-filter .more-btn {
  width: 84px;
  padding: 2px 8px 2px 0;
  background: #bf3019;
}

.open-more-filter .more-btn .arrowB .s {
  border-top-color: #bf3019;
  top: -7px;
}

.open-more-filter .more-btn .arrowB {
  right: 10px;
  top: 9px;
}

.open-more-filter .more-btn .arrowT .s {
  position: absolute;
  left: -5px;
  top: -3px;
  border-bottom-color: #BF3019;
}

.sidebarfix {
  position /*\**/: static\9;
}

.selectClass {
  color: red;
}

.table th {
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
  <section class="section sidebarfix">
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
          <!-- content-header -->
          <div class="content-header">
            <h3>
            <!-- content-breadcrumb -->
            <div class="content-breadcrumb">
              <!--breadcrumb-->
              <ul class="breadcrumb bold" style="font-size: 12px; padding-left: 20px; background: #eee">
                <li>船类:<span class="divider"></span></li>
                <c:forEach var="typeData" items="${typeDatas}">
                  <c:choose>
                    <c:when test="${typeData.typeCode == typeCode}">
                      <li><a href="#"><span class="color-teal">${typeData.typeName}</span></a>
                      <span class="divider"></span></li>
                    </c:when>
                    <c:otherwise>
                      <li><a class="typeCode" idVal="${typeData.typeCode}" href="javascript:void(0);"><span>${typeData.typeName}</span></a>
                      <span class="divider"></span></li>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </ul>
              <!--/breadcrumb-->
            </div>
            </h3>
          </div>
          <!-- /content-header -->
          <form action="${ctx}/portal/home/sortShipList" id="pageform" name="shipSortForm" method="post">
            <!-- /content-breadcrumb -->
            <div class="goods-infos">
              <input type="hidden" id="typeCode" name="typeCode" value="${typeCode}" />

              <!-- content-body -->
              <div class="content-body">
                <!--row-fluid-->
                <div class="row-fluid">
                  <!--span-->
                  <div class="span12">
                    <!--box-->
                    <div class="box corner-all">
                      <!--box header-->
                      <div class="box-header grd-white color-silver-dark corner-top">
                        <div>船舶类别列表</div>
                      </div>

                      <!--box body-->
                      <div class="box-body" style="padding: 0px;">
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
			                    	<a href="${ctx}/portal/home/shipDetail?shipId=${myShipData.id}" target="_blank">
			                      	<c:choose>
			                          <c:when test="${!empty myShipData.shipLogo}">
			                            <img src="${ctx}/download/imageDownload?url=${myShipData.shipLogo}"
			                      		style="width: 80px; height: 60px;" alt="" class="thumbnail" />
			                          </c:when>
			                          <c:otherwise>
			                            <img src="${ctx}/img/shipImage/${myShipData.shipType}.jpg"
			                             style="width: 80px; height: 60px;" alt="" class="thumbnail" />
			                          </c:otherwise>
			                        </c:choose>
			                      	</a>
			                    </td>
			                    <td>
			                      <c:choose>
			                        <c:when test="${!empty myShipData.broker.trueName }">${myShipData.broker.trueName}</c:when>
			                        <c:otherwise>${myShipData.broker.loginName}</c:otherwise>
			                      </c:choose>
			                    </td>
			                    <td>
			                    	<a href="${ctx}/portal/home/shipDetail?shipId=${myShipData.id}" target="_blank">
			                    	${myShipData.shipName}</a>
			                    </td>
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

                      <div class="gallery-controls bottom">
                        <div class="gallery-category">
                          <div class="pull-right">
                            <span>页号 ${pageData.pageNo} of
                              ${pageData.totalPages }</span>
                          </div>
                        </div>
                        <div>
                          <jsp:include page="./pager.jsp"></jsp:include>
                        </div>
                      </div>

                    </div>
                  </div>
                </div>
              </div>
              <!--/content-body -->

            </div>
            <!-- /content -->
          </form>
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
                            target="mychat">
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
  <script src="${ctx}/js/jquery-v.min.js"></script>
  <script src="${ctx}/js/bootstrap.js"></script>
  <script src="${ctx}/js/uniform/jquery.uniform.js"></script>

  <script src="${ctx}/js/knob/jquery.knob.js"></script>
  <script src="${ctx}/js/peity/jquery.peity.js"></script>

  <!-- required stilearn template js, for full feature-->
  <script src="${ctx}/js/holder.js"></script>
  <script src="${ctx}/js/jquery.form-2.63.js"></script>
  <script src="${ctx}/js/portal/portal-sortList.js"></script>
  <script type="text/javascript">
    var _rootPath = "${ctx}";
    var filters = $("#filters"), btnMore = filters.find("dd.more");
    filters.find(".fil-name-wrap").height(function(k, v) {
      if (v > 60) {
        btnMore.eq(k).show();
        return 60;
      } else {
        return "auto";
      }
    });
    var filItem = function() {
      if (filters.find("dl.fil-item-list").length >= 2) {
        filters.find("dl.fil-item-list:gt(" + 1 + ")").hide();
        filters.next(".open-more-filter").find(".toggle-row").show();
      }
      //<!-- 如果没有筛选条件，隐藏open-more-filter 2013年8月8日 14:12:54 add -->
      if (filters.find("dl.fil-item-list").length < 1) {
        filters.next(".open-more-filter").hide();
      }
    }
    filItem();
    //点击展开更多;
    $(".more-btn", ".toggle-row").on(
        "click",
        function() {
          var o = $(this), txt = "展开更多", className = "arrowB";
          if (o.find(".arrow").hasClass("arrowB")) {
            className = "arrowT";
            txt = "收起更多";
            filters.find("dl.fil-item-list:gt(" + 1 + ")").show();
          } else {
            className = "arrowB";
            txt = "展开更多";
            filters.find("dl.fil-item-list:gt(" + 1 + ")").hide();
          }
          o.find(".txt").text(txt).end().find(".arrow").removeClass(
              "arrowB arrowT").addClass(className);

          return false;
        });

    btnMore.on("click", function() {
      var o = $(this), className = "arrowB", txt = "更多", h = o.parent()
          .find(".fil-name-wrap").height(function(k, v) {
            if (v > 60) {
              className = "arrowB";
              txt = "更多";
              return 60;
            } else {
              className = "arrowT";
              txt = "收起";

              return "auto";
            }
          });
      o.find(".txt").text(txt).end().find(".arrow").removeClass(
          "arrowB arrowT").addClass(className);
      //o.find(".more-text").html(txt);;
      return false;
    });
  </script>
</body>
</html>