<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>前台浏览 - 搜索货物结果</title>
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

.demo-knob {
  height: 230px;
  width: 220px;
  overflow: hidden;
  text-align: left;
}

.demo-knob .shipKeyWords span {
  width: 220px;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  "
}

.account-details span {
  display: block;
}

body {
  font-size: 12px;
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
  <section class="section">
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
          <!-- 
          <div class="content-header">
            <h3>
              <i class="icofont-home"></i>搜索： <a class="link" href="#">巨型集装箱</a>&nbsp;&gt;&nbsp;
            </h3>
          </div>
           -->

          <!-- content-breadcrumb -->
          <div class="content-breadcrumb">
            <!--breadcrumb-->
            <ul class="breadcrumb bold" style="font-size: 12px;">

              热门搜索：
              <c:forEach var="searchHotCargo" items="${searchHotCargos}">

                <c:choose>
                  <c:when
                    test="${!empty selectSearchHot && selectSearchHot == searchHotCargo.searchWords }">
                    <li><a
                      href="${ctx}/portal/home/searchRelCargo?keyWords=${searchHotCargo.searchWords}"><span
                        class="color-teal">${searchHotCargo.searchWords}</span></a> <span
                      class="divider"></span></li>
                  </c:when>
                  <c:otherwise>
                    <li><a
                      href="${ctx}/portal/home/searchRelCargo?keyWords=${searchHotCargo.searchWords}">${searchHotCargo.searchWords}</a>
                      <span class="divider"></span></li>
                  </c:otherwise>
                </c:choose>

              </c:forEach>
            </ul>
            <!--/breadcrumb-->
          </div>
          <!-- /content-breadcrumb -->

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
                    <span>货物列表</span> 
                    <%-- <span style="float: right; margin-top: -5px;"> 
                    	<a class="btn btn-info" onclick="window.location.href='${ctx}/space/cargo/myCargo/addMyCargo?id=0&isReception=yes';">我有货物</a>
                    </span>--%>
                  </div>

                  <!--box body-->
                  <div class="box-body" style="padding: 0px;">
                    <table class="table table-bordered data-table table-striped"
                      style="word-break: break-all;">
                      <thead>
                        <tr>
		                  <th style="text-align: center; width: 8%">货物图片</th>
		                  <th style="text-align: center; width: 5%">货号</th>
		                  <th style="text-align: center; width: 12%">装货港</th>
		                  <th style="text-align: center; width: 12%">卸货港</th>
		                  <th style="text-align: center; width: 8%">托运人</th>
		                  <th style="text-align: center; width: 10%">货名</th>
		                  <th style="text-align: center; width: 8%">货量</th>
		                  <th style="text-align: center; width: 10%">运价</th>
		                  <th style="text-align: center; width: 10%">运费</th>
		                  <th style="text-align: center; width: 12%">发布日期</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach  var="cargoData" items="${pageData.result}">
                      	  <tr>
                        	<td style="text-align: center">
                        	  <a href="${ctx}/portal/home/cargoDetail?id=${cargoData.id}" target="_blank"> 
                        	    <c:choose>
                                  <c:when test="${!empty cargoData.cargoImage}">
                                    <img src="${ctx}/download/imageDownload?url=${cargoData.cargoImage}"
                                      style="width: 80px; height: 60px;" alt="" class="thumbnail" title="点击查看详情"/>
                                  </c:when>
                                  <c:otherwise>
                                    <img src="${ctx}/img/cargoImage/${cargoData.cargoType}.jpg"
                                      style="width: 80px; height: 60px;" alt=""class="thumbnail" title="点击查看详情"/>
                                  </c:otherwise>
                                </c:choose>
                        	  </a>
                        	</td>
	                        <td>${cargoData.id}</td>
	                    	<td>${cargoData.startPortData.fullName}</td>
	                    	<td>${cargoData.endPortData.fullName}</td>
	                    	<td>
           	                  <c:if test="${!empty cargoData.agent}">
           	                    ${cargoData.agent.trueName}
				              </c:if>
			                  <c:if test="${empty cargoData.agent}">
			                    ${cargoData.owner.trueName}
			                  </c:if>
	                        </td>
		                    <td>
		                      <c:if test="${cargoData.cargoType=='container20e'}">${cargoData.containerNames}</c:if>
		                      <c:if test="${cargoData.cargoType!='container20e'}">${cargoData.cargoTypeName}</c:if>
		                    </td>
		                    <td style="text-align: right">
		                      <c:if test="${cargoData.cargoType=='container20e'}">${cargoData.containerTeus}</c:if>
		                      <c:if test="${cargoData.cargoType!='container20e'}">${cargoData.tonTeuDes}</c:if>
		                    </td>
		                    <td style="text-align: right">
		                      <c:if test="${cargoData.cargoType=='container20e'}">${cargoData.containerPrices}</c:if>
		                      <c:if test="${cargoData.cargoType!='container20e'}">${cargoData.priceDes}</c:if>
		                    </td>
		                    <td style="text-align: right">${cargoData.transFeeDes}</td>
		                    <td style="text-align: center">${cargoData.createTime}</td>
                      	  </tr>
                    	</c:forEach>
                      </tbody>
                    </table>
                  </div>


                  <div class="gallery-controls bottom">
                    <div class="gallery-category">
                      <div class="pull-right">
                        <span>页号 ${pageData.pageNo} of ${pageData.totalPages }</span>
                      </div>
                    </div>

                    <div>
                      <form action="${ctx}/portal/home/searchRelCargo"
                        id="pageform" method="post">
                        <c:if test="${!empty selectSearchHot }">
                          <input type="hidden" name="keyWords"
                            value="${selectSearchHot }" />
                        </c:if>
                        <jsp:include page="./pager.jsp"></jsp:include>
                      </form>
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

	<!-- Modal deleteDialog -->
	<div id="operCargoDialog" class="modal hide fade">
		<form class="form-horizontal" name="operCargoDialogForm"
			id="operCargoDialogForm" novalidate="novalidate" method="get"
			action="${ctx}/space/cargo/myCargo/saveOperCargo">
		<input type="hidden" name="cargoId" id="operCargoId" value="" />
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>代理货物确认</h3>
		</div>
		<div class="modal-body">
			<p>你确认要代理该货物信息吗？</p>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal">
				<i class="icon icon-off"></i> 取消
			</button>
			<a href="javascript:void(0);" class="btn btn-primary btnOperCargo">
	          <i class="icon icon-ok icon-white"></i> 确认
	        </a>
		</div>
		</form>
	</div>


  <!-- javascript
        ================================================== -->

  <script src="${ctx}/js/jquery-v.min.js"></script>
  <script src="${ctx}/js/bootstrap.min.js"></script>
  <script src="${ctx}/js/bootstrap.js"></script>
  <script src="${ctx}/js/uniform/jquery.uniform.js"></script>
  <script src="${ctx}/js/knob/jquery.knob.js"></script>
  <script src="${ctx}/js/peity/jquery.peity.js"></script>
  <script src="${ctx}/js/holder.js"></script>
  <script src="${ctx}/js/jquery.form-2.63.js"></script>
  <script src="${ctx}/js/portal/portal-serachRelCargo.js"></script>
  <script type="text/javascript">
    var _rootPath = "${ctx}";
    var pageNo = "${pageData.pageNo}";
    var inputSearch = "${inputSearch}";
    var selSearchRlsCode = "${selSearchRlsCode}";
    var selectSearchHot = "${selectSearchHot}";
  </script>

</body>
</html>