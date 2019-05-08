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

          <!-- content-body -->
          <div class="content-body">
            <!--row-fluid-->
            <div class="row-fluid">
              <!--span-->
              <div class="span12">
				<form name="pageform" id="pageform" action="" method="get">
                <div class="box corner-all">
                  <div class="box-header grd-white color-silver-dark corner-top">
                    <div style="font-size: 16px;">公告</div>
                    <div style="margin-top: -25px; margin-left: 80px; height: 25px;">
                    
                    </div>
                  </div>
                  <div class="box-body" style="padding: 0px;">
                    <table class="table">

                      <tbody>
                        <c:forEach var="notice" items="${notices.result}">
		                  <tr>
		                    <td>
		                    	<a href="${ctx}/portal/home/noticeDetail?noticeId=${notice.id}" target="_blank">
									<div class="row-fluid">
										<div class="col-xs-6 span2" align="center">
										<c:choose>
				                          <c:when test="${!empty notice.source}">
				                            <img src="${ctx}/download/imageDownload?url=${notice.source}"
				                      		 alt="" class="thumbnail" />
				                          </c:when>
				                          <c:otherwise>
				                            <img src="${ctx}/img/notice/notice.png"
				                              alt="" class="thumbnail" />
				                          </c:otherwise>
				                        </c:choose>
										</div>
										<div class="col-xs-6 span10">
												<div class="row-fluid">
													<div class="col-xs-6 span10">
													<h3>
														${notice.title}
													</h3>
													</div>
													<div class="col-xs-6 span2">
														<h5>
															${notice.publishTime}
														</h5>
													</div>
												</div>
												${notice.content}
										</div>
									</div>
		                      	</a>
		                    </td>
		                  </tr>
		                </c:forEach>
                      </tbody>
                    </table>

                  </div>
                  <jsp:include page="./pagerHomeNotice.jsp"></jsp:include>
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