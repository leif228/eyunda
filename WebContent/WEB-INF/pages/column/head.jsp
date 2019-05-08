<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->


<div class="header-top">
	 <div class="container">
		 <div class="pull-left account-avatar user-logo">			
				 <a href="###"><img src="${ctx}/download/imageDownload?url=${userData.userLogo}" class="thumbnail"></a>		
		 </div>
		 <div class="details">				 
				<div class="locate">
					 <div class="detail-grid">
						 <div class="lctr mt5">
								<img src="${ctx}/img/lct.png" alt=""/>
						 </div>
						 <p>${userData.trueName}
						 <span>http://${pageContext.request.serverName}</span></p>
						 <div class="clearfix"></div>
					 </div>
					 <div class="detail-grid">
						 <div class="lctr">
								<img src="${ctx}/img/phn.png" alt=""/>
						 </div>
						 <p>电话:${userData.mobile }</p>
						 <div class="clearfix"></div>
					 </div>
				</div>
		 </div>
		 <div class="clearfix"></div>
	 </div>
</div>
<div class="header">
	 <div class="container">
	 	 <div class="top-title">${userData.trueName}</div>
		 <div class="top-menu">
			 <span class="menu"><img src="${ctx}/img/menu.png" alt=""></span>
			 <ul class="nav1">
				<c:forEach var="columnCode" items="${columnCodes}">
					<c:choose>  
					   <c:when test="${currentIndex eq columnCode }">
					    <li class="active">
					    	<a href="${ctx}/portal/site/index?c=${columnCode}" target="_self">${columnCode.description}</a>
	              		</li>
					   </c:when>    
					   <c:otherwise>
					    <li>
					    	<a href="${ctx}/portal/site/index?c=${columnCode}" target="_self">${columnCode.description}</a>
	              		</li>
					   </c:otherwise>  
					</c:choose>
                 </c:forEach>
			 </ul>
		 </div>
		 <!-- script-for-menu -->
							 <script>
							   $( "span.menu" ).click(function() {
								 $( "ul.nav1" ).slideToggle( 300, function() {
								 // Animation complete.
								  });
								 });
							</script>
		 <!-- /script-for-menu -->
		 <div class="clearfix"></div>
	 </div>
</div>
<div id="main">
  <div class="container">
		<div class="row">
			<div class="span12">