$(document).ready(
    function() {
      $("#loadFavoriteContent").click(
          function() {
            $.ajax({
              method : "GET",
              data : {},
              url : _rootPath
                  + "/space/ship/myShip/loadFavoriteContent",
              datatype : "json",
              success : function(data) {
                var returnCode = $(data)[0].returnCode;
                var message = $(data)[0].message;
                if (returnCode == "Failure") {
                  alert(message);
                  return false;
                } else {
                  var mfd = $(data)[0].myShipFavoriteDatas;
                  var str = "";
                  for (var i = 0; i < mfd.length; i++) {
                    str += "<option value=" + mfd[i].id
                        + ">" + mfd[i].favoriteName
                        + "</option>";
                  }
                  $("#myShipFavorite").html(str);
                  $("#saveFavoriteDialog").modal("show");
                  return true;
                }
              }
            });
          })
    $("#listFavoriteContent").click(function() {
        $.ajax({
            method : "GET",
            data : {},
            url : _rootPath
                + "/space/ship/myShip/loadFavoriteContent",
            datatype : "json",
            success : function(data) {
              var returnCode = $(data)[0].returnCode;
              var message = $(data)[0].message;
              if (returnCode == "Failure") {
                alert(message);
                return false;
              } else {
                var mfd = $(data)[0].myShipFavoriteDatas;
                var str = "<tr>"+
                  "<td>序号</td>"+
                  "<td>组名</td>"+
                  "<td>操作"+
                  "<button class='btn btn-primary btnNewFavorite' title='添加' idVal='0'>"+
                  "<i class='icon-plus icon-white'></i>"+
                  "</button>"+
                  "</td>"+
                  "</tr>";
                for (var i = 0; i < mfd.length; i++) {
                  var j = i+1;
                  str += "<tr>"+
                         "<td>"+
                         "<div>"+j+"</div>"+
                         "</td>"+
                         "<td width='200'>"+
                         "<div>"+mfd[i].favoriteName+"</div>"+
                         "</td>"+
                         "<td>"+
                         "<div class='center'>"+
                         "<a class='btn btn-primary btnEditFavorite' title='修改分组'"+
                         "    favoriteId="+mfd[i].id+
                         "    favoriteName="+mfd[i].favoriteName+">"+
                         "  <i class='icon-pencil icon-white'></i>"+
                         "</a>"+
                         "<a class='btn btn-danger btnDeleteFavorite' title='删除分组' idVal="+mfd[i].id+">"+
                         "  <i class='icon-trash icon-white'></i>"+
                         "</a>"+
                         "</div>"+
                         "</td>"+
                         "</tr>";
                }
                $("#listFavorite").html(str);
                $('#saveFavoriteDialog').modal('hide');
        		$('#listFavoriteDialog').modal('show');
                return true;
              }
            }
          });
    });
	
    // 新建分组
  	$("#listFavorite").on("click",".btnNewFavorite",function(){
  		$("#favoriteId").val("0");
  		$("#favoriteName").val("");
  		return false;
  	});

  	//修改分组
  	$("#listFavorite").on("click",".btnEditFavorite",function(){
  		$("#saveFavoriteId").val($(this).attr("favoriteId"));
  		$("#saveFavoriteName").val($(this).attr("favoriteName"));
  	});
  	
    //删除分组
	$("#listFavorite").on("click",".btnDeleteFavorite",function(){
		$.ajax({
			method : "GET",
			data : {deleFavoriteId:$(this).attr("idVal")},
			url : _rootPath+"/space/ship/myShip/deleteFavorite",
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
	
	//保存新增或修改分组
	$("#btnSaveFavorite").click(function() {
		$("#saveFavorite").ajaxSubmit({
		      method :"POST",
		      url : _rootPath+"/space/ship/myShip/saveFavorite",
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
		return true;
	});
	
	//保存分组
	$("#btnSaveShipInFavorite").click(function() {
		$("#saveShipInFavorite").ajaxSubmit({
		      method :"POST",
		      url : _rootPath+"/space/ship/myShip/saveFavoriteContent",
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
		return true;
	});
	
	$("#manageFavorite").click(function() {
		alert("manageFavorite");
		var demo2 = $(".demo2").bootstrapDualListbox({
		    nonSelectedListLabel : '未选择',
		    selectedListLabel : '已选择',
		    preserveSelectionOnMove : 'moved',
		    moveOnSelect : false,
		    nonSelectedFilter : ''
		  });
		$('#manageFavoriteDialog').modal('show');
	})
	
});