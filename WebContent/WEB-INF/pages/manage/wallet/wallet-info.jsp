<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>账务</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />

<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>
<style>
@media screen and (min-width: 1023px) {
  .ctx-container {
    width: 1000px;
    margin: 0px auto
  }
}

@media screen and (max-width: 1023px) {
  .ctx-container {
    width: 98%;
    margin: 0px auto
  }
}

<!--
.order-title {
  background: #E4E4E4;
  height: 30px;
  line-height: 30px;
  font-size: 16px;
  margin-bottom: 10px;
  text-align: left
}

img {
  width: 100%;
}

.clear:after {
  display: block;
  clear: both;
  content: "";
  visibility: hidden;
  height: 0;
}

div.divider-content {
  display: block;
  border-bottom-color: #F5F5F5;
  border-bottom-style: solid;
  border-bottom-width: 1px;
  border-top-color: #CDCDCD;
  border-top-style: solid;
  border-top-width: 1px;
  height: 0;
  margin: 20px 0;
  position: relative;
}

div.divider-content>span {
  background-color: #FFFFFF;
  background-image: url('${ctx}/img/divider.jpg');
  background-position: 50% 50%;
  background-repeat: no-repeat no-repeat;
  display: block;
  height: 9px;
  left: 50%;
  margin-left: -21px;
  padding: 0 3px;
  position: absolute;
  top: -4px;
  width: 36px;
}

div.contain-head {
  text-align: center;
  background: #f7f7f7;
  border: 1px solid #dddddd;
  border-collapse: separate;
  border-top-left-radius: 0px;
  border-top-right-radius: 0px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0px;
  height: 30px;
  line-height: 30px;
}

tr>.center {
  text-align: center
}

tr>.title {
  font-size: 16px;
  background: #f7f7f7;
  width: 80px;
}

td {
  word-wrap: break-word;
}

@media ( max-width : 767px) {
  body {
    padding-right: 2px;
    padding-left: 2px;
  }
}

.table-bordered {
  border-radius: 0px;
}

.table {
  margin-bottom: 0px;
}

.table-bordered thead:last-child tr:last-child>th:first-child,
  .table-bordered tbody:last-child tr:last-child>td:first-child,
  .table-bordered tfoot:last-child tr:last-child>td:first-child {
  -webkit-border-bottom-left-radius: 0px;
  border-bottom-left-radius: 0px;
  -moz-border-radius-bottomleft: 0px;
}

.table-bordered thead:first-child tr:first-child>th:first-child,
  .table-bordered tbody:first-child tr:first-child>td:first-child {
  -webkit-border-top-left-radius: 0px;
  border-top-left-radius: 0px;
  -moz-border-radius-topleft: 0px;
}

