<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>前台浏览 - 商品详情</title>
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
              <i class="icofont-home"></i>商品：${gasWaresData.waresName}
            </h3>
          </div>
          <!-- /content-header -->
          <!-- content-breadcrumb -->
          <div class="content-breadcrumb content-body"
            style="padding-bottom: 0px">
            <!--breadcrumb-->
            <div class="row-fluid" style="background: #fff">
              <!--left-->
              <div class="span4">
                <div class="center">
                  <img src="${ctx}/download/imageDownload?url=${gasWaresData.waresLogo}"
                        alt="" class="thumbnail" style="width: 300px; height: 200px;" />
                  <a class="btn btn-info " data-src="${gasWaresData.id}" href="javascript:btnBuy(${gasWaresData.id})">
                    <i class="icon-inbox icon-white"></i>购买
                  </a>
                </div>
              </div>
              <!--/left-->

              <!--span-->
              <div class="span8">
                <!--box-->
                <div class="box corner-all" style="margin-bottom: 0px">
                  <!--box header-->
                  <div class="box-header grd-white color-silver-dark corner-top">
                    <span>商品信息</span>
                  </div>

                  <!--box body-->
                  <div class="box-body">
                    <table class="table table-bordered data-table">
                      <tbody>
                        <tr>
                          <td style="background: #eeeeee;" class="bold">商品号:</td>
                          <td>${gasWaresData.id}</td>
                          <td style="background: #eeeeee;" class="bold">商品所属公司:</td>
                          <td>
                            <a class="link" href="${ctx}/portal/home/gasWaresList?companyName=${gasWaresData.companyData.companyName}">
                              ${gasWaresData.companyData.companyName}
                            </a>
                          </td>
                        </tr>
                        
                        <tr>
                          <td style="background: #eeeeee;" class="bold">商品名称:</td>
                          <td>${gasWaresData.waresName}</td>
                          <td style="background: #eeeeee;" class="bold">商品描述:</td>
                          <td>${gasWaresData.subTitle}</td>
                        </tr>
						
                        <tr>
                          <td style="background: #eeeeee;" class="bold">销售价格:</td>
                          <td>${gasWaresData.price}</td>
                          <td style="background: #eeeeee;" class="bold">市场价格:</td>
                          <td>${gasWaresData.stdPrice}</td>
                        </tr>
                        <tr>
                          <td style="background: #eeeeee;" class="bold">价格标语:</td>
                          <td colspan="3">${gasWaresData.priceSignal}</td>
                        </tr>
                        
                        <tr>
                          <td rowspan="3" style="background: #eeeeee;" class="bold">商品详情:</td>
                          <td rowspan="3" colspan="3">${gasWaresData.description}</td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
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
                        <li class="contact-alt grd-white"><a
                          href="${ctx}/space/chat/show?toUserId=${cargoData.agent.id}"
                          target="_blank">
                            <div class="contact-item">
                              <div class="pull-left">
                                <img class="contact-item-object"
                                  style="width: 32px; height: 32px;"
                                  src="${ctx}/download/imageDownload?url=${cargoData.agent.userLogo}" />
                              </div>
                              <div class="contact-item-body">
                                <div class="status" title="ofline">
                                  <i class="icofont-certificate color-silver-dark"></i>
                                </div>
                                <p class="contact-item-heading bold">${cargoData.agent.trueName} ${cargoData.agent.mobile}</p>
                                <p class="contact-item-heading">${cargoData.agent.unitName}</p>
                              </div>
                            </div>
                        </a></li>
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
  <script src="${ctx}/js/portal/portal-user-detail.js"></script>
  <script src="${ctx}/js/portal/portal-cargoDetail.js"></script>
  <script src="${ctx}/js/portal/portal-gasorder-buy.js"></script>
  <script type="text/javascript">
    var _rootPath = "${ctx}";
  </script>
  
   <!-- Modal showDialog -->
	<div id="btninDialog" class="modal hide fade">
		<form class="form-horizontal" name="btninDialogForm" role="form"
			id="btninDialogForm" novalidate="novalidate" method="post"
			action="${ctx}/portal/home/gasWaresBuy">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>购买页面</h3>
			</div>
			<input type="hidden" id="waresId" name="waresId" value="" />
			<div class="modal-body">
				<div class="control-group">
					<label class="control-label">商品号:</label>
					<div class="controls waresId"></div>
				</div>
				<div class="control-group">
					<label class="control-label">商品名称:</label>
					<div class="controls waresName"></div>
				</div>
				<div class="control-group">
					<label class="control-label">销售价格(元):</label>
					<div class="controls price"></div>
				</div>
				<div class="control-group">
					<label class="control-label">购买数量:</label>
					<div class="controls">
						<input type="text" id="saleCount" name="saleCount" value="1" style="width: 150px;ime-mode:disabled;" onkeyup="return ValidateNumber(this,value)"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">交易金额(元):</label>
					<div class="controls">
						<input type="text" id="tradeMoney" name="tradeMoney" value="" style="width: 150px;" readonly />
					</div>
				</div>

			<%-- 	<div class="control-group">
					<label class="control-label">加油船舶：</label>
					<div class="controls">
						<div class="selShip">
							<select id="ship" name="shipId" style="width: 160px;">
								<c:forEach var="s" items="${combos}">
									<option value="${combo}">${combo.description}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div> --%>

				<div class="modal-footer">
					<button class="btn btn-primary btn_surein">
						<i class="icon-arrow-up icon-white"></i> 下单
					</button>
					<button class="btn" data-dismiss="modal">
						<i class="icon icon-off"></i> 关闭
					</button>
				</div>
			</div>
		</form>
	</div>

</body>
</html>