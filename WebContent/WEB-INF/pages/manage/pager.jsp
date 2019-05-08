<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
  function jumpPage(currPageNo) {
	if(currPageNo==null || currPageNo==""){
		currPageNo=$("#pageNo").val();
	}
    $("#pageNo").val(currPageNo);
    $("#pageform").submit();
  }
  function clearNoNum(obj) {
      obj.value = obj.value.replace(/[^\d]/g, ""); //清除“数字”以外的字符
  }
</script>

<div class="parentDiv">
  <input type="hidden" id="pageNo" name="pageNo" value="${pageData.pageNo}" />

  <div class="floatcom pagebody">

    <c:set var="startIndex" value="2" />
    <c:set var="begin" value="${pageData.pageNo-startIndex}" />
    <c:set var="end" value="${pageData.pageNo+startIndex}" />

    <c:if test="${begin<1}">
      <c:set var="begin" value="1" />
      <c:set var="end" value="${begin+4}" />
    </c:if>

    <c:if test="${end>=pageData.totalPages}">
      <c:set var="end" value="${pageData.totalPages}" />
      <c:set var="begin" value="${end-4}" />
      <c:if test="${begin<1}">
        <c:set var="begin" value="1" />
      </c:if>
    </c:if>

    <ul class="pager helper-font-small">
      <li>共${pageData.totalCount}条纪录 页次:${pageData.pageNo}/${pageData.totalPages}页</li>
      <c:choose>
        <c:when test="${pageData.pageNo==1}">
          <li class="disabled"><a href="#">首页</a></li>
        </c:when>
        <c:otherwise>
          <li><a href="javascript:jumpPage(1)">首页</a></li>
        </c:otherwise>
      </c:choose>

      <c:choose>
        <c:when test="${pageData.pageNo==1}">
          <li class="disabled"><a href="#">上一页</a></li>
        </c:when>
        <c:otherwise>
          <li><a href="javascript:jumpPage(${pageData.pageNo-1})">上一页</a></li>
        </c:otherwise>
      </c:choose>

      <c:forEach begin="${begin}" end="${end}" var="p">
        <c:choose>
          <c:when test="${p==pageData.pageNo}">
            <li class="disabled">${p}</li>
          </c:when>
          <c:otherwise>
            <li><a href="javascript:jumpPage(${p})">${p}</a></li>
          </c:otherwise>
        </c:choose>
      </c:forEach>

      <c:if test="${end<pageData.totalPages}">
        <li>...</li>
      </c:if>


      <c:choose>
        <c:when test="${pageData.pageNo==pageData.totalPages}">
          <li class="disabled"><a href="#">下一页</a></li>
        </c:when>
        <c:otherwise>
          <li><a href="javascript:jumpPage(${pageData.pageNo+1})">下一页</a></li>
        </c:otherwise>
      </c:choose>

      <c:choose>
        <c:when test="${pageData.totalPages == pageData.pageNo}">
          <li class="disabled"><a href="#">尾页</a></li>
        </c:when>
        <c:otherwise>
          <li><a href="javascript:jumpPage(${pageData.totalPages})">尾页</a></li>
        </c:otherwise>
      </c:choose>
      <li>转到：
        <input type="text" style="width:3em;margin-top:8px;" id="goPageNo" value="" 
        		onpropertychange="clearNoNum(this)" onkeypress="clearNoNum(this)" 
        		oninput="clearNoNum(this)" onblur="clearNoNum(this)"/>页 
        <input type="button" style="" value="Go" onclick="javascript:jumpPage($('#goPageNo').val())"/>
      </li>
    </ul>
  </div>
</div>
