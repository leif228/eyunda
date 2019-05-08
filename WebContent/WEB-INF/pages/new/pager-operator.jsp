<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
  var _endPage = "${pageData.totalPages}";
  
  function jumpPage(currPageNo) {
	if(currPageNo < 1)
		currPageNo = 1;
	if(currPageNo > _endPage)
		currPageNo = _endPage;
	
	$("#pageNo2").val(currPageNo);
	$("#pageform").submit();
  }
</script>

<c:set var="startIndex" value="8" />
<c:set var="begin" value="${pageData.pageNo-startIndex}" />
<c:set var="end" value="${pageData.pageNo+startIndex}" />

<c:if test="${begin < 1}">
  <c:set var="begin" value="1" />
  <c:set var="end" value="${begin + 9}" />
</c:if>

<c:if test="${end >= pageData.totalPages}">
  <c:set var="end" value="${pageData.totalPages}" />
  <c:set var="begin" value="${end - 9}" />
  <c:if test="${begin < 1}">
    <c:set var="begin" value="1" />
  </c:if>
</c:if>

<nav class="navigation pagination">
	<div class="nav-links">
		<a class="prev page-numbers" href="javascript:jumpPage(${pageData.pageNo - 1})"><i class="fa fa-angle-left"></i></a>
		
		<c:forEach begin="${begin}" end="${end}" var="p">
		  <c:if test="${p != pageData.pageNo}">
		    <a class="page-numbers" href="javascript:jumpPage(${p})"><span class="screen-reader-text">第 </span>${p}<span class="screen-reader-text"> 页</span></a> 
		  </c:if>
		  <c:if test="${p == pageData.pageNo}">
		    <span class="page-numbers current"><span class="screen-reader-text">第 </span>${p}<span class="screen-reader-text"> 页</span></span> 
		  </c:if>
		</c:forEach>
		
		<a class="next page-numbers" href="javascript:jumpPage(${pageData.pageNo + 1})"><i class="fa fa-angle-right"></i></a>
	</div>
</nav>
