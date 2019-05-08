<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<link rel="stylesheet"
  href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/bootstrap-colorpicker.js"></script>
<script src="${ctx}/js/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/jquery.validate.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js"
  charset="UTF-8"></script>
<script src="${ctx}/js/space/space-orderCommon.js"></script>
<script src="${ctx}/js/space/space-validate.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
  var _signImage = "${userData.signature}";
  var _stampImage = "${userData.stamp}";

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

.help-inline {
  color: #B94A48;
}

#signImage img {
  width: 120px;
  height: 120px;
}

#stampImage img {
  width: 120px;
  height: 120px;
}
</style>
</head>

<body>

  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span10">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 合同管理
    </h1>
    <div class="row">

      <div class="span10">
        <form novalidate="novalidate" method="get" id="pageform"
          action="${ctx}/space/orderCommon/orderList">
          <div class="widget-box">
            <div class="widget-title">
              <c:if test="${flag}">
                <div style="float: right; margin-left: 10px; margin-top: 4px;">
                  <span>添加：</span>
                  <select id="newOrderTypeCode" name="newOrderTypeCode" style="width: 160px;">
                    <option value="">请选择</option>
            		<c:forEach var="orderTypeCode" items="${orderTypeCodes}">
              			<option value="${orderTypeCode}">${orderTypeCode.description}</option>
            		</c:forEach>
          		  </select>
                </div>
              </c:if>

              <div style="float: left; margin-left: 10px; height: 32px;">
                <div class="control-group"
                  style="float: left; width: 280px; margin-left: 10px; margin-top: 4px;">
                  <label class="control-label"
                    style="float: left; width: 80px; margin-top: 5px;">起始日期：</label>
                  <div style="margin-left: 10px;"
                    class="controls input-append date form_datetimeStart"
                    data-date="" data-date-format="yyyy-mm-dd"
                    data-link-field="dtp_input1">
                    <input size="16" type="text" name="startTime" id="startTime"
                      style="width: 120px; margin-bottom: 0px;"
                      value="${startTime}" placeholder="请输入起始查询时间!" /> <span
                      class="add-on"><i class="icon-trash removeStartTime"></i></span>
                    <span class="add-on"><i class="icon-th"></i></span>
                  </div>
                  <input type="hidden" id="dtp_input1" value="" />
                </div>
                <div class="control-group"
                  style="float: left; width: 280px; margin-left: 10px; margin-top: 4px;">
                  <label class="control-label"
                    style="float: left; width: 80px; margin-top: 5px;">终止日期：</label>
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
                <div style="float: right; margin-top: 5px;">
                  <a class="btn btn-primary findOrder" style="margin-top: -1px;"
                    title="查询"> <i class=" icon-search icon-white"></i>查询
                  </a>
                </div>
              </div>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table table-striped">
                <thead>
                  <tr>
                    <th style="text-align: center; width: 14%">合同</th>
                    <th style="text-align: center; width: 10%">甲方</th>
                    <th style="text-align: center; width: 10%">乙方</th>
                    <th style="text-align: center; width: 14%">合同金额</th>
                    <th style="text-align: center; width: 8%">状态</th>
                    <th style="text-align: center; width: 20%">支付情况</th>
                    <th style="text-align: center; width: 12%">建立日期</th>
                    <th style="text-align: center; width: 12%">操作</th>
                  </tr>
                </thead>

                <tbody>

                  <c:forEach items="${pageData.result}" var="order">
                    <tr>
                      <td><a target="_blank" href="${ctx}/space/orderCommon/showContainer?orderId=${order.id}&orderType=${order.orderType}">
                        ${order.shipData.shipName}${order.orderType.description}(${order.id})</a></td>
                      <td>${order.owner.trueName}</td>
                      <td>
                        <c:if test="${empty order.master}">
                          ${order.broker.trueName}
                        </c:if>
                        <c:if test="${!empty order.master}">
                          ${order.master.trueName}
                        </c:if>
                      </td>
                      <td style="text-align: center;">${order.transFee}元(${order.paySteps}期)</td>
                      <td>${order.state.description}</td>
                      <td>
                        <c:if test="${empty order.walletDatas}">未支付</c:if>
                        <c:if test="${!empty order.walletDatas}">
                          <c:set var="w" value="${fn:length(order.walletDatas)}"/>
                          <c:forEach items="${order.walletDatas}" var="walletData" varStatus="status">
                            ${walletData.gmtPayment}支付${walletData.totalFee}${walletData.paymentStatus.description}<br>
                            <c:if test="${status.index<w-1}"></c:if>
                          </c:forEach>
                        </c:if>
                      </td>
                      <td>${order.createTime}</td>
                      <td>
                        <c:if test="${order.ops.edit}">
                          <a title="修改" class="btn btn-primary btnEdit"
                            href="${ctx}/space/orderCommon/orderEdit?orderId=${order.id}&orderType=${order.orderType}">
                            <i class="icon-pencil icon-white"></i>
                          </a>
                          <a title="删除" class="btn btn-danger btnDelete" idVal="${order.id}" oVal="${order.shipData.shipName}${order.orderType.description}(${order.id})">
                            <i class="icon-trash icon-white"></i>
                          </a>
                        </c:if>
                        <c:if test="${order.ops.submit}">
                          <a title="确认订单" class="btn btn-primary btnSubmit" idVal="${order.id}" oVal="${order.shipData.shipName}${order.orderType.description}(${order.id})">
                            <i class="icon-share icon-white"></i>
                          </a>
                        </c:if>
                        <c:if test="${order.ops.startsign}">
                          <a title="乙方签字" class="btn btn-primary btnStartSign" idVal="${order.id}" oVal="${order.shipData.shipName}${order.orderType.description}(${order.id})">
                            <i class="icon-edit icon-white"></i>
                          </a>
                        </c:if>
                        <c:if test="${order.ops.endsign}">
                          <a title="甲方签字" class="btn btn-primary btnEndSign" idVal="${order.id}" oVal="${order.shipData.shipName}${order.orderType.description}(${order.id})">
                            <i class="icon-edit icon-white"></i>
                          </a>
                        </c:if>
                        <c:if test="${order.ops.archive}">
                          <a title="归档" class="btn btn-primary btnArchive" idVal="${order.id}" oVal="${order.shipData.shipName}${order.orderType.description}(${order.id})">
                            <i class="icon-edit icon-white"></i>
                          </a>
                        </c:if>
                        <c:if test="${order.ops.approval}">
                          <a title="评价" class="btn btn-primary btnApprovalEdit" idVal="${order.id}" oVal="${order.shipData.shipName}${order.orderType.description}(${order.id})">
                            <i class="icon-edit icon-white"></i>
                          </a>
                        </c:if>
                        <c:if test="${order.ops.payment}">
                          <a title="支付" class="btn btn-primary orderPay" idVal="${order.id}">
                            <i class="icon-pencil icon-white" style="background-position: -145px -142px;"></i>
                          </a>
                        </c:if>
                        <c:if test="${order.ops.monitor}">
                          <a title="实时监控" class="btn btn-success btnMonitor" target="_blank"
                            href="${ctx}/space/state/routePlay?shipId=${order.shipData.id}">
                            <i class="icon-map-marker icon-white"></i>
                          </a>
                        </c:if>
                        
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

  <!-- Modal orderPutDlg -->
  <div id="orderPutDlg" class="modal hide fade">
      <input type="hidden" name="orderId" id="orderId" value="0" />
      <input type="hidden" name="orderForm" id="orderForm" value="" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <div id="orderPutTitle">
          <h3></h3>
        </div>
      </div>
      <div class="modal-body">
        <div id="orderPutContent">
          <p></p>
        </div>
        <div id="signImage" style="float: left;"></div>
        <div id="stampImage" style="float: left; margin-left: 30px;"></div>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <a class="btn btn-primary executeCommand">
          <i class="icon-ok icon-white"></i> 确认
        </a>
      </div>
  </div>

  <!-- Modal approvalDlg -->
  <div id="approvalDlg" class="modal hide fade">
      <input type="hidden" name="id" id="approvalOrderId" value="0" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <div id="orderPutTitle">
          <h3>评价</h3>
        </div>
      </div>
      <div class="modal-body">
        <div class="orderPutContent" id="">
          <p>请对本次服务进行评价，你的认可是对我们提供服务的最大激励！</p>
        </div>
        <div class="control-group">
          <label class="control-label">满意度：</label>
          <div class="controls" id="evalTypesDiv">
            <input type="radio" class="radio" name="evalType" id="verysatisfied" value="verysatisfied" checked />非常满意
            <input type="radio" class="radio" name="evalType" id="satisfied" value="satisfied" />满意
            <input type="radio" class="radio" name="evalType" id="ok" value="ok" />一般 
            <input type="radio" class="radio" name="evalType" id="dissatisfied" value="dissatisfied" />不满意 
            <input type="radio" class="radio" name="evalType" id="verydissatisfied" value="verydissatisfied" />极不满意
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="orderContent">评价内容：</label>
          <div class="controls">
            <textarea id="evalContent" name="evalContent" rows="8" style="width: 520px;"
              placeholder="请输入不超过250个字的评价内容 ...">${myOrder.orderContent}</textarea>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <button class="btn btn-primary orderApproval" type="submit">
          <i class="icon icon-ok icon-white"></i> 确认
        </button>
      </div>
  </div>

  <!-- Modal PayDialog -->
  <div id="walletPayDialog" class="modal hide fade">
    <form class="form-horizontal" name="receiveDialogForm" role="form"
      id="walletPayForm" novalidate="novalidate" method="post" target="_blank"
      action="${ctx}/space/pinganpay/orderPay">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>支付密码验证</h3>
      </div>
      <input type='hidden' id="orderPayId" name='orderId' value='' />
      <input type='hidden' id="feeItem" name='feeItem' value='prefee' />
      
      <div class="modal-body">
        <div class="control-group">
          <label class="control-label">支付密码：</label>
          <div class="controls">
            <input type="password" class="input-medium" name="paypwd" style="width: 150px;"
              value="" placeholder="请输入支付密码" /><span class="color-red"></span>
          </div>
          
          <label class="control-label">支付说明：</label>
          <div class="controls">
            <input type="text" class="input-medium" name="remark" style="width: 150px;"
              value="" placeholder="请输入支付说明" /><span class="color-red"></span>
          </div>
          
          <label class="control-label">支付金额：</label>
          <div class="controls">
            <input type="text" class="input-medium" name="payMoney" style="width: 150px;"
              value="" placeholder="请输入支付金额" /><span class="color-red"></span>
          </div>
          
          <label class="control-label">支付担保期：</label>
          <div class="controls">
            <input type="text" class="input-medium" name="suretyDay" style="width: 150px;"
              value="" placeholder="请输入支付担保期" /><span class="color-red"></span>
          </div>
        </div>
        
        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnOrderPay">
            <i class="icon-ok icon-white"></i> 提交
          </a>
        </div>
      </div>
    </form>
  </div>
  
</body>
</html>
