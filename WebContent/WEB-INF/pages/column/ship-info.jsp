<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>车船信息</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
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

/*评价样式*/
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
  <jsp:include page="./head.jsp"></jsp:include>
  <div id="container" style="margin: 0px auto">
    <div id="order-basic-info" style="margin-top: 5px;">
      <div class="fluid">
        <table class="table table-bordered">
          <thead>
            <tr>
              <th colspan="2" style="text-align: center; background: #E4E4E4;">${myShipData.shipName}</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <c:choose>
                <c:when test="${empty myShipData.shipLogo}">
                  <td colspan="2" style="text-align: center"><img width="100%" 
                    src="${ctx}/img/shipImage/${myShipData.shipType}.jpg" />
                  </td>
                </c:when>
                <c:otherwise>
                  <td colspan="2" style="text-align: center;"><img width="100%"
                    src="${ctx}/download/imageDownload?url=${myShipData.shipLogo}" />
                  </td>
                </c:otherwise>
              </c:choose>
            </tr>
            <tr>
              <td colspan="2">${myShipData.shipTitle}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">船舶名称</td>
              <td>${myShipData.shipName}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">承运人</td>
              <td>
	            <c:choose>
             	  <c:when test="${!empty myShipData.broker.trueName}">${myShipData.broker.trueName}</c:when>
             	  <c:otherwise>${myShipData.broker.loginName}</c:otherwise>
             	</c:choose>
              </td>
            </tr>
            <tr>
              <td style="font-weight: bold">承运人电话</td>
              <td>
             	<c:if test="${!empty myShipData.broker.mobile}">${myShipData.broker.mobile}</c:if>
              </td>
            </tr>
            <tr>
              <td style="font-weight: bold">MMSI编号</td>
              <td>${myShipData.mmsi}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">航区</td>
              <td>${myShipData.keyWords}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">船类</td>
              <td>${myShipData.typeData.typeName}</td>
            </tr>

            <tr>
              <td style="font-weight: bold">IMO</td>
              <td>${myShipData.imo}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">呼号</td>
              <td>${myShipData.callsign}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">船长(米)</td>
              <td>${myShipData.length}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">船宽(米)</td>
              <td>${myShipData.breadth}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">型深(米)</td>
              <td>${myShipData.mouldedDepth}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">吃水深度(米)</td>
              <td>${myShipData.draught}</td>
            </tr>

            <tr>
              <td style="font-weight: bold">总吨(吨)</td>
              <td>${myShipData.sumTons}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">净吨(吨)</td>
              <td>${myShipData.cleanTons}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">载重A级(吨)</td>
              <td>${myShipData.aTons}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">载重B级(吨)</td>
              <td>${myShipData.bTons}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">重箱(TEU)</td>
              <td>${myShipData.fullContainer}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">半重箱(TEU)</td>
              <td>${myShipData.halfContainer}</td>
            </tr>
            <tr>
              <td style="font-weight: bold">吉箱(TEU)</td>
              <td>${myShipData.spaceContainer}</td>
            </tr>

            <c:forEach var="attrNameData" items="${myShipData.attrNameDatas}">
              <tr>
                <td style="font-weight: bold">${attrNameData.attrName}</td>
                <td><c:if test="${attrNameData.attrType == 'booltype'}">
                    <c:forEach var="attrValueData"
                      items="${attrNameData.attrValueDatas}">
                      <c:if
                        test="${attrNameData.currAttrValue.attrValue == attrValueData.attrValueCode || isFirst=='true'}">
                        ${attrValueData.attrValue}
                      </c:if>
                    </c:forEach>
                  </c:if> <c:if test="${attrNameData.attrType == 'datetype'}">
                  ${attrNameData.currAttrValue.attrValue}
                  </c:if> <c:if test="${attrNameData.attrType == 'dblnum'}">
                  ${attrNameData.currAttrValue.attrValue}
                  </c:if> <c:if test="${attrNameData.attrType == 'intnum'}">
                  ${attrNameData.currAttrValue.attrValue}
                  </c:if> <c:if test="${attrNameData.attrType == 'charstr'}">
                  ${attrNameData.currAttrValue.attrValue}
                  </c:if> <c:if test="${attrNameData.attrType == 'charcode'}">
                    <c:forEach var="attrValueData"
                      items="${attrNameData.attrValueDatas}">
                      <c:if
                        test="${attrNameData.currAttrValue.attrValue == attrValueData.attrValueCode}">
                        ${attrValueData.attrValue}
                      </c:if>
                    </c:forEach>
                  </c:if></td>
              </tr>
            </c:forEach>
          </tbody>
        </table>

        <div class="order-title">船舶动态</div>
        <table class="table table-bordered">
          <tbody>
            <tr>
              <td>
                <c:forEach items="${myShipData.shipArvlftDatas}" var="shipArvlft">
                ${shipArvlft.arvlftDesc}<br />
                </c:forEach>
              </td>
            </tr>
          </tbody>
        </table>

        <div class="order-title">接货类别</div>
          <table class="table table-bordered">
            <tbody>
                <c:forEach var="cargoType" items="${myShipData.shipCargoTypes}" varStatus="status">
                  <c:if test="${(status.count-1)%4 == 0}">
                  <tr>
                  </c:if>
                  <td>
                  <c:if test="${cargoType.cargoBigType == 'container'}">
                    <div>${cargoType.cargoBigType.description }</div>
                  </c:if>
                  <c:if test="${cargoType.cargoBigType != 'container'}">
                    <div>${cargoType.description }</div>
                  </c:if>
                  </td>
                  <c:if test="${(status.count)%4 == 0}">
                  </tr>
                  </c:if>
                </c:forEach>
                <c:forEach begin="1" end="${(4-fn:length(myShipData.shipCargoTypes)%4)%4}" var="i">
                  <td></td>
                  <c:if test="${(i + fn:length(myShipData.shipCargoTypes)%4)%4 == 0}">
                  </tr>
                  </c:if>
                </c:forEach>
            </tbody>
          </table>

        <div class="order-title">经营区域</div>
          <table class="table table-bordered">
            <tbody>
                <c:forEach var="shipPortData" items="${myShipData.shipPortDatas}" varStatus="status">
                  <c:if test="${(status.count-1)%4 == 0}">
                  <tr>
                  </c:if>
                  <td>
                    <div>${shipPortData.cityName}</div>
                  </td>
                  <c:if test="${(status.count)%4 == 0}">
                  </tr>
                  </c:if>
                </c:forEach>
                <c:forEach begin="1" end="${(4-fn:length(myShipData.shipPortDatas)%4)%4}" var="i">
                  <td></td>
                  <c:if test="${(i + fn:length(myShipData.shipPortDatas)%4)%4 == 0}">
                  </tr>
                  </c:if>
                </c:forEach>
            </tbody>
          </table>

        <div class="order-title">船舶详情</div>
        <div id="order-more-detail">
          <div class="fluid">
            <c:forEach items="${myShipData.myShipAttaDatas}" var="shipAtta"
              varStatus="getIndex">
              <c:if test="${!empty shipAtta.url}">
                <div>
                  <img src="${ctx}/download/imageDownload?url=${shipAtta.url}" />
                </div>
              </c:if>
              <c:if test="${!empty shipAtta.titleDes}">
                <c:if test="${!empty shipAtta.url}">
                  <div style="text-align: center">${shipAtta.titleDes}</div>
                </c:if>
                <c:if test="${empty shipAtta.url}">
                  <div style="text-align: left">${shipAtta.titleDes}</div>
                </c:if>
              </c:if>
            </c:forEach>
          </div>
        </div>
        <!-- 详情结束 -->

        <!-- 评价开始 -->
        <div class="order-title">会员评价</div>

        <div class="fluid">
          <c:forEach items="${evaluateDatas}" var="evaluate"
            varStatus="getIndex">
            <div class="one-comment">
              <!-- 头像 -->
              <div class="one-comment-logo" style="width: 10%">
                <c:choose>
                  <c:when test="${empty evaluate.userData.userLogo}">
                  </c:when>
                  <c:otherwise>
                    <img
                      src="${ctx}/download/imageDownload?url=${evaluate.userData.userLogo}" />
                  </c:otherwise>
                </c:choose>
              </div>
              <div class="one-comment-detail" style="width: 85%">
                <!-- 评价结果 -->
                <p class="one-comment-detail-res">${evaluate.evalType.description}
                  <span class="one-comment-time">${evaluate.createTime}</span>
                </p>
                <!-- 评价内容 -->
                <p class="one-comment-detail-content">${evaluate.evalContent}</p>
              </div>
              <div class="clear"></div>
            </div>
            <c:if test="${getIndex.count%2==0}">
        </div>
        <!-- 间隔条 -->
        <div class="divider-content">
          <span></span>
        </div>
        <div class="fluid">
          </c:if>
          </c:forEach>
        </div>
      </div>
    </div>
    <jsp:include page="./foot.jsp"></jsp:include>
</body>
</html>