<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>会员空间</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
  content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/css/adminia.css" />
<link rel="stylesheet" href="${ctx}/css/adminia-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/pages/plans.css" />
<link rel="stylesheet" href="${ctx}/css/fullcalendar.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-helper.css" />
<link rel="stylesheet" href="${ctx}/css/stilearn-icon.css" />
<link rel="stylesheet" href="${ctx}/css/datepicker.css" />
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/space/space-ship-state.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>
<style>
#content {
  margin-left: 0px;
}
</style>


</head>

<body>

  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span10">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 船舶动态
    </h1>
    <div class="row">
      <div class="span10">
        <form novalidate="novalidate" method="post" id="pageform" action="${ctx}/space/state/myShip">
          <div class="widget-box">
            <div class="widget-title">
              <h5>船舶信息列表</h5>

              <div style="float: left; margin-top: 4px; margin-left: 12px;">
                部门：<select id="deptId" name="deptId" style="width: 140px;">
                    <option value="-1" selected>[全部]</option>
                    <c:forEach var="departmentData" items="${departmentDatas}">
                      <c:if test="${deptId == departmentData.id}">
                        <option value="${departmentData.id}" selected>${departmentData.deptName}</option>
                      </c:if>
                      <c:if test="${deptId != departmentData.id}">
                        <option value="${departmentData.id}">${departmentData.deptName}</option>
                      </c:if>
                    </c:forEach>
                </select>
              </div>

              <div style="float: left; margin-left: 10px;">
                <div style="float: left; margin-top: 5px; margin-left: 6px; height: 13px;">
                  <input id="keyWords" name="keyWords" type="text" placeholder="请输入船名或MMSI" style="width: 140px;" value="${keyWords}" />
                  <%-- <c:if test="${empty filter}">
                  	<input type="checkbox" value="0" name="filter">只查看一个月内有动态上报的船舶
                  </c:if>
                  <c:if test="${not empty filter}">
                  	<input type="checkbox" value="0" name="filter" checked="checked" >只查看一个月内有动态上报的船舶
                  </c:if> --%>
                  <button class="btn btn-primary search" style="margin-bottom: 9px">
                    <i class="icon-search icon-white" title="查询"></i>查询
                  </button>
                </div>
              </div>

              <!--  <button id="btnAdd" class="btn btn-info"  onclick="window.location.href='${ctx}/space/ship/myShip/edit?id=0';">
                      <i class="icon-plus icon-white"></i> 添加
                    </button>-->
            </div>
            <div class="widget-content nopadding">
              <c:if test="${flag}">${msg}</c:if>
              <c:if test="${empty flag || !flag}">
               <table id="tblShip" class="table table-bordered data-table table-striped">
                <thead>
                  <tr>
                    <th width="8%">船舶图片</th>
                    <th width="10%">船舶</th>
                    <th width="12%">港口</th>
                    <th width="10%">到港时间</th>
                    <th width="20%">卸货</th>
                    <th width="10%">离港时间</th>
                    <th width="20%">装货</th>
                    <!-- <th width="8%">下一港口</th>
                    <th width="10%">备注</th> -->
                    <th width="10%">操作</th>
                  </tr>
                </thead>
                <tbody>
                   <c:forEach var="arvlftShipData" items="${pageData.result}">
                    <tr>
                      <td><a
                        href="${ctx}/portal/home/shipInfo?shipId=${arvlftShipData.shipData.id}"
                        target="_blank"> <c:choose>
                            <c:when test="${!empty arvlftShipData.shipData.shipLogo}">
                              <img
                                src="${ctx}/download/imageDownload?url=${arvlftShipData.shipData.shipLogo}"
                                style="width: 80px; height: 60px;" alt=""
                                class="thumbnail" />
                            </c:when>
                            <c:otherwise>
                              <img
                                src="${ctx}/img/shipImage/${arvlftShipData.shipData.shipType}.jpg"
                                style="width: 80px; height: 60px;" alt=""
                                class="thumbnail" />
                            </c:otherwise>
                          </c:choose>
                      </a></td>
                      <td>
                        ${arvlftShipData.shipData.shipName}<br />
                        ${arvlftShipData.shipData.mmsi}
                      </td>
                      <td>${arvlftShipData.arriveData.portData.fullName}</td>
                      <td>${arvlftShipData.arriveData.arvlftTime}</td>
                      <td style="text-align: left;">
                        <c:if test="${!empty arvlftShipData.arriveData.shipUpdownDatas}">
                          <c:forEach var="shipUpdownData" items="${arvlftShipData.arriveData.shipUpdownDatas}">
                            ${shipUpdownData.cargoNameDesc}:${shipUpdownData.tonTeuDesc}
                            <br />
                          </c:forEach>
                        </c:if></td>
                      <td>${arvlftShipData.leftData.arvlftTime}</td>
                      <td style="text-align: left;">
                        <c:if test="${!empty arvlftShipData.leftData.shipUpdownDatas}">
                          <c:forEach var="shipUpdownData" items="${arvlftShipData.leftData.shipUpdownDatas}">
                            ${shipUpdownData.cargoNameDesc}:${shipUpdownData.tonTeuDesc}
                            <br />
                          </c:forEach>
                        </c:if></td>
                      <%-- <td>${arvlftShipData.leftData.goPortData.fullName}</td>
                      <td>${arvlftShipData.arriveData.remark}</td> --%>
                      <td>
                        <a class="btn btn-primary" title="动态上报"
                          onclick="window.location.href='${ctx}/space/state/myShip/shipArvlft?mmsi=${arvlftShipData.shipData.mmsi}';">
                          <i class=" icon-tags icon-white"></i>
                        </a>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
			 </c:if>
            </div>
            <jsp:include page="./pager.jsp"></jsp:include>
          </div>
        </form>
      </div>
    </div>
  </div>
  <!-- /span9 -->

  </div>
  <!-- /row -->

  </div>
  <!-- /span9 -->
  </div>
  <!-- /row -->

  </div>
  <!-- /container -->

  </div>
  <!-- /content -->

  <jsp:include page="./space-foot.jsp"></jsp:include>



  <!-- Modal publishDialog -->
  <div id="publishDialog" class="modal hide fade">
    <form class="form-horizontal" name="publishDialogForm"
      id="publishDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/space/ship/myShip/publish">
      <input type="hidden" name="_method" value="delete" /> <input
        type="hidden" name="id" id="pubId" value="" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>发布</h3>
      </div>
      <div class="modal-body">
        <p>你确认要发布该船舶信息吗？</p>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <button class="btn btn-primary">
          <i class="icon icon-ok icon-white"></i> 确认
        </button>
      </div>
    </form>
  </div>

  <!-- Modal unpublishDialog -->
  <div id="unpublishDialog" class="modal hide fade">
    <form class="form-horizontal" name="unpublishDialogForm"
      id="unpublishDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/space/ship/myShip/unpublish">
      <input type="hidden" name="_method" value="delete" /> <input
        type="hidden" name="id" id="unpubId" value="" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>取消发布</h3>
      </div>
      <div class="modal-body">
        <p>你确认要取消发布该船舶信息吗？</p>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <button class="btn btn-primary">
          <i class="icon icon-ok icon-white"></i> 确认
        </button>
      </div>
    </form>
  </div>

  <!-- Modal deleteDialog -->
  <div id="deleteDialog" class="modal hide fade">
    <form class="form-horizontal" name="deleteDialogForm"
      id="deleteDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/space/ship/myShip/delete">
      <input type="hidden" name="_method" value="delete" /> <input
        type="hidden" name="id" id="delId" value="" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>删除确认</h3>
      </div>
      <div class="modal-body">
        <p>你确认要删除该船舶信息吗？</p>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <button class="btn btn-primary">
          <i class="icon icon-ok icon-white"></i> 确认
        </button>
      </div>
    </form>
  </div>
</body>
</html>