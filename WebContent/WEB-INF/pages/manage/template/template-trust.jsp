<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>合同</title>

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
        ${orderData.orderType.description}(合同号:${orderData.id})
        <c:if test="${makePdf=='no'}">
          <span class="pull-right btn" style="display: none;"> <a
            href="${ctx}/space/order/myOrder/downloadPdf?id=${orderData.id}"
            target="_blank">下载合同</a>
          </span>
        </c:if>
      </div>

      <div style="height: 40px;"></div>

      <!-- 合同双方 -->
      <div class="row-fluid">
        <div class="span12">
          <table class="table table-bordered">
            <c:if test="${orderData.master.id == orderData.broker.id || orderData.owner.id == orderData.broker.id}">
            <tbody>
              <tr height="40">
                <td rowspan="2" class="center title" style="width: 3%">受托人</td>
                <td style="width: 47%">${orderData.owner.trueName}</td>
                <td rowspan="2" class="center title" style="width: 3%">委托人</td>
                <td style="width: 47%">${orderData.master.trueName}</td>
              </tr>
              <tr height="40">
                <td>
                </td>
                <td>
                </td>
              </tr>
            </tbody>
            </c:if>
            <c:if test="${orderData.owner.id != orderData.broker.id && orderData.master.id != orderData.broker.id}">
            <tbody>
              <tr height="40">
                <td rowspan="2" class="center title" style="width: 3%">受托人</td>
                <td style="width: 31%">${orderData.owner.trueName}</td>
                <td rowspan="2" class="center title" style="width: 3%">委托人</td>
                <td style="width: 30%">${orderData.master.trueName}</td>
                <td rowspan="2" class="center title" style="width: 3%">代理人</td>
                <td style="width: 30%">${orderData.broker.trueName}&nbsp;&nbsp;${orderData.handler.trueName}
                </td>
              </tr>
              <tr height="40">
                <td>
                </td>
                <td>
                </td>
                <td>
                </td>
              </tr>
            </tbody>
            </c:if>
          </table>
        </div>
      </div>

      <!-- 船舶 -->
      <div class="row-fluid">
        <div class="span12">
          <table class="table table-bordered">
            <tbody>
              <tr>
                <td rowspan="2" class="center title" style="width: 3%">船舶资料</td>
                <td class="center title" style="width: 19%;">船名</td>
                <td style="width: 28%">${orderData.shipData.shipName}</td>
                <td class="center title" style="width: 19%;">总吨/净吨</td>
                <td style="width: 31%">${orderData.shipData.sumTons}/${orderData.shipData.cleanTons}</td>
              </tr>
              <tr>
                <td class="center title">载货量</td>
                <td>${orderData.shipData.aTons}吨</td>
                <td class="center title">载箱量</td>
                <td>${orderData.shipData.fullContainer}(TEU)</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 租期 -->
      <div class="row-fluid">
        <div class="span12">
          <table class="table table-bordered">
            <tbody>
              <tr height="36">
                <td rowspan="6" class="center title" style="width: 3%;">租期</td>
                <td class="center title" style="width: 19%;">开始日期</td>
                <td style="width: 28%;">${orderData.startDate}</td>
                <td class="center title" style="width: 19%;">结束日期</td>
                <td style="width: 31%;">${orderData.endDate}</td>
              </tr>
              <tr height="36">
                <td class="center title">交船日期</td>
                <td>${orderData.rcvDate}</td>
                <td class="center title">交船地点</td>
                <td>${orderData.rcvPort.fullName}</td>
              </tr>
              <tr height="36">
                <td class="center title">还船日期</td>
                <td>${orderData.retDate}</td>
                <td class="center title">还船地点</td>
                <td>${orderData.retPort.fullName}</td>
              </tr>
              <tr height="36">
                <td class="center title">租船说明</td>
                <td colspan="3">${orderData.shipDesc}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <!-- 燃油约定 -->
      <div class="row-fluid">
        <div class="span12">
          <table class="table table-bordered">
            <tbody>
              <tr>
                <td rowspan="2" class="center title" style="width: 3%">燃油费计算</td>
                <td class="center title" style="width: 19%;">油耗</td>
                <td style="width: 28%;">${orderData.oilRate}公斤/小时</td>
                <td class="center title" style="width: 19%;">油价</td>
                <td style="width: 31%;">${orderData.oilPrice}元/吨</td>
              </tr>
              <tr>
                <td class="center title">燃油费计算说明</td>
                <td colspan="3">${orderData.oilDesc}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <!-- 支付约定 -->
      <div class="row-fluid">
        <div class="span12">
          <table class="table table-bordered">
            <tbody>
              <tr>
                <td rowspan="3" class="center title" style="width: 3%">支付约定</td>
                <td class="center title" style="width: 19%;">租金率</td>
                <td style="width: 28%;text-align:left;">${orderData.rentRate}元/月</td>
                <td class="center title" style="width: 19%;">预付款</td>
                <td style="width: 31%;">${orderData.preRent}元</td>
              </tr>
              <tr>
                <td class="center title">付款分期</td>
                <td colspan="3">${orderData.paySteps}期</td>
              </tr>
              <tr>
                <td class="center title">支付结算说明</td>
                <td colspan="3">${orderData.payDesc}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 特约条款 -->
      <div class="row-fluid">
        <div class="span12">
          <table class="table table-bordered">
            <tbody>
              <tr>
                <td class="center title" style="width: 3%">特约条款</td>
                <td>${orderData.content}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 合同签名 -->
      <div class="row-fluid">
        <div class="span12">
          <table class="table table-bordered">
            <tbody>
              <tr>
                <td class="title">受托人<br />(签章)</td>
                <c:choose>
                  <c:when
                    test="${orderData.state == 'edit' || orderData.state == 'submit' || orderData.state == 'startsign'}">
                    <td style="text-align: center; width: 214px; height: 214px;">未签</td>
                  </c:when>
                  <c:otherwise>
                    <td style="text-align: center;"><img
                      style="width: 214px; height: 214px;"
                      src="${ctx}/download/imageDownload?url=${orderData.owner.signature}" /></td>
                  </c:otherwise>
                </c:choose>
                <td class="title">委托人<br />(签章)</td>
                <c:choose>
                  <c:when test="${orderData.state == 'edit' || orderData.state == 'submit'}">
                    <td style="text-alig200px; width: 214px; height: 214px;">未签</td>
                  </c:when>
                  <c:otherwise>
                    <td style="text-align: center;"><img
                      style="width: 214px; height: 214px;"
                      src="${ctx}/download/imageDownload?url=${orderData.master.signature}" /></td>
                  </c:otherwise>
                </c:choose>
              </tr>
              <tr>
                <td class="title">签名时间</td>
                <c:choose>
                  <c:when
                    test="${orderData.state == 'edit' || orderData.state == 'submit' || orderData.state == 'startsign'}">
                    <td style="text-align: center;"></td>
                  </c:when>
                  <c:otherwise>
                    <td style="text-align: center;">${orderData.owner.createTime}</td>
                  </c:otherwise>
                </c:choose>
                <td class="title">签名时间</td>
                <c:choose>
                  <c:when test="${orderData.state == 'edit' || orderData.state == 'submit'}">
                    <td style="text-align: center;"></td>
                  </c:when>
                  <c:otherwise>
                    <td style="text-align: center;">${orderData.master.createTime}</td>
                  </c:otherwise>
                </c:choose>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

    </div>
  </div>
</body>
</html>