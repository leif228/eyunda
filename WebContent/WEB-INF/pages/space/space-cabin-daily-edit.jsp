<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css" class="skin-color" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/unicorn.tables.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/space/space-validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/space/space-cabin-daily-edit.js"></script>

<script type="text/javascript">
  var _rootPath = "${ctx}";

  $(document).ready(function() {
    // 时间一周后
    var d = new Date();
    d.setDate(d.getDate()+7);
 	// datepicker
 	$('[data-form=datepicker]').datepicker({
    	startDate:new Date(),
    	endDate:d,
    	todayHighlight:true
    });

    $("#startPortCity").trigger("change");
    $("#endPortCity").trigger("change");
  });
</script>
<style>
#content {
  margin-left: 0px;
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

.form-horizontal .control-label {
  width: 150px;
}

.form-horizontal .controls {
  margin-left: 170px;
}

.form-horizontal input[type=text], .form-horizontal input[type=password]
  {
  width: 200px
}

.select2-container {
  width: 200px
}

.user-info li {
  float: left;
  margin-left: 20px;
}

.form-horizontal .control-label {
  width: 100px;
}

.form-horizontal .controls {
  margin-left: 120px;
}

.form-horizontal .form-actions {
  padding-left: 0px;
  text-align: center;
}

#s2id_endPortNo, #s2id_startPortNo {
  width: 160px;
}

.modal{
	z-index: 1081 !important;
}
</style>
</head>

