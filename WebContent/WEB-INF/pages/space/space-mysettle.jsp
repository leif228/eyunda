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

  var payStyles = [];
  <c:forEach var="payStyle" items="${payStyles}">
  payStyles.push({
    "payStyle" : "${payStyle}",
    "description" : "${payStyle.description}"
  });
  </c:forEach>

  $(document).ready(function() {
    $('#myTab a[href="#${tab}"]').tab('show');
    var callback = "${callback}";
    var callbackmsg = "${callbackmsg}";
    if (callback == 'fail') {
      $('#succes').hide();
      $('#fail').show();
      $('#fail').append(callbackmsg);
    } else if (callback == 'succes') {
      $('#succes').show();
      $('#succes').append(callbackmsg);
      $('#fail').hide();
    } else {
      $('#callback').hide();
    }
  });

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
        <div class="box-header corner-top">
          <!-- <ul id="myTab" class="nav nav-pills">
            <li class="active"><a href="#settle" data-toggle="tab"
              tab="settle" class="bold load">账务信息</a></li>
            <li><a href="#balancebaby" data-toggle="tab"
              tab="balancebaby" class="bold load">充值扣费</a></li>
          </ul> -->

          <div class="tab-content">

            <div class="tab-pane active" id="settle">
              <form novalidate="novalidate" method="get" id="pageform"
                action="${ctx}/space/settle/mySettle">
                <div class="widget-box">
                  <div class="widget-title">
                    <h5>我的账务信息</h5>
                    <div class="control-group"
                      style="float: left; width: 280px; height: 32px; margin-left: 10px; margin-top: 4px;">
                      <label class="control-label"
                        style="float: left; width: 80px; margin-top: 5px;">起始日期：</label>
                      <div style="margin-left: 10px;"
                        class="controls input-append date form_datetimeStart"
                        data-date="" data-date-format="yyyy-mm-dd"
                        data-link-field="dtp_input1">
                        <input size="16" type="text" name="startTime" id="startTime"
                          style="width: 120px; margin-bottom: 0px;"
                          value="${startTime}" placeholder="请输入起始查询时间!" /> <span
                          class="add-on"><i
                          class="icon-trash removeStartTime"></i></span> <span
                          class="add-on"><i class="icon-th"></i></span>
                      </div>
                      <input type="hidden" id="dtp_input1" value="" />
                    </div>
                    <div class="control-group"
                      style="float: left; width: 280px; height: 32px; margin-left: 10px; margin-top: 4px;">
                      <label class="control-label"
                        style="float: left; width: 80px; margin-top: 5px;">终止日期：</label>
                      <div style="margin-left: 10px;"
                        class="controls input-append date form_datetimeEnd"
                        data-date="" data-date-format="yyyy-mm-dd"
                        data-link-field="dtp_input2">
                        <input size="16" type="text" name="endTime" id="endTime"
                          style="width: 120px; margin-bottom: 0px;"
                          value="${endTime}" placeholder="请输入终止查询时间!" /> <span
                          class="add-on"><i class="icon-trash removeEndTime"></i></span>
                        <span class="add-on"><i class="icon-th"></i></span>
                      </div>
                      <input type="hidden" id="dtp_input2" value="" />
                    </div>
                    <div style="float: left; margin-top: 5px;">
                      <a class="btn btn-primary findSettle"
                        style="margin-top: -1px;" title="查询"> <i
                        class=" icon-search icon-white"></i>查询
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
            <%--       <div class="tab-pane" id="balancebaby">
              <div id="callback">
                <div id="succes" class="alert alert-success alert-dismissable">
                  <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true">&times;</button>
                </div>
                <div id="fail" class="alert alert-danger alert-dismissable">
                  <button type="button" class="close" data-dismiss="alert"
                    aria-hidden="true">&times;</button>
                </div>
              </div>
              <form novalidate="novalidate" id="pageform1" name="pageform1"
                method="get" action="${ctx}/space/settle/mySettle">
                <input type="hidden" id="tab" name="tab" value="balancebaby" />
                <div class="widget-box">
                  <div class="widget-title" style="margin-top: 5px;">
                    <div style="float: left;">
                      <h5>充值扣费</h5>
                      <a class="btn btn-info btnin"> <i
                        class="icon-plus icon-white"></i> 转入
                      </a>
                    </div>
                  </div>

                  <div class="widget-content nopadding">
                    <table class="table table-bordered data-table table-striped">
                      <thead>
                        <tr>
                          <th>转帐类型</th>
                          <th>金额</th>
                          <th>时间</th>
                          <th>备注</th>
                          <th>余额</th>
                          <th>操作</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${pageData1.result}" var="baby"
                          varStatus="status">
                          <tr>

                            <td>${baby.giroType.description}</td>
                            <td><c:if test="${baby.giroType=='inaccount'}">
                                               +${baby.money}
                                          </c:if> <c:if
                                test="${baby.giroType=='outaccount'||baby.giroType=='dedunct'}">
                                              -${baby.money}
                                         </c:if></td>

                            <td>${baby.createTime}</td>
                            <td>${baby.remark}</td>
                            <td>${baby.balance}</td>
                            <td><c:if
                                test="${pageData1.pageNo=='1'&&status.first&&baby.balance>0.00}">
                                <a class="btn btn-primary btnout"
                                  style="width: 40px; height: 18px;"> <i
                                  class="icon-minus icon-white"></i>转出
                                </a>
                              </c:if></td>

                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                  <jsp:include page="./balbaby-pager.jsp"></jsp:include>
                </div>
              </form>
            </div> --%>
          </div>
        </div>
        <!-- /span9 -->
      </div>
    </div>
  </div>
  </div>
  </div>
  </div>
  <!-- /row -->


  <jsp:include page="./space-foot.jsp"></jsp:include>

  <%-- <!-- Modal showDialog -->
  <div id="btnoutDialog" class="modal hide fade">
    <form class="form-horizontal" name="btnoutDialogForm" role="form"
      id="btnoutDialogForm" novalidate="novalidate" method="post"
      action="${ctx}/space/settle/outaccount">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>充值扣费-转出</h3>
      </div>
      <input type="hidden" id="userId" name="userId" value="${userData.id}" />
      <div class="modal-body">
        <div class="control-group">
          <label class="control-label">转账类型：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="giroType" value="转出"
              disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">转出金额(元)：</label>
          <div class="controls">
            <input type="number" class="input-medium" id="money" name="money"
              value=" " placeholder="保留两位小数" /><span class="color-red"></span>
          </div>
        </div>

        <div class="control-group">
          <label class="control-label">转账方式：</label>
          <div class="controls">
            <div class="selPayStyle">
              <select id="payStyle" name="payStyle" style="width: 280px;">
                <c:forEach var="payStyle" items="${payStyles}">
                  <option value="${payStyle}">${payStyle.description}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label">帐户开户人：</label>
          <div class="controls">
            <select id="accounter" name="accounter" style="width: 280px;">
              <c:forEach var="childrenAccountData"
                items="${childrenAccountDatas}">
                <option value="${childrenAccountData.accounter}">${childrenAccountData.accounter}</option>
              </c:forEach>
            </select><span class="color-red"></span>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label">帐号：</label>
          <div class="controls">
            <div class="selPayStyle">
              <select id="accountNo" name="accountNo" style="width: 280px;">
                <c:forEach var="childrenAccountData"
                  items="${childrenAccountDatas}">
                  <option value="${childrenAccountData.accountNo}">${childrenAccountData.accountNo}</option>
                </c:forEach>
              </select><span class="color-red"></span>
            </div>
          </div>
        </div>

        <div class="modal-footer">
          <button class="btn btn-primary btn_sureout">
            <i class="icon-arrow-down icon-white"></i> 转出
          </button>
          <button class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </button>
        </div>
      </div>
    </form>
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

        <div class="control-group">
          <label class="control-label">转账方式：</label>
          <div class="controls">
            <div class="selPayStyle">
              <select id="payStyle" name="payStyle" style="width: 280px;">
                <c:forEach var="payStyle" items="${payStyles}">
                  <option value="${payStyle}">${payStyle.description}</option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label">帐户开户人：</label>
          <div class="controls">
            <select id="accounter" name="accounter" style="width: 280px;">
              <c:forEach var="childrenAccountData"
                items="${childrenAccountDatas}">
                <option value="${childrenAccountData.accounter}">${childrenAccountData.accounter}</option>
              </c:forEach>
            </select><span class="color-red"></span>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label">帐号：</label>
          <div class="controls">
            <div class="selPayStyle">
              <select id="accountNo" name="accountNo" style="width: 280px;">
                <c:forEach var="childrenAccountData"
                  items="${childrenAccountDatas}">
                  <option value="${childrenAccountData.accountNo}">${childrenAccountData.accountNo}</option>
                </c:forEach>
              </select><span class="color-red"></span>
            </div>
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
  </div> --%>

</body>
</html>