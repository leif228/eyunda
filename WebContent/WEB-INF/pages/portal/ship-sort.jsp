<%@page pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	function jumpShipSortPage(shipSortCode) {
		$("#shipSortCode").val(shipSortCode);
		$("#shipSortForm").submit();
	};
</script>

<div class="parentDiv">
	<input type="hidden" id="shipSortCode" name="shipSortCode"
		value="${shipSortCode }" />
	<div class="floatcom pagebody">
		<span>排序：</span>
		<c:choose>
			<c:when test="${shipSortCode == 'POINTER' }">
				<span class="label color-teal"> <a class="filterable"
					href="javascript:jumpShipSortPage('POINTER')" rel="popularity">人气</a>
				</span>
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('ORDER')" rel="sales">销量</a>
				</span>
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('TIME')" rel=" newly">最新</a>
				</span>
				<%-- 
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('PRICE')" rel="all">报价</a>
				</span>
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('TONS')" rel="portofolio">载货量</a>
				</span>--%>
			</c:when>
			<c:when test="${shipSortCode == 'ORDER' }">
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('POINTER')" rel="popularity">人气</a>
				</span>
				<span class="label color-teal"> <a class="filterable"
					href="javascript:jumpShipSortPage('ORDER')" rel="sales">销量</a>
				</span>
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('TIME')" rel=" newly">最新</a>
				</span>
				<%-- 
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('PRICE')" rel="all">报价</a>
				</span>
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('TONS')" rel="portofolio">载货量</a>
				</span>--%>
			</c:when>
			<c:when test="${shipSortCode == 'TIME' }">
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('POINTER')" rel="popularity">人气</a>
				</span>
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('ORDER')" rel="sales">销量</a>
				</span>
				<span class="label color-teal"> <a class="filterable"
					href="javascript:jumpShipSortPage('TIME')" rel=" newly">最新</a>
				</span>
				<%-- 
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('PRICE')" rel="all">报价</a>
				</span>
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('TONS')" rel="portofolio">载货量</a>
				</span>--%>
			</c:when>
			<%-- <c:when test="${shipSortCode == 'PRICE' }">
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('POINTER')" rel="popularity">人气</a>
				</span>
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('ORDER')" rel="sales">销量</a>
				</span>
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('TIME')" rel=" newly">最新</a>
				</span>
				<span class="label color-teal"> <a class="filterable"
					href="javascript:jumpShipSortPage('PRICE')" rel="all">报价</a>
				</span>
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('TONS')" rel="portofolio">载货量</a>
				</span>
			</c:when>
			<c:when test="${shipSortCode == 'TONS' }">
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('POINTER')" rel="popularity">人气</a>
				</span>
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('ORDER')" rel="sales">销量</a>
				</span>
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('TIME')" rel=" newly">最新</a>
				</span>
				<span class="label"> <a class="filterable"
					href="javascript:jumpShipSortPage('PRICE')" rel="all">报价</a>
				</span>
				<span class="label color-teal"> <a class="filterable"
					href="javascript:jumpShipSortPage('TONS')" rel="portofolio">载货量</a>
				</span>
			</c:when>--%>
			<c:otherwise>
			</c:otherwise>
		</c:choose>

	</div>
</div>
