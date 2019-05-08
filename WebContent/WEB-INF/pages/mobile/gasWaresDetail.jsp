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
@media (max-width: 767px)
body {
    padding-right: 0px;
    padding-left: 0px;
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

  <!-- section content -->
  <section class="section">
    <div class="row-fluid">
      <!-- span content -->
      <div class="span12">
        <!-- content -->
        <div class="content">

          <!-- content-breadcrumb -->
          <div class="content-breadcrumb content-body"
            style="padding-bottom: 0px">
            <!--breadcrumb-->
            <div class="row-fluid" style="background: #fff">
              <div class="span12">
                <div class="box center" style="margin-bottom:20px;">
                  <img src="${ctx}/download/imageDownload?url=${gasWaresData.waresLogo}"
                        alt="" class="thumbnail" style="width:100%" />
                </div>
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
 
  <script type="text/javascript">
    var _rootPath = "${ctx}";
  </script>

</body>
</html>