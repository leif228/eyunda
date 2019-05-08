<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>货物信息</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/css/reset.css" media="screen" />
<script src="${ctx}/js/jquery.min.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>

<style>
<!--
.order-title {
  background: #E4E4E4;
  height: 30px;
  line-height: 30px;
  font-weight: bold;
  margin-bottom: 10px;
  text-align: center
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

.boat-detail-text {
  margin: 10px 0px;
}
/*评论样式*/
.one-comment {
  margin-bottom: 10px;
}

.one-comment-logo {
  width: 10%;
}

.one-comment-logo img {
  float: left;
  width: 40px;
  height: 40px;
  border: 1px solid #ccc;
  padding: 2px;
  border-radius: 4px;
}

.one-comment-detail {
  float: left;
  margin-left: 10px;
  width: 80%;
}

.one-comment-time {
  float: right;
}

.one-comment-detail-res {
  font-weight: bold;
}

img {
  width: 100%;
}

@media
(
max-width
:
767px)
{
body {
  padding-right: 2px;
  padding-left: 2px;
}
}
-->
</style>

</head>
<body>
  <jsp:include page="./head.jsp"></jsp:include>
  <div id="container" style="margin: 0px auto">
    <div id="order-basic-info" style="margin-top: 5px;">
      <div class="fluid">
        <table class="table table-bordered">
          <thead>
            <tr>
              <th colspan="4" style="text-align: center; background: #E4E4E4;">货物信息</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td colspan="2" style="text-align: center;"><c:choose>
                  <c:when test="${!empty cargoData.cargoImage}">
                    <img width="100%"
                      src="${ctx}/download/imageDownload?url=${cargoData.cargoImage}" />
                  </c:when>
                  <c:otherwise>
                    <img src="${ctx}/img/cargoImage/${cargoData.cargoType}.jpg" />
                  </c:otherwise>
                </c:choose></td>
            </tr>

            <tr>
              <td width="30%" style="font-weight: bold">装货港</td>
              <td>${cargoData.startPortData.fullName }</td>
            </tr>
            <tr>
              <td style="font-weight: bold">卸货港</td>
              <td>${cargoData.endPortData.fullName }</td>
            </tr>
            <tr>
              <td style="font-weight: bold">托运人</td>
              <td>
                <c:if test="${!empty cargoData.agent}">
                  ${cargoData.agent.trueName}
                </c:if>
                <c:if test="${empty cargoData.agent}">
                  ${cargoData.owner.trueName}
                </c:if>
              </td>
            </tr>
            <tr>
              <td style="font-weight: bold">托运人电话</td>
              <td>
                <c:if test="${!empty cargoData.agent}">
                  <c:if test="${!empty cargoData.agent.mobile}">${cargoData.agent.mobile}</c:if>
                </c:if>
                <c:if test="${empty cargoData.agent}">
                  <c:if test="${!empty cargoData.owner.mobile}">${cargoData.owner.mobile}</c:if>
                </c:if>
              </td>
            </tr>
            <tr>
              <td style="font-weight: bold">货号</td>
              <td>${cargoData.id}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">货类</td>
              <td>${cargoData.cargoType.description }</td>
            </tr>
            <tr>
              <td style="font-weight: bold">货名</td>
              <td>${cargoData.cargoName}</td>
            </tr>

            <tr>
              <td style="font-weight: bold">包装</td>
              <td>${cargoData.wrapStyle.description }</td>
            </tr>
            <tr>
              <td style="font-weight: bold">数量</td>
              <td>${cargoData.wrapCount }</td>
            </tr>
            <tr>
              <td style="font-weight: bold">单重</td>
              <td>${cargoData.unitWeightDes }</td>
            </tr>
            <tr>
              <td style="font-weight: bold">总重</td>
              <td>${cargoData.fullWeightDes }</td>
            </tr>
            <tr>
              <td style="font-weight: bold">长</td>
              <td>${cargoData.ctlLengthDes }</td>
            </tr>
            <tr>
              <td style="font-weight: bold">宽</td>
              <td>${cargoData.ctlWidthDes }</td>
            </tr>
            <tr>
              <td style="font-weight: bold">高</td>
              <td>${cargoData.ctlHeightDes }</td>
            </tr>
            <tr>
              <td style="font-weight: bold">体积</td>
              <td>${cargoData.ctlVolumeDes }</td>
            </tr>

            <tr>
              <td style="font-weight: bold">货量</td>
              <td>${cargoData.tonTeuDes }</td>
            </tr>
            <tr>
              <td style="font-weight: bold">价格</td>
              <td>${cargoData.priceDes }</td>
            </tr>
            <tr>
              <td style="font-weight: bold">总价</td>
              <td>${cargoData.transFeeDes }</td>
            </tr>
            <tr>
              <td style="font-weight: bold">截止日期</td>
              <td>${cargoData.periodTime }</td>
            </tr>
          </tbody>
        </table>

        <div class="order-title">代理人列表</div>
        <c:if test="${!empty operatorDatas}">
          <c:forEach items="${operatorDatas}" var="operatorData">
            <table class="table table-bordered">
              <tbody>
                <tr>
                  <c:choose>
                    <c:when test="${empty operatorData.userData.userLogo}">
                      <td colspan="4" style="text-align: center">暂无该代理人图片！</td>
                    </c:when>
                    <c:otherwise>
                      <td colspan="2" style="text-align: center;"><img
                        width="100%"
                        src="${ctx}/download/imageDownload?url=${operatorData.userData.userLogo}" />
                      </td>
                    </c:otherwise>
                  </c:choose>
                </tr>
                <tr>
                  <td class="bold">姓名</td>
                  <td><c:choose>
                      <c:when test="${!empty operatorData.userData.trueName}">${operatorData.userData.trueName}</c:when>
                      <c:otherwise>${operatorData.userData.nickName}</c:otherwise>
                    </c:choose></td>
                </tr>
                <tr>
                  <td class="bold">邮箱</td>
                  <td>${operatorData.userData.email}</td>
                </tr>
                <tr>
                  <td class="bold">地区</td>
                  <td>${operatorData.companyData.portCity}</td>
                </tr>
                <tr>
                  <td class="bold">船舶数</td>
                  <td>${operatorData.shipNumber}</td>
                </tr>
                <tr>
                  <td class="bold">成交数</td>
                  <td>${operatorData.orderNumber}</td>
                </tr>
                <tr>
                  <td class="bold">联系电话</td>
                  <td>${operatorData.telephone}</td>
                </tr>
              </tbody>
            </table>
            <div class="divider-content">
              <span></span>
            </div>
          </c:forEach>
        </c:if>
      </div>
    </div>
    <jsp:include page="./foot.jsp"></jsp:include>
</body>
</html>