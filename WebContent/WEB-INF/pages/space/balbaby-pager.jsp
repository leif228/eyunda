<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
  function jumpPage(currPageNo) {
	if(currPageNo==null || currPageNo==""){
		currPageNo=$("#pageNo1").val();
	}
    $("#pageNo1").val(currPageNo);
    $("#pageform1").submit();
  }
  $(function() {
    $("#goPageNo").keydown(function(e) {
      if (e.keyCode >= 48 && e.keyCode <= 57)
        return true;
      else if (e.keyCode >= 96 && e.keyCode <= 105)
        return true;
      else if (e.keyCode == 13){
    	  jumpPage($(this).val());
    	  return false;
      }
    });
  });
  
  function clearNoNum(obj) {
      obj.value = obj.value.replace(/[^\d]/g, ""); //清除“数字”以外的字符
  }
</script>

<div class="parentDiv">
  <input type="hidden" id="pageNo1" name="pageNo1" value="${pageData1.pageNo}" />

  <div class="floatcom pagebody">

    <c:set var="startIndex" value="2" />
    <c:set var="begin" value="${pageData1.pageNo-startIndex}" />
    <c:set var="end" value="${pageData1.pageNo+startIndex}" />

    <c:if test="${begin<1}">
      <c:set var="begin" value="1" />
      <c:set var="end" value="${begin+4}" />
    </c:if>

    <c:if test="${end>=pageData1.totalPages}">
      <c:set var="end" value="${pageData1.totalPages}" />
      <c:set var="begin" value="${end-4}" />
      <c:if test="${begin<1}">
        <c:set var="begin" value="1" />
      </c:if>
    </c:if>

    <ul class="pager helper-font-small">
      <li>共${pageData1.totalCount}条纪录 页次:${pageData1.pageNo}/${pageData1.totalPages}页</li>
      <c:choose>
        <c:when test="${pageData1.pageNo==1}">
          <li class="disabled"><a href="#">首页</a></li>
        </c:when>
        <c:otherwise>
          <li><a href="javascript:jumpPage(1)">首页</a></li>
        </c:otherwise>
      </c:choose>

      <c:choose>
        <c:when test="${pageData1.pageNo==1}">
          <li class="disabled"><a href="#">上一页</a></li>
        </c:when>
        <c:otherwise>
          <li><a href="javascript:jumpPage(${pageData1.pageNo-1})">上一页</a></li>
        </c:otherwise>
      </c:choose>

      <c:forEach begin="${begin}" end="${end}" var="p">
        <c:choose>
          <c:when test="${p==pageData1.pageNo}">
            <li class="disabled">${p}</li>
          </c:when>
          <c:otherwise>
            <li><a href="javascript:jumpPage(${p})">${p}</a></li>
          </c:otherwise>
        </c:choose>
      </c:forEach>

      <c:if test="${end<pageData1.totalPages}">
        <li>...</li>
      </c:if>


      <c:choose>
        <c:when test="${pageData1.pageNo==pageData1.totalPages}">
          <li class="disabled"><a href="#">下一页</a></li>
        </c:when>
        <c:otherwise>
          <li><a href="javascript:jumpPage(${pageData1.pageNo+1})">下一页</a></li>
        </c:otherwise>
      </c:choose>

      <c:choose>
        <c:when test="${pageData1.totalPages == pageData1.pageNo}">
          <li class="disabled"><a href="#">尾页</a></li>
        </c:when>
        <c:otherwise>
          <li><a href="javascript:jumpPage(${pageData1.totalPages})">尾页</a></li>
        </c:otherwise>
      </c:choose>
      <li>转到：<!-- onpropertychange="clearNoNum(this)" onkeypress="clearNoNum(this)" oninput="clearNoNum(this)" onblur="clearNoNum(this)" -->
        <input type="text" style="width:3em;margin-top:8px;" id="goPageNo" value=""/>页 
        <input type="button" style="" value="Go" onclick="javascript:jumpPage($('#goPageNo').val())"/>
      </li>
    </ul>
  </div>
</div>
