<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>易运达 - 首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="stilearning" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-helper.css" rel="stylesheet" />
<link href="${ctx}/css/stilearn-icon.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/animate.css" rel="stylesheet" />
<link href="${ctx}/css/select2.css" rel="stylesheet" />
<link href="${ctx}/css/uniform.default.css" rel="stylesheet" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/select2.min.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>
<script src="${ctx}/js/uniform/jquery.uniform.js"></script>
<script src="${ctx}/js/knob/jquery.knob.js"></script>
<script src="${ctx}/js/peity/jquery.peity.js"></script>
<script src="${ctx}/js/holder.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/portal/portal-home.js"></script>
<script type="text/javascript">
  var _rootPath = "${ctx}";
</script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->
<style>
/* this use for demo knob only, you can delete this if you want*/
.demo-knob {
  text-align: center;
  margin-top: 10px;
  margin-bottom: 10px;
}

.w100 {
  width: 110px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

body{
  font-size:12px;
}
</style>

</head>

<body>
  <!-- section header -->
  <jsp:include page="portal-head.jsp"></jsp:include>

  <!-- section content -->
  <section class="section">
    <div class="row-fluid">
      <!-- span side-left -->
      <div class="span1">
        <!--side bar-->
        <jsp:include page="portal-sideleft.jsp"></jsp:include>
        <!--/side bar -->
      </div>
      <!-- span side-left -->

      <!-- span content -->
      <div class="span9">
        <!-- content -->
        <div class="content">
          <div class="content-header">
            <h3>接货船舶</h3>
          </div>

          <!-- content-body -->
          <div class="content-body">
            <!--row-fluid-->
            <div class="row-fluid">
              <!--span-->
              <div class="span12">
				<form name="pageform" id="pageform" action="" method="post">
                <div class="box corner-all">
                  <div class="box-header grd-white color-silver-dark corner-top">
                    <div style="font-size: 16px;">船舶列表</div>
                    <div style="margin-top: -25px; margin-left: 80px; height: 25px;">
                      <select id="changePortCity" name="changePortCity" style="width: 160px;">
                        <option value="" selected>经营区域......</option>
                        <c:forEach var="bigAreaCode" items="${bigAreaCodes}">
                          <optgroup label="${bigAreaCode.description}">
                            <c:forEach var="portCity" items="${bigAreaCode.portCities}">
                              <c:choose>
                                <c:when test="${portCity == recvCargoPort}">
                                  <option value="${portCity} " selected>${portCity.description}</option>
                                </c:when>
                                <c:otherwise>
                                  <option value="${portCity}">${portCity.description}</option>
                                </c:otherwise>
                              </c:choose>
                            </c:forEach>
                          </optgroup>
                        </c:forEach>
                      </select>

					  <select id="changeRecvCargoType" name="changeReceCargoType" style="width: 160px;">
					    <option value="" selected>接货类别......</option>
                        <c:forEach var="cargoBigTypeCode" items="${cargoTypeCodes}">
                          <c:if test="${cargoBigTypeCode.toString() == 'container'}">
                            <optgroup label="${cargoBigTypeCode.description}">
                              <c:choose>
                                <c:when test="${cargoBigTypeCode == cargoType.cargoBigType}">
                                  <option value="${cargoBigTypeCode.cargoTypes[0]}" selected>${cargoBigTypeCode.description}</option>
                                </c:when>
                                <c:otherwise>
                                  <option value="${cargoBigTypeCode.cargoTypes[0]}">${cargoBigTypeCode.description}</option>
                                </c:otherwise>
                              </c:choose>
                            </optgroup>
                          </c:if>
                          <c:if test="${cargoBigTypeCode.toString() != 'container'}">
                            <optgroup label="${cargoBigTypeCode.description}">
                              <c:forEach var="cargoTypeCode" items="${cargoBigTypeCode.cargoTypes}">
                                <c:choose>
                                  <c:when test="${cargoTypeCode == cargoType}">
                                    <option value="${cargoTypeCode}" selected>${cargoTypeCode.description}</option>
                                  </c:when>
                                  <c:otherwise>
                                    <option value="${cargoTypeCode}">${cargoTypeCode.description}</option>
                                  </c:otherwise>
                                </c:choose>
                              </c:forEach>
                            </optgroup>
                          </c:if>
                        </c:forEach>
                      </select>
                      
                      <select id="changeLoadCargo" name="changeLoadCargo" style="width: 160px;">
                        <option value="" selected>载货量......</option>
                        <c:choose>
                        <c:when test="${cargoType.cargoBigType.toString() != 'container'}">
                         <c:forEach var="cargoWeightCode" items="${cargoWeightCodes}">
                           <c:choose>
                             <c:when test="${cargoWeightCode == cargoWeight}">
                               <option value="${cargoWeightCode}" selected>${cargoWeightCode.description}</option>
                             </c:when>
                             <c:otherwise>
                               <option value="${cargoWeightCode}">${cargoWeightCode.description}</option>
                             </c:otherwise>
                           </c:choose>
                         </c:forEach>
                       </c:when>
                       <c:otherwise>
                         <c:forEach var="cargoVolumnCode" items="${cargoVolumnCodes}">
                           <c:choose>
                             <c:when test="${cargoVolumnCode == cargoVolumn}">
                               <option value="${cargoVolumnCode}" selected>${cargoVolumnCode.description}</option>
                             </c:when>
                             <c:otherwise>
                               <option value="${cargoVolumnCode}">${cargoVolumnCode.description}</option>
                             </c:otherwise>
                           </c:choose>
                         </c:forEach>
                       </c:otherwise>
                     </c:choose>
                      </select>
                      
                      <a class="btn btn-primary searchShips" style="margin-bottom: 8px">
                        <i class=" icon-search icon-white"></i>查找
                      </a>
                    </div>
                  </div>
                  <div class="box-body" style="padding: 0px;">
                    <table class="table table-bordered data-table"
                      style="word-break: break-all;margin-bottom: 0px;">
                      <thead>
                        <tr style="text-align: center;">
                          <th style="width: 10%">船舶图片</th>
		                  <th style="width: 10%">承运人</th>
		                  <th style="width: 10%">船名</th>
		                  <th style="width: 10%">MMSI编号</th>
		                  <th style="width: 10%">船类</th>
                          <th style="width: 8%">载货(吨)</th>
                          <th style="width: 8%">载箱(个)</th>
		                  <th>动态</th>
                        </tr>
                      </thead>

                      <tbody>
                        <c:forEach var="myShipData" items="${shipPageData.result}">
		                  <tr>
		                    <td>
		                    	<a href="${ctx}/portal/home/shipDetail?shipId=${myShipData.id}" target="_blank">
		                      	<c:choose>
		                          <c:when test="${!empty myShipData.shipLogo}">
		                            <img src="${ctx}/download/imageDownload?url=${myShipData.shipLogo}"
		                      		style="width: 80px; height: 60px;" alt="" class="thumbnail" />
		                          </c:when>
		                          <c:otherwise>
		                            <img src="${ctx}/img/shipImage/${myShipData.shipType}.jpg"
		                             style="width: 80px; height: 60px;" alt="" class="thumbnail" />
		                          </c:otherwise>
		                        </c:choose>
		                      	</a>
		                    </td>
		                    <td>
		                      <c:choose>
		                        <c:when test="${!empty myShipData.broker.trueName }">${myShipData.broker.trueName}</c:when>
		                        <c:otherwise>${myShipData.broker.loginName}</c:otherwise>
		                      </c:choose>
		                    </td>
		                    <td>
		                    	<a href="${ctx}/portal/home/shipDetail?shipId=${myShipData.id}" target="_blank">
		                    	${myShipData.shipName}</a>
		                    </td>
		                    <td>${myShipData.mmsi}</td>
		                    <td>${myShipData.typeData.typeName}</td>
		                    <td>${myShipData.aTons}</td>
		                    <td>${myShipData.fullContainer}</td>
		                    <td>${myShipData.arvlftDesc}</td>
		                  </tr>
		                </c:forEach>
                      </tbody>
                    </table>

                  </div>
                  <jsp:include page="./pagerHomeShip.jsp"></jsp:include>
                </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- span side-right -->
    <div class="span2">
      <!-- side-right -->
      <aside class="side-right">
        <!-- sidebar-right -->
        <div class="sidebar-right">

          <!-- sidebar-right-content -->
          <div class="sidebar-right-content">
            <div class="tab-content">
              <!--contact-->
              <div class="tab-pane fade active in" id="contact"
                style="font-size: 12px;">
                <div class="side-contact">

                  <!--contact-control-->
                  <div class="contact-control">
                    <div class="btn-group pull-right">
                      <button class="dropdown-toggle bg-transparent no-border"
                        data-toggle="dropdown">
                        <i class="icofont-caret-down"></i>
                      </button>
                      <ul class="dropdown-menu">
                        <li><a href="#"><i
                            class="icofont-certificate color-green"></i>内河</a></li>
                        <li><a href="#"><i
                            class="icofont-certificate color-silver-dark"></i>沿海</a></li>
                        <li><a href="#"><i
                            class="icofont-certificate color-red"></i>远洋</a></li>
                        <li><a href="#"><i
                            class="icofont-certificate color-orange"></i>陆路</a></li>
                      </ul>
                    </div>
                    <h5>
                      <i class="icofont-comment color-teal"></i> 在线客服
                    </h5>
                  </div>
                  <!--/contact-control-->
                  <!--contact-search-->
                  <div class="contact-search">
                    <div class="input-icon-prepend">
                      <div class="icon">
                        <button type="submit">
                          <i class="typicn-message color-silver-dark"></i>
                        </button>
                      </div>
                      <input class="input-block-level grd-white" maxlength="11"
                        type="text" name="contact-search" placeholder="chat with..." />
                    </div>
                  </div>
                  <!--/contact-search-->
                  <!--contact-list, we set this max-height:380px, you can set this if you want-->
                  <ul class="contact-list">
                    <c:forEach var="releaseOperatorData"
                      items="${releaseOperatorDatas}">
                      <li class="contact-alt grd-white"><a
                        href="${ctx}/space/chat/show?toUserId=${releaseOperatorData.operatorData.id}"
                        target="_blank">
                          <div class="contact-item">
                            <div class="pull-left">
                              <img class="contact-item-object"
                                style="width: 32px; height: 32px;"
                                src="${ctx}/download/imageDownload?url=${releaseOperatorData.operatorData.userLogo}" />
                            </div>
                            <div class="contact-item-body">
                              <!-- <div class="status" title="在线">
                                <i class="icofont-certificate color-green"></i>
                              </div> -->
                              <p class="contact-item-heading bold">${releaseOperatorData.operatorData.trueName} ${releaseOperatorData.operatorData.mobile}</p>
                              <p class="contact-item-heading">${releaseOperatorData.operatorData.unitName}</p>
                            </div>
                          </div>
                      </a></li>
                    </c:forEach>

                  </ul>
                  <!--/contact-list-->

                </div>
              </div>
              <!--/contact-->

            </div>
          </div>
          <!-- /sidebar-right-content -->
        </div>
        <!-- /sidebar-right -->
      </aside>
      <!-- /side-right -->
    </div>
    <!-- /span side-right -->
  </section>

  <!-- javascript
    ================================================== -->
</body>
</html>