<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<link rel="stylesheet"
  href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/space/space-ship-state-arvlft.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";

  $(function() {
    $('.form_datetimeStart').datetimepicker({
      //language:  'fr',
      weekStart : 1,
      todayBtn : 1,
      todayHighlight : 1,
      startView : 2,
      forceParse : 0,
      showMeridian : 1,
      minView : "month", //选择日期后，不会再跳转去选择时分秒 
      autoclose : true
    });

    $('.removeStartTime').click(function() {
      $('#startTime').val("");
    });

    $('.form_datetimeEnd').datetimepicker({
      //language:  'fr',
      weekStart : 1,
      todayBtn : 1,
      todayHighlight : 1,
      startView : 2,
      forceParse : 0,
      showMeridian : 1,
      minView : "month", //选择日期后，不会再跳转去选择时分秒 
      autoclose : true
    });

    $('.removeEndTime').click(function() {
      $('#endTime').val("");
    });
  });
</script>
<style>
#content {
  margin-left: 0px;
}

.styleCenter {
  margin-top: 4px;
}

#dlgAdd .user-info {
  list-style: none;
}

#dlgAdd .account-container {
  padding: 3px;
}

#dlgAdd .user-info>li {
  float: left;
  margin: 10px;
}

#dlgAdd .account-container:hover {
  padding: 3px;
  background: #00CCFF;
  cursor: pointer
}

.addBack {
  background: #00CCFF;
}

.detail-descrip {
  width: 100%;
  margin-left: 50px;
  margin-bottom: 20px;
}

.detail-descrip-info {
  width: 620px;
}

.detail-descrip-text {
  
}

.form-horizontal .control-group {
  border: 0px;
}

.form-horizontal input[type=text], .form-horizontal input[type=password]
  {
  width: 200px
}

.select2-container {
  width: 200px
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
        <form name="pageform" id="pageform"
          action="${ctx}/space/state/myShip/shipArvlft">
          <input type="hidden" name="mmsi" id="mmsi" value="${mmsi}" />
          <div class="widget-box">
            <div class="widget-title">
              <div style="float: left;">
                <h5>${shipName}船舶动态</h5>
              </div>
              <div class="control-group styleCenter"
                style="float: left; width: 280px; margin-left: 10px;">
                <label class="control-label" style="float: left; width: 80px;">
                  <span style="line-height: 28px;">起始日期：</span>
                </label>
                <div style="margin-left: 10px;"
                  class="controls input-append date form_datetimeStart"
                  data-date="" data-date-format="yyyy-mm-dd"
                  data-link-field="dtp_input1">
                  <input size="16" type="text" name="startTime" id="startTime"
                    style="width: 120px; margin-bottom: 0px;" value="${startTime}"
                    placeholder="请输入起始查询时间!" /> <span class="add-on"><i
                    class="icon-trash removeStartTime"></i></span> <span class="add-on"><i
                    class="icon-th"></i></span>
                </div>
                <input type="hidden" id="dtp_input1" value="" />
              </div>
              <div class="control-group styleCenter"
                style="float: left; width: 280px; margin-left: 10px;">
                <label class="control-label" style="float: left; width: 80px;">
                  <span style="line-height: 28px;">终止日期：</span>
                </label>
                <div style="margin-left: 10px;"
                  class="controls input-append date form_datetimeEnd"
                  data-date="" data-date-format="yyyy-mm-dd"
                  data-link-field="dtp_input2">
                  <input size="16" type="text" name="endTime" id="endTime"
                    style="width: 120px; margin-bottom: 0px;" value="${endTime}"
                    placeholder="请输入终止查询时间!" /> <span class="add-on"><i
                    class="icon-trash removeEndTime"></i></span> <span class="add-on"><i
                    class="icon-th"></i></span>
                </div>
                <input type="hidden" id="dtp_input2" value="" />
              </div>
              <button class="btn btn-info findCargo styleCenter" title="查询">
                <i class=" icon-search icon-white"></i>
              </button>
              <div class="pull-right styleCenter">

                <c:if test="${flag}">
                  <a id="btnAdd" class="btn btn-info" title="添加"
                    onclick="window.location.href='${ctx}/space/state/myShip/shipArvlft/editShipArvlft?id=0&mmsi=${mmsi}';">
                    <i class="icon-plus icon-white"></i>添加
                  </a>
                </c:if>

                <a href="javascript:void(0);" id="exportStateExcel"
                  class="btn btn-info" title="导出"> <i
                  class="icon-list-alt icon-white"></i>导出
                </a>
              </div>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th width="13%">港口</th>
                    <th width="12%">到港时间</th>
                    <th width="24%">卸货</th>
                    <th width="12%">离港时间</th>
                    <th width="24%">装货</th>
                    <!-- <th width="8%">下一港口</th>
                    <th width="5%">备注</th> -->
                    <th width="15%">操作</th>
                  </tr>
                </thead>

                <tbody>
                  <c:set var="arvlftShipDatas" value="${pageData.result}"/>
                  <c:set var="dSize" value="${fn:length(arvlftShipDatas)}"/>
                  <c:forEach items="${arvlftShipDatas}" varStatus="st">
                    <c:set var="arvlftShipData" value="${arvlftShipDatas[dSize - st.index - 1]}"/>
                    <tr>
                      <td>${arvlftShipData.arriveData.portData.fullName}</td>
                      <td>${arvlftShipData.arriveData.arvlftTime}</td>
                      <td>
                        <div>
                          <c:if test="${flag}">
                            <div class="pull-left">
                              <a onclick="window.location.href='${ctx}/space/state/myShip/shipArvlft/listUpdown?id=${arvlftShipData.arriveData.id}&mmsi=${mmsi}';"
                                class="btn btn-primary"
                                title="${arvlftShipData.arriveData.arvlft.updown}"><i
                                class="icon-arrow-down icon-white"></i></a>
                            </div>
                          </c:if>
                          <div style="margin-left: 50px;">
                            <c:if test="${!empty arvlftShipData.arriveData.shipUpdownDatas}">
                              <c:forEach var="shipUpdownData" items="${arvlftShipData.arriveData.shipUpdownDatas}">
                              ${shipUpdownData.cargoNameDesc}:${shipUpdownData.tonTeuDesc}
                              <br />
                              </c:forEach>
                            </c:if>
                          </div>
                        </div>
                      </td>
                      <td>${arvlftShipData.leftData.arvlftTime}</td>
                      <td>
                        <div>
                          <c:if test="${flag}">
                            <div class="pull-left">
                              <a onclick="window.location.href='${ctx}/space/state/myShip/shipArvlft/listUpdown?id=${arvlftShipData.leftData.id}&mmsi=${mmsi}';"
                                class="btn btn-primary"
                                title="${arvlftShipData.leftData.arvlft.updown}"><i
                                class="icon-arrow-up icon-white"></i></a>
                            </div>
                          </c:if>
                          <div style="margin-left: 50px;">
                            <c:if test="${!empty arvlftShipData.leftData.shipUpdownDatas}">
                              <c:forEach var="shipUpdownData" items="${arvlftShipData.leftData.shipUpdownDatas}">
                              ${shipUpdownData.cargoNameDesc}:${shipUpdownData.tonTeuDesc}
                              <br />
                              </c:forEach>
                            </c:if>
                          </div>
                        </div>
                      </td>
                      <%-- <td>${arvlftShipData.leftData.goPortData.fullName}</td>
                      <td>${arvlftShipData.arriveData.remark}</td> --%>
                      <td style="text-align: center">
                        <div class="center">
                          <a class="btn btn-primary historyRoute" style="margin-left: 3px" target="_blank"
                             href="${ctx}/space/state/routePlay?shipId=${arvlftShipData.shipData.id}&arvlftId=${arvlftShipData.leftData.id}">
                            <i class="icon-map-marker icon-white" title="航次回放"></i>
                          </a>
                          <c:if test="${flag}">
                            <a onclick="window.location.href='${ctx}/space/state/myShip/shipArvlft/editShipArvlft?id=${arvlftShipData.arriveData.id}&mmsi=${mmsi}';"
                              class="btn btn-primary" title="修改"> <i
                              class="icon-pencil icon-white"></i>
                            </a>
                          </c:if>
                          <c:if test="${flag && arvlftShipData.leftData.status == 'yes'}">
                            <a href="javascript:void(0);"
                              class="btn btn-danger btnRemoveLastShipArvlft" title="删除"
                              idVal="${arvlftShipData.arriveData.id}"> <i
                              class="icon-trash icon-white"></i>
                            </a>
                          </c:if>
                        </div>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
            <jsp:include page="./pager.jsp"></jsp:include>
          </div>
        </form>
      </div>

    </div>
  </div>

  <jsp:include page="./space-foot.jsp"></jsp:include>

  <!-- Modal deleteDialog -->
  <div id="removeLastShipArvlftDialog" class="modal hide fade">
    <form class="form-horizontal" name="removeLastShipArvlftDialogForm"
      id="removeLastShipArvlftDialogForm" novalidate="novalidate"
      method="post"
      action="${ctx}/space/state/myShip/shipArvlft/delShipArvlft">
      <input type="hidden" name="mmsi" id="removeMmsi" value="" /> <input
        type="hidden" name="arriveId" id="removeArriveId" value="" /> <input
        type="hidden" name="_method" value="delete" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>删除确认</h3>
      </div>
      <div class="modal-body">
        <p>你确认要删除该条上报信息吗？</p>
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
