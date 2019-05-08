<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style>
#site-nav .btn{
	background-image: linear-gradient(to bottom, #e6e6e6, #e6e6e6);
}
	.logo{width: 50px;height: 80px;}
</style>
<header id="masthead" class="site-header">
	<div >
		
				</div>
	<nav id="top-header">
		<div style="width: 100%;height: 60px;background-color:#0B5EA5;">
		<hgroup cpitch: itch: ass="logo-site" style="display: inline;position: absolute;">
					<div class="site-title" style="width: 220px;margin-left: 180px;">
						<a href="javascript:void(0)">
						<img src="${ctx}/img/eyq/hyqlogo2.png"
					title="易运达货运电商平台" class="logo" rel="home" style="height: 60px;width: 1800px;padding-top: 5px;">
							</a>
					</div>
				</hgroup>
				
		<div class="top-nav" style="position: relative;">
			<div id="user-profile" style="color: white;float: right;">欢迎光临 !
			  <span class="nav-set"> <span class="nav-login">
				<c:if test="${empty userData}">
				  <a href="${ctx}/hyquan/login/login" class="flatbtn" style="color: white;">请登录</a>
				  <a href="${ctx}/hyquan/login/register" class="flatbtn" style="color: white;">注册</a>
				</c:if>
				<c:if test="${!empty userData}">
				  <a title="会员空间" href="${ctx}/space/account/myAccount"
				   target="_blank" class="flatbtn" id="login-main">${userData.trueName}</a> ｜ <a 
				   href="${ctx}/portal/login/logout" class="flatbtn" id="login-main" style="color: white;">登出</a>
				</c:if>				
				</span>
			  &nbsp;&nbsp;<span class="nav-login" style="color: white;"> 客服电话：020-62321245</span>&nbsp;&nbsp;
		      </span>
			</div>
			<div style="float:right;">
			 <%--  <span><a href="${ctx}/portal/home/noticeInfo?noticeId=74155545">商家入驻</a></span>&nbsp;&nbsp;
			  <span class="dropdown">
                <a class="dropdown-toggle" id="dropHelp" role="button" data-toggle="dropdown" href="#">操作指南<b class="caret"></b></a>
                <ul id="menu2" class="dropdown-menu" role="menu" aria-labelledby="dropHelp">
                  <li role="presentation"><a role="menuitem" tabindex="1" href="${ctx}/portal/home/noticeInfo?noticeId=62559401">注册登录</a></li>
                  <li role="presentation"><a role="menuitem" tabindex="2" href="${ctx}/portal/home/noticeInfo?noticeId=63298378">船舶监控</a></li>
                  <li role="presentation"><a role="menuitem" tabindex="3" href="${ctx}/portal/home/noticeInfo?noticeId=63978737">在线签约</a></li>
                  <li role="presentation"><a role="menuitem" tabindex="4" href="${ctx}/portal/home/noticeInfo?noticeId=72782524">在线支付</a></li>
                </ul>
              </span> --%>
			</div>
		</div>
		</div>
	</nav>
	<div id="menu-box" class="">
		<div style="margin-left: 180px;margin-top: ">
			<img alt="" src="${ctx}/img/eyq/buttom.png" style="width: 110px;height: 18px;">
		</div>
		<div id="top-menu">
			<!-- <span class="nav-search"><i class="fa fa-search"></i></span>  -->
			<div>
				
			</div>
		</div>
	</div>
</header>
