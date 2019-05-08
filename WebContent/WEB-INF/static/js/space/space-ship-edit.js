$(document).ready(function(){

  $('input[type=checkbox],input[type=file]').uniform();
  
  $('select').select2();
  
  $("#addGroupForm").validate({
	    rules:{
	      groupName:{
	    	required:true,
	        minlength:2,
	        maxlength:20
	      }
	    },
	    errorClass: "help-inline",
	    errorElement: "span",
	    highlight:function(element, errorClass, validClass) {
	      $(element).parents('.control-group').addClass('error');
	    },
	    unhighlight: function(element, errorClass, validClass) {
	      $(element).parents('.control-group').removeClass('error');
	      $(element).parents('.control-group').addClass('success');
	    }
	  });
  
  $("#frmBaseInfo").validate({
    rules:{
      shipName:{
        required:true,
        minlength:2,
        maxlength:25
      },
      englishName:{
          required:false,
          minlength:2,
          maxlength:25
      },
      mmsi:{
        required:true,
        maxlength:50
      },
	  length:{
	    required:false,
	    number:true
	  },
      breadth:{
        required:false,
        number:true
      },
      mouldedDepth:{
  	    required:false,
  	    number:true
  	  },
      draught:{
        required:false,
        number:true
      },
      sumTons:{
        required:false,
        digits:true
      },
      cleanTons:{
        required:false,
        digits:true
      },
      aTons:{
        required:false,
        digits:true
      },
      bTons:{
        required:false,
        digits:true
      },
      fullContainer:{
         required:false,
         digits:true
      },
      halfContainer:{
         required:false,
         digits:true
      },
      spaceContainer:{
          required:false,
          digits:true
      },
      keyWords:{
        required:false,
        maxlength:250
      },
      shipTitle:{
        required:false,
        maxlength:250
      }
      
    },
    errorClass: "help-inline",
    errorElement: "span",
    highlight:function(element, errorClass, validClass) {
      $(element).parents('.control-group').addClass('error');
    },
    unhighlight: function(element, errorClass, validClass) {
      $(element).parents('.control-group').removeClass('error');
      $(element).parents('.control-group').addClass('success');
    }
  });
  
  $("#frmDetailInfo").validate({
    rules:{
      title:{
        required:false,
        minlength:0,
        maxlength:4096
      }
      
    },
    errorClass: "help-inline",
    errorElement: "span",
    highlight:function(element, errorClass, validClass) {
      $(element).parents('.control-group').addClass('error');
    },
    unhighlight: function(element, errorClass, validClass) {
      $(element).parents('.control-group').removeClass('error');
      $(element).parents('.control-group').addClass('success');
    }
  });
  
  $("#frmPriceInfo").validate({
    rules:{
      cargoName:{
        required:true,
        minlength:2,
        maxlength:25
      },
      tonTeu:{
		required:true,
		digits:true
      },
      price:{
		required:true,
		number:true
      },
      transFee:{
		required:true,
		number:true
      }
    },
    errorClass: "help-inline",
    errorElement: "span",
    highlight:function(element, errorClass, validClass) {
      $(element).parents('.control-group').addClass('error');
    },
    unhighlight: function(element, errorClass, validClass) {
      $(element).parents('.control-group').removeClass('error');
      $(element).parents('.control-group').addClass('success');
    }
  });
  
  $("#frmNewPortInfo").validate({
		rules:{
			portName:{
				required:true,
				minlength:2,
				maxlength:10
			}
		},
		errorClass: "help-inline",
		errorElement: "span",
		highlight:function(element, errorClass, validClass) {
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight: function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
			$(element).parents('.control-group').addClass('success');
		}
	});
  
  	//Form Validation
	$("#frmSaveOrderItem").validate({
		rules:{
			content:{
				required:true,
		        maxlength:500
			}
		},
		errorClass: "help-inline",
		errorElement: "span",
		highlight:function(element, errorClass, validClass) {
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight: function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
			$(element).parents('.control-group').addClass('success');
		}
	});
  
  // 返回
  $(".btnBack").click(function() {
    window.location.href = _rootPath+"/space/ship/myShip";
  });
  
  // 保存基本信息
  $(".btnSaveBaseInfo").click(function() {
    if ($("#frmBaseInfo").valid())
    $("#frmBaseInfo").ajaxSubmit({
      method : "POST",
      url : _rootPath+"/space/ship/myShip/saveBaseInfo",
      datatype : "json",
      success : function(data) {
        var redata = eval('('+data+')');
        var returnCode = redata.returnCode;
        var message = redata.message;
        if (returnCode == "Failure") {
          alert(message);
          return false;
        } else {
          alert(message);
          // window.location.href = _rootPath+"/space/ship/myShip/edit?tab=ship-baseinfo&id="+redata.shipId;
          window.location.href = _rootPath+"/space/ship/myShip";
          return true;
        }
      }
    });
  });

  // 保存分类属性
  $(".btnSaveSortInfo").click(function() {
    $("#frmSortInfo").ajaxSubmit({
      method : "POST",
      url : _rootPath+"/space/ship/myShip/saveSortInfo",
      datatype : "json",
      success : function(data) {
        var redata = eval('('+data+')');
        var returnCode = redata.returnCode;
        var message = redata.message;
        if (returnCode == "Failure") {
          alert(message);
          return false;
        } else {
          window.location.href = _rootPath+"/space/ship/myShip/edit?tab=ship-sortinfo&id="+redata.shipId;
          alert(message);
          return true;
        }
      }
    });
  });

  // 保存认证资料
  $(".btnSaveAudit").click(function() {
    $("#frmAudit").ajaxSubmit({
      method : "POST",
      url : _rootPath+"/space/ship/myShip/saveAudit",
      datatype : "json",
      success : function(data) {
        var redata = eval('('+data+')');
        var returnCode = redata.returnCode;
        var message = redata.message;
        if (returnCode == "Failure") {
          alert(message);
          return false;
        } else {
          window.location.href = _rootPath+"/space/ship/myShip/edit?tab=ship-audit&id="+redata.shipId;
          alert(message);
          return true;
        }
      }
    });
  });

  // 保存详细信息
  $(".btnSaveDetailInfo").click(function() {
    $("#frmDetailInfo").ajaxSubmit({
      method : "POST",
      url : _rootPath+"/space/ship/myShip/saveDetailInfo",
      datatype : "json",
      success : function(data) {
        var redata = eval('('+data+')');
        var returnCode = redata.returnCode;
        var message = redata.message;
        if (returnCode == "Failure") {
          alert(message);
          return false;
        } else {
          window.location.href = _rootPath+"/space/ship/myShip/edit?tab=ship-detail&id="+redata.shipId;
          alert(message);
          return true;
        }
      }
    });
  }); 

  // 船舶附件上移
  $(".btnMoveAttaUp").click(function() {
    $.ajax({
      method : "GET",
      data : {attaId : $(this).attr("idVal")},
      url : _rootPath+"/space/ship/myShip/moveAttaUp",
      datatype : "json",
      success : function(data) {
        var returnCode = $(data)[0].returnCode;
        var message = $(data)[0].message;
        if (returnCode == "Failure") {
          alert(message);
          return false;
        } else {
          window.location.href = _rootPath+"/space/ship/myShip/edit?tab=ship-detail&id="+$(data)[0].shipId;
          alert(message);
          return true;
        }
      }
    });
  });
  
  // 船舶附件下移
  $(".btnMoveAttaDown").click(function() {
    $.ajax({
      method : "GET",
      data : {attaId : $(this).attr("idVal")},
      url : _rootPath+"/space/ship/myShip/moveAttaDown",
      datatype : "json",
      success : function(data) {
        var returnCode = $(data)[0].returnCode;
        var message = $(data)[0].message;
        if (returnCode == "Failure") {
          alert(message);
          return false;
        } else {
          window.location.href = _rootPath+"/space/ship/myShip/edit?tab=ship-detail&id="+$(data)[0].shipId;
          alert(message);
          return true;
        }
      }
    });
  });
  
  //删除船舶附件信息
  $(".btnRemoveAtta").click(function() {
    $("#removeAttaId").val($(this).attr("idVal"));
    $("#removeAttaDialog").modal("show");
    
    return true;
  });
  
  // 保存接货信息
  $(".btnSaveDelivery").click(function() {
    $("#frmDelivery").ajaxSubmit({
      method :"POST",
      url : _rootPath+"/space/ship/myShip/saveDelivery",
      datatype : "json",
      success : function(data) {
        var redata = eval('('+data+')');
        var returnCode = redata.returnCode;
        var message = redata.message;
        if (returnCode == "Failure") {
          alert(message);
          return false;
        } else {
          window.location.href = _rootPath+"/space/ship/myShip/edit?tab=ship-delivery&id="+redata.shipId;
          alert(message);
          return true;
        }
      }
    });
  }); 
  
	// 保存合同条款
	$(".btnSaveOrderItem").click(function() {
		if($("#frmSaveOrderItem").valid())
		$("#frmSaveOrderItem").ajaxSubmit({
			method : "POST",
			url : _rootPath+"/space/ship/myShip/saveOrderItem",
			datatype : "json",
			success : function(data) {
				var redata = eval('('+data+')');
				var returnCode = redata.returnCode;
				var message = redata.message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					window.location.href = _rootPath+"/space/ship/myShip/edit?tab=ship-orderTemplate&id="+redata.shipId;
					alert(message);
					return true;
				}
			}
		});
	});
	
	// 修改合同条款
	$(".btnEditOrderItem").click(function() {
		var id = $(this).attr("idVal");
		if (id==0) {
			$("#orderItemId").val("0");
			$("#contentItem").val("");
		} else
		$.ajax({
			method : "GET",
			data : {id : $(this).attr("idVal")},
			url : _rootPath+"/space/ship/myShip/getTempItem",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					$("#orderItemId").val($(data)[0].tempItemData.id);
					$("#contentItem").val($(data)[0].tempItemData.content);
			        
			        return true;
				}
			}
		});
	});
	
	// 删除合同样板单条条款
	$(".btnRemoveOrderItem").click(function() {
		$("#removeOrderItemId").val($(this).attr("idVal"));
		$("#removeOrderItemDialog").modal("show");
		
		return true;
	});
	
	// 选择委托类型
	$("#warrantType").live("change",function(){
		if($(this).val() == "companyWarrant"){
			$("#BL").html("营业执照：");
			$("#OCC").html("组织机构代码证：");
		} else{
			$("#BL").html("船东身份证正面：");
			$("#OCC").html("船东身份证反面：");
		}
	});

	// 添加分组
	$("#btnAddGroup").click(function() {
		$.ajax({
			method : "GET",
			data : {},
			url : _rootPath+"/space/ship/myShip/addGroup",
			datatype : "json",
			success : function(data) {
				var returnCode = $(data)[0].returnCode;
				var message = $(data)[0].message;
				if (returnCode == "Failure") {
					alert(message);
					return false;
				} else {
					//读入数据，并填入
					/*$("#deleteGroupName").empty();
					var tr = "<tr>"+
					 		 "	<td width='200'>组名</td>"+
					 		 "	<td width='200'>操作</td>"+
					 		 "</tr>"
					for(var i=0;i<$(data)[0].content.myShipGroupDatas.length;i++){
						tr +="<tr>"+
					 		 "	<td width='200'>"+$(data)[0].content.myShipGroupDatas[i].groupName+"</td>"+
					 		 "	<td width='200'><a class='btn btn-danger btnDeleteGroup' idVal='"+$(data)[0].content.myShipGroupDatas[i].id+"'>删除分组</a></td>"+
					 		 "</tr>";
					}
					$("#deleteGroupName").append(tr);*/
					$("#groupId").val("0");
					$("#groupName").val("");
					$("#addGroupDialog").modal("show");
				}
			}
		});
		return true;
    });

	//保存分组
	$("#btnSaveGroup").click(function() {
		if ($("#addGroupForm").valid()){
		$("#addGroupForm").ajaxSubmit({
			method : "POST",
			url : _rootPath+"/space/ship/myShip/saveGroup",
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
		}
		return true;
	});

	// 删除分组
	$(".btnDeleteGroup").click(function() {
		var id = $(this).attr("idVal");
		$.ajax({
			method : "GET",
			data : {id:id},
			url : _rootPath+"/space/ship/myShip/deleteGroup",
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

	// 新建分组
	$(".btnNewGroup").click(function() {
		$("#groupId").val(0);
		$("#groupName").val("");
    });

	// 修改分组
	$(".btnEditGroup").click(function() {
		$("#groupId").val($(this).attr("groupId"));
		$("#groupName").val($(this).attr("groupName"));
    });

	
});
