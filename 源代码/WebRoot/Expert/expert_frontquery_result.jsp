<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Expert" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Expert> expertList = (List<Expert>)request.getAttribute("expertList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String expertUserName = (String)request.getAttribute("expertUserName"); //专家用户名查询关键字
    String name = (String)request.getAttribute("name"); //姓名查询关键字
    String birthDate = (String)request.getAttribute("birthDate"); //出生日期查询关键字
    String zhicheng = (String)request.getAttribute("zhicheng"); //职称查询关键字
    String telephone = (String)request.getAttribute("telephone"); //联系电话查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>专家查询</title>
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
  			<li><a href="<%=basePath %>Expert/frontlist">专家信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Expert/expert_frontAdd.jsp" style="display:none;">添加专家</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<expertList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Expert expert = expertList.get(i); //获取到专家对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>Expert/<%=expert.getExpertUserName() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=expert.getExpertPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		专家用户名:<%=expert.getExpertUserName() %>
			     	</div>
			     	<div class="field">
	            		姓名:<%=expert.getName() %>
			     	</div>
			     	<div class="field">
	            		性别:<%=expert.getGender() %>
			     	</div>
			     	<div class="field">
	            		出生日期:<%=expert.getBirthDate() %>
			     	</div>
			     	<div class="field">
	            		职称:<%=expert.getZhicheng() %>
			     	</div>
			     	<div class="field">
	            		联系电话:<%=expert.getTelephone() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Expert/<%=expert.getExpertUserName() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="expertEdit('<%=expert.getExpertUserName() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="expertDelete('<%=expert.getExpertUserName() %>');" style="display:none;">删除</a>
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
    		<h1>专家查询</h1>
		</div>
		<form name="expertQueryForm" id="expertQueryForm" action="<%=basePath %>Expert/frontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="expertUserName">专家用户名:</label>
				<input type="text" id="expertUserName" name="expertUserName" value="<%=expertUserName %>" class="form-control" placeholder="请输入专家用户名">
			</div>
			<div class="form-group">
				<label for="name">姓名:</label>
				<input type="text" id="name" name="name" value="<%=name %>" class="form-control" placeholder="请输入姓名">
			</div>
			<div class="form-group">
				<label for="birthDate">出生日期:</label>
				<input type="text" id="birthDate" name="birthDate" class="form-control"  placeholder="请选择出生日期" value="<%=birthDate %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
			<div class="form-group">
				<label for="zhicheng">职称:</label>
				<input type="text" id="zhicheng" name="zhicheng" value="<%=zhicheng %>" class="form-control" placeholder="请输入职称">
			</div>
			<div class="form-group">
				<label for="telephone">联系电话:</label>
				<input type="text" id="telephone" name="telephone" value="<%=telephone %>" class="form-control" placeholder="请输入联系电话">
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="expertEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;专家信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="expertEditForm" id="expertEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="expert_expertUserName_edit" class="col-md-3 text-right">专家用户名:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="expert_expertUserName_edit" name="expert.expertUserName" class="form-control" placeholder="请输入专家用户名" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="expert_password_edit" class="col-md-3 text-right">登录密码:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="expert_password_edit" name="expert.password" class="form-control" placeholder="请输入登录密码">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="expert_name_edit" class="col-md-3 text-right">姓名:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="expert_name_edit" name="expert.name" class="form-control" placeholder="请输入姓名">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="expert_gender_edit" class="col-md-3 text-right">性别:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="expert_gender_edit" name="expert.gender" class="form-control" placeholder="请输入性别">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="expert_expertPhoto_edit" class="col-md-3 text-right">专家照片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="expert_expertPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="expert_expertPhoto" name="expert.expertPhoto"/>
			    <input id="expertPhotoFile" name="expertPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="expert_birthDate_edit" class="col-md-3 text-right">出生日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date expert_birthDate_edit col-md-12" data-link-field="expert_birthDate_edit" data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="expert_birthDate_edit" name="expert.birthDate" size="16" type="text" value="" placeholder="请选择出生日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="expert_zhicheng_edit" class="col-md-3 text-right">职称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="expert_zhicheng_edit" name="expert.zhicheng" class="form-control" placeholder="请输入职称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="expert_telephone_edit" class="col-md-3 text-right">联系电话:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="expert_telephone_edit" name="expert.telephone" class="form-control" placeholder="请输入联系电话">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="expert_address_edit" class="col-md-3 text-right">家庭地址:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="expert_address_edit" name="expert.address" class="form-control" placeholder="请输入家庭地址">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="expert_expertDesc_edit" class="col-md-3 text-right">专家介绍:</label>
		  	 <div class="col-md-9">
			 	<textarea name="expert.expertDesc" id="expert_expertDesc_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		</form> 
	    <style>#expertEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxExpertModify();">提交</button>
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
var expert_expertDesc_edit = UE.getEditor('expert_expertDesc_edit'); //专家介绍编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.expertQueryForm.currentPage.value = currentPage;
    document.expertQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.expertQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.expertQueryForm.currentPage.value = pageValue;
    documentexpertQueryForm.submit();
}

/*弹出修改专家界面并初始化数据*/
function expertEdit(expertUserName) {
	$.ajax({
		url :  basePath + "Expert/" + expertUserName + "/update",
		type : "get",
		dataType: "json",
		success : function (expert, response, status) {
			if (expert) {
				$("#expert_expertUserName_edit").val(expert.expertUserName);
				$("#expert_password_edit").val(expert.password);
				$("#expert_name_edit").val(expert.name);
				$("#expert_gender_edit").val(expert.gender);
				$("#expert_expertPhoto").val(expert.expertPhoto);
				$("#expert_expertPhotoImg").attr("src", basePath +　expert.expertPhoto);
				$("#expert_birthDate_edit").val(expert.birthDate);
				$("#expert_zhicheng_edit").val(expert.zhicheng);
				$("#expert_telephone_edit").val(expert.telephone);
				$("#expert_address_edit").val(expert.address);
				expert_expertDesc_edit.setContent(expert.expertDesc, false);
				$('#expertEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除专家信息*/
function expertDelete(expertUserName) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Expert/deletes",
			data : {
				expertUserNames : expertUserName,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#expertQueryForm").submit();
					//location.href= basePath + "Expert/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交专家信息表单给服务器端修改*/
function ajaxExpertModify() {
	$.ajax({
		url :  basePath + "Expert/" + $("#expert_expertUserName_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#expertEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#expertQueryForm").submit();
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

    /*出生日期组件*/
    $('.expert_birthDate_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd',
    	minView: 2,
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

