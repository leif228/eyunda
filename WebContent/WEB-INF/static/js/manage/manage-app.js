$(document).ready(function() {
    //添加服务按钮
    $("#btnAdd").live("click",function(){
        $("#appId").attr("value","0");
        $("#appName").attr("value","");
        $("#appDesc").attr("value","");
        
        $("#appIconImg").attr("src","");
        $("#appIcon").attr("value","");
        
        $("#appUrl").attr("value","");
    });
    
    //修改服务按钮
    $(".btnEdit").live("click",function(){
        var id = $(this).attr("idVal");
        $("#appId").attr("value",id);
        var appName = $(this).attr("appNameVal");
        $("#appName").attr("value",appName);
        var appDesc = $(this).attr("appDescVal");
        $("#appDesc").attr("value",appDesc);
        
        var appIcon = $(this).attr("appIconVal");
        $("#appIconImg").attr("src",_rootPath+"/hyquan/download/imageDownload?url="+appIcon);
        $("#appIcon").attr("value","");
        
        var appUrl = $(this).attr("appUrlVal");
        $("#appUrl").attr("value",appUrl);
    })
    
    //保存服务信息
    $("#saveAppBtn").live("click",function(){
        if(!$("#appName").val()){
            alert("服务名字不能为空");
            return false;
        }
        $("#editDialogForm").ajaxSubmit({
            type : "POST",
            url : _rootPath+"/manage/app/saveApp",
            datatype : "json",
            success : function(data) {
                var redata = eval('(' + data + ')');
                var returnCode = redata.returnCode;
                var message = redata.message;
                if (returnCode == "Failure") {
                    alert(message);
                    return false;
                } else {
                    // alert(message);
                    window.location.href = _rootPath+"/manage/app/appList";
                    return false;
                }
            }
        });
    });
    
    //删除服务按钮
    $(".btnDelete").live("click",function(){
        var delAppId = $(this).attr("idVal");
        $("#delAppId").attr("value",delAppId);
    });
    
    //提交删除服务信息
    $("#delAppBtn").click(function(){
        $("#deleteDialogForm").ajaxSubmit({
            type : "POST",
            url : _rootPath+"/manage/app/deleteApp",
            datatype : "json",
            success : function(data) {
                var returnCode = $(data)[0].returnCode;
                var message = $(data)[0].message;
                var id = $(data)[0].id;
                if (returnCode == "Failure") {
                    alert(message);
                    return false;
                } else {
                    alert(message);
                    window.location.href = _rootPath+"/manage/app/appList";
                    return false;
                }
            }
        });
    });

});