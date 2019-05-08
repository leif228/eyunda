<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<script src="${ctx}/js/unicorn.tables.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/space/space-validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/space/space-cargo-edit.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";

  $(document).ready(function() {

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
</style>
</head>

<body>
  <jsp:include page="./space-head.jsp"></jsp:include>
  <div class="span10">
    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 货物管理
    </h1>

  <div class="row">

    <div class="span10">

      <div class="widget">

        <div class="widget-header">
          <c:if test="${cargoData.cargoType.cargoBigType=='container'}">
          <h3>货物编辑 - 集装箱</h3>
          </c:if>
          <c:if test="${cargoData.cargoType.cargoBigType!='container'}">
          <h3>货物编辑 - ${cargoData.cargoType.description}</h3>
          </c:if>
        </div>
        <!-- /widget-header -->
        <div class="widget-content">
          <div class="box-header corner-top"></div>
          <br />
          <div class="tab-content">
            <div class="tab-pane active" id="cargo-edit1">
              <form class="form-horizontal" name="editDialogForm"
                id="editDialogForm" novalidate="novalidate" method="post"
                enctype="multipart/form-data"
                action="${ctx}/space/cargo/save">

                <input type="hidden" name="id" id="id" value="${cargoData.id}" />
                <input type="hidden" name="publisherId" value="${userData.id}" />
                <input type="hidden" name="cargoType" value="${cargoData.cargoType}" />

                <fieldset>
                  <div class="control-group">
                    <label class="control-label"></label>
                    <div class="controls">
                      <div class="account-avatar">
                        <img
                          src="${ctx}/download/imageDownload?url=${cargoData.cargoImage}"
                          alt="" class="thumbnail"
                          style="width: 100px; height: 100px;">
                      </div>
                    </div>
                  </div>

                  <div class="control-group">
                    <label class="control-label">货物图片：</label>
                    <div class="controls">
                      <input type="file" id="cargoImageFile" name="cargoImageFile"
                        value="" />
                    </div>
                  </div>
                  
                  <div class="control-group">
                    <label class="control-label" for="shipName">起始港口：</label>
                    <div class="controls">
                      <span class="startPortCityName"> <select
                        id="startPortCity" name="startPortCity"
                        style="width: 220px;">
                          <c:forEach var="bigArea" items="${bigAreas}">
                            <optgroup label="${bigArea.description}">
                              <c:forEach var="portCity" items="${bigArea.portCities}">
                                <c:if
                                  test="${cargoData.startPortData.portCity.code==portCity.code}">
                                  <option value="${portCity.code}" selected>${portCity.description}</option>
                                </c:if>
                                <c:if
                                  test="${cargoData.startPortData.portCity.code!=portCity.code}">
                                  <option value="${portCity.code}">${portCity.description}</option>
                                </c:if>
                              </c:forEach>
                            </optgroup>
                          </c:forEach>
                      </select>
                      </span> <span id="startPortName"> <select id="startPortNo"
                        name="startPortNo" style="width: 220px;">
                          <c:forEach var="startPortData" items="${startPortDatas}">
                            <c:if
                              test="${cargoData.startPortData.portNo==startPortData.portNo}">
                              <option value="${startPortData.portNo}" selected>${startPortData.portName}</option>
                            </c:if>
                            <c:if
                              test="${cargoData.startPortData.portNo!=startPortData.portNo}">
                              <option value="${startPortData.portNo}">${startPortData.portName}</option>
                            </c:if>
                          </c:forEach>
                      </select>
                      </span> <a href="javascript:void(0);"
                        class="btn btn-danger btnAddPort"> <i
                        class="icon-plus icon-white"></i>新增港口
                      </a>
                    </div>
                  </div>
                  <div class="control-group">
                    <label class="control-label" for="shipName">卸货港口：</label>
                    <div class="controls">
                      <span class="endPortCityName"> <select
                        id="endPortCity" name="endPortCity" style="width: 220px;">
                          <c:forEach var="bigArea" items="${bigAreas}">
                            <optgroup label="${bigArea.description}">
                              <c:forEach var="portCity" items="${bigArea.portCities}">
                                <c:if
                                  test="${cargoData.endPortData.portCity.code==portCity.code}">
                                  <option value="${portCity.code}" selected>${portCity.description}</option>
                                </c:if>
                                <c:if
                                  test="${cargoData.endPortData.portCity.code!=portCity.code}">
                                  <option value="${portCity.code}">${portCity.description}</option>
                                </c:if>
                              </c:forEach>
                            </optgroup>
                          </c:forEach>
                      </select>
                      </span> <span id="endPortName"> <select id="endPortNo"
                        name="endPortNo" style="width: 220px;">
                          <c:forEach var="endPortData" items="${endPortDatas}">
                            <c:if
                              test="${cargoData.endPortData.portNo==endPortData.portNo}">
                              <option value="${endPortData.portNo}" selected>${endPortData.portName}</option>
                            </c:if>
                            <c:if
                              test="${cargoData.endPortData.portNo!=endPortData.portNo}">
                              <option value="${endPortData.portNo}">${endPortData.portName}</option>
                            </c:if>
                          </c:forEach>
                      </select>
                      </span>
                    </div>
                  </div>

                  <c:if
                      test="${cargoData.cargoType.cargoBigType=='container'}">

                      <div class="row-fluid">
                        <div class="span12">
                          <div style="text-align: center; background-color: #eee;">货量：</div>
                          <div id="cargoContainer">
                            <div class="control-group specificaOrCargoName">
                              <label class="control-label">规格：</label>
                              <div class="controls">
                                <c:forEach var="mapCargoName"
                                  items="${cargoData.mapCargoNames}">
                                  <input type="text" style="width: 80px;"
                                    name="cargoName-${mapCargoName.key}"
                                    value="${mapCargoName.value}" />
                                </c:forEach>
                              </div>
                            </div>

                            <div class="control-group tonOrBox">
                              <label class="control-label">箱量(个)：</label>
                              <div class="controls">
                                <c:forEach var="mapTonTeu"
                                  items="${cargoData.mapTonTeus}">
                                  <fmt:formatNumber var="mapValue"
                                    value="${mapTonTeu.value}" pattern="#" />
                                  <input type="text" placeholder="请输入数字"
                                    style="width: 80px;" id="tonTeu-${mapTonTeu.key}"
                                    name="tonTeu-${mapTonTeu.key}" value="${mapValue}" />
                                </c:forEach>
                              </div>
                            </div>

                            <div class="control-group unitPriceTonOrBox">
                              <label class="control-label">报价(元/个)：</label>
                              <div class="controls">
                                <c:forEach var="mapPrice"
                                  items="${cargoData.mapPrices}">
                                  <fmt:formatNumber var="mapValue"
                                    value="${mapPrice.value}" pattern="#.##" />
                                  <input type="text" placeholder="请输入数字" 
                                    style="width: 80px;" id="price-${mapPrice.key}"
                                    name="price-${mapPrice.key}" value="${mapValue}" />
                                </c:forEach>
                              </div>
                            </div>
                            <!-- 规格属性字符串 -->
                            <input type="hidden" name="cargoNames" value="">
                            <!-- 箱量(个)属性字符串 -->
                            <input type="hidden" name="tonTeus" value="">
                            <!-- 运价(元/个)属性字符串 -->
                            <input type="hidden" name="prices" value="">
                          </div>
                        </div>
                      </div>

                      <div class="row-fluid">
                        <div class="span12">
                          <div class="control-group">
                            <label class="control-label">总运费：</label>
                            <div class="controls">
                              <fmt:formatNumber var="fmtTransFee"
                                value="${cargoData.transFee}" pattern="#.##" />
                              <input placeholder="请输入有效数字" type="text" readonly="readonly"
                                class="data-empty" name="transFee" id="transFee"
                                value="${fmtTransFee}" style="width: 150px;" />&nbsp;&nbsp;元
                            </div>
                          </div>
                        </div>
                      </div>
                    </c:if>

                    <c:if
                      test="${cargoData.cargoType.cargoBigType!='container'}">
                      <div class="row-fluid">
                        <div class="span6">
                          <div id="cargoBulkOrDanger">
                            <div class="control-group specificaOrCargoName">
                              <label class="control-label">货名：</label>
                              <div class="controls">
                                <input type="text" style="width: 150px;"
                                  name="cargoNames" value="${cargoData.cargoNames}" />
                              </div>
                            </div>
                          </div>
                        </div>

                        <div class="span6">
                          <div class="control-group tonOrBox">
                            <label class="control-label">货量：</label>
                            <div class="controls">
                              <fmt:formatNumber var="fmtTonTeus"
                                value="${cargoData.tonTeus}" pattern="#" />
                              <input type="text" placeholder="请输入数字"
                                style="width: 150px;" id="tonTeus" name="tonTeus"
                                value="${fmtTonTeus}" />&nbsp;&nbsp;吨
                            </div>
                          </div>
                        </div>
                      </div>

                      <div class="row-fluid">
                        <div class="span6">
                          <div class="control-group unitPriceTonOrBox">
                            <label class="control-label">运价：</label>
                            <div class="controls">
                              <fmt:formatNumber var="fmtPrices"
                                value="${cargoData.prices}" pattern="#.##" />
                              <input type="text" placeholder="请输入数字" 
                                style="width: 150px;" id="prices" name="prices"
                                value="${fmtPrices}" />&nbsp;&nbsp;元/吨
                            </div>
                          </div>
                        </div>

                        <div class="span6">
                          <div class="control-group">
                            <label class="control-label">总运费：</label>
                            <div class="controls">
                              <fmt:formatNumber var="fmtTransFee"
                                value="${cargoData.transFee}" pattern="#.##" />
                              <input placeholder="请输入有效数字" type="text" readonly="readonly"
                                class="data-empty" name="transFee" id="transFee"
                                value="${fmtTransFee}" style="width: 150px;" />&nbsp;&nbsp;元
                            </div>
                          </div>
                        </div>
                      </div>
                    </c:if>

                  <br />
                  <div class="form-actions">
                    <a class="btn btn-primary saveMyCargo">保存</a> 
                    <a href="javascript:window.history.go(-1);" class="btn btn-warning">返回</a>
                    <!--  <a href="javascript:void(0);" class="btn btn-warning btnBack">返回</a>-->
                  </div>
                  <!-- /form-actions -->
                </fieldset>
              </form>
            </div>

          </div>
        </div>
      </div>
      <!-- /widget-content -->

    </div>
    <!-- /widget -->

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

  <div id="addPortDialog" class="modal hide fade">
    <form class="form-horizontal" name="frmNewPortInfo"
      id="frmNewPortInfo" novalidate="novalidate" method="post"
      action="${ctx}/space/ship/port/saveNewPort">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>编辑</h3>
      </div>
      <div class="modal-body">
        <fieldset>

          <div id="selContent">
            <select id="portCityCode" name="portCityCode">
              <c:forEach var="bigArea" items="${bigAreas}">
                <optgroup label="${bigArea.description}">
                  <c:forEach var="portCity" items="${bigArea.portCities}">
                    <option value="${portCity.code}">${portCity.description}</option>
                  </c:forEach>
                </optgroup>
              </c:forEach>
            </select>
          </div>

          <div class="control-group">
            <label class="control-label">港口名称：</label>
            <div class="controls">
              <input type="hidden" name="portNo" id="portNo" value="" /> <input
                type="text" name="portName" id="portName" value=""
                style="width: 264px;" />
            </div>
          </div>

        </fieldset>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <a href="javascript:void(0);"
          class="btn btn-primary btnSaveNewPortInfo"> <i
          class="icon icon-ok icon-white"></i> 保存
        </a>
      </div>
    </form>
  </div>

</body>
</html>
