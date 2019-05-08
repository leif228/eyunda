<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<header id="masthead" class="site-header">
	<nav id="top-header">
		<div class="top-nav">
			<div id="user-profile">欢迎光临 !
			  <span class="nav-set"> <span class="nav-login">
				<c:if test="${empty userData}">
				  <a href="${ctx}/portal/login/login" class="flatbtn">请登录</a>
				  <a href="${ctx}/portal/login/register" class="flatbtn">注册</a>
				</c:if>
				<c:if test="${!empty userData}">
				  <a title="会员空间" href="${ctx}/space/account/myAccount"
				   target="_blank" class="flatbtn" id="login-main">${userData.trueName}</a> ｜ <a 
				   href="${ctx}/portal/login/logout" class="flatbtn" id="login-main">登出</a>
				</c:if>				
				</span>
		      </span>
			</div>
			<div style="float:right;">
			  <span class="nav-login"> 客服电话：020-62321245</span>&nbsp;&nbsp;
			  <span><a href="${ctx}/portal/home/noticeInfo?noticeId=74155545">商家入驻</a></span>&nbsp;&nbsp;
			  <span class="dropdown">
                <a class="dropdown-toggle" id="dropHelp" role="button" data-toggle="dropdown" href="#">操作指南<b class="caret"></b></a>
                <ul id="menu2" class="dropdown-menu" role="menu" aria-labelledby="dropHelp">
                  <li role="presentation"><a role="menuitem" tabindex="1" href="${ctx}/portal/home/noticeInfo?noticeId=62559401">注册登录</a></li>
                  <li role="presentation"><a role="menuitem" tabindex="2" href="${ctx}/portal/home/noticeInfo?noticeId=63298378">船舶监控</a></li>
                  <li role="presentation"><a role="menuitem" tabindex="3" href="${ctx}/portal/home/noticeInfo?noticeId=63978737">在线签约</a></li>
                  <li role="presentation"><a role="menuitem" tabindex="4" href="${ctx}/portal/home/noticeInfo?noticeId=72782524">在线支付</a></li>
                </ul>
              </span>
			</div>
		</div>
	</nav>
	<div id="menu-box" class="">
		<div id="top-menu">
			<!-- <span class="nav-search"><i class="fa fa-search"></i></span>  -->
			<div>
				<hgroup class="logo-site">
					<h1 class="site-title">
						<a href="javascript:void(0)"><img src="${ctx}/img/logo-new.png"
							title="易运达货运电商平台" rel="home"><span class="site-name">易运达</span></a>
					</h1>
				</hgroup>

			<nav id="site-nav" class="main-nav" style="display:none">
					<a href="#sidr-main" id="navigation-toggle" class="bars"><i
						class="fa fa-bars"></i></a>
					<div class="">
						<ul id="" class="down-menu nav-menu">
							<li class="menu-item"><a href="${ctx }/portal/home/cargoListx/?waresType=nmjzx&cargoType=container20e" style="<c:if test='${waresType eq "nmjzx"}'>color:red;font-weight:bold</c:if>" ><i
									class="fa" style="color:#000;font-weight:bold;font-size:16px;">集装箱：</i><i class="fa-credit-card fa"></i>内贸集装箱</a></li>
							<li class="menu-item"><a href="${ctx }/portal/home/cargoListx/?waresType=gaxjzx&cargoType=container20e" style="<c:if test='${waresType eq "gaxjzx" }'>color:red;font-weight:bold</c:if>" ><i class="fa-dashboard fa"></i>港澳集装箱</a></li>
							<li class="menu-item"><a href="${ctx }/portal/home/cargoListx/?waresType=nmszh&cargoType=coal" style="<c:if test='${waresType eq "nmszh"  && cargoType == "coal"}'>color:red;font-weight:bold</c:if>" ><i
									class="fa" style="color:#000;font-weight:bold;font-size:16px;">散杂货：</i><i class="fa-credit-card fa"></i>煤炭</a></li>
							<li class="menu-item"><a href="${ctx }/portal/home/cargoListx/?waresType=nmszh&cargoType=steel"style="<c:if test='${waresType eq "nmszh"  && cargoType == "steel"}'>color:red;font-weight:bold</c:if>" ><i
									class="fa-camera fa"></i>钢铁</a></li>
							<li class="menu-item"><a href="${ctx }/portal/home/cargoListx/?waresType=nmszh&cargoType=tile" style="<c:if test='${waresType eq "nmszh"  && cargoType == "tile"}'>color:red;font-weight:bold</c:if>" ><i
									class="fa-dashboard fa"></i>建材</a></li>
							<li class="menu-item"><a href="${ctx }/portal/home/cargoListx/?waresType=nmszh&cargoType=cement" style="<c:if test='${waresType eq "nmszh"  && cargoType == "cement"}'>color:red;font-weight:bold</c:if>" ><i
									class="fa-camera fa"></i>水泥</a></li>
						</ul>
					</div>
				</nav>
			</div>
		</div>
	</div>
</header>
