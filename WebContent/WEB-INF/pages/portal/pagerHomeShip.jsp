<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
  function jumpPage(currPageNo) {
    $("#pageNo").val(currPageNo);
    if("${cargoType}".indexOf("container") < 0){
	    $("#pageform").attr("action",_rootPath+"/portal/home/shipHome?recvCargoPort="+
	    		"${recvCargoPort}&cargoType=${cargoType}&cargoWeight=${cargoWeight}");
    }else {
    	$("#pageform").attr("action",_rootPath+"/portal/home/shipHome?recvCargoPort="+
				"${recvCargoPort}&cargoType=${cargoType}&cargoVolumn=${cargoVolumn}");
    }
    $("#pageform").submit();
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
</script>

<div class="parentDiv">
  <input type="hidden" id="pageNo" name="pageNo" value="${shipPageData.pageNo}" />
  <input type="hidden" id="shipSortCode" name="shipSortCode" value="${shipSortCode }"/>

  <div class="floatcom pagebody">

    <c:set var="startIndex" value="2" />
    <c:set var="begin" value="${shipPageData.pageNo-startIndex}" />
    <c:set var="end" value="${shipPageData.pageNo+startIndex}" />

    <c:if test="${begin<1}">
      <c:set var="begin" value="1" />
      <c:set var="end" value="${begin+4}" />
    </c:if>

    <c:if test="${end>=shipPageData.totalPages}">
      <c:set var="end" value="${shipPageData.totalPages}" />
      <c:set var="begin" value="${end-4}" />
      <c:if test="${begin<1}">
        <c:set var="begin" value="1" />
      </c:if>
    </c:if>

    <ul class="pager helper-font-small">
      <li>共${shipPageData.totalCount}条纪录 页次:${shipPageData.pageNo}/${shipPageData.totalPages}页</li>
      <c:choose>
        <c:when test="${shipPageData.pageNo==1}">
          <li class="disabled"><a href="#">首页</a></li>
        </c:when>
        <c:otherwise>
          <li><a href="javascript:jumpPage(1)">首页</a></li>
        </c:otherwise>
      </c:choose>

      <c:choose>
        <c:when test="${shipPageData.pageNo==1}">
          <li class="disabled"><a href="#">上一页</a></li>
        </c:when>
        <c:otherwise>
          <li><a href="javascript:jumpPage(${shipPageData.pageNo-1})">上一页</a></li>
        </c:otherwise>
      </c:choose>

      <c:forEach begin="${begin}" end="${end}" var="p">
        <c:choose>
          <c:when test="${p==shipPageData.pageNo}">
            <li class="disabled">${p}</li>
          </c:when>
          <c:otherwise>
            <li><a href="javascript:jumpPage(${p})">${p}</a></li>
          </c:otherwise>
        </c:choose>
      </c:forEach>

      <c:if test="${end<shipPageData.totalPages}">
        <li>...</li>
      </c:if>


      <c:choose>
        <c:when test="${shipPageData.pageNo==shipPageData.totalPages}">
          <li class="disabled"><a href="#">下一页</a></li>
        </c:when>
        <c:otherwise>
          <li><a href="javascript:jumpPage(${shipPageData.pageNo+1})">下一页</a></li>
        </c:otherwise>
      </c:choose>

      <c:choose>
        <c:when test="${shipPageData.totalPages == shipPageData.pageNo}">
          <li class="disabled"><a href="#">尾页</a></li>
        </c:when>
        <c:otherwise>
          <li><a href="javascript:jumpPage(${shipPageData.totalPages})">尾页</a></li>
        </c:otherwise>
      </c:choose>
      <li>转到：
        <input type="text" style="width:3em;margin-top:8px;" id="goPageNo" value="" />页 
        <input type="button" value="Go" onclick="javascript:jumpPage($('#goPageNo').val());" />
      </li>

    </ul>

  </div>
  <div style="margin:0px auto;text-align:center">Copyright © 2014-2017 版权所有：广州市航讯船务有限公司<a href="http://www.miit.gov.cn/n11293472/index.html">粤ICP备17030513号-1</a>
  <p align="center">
  	<div style="display:none"><a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=44010402000562"><img src="${ctx}/img/beian.png"/>粤公网安备 44010402000562号</a></div>
  </p>
  </div>
  </div>
</div>
