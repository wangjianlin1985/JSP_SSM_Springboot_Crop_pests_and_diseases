<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Wxqk" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Wxqk> wxqkList = (List<Wxqk>)request.getAttribute("wxqkList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String wxqkType = (String)request.getAttribute("wxqkType"); //文献期刊类别查询关键字
    String xueke = (String)request.getAttribute("xueke"); //学科查询关键字
    String title = (String)request.getAttribute("title"); //篇名查询关键字
    String author = (String)request.getAttribute("author"); //作者查询关键字
    String km = (String)request.getAttribute("km"); //刊名查询关键字
    String daoshi = (String)request.getAttribute("daoshi"); //导师查询关键字
    String publishDate = (String)request.getAttribute("publishDate"); //发布日期查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>文献期刊查询</title>
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
  			<li><a href="<%=basePath %>Wxqk/frontlist">文献期刊信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Wxqk/wxqk_frontAdd.jsp" style="display:none;">添加文献期刊</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<wxqkList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Wxqk wxqk = wxqkList.get(i); //获取到文献期刊对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>Wxqk/<%=wxqk.getQkId() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=wxqk.getWxqkPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		记录id:<%=wxqk.getQkId() %>
			     	</div>
			     	<div class="field">
	            		文献期刊类别:<%=wxqk.getWxqkType() %>
			     	</div>
			     	<div class="field">
	            		学科:<%=wxqk.getXueke() %>
			     	</div>
			     	<div class="field">
	            		篇名:<%=wxqk.getTitle() %>
			     	</div>
			     	<div class="field">
	            		作者:<%=wxqk.getAuthor() %>
			     	</div>
			     	<div class="field">
	            		刊名:<%=wxqk.getKm() %>
			     	</div>
			     	<div class="field">
	            		关键词:<%=wxqk.getKeywordInfo() %>
			     	</div>
			     	<div class="field">
	            		导师:<%=wxqk.getDaoshi() %>
			     	</div>
			     	<div class="field">
	            		发布日期:<%=wxqk.getPublishDate() %>
			     	</div>
			     	<div class="field">
	            		文献期刊文件:<%=wxqk.getWxqkFile().equals("")?"暂无文件":"<a href='" + basePath + wxqk.getWxqkFile() + "' target='_blank'>" + wxqk.getWxqkFile() + "</a>"%>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Wxqk/<%=wxqk.getQkId() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="wxqkEdit('<%=wxqk.getQkId() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="wxqkDelete('<%=wxqk.getQkId() %>');" style="display:none;">删除</a>
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
    		<h1>文献期刊查询</h1>
		</div>
		<form name="wxqkQueryForm" id="wxqkQueryForm" action="<%=basePath %>Wxqk/frontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="wxqkType">文献期刊类别:</label>
				<input type="text" id="wxqkType" name="wxqkType" value="<%=wxqkType %>" class="form-control" placeholder="请输入文献期刊类别">
			</div>
			<div class="form-group">
				<label for="xueke">学科:</label>
				<input type="text" id="xueke" name="xueke" value="<%=xueke %>" class="form-control" placeholder="请输入学科">
			</div>
			<div class="form-group">
				<label for="title">篇名:</label>
				<input type="text" id="title" name="title" value="<%=title %>" class="form-control" placeholder="请输入篇名">
			</div>
			<div class="form-group">
				<label for="author">作者:</label>
				<input type="text" id="author" name="author" value="<%=author %>" class="form-control" placeholder="请输入作者">
			</div>
			<div class="form-group">
				<label for="km">刊名:</label>
				<input type="text" id="km" name="km" value="<%=km %>" class="form-control" placeholder="请输入刊名">
			</div>
			<div class="form-group">
				<label for="daoshi">导师:</label>
				<input type="text" id="daoshi" name="daoshi" value="<%=daoshi %>" class="form-control" placeholder="请输入导师">
			</div>
			<div class="form-group">
				<label for="publishDate">发布日期:</label>
				<input type="text" id="publishDate" name="publishDate" class="form-control"  placeholder="请选择发布日期" value="<%=publishDate %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="wxqkEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;文献期刊信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="wxqkEditForm" id="wxqkEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="wxqk_qkId_edit" class="col-md-3 text-right">记录id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="wxqk_qkId_edit" name="wxqk.qkId" class="form-control" placeholder="请输入记录id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="wxqk_wxqkType_edit" class="col-md-3 text-right">文献期刊类别:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="wxqk_wxqkType_edit" name="wxqk.wxqkType" class="form-control" placeholder="请输入文献期刊类别">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="wxqk_xueke_edit" class="col-md-3 text-right">学科:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="wxqk_xueke_edit" name="wxqk.xueke" class="form-control" placeholder="请输入学科">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="wxqk_title_edit" class="col-md-3 text-right">篇名:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="wxqk_title_edit" name="wxqk.title" class="form-control" placeholder="请输入篇名">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="wxqk_wxqkPhoto_edit" class="col-md-3 text-right">文献期刊图片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="wxqk_wxqkPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="wxqk_wxqkPhoto" name="wxqk.wxqkPhoto"/>
			    <input id="wxqkPhotoFile" name="wxqkPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="wxqk_author_edit" class="col-md-3 text-right">作者:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="wxqk_author_edit" name="wxqk.author" class="form-control" placeholder="请输入作者">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="wxqk_km_edit" class="col-md-3 text-right">刊名:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="wxqk_km_edit" name="wxqk.km" class="form-control" placeholder="请输入刊名">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="wxqk_keywordInfo_edit" class="col-md-3 text-right">关键词:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="wxqk_keywordInfo_edit" name="wxqk.keywordInfo" class="form-control" placeholder="请输入关键词">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="wxqk_zhaiyao_edit" class="col-md-3 text-right">摘要:</label>
		  	 <div class="col-md-9">
			    <textarea id="wxqk_zhaiyao_edit" name="wxqk.zhaiyao" rows="8" class="form-control" placeholder="请输入摘要"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="wxqk_daoshi_edit" class="col-md-3 text-right">导师:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="wxqk_daoshi_edit" name="wxqk.daoshi" class="form-control" placeholder="请输入导师">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="wxqk_publishDate_edit" class="col-md-3 text-right">发布日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date wxqk_publishDate_edit col-md-12" data-link-field="wxqk_publishDate_edit" data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="wxqk_publishDate_edit" name="wxqk.publishDate" size="16" type="text" value="" placeholder="请选择发布日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="wxqk_wxqkFile_edit" class="col-md-3 text-right">文献期刊文件:</label>
		  	 <div class="col-md-9">
			    <a id="wxqk_wxqkFileA" target="_blank"></a><br/>
			    <input type="hidden" id="wxqk_wxqkFile" name="wxqk.wxqkFile"/>
			    <input id="wxqkFileFile" name="wxqkFileFile" type="file" size="50" />
		  	 </div>
		  </div>
		</form> 
	    <style>#wxqkEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxWxqkModify();">提交</button>
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
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.wxqkQueryForm.currentPage.value = currentPage;
    document.wxqkQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.wxqkQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.wxqkQueryForm.currentPage.value = pageValue;
    documentwxqkQueryForm.submit();
}

