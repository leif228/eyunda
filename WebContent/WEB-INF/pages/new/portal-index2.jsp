<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>易运达 - 首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="guoqiang" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/common2.css" rel="stylesheet" />
<link href="${ctx}/css/new/portal-index2.css" rel="stylesheet" />


<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>

<script type="text/javascript">
	var _rootPath = "${ctx}";
	
</script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->
<style>
#scrollDivCargo .btn,#scrollDivCabin .btn{
	background-image: linear-gradient(to bottom, #e6e6e6, #e6e6e6);
}
</style>

</head>

<body>
	<!-- section header -->
	<jsp:include page="portal-head-index2.jsp"></jsp:include>

	<nav class="breadcrumb" style="height: 16px"></nav>
	<!-- section content -->
	<div id="content" class="site-content">
		<div class="clear"></div>
		<!-- content-area -->
		<div id="primary" class="content-area" style="margin-left: 10px;margin-top: 10px;height: 50%">
			<!-- ship and cargo start -->
			<div class="line-one">
				<div class="row-fluid">
					<div class="span12 cat-box">
						<!-- <h3 class="cat-title">
							<a href="" title=""><i
								class="fa fa-bars"></i></a><span class="more"><a
								href="" target="_blank"></a></span>
						</h3> -->
						<div class="clear"></div>
						<div class="cat-site" id="scrollDivCabin" style="margin:0px;">      
						 	 <div class="ibox float-e-margins">
                   
                    <div class="ibox-content">
                        <div class="carousel slide" id="carousel1">
                            <div class="carousel-inner">
                                <div class="item active">
                                    <img alt="image" class="img-responsive" src="${ctx}/hyqback/img/tu1.png">
                                </div>
                                <div class="item">
                                    <img alt="image" class="img-responsive" src="${ctx}/hyqback/img/tu2.png">
                                </div>
                                <%-- <div class="item ">
                                    <img alt="image" class="img-responsive" src="${ctx}/hyqback/img/tu2.png">
                                </div> --%>

                            </div>
                            <a data-slide="prev" href="carousel.html#carousel1" class="left carousel-control">
                                <span class="icon-prev"></span>
                            </a>
                            <a data-slide="next" href="carousel.html#carousel1" class="right carousel-control">
                                <span class="icon-next"></span>
                            </a>
                        </div>
                    </div>
                </div>
			              </div>
			              <div class="clear"></div>
						</div>
					</div>
				</div>
				</div>
		</div>
		<!-- content end -->

		<!-- widget-area -->
		<div id="sidebar" class="widget-area">
			<jsp:include page="./notice2.jsp"></jsp:include>
			<jsp:include page="./customerDownload.jsp"></jsp:include>

		</div>
		<div class="clear"></div>
	</div>
	<!-- section footer -->
	<jsp:include page="portal-foot.jsp"></jsp:include>
	<!-- javascript
    ================================================== -->
	<script src="${ctx}/js/jquery.divSelect.js"></script>
	<script src="${ctx}/js/jquery.textSlider.js"></script>
	<script src="${ctx}/js/new/portal-index.js"></script>
	
</body>
</html>