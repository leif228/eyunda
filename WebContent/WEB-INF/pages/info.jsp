<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>船运圈 - 提示信息</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="${ctx}/hyqback/favicon.ico"> <link href="${ctx}/hyqback/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx}/hyqback/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="${ctx}/hyqback/css/animate.css" rel="stylesheet">
    <link href="${ctx}/hyqback/css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">


    <div class="middle-box text-center animated fadeInDown">
        <h1>提示</h1>
        <h3 class="font-bold">船运圈 - 提示信息</h3>

        <div class="error-desc">
            ${info}
            <br/><a href="javascript:window.androidJava.finishActivity();" class="btn btn-primary m-t">返回</a>
        </div>
    </div>

    <!-- 全局js -->
    <script src="${ctx}/hyqback/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ctx}/hyqback/js/bootstrap.min.js?v=3.3.6"></script>

</body>

</html>
