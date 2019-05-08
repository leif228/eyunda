<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx"
  value="${pageContext.request.contextPath}" />
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
<link rel="stylesheet" href="${ctx}/css/elusive-webfont.css" />
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
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/space/space-ship.js"></script>
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
      <i class="icon-star icon-white"></i> 船舶管理
    </h1>
    <div class="row">

      <div class="span10">
        <form novalidate="novalidate" method="post" id="pageform"
          action="${ctx}/space/ship/myShip">
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
                <div
                  style="float: left; margin-top: 5px; margin-left: 6px; height: 13px;">
                  <input id="keyWords" name="keyWords" type="text"
                    placeholder="请输入船名或MMSI" style="width: 140px;"
                    value="${keyWords}" />
                  <button class="btn btn-primary search"
                    style="margin-bottom: 9px">
                    <i class="icon-search icon-white" title="查询"></i>查询
                  </button>
                </div>
              </div>

              <div style="float: right; margin-left: 10px; margin-top: 4px;">
                <c:if test="${canAdd}">
                <a id="btnAdd" class="btn btn-info"
                  onclick="window.location.href='${ctx}/space/ship/myShip/edit?id=0';">
                  <i class="icon-plus icon-white" title="添加"></i>添加
                </a>
                </c:if>
              </div>
            </div>
            <div class="widget-content nopadding">
              <c:if test="${flag}"> ${msg} </c:if>
              <c:if test="${empty flag || !flag}">
                <table class="table table-bordered data-table table-striped">
                  <thead>
                    <tr>
                      <th style="width: 12%">船舶图片</th>
                      <th style="width: 12%">船舶</th>
                      <th style="width: 10%">船东</th>
                      <th>动态</th>
                      <th style="width: 10%">状态</th>
                      <th style="width: 16%">操作</th>
                    </tr>
                  </thead>

                  <tbody>

                    <c:forEach var="myShipData" items="${pageData.result}">
                      <tr>
                        <td><a
                          href="${ctx}/portal/home/shipInfo?shipId=${myShipData.id}"
                          target="_blank"> <c:choose>
                              <c:when test="${!empty myShipData.shipLogo}">
                                <img
                                  src="${ctx}/download/imageDownload?url=${myShipData.shipLogo}"
                                  style="width: 80px; height: 60px;" alt=""
                                  class="thumbnail" />
                              </c:when>
                              <c:otherwise>
                                <img
                                  src="${ctx}/img/shipImage/${myShipData.shipType}.jpg"
                                  style="width: 80px; height: 60px;" alt=""
                                  class="thumbnail" />
                              </c:otherwise>
                            </c:choose>
                        </a></td>
                        <td>
                          ${myShipData.shipName}<br />
                          ${myShipData.mmsi}
                        </td>
                        <td><c:choose>
                            <c:when test="${!empty myShipData.master.trueName }">${myShipData.master.trueName}</c:when>
                            <c:otherwise>${myShipData.master.loginName}</c:otherwise>
                          </c:choose></td>
                        <td>${myShipData.arvlftDesc}</td>
                        <td>${myShipData.shipStatus.description}</td>
                        <td><c:if test="${myShipData.masterId == userData.id}">
                            <c:if test="${myShipData.shipStatus=='edit'}">
                              <a class="btn btn-primary"
                                onclick="window.location.href='${ctx}/space/ship/myShip/edit?id=${myShipData.id}';">
                                <i class="icon-pencil icon-white" title="修改"></i>
                              </a>
                              <a href="javascript:void(0);"
                                class="btn btn-danger btnDelete"
                                idVal="${myShipData.id}"> <i
                                class="icon-trash icon-white" title="删除"></i>
                              </a>
                              <a class="btn btn-success btnCommit"
                                idVal="${myShipData.id}"> <i
                                class="icon-ok-sign icon-white" title="提交审核"></i>
                              </a>
                            </c:if>

                            <c:if test="${myShipData.shipStatus=='commit'}">
                              <a class="btn btn-primary"
                                onclick="window.location.href='${ctx}/space/ship/myShip/edit?id=${myShipData.id}';">
                                <i class="icon-pencil icon-white" title="修改"></i>
                              </a>
                              <a href="javascript:void(0);"
                                class="btn btn-danger btnDelete"
                                idVal="${myShipData.id}"> <i
                                class="icon-trash icon-white" title="删除"></i>
                              </a>
                            </c:if>
                            <c:if test="${myShipData.shipStatus=='audit'}">
                              <a class="btn btn-primary"
                                onclick="window.location.href='${ctx}/space/ship/myShip/edit?id=${myShipData.id}';">
                                <i class="icon-pencil icon-white" title="修改"></i>
                              </a>
                              <a href="javascript:void(0);"
                                class="btn btn-danger btnDelete"
                                idVal="${myShipData.id}"> <i
                                class="icon-trash icon-white" title="删除"></i>
                              </a>
                              <a href="javascript:void(0);"
                                class="btn btn-success btnRelease"
                                idVal="${myShipData.id}"> <i
                                class="icon-chevron-up icon-white" title="发布"></i>
                              </a>
                            </c:if>
                            <c:if test="${myShipData.shipStatus=='publish'}">
                              <a href="javascript:void(0);"
                                class="btn btn-warning btnUnRelease"
                                idVal="${myShipData.id}"> <i
                                class="icon-chevron-down icon-white" title="取消发布"></i>
                              </a>
                              <a href="javascript:void(0);"
                                class="btn btn-success btnSet" idVal="${myShipData.id}"
                                pageNo="${pageNo}" keyWords="${keyWords}"> <i
                                class="icon-cog icon-white" title="设置坐标来源"></i>
                              </a>
                              <c:if test="3==5">
                                <!-- 控制权限按钮不显视 -->
                                <a href="javascript:void(0);"
                                  class="btn btn-info btnPower" idVal="${myShipData.id}"
                                  nameVal="${myShipData.shipName}"> <i
                                  class="icon-edit icon-white" title="权限"></i>
                                </a>
                              </c:if>
                            </c:if>
                            <%-- <a href="javascript:void(0);" class="btn btn-primary btnDues" idVal="${myShipData.id}" nameVal="${myShipData.shipName}">
                              <i class="elusive-hand-right icon-white" title="缴费"></i>
                            </a>
                            <a href="javascript:void(0);" class="btn btn-primary btnGasOrder" idVal="${myShipData.id}" nameVal="${myShipData.shipName}">
                              <i class="elusive-hand-right icon-white" title="加油"></i>
                            </a> --%>
                          </c:if>
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

  <!-- Modal auditDialog -->
  <div id="commitDialog" class="modal hide fade">
    <form class="form-horizontal" name="commitDialogForm"
      id="commitDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/space/ship/myShip/commit">
      <input type="hidden" name="id" id="commitId" value="" />
      <input type="hidden" name="deptId" value="${deptId}" />
      <input type="hidden" name="keyWords" value="${keyWords}" />
      <input type="hidden" name="pageNo" value="${pageNo}" />

      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>提交审核</h3>
      </div>
      <div class="modal-body">
        <p>你确认要提交该船舶信息吗？</p>
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

  <!-- Modal publishDialog -->
  <div id="publishDialog" class="modal hide fade">
    <form class="form-horizontal" name="publishDialogForm"
      id="publishDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/space/ship/myShip/publish">
      <input type="hidden" name="id" id="pubId" value="" />
      <input type="hidden" name="deptId" value="${deptId}" />
      <input type="hidden" name="keyWords" value="${keyWords}" />
      <input type="hidden" name="pageNo" value="${pageNo}" />
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
      <input type="hidden" name="id" id="unpubId" value="" />
      <input type="hidden" name="deptId" value="${deptId}" />
      <input type="hidden" name="keyWords" value="${keyWords}" />
      <input type="hidden" name="pageNo" value="${pageNo}" />
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
      <input type="hidden" name="_method" value="delete" />
      <input type="hidden" name="id" id="delId" value="" />
      <input type="hidden" name="deptId" value="${deptId}" />
      <input type="hidden" name="keyWords" value="${keyWords}" />
      <input type="hidden" name="pageNo" value="${pageNo}" />
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

  <!-- Modal importExcelDialog -->
  <div id="importExcelDialog" class="modal hide fade">
    <form class="form-horizontal" name="importExcelDialogForm"
      id="importExcelDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/space/ship/myShip/importShipExcel"
      enctype="multipart/form-data">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>导入船舶EXCEL文件</h3>
      </div>
      <div class="modal-body">
        <div class="control-group">
          <label class="control-label">船舶EXCEL：</label>
          <div class="controls">
            <input id="shipExcelFile" name="shipExcelFile"
              placeholder="请选择要导入的船舶EXCEL文件" value="" type="file">
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <button type="submit" class="btn btn-primary">
          <i class="icon icon-ok icon-white"></i> 确认
        </button>
      </div>
    </form>
  </div>

  <!-- set monitorDataSource -->
  <div id="setDataSourceDialog" class="modal hide fade">
    <form class="form-horizontal" name="setDataSourceDialog"
      id="setDataSourceDialog" novalidate="novalidate" method="get"
      action="${ctx}/space/ship/myShip/setPlant">
      <input type="hidden" name="shipId" id="shipId" value="0" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>设置监控数据源</h3>
      </div>
      <div class="modal-body">
        <p>请选择数据源 :</p>
        <div id="dataSource"></div>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <a class="btn btn-primary btnSetDataSouce"> <i
          class="icon icon-ok icon-white"></i> 确认
        </a>
      </div>
    </form>
  </div>

  <!-- Modal deleteDialog -->
  <div id="deleteCollectDialog" class="modal hide fade">
    <input type="hidden" name="_method" value="delete" /> <input
      type="hidden" name="id" id="delColId" value="" /> <input
      type="hidden" name="id" id="userId" value="${userData.id}" />
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal">×</button>
      <h3>删除确认</h3>
    </div>
    <div class="modal-body">
      <p>删除后将无法看到该船舶航次监控，你确认要删除该收藏的船舶信息吗？</p>
    </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 取消
      </button>
      <button class="btn btn-primary deleteCollect">
        <i class="icon icon-ok icon-white"></i> 确认
      </button>
    </div>
  </div>

  <!-- Modal showDialog -->
  <div id="btninDialog" class="modal hide fade">
    <form class="form-horizontal" name="btninDialogForm" role="form"
      id="btninDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/space/settle/inaccount">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>充值扣费-转入</h3>
      </div>
      <input type="hidden" id="userId" name="userId" value="${userData.id}" />
      <div class="modal-body">
        <div class="control-group">
          <label class="control-label">转账类型：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="giroType" value="转入"
              disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">转入金额(元)：</label>
          <div class="controls">
            <input type="number" class="input-medium" id="money" name="money"
              value=" " placeholder="保留两位小数" /><span class="color-red"></span>
          </div>
        </div>

        <div class="modal-footer">
          <button class="btn btn-primary btn_surein">
            <i class="icon-arrow-up icon-white"></i> 转入
          </button>
          <button class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </button>
        </div>
      </div>
    </form>
  </div>

</body>
</html>