/*弹出修改文献期刊界面并初始化数据*/
function wxqkEdit(qkId) {
	$.ajax({
		url :  basePath + "Wxqk/" + qkId + "/update",
		type : "get",
		dataType: "json",
		success : function (wxqk, response, status) {
			if (wxqk) {
				$("#wxqk_qkId_edit").val(wxqk.qkId);
				$("#wxqk_wxqkType_edit").val(wxqk.wxqkType);
				$("#wxqk_xueke_edit").val(wxqk.xueke);
				$("#wxqk_title_edit").val(wxqk.title);
				$("#wxqk_wxqkPhoto").val(wxqk.wxqkPhoto);
				$("#wxqk_wxqkPhotoImg").attr("src", basePath +　wxqk.wxqkPhoto);
				$("#wxqk_author_edit").val(wxqk.author);
				$("#wxqk_km_edit").val(wxqk.km);
				$("#wxqk_keywordInfo_edit").val(wxqk.keywordInfo);
				$("#wxqk_zhaiyao_edit").val(wxqk.zhaiyao);
				$("#wxqk_daoshi_edit").val(wxqk.daoshi);
				$("#wxqk_publishDate_edit").val(wxqk.publishDate);
				$("#wxqk_wxqkFile").val(wxqk.wxqkFile);
				$("#wxqk_wxqkFileA").text(wxqk.wxqkFile);
				$("#wxqk_wxqkFileA").attr("href", basePath +　wxqk.wxqkFile);
				$('#wxqkEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除文献期刊信息*/
function wxqkDelete(qkId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Wxqk/deletes",
			data : {
				qkIds : qkId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#wxqkQueryForm").submit();
					//location.href= basePath + "Wxqk/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交文献期刊信息表单给服务器端修改*/
function ajaxWxqkModify() {
	$.ajax({
		url :  basePath + "Wxqk/" + $("#wxqk_qkId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#wxqkEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#wxqkQueryForm").submit();
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

    /*发布日期组件*/
    $('.wxqk_publishDate_edit').datetimepicker({
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

