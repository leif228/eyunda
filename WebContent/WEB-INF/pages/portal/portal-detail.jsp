<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>前台浏览 - 船舶详情</title>
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
<link href="${ctx}/css/uniform.default.css" rel="stylesheet" />

<script src="${ctx}/js/jquery.js"></script>
<script src="${ctx}/js/portal/portal-detail-currRoutePlay.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=3dj1jxWYQCN2UCG8MhCPVoYB" type="text/javascript"></script>
<script src="${ctx}/js/space/space-monitor-currLushu.js"></script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->
<script type="text/javascript">
    var _rootPath="${ctx}";
    
    var _points = [];
    var _pointArray = new Array();
    var _shipName = "${myShipData.shipName}";
  	  
    <c:forEach var="shipCooordData" items="${shipCooordDatas}">
  	  _points.push(["${shipCooordData.longitude}","${shipCooordData.latitude}","${shipCooordData.posTime}"]);
    </c:forEach>
</script>

<style>
/* this use for demo knob only, you can delete this if you want*/
.demo-knob {
  text-align: center;
  margin-top: 10px;
  margin-bottom: 10px;
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
          <!-- content-body -->
          <div class="content-body">
            <div>
              <p class="">${myShipData.shipTitle}</p>
            </div>

            <!--Profile-->
            <div class="row-fluid">
              <!--left-->
              <div class="span2">
                <div class="center" >
                  <h5 style="text-align: center;">${myShipData.shipName}</h5> 
                  <c:choose>
                    <c:when test="${!empty myShipData.shipLogo}">
                      <img
                        src="${ctx}/download/imageDownload?url=${myShipData.shipLogo}"
                        style="width: 300px;" class="thumbnail" />
                    </c:when>
                    <c:otherwise>
                      <img src="${ctx}/img/shipImage/${myShipData.shipType}.jpg"
                        style="width: 300px;" class="thumbnail" />
                    </c:otherwise>
                  </c:choose>
                  
                  <%-- <a class="btn btn-info isICarry" flag="${userData.isChildUser}" data-src="${myShipData.id}" href="javascript:void(0);">我有货要运</a>&nbsp;&nbsp; --%>
                  <a class="btn btn-info btnFavorite" flag="${userData.isChildUser}" data-src="${myShipData.id}" href="javascript:void(0);">收藏</a>
                </div>
              </div>
              <!--/left-->

              <!--right-->
              <div class="span10">
                <div class="center">

                  <!-- Default styles -->
                  <div class="row-fluid">
                    <div class="span12">
                      <!-- tab stat -->
                      <div class="box-body">
                        <!-- widgets-tab-body -->
                        <div class="tab-content">
                            <div class="box-body">
                              <table class="table table-bordered invoice responsive">
                                <tbody>
                                  <tr>
                                    <td colspan="4" class="bold" style="font-size: 16px; text-align: center;">最新动态</td>
                                  </tr>
                                  <tr>
                                    <td colspan="4" >
                                      <div id="mapContainer" style="height: 300px;"></div>
                                    </td>
                                  </tr>
                                  
                                  <tr>
                                    <td colspan="4" class="bold" style="font-size: 16px; text-align: center;">船舶属性</td>
                                  </tr>
                                  
                                  <tr>
                                    <td style="background: #eeeeee;" class="bold">承运人:</td>
                                    <td><a href="${ctx}/space/chat/show?toUserId=${myShipData.brokerId}" target="mychat"> 
                                      <c:choose>
                                        <c:when test="${!empty myShipData.broker.userLogo}">
                                            <img class="contact-item-object" style="width: 32px; height: 32px;"
                                              src="${ctx}/download/imageDownload?url=${myShipData.broker.userLogo}" alt="" />
                                          </c:when>
                                          <c:otherwise>
                                            <img class="contact-item-object" style="width: 32px; height: 32px;"
                                              src="${ctx}/img/user.jpg" alt="" />
                                          </c:otherwise>
                                        </c:choose>
                                        <br />${myShipData.broker.trueName} ${myShipData.broker.mobile}
                                        <br />公司: ${myShipData.broker.unitName}
                                    </a></td>
                                    <td style="background: #eeeeee;" class="bold">船舶名称:</td>
                                    <td>${myShipData.shipName}</td>
                                  </tr>
                                  <tr>
                                    <td style="background: #eeeeee;" class="bold">船类</td>
                                    <td>${myShipData.typeData.typeName}</td>
                                    <td style="background: #eeeeee;" class="bold">MMSI编号:</td>
                                    <td>${myShipData.mmsi}</td>
                                  </tr>
                                  <tr>
                                    <td style="background: #eeeeee;" class="bold">船长(米):</td>
                                    <td>${myShipData.length}</td>
                                    <td style="background: #eeeeee;" class="bold">船宽(米):</td>
                                    <td>${myShipData.breadth}</td>
                                  </tr>
                                  <tr>
                                    <td style="background: #eeeeee;" class="bold">型深(米):</td>
                                    <td>${myShipData.mouldedDepth}</td>
                                    <td style="background: #eeeeee;" class="bold">吃水深度(米):</td>
                                    <td>${myShipData.draught}</td>
                                  </tr>
                                  <tr>
                                    <td style="background: #eeeeee;" class="bold">总吨(吨):</td>
                                    <td>${myShipData.sumTons}</td>
                                    <td style="background: #eeeeee;" class="bold">净吨(吨):</td>
                                    <td>${myShipData.cleanTons}</td>
                                  </tr>
                                  <tr>
                                    <td style="background: #eeeeee;" class="bold">载重(吨):</td>
                                    <td>${myShipData.aTons}</td>
                                    <td style="background: #eeeeee;" class="bold">载箱(TEU):</td>
                                    <td>${myShipData.fullContainer}</td>
                                  </tr>
                                  
                                  <c:forEach var="attrNameData"
                                    items="${myShipData.attrNameDatas}"
                                    varStatus="attrNameStatus">

                                    <c:if test="${attrNameStatus.count%2 != 0 }">
                                      <tr>
                                    </c:if>

                                    <c:if test="${attrNameData.attrType == 'booltype'}">
                                      <td style="background: #eeeeee;" class="bold">${attrNameData.attrName}：</td>
                                      <td><c:forEach var="attrValueData"
                                          items="${attrNameData.attrValueDatas}">
                                          <c:if
                                            test="${attrNameData.currAttrValue.attrValue == attrValueData.attrValueCode }">
                                                       ${attrValueData.attrValue}
                                                        </c:if>
                                        </c:forEach></td>
                                    </c:if>

                                    <c:if test="${attrNameData.attrType == 'datetype'}">
                                      <td style="background: #eeeeee;" class="bold">${attrNameData.attrName}：</td>
                                      <td>${attrNameData.currAttrValue.attrValue}</td>
                                    </c:if>

                                    <c:if test="${attrNameData.attrType == 'dblnum' }">
                                      <td style="background: #eeeeee;" class="bold">${attrNameData.attrName}：</td>
                                      <td>${attrNameData.currAttrValue.attrValue}</td>
                                    </c:if>

                                    <c:if test="${attrNameData.attrType == 'intnum' }">
                                      <td style="background: #eeeeee;" class="bold">${attrNameData.attrName}：</td>
                                      <td>${attrNameData.currAttrValue.attrValue}</td>
                                    </c:if>

                                    <c:if test="${attrNameData.attrType == 'charstr'}">
                                      <td style="background: #eeeeee;" class="bold">${attrNameData.attrName}：</td>
                                      <td>${attrNameData.currAttrValue.attrValue}</td>
                                    </c:if>

                                    <c:if test="${attrNameData.attrType == 'charcode'}">
                                      <td style="background: #eeeeee;" class="bold">${attrNameData.attrName}：</td>
                                      <td><c:forEach var="attrValueData"
                                          items="${attrNameData.attrValueDatas}">
                                          <c:if
                                            test="${attrNameData.currAttrValue.attrValue == attrValueData.attrValueCode}">
                                                         ${attrValueData.attrValue}
                                                          </c:if>
                                        </c:forEach></td>
                                    </c:if>

                                    <%-- <c:if test="${myShipData.attrNameDatas[end]}"> <td></td></tr>
                                      	 </c:if>
                                    --%>

                                    <c:if test="${attrNameStatus.count%2 == 0 }">
                                    </tr>
                                    </c:if>

                                    <c:if test="${attrNameStatus.last}">
                                      <c:choose>
                                        <c:when
                                          test="${fn:length(myShipData.attrNameDatas) == 1}">
                                          <td style="background: #eeeeee;"></td>
                                          <td></td>
                                          </tr>
                                        </c:when>
                                        <c:when
                                          test="${fn:length(myShipData.attrNameDatas)> 1 && fn:length(myShipData.attrNameDatas)%2 != 0}">
                                          <td style="background: #eeeeee;"></td>
                                          <td></td>
                                          </tr>
                                        </c:when>
                                        <c:when
                                          test="${fn:length(myShipData.attrNameDatas)> 1 && fn:length(myShipData.attrNameDatas)%2 == 0}">
                                        </c:when>
                                      </c:choose>
                                    </c:if>
                                  </c:forEach>
                                  
                                  <tr>
                                    <td colspan="4" class="bold" style="font-size: 16px; text-align: center;">接货类别</td>
                                  </tr>
                                  
                                  <tr>
                                    <td colspan="4">
                                      <c:forEach varStatus="status" var="cargoType" items="${myShipData.shipCargoTypes}">
                                        <div class="span2" style ="margin-left:10px;">${cargoType.cargoBigType.description }</div>
                                      </c:forEach>
                                    </td>
                                  </tr>
                                  
                                  <tr>
                                    <td colspan="4" class="bold" style="font-size: 16px; text-align: center;">接货港口</td>
                                  </tr>
                                  <tr>
                                    <td colspan="4">	
                                      <c:forEach var="shipPortData" items="${myShipData.shipPortDatas}">
                                        <div class="span3" style ="margin-left:10px;">${shipPortData.cityName}</div>
                                      </c:forEach>
                                    </td>
                                  </tr>
                                  
                                  <tr>
                                    <td colspan="4" class="bold" style="font-size: 16px; text-align: center;">船舶详情</td>
                                  </tr>
                                  
                                  <c:forEach var="myShipAttaData" items="${myShipData.myShipAttaDatas}">
                                    <tr>
                                      <td colspan="4">
			                            <c:if test="${!empty myShipAttaData.url}">
			                              <div class=" detail-descrip-info"
			                                style="text-align: center;">
			                                <div>
			                                  <img src="${ctx}/download/imageDownload?url=${myShipAttaData.url}"
			                                    style="margin: 0 auto; border: 1px solid #ccc; padding: 2px;"
			                                    class="thumbnail" width="600" />
			                                </div>
			                                <div
			                                  style="width: 600px; text-align: center; margin: 10px auto;"
			                                  class="detail-descrip-text">${myShipAttaData.titleDes}
			                                </div>
			                              </div>
			                            </c:if>
			                            <c:if test="${empty myShipAttaData.url}">
			                              <div class=" detail-descrip-info" style="text-align: left;">
			                                <div style="width: 600px; text-align: left; margin: 10px auto;"
			                                  class="detail-descrip-text">${myShipAttaData.titleDes}
			                                </div>
			                              </div>
			                            </c:if>
		                              </td>
		                            </tr>
		                          </c:forEach>
                                  
                                  <tr>
                                    <td colspan="4" class="bold" style="font-size: 16px; text-align: center;">合同条款 ( ${templateData.title} )</td>
                                  </tr>
                                  <c:forEach var="tempItemData" items="${templateData.tempItemDatas}">
                                    <tr>
                                      <td colspan="4">
                                        <div style="text-align: left; margin: 5px auto;" class="detail-descrip-text">
                                          ${tempItemData.no}. ${tempItemData.content}
                                		</div>
                                      </td>
                                    </tr>
                                  </c:forEach>
                                  
                                  <tr>
                                    <td colspan="4" class="bold" style="font-size: 16px; text-align: center;">船舶评价</td>
                                  </tr>
                                  <c:forEach var="evaluateData" items="${evaluateDatas}">
                                    <tr>
                                      <td colspan="4">
	                                	<div class="media">
		                              	  <a class="pull-left" href="#"> 
		                                	<img src="${ctx}/download/imageDownload?url=${evaluateData.userData.userLogo}" class="thumbnail" style="width: 48px; height: 48px;" />
		                              	  </a>
		                              	  <div class="media-body">
		                                	<h4 class="media-heading">
		                                      <a href="#">${evaluateData.evalType.description}</a> 
		                                      <small class="helper-font-small">
		                                        ${evaluateData.userData.trueName} ${evaluateData.createTime}
		                                      </small>
		                                    </h4>
		                                    <p>${evaluateData.evalContent}</p>
		                                  </div>
		                           		</div>
		                           	  </td>
		                           </tr>
		                         </c:forEach>
                                  
                                </tbody>
                              </table>
                            </div>
                          
                        </div>
                      </div>

                    </div>
                    <!-- /span -->
                  </div>
                  <!--/Default styles-->
                </div>
              </div>
              <!--/right-->
            </div>
            <!--/Profile-->

          </div>
          <!--/content-body -->
        </div>
        <!-- /content -->
      </div>
      <!-- /span content -->

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
                        <i class="icofont-comment color-teal"></i> 联系人
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
                          type="text" name="contact-search"
                          placeholder="chat with..." />
                      </div>
                    </div>
                    <!--/contact-search-->
                    <!--contact-list, we set this max-height:380px, you can set this if you want-->
                    <ul class="contact-list">
                      <c:forEach var="userData" items="${userDatas}">
                        <li class="contact-alt grd-white">
                          <a href="${ctx}/space/chat/show?toUserId=${userData.id}" target="_blank">
                            <div class="contact-item">
                              <div class="pull-left">
                                <img class="contact-item-object"
                                  style="width: 32px; height: 32px;"
                                  src="${ctx}/download/imageDownload?url=${userData.userLogo}" />
                              </div>
                              <div class="contact-item-body">
                                <div class="status" title="ofline">
                                  <i class="icofont-certificate color-silver-dark"></i>
                                </div>
                                <p class="contact-item-heading bold">${userData.trueName} ${userData.mobile}</p>
                                <p class="contact-item-heading">${userData.unitName}</p>
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
    </div>
  </section>
  
  <!-- javascript
        ================================================== -->
  <script src="${ctx}/js/bootstrap.js"></script>
  <script src="${ctx}/js/uniform/jquery.uniform.js"></script>

  <script src="${ctx}/js/knob/jquery.knob.js"></script>
  <script src="${ctx}/js/peity/jquery.peity.js"></script>

  <!-- required stilearn template js, for full feature-->
  <script src="${ctx}/js/holder.js"></script>
  <script src="${ctx}/js/jquery.form-2.63.js"></script>
  <script src="${ctx}/js/portal/portal-detail.js"></script>
  <script type="text/javascript">
    var _rootPath = "${ctx}";
  </script>

</body>
</html>