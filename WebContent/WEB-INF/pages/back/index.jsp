<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@
taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title> hAdmin- 主页</title>

    <meta name="keywords" content="">
    <meta name="description" content="">

    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->

    <link rel="shortcut icon" href="favicon.ico"> <link href="${ctx}/hyqback/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx}/hyqback/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="${ctx}/hyqback/css/animate.css" rel="stylesheet">
    <link href="${ctx}/hyqback/css/style.css?v=4.1.0" rel="stylesheet">
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                                    <span class="block m-t-xs" style="font-size:20px;">
                                        <i class="fa fa-area-chart"></i>
                                        <strong class="font-bold">hAdmin</strong>
                                    </span>
                                </span>
                            </a>
                        </div>
                        <div class="logo-element">hAdmin
                        </div>
                    </li>
                    <li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
                        <span class="ng-scope">分类</span>
                    </li>
                    <li>
                        <a class="J_menuItem" href="welcomeEyd">
                            <i class="fa fa-home"></i>
                            <span class="nav-label">主页</span>
                        </a>
                    </li>
                    <li class="line dk"></li>
                    <c:if test="${!empty menuMap}">
                    <c:forEach var="menu" items="${menuMap}">
                    <c:if test="${menu.key.menuid==backMenuOpen.menuid}">
    				<li class="active">
    				</c:if>
    				<c:if test="${menu.key.menuid!=backMenuOpen.menuid}">
    				<li>
    				</c:if>
                        <a href="#">
                            <i class="fa fa fa-bar-chart-o"></i>
                            <span class="nav-label">${menu.key.menuname}</span>
                            <span class="fa arrow">${fn:length(menu.value)}</span>
                        </a>
                        <c:if test="${!empty menu.value}">
                        <ul class="nav nav-second-level">
                        	<c:forEach var="submenu" items="${menu.value}">
                        	<c:if test="${submenu.menuid==backMenuAct.menuid}">
          					<li class="active">
        					</c:if>
                            <c:if test="${submenu.menuid!=backMenuAct.menuid}">
          						<li>
        					</c:if>
                                <a class="J_menuItem" href="${ctx}${submenu.url}">${submenu.menuname}</a>
                            </li>
                            </c:forEach>
                        </ul>
                        </c:if>
                    </li>
                    <li class="line dk"></li>
                        </c:forEach>
                </c:if>
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-info " href="#"><i class="fa fa-bars"></i> </a>
                        <!-- <form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                            <div class="form-group">
                                <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">
                            </div>
                        </form> -->
                    </div>
                    <ul class="nav navbar-top-links navbar-right">
                    <a title="" href="${ctx}/back/login/logout">
                    <button type="button" class="btn btn-primary btn-sm">退出</button>
                    </a>
                        <li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <i class="fa fa-envelope"></i> <!-- <span class="label label-warning">16</span> -->
                            </a>
                            <ul class="dropdown-menu dropdown-messages">
                                <!-- <li class="m-t-xs">
                                    <div class="dropdown-messages-box">
                                        <a href="profile.html" class="pull-left">
                                            <img alt="image" class="img-circle" src="img/a7.jpg">
                                        </a>
                                        <div class="media-body">
                                            <small class="pull-right">46小时前</small>
                                            <strong>小四</strong> 是不是只有我死了,你们才不骂爵迹
                                            <br>
                                            <small class="text-muted">3天前 2014.11.8</small>
                                        </div>
                                    </div>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="dropdown-messages-box">
                                        <a href="profile.html" class="pull-left">
                                            <img alt="image" class="img-circle" src="img/a4.jpg">
                                        </a>
                                        <div class="media-body ">
                                            <small class="pull-right text-navy">25小时前</small>
                                            <strong>二愣子</strong> 呵呵
                                            <br>
                                            <small class="text-muted">昨天</small>
                                        </div>
                                    </div>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <div class="text-center link-block">
                                        <a class="J_menuItem" href="mailbox.html">
                                            <i class="fa fa-envelope"></i> <strong> 查看所有消息</strong>
                                        </a>
                                    </div>
                                </li> -->
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe id="J_iframe" width="100%" height="100%" src="${ctx}/back/login/welcomeEyd" frameborder="0" data-id="index_v1.html" seamless></iframe>
            </div>
        </div>
        <!--右侧部分结束-->
    </div>

    <!-- 全局js -->
    <script src="${ctx}/hyqback/js/jquery.min.js?v=2.1.4"></script>
    <script src="${ctx}/hyqback/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="${ctx}/hyqback/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/hyqback/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${ctx}/hyqback/js/plugins/layer/layer.min.js"></script>

    <!-- 自定义js -->
    <script src="${ctx}/hyqback/js/hAdmin.js?v=4.1.0"></script>
    <script type="text/javascript" src="${ctx}/hyqback/js/index.js"></script>

    <!-- 第三方插件 -->
    <script src="${ctx}/hyqback/js/plugins/pace/pace.min.js"></script>

</body>

</html>
