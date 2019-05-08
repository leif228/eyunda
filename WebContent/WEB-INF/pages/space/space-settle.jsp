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
<script src="${ctx}/js/space/space-settle.js"></script>
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
</style>
</head>

<body>

  <jsp:include page="./space-head.jsp"></jsp:include>

  <div class="span10">

    <h1 class="page-title">
      <i class="icon-star icon-white"></i> 账务管理
    </h1>
    <div class="row">

      <div class="span10">
        <form novalidate="novalidate" method="get" id="pageform"
          action="${ctx}/space/settle/mySettle">
          <div class="widget-box">
            <div class="widget-title">
              <h5>我的账务信息</h5>
              <div class="control-group"
                style="float: left; width: 280px; height: 32px; margin-left: 10px; margin-bottom: 0px;">
                <label class="control-label"
                  style="float: left; width: 80px; margin-top: 5px;">起始日期：</label>
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
              <div class="control-group"
                style="float: left; width: 280px; height: 32px; margin-left: 10px; margin-bottom: 0px;">
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
              <div style="float: left; margin-top: 5px;">
                <a class="btn btn-primary findSettle" style="margin-top: -1px;"
                  title="查询"> <i class=" icon-search icon-white"></i>查询
                </a>
              </div>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table table-striped">
                <thead>
                  <tr>
                    <th>合同号</th>
                    <th>合同描述</th>
                    <th>付款方</th>
                    <th>收款方</th>
                    <th>款项</th>
                    <th>交易状态</th>
                    <th>交易金额(元)</th>
                    <th>支付方式</th>
                    <th>支付时间</th>
                    <th>退款状态</th>
                    <th>退款时间</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${pageData.result}" var="settle">
                    <tr>
                      <td><a
                        href="${ctx}/space/order/showAct?id=${settle.order.id}"
                        target="_blank">${settle.order.id}</a></td>
                      <td>${settle.subject}</td>
                      <td>${settle.payer.trueName}</td>
                      <td>${settle.payee.trueName}</td>
                      <td>${settle.feeItem.description}</td>
                      <td>${settle.tradeStatus.description}</td>
                      <td>${settle.totalFee}</td>
                      <td>${settle.payStyle.description}</td>
                      <td>${settle.gmtPayment}</td>
                      <td><c:if test="${!empty settle.refundStatus}">
                        ${settle.refundStatus.description}
                      </c:if></td>
                      <td><c:if
                          test="${!empty settle.refundStatus && settle.refundStatus == 'REFUND_SUCCESS'}">
                         ${settle.gmtRefund}
                      </c:if></td>
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
  <!-- /row -->

  </div>
  <!-- /row -->

  </div>
  <!-- /container -->

  </div>
  <!-- /content -->

  <jsp:include page="./space-foot.jsp"></jsp:include>

</body>
</html>
