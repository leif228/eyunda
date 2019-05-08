<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>合同</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>
<style>
.ctx-container {
  width: 98%;
  margin: 0px auto
}

<!--
.order-title {
  background: #E4E4E4;
  height: 30px;
  line-height: 30px;
  font-weight: bold;
  margin-bottom: 10px;
  text-align: center
}

img {
  width: 100%;
}

.bold {
  font-weight: bold;
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
  border-left: 1px solid #dddddd;
  border-right: 1px solid #dddddd;
  border-top: 1px solid #dddddd;
  border-collapse: separate;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0px;
  margin-bottom: 20px;
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

@media ( max-width : 767px) {
  body {
    padding-right: 2px;
    padding-left: 2px;
  }
}
-->
</style>
</head>
<body style="font-family: 'Arial Unicode MS'">
  <div id="container" style="margin: 0px auto">

    <div id="order-basic-info" style="margin-top: 5px;"
      class="ctx-container">
      <div class="contain-head bold" style="font-size: 16px; border-bottom: 1px solid #dddddd">
        <c:if test="${empty orderData.shipData}">批次运输合同(合同号:${orderData.id})</c:if>
        <c:if test="${!empty orderData.shipData}">${orderData.shipData.shipName}航次运输合同(合同号:${orderData.id})</c:if>
      </div>

      <div class="row-fluid">
        <div class="span4">
          <table class="table table-bordered">
            <tbody>
              <tr>
                <td class="title">托运人</td>
                <td>
                  <c:if test="${!empty orderData.owner.unitName}">${orderData.owner.unitName}</c:if>
                  <c:if test="${empty orderData.owner.unitName}">${orderData.owner.trueName}</c:if>
                </td>
              </tr>
              <tr>
                <td class="title">经手人</td>
                <td>
                  <c:if test="${!empty orderData.owner.unitName}">${orderData.owner.trueName}</c:if>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="span4">
          <table class="table table-bordered">
            <tbody>
              <tr>
                <td class="title">承运人</td>
                <td>
                  <c:if test="${!empty orderData.master.unitName}">${orderData.master.unitName}</c:if>
                  <c:if test="${empty orderData.master.unitName}">${orderData.master.trueName}</c:if>
                </td>
              </tr>
              <tr>
                <td class="title">经手人</td>
                <td>
                  <c:if test="${!empty orderData.master.unitName}">${orderData.master.trueName}</c:if>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <c:if test="${orderData.master.id != orderData.broker.id || orderData.handler.id != orderData.broker.id}">
        <div class="span4">
          <table class="table table-bordered">
            <tbody>
              <tr>
                <td class="title">代理人</td>
                <td>
                  <c:if test="${!empty orderData.broker.unitName}">${orderData.broker.unitName}</c:if>
                  <c:if test="${empty orderData.broker.unitName}">${orderData.broker.trueName}</c:if>
                </td>
              </tr>
              <tr>
                <td class="title">经手人</td>
                <td>${orderData.handler.trueName}</td>
              </tr>
            </tbody>
          </table>
        </div>
        </c:if>
      </div>

      <c:if test="${!empty orderData.shipData}">
      <div class="order-title">船舶规范</div>
      <div class="row-fluid">
        <div class="span12">
          <table class="table table-bordered">
            <tbody>
              <tr>
                <td class="title">船舶名称</td>
                <td>${orderData.shipData.shipName}</td>
              </tr>
              <tr>
                <td class="title">总吨/净吨</td>
                <td>${orderData.shipData.sumTons}/${orderData.shipData.cleanTons}</td>
              </tr>
              <tr>
                <td class="title">载货量</td>
                <td>${orderData.shipData.aTons}吨</td>
              </tr>
              <tr>
                <td class="title">载箱量</td>
                <td>${orderData.shipData.fullContainer}TEU</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      </c:if>

      <div class="order-title">货物清单</div>
      <div class="row-fluid">
          <div class="span12">
            <table class="table table-bordered"
              style="max-width: 100%; overflow: hidden; margin: 5px 0px;">
              <tbody>
                <tr>
                <td class="title">货类</td>
                <td>
                  <c:if test="${orderData.cargoType=='container20e'}">${orderData.cargoType.cargoBigType.description}</c:if>
                  <c:if test="${orderData.cargoType!='container20e'}">${orderData.cargoType.description}</c:if>
                </td>
                </tr>
                <tr>
                <td class="title">货名</td>
                <td>
                  <c:if test="${orderData.cargoType=='container20e'}">${orderData.containerNames}</c:if>
                  <c:if test="${orderData.cargoType!='container20e'}">${orderData.cargoName}</c:if>
                </td>
                </tr>
                <tr>
                <td class="title">货量</td>
                <td>
                  <c:if test="${orderData.cargoType=='container20e'}">${orderData.containerTeus}</c:if>
                  <c:if test="${orderData.cargoType!='container20e'}">${orderData.tonTeu}吨</c:if>
                </td>
                </tr>
                <tr>
                <td class="title">运价</td>
                <td>
                  <c:if test="${orderData.cargoType=='container20e'}">${orderData.containerPrices}</c:if>
                  <c:if test="${orderData.cargoType!='container20e'}">${orderData.price}元/吨</c:if>
                </td>
                </tr>
                <tr>
                <td class="title">运费(小计)</td>
                <td>
                  <c:if test="${orderData.cargoType=='container20e'}">${orderData.containerFees}</c:if>
                  <c:if test="${orderData.cargoType!='container20e'}">${orderData.transFee}元</c:if>
                </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div style="clear: both"></div>
          <div class="divider-content">
            <span></span>
          </div>
      </div>

      <div class="order-title">装卸约定</div>
      <div class="row-fluid">
        <div class="span12">
          <table class="table table-bordered" style="margin-bottom: 20px">
            <tbody>
              <tr>
                <td class="title">装货港</td>
                <td>${orderData.startPort.fullName}</td>
              </tr>
              <tr>
                <td class="title">卸货港</td>
                <td>${orderData.endPort.fullName}</td>
              </tr>
              <tr>
                <td class="title">受载开始日期</td>
                <td>${orderData.upDate}</td>
              </tr>
              <tr>
                <td class="title">受载结束日期</td>
                <td>${orderData.downDate}</td>
              </tr>
              <tr>
                <td class="title">允许装货时间</td>
                <td>${orderData.upTime}小时</td>
              </tr>
              <tr>
                <td class="title">允许卸货时间</td>
                <td>${orderData.downTime}小时</td>
              </tr>
              <%-- <tr>
                <td class="title">收货人</td>
                <td>${orderData.receiver}</td>
              </tr>
              <tr>
                <td class="title">收货人电话</td>
                <td>${orderData.receMobile}</td>
              </tr> --%>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 支付 -->
      <div class="order-title">支付</div>
      <div class="row-fluid">
        <div class="span12">
          <table class="table table-bordered" style="margin-bottom: 20px">
            <tbody>
              <tr>
                <td class="title">合同金额</td>
                <td>${orderData.transFee}元</td>
              </tr>
              <tr>
                <td class="title">滞期费率</td>
                <td>${orderData.demurrage}元/天</td>
              </tr>
              <tr>
                <td class="title">其中：代理人佣金</td>
                <td>${orderData.brokerFee}元</td>
              </tr>
              <tr>
                <td class="title">平台服务费</td>
                <td>${orderData.platFee}元</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 条款 -->
      <div class="order-title">特约条款</div>
      <div class="row-fluid">
        <div class="span12">
          <table class="table table-bordered">
            <tbody>
              <tr>
                <td>${orderData.showOrderContent}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div class="order-title">合同签名</div>
      <div class="row-fluid">
        <div class="span4">
          <table class="table table-bordered" style="margin-bottom: 20px">
            <tbody>
              <tr>
                <td class="title">托运人(签章)</td>
                <c:choose>
                  <c:when
                    test="${orderData.status == 'edit' || orderData.status == 'startsign'}">
                    <td colspan="3">未签</td>
                  </c:when>
                  <c:otherwise>
                    <td style="text-align: center;"><img
                      style="width: 300px; height: 300px;"
                      src="${ctx}/download/imageDownload?url=${orderData.owner.signature}" /></td>
                  </c:otherwise>
                </c:choose>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="span4">
          <table class="table table-bordered" style="margin-bottom: 20px">
            <tbody>
              <tr>
                <td class="title">承运人(签章)</td>
                <c:choose>
                  <c:when test="${orderData.status == 'edit' }">
                    <td>未签</td>
                  </c:when>
                  <c:otherwise>
                    <td style="text-align: center;"><img
                      style="width: 300px; height: 300px;"
                      src="${ctx}/download/imageDownload?url=${orderData.master.signature}" /></td>
                  </c:otherwise>
                </c:choose>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
</body>
</html>