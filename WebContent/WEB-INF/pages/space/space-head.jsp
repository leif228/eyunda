<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->
<script>
$(document).ready(function() {
  $(".btnSetCurrCompId").live("click",function(){
    $.ajax({
      method : "GET",
      data : {compId : $(this).attr("compIdVal")},
      url : _rootPath+"/space/account/myAccount/setCurrCompany",
      datatype : "json",
      success : function(data) {
        var returnCode = $(data)[0].returnCode;
        var message = $(data)[0].message;
        if (returnCode == "Failure") {
          alert(message);
          return false;
        } else {
          window.location.reload();
          return true;
        }
      }
    });
  });
});
</script>
<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <a class="brand" href="./">会员空间</a>
      <div class="nav-collapse">

        <ul class="nav pull-right">
          <li class="dropdown">
            <a data-toggle="dropdown" class="dropdown-toggle " href="#">
              <c:if test="${!empty userData.currCompanyData}">
                ${userData.currCompanyData.compName} - ${userData.nickName}
              </c:if>
              <c:if test="${empty userData.currCompanyData}">
                ${userData.nickName}
              </c:if>
              <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
              <c:if test="${!empty userData.companyDatas}">
              <c:forEach items="${userData.companyDatas}" var="companyData">
              <li><a href="javascript:void(0);" class="btnSetCurrCompId" compIdVal="${companyData.id}">
                <i class="icon-home"></i>${companyData.compName}</a></li>
              </c:forEach>
              </c:if>
              <li class="divider"></li>
              <li><a href="${ctx}/portal/login/logout"> <i
                  class="icon-off"></i> 登出
              </a></li>
            </ul></li>
        </ul>

      </div>
      <!-- /nav-collapse -->
    </div>
    <!-- /container -->
  </div>
  <!-- /navbar-inner -->
</div>
<!-- /navbar -->

<div id="content">
  <div class="container">
    <div class="row">
      <div class="span2">

        <div class="account-container">
          <div class="account-avatar">
            <c:if test="${!empty userData.userLogo}">
              <img src="${ctx}/download/imageDownload?url=${userData.userLogo}"
                alt="${userData.loginName}" class="thumbnail" />
            </c:if>
            <c:if test="${empty userData.userLogo}">
              <img src="${ctx}/img/user.jpg" alt="${userData.loginName}"
                class="thumbnail" />
            </c:if>
          </div>
          <div class="account-details">
            <span class="account-name">${userData.loginName}</span>
            <span class="account-name">${userData.trueName}</span>
            <span class="account-role"></span>
          </div>
        </div>

        <hr />

        <ul id="main-nav" class="nav nav-tabs nav-stacked">

          <c:forEach var="menu" items="${menus}">
            <c:if test="${menu.menuid==menuAct.menuid}">
              <li class="active"><a href="${ctx}${menu.url}"> <i
                  class="${menu.icon}"></i> ${menu.menuname}
              </a></li>
            </c:if>
            <c:if test="${menu.menuid!=menuAct.menuid}">
              <li><a href="${ctx}${menu.url}"> <i class="${menu.icon}"></i>
                  ${menu.menuname}
              </a></li>
            </c:if>
          </c:forEach>

        </ul>

        <hr />

      </div>
      <!-- /span3 -->