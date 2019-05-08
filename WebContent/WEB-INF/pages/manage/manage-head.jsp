<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@
taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->

<div id="header">
  <h1>
    <a href="#;"></a>
  </h1>
</div>

<div id="search"></div>

<div id="user-nav" class="navbar navbar-inverse">
  <ul class="nav btn-group">
    <li class="btn btn-inverse"><a title=""
      href="${ctx}/manage/login/logout"> <i class="icon icon-share-alt"></i>
        <span class="text" style="font-size: 12px;">退出</span>
    </a></li>
  </ul>
</div>

<div id="sidebar">
  <c:if test="${!empty menuMap}">
  <ul>
    <c:forEach var="menu" items="${menuMap}">
    <c:if test="${menu.key.menuid==menuOpen.menuid}">
    <li class="submenu active open">
    </c:if>
    <c:if test="${menu.key.menuid!=menuOpen.menuid}">
    <li class="submenu">
    </c:if>
      <a href="#">
        <i class="icon icon-home"></i>
        <span>${menu.key.menuname}</span>
        <span class="label">${fn:length(menu.value)}</span>
      </a>
      <c:if test="${!empty menu.value}">
      <ul>
        <c:forEach var="submenu" items="${menu.value}">
        <c:if test="${submenu.menuid==menuAct.menuid}">
          <li class="active">
        </c:if>
        <c:if test="${submenu.menuid!=menuAct.menuid}">
          <li>
        </c:if>
          <a href="${ctx}${submenu.url}"> <i class="icon icon-home"></i>
            ${submenu.menuname}
          </a>
          </li>
        </c:forEach>
      </ul>
      </c:if>
    </li>
    </c:forEach>
  </ul>
  </c:if>
</div>
