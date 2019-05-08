<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>管理控制台</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="${ctx}/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-responsive.css" />
<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css" media="screen"/>
<link rel="stylesheet" href="${ctx}/css/uniform.css" />
<link rel="stylesheet" href="${ctx}/css/select2.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.main.css" />
<link rel="stylesheet" href="${ctx}/css/unicorn.grey.css"
  class="skin-color" />

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/jquery.ui.custom.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
<script src="${ctx}/js/jquery.uniform.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script src="${ctx}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${ctx}/js/unicorn.js"></script>
<script src="${ctx}/js/manage/manage-wallet-error.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>

<style type="text/css">
  table {
    table-layout:fixed; 
    word-wrap: break-word;"
  }
</style>

</head>
<body>

  <jsp:include page="./manage-head.jsp"></jsp:include>

  <div id="content">
    <div id="breadcrumb">
      <a href="#" style="font-size: 12px;" class="tip-bottom"> <i
        class="icon-home"></i> 钱包管理
      </a> <a href="#" style="font-size: 12px;" class="current">异常处理</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span12">
		 <form name="pageform" id="pageform"action="${ctx}/manage/wallet/walletError" method="post">
          <div class="widget-box">
            <div class="widget-title">
              <h5>帐务列表</h5>
              <div class="control-group styleCenter" style="float:left;">
				<select name="khsdOrZjjz" id="khsdOrZjjz" style="margin-top: 3px;width: 110px; ">
				  <c:if test="${khsdOrZjjz == 'khsd'}">
					<option value="khsd" selected>跨行收单</option>
					<option value="jzzj">见证宝</option>
				  </c:if>
				  <c:if test="${khsdOrZjjz == 'jzzj'}">
					<option value="khsd">跨行收单</option>
					<option value="jzzj" selected>见证宝</option>
				  </c:if>
				</select>

				<select name="errorStatus" id="errorStatus" style="margin-top: 3px;width: 110px; ">
					<option value="" selected>异常状态...</option>
					<c:forEach var="st" items="${errorStatuss}">
						<c:choose>
							<c:when test="${st == errorStatus}">
								<option value="${st}" selected>${st.description}</option>
							</c:when>
							<c:otherwise>
								<option value="${st}">${st.description}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				
             	<input name="userInfo" id="userInfo" type="text" value="${userInfo}"
             		style="margin-top:3px;width: 100px" placeholder="用户登录名"/>
              </div>

              <div class="control-group styleCenter" style="float:left;width:200px;margin-left:10px;margin-top:3px;">
                <div style="margin-left:10px;" class="controls input-append date dateStartDate" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1">
            	  <input size="16" type="text" name="startDate" id="startDate"  style="width:120px;margin-bottom:0px;" value="${startDate}" placeholder="起始日期!"/>            	
                  <span class="add-on"><i class="icon-trash removeStartDate"></i></span>
				  <span class="add-on"><i class="icon-th"></i></span>
                </div>
				<input type="hidden" id="dtp_input1" value="" />
	          </div>
	          
	          <div class="control-group styleCenter" style="float:left;width:200px;margin-left:10px;margin-top:3px;">
                <div style="margin-left:10px;" class="controls input-append date dateEndDate" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2">
            	  <input size="16" type="text" name="endDate" id="endDate"  style="width:120px;margin-bottom:0px;" value="${endDate}" placeholder="终止日期!"/>            	
                  <span class="add-on"><i class="icon-trash removeEndDate"></i></span>
				  <span class="add-on"><i class="icon-th"></i></span>
                </div>
				<input type="hidden" id="dtp_input2" value="" />
	          </div>
              
           	  <button type="submit" class="btn btn-primary" id="btnSerach"
           	    style="margin-bottom:8px;line-heigth: 20px">查询</button>
            </div>
            <div class="widget-content nopadding">
              <table class="table table-bordered data-table">
                <thead>
                  <tr>
                    <th style="width: 8%">支付编号</th>
                    <th style="width: 6%">类型</th>
                    <th style="width: 6%">费项</th>
                    <th style="width: 10%">买家</th>
                    <th style="width: 10%">卖家</th>
                    <th style="width: 10%">代理</th>
                    <th style="width: 16%">交易描述</th>
                    <th style="width: 8%">交易状态</th>
                    <th style="width: 8%">退款状态</th>
                    <th style="width: 8%">异常状态</th>
                    <th style="width: 10%">操作</th>
                  </tr>
                </thead>

                <tbody>
                  <c:forEach items="${pageData.result}" var="wallet">
                    <tr class="gradeX">
                      <td>${wallet.shortPaymentNo}</td>
                      <td>${wallet.settleStyle.description}</td>
                      <td>${wallet.feeItem.description}</td>
                      <td>${wallet.buyerData.loginName}</td>
                      <td>${wallet.sellerData.loginName}</td>
                      <td>${wallet.brokerData.loginName}</td>
                      <td>${wallet.body}</td>
                      <td>
               	        <c:if test="${wallet.settleStyle == 'fetch'}">${wallet.paymentStatus.remark}</c:if>
               	        <c:if test="${wallet.settleStyle != 'fetch'}">${wallet.paymentStatus.description}</c:if>
                      </td>
                      <td>${wallet.refundStatus.description}</td>
                      <td>
                        <c:if test="${wallet.errorStatus == 'yes' || wallet.khErrorStatus == 'yes'}">是</c:if>
                        <c:if test="${wallet.errorStatus == 'no' && wallet.khErrorStatus == 'no'}">否</c:if>
                      </td>
                      <td>
                        <a class="btn btn-primary btnShow" idVal="${wallet.id}" 
                       		    data-toggle="modal" data-target="#showDialog">
                          <i class="icon-list-alt icon-white"></i> 查看
                        </a>
                        <br />
						<a href="${ctx}/manage/wallet/getPayErrorInfo?walletId=${wallet.id}" 
                       		 class="btn btn-primary" target="_blank">
                          <i class="icon-list-alt icon-white"></i> 异常
                        </a>
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

      <jsp:include page="./manage-foot.jsp"></jsp:include>

    </div>
  </div>

  <!-- Modal showDialog -->
  <div id="showDialog" class="modal hide fade">
    <form class="form-horizontal" name="showDialogForm"
      id="showDialogForm" novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>帐务详情</h3>
      </div>
      <div class="modal-body">

        <div class="control-group">
          <label class="control-label" for="shipName">帐务类型：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_settleStyle" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">支付号：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_paymentNo" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">费项：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_feeItem" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">订单ID：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_orderId" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">买家：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_buyyer" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">付款帐户名：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_sndAccountName" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">付款帐号：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_sndCardNo" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">卖家：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_seller" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">收款帐户名：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_rcvAccountName" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">收款帐号：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_rcvCardNo" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">代理人：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_broker" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">收款帐户名：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_brokerAccountName" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">收款帐号：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_brokerCardNo" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">交易标题：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_subject" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">交易描述：</label>
          <div class="controls">
            <textarea class="input-medium" id="_body" cols="30" rows="5" disabled></textarea>
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">交易金额(元)：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_totalFee" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">代理人佣金(元)：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_middleFee" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">平台服务费(元)：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_serviceFee" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">支付状态：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_paymentStatus" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">支付时间：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_gmtPayment" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">资金托管天数：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_suretyDays" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">确认付款时间：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_gmtSurety" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">退款状态：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_errorStatus" value="" disabled />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="shipName">退款时间：</label>
          <div class="controls">
            <input type="text" class="input-medium" id="_gmtRefund" value="" disabled />
          </div>
        </div>

      </div>
    </form>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 关闭
      </button>
    </div>
  </div>

  <!-- Modal errorDialog -->
  <div id="errorDialog" class="modal hide fade">
    <form class="form-horizontal" name="showDialogForm"
      id="errorDialogForm" novalidate="novalidate" method="post" action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>见证接口详情列表</h3>
      </div>
      <div class="modal-body">
        <div class="widget-content nopadding" id="_zjjzList">
        </div>
      </div>
    <div class="modal-footer">
      <button class="btn" data-dismiss="modal">
        <i class="icon icon-off"></i> 关闭
      </button>
    </div>
  </div>

  <!-- Modal paymentDialog -->
  <div id="doPaymentDialog" class="modal hide fade">
    <form class="form-horizontal" name="doPaymentDialogForm" role="form"
      id="doPaymentDialogForm" novalidate="novalidate" method="post" 
      action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>确认付款</h3>
      </div>
      <input type="hidden" id="doPaymentId" name="walletId" value="" />
      <div class="modal-body">

        <div class="control-group">
          <div id="paymentOrderDesc"></div>
          <p></p>
       	  <p>该订单已支付，资金托管中，付款人请求退款，收款人不同意，你确认要最终支付给收款人吗？</p>
        </div>

        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnDoPayment">
            <i class="icon-ok icon-white"></i> 确认
          </a>
        </div>
      </div>
    </form>
  </div>

  <!-- Modal refundDialog -->
  <div id="doRefundDialog" class="modal hide fade">
    <form class="form-horizontal" name="doRefundDialogForm" role="form"
      id="doRefundDialogForm" novalidate="novalidate" method="post" 
      action="#">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>退款处理</h3>
      </div>
      <input type="hidden" id="doRefundId" name="walletId" value="" />
      <div class="modal-body">

        <div class="control-group">
          <div id="refundOrderDesc"></div>
          <p></p>
       	  <p>该订单已支付，资金托管中，付款人请求退款，收款人不同意，你确认要全额退还给付款人吗？</p>
        </div>

        <div class="modal-footer">
          <a class="btn" data-dismiss="modal">
            <i class="icon icon-off"></i> 关闭
          </a>
          <a class="btn btn-primary btnDoRefund">
            <i class="icon-ok icon-white"></i> 确认
          </a>
        </div>
      </div>
    </form>
  </div>

</body>
</html>