<body>

  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span9">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 船盘
    </h1>
    <div class="widget">
      <div class="widget-header">
        <h3>船盘编辑 : ${cabinData.description}</h3>
      </div>
      <!-- /widget-header -->

      <div class="widget-content">
        <div class="box-header corner-top"></div>

        <div class="tab-content">
          <div class="tab-pane active" id="order-edit2">
            <form class="form-horizontal" name="frmSaveAll" id="frmSaveAll" method="post" action="" novalidate="novalidate">

              <input type="hidden" id="waresBigType" name="waresBigType" value="${cabinData.waresBigType}" />
              <input type="hidden" id="waresType" name="waresType" value="${cabinData.waresType}" />
              <input type="hidden" id="cargoType" name="cargoType" value="${cabinData.cargoType}" />
              <input type="hidden" id="id" name="id" value="${cabinData.id}" />
              <input type="hidden" id="publisherId" name="publisherId" value="${cabinData.publisherId}" />
              <input type="hidden" id="brokerId" name="brokerId" value="${cabinData.brokerId}" />
              <input type="hidden" id="masterId" name="masterId" value="${cabinData.masterId}" />
              <input type="hidden" id="shipId" name="shipId" value="${cabinData.shipId}" />
              <input type="hidden" id="ports" name="ports" value="${cabinData.ports}" />
              <input type="hidden" id="status" name="status" value="${cabinData.status}" />

              <fieldset>
                <div class="row-fluid">
                  <div class="span6">
                    <div class="control-group">
                      <div style="text-align: center; background-color: #eee;">出租人：</div>
                      <div>
                        <div class="row-fluid"
                          style="width: 250px; margin-top: 0px">
                          <div class="pull-left">
                            <img id="masterUserLogo" alt="" class="thumbnail"
                              src="${ctx}/download/imageDownload?url=${cabinData.master.userLogo}"
                              style="width: 60px; height: 60px;" />
                          </div>
                          <div class="pull-left" style="margin-left: 10px">
                            <div id="masterTrueName">${cabinData.master.trueName}</div>
                            <div>
                              <c:if test="${cabinData.publisherId==userData.id}">
                              <a href="javascript:void(0);" class="btn btn-info"
                                id="btnChangeMaster" title="查找承运人">查找</a>
                              </c:if>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="span6">
                    <div class="control-group">
                      <div style="text-align: center; background-color: #eee;">船舶：</div>
                      <div>
                        <div class="account-avatar">
                          <div class="row-fluid"
                            style="width: 250px; margin-top: 0px">
                            <div class="pull-left">
                              <c:choose>
                                <c:when
                                  test="${!empty cabinData.shipData.shipLogo}">
                                  <img id="imgShipLogo" alt="" class="thumbnail"
                                    src="${ctx}/download/imageDownload?url=${cabinData.shipData.shipLogo}"
                                    style="width: 60px; height: 60px;" />
                                </c:when>
                                <c:otherwise>
                                  <img id="imgShipLogo" alt="" class="thumbnail"
                                    src="${ctx}/img/shipImage/${cabinData.shipData.shipType}.jpg"
                                    style="width: 60px; height: 60px;" />
                                </c:otherwise>
                              </c:choose>
                            </div>
                            <div class="pull-left" style="margin-left: 10px">
                              <div id="shipTypeName">
                                <c:if test="${!empty cabinData.shipData }">${cabinData.shipData.typeData.typeName}</c:if>
                              </div>
                              <div id="shipName" style="width: 170px">
                                <c:if test="${!empty cabinData.shipData }">${cabinData.shipData.shipName}</c:if>
                              </div>
                              <div>
                                <c:if test="${cabinData.publisherId==userData.id}">
                                <a href="javascript:void(0);" class="btn btn-info"
                                  id="btnFindShip" title="查找船舶">查找</a>
                                <!-- <a href="javascript:void(0);" class="btn btn-info"
                                  id="btnRemoveShip" title="移除船舶">移除</a> -->
                                </c:if>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="row-fluid">
                  <div class="span6">
                    <div class="control-group">
                      <label class="control-label" for="remark">租金率：</label>
                      <div class="controls">
                        <fmt:formatNumber var="fmtPrices" value="${cabinData.prices}" pattern="#.##"/>
                        <input type="text" style="width: 150px;" name="prices" value="${fmtPrices}">&nbsp;&nbsp;元/天
                      </div>
                    </div>
                  </div>
                  <div class="span6">
                    <div class="control-group">
                      <label class="control-label">燃油费计算：</label>
                      <div class="controls">
                        <fmt:formatNumber var="fmtOilPrice" value="${cabinData.oilPrice}" pattern="#.##"/>
                        <input type="text" style="width: 150px;" name="oilPrice" value="${fmtOilPrice}">&nbsp;&nbsp;元/公里
                      </div>
                    </div>
                  </div>
                </div>

                <div class="row-fluid">
                  <div class="span6">
                    <div class="control-group">
                      <label class="control-label">支付分期：</label>
                      <div class="controls">
                        <select id="paySteps" style="width: 160px;" name="paySteps">
                        <c:forEach var="st" begin="1" end="3" step="1" varStatus="vst">
                          <c:if test="${cabinData.paySteps==st}">
                          <option value="${st}" selected>${st}</option>
                          </c:if>
                          <c:if test="${cabinData.paySteps!=st}">
                          <option value="${st}">${st}</option>
                          </c:if>
                        </c:forEach>
                        </select>&nbsp;&nbsp;期
                      </div>
                    </div>
                  </div>
                  <div class="span6">
                    <div class="control-group">
                      <label class="control-label" for="upDate">受载日期：</label>
                      <div class="controls">
                        <div class="input-append date" data-form="datepicker"
                          data-date="${cabinData.startDate}"
                          data-date-format="yyyy-mm-dd">
                          <input id="startDate" name="startDate" class="grd-white"
                            data-form="datepicker"
                            style="width: 150px; border: 1px solid #cccccc;"
                            size="10" type="text" value="${cabinData.startDate}"
                            data-date-format="yyyy-mm-dd" /> <span class="add-on"><i
                            class="icon-th"></i></span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="row-fluid">
                  <div class="span12">

                    <div class="control-group">
                      <label class="control-label" for="remark">装卸港口：</label>
                      <div class="controls">
                        <div style="color: #ff0000;">说明：运营港口选中，非运营港口取消选中即可。</div>
                        <div>
                          <table class="table table-bordered">
                            <thead>
			                  <tr>
			                    <th style="width:50%;text-align:center;">
			                      <input type="checkbox" id="allUpDownPort" value="" />
			                      装卸港口
			                    </th>
			                    <th style="width:50%;text-align:center;">载重量(吨)</th>
			                  </tr>
			                </thead>
			                <tbody>
			                  <c:forEach items="${cabinData.upDownPortDatas}" var="upDownPortData">
			                  <tr>
			                    <td>
			                      <c:if test="${upDownPortData.gotoThisPort}">
			                      <input type="checkbox" name="gotoThisPort-${upDownPortData.startPortNo}" value="1" checked="checked" />
			                      </c:if>
			                      <c:if test="${!upDownPortData.gotoThisPort}">
			                      <input type="checkbox" name="gotoThisPort-${upDownPortData.startPortNo}" value="1" />
			                      </c:if>
			                      ${upDownPortData.startPortData.fullName}
			                    </td>
			                    <td>
			                      <input type="text" class="weight" style="width: 50px;" name="weight-${upDownPortData.startPortNo}" value="${upDownPortData.weight}" />
			                    </td>
			                  </tr>
			                  </c:forEach>
			                </tbody>
                          </table>
                        </div>
                      </div>
                    </div>

                    <div class="control-group">
                      <label class="control-label">特约条款：</label>
                      <div class="controls">
                        <textarea placeholder="请输入不超过2000个字的特约条款 ..." rows="8"
                         id="orderDesc" name="orderDesc">${cabinData.orderDesc}</textarea>
                      </div>
                    </div>

                  </div>
                </div>

                <div class="form-actions">
                  <a href="javascript:void(0);" class="btn btn-primary btnSave">保存</a>
                  <a href="javascript:void(0);" class="btn btn-warning btnBack">返回</a>
                </div>
              </fieldset>
            </form>
          </div>
        </div>
      </div>
      <!-- /widget-content -->

    </div>
  </div>
  </div>
  </div>
  </div>

  <jsp:include page="./space-foot.jsp"></jsp:include>

  <div id="dlgUserSelector" class="modal hide fade">
    <form novalidate="novalidate">
      <input type="hidden" id="objUserRole" name="objUserRole" value="owner" />
      <input type="hidden" id="objUserId" name="objUserId" value="0" />
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"
              aria-hidden="true">×</button>
            <h4 class="modal-title">查找用户</h4>
          </div>
          <div class="modal-body">
            <fieldset>
              <div class="control-group pdleft">
                <label class="control-label" for="inputCombinedIcon"
                  style="width: 530px; font-size: 14px;">请输入搜索词</label>
                <div class="controls" style="margin-left: 10px">
                  <div class="controls" style="margin-left: 10px">
                    <div class="input-append input-icon-prepend">
                      <div class="add-on" style="width: 400px">
                        <a title="search" class="icon"><i class="icofont-search"></i></a>
                        <input id="userinfoKeyWords"
                          placeholder="请输入用户真实姓名、电话、登录名或email" type="text"
                          class="grd-white" style="width: 360px" />
                      </div>
                      <a href="javascript:void(0);" id="btnFindUsers" class="btn"
                        style="height: 20px; line-heigth: 20px">查询</a>
                    </div>
                  </div>
                </div>
              </div>
              <div id="objUserList"></div>
            </fieldset>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" id="btnSelectUser">确认</button>
          </div>
        </div>
        <!-- /.modal-content -->
      </div>
      <!-- /.modal-dialog -->
    </form>
  </div>
  
  <!-- Modal dlgAddShip -->
  <div id="dlgAddShip" class="modal hide fade">
    <form class="form-horizontal" name="frmAddShip" id="frmAddShip" novalidate="novalidate" action="#">
      <input type="hidden" id="selShipId" name="selShipId" value="0" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>选择添加</h3>
      </div>
      <div class="modal-body">
        <fieldset>
          <div class="control-group pdleft">
            <label class="control-label" for="inputCombinedIcon"
              style="width: 45px; font-size: 14px;">船舶：</label>
            <div class="controls" style="margin-left: 10px">
              <div class="input-append input-icon-prepend">
                <div class="add-on" style="width: 400px">
                  <a title="search" class="icon"><i class="icofont-search"></i></a>
                  <input name="keyWords" id="keyWords" type="text" class="grd-white" style="width: 360px" />
                </div>
                <a href="javascript:void(0);" id="btnSerachShip" class="btn" style="height: 20px; line-heigth: 20px">查询</a>
              </div>
            </div>
          </div>
          <div id="shipList"></div>
        </fieldset>
      </div>
      <div class="modal-footer">
        <a href="javascript:void(0);" class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </a>
        <a href="javascript:void(0);" id="btnAddShip" class="btn btn-primary">
          <i class="icon icon-ok icon-white"></i> 确认
        </a>
      </div>
    </form>
  </div>

</body>
</html>
