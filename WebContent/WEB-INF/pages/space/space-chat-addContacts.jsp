<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>易运达 - 首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta name="description" content="" />
<meta name="author" content="guoqiang" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/common.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/css/page-tabs.css" />

<style>
.tabs nav {
  text-align: center;
}
#content{
	padding-top: 10px;
	border-top-left-radius:0px;
}

.hiding{
	display:none;
}

</style>

<script type="text/javascript">
$(document).ready(function(){
	$("#tab-info").on("change",".check",function(){
		var num = $(this).val();
		var hasChk = $(this).is(':checked');
		if(hasChk){
		  $("[value = '"+num+"'][class = 'check']:checkbox").attr("checked",true);
		}else{
		  $("[value = '"+num+"'][class = 'check']:checkbox").attr("checked",false);
		}
	});
	$("li[UserRoleCode]").on("click",function(){
		$("#keyWord").attr("UserRoleCode",$(this).attr("UserRoleCode"));
		$(".hiding").removeClass();
		$("#keyWord").val("");
	})
});
</script>
</head>

<body>
  <!-- 群ID -->
  <input type="hidden" id="roomId" name="roomId" value = "${roomId}">
  <!-- section header -->
  <div id="content" class="site-content" style="width: 750px;">
    <div class="clear"></div>
    <!-- content-area -->

    <div class="line-one">
      <div class="row-fluid">
        <div class="span12">
          <div id="tabs" class="tabs">
            <nav>
              <nav>
                <ul>
                  <c:forEach var="dd" items="${departmentDatas}" varStatus="status">
                    <c:if test="${status.index == 0}">
                      <li class="tab-current" UserRoleCode="${dd.id}"><a href="#section-${status.index}"><span>${dd.deptName}</span></a></li>
                    </c:if>
                    <c:if test="${status.index > 0}">
                      <li class="" UserRoleCode="${dd.id}"><a href="#section-${status.index}"><span>${dd.deptName}</span></a></li>
                    </c:if>
                  </c:forEach>
                </ul>
              </nav>
            </nav>
            <div class="tab-info" id="tab-info">
              <c:forEach var="dd" items="${departmentDatas}" varStatus="status">
              <c:if test="${status.index == 0}">
              <section id="section-${status.index}" class="content-current" UserRoleCode="${dd.id}">
              </c:if>
              <c:if test="${status.index > 0}">
              <section id="section-${status.index}" class="" UserRoleCode="${dd.id}">
              </c:if>
                <div class="mediabox">
                  <table style="width: 100%">
                    <tbody>
                    <c:forEach var="ud" items="${dd.userDatas}">
                      <tr>
                        <td>
                          <c:set var="flag" value="false" />
                          <c:forEach var="member" items="${roomMembers}">
                            <c:if test="${member.id == ud.id}">
                              <c:set var="flag" value="true" />
                            </c:if>
                          </c:forEach>
                          <c:if test="${flag}">
                          <input type="checkbox" name="contacts" class="check" style="margin-bottom: 6px;" value="${ud.id}" checked="checked">
                          </c:if>
                          <c:if test="${!flag}">
                          <input type="checkbox" name="contacts" class="check" style="margin-bottom: 6px;" value="${ud.id}">
                          </c:if>
                          <span keyword="">${ud.trueName}（电话：${ud.mobile}）</span>
                        </td>
                      </tr>
                    </c:forEach>
                    </tbody>
                  </table>
                </div>
              </section>
              </c:forEach>
          </div>
        </div>
      </div>
    </div>
  </div>
  <script src="${ctx}/js/CBPFWTabs.js"></script>
  <script>
    new CBPFWTabs(document.getElementById('tabs'));
  </script>
</body>
</html>