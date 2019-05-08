<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx"
	value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>易运达 - 首页</title>
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="guoqiang" />

<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/css/bootstrap-responsive.css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.css" rel="stylesheet" />
<link href="${ctx}/css/common.css" rel="stylesheet" />
<link href="${ctx}/css/smartMenu.css" rel="stylesheet" />

<script src="${ctx}/js/jquery-v.min.js"></script>
<script src="${ctx}/js/bootstrap.js"></script>
<script src="${ctx}/js/new/portal-favorite-ships.js"></script>
<script src="${ctx}/js/jquery.form-2.63.js"></script>
<script src="${ctx}/js/jquery-smartMenu.js"></script>

<script type="text/javascript">
	var _rootPath = "${ctx}";
</script>
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="${ctx}/js/html5.js"></script>
<![endif]-->
<style>
.floats{
	float: left;
}

.cs{
	height: 35px;
}
</style>

</head>

<body>
	<!-- section header -->
	<jsp:include page="portal-head.jsp"></jsp:include>
	<nav class="breadcrumb" style="margin-bottom:10px;background: transparent;">
		<i class="fa-television fa"></i>
		<a class="crumbs" title="返回首页" href="${ctx}/portal/home/shipHome">首页</a>
		<i class="fa fa-angle-right"></i>
		收藏
	</nav>
	<!-- section content -->
	<div id="content" class="site-content">
		<div class="clear"></div>
		<!-- content-area -->
		<div id="primary" class="content-area">
			<div class="line-one">
				<div class="cat-box">
					<h3 class="cat-title">
						<a href="###" title="最新收藏信息"><i class="fa fa-bars"></i>收藏</a><span class="more"><a href="###">更多</a></span>
					</h3>
					<div class="clear"></div>
					<div class="cat-site" style="margin:0px;">
					<div><p align="center" style="color: #84c1ff"><b>易运达提示：</b>右键组别内容可获取更多功能</p></div>
					  <!-- 组别和收藏的船 -->
					  <div class='floats' style="width: 40%">
					  	<div class="cs"><b>收藏夹内容</b></div>
					  	<c:forEach items="${myShipFavoriteDatas}" var="myShipFavoriteData">
					  	 <div class="cs favoriteMouse" favoriteId="${myShipFavoriteData.id}" favoriteName="${myShipFavoriteData.favoriteName}">
					  	   <b>组名：</b>${myShipFavoriteData.favoriteName}
					  	 </div>
					  	  <c:forEach items="${myShipFavoriteData.shipNameDatas}" var="shipNameData">
					  	    <div class="cs shipMouse" shipId="${shipNameData.id}" shipName="${shipNameData.shipName}" favoriteId="${myShipFavoriteData.id}">
					  	      &nbsp;&nbsp;&nbsp;&nbsp;${shipNameData.shipName}
					  	    </div>
					  	  </c:forEach>
					  	</c:forEach>
					  </div>
					  <!-- 每个组有几项以及船舶详情 -->
      				  <div class='floats' style="width: 60%">
      				  	<div class="cs"><b>说明</b></div>
      				  	<c:forEach varStatus="status" items="${myShipFavoriteDatas}" var="myShipFavoriteData">
      				  	  <c:if test="${empty myShipFavoriteData.shipNameDatas}">
      				  	    <div class="cs">0项</div>
					  	  </c:if>
					  	  <c:if test="${!empty myShipFavoriteData.shipNameDatas}">
      				  	    <div class="cs">${fn:length(myShipFavoriteData.shipNameDatas)}项</div>
					  	  </c:if>
					  	  <c:forEach items="${myShipFavoriteData.shipNameDatas}" var="shipNameData">
					  	    <div class="cs"><a href="${ctx}/portal/home/shipInfo?shipId=${shipNameData.id}">${shipNameData.shipName}（MMSI：${shipNameData.mmsi}）</a></div>
					  	  </c:forEach>
					  	</c:forEach>
      				  </div>
      				  <!-- 操作 -->
      				  <!-- 注释
      				  <div class='floats' style="width: 15%">
      				  	<div class="cs"><b>操作&nbsp</b><button class="btn btn-primary btnNewFavorite" title="添加分组" idVal="0">
                         <i class="icon-plus icon-white"></i>
                        </button></div>
      				  	<c:forEach items="${myShipFavoriteDatas}" var="myShipFavoriteData">
					  	 <div class="cs">
					  	 	<a class="btn btn-primary btnEditFavorite" title="修改分组"
                          	favoriteId="${myShipFavoriteData.id}"
                          	favoriteName="${myShipFavoriteData.favoriteName}"> <i
                          	class="icon-pencil icon-white"></i>
                        	</a>
                        	<a class="btn btn-danger deleteFavorite" title="删除分组"
                          	idVal="${myShipFavoriteData.id}" favoriteName="${myShipFavoriteData.favoriteName}"> <i
                          	class="icon-trash icon-white"></i>
                        	</a>
					  	 </div>
					  	  <c:forEach items="${myShipFavoriteData.shipNameDatas}" var="shipNameData">
					  	    <div class="cs">
                        	</div>
					  	  </c:forEach>
					  	</c:forEach>
      				  </div>
      				   -->
      				  <div style="clear:both;"></div>
				    </div>
				    <div style="clear:both;"></div>
				    <div class="clear"></div>
				</div>
			</div>
		</div>
		<div id="sidebar" class="widget-area">
			<jsp:include page="./notice.jsp"></jsp:include>

			<jsp:include page="./customerDownload.jsp"></jsp:include>
		</div>
		</div>
		<!-- widget-area -->
		
		<div class="clear"></div>
	</div>
	<!-- section footer -->
	<jsp:include page="portal-foot.jsp"></jsp:include>
	<!-- javascript
    ================================================== -->
	<script src="${ctx}/js/jquery.divSelect.js"></script>
	
	<!-- 新增或新增分组对话框 -->
	<div id="saveFavoriteDialog" class="modal hide">
    <form class="form-horizontal" name="saveFavorite" id="saveFavorite"
      novalidate="novalidate" method="post" action="">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>收藏夹管理</h3>
      </div>
      <div style="text-align:center;margin-top:10px;" class="modal-body">
        <input type="hidden" id="saveFavoriteId" name="saveFavoriteId" value="0">
        分组名称: <input type="text" id="saveFavoriteName" name="saveFavoriteName" value="" style="height:26px;">
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <a id="btnSaveFavorite" class="btn btn-primary"> <i
          class="icon icon-ok icon-white"></i> 保存
        </a>
       </div>
     </form>
     </div>
     
    <!-- 删除分组对话框 -->
	<div id="deleteFavoriteDialog" class="modal hide">
    <form class="form-horizontal" name="deleteFavorite" id="deleteFavorite"
      novalidate="novalidate" method="post" action="">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>收藏夹管理</h3>
      </div>
      <div class="modal-body">
      	   <p id="deleFavoriteText"></p>
           <input type="hidden" id="deleFavoriteId" name="deleFavoriteId" value="">
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <a id="btnDeleteFavorite" class="btn btn-primary"> <i
          class="icon icon-ok icon-white"></i> 保存
        </a>
       </div>
     </form>
     </div>
     
    <!-- 删除收藏船舶对话框 -->
	<div id="deleteFavoriteShipDialog" class="modal hide">
    <form class="form-horizontal" name="deleteFavoriteShip" id="deleteFavoriteShip"
      novalidate="novalidate" method="post" action="">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>收藏夹管理</h3>
      </div>
      <div class="modal-body">
      	   <p id="deleteShipText"></p>
           <input type="hidden" id="deleteShipId" name="deleteShipId" value="">
           <input type="hidden" id="deleteShipFavoriteId" name="deleteShipFavoriteId" value="">
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <a id="btnDeleteFavoriteShip" class="btn btn-primary"> <i
          class="icon icon-ok icon-white"></i> 保存
        </a>
       </div>
     </form>
     </div>
     
    <!-- 更新收藏船舶对话框 -->
	<div id="updateFavoriteShipDialog" class="modal hide">
    <form class="form-horizontal" name="updateFavoriteShip" id="updateFavoriteShip"
      novalidate="novalidate" method="post" action="">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>收藏夹管理</h3>
      </div>
      <div class="modal-body">
      	   <p id="updateShipFavoriteText"></p>
           <input type="hidden" id="updateShipId" name="updateShipId" value="">
           <select name="updateFavoriteSelect" id="updateFavoriteSelect">
             <c:forEach items="${myShipFavoriteDatas}" var="myShipFavoriteData">
               <option value ="${myShipFavoriteData.id}">${myShipFavoriteData.favoriteName}</option>
             </c:forEach>
           </select>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <a id="btnupdateFavoriteShip" class="btn btn-primary"> <i
          class="icon icon-ok icon-white"></i> 保存
        </a>
       </div>
     </form>
     </div>
  
</body>
</html>