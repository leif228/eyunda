<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<aside class="widget">
	<h3 class="widget-title">
		<i class="fa fa-bars"></i>通知、公告
	</h3>
	<div id="hot_post_widget">
		<ul>
			<c:forEach varStatus="status" items="${noticePage.result}" var="noticeData">
              <li>
                <span class="li-icon li-icon-1">${status.index + 1}</span>
                <a href="${ctx}/portal/home/noticeInfo?noticeId=${noticeData.id}">${noticeData.title}</a>
              </li>
            </c:forEach>
		</ul>
	</div>
	<div class="clear"></div>
</aside>
