$(document).ready(function() {
	$(".btnNewFavorite").live("click",function(){
		$("#saveFavoriteId").val("0");
  		$("#saveFavoriteName").val("");
		$('#saveFavoriteDialog').modal('show');
	});
	
	//保存新增或修改分组
	$("#btnSaveFavorite").live("click",function(){
		$("#saveFavorite").ajaxSubmit({
		      method :"POST",
		      url : _rootPath+"/space/ship/myShip/saveFavorite",
		      datatype : "json",
		      success : function(data) {
		    	var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
		        if (returnCode == "Failure") {
					alert(message);
					$('#saveFavoriteDialog').modal('hide');
					return false;
				} else {
					alert(message);
					location.reload();
					return true;
				}
		      }
		    });
		return true;
	});
	
	//修改分组
	$(".btnEditFavorite").live("click",function(){
  		$("#saveFavoriteId").val($(this).attr("favoriteId"));
  		$("#saveFavoriteName").val($(this).attr("favoriteName"));
  		$('#saveFavoriteDialog').modal('show');
  	});
  	
	//弹出删除分组对话框
	$(".deleteFavorite").live("click",function(){
  		$("#deleFavoriteText").html("你确认删除：  \""+$(this).attr("favoriteName")+"\"  收藏夹吗？");
  		$("#deleFavoriteId").val($(this).attr("idVal"));
		$('#deleteFavoriteDialog').modal('show');
	});
	
	//删除分组
	$("#btnDeleteFavorite").live("click",function(){
		$("#deleteFavorite").ajaxSubmit({
		      method :"POST",
		      url : _rootPath+"/space/ship/myShip/deleteFavorite",
		      datatype : "json",
		      success : function(data) {
		    	var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
		        if (returnCode == "Failure") {
					alert(message);
					$('#deleteFavoriteDialog').modal('hide');
					return false;
				} else {
					alert(message);
					location.reload();
					return true;
				}
		      }
		});    
	});
	
	//收藏船的鼠标右键
	$(".shipMouse").bind("mousedown", (function (e) {
	  if (e.which == 3) {
	    var opertionn = {
	      name: "shipMouse",
	      offsetX: 2,
	      offsetY: 2,
	      textLimit: 10,
	      beforeShow: $.noop,
	      afterShow: $.noop
	     };
		 var imageMenuData = [
		    [
		      {
		        text: "删除收藏",
		        func: function () {
			      $("#deleteShipId").val($(this).attr("shipId"));
			      $("#deleteShipFavoriteId").val($(this).attr("favoriteId"));
			      $("#deleteShipText").html("你确认删除收藏：  \""+$(this).attr("shipName")+"\"  船舶？");
			      $('#deleteFavoriteShipDialog').modal('show');
		          }
		        },
		        {
			      text: "移动收藏到另一分组",
			      func: function () {
				    $("#updateShipId").val($(this).attr("shipId"));
				    $("#updateShipFavoriteText").html("你要把此   \""+$(this).attr("shipName")+"\"  收藏船舶移动到？");
				    $('#updateFavoriteShipDialog').modal('show');
			        }
			     }
		      ]
		    ]; 
		    $(this).smartMenu(imageMenuData, opertionn); 
		  } 
		}));
	
	//删除收藏船舶
	$("#btnDeleteFavoriteShip").live("click",function(){
		$("#deleteFavoriteShip").ajaxSubmit({
		  method :"POST",
		  url : _rootPath+"/space/ship/myShip/deleteFavoriteShip",
		  datatype : "json",
		  success : function(data) {
		    var returnCode = $(data)[0].returnCode;
			var message = $(data)[0].message;
		    if (returnCode == "Failure") {
			  alert(message);
			  return false;
		    } else {
			  alert(message);
			  location.reload();
			  return true;
		    }
		  }
		});    
	});

	//移动收藏船舶
	$("#btnupdateFavoriteShip").live("click",function(){
		$("#updateFavoriteShip").ajaxSubmit({
		  method :"POST",
		  url : _rootPath+"/space/ship/myShip/moveFavoriteShip",
		  datatype : "json",
		  success : function(data) {
		    var returnCode = $(data)[0].returnCode;
			var message = $(data)[0].message;
		    if (returnCode == "Failure") {
			  alert(message);
			  return false;
		    } else {
			  alert(message);
			  location.reload();
			  return true;
		    }
		  }
		});    
	});

	$(".favoriteMouse").bind("mousedown", (function (e) {
	  if (e.which == 3) {
	    var opertionn = {
	    name: "favoriteMouse",
	    offsetX: 2,
	    offsetY: 2,
	    textLimit: 10,
	    beforeShow: $.noop,
	    afterShow: $.noop
	    };
	    var imageMenuData = [
	      [
	        {
              text: "新增分组",
		      func: function () {
		    	$("#saveFavoriteId").val("0");
		    	$("#saveFavoriteName").val("");
				$('#saveFavoriteDialog').modal('show');
			   }
	         }
	      ],
	      [
	        {
	          text: "修改分组名称",
	          func: function () {
	        	$("#saveFavoriteId").val($(this).attr("favoriteId"));
	        	$("#saveFavoriteName").val($(this).attr("favoriteName"));
			    $('#saveFavoriteDialog').modal('show');
		       }
		     },
		     {
		       text: "删除分组",
		       func: function () {
		         $("#deleFavoriteText").html("你确认删除：  \""+$(this).attr("favoriteName")+"\"  收藏夹吗？");
		     	 $("#deleFavoriteId").val($(this).attr("favoriteId"));
		   		 $('#deleteFavoriteDialog').modal('show');
			   }
			 }
		   ]
		]; 
		$(this).smartMenu(imageMenuData, opertionn); 
	  } 
	}));

});