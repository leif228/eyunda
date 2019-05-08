<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div>
<!-- 收藏船舶对话框 -->
  <div id="saveFavoriteDialog" class="modal hide fade">
    <form class="form-horizontal" name="saveShipInFavorite"
      id="saveShipInFavorite" novalidate="novalidate" method="post"
      action="">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>收藏分组</h3>
      </div>
      <div class="modal-body">
        <fieldset>
          <div
            style="padding: 0px 120px; text-align: center; width: 300px; margin-top: 20px; margin-bottom: 20px;">
            <!-- 收藏分组下拉选 -->
            收藏分组: <select name="myShipFavorite" id="myShipFavorite">
            </select> 
            <input type="button" value="收藏夹管理" id="listFavoriteContent" />
          </div>
          <div style="padding: 0px 120px">
            <input type="hidden" id="favoriteShipId" name="shipId"
              value="${myShipData.id}">
            <p>船舶名称：${myShipData.shipName}（MMSI:${myShipData.mmsi}）</p>
          </div>
        </fieldset>
      </div>
      <div class="modal-footer">
        <button class="btn" data-dismiss="modal">
          <i class="icon icon-off"></i> 取消
        </button>
        <a id="btnSaveShipInFavorite" class="btn btn-primary"> <i
          class="icon icon-ok icon-white"></i> 保存
        </a>
      </div>
    </form>
  </div>
  
  <!-- 收藏夹管理对话框 -->
  <div id="listFavoriteDialog" class="modal hide">
    <form class="form-horizontal" name="saveFavorite" id="saveFavorite"
      novalidate="novalidate" method="post" action="">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>收藏夹管理</h3>
      </div>
      <div class="modal-body">
        <fieldset>
          <div>
            <table style="margin: 0 auto;" border="1" id="">
              <tbody id="listFavorite">
              </tbody>
            </table>
          </div>
          <div style="text-align:center;margin-top:10px;">
            <input type="hidden" id="saveFavoriteId" name="saveFavoriteId" value="0">
            分组名称: <input type="text" id="saveFavoriteName" name="saveFavoriteName" value="" style="height:26px;">
          </div>
        </fieldset>
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
 </div>