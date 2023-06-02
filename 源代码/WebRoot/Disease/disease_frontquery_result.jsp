<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Disease" %>
<%@ page import="com.chengxusheji.po.Crop" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Disease> diseaseList = (List<Disease>)request.getAttribute("diseaseList");
    //获取所有的cropObj信息
    List<Crop> cropList = (List<Crop>)request.getAttribute("cropList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    Crop cropObj = (Crop)request.getAttribute("cropObj");
    String diseaseName = (String)request.getAttribute("diseaseName"); //病害名称查询关键字
    String addTime = (String)request.getAttribute("addTime"); //发布时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>病害查询</title>
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
  			<li><a href="<%=basePath %>Disease/frontlist">病害信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Disease/disease_frontAdd.jsp" style="display:none;">添加病害</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<diseaseList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Disease disease = diseaseList.get(i); //获取到病害对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>Disease/<%=disease.getDiseaseId() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=disease.getDiseasePhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		病害id:<%=disease.getDiseaseId() %>
			     	</div>
			     	<div class="field">
	            		农作物:<%=disease.getCropObj().getCropName() %>
			     	</div>
			     	<div class="field">
	            		病害名称:<%=disease.getDiseaseName() %>
			     	</div>
			     	<div class="field">
	            		发布时间:<%=disease.getAddTime() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Disease/<%=disease.getDiseaseId() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="diseaseEdit('<%=disease.getDiseaseId() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="diseaseDelete('<%=disease.getDiseaseId() %>');" style="display:none;">删除</a>
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
    		<h1>病害查询</h1>
		</div>
		<form name="diseaseQueryForm" id="diseaseQueryForm" action="<%=basePath %>Disease/frontlist" class="mar_t15" method="post">
            <div class="form-group">
            	<label for="cropObj_cropId">农作物：</label>
                <select id="cropObj_cropId" name="cropObj.cropId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(Crop cropTemp:cropList) {
	 					String selected = "";
 					if(cropObj!=null && cropObj.getCropId()!=null && cropObj.getCropId().intValue()==cropTemp.getCropId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=cropTemp.getCropId() %>" <%=selected %>><%=cropTemp.getCropName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="diseaseName">病害名称:</label>
				<input type="text" id="diseaseName" name="diseaseName" value="<%=diseaseName %>" class="form-control" placeholder="请输入病害名称">
			</div>
			<div class="form-group">
				<label for="addTime">发布时间:</label>
				<input type="text" id="addTime" name="addTime" class="form-control"  placeholder="请选择发布时间" value="<%=addTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="diseaseEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;病害信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="diseaseEditForm" id="diseaseEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="disease_diseaseId_edit" class="col-md-3 text-right">病害id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="disease_diseaseId_edit" name="disease.diseaseId" class="form-control" placeholder="请输入病害id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="disease_cropObj_cropId_edit" class="col-md-3 text-right">农作物:</label>
		  	 <div class="col-md-9">
			    <select id="disease_cropObj_cropId_edit" name="disease.cropObj.cropId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="disease_diseaseName_edit" class="col-md-3 text-right">病害名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="disease_diseaseName_edit" name="disease.diseaseName" class="form-control" placeholder="请输入病害名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="disease_diseasePhoto_edit" class="col-md-3 text-right">病害图片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="disease_diseasePhotoImg" border="0px"/><br/>
			    <input type="hidden" id="disease_diseasePhoto" name="disease.diseasePhoto"/>
			    <input id="diseasePhotoFile" name="diseasePhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="disease_diseaseDesc_edit" class="col-md-3 text-right">病害描述:</label>
		  	 <div class="col-md-9">
			 	<textarea name="disease.diseaseDesc" id="disease_diseaseDesc_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="disease_cureDesc_edit" class="col-md-3 text-right">防治方法:</label>
		  	 <div class="col-md-9">
			 	<textarea name="disease.cureDesc" id="disease_cureDesc_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="disease_addTime_edit" class="col-md-3 text-right">发布时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date disease_addTime_edit col-md-12" data-link-field="disease_addTime_edit">
                    <input class="form-control" id="disease_addTime_edit" name="disease.addTime" size="16" type="text" value="" placeholder="请选择发布时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		</form> 
	    <style>#diseaseEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxDiseaseModify();">提交</button>
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
var disease_diseaseDesc_edit = UE.getEditor('disease_diseaseDesc_edit'); //病害描述编辑器
var disease_cureDesc_edit = UE.getEditor('disease_cureDesc_edit'); //防治方法编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.diseaseQueryForm.currentPage.value = currentPage;
    document.diseaseQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.diseaseQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.diseaseQueryForm.currentPage.value = pageValue;
    documentdiseaseQueryForm.submit();
}

/*弹出修改病害界面并初始化数据*/
function diseaseEdit(diseaseId) {
	$.ajax({
		url :  basePath + "Disease/" + diseaseId + "/update",
		type : "get",
		dataType: "json",
		success : function (disease, response, status) {
			if (disease) {
				$("#disease_diseaseId_edit").val(disease.diseaseId);
				$.ajax({
					url: basePath + "Crop/listAll",
					type: "get",
					success: function(crops,response,status) { 
						$("#disease_cropObj_cropId_edit").empty();
						var html="";
		        		$(crops).each(function(i,crop){
		        			html += "<option value='" + crop.cropId + "'>" + crop.cropName + "</option>";
		        		});
		        		$("#disease_cropObj_cropId_edit").html(html);
		        		$("#disease_cropObj_cropId_edit").val(disease.cropObjPri);
					}
				});
				$("#disease_diseaseName_edit").val(disease.diseaseName);
				$("#disease_diseasePhoto").val(disease.diseasePhoto);
				$("#disease_diseasePhotoImg").attr("src", basePath +　disease.diseasePhoto);
				disease_diseaseDesc_edit.setContent(disease.diseaseDesc, false);
				disease_cureDesc_edit.setContent(disease.cureDesc, false);
				$("#disease_addTime_edit").val(disease.addTime);
				$('#diseaseEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除病害信息*/
function diseaseDelete(diseaseId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Disease/deletes",
			data : {
				diseaseIds : diseaseId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#diseaseQueryForm").submit();
					//location.href= basePath + "Disease/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交病害信息表单给服务器端修改*/
function ajaxDiseaseModify() {
	$.ajax({
		url :  basePath + "Disease/" + $("#disease_diseaseId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#diseaseEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#diseaseQueryForm").submit();
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

    /*发布时间组件*/
    $('.disease_addTime_edit').datetimepicker({
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