.table-bordered thead:last-child tr:last-child>th:last-child,
  .table-bordered tbody:last-child tr:last-child>td:last-child,
  .table-bordered tfoot:last-child tr:last-child>td:last-child {
  
}
-->
</style>
</head>
<body style="font-family: 'Arial Unicode MS'">
  <div id="container" style="margin: 0px auto">
    <div id="order-basic-info" style="margin-top: 5px;"
      class="ctx-container">

      <div style="text-align: center; font-size: 24px;">
      交易记录
      </div>

      <div style="height: 20px;"></div>

      <!-- 合同双方 -->
      <div class="row-fluid">
        <div class="span12">
         <table class="table table-bordered">
            <tbody>
              <c:if test="${walletData.settleStyle == 'fill'}">
              <tr><td>支付编号</td><td colspan="2">${walletData.paymentNo}</td><td>交易时间</td><td colspan="2">${walletData.gmtPayment}</td></tr>
              <tr><td rowspan="4">付款方</td><td>帐号</td><td></td><td rowspan="4">收款方</td><td>帐号</td><td>${walletData.rcvCardNo}</td></tr>
              <tr><td>户名</td><td></td><td>户名</td><td>${walletData.rcvAccountName}</td></tr>
              <tr><td>开户行</td><td></td><td>开户行</td><td>平安银行</td></tr>
              <tr><td>帐户性质</td><td></td><td>帐户性质</td><td>见证宝</td></tr>
              <tr><td rowspan="2">金额</td><td>小写</td><td>￥${walletData.totalFee}</td><td>手续费</td><td colspan="2">￥0.00</td></tr>
              <tr><td>合计</td><td>￥${walletData.totalFee}</td><td>合计(大写)</td><td colspan="2">${walletData.totalFeeChinese}</td></tr>
              <tr><td>用途</td><td colspan="5">${walletData.settleStyle.description}</td></tr>
              </c:if>

              <c:if test="${walletData.settleStyle == 'fetch'}">
              <tr><td>支付编号</td><td colspan="2">${walletData.paymentNo}</td><td>交易时间</td><td colspan="2">${walletData.gmtPayment}</td></tr>
              <tr><td rowspan="4">付款方</td><td>帐号</td><td>${walletData.sndCardNo}</td><td rowspan="4">收款方</td><td>帐号</td><td></td></tr>
              <tr><td>户名</td><td>${walletData.sndAccountName}</td><td>户名</td><td></td></tr>
              <tr><td>开户行</td><td>平安银行</td><td>开户行</td><td></td></tr>
              <tr><td>帐户性质</td><td>见证宝</td><td>帐户性质</td><td></td></tr>
              <tr><td rowspan="2">金额</td><td>小写</td><td>￥${walletData.totalFee}</td><td>手续费</td><td colspan="2">￥0.00</td></tr>
              <tr><td>合计</td><td>￥${walletData.totalFee}</td><td>合计(大写)</td><td colspan="2">${walletData.totalFeeChinese}</td></tr>
              <tr><td>用途</td><td colspan="5">${walletData.settleStyle.description}</td></tr>
              </c:if>

              <c:if test="${walletData.settleStyle == 'pay' && settleType == 'masterfee'}">
              <tr><td>支付编号</td><td colspan="2">${walletData.paymentNo}</td><td>交易时间</td><td colspan="2">${walletData.gmtPayment}</td></tr>
              <tr><td rowspan="4">付款方</td><td>帐号</td><td>${walletData.sndCardNo}</td><td rowspan="4">收款方</td><td>帐号</td><td>${walletData.rcvCardNo}</td></tr>
              <tr><td>户名</td><td>${walletData.sndAccountName}</td><td>户名</td><td>${walletData.rcvAccountName}</td></tr>
              <tr><td>开户行</td><td>平安银行</td><td>开户行</td><td>平安银行</td></tr>
              <tr><td>帐户性质</td><td>见证宝</td><td>帐户性质</td><td>见证宝</td></tr>
              <tr><td rowspan="2">金额</td><td>小写</td><td>￥${walletData.totalFee}</td><td>手续费</td><td colspan="2">￥0.00</td></tr>
              <tr><td>合计</td><td>￥${walletData.totalFee}</td><td>合计(大写)</td><td colspan="2">${walletData.totalFeeChinese}</td></tr>
              <tr><td>用途</td><td colspan="5">支付${settleType.description}</td></tr>
              </c:if>

              <c:if test="${walletData.settleStyle == 'pay' && settleType == 'brokerfee'}">
              <tr><td>支付编号</td><td colspan="2">${walletData.paymentNo}</td><td>交易时间</td><td colspan="2">${walletData.gmtPayment}</td></tr>
              <tr><td rowspan="4">付款方</td><td>帐号</td><td>${walletData.rcvCardNo}</td><td rowspan="4">收款方</td><td>帐号</td><td>${walletData.brokerCardNo}</td></tr>
              <tr><td>户名</td><td>${walletData.rcvAccountName}</td><td>户名</td><td>${walletData.brokerAccountName}</td></tr>
              <tr><td>开户行</td><td>平安银行</td><td>开户行</td><td>平安银行</td></tr>
              <tr><td>帐户性质</td><td>见证宝</td><td>帐户性质</td><td>见证宝</td></tr>
              <tr><td rowspan="2">金额</td><td>小写</td><td>￥${walletData.middleFee}</td><td>手续费</td><td colspan="2">￥0.00</td></tr>
              <tr><td>合计</td><td>￥${walletData.middleFee}</td><td>合计(大写)</td><td colspan="2">${walletData.middleFeeChinese}</td></tr>
              <tr><td>用途</td><td colspan="5">支付${settleType.description}</td></tr>
              </c:if>

              <c:if test="${walletData.settleStyle == 'pay' && settleType == 'platfee'}">
              <tr><td>支付编号</td><td colspan="2">${walletData.paymentNo}</td><td>交易时间</td><td colspan="2">${walletData.gmtPayment}</td></tr>
              <tr><td rowspan="4">付款方</td><td>帐号</td><td>${walletData.rcvCardNo}</td><td rowspan="4">收款方</td><td>帐号</td><td>888100288016291</td></tr>
              <tr><td>户名</td><td>${walletData.rcvAccountName}</td><td>户名</td><td>平台服务费</td></tr>
              <tr><td>开户行</td><td>平安银行</td><td>开户行</td><td>平安银行</td></tr>
              <tr><td>帐户性质</td><td>见证宝</td><td>帐户性质</td><td>见证宝</td></tr>
              <tr><td rowspan="2">金额</td><td>小写</td><td>￥${walletData.serviceFee}</td><td>手续费</td><td colspan="2">￥0.00</td></tr>
              <tr><td>合计</td><td>￥${walletData.serviceFee}</td><td>合计(大写)</td><td colspan="2">${walletData.serviceFeeChinese}</td></tr>
              <tr><td>用途</td><td colspan="5">支付${settleType.description}</td></tr>
              </c:if>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</body>
</html>