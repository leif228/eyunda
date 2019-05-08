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
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/space/space-order-voyageEdit.js"></script>
<script src="${ctx}/js/space/space-validate.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
  var _cargoType = "${orderCommonData.orderType.cargoType}";

  $(document).ready(function() {
    $('[data-form=datepicker]').datepicker();
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

#dlgUserSelector .user-info {
  list-style: none;
}

#dlgUserSelector .account-container {
  padding: 3px;
}

#dlgUserSelector .user-info>li {
  float: left;
  margin: 10px;
}

#dlgUserSelector .account-container:hover {
  padding: 3px;
  background: #00CCFF;
  cursor: pointer
}
</style>
</head>

<body>

  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span9">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 合同管理
    </h1>
    <div>

      <div>

        <div class="widget">
          <div class="widget-header">
            <h3>
              ${orderCommonData.orderType.description}
              <c:if test="${orderCommonData.id > 0}">(合同号：${orderCommonData.id})</c:if>
              <c:if test="${orderCommonData.id <= 0}"> (新建合同)</c:if>
            </h3>
          </div>
          <!-- /widget-header -->

          <div class="widget-content">
            <div class="box-header corner-top"></div>

            <div class="tab-content">
              <div class="tab-pane active" id="order-edit2">
                <form class="form-horizontal" name="frmSaveAll" id="frmSaveAll"
                  method="post" action="${ctx}/space/orderCommon/orderSave"
                  novalidate="novalidate">

                  <input type="hidden" id="id" name="id"
                    value="${orderCommonData.id}" /> <input type="hidden"
                    id="ownerId" name="ownerId" value="${orderCommonData.ownerId}" />
                  <input type="hidden" id="masterId" name="masterId"
                    value="${orderCommonData.masterId}" /> <input type="hidden"
                    id="brokerId" name="brokerId"
                    value="${orderCommonData.brokerId}" /> <input type="hidden"
                    id="handlerId" name="handlerId"
                    value="${orderCommonData.handlerId}" /> <input type="hidden"
                    id="shipId" name="shipId" value="${orderCommonData.shipId}" />
                  <input type="hidden" id="orderType" name="orderType"
                    value="${orderCommonData.orderType}" /> <input type="hidden"
                    id="startPortNo" name="startPortNo"
                    value="${orderCommonData.startPortNo}" /> <input
                    type="hidden" id="endPortNo" name="endPortNo"
                    value="${orderCommonData.endPortNo}" />

                  <fieldset>

                    <div class="orderCargoInfo">
                      <div class="row-fluid">

                        <div class="span4">
                          <div class="control-group">
                            <div style="text-align: center; background-color: #eee;">托运人：</div>
                            <div>
                              <div class="account-avatar">
                                <div class="row-fluid"
                                  style="width: 250px; margin-top: 0px">
                                  <div class="pull-left">
                                    <img id="ownerUserLogo" alt="" class="thumbnail"
                                      src="${ctx}/download/imageDownload?url=${orderCommonData.owner.userLogo}"
                                      style="width: 60px; height: 60px;" />
                                  </div>
                                  <div class="pull-left" style="margin-left: 10px">
                                    <div id="ownerTrueName">${orderCommonData.owner.trueName}</div>
                                    <div>
                                      <c:if test="${orderCommonData.handlerId==userData.id}">
                                      <a href="javascript:void(0);" class="btn btn-info"
                                        id="btnChangeOwner" title="查找托运人">查找</a>
                                      </c:if>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="span4">
                          <div class="control-group">
                            <div style="text-align: center; background-color: #eee;">承运人：</div>
                            <div>
                              <div class="row-fluid"
                                style="width: 250px; margin-top: 0px">
                                <div class="pull-left">
                                  <img id="masterUserLogo" alt="" class="thumbnail"
                                    src="${ctx}/download/imageDownload?url=${orderCommonData.master.userLogo}"
                                    style="width: 60px; height: 60px;" />
                                </div>
                                <div class="pull-left" style="margin-left: 10px">
                                  <div id="masterTrueName">${orderCommonData.master.trueName}</div>
                                  <div>
                                    <c:if test="${orderCommonData.handlerId==userData.id}">
                                    <a href="javascript:void(0);" class="btn btn-info"
                                      id="btnChangeMaster" title="查找承运人">查找</a>
                                    </c:if>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="span4">
                          <div class="control-group">
                            <div style="text-align: center; background-color: #eee;">船舶：</div>
                            <div>
                              <div class="account-avatar">
                                <div class="row-fluid"
                                  style="width: 250px; margin-top: 0px">
                                  <div class="pull-left">
                                    <c:choose>
                                      <c:when
                                        test="${!empty orderCommonData.shipData.shipLogo}">
                                        <img id="imgShipLogo" alt="" class="thumbnail"
                                          src="${ctx}/download/imageDownload?url=${orderCommonData.shipData.shipLogo}"
                                          style="width: 60px; height: 60px;" />
                                      </c:when>
                                      <c:otherwise>
                                        <img id="imgShipLogo" alt="" class="thumbnail"
                                          src="${ctx}/img/shipImage/${orderCommonData.shipData.shipType}.jpg"
                                          style="width: 60px; height: 60px;" />
                                      </c:otherwise>
                                    </c:choose>
                                  </div>
                                  <div class="pull-left" style="margin-left: 10px">
                                    <div id="shipTypeName">
                                      <c:if test="${!empty orderCommonData.shipData }">${orderCommonData.shipData.typeData.typeName}</c:if>
                                    </div>
                                    <div id="shipName" style="width: 170px">
                                      <c:if test="${!empty orderCommonData.shipData }">${orderCommonData.shipData.shipName}</c:if>
                                    </div>
                                    <div>
                                      <c:if test="${orderCommonData.handlerId==userData.id}">
                                      <a href="javascript:void(0);" class="btn btn-info"
                                        id="btnFindShip" title="查找船舶">查找</a>
                                      <a href="javascript:void(0);" class="btn btn-info"
                                        id="btnRemoveShip" title="移除船舶">移除</a>
                                      </c:if>
                                    </div>
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
                          <label class="control-label">航线：</label>
                          <div class="controls">
                            <select id="sailLine" name="sailLine" 
                              style="width: 260px;">
                              <c:set var="sailLineNo">${orderCommonData.startPortNo}-${orderCommonData.endPortNo}</c:set>
                              <c:forEach var="sailLineData" items="${sailLineDatas}">
                                <c:if test="${sailLineNo == sailLineData.sailLineNo}">
                                  <option value="${sailLineData.sailLineNo}" distanceVal="${sailLineData.distance}" weightVal="${sailLineData.weight}" 
                                  <c:forEach var="mapPrice" items="${sailLineData.mapPrices}">
                                  <c:if test="${orderCommonData.orderType.cargoType=='container20e'}">
                                    price-${mapPrice.key}Val="${mapPrice.value}"
                                  </c:if>
                                  <c:if test="${orderCommonData.orderType.cargoType!='container20e'}">
                                    pricesVal="${mapPrice.value}"
                                  </c:if>
                                  </c:forEach> 
                                  selected>${sailLineData.startPortData.fullName} - ${sailLineData.endPortData.fullName}</option>
                                </c:if>
                                <c:if test="${sailLineNo != sailLineData.sailLineNo}">
                                  <option value="${sailLineData.sailLineNo}" distanceVal="${sailLineData.distance}" weightVal="${sailLineData.weight}" 
                                  <c:forEach var="mapPrice" items="${sailLineData.mapPrices}">
                                  <c:if test="${orderCommonData.orderType.cargoType=='container20e'}">
                                    price-${mapPrice.key}Val="${mapPrice.value}"
                                  </c:if>
                                  <c:if test="${orderCommonData.orderType.cargoType!='container20e'}">
                                    pricesVal="${mapPrice.value}"
                                  </c:if>
                                  </c:forEach> 
                                  >${sailLineData.startPortData.fullName} - ${sailLineData.endPortData.fullName}</option>
                                </c:if>
                              </c:forEach>
                            </select>
                          </div>
                        </div>
                      </div>
                      <div class="span6">
                        <div id="cargoBulkOrDanger">
                          <div class="control-group specificaOrCargoName">
                            <label class="control-label">航程：</label>
                            <div class="controls">
                              <input type="text" style="width: 150px;" readonly="readonly" id="distance"
                                name="distance" value="${orderCommonData.distance}" />&nbsp;&nbsp;公里
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>

                    <div class="row-fluid">
                      <div class="span6">
                        <div id="cargoBulkOrDanger">
                          <div class="control-group specificaOrCargoName">
                            <label class="control-label">载箱量：</label>
                            <div class="controls">
                              <input type="text" style="width: 150px;" readonly="readonly"
                                id="containerCount" name="containerCount"
                                value="${orderCommonData.containerCount}" />&nbsp;&nbsp;TEU
                            </div>
                          </div>
                        </div>
                      </div>

                      <div class="span6">
                        <div class="control-group tonOrBox">
                          <label class="control-label">载重量：</label>
                          <div class="controls">
                            <input type="text" style="width: 150px;" id="weight" readonly="readonly"
                              name="weight" value="${orderCommonData.weight}" />&nbsp;&nbsp;吨
                          </div>
                        </div>
                      </div>
                    </div>
                    
                    <div class="row-fluid">
                      <div class="span6">
                        <div class="control-group">
                          <label class="control-label">滞期费率：</label>
                          <div class="controls">
                            <fmt:formatNumber var="fmtDemurrage"
                              value="${orderCommonData.demurrage}" pattern="#.##" />
                            <input placeholder="请输入有效数字" type="text" <c:if test="${orderCommonData.handlerId!=userData.id}">readonly="readonly"</c:if>
                              class="data-empty" name="demurrage" id="demurrage"
                              value="${fmtDemurrage}" style="width: 150px;" />&nbsp;&nbsp;元/船.天
                          </div>
                        </div>
                      </div>

                      <div class="span6">
                        <div class="control-group">
                          <label class="control-label">付款分期数：</label>
                          <div class="controls">
                            <select id="paySteps" name="paySteps" <c:if test="${orderCommonData.handlerId!=userData.id}">readonly="readonly"</c:if>
                              style="width: 160px;">
                              <c:forEach var="st" begin="1" end="3" step="1"
                                varStatus="vst">
                                <c:if test="${orderCommonData.paySteps==st}">
                                  <option value="${st}" selected>${st}</option>
                                </c:if>
                                <c:if test="${orderCommonData.paySteps!=st}">
                                  <option value="${st}">${st}</option>
                                </c:if>
                              </c:forEach>
                            </select>&nbsp;&nbsp;期
                          </div>
                        </div>
                      </div>
                    </div>

                    <div class="row-fluid">
                      <div class="span6">
                        <div class="control-group">
                          <label class="control-label" for="upDate">受载开始日期：</label>
                          <div class="controls">
                            <div class="input-append date" data-form="datepicker"
                              data-date="${orderCommonData.upDate}"
                              data-date-format="yyyy-mm-dd">
                              <input id="upDate" name="upDate" class="grd-white"
                                data-form="datepicker"
                                style="width: 150px; border: 1px solid #cccccc;"
                                size="10" type="text" value="${orderCommonData.upDate}"
                                data-date-format="yyyy-mm-dd" /> <span class="add-on"><i
                                class="icon-th"></i></span>
                            </div>
                          </div>
                        </div>
                      </div>

                      <div class="span6">
                        <div class="control-group">
                          <label class="control-label" for="downDate">受载结束日期：</label>
                          <div class="controls">
                            <div class="input-append date" data-form="datepicker"
                              data-date="${orderCommonData.downDate}"
                              data-date-format="yyyy-mm-dd">
                              <input id="downDate" name="downDate" class="grd-white"
                                data-form="datepicker"
                                style="width: 150px; border: 1px solid #cccccc;"
                                size="10" type="text"
                                value="${orderCommonData.downDate}"
                                data-date-format="yyyy-mm-dd" /> <span class="add-on"><i
                                class="icon-th"></i></span>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>

                    <c:if
                      test="${orderCommonData.orderType.cargoType.cargoBigType=='container'}">

                      <div class="row-fluid">
                        <div class="span12">
                          <div style="text-align: center; background-color: #eee;">货量：</div>
                          <div id="cargoContainer">
                            <div class="control-group specificaOrCargoName">
                              <label class="control-label">规格：</label>
                              <div class="controls">
                                <c:forEach var="mapCargoName"
                                  items="${orderCommonData.mapCargoNames}">
                                  <input type="text" style="width: 80px;"
                                    name="cargoName-${mapCargoName.key}" readonly="true"
                                    value="${mapCargoName.value}" />
                                </c:forEach>
                              </div>
                            </div>

                            <div class="control-group tonOrBox">
                              <label class="control-label">箱量(个)：</label>
                              <div class="controls">
                                <c:forEach var="mapTonTeu"
                                  items="${orderCommonData.mapTonTeus}">
                                  <fmt:formatNumber var="mapValue"
                                    value="${mapTonTeu.value}" pattern="#" />
                                  <input type="text" placeholder="请输入数字"
                                    style="width: 80px;" id="tonTeu-${mapTonTeu.key}"
                                    name="tonTeu-${mapTonTeu.key}" value="${mapValue}" />
                                </c:forEach>
                              </div>
                            </div>

                            <div class="control-group unitPriceTonOrBox">
                              <label class="control-label">运价(元/个)：</label>
                              <div class="controls">
                                <c:forEach var="mapPrice"
                                  items="${orderCommonData.mapPrices}">
                                  <fmt:formatNumber var="mapValue"
                                    value="${mapPrice.value}" pattern="#.##" />
                                  <input type="text" placeholder="请输入数字" <c:if test="${orderCommonData.handlerId!=userData.id}">readonly="readonly"</c:if>
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
                                value="${orderCommonData.transFee}" pattern="#.##" />
                              <input placeholder="请输入有效数字" type="text" readonly="readonly"
                                class="data-empty" name="transFee" id="transFee"
                                value="${fmtTransFee}" style="width: 150px;" />&nbsp;&nbsp;元
                            </div>
                          </div>
                        </div>
                      </div>
                    </c:if>

                    <c:if
                      test="${orderCommonData.orderType.cargoType.cargoBigType!='container'}">
                      <div class="row-fluid">
                        <div class="span6">
                          <div id="cargoBulkOrDanger">
                            <div class="control-group specificaOrCargoName">
                              <label class="control-label">货名：</label>
                              <div class="controls">
                                <input type="text" style="width: 150px;"
                                  name="cargoNames" value="${orderCommonData.cargoNames}" />
                              </div>
                            </div>
                          </div>
                        </div>

                        <div class="span6">
                          <div class="control-group tonOrBox">
                            <label class="control-label">货量：</label>
                            <div class="controls">
                              <fmt:formatNumber var="fmtTonTeus"
                                value="${orderCommonData.tonTeus}" pattern="#" />
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
                                value="${orderCommonData.prices}" pattern="#.##" />
                              <input type="text" placeholder="请输入数字" <c:if test="${orderCommonData.handlerId!=userData.id}">readonly="readonly"</c:if>
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
                                value="${orderCommonData.transFee}" pattern="#.##" />
                              <input placeholder="请输入有效数字" type="text" readonly="readonly"
                                class="data-empty" name="transFee" id="transFee"
                                value="${fmtTransFee}" style="width: 150px;" />&nbsp;&nbsp;元
                            </div>
                          </div>
                        </div>

                    </c:if>

                    <div class="row-fluid">
                      <div class="span12">
                        <div class="control-group">
                          <label class="control-label" for="orderContent">特约条款：</label>
                          <div class="controls">
                            <textarea name="orderDesc" rows="8" <c:if test="${orderCommonData.handlerId!=userData.id}">readonly="readonly"</c:if>
                              placeholder="请输入不超过2000个字的特约条款 ...">${orderCommonData.orderDesc}</textarea>
                          </div>
                        </div>
                      </div>
                    </div>

                    <div class="form-actions">
                      <a href="javascript:void(0);"
                        class="btn btn-primary btnSaveAll">保存</a> <a
                        href="javascript:void(0);" class="btn btn-warning btnBack">返回</a>
                    </div>
              </div>
              </fieldset>
              </form>
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

  <%-- 
  <jsp:include page="./space-foot.jsp"></jsp:include>--%>

  <!-- Modal dlgAddUser -->
  <div id="dlgAddUser" class="modal hide fade">
    <form class="form-horizontal" name="dlgAddUserForm"
      id="dlgAddUserForm" novalidate="novalidate" method="post"
      action="${ctx}/space/contact/myContact/save">
      <input type="hidden" id="userId" name="userId" value="0" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>选择添加</h3>
      </div>
      <div class="modal-body">
        <fieldset>
          <div class="control-group pdleft">
            <label class="control-label" for="inputCombinedIcon"
              style="width: 45px; font-size: 14px;">用户：</label>
            <div class="controls" style="margin-left: 10px">
              <div class="input-append input-icon-prepend">
                <div class="add-on" style="width: 400px">
                  <a title="search" class="icon"><i class="icofont-search"></i></a>
                  <input id="userKeyWords" placeholder="请输入登录名、昵称、真实姓名、手机号码、邮箱"
                    type="text" class="grd-white" style="width: 360px" />
                </div>
                <a href="javascript:void(0);" id="btnSerachUser" class="btn"
                  style="height: 20px; line-heigth: 20px">查询</a>
              </div>
            </div>
          </div>
          <div id="userList"></div>
        </fieldset>
      </div>
      <div class="modal-footer">
        <a href="javascript:void(0);" class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </a> <a href="javascript:void(0);" id="btnAddUser"
          class="btn btn-primary"> <i class="icon icon-ok icon-white"></i>
          确认
        </a>
      </div>
    </form>
  </div>

  <!-- Modal dlgAddShip -->
  <div id="dlgAddShip" class="modal hide fade">
    <form class="form-horizontal" name="dlgAddForm" id="dlgAddForm"
      novalidate="novalidate" action="#">
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
                  <input name="shipKeyWords" id="shipKeyWords" type="text"
                    class="grd-white" style="width: 360px" />
                </div>
                <a href="javascript:void(0);" id="btnSerachShip" class="btn"
                  style="height: 20px; line-heigth: 20px">查询</a>
              </div>
            </div>
          </div>
          <div id="shipList"></div>
        </fieldset>
      </div>
      <div class="modal-footer">
        <a href="javascript:void(0);" class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </a> <a href="javascript:void(0);" id="btnAddShip"
          class="btn btn-primary"> <i class="icon icon-ok icon-white"></i>
          确认
        </a>
      </div>
    </form>
  </div>

  <!-- Modal editDialog -->
  <!-- 更变托运人对话框 -->
  <div id="editDialog" class="modal hide fade">
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
                type="text" name="portName" id="portName" value="集装箱"
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

  <div class="modal hide fade" id="dlgUserSelector">
    <form novalidate="novalidate">
      <input type="hidden" id="objUserRole" name="objUserRole"
        value="owner" /> <input type="hidden" id="objUserId"
        name="objUserId" value="0" />
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

</body>
</html>
