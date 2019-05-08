<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>前台浏览 - 代理人详情</title>
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

.gallery-category ul li {
    font-size: 1em;
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
              <i class="icofont-home"></i>
              <c:choose>
                <c:when test="${!empty operatorData.userData.trueName}">${operatorData.userData.trueName}</c:when>
                <c:otherwise>${operatorData.userData.nickName}</c:otherwise>
              </c:choose>
              <span class="user-glory">金牌代理人</span>
            </h3>
          </div>
          <!-- /content-header -->
          <!-- content-breadcrumb -->
          <div class="content-breadcrumb">
            <!--breadcrumb-->
            <div class="row-fluid  userInfo">
              <div class="span12">
                <!-- userLogo -->
                <div class="fl userLogo" style="margin-left: 20px;">
                  <c:if
                    test="${!empty operatorData.userData && !empty operatorData.userData.userLogo}">
                    <img style="width: 80px; height: 60px;"
                      src="${ctx}/download/imageDownload?url=${operatorData.userData.userLogo}" />
                  </c:if>
                  <c:if
                    test="${empty operatorData.userData || empty operatorData.userData.userLogo}">
                    <img style="width: 80px; height: 60px;"
                      src="${ctx}/img/user.jpg" />
                  </c:if>
                </div>
                <!-- userInfo -->
                <div class="fl" style="padding-left: 10px; width: 800px">
                  <p style="margin: 0px">电话：${operatorData.userData.mobile}</p>
                  <p style="margin: 0px">email：${operatorData.userData.email}</p>
                  <p style="margin: 0px">公司名称:${operatorData.userData.unitName}&nbsp;注册时间：${operatorData.userData.createTime}</p>
                </div>

              </div>
            </div>
            <!--/breadcrumb-->
          </div>
          <!-- /content-breadcrumb -->

          <!-- content-body -->
          <div class="content-body">
	          <form action="${ctx}/portal/home/operDetail" id="pageform" method="post">
	          	<input type="hidden" name="id" id="operId" value="${operatorData.userId}" />
	            <!--row-fluid-->
	            <div class="row-fluid">
	              <!--span-->
	              <div class="span12">
	                <!--box-->
	                <div class="box corner-all">
	                  <!--box header-->
	                  <div class="box-header grd-white color-silver-dark corner-top">
	                    <span>代理船舶列表</span>
	                  </div>
	
	                  <!--box body-->
	                  <div class="box-body" style="padding: 0px;">
	                    <table class="table table-bordered data-table"
							style="word-break: break-all;">
							<thead>
								<tr>
									<th style="text-align: center; width: 10%">装货港</th>
									<th style="text-align: center; width: 10%">卸货港</th>
									<th style="text-align: center; width: 10%">船舶图片</th>
									<th style="text-align: left; width: 8%">承运人</th>
									<th style="text-align: left; width: 8%">MMSI编号</th>
									<th style="text-align: right; width: 8%">总吨</th>
									<th style="text-align: center; width: 8%">船类</th>
									<th style="text-align: center; width: 8%">船名</th>
									<th style="text-align: center; width: 18%">船舶动态</th>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${pageData.result}" var="shipData">
									<tr class="gradeX">
										<c:choose>
											<c:when test="${empty shipData.shipArvlftDatas[0] || empty shipData.shipArvlftDatas[0].arvlft}">
				                                <td></td>
												<td></td>
				                            </c:when>
				                            <c:when test="${! empty shipData.shipArvlftDatas[0] && shipData.shipArvlftDatas[0].arvlft == 'arrive' }">
				                                <td>${shipData.shipArvlftDatas[0].goPortData.fullName}</td>
												<td>${shipData.shipArvlftDatas[0].portData.fullName}</td>
				                            </c:when>
				                            <c:when test="${! empty shipData.shipArvlftDatas[0] && shipData.shipArvlftDatas[0].arvlft == 'left' }">
				                               <td>${shipData.shipArvlftDatas[0].portData.fullName}</td>
											   <td>${shipData.shipArvlftDatas[0].goPortData.fullName}</td>
				                            </c:when>
										</c:choose>
										<td>
											<a href="${ctx}/portal/home/shipDetail?shipId=${shipData.id}" target="_blank"> 
			                                  <c:choose>
						                          <c:when test="${!empty shipData.shipLogo}">
						                            <img style="width: 80px; height: 60px;"  src="${ctx}/download/imageDownload?url=${shipData.shipLogo}"  class="thumbnail" />
						                          </c:when>
						                          <c:otherwise>
						                          	<img style="width: 80px; height: 60px;"  src="${ctx}/img/shipImage/${shipData.shipType}.jpg"  class="thumbnail" />
						                          </c:otherwise>
						                      </c:choose>
			                                 </a>
										</td>
										<c:choose>
							            	<c:when test="${!empty shipData.broker.trueName}"><td>${shipData.broker.trueName}</td></c:when>
							            	<c:otherwise><td>${shipData.broker.loginName}</td></c:otherwise>
							            </c:choose>
										<td>${shipData.mmsi}</td>
										<td>${shipData.sumTons}吨</td>
										<td>${shipData.typeData.typeName}</td>
										<td>${shipData.shipName}</td>
										<td>${shipData.shipArvlftDatas[0].arvlftDesc}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
	                    
	                  </div>
	
	                  <!--box footer-->
	                  <div class="gallery-controls bottom">
	                    <div class="gallery-category">
	                      <jsp:include page="./pager.jsp"></jsp:include>
	                    </div>
	                  </div>
	                </div>
	                <!--/box-->
	              </div>
	              <!--/span-->
	            </div>
	            <!--/row-fluid-->
	
	            <!--row-fluid-->
	            <div class="row-fluid">
	              <!--span-->
	              <div class="span12">
	                <!--box-->
	                <div class="box corner-all">
	                  <!--box header-->
	                  <div class="box-header grd-white color-silver-dark corner-top">
	                    <span>代理货物列表</span>
	                  </div>
	
	                  <!--box body-->
	                  <div class="box-body" style="padding: 0px;">
	                    <table class="table table-bordered data-table table-striped"
	                      style="word-break: break-all;">
	                      <thead>
	                        <tr>
			                  <th style="text-align: center; width: 8%">货物图片</th>
			                  <th style="text-align: center; width: 5%">货号</th>
			                  <th style="text-align: center; width: 12%">装货港</th>
			                  <th style="text-align: center; width: 12%">卸货港</th>
			                  <th style="text-align: center; width: 8%">托运人</th>
			                  <th style="text-align: center; width: 10%">货名</th>
			                  <th style="text-align: center; width: 8%">货量</th>
			                  <th style="text-align: center; width: 10%">运价</th>
			                  <th style="text-align: center; width: 10%">运费</th>
			                  <th style="text-align: center; width: 12%">发布日期</th>
	                        </tr>
	                      </thead>
	                      <tbody>
	                        <c:forEach items="${pageCargoData.result}" var="cargoData">
	                          <tr>
		                        <td style="text-align: center">
	                        	  <a href="${ctx}/portal/home/cargoDetail?id=${cargoData.id}" target="_blank"> 
	                        	    <c:choose>
	                                  <c:when test="${!empty cargoData.cargoImage}">
	                                    <img src="${ctx}/download/imageDownload?url=${cargoData.cargoImage}"
	                                      style="width: 80px; height: 60px;" alt="" class="thumbnail" title="点击查看详情"/>
	                                  </c:when>
	                                  <c:otherwise>
	                                    <img src="${ctx}/img/cargoImage/${cargoData.cargoType}.jpg"
	                                      style="width: 80px; height: 60px;" alt=""class="thumbnail" title="点击查看详情"/>
	                                  </c:otherwise>
	                                </c:choose>
	                        	  </a>
	                        	</td>
		                        <td>${cargoData.id}</td>
		                    	<td>${cargoData.startPortData.fullName}</td>
		                    	<td>${cargoData.endPortData.fullName}</td>
		                    	<td>
	           	                  <c:if test="${!empty cargoData.agent}">
	           	                    ${cargoData.agent.trueName}
					              </c:if>
				                  <c:if test="${empty cargoData.agent}">
				                    ${cargoData.owner.trueName}
				                  </c:if>
		                        </td>
			                    <td>
			                      <c:if test="${cargoData.cargoType=='container20e'}">${cargoData.containerNames}</c:if>
			                      <c:if test="${cargoData.cargoType!='container20e'}">${cargoData.cargoTypeName}</c:if>
			                    </td>
			                    <td style="text-align: right">
			                      <c:if test="${cargoData.cargoType=='container20e'}">${cargoData.containerTeus}</c:if>
			                      <c:if test="${cargoData.cargoType!='container20e'}">${cargoData.tonTeuDes}</c:if>
			                    </td>
			                    <td style="text-align: right">
			                      <c:if test="${cargoData.cargoType=='container20e'}">${cargoData.containerPrices}</c:if>
			                      <c:if test="${cargoData.cargoType!='container20e'}">${cargoData.priceDes}</c:if>
			                    </td>
			                    <td style="text-align: right">${cargoData.transFeeDes}</td>
			                    <td style="text-align: center">${cargoData.createTime}</td>
	                          </tr>
	                        </c:forEach>
	                      </tbody>
	                    </table>
	                  </div>
	                  <!--box footer-->
	
	                  <div class="gallery-controls bottom">
	                    <div class="gallery-category">
	                      <jsp:include page="./pager-cargo.jsp"></jsp:include>
	                    </div>
	                  </div>
	                </div>
	                <!--/box-->
	              </div>
	              <!--/span-->
	            </div>
	            <!--/row-fluid-->
	          </form>
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
                      <li class="contact-alt grd-white"><a
                        href="${ctx}/space/chat/show?toUserId=${operatorData.userId}"
                        target="mychat">
                          <div class="contact-item">
                            <div class="pull-left">
                              <img class="contact-item-object"
                                style="width: 32px; height: 32px;"
                                src="${ctx}/download/imageDownload?url=${operatorData.userData.userLogo}" />
                            </div>
                            <div class="contact-item-body">
                              <div class="status" title="ofline">
                                <i class="icofont-certificate color-silver-dark"></i>
                              </div>
                              <p class="contact-item-heading bold">${operatorData.userData.trueName} ${operatorData.userData.mobile}</p>
                              <p class="contact-item-heading">${operatorData.userData.unitName}</p>
                            </div>
                          </div>
                      </a></li>
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
  <script src="${ctx}/js/portal/portal-user-detail.js"></script>
  <script src="${ctx}/js/portal/portal-operDetail.js"></script>
  <script type="text/javascript">
    var _rootPath = "${ctx}";
  </script>
</body>
</html>