<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> - 空白页</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico"> <link href="${ctx}/hyqback/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx}/hyqback/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="${ctx}/hyqback/css/animate.css" rel="stylesheet">
    <link href="${ctx}/hyqback/css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
    <!-- <div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-sm-4">
            <h2>标题</h2>
            <ol class="breadcrumb">
                <li>
                    <a href="index.html">主页</a>
                </li>
                <li>
                    <strong>包屑导航</strong>
                </li>
            </ol>
        </div>
        <div class="col-sm-8">
            <div class="title-action">
                <a href="empty_page.html" class="btn btn-primary">活动区域</a>
            </div>
        </div>
    </div> -->
	
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-12">
                <div class="middle-box text-center animated fadeInRightBig">
                    <h3 class="font-bold">欢迎进入</h3>

                    <div class="error-desc">
                        货运电商平台是针对公路运输及航道运输业务，以移动互联网技术为手段，通过手机或电脑上网，线上进行船舶租赁、交易支付、状态跟踪、信息互动，线下进行装卸运输的O2O电子商务运营平台。
                        <!--  <br/><a href="#" class="btn btn-primary m-t">打开主页</a>-->
                    </div>
                </div>
            </div>
        </div>
    </div>
    
   <%-- <jsp:include page="./foot.jsp"></jsp:include> --%>

    <!-- 全局js -->
    <script src="${ctx}/hyqback/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ctx}/hyqback/js/bootstrap.min.js?v=3.3.6"></script>

    <!-- 自定义js -->
    <script src="${ctx}/hyqback/js/content.js?v=1.0.0"></script>

    
    

</body>

</html>
