<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Crop" %>
<%@ page import="com.chengxusheji.po.CropClass" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Crop> cropList = (List<Crop>)request.getAttribute("cropList");
    //获取所有的cropClassObj信息
    List<CropClass> cropClassList = (List<CropClass>)request.getAttribute("cropClassList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    CropClass cropClassObj = (CropClass)request.getAttribute("cropClassObj");
    String cropName = (String)request.getAttribute("cropName"); //农作物名称查询关键字
    String addTime = (String)request.getAttribute("addTime"); //添加时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>农作物查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
		<ul class="breadcrumb">
  			<li><a href="<%=basePath %>index.jsp">首页</a></li>
  			<li><a href="<%=basePath %>Crop/frontlist">农作物信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Crop/crop_frontAdd.jsp" style="display:none;">添加农作物</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<cropList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Crop crop = cropList.get(i); //获取到农作物对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>Crop/<%=crop.getCropId() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=crop.getCropPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		农作物id:<%=crop.getCropId() %>
			     	</div>
			     	<div class="field">
	            		农作物分类:<%=crop.getCropClassObj().getCropClassName() %>
			     	</div>
			     	<div class="field">
	            		农作物名称:<%=crop.getCropName() %>
			     	</div>
			     	<div class="field">
	            		添加时间:<%=crop.getAddTime() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Crop/<%=crop.getCropId() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="cropEdit('<%=crop.getCropId() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="cropDelete('<%=crop.getCropId() %>');" style="display:none;">删除</a>
			     </div>
			</div>
			<%  } %>

			<div class="row">
				<div class="col-md-12">
					<nav class="pull-left">
						<ul class="pagination">
							<li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
							<%
								int startPage = currentPage - 5;
								int endPage = currentPage + 5;
								if(startPage < 1) startPage=1;
								if(endPage > totalPage) endPage = totalPage;
								for(int i=startPage;i<=endPage;i++) {
							%>
							<li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
							<%  } %> 
							<li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						</ul>
					</nav>
					<div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>农作物查询</h1>
		</div>
		<form name="cropQueryForm" id="cropQueryForm" action="<%=basePath %>Crop/frontlist" class="mar_t15" method="post">
            <div class="form-group">
            	<label for="cropClassObj_cropClassId">农作物分类：</label>
                <select id="cropClassObj_cropClassId" name="cropClassObj.cropClassId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(CropClass cropClassTemp:cropClassList) {
	 					String selected = "";
 					if(cropClassObj!=null && cropClassObj.getCropClassId()!=null && cropClassObj.getCropClassId().intValue()==cropClassTemp.getCropClassId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=cropClassTemp.getCropClassId() %>" <%=selected %>><%=cropClassTemp.getCropClassName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="cropName">农作物名称:</label>
				<input type="text" id="cropName" name="cropName" value="<%=cropName %>" class="form-control" placeholder="请输入农作物名称">
			</div>
			<div class="form-group">
				<label for="addTime">添加时间:</label>
				<input type="text" id="addTime" name="addTime" class="form-control"  placeholder="请选择添加时间" value="<%=addTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="cropEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;农作物信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="cropEditForm" id="cropEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="crop_cropId_edit" class="col-md-3 text-right">农作物id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="crop_cropId_edit" name="crop.cropId" class="form-control" placeholder="请输入农作物id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="crop_cropClassObj_cropClassId_edit" class="col-md-3 text-right">农作物分类:</label>
		  	 <div class="col-md-9">
			    <select id="crop_cropClassObj_cropClassId_edit" name="crop.cropClassObj.cropClassId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="crop_cropName_edit" class="col-md-3 text-right">农作物名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="crop_cropName_edit" name="crop.cropName" class="form-control" placeholder="请输入农作物名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="crop_cropPhoto_edit" class="col-md-3 text-right">农作物图片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="crop_cropPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="crop_cropPhoto" name="crop.cropPhoto"/>
			    <input id="cropPhotoFile" name="cropPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="crop_cropDesc_edit" class="col-md-3 text-right">农作物描述:</label>
		  	 <div class="col-md-9">
			 	<textarea name="crop.cropDesc" id="crop_cropDesc_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="crop_cropMemo_edit" class="col-md-3 text-right">备注信息:</label>
		  	 <div class="col-md-9">
			    <textarea id="crop_cropMemo_edit" name="crop.cropMemo" rows="8" class="form-control" placeholder="请输入备注信息"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="crop_addTime_edit" class="col-md-3 text-right">添加时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date crop_addTime_edit col-md-12" data-link-field="crop_addTime_edit">
                    <input class="form-control" id="crop_addTime_edit" name="crop.addTime" size="16" type="text" value="" placeholder="请选择添加时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		</form> 
	    <style>#cropEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxCropModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
//实例化编辑器
var crop_cropDesc_edit = UE.getEditor('crop_cropDesc_edit'); //农作物描述编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.cropQueryForm.currentPage.value = currentPage;
    document.cropQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.cropQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.cropQueryForm.currentPage.value = pageValue;
    documentcropQueryForm.submit();
}

/*弹出修改农作物界面并初始化数据*/
function cropEdit(cropId) {
	$.ajax({
		url :  basePath + "Crop/" + cropId + "/update",
		type : "get",
		dataType: "json",
		success : function (crop, response, status) {
			if (crop) {
				$("#crop_cropId_edit").val(crop.cropId);
				$.ajax({
					url: basePath + "CropClass/listAll",
					type: "get",
					success: function(cropClasss,response,status) { 
						$("#crop_cropClassObj_cropClassId_edit").empty();
						var html="";
		        		$(cropClasss).each(function(i,cropClass){
		        			html += "<option value='" + cropClass.cropClassId + "'>" + cropClass.cropClassName + "</option>";
		        		});
		        		$("#crop_cropClassObj_cropClassId_edit").html(html);
		        		$("#crop_cropClassObj_cropClassId_edit").val(crop.cropClassObjPri);
					}
				});
				$("#crop_cropName_edit").val(crop.cropName);
				$("#crop_cropPhoto").val(crop.cropPhoto);
				$("#crop_cropPhotoImg").attr("src", basePath +　crop.cropPhoto);
				crop_cropDesc_edit.setContent(crop.cropDesc, false);
				$("#crop_cropMemo_edit").val(crop.cropMemo);
				$("#crop_addTime_edit").val(crop.addTime);
				$('#cropEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除农作物信息*/
function cropDelete(cropId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Crop/deletes",
			data : {
				cropIds : cropId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#cropQueryForm").submit();
					//location.href= basePath + "Crop/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交农作物信息表单给服务器端修改*/
function ajaxCropModify() {
	$.ajax({
		url :  basePath + "Crop/" + $("#crop_cropId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#cropEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#cropQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();

    /*添加时间组件*/
    $('.crop_addTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>

