<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>前台浏览 - 货物详情</title>
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
<script type="text/javascript">
	var _rootPath = "${ctx}";
</script>

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->
<style>
/* this use for demo knob only, you can delete this if you want*/
body {
  font-size: 12px;
}

.layout-right, .fr {
  float: right;
}

.dn {
  display: none
}

.fl {
  float: left;
}

.clearfix:after {
  visibility: hidden;
  display: block;
  font-size: 0px;
  content: " ";
  clear: both;
  height: 0px;
}

.user-glory {
  font-size: 12px;
  font-weight: normal;
  color: red
}

.box-body>.center {
  text-align: center
}

.box>.box-body {
  padding: 0px
}

.userInfo {
  height: 60px;
  padding: 20px 0px;
  background: #fff
}

.userLogo {
  text-align: right;
  line-height: 40px;
  width: 80px
}

.content>.content-body {
  padding-top: 20px;
  border-top: 1px solid #ccc;
}

.table th {
  text-align: center
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
          <!-- content-header -->
          <div class="content-header">
            <h3>
              <i class="icofont-home"></i>货物：${cargoData.cargoTypeName}
            </h3>
          </div>
          <!-- /content-header -->
          <!-- content-breadcrumb -->
          <div class="content-breadcrumb content-body"
            style="padding-bottom: 0px">
            <!--breadcrumb-->
            <div class="row-fluid" style="background: #fff">
              <!--left-->
              <div class="span2">
                <div class="center">
                  <h5 style="text-align: center;">${cargoData.cargoName}</h5> 
                  <c:choose>
                    <c:when test="${!empty cargoData.cargoImage}">
                      <img src="${ctx}/download/imageDownload?url=${cargoData.cargoImage}"
                         class="thumbnail" style="width: 300px;" />
                    </c:when>
                    <c:otherwise>
                      <img src="${ctx}/img/cargoImage/${cargoData.cargoType}.jpg"
                         class="thumbnail" style="width: 300px;" />
                    </c:otherwise>
                  </c:choose>
                  <%-- <c:if test="${cargoData.cargoStatus != 'publish'}">
                  	<a class="btn btn-info isICarry" flag="${userData.isChildUser}" data-src="${cargoData.id}" href="javascript:void(0)">我来运</a>
                  </c:if>
                  <c:if test="${cargoData.cargoStatus == 'publish'}">
                  	<a class="btn btn-info" disabled>我来运</a>
                  </c:if> --%>
                </div>
              </div>
              <!--/left-->

              <!--span-->
              <div class="span10">
                <!--box-->
                <div class="box corner-all" style="margin-bottom: 0px">
                  <!--box header-->
                  <%-- <div class="box-header grd-white color-silver-dark corner-top">
                    <span>货物发布时间</span>
                    <div style="float: right; color: red;">
                    	<span>${cargoData.createTime}</span>
                    </div>
                  </div> --%>

                  <!--box body-->
                  <table class="table table-bordered data-table">
                    <tbody>
                      <tr>
                        <td colspan="4" class="bold" style="font-size: 16px; text-align: center;">货物属性</td>
                      </tr>
                      
                      <tr>
                        <td style="background: #eeeeee;" class="bold">托运人:</td>
                        <td>
                        <c:if test="${!empty cargoData.agent}">
                          <a href="${ctx}/space/chat/show?toUserId=${cargoData.agentId}" target="mychat">
                            <c:choose>
                              <c:when test="${!empty cargoData.agent.userLogo}">
                                <img class="contact-item-object" style="width: 32px; height: 32px;" alt="" 
                                  src="${ctx}/download/imageDownload?url=${cargoData.agent.userLogo}" />
                              </c:when>
                              <c:otherwise>
                                <img class="contact-item-object" style="width: 32px; height: 32px;"
                                  src="${ctx}/img/user.jpg" alt="" />
                              </c:otherwise>
                            </c:choose>
                            <br /> ${cargoData.agent.trueName} ${cargoData.agent.mobile} 
                            <br /> 公司: ${cargoData.agent.unitName}
                          </a>
                        </c:if>
                        <c:if test="${empty cargoData.agent}">
                          <a href="${ctx}/space/chat/show?toUserId=${cargoData.ownerId}" target="mychat">
                            <c:choose>
                              <c:when test="${!empty cargoData.owner.userLogo}">
                                <img class="contact-item-object" style="width: 32px; height: 32px;" alt="" 
                                  src="${ctx}/download/imageDownload?url=${cargoData.owner.userLogo}" />
                              </c:when>
                              <c:otherwise>
                                <img class="contact-item-object" style="width: 32px; height: 32px;"
                                  src="${ctx}/img/user.jpg" alt="" />
                              </c:otherwise>
                            </c:choose> 
                            <br /> ${cargoData.owner.trueName} ${cargoData.owner.mobile}
                          </a>
                        </c:if>
                        </td>
                        <td style="background: #eeeeee;" class="bold">货号:</td>
                        <td>${cargoData.id}</td>
                      </tr>
                      <tr>
                        <td style="background: #eeeeee;" class="bold">装货港:</td>
                        <td>${cargoData.startPortData.fullName }</td>
                        <td style="background: #eeeeee;" class="bold">卸货港:</td>
                        <td>${cargoData.endPortData.fullName }</td>
                      </tr>

                      <%-- <tr>
                        <td style="background: #eeeeee;" class="bold">数量:</td>
                        <td>${cargoData.wrapCount }</td>
                        <td style="background: #eeeeee;" class="bold">包装:</td>
                        <td>${cargoData.wrapStyle.description }</td>
                      </tr>
                      <tr>
                        <td style="background: #eeeeee;" class="bold">单重:</td>
                        <td>${cargoData.unitWeightDes }</td>
                        <td style="background: #eeeeee;" class="bold">总重:</td>
                        <td>${cargoData.fullWeightDes }</td>
                      </tr>
                      <tr>
                        <td style="background: #eeeeee;" class="bold">长:</td>
                        <td>${cargoData.ctlLengthDes }</td>
                        <td style="background: #eeeeee;" class="bold">宽:</td>
                        <td>${cargoData.ctlWidthDes }</td>
                      </tr>
                      <tr>
                        <td style="background: #eeeeee;" class="bold">高:</td>
                        <td>${cargoData.ctlHeightDes }</td>
                        <td style="background: #eeeeee;" class="bold">体积:</td>
                        <td>${cargoData.ctlVolumeDes }</td>
                      </tr> --%>

                      <tr>
                        <td style="background: #eeeeee;" class="bold">货名:</td>
                        <td>
					<c:if test="${cargoData.cargoType=='container20e'}">${cargoData.containerNames}</c:if>
					<c:if test="${cargoData.cargoType!='container20e'}">${cargoData.cargoTypeName}</c:if>
                        </td>
                        <td style="background: #eeeeee;" class="bold">货量:</td>
                        <td>
					<c:if test="${cargoData.cargoType=='container20e'}">${cargoData.containerTeus}</c:if>
					<c:if test="${cargoData.cargoType!='container20e'}">${cargoData.tonTeuDes}</c:if>
                        </td>
                      </tr>
                      <tr>
                        <td style="background: #eeeeee;" class="bold">运价:</td>
                        <td>
					<c:if test="${cargoData.cargoType=='container20e'}">${cargoData.containerPrices}</c:if>
					<c:if test="${cargoData.cargoType!='container20e'}">${cargoData.priceDes}</c:if>
                        </td>
                        <td style="background: #eeeeee;" class="bold">运费:</td>
                        <td>${cargoData.transFeeDes}</td>
                      </tr>
                      <tr>
                        <td style="background: #eeeeee;" class="bold">价格标语:</td>
                        <td colspan="3">${cargoData.remark }</td>
                      </tr>
                      <tr>
                        <td style="background: #eeeeee;" class="bold">收货人:</td>
                        <td>${cargoData.receiver }</td>
                        <td style="background: #eeeeee;" class="bold">收货人电话:</td>
                        <td>${cargoData.receMobile }</td>
                      </tr>
                      <tr>
                        <td style="background: #eeeeee;" class="bold">收货人地址:</td>
                        <td>${cargoData.receAddress }</td>
                        <td style="background: #eeeeee;" class="bold">发布日期:</td>
                        <td>${cargoData.createTime }</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <!--/box-->
              </div>
              <!--/span-->
            </div>
            <!--/breadcrumb-->
          </div>
          <!-- /content-breadcrumb -->
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
                      <c:if test="${!empty cargoData.agent}">
                        <c:forEach var="userData" items="${userDatas}">
	                      <li class="contact-alt grd-white">
                            <a href="${ctx}/space/chat/show?toUserId=${userData.id}" target="_blank">
                              <div class="contact-item">
                                <div class="pull-left">
                                  <img class="contact-item-object" style="width: 32px; height: 32px;"
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
	                        </a>
	                      </li>
	                    </c:forEach>
                      </c:if>
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
  <script src="${ctx}/js/portal/portal-cargoDetail.js"></script>
  <script type="text/javascript">
    var _rootPath = "${ctx}";
  </script>

</body>
</html>