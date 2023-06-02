<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Zhenduan" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Zhenduan> zhenduanList = (List<Zhenduan>)request.getAttribute("zhenduanList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String zhengzhuang = (String)request.getAttribute("zhengzhuang"); //症状标题查询关键字
    String bingyin = (String)request.getAttribute("bingyin"); //病因查询关键字
    String tezheng = (String)request.getAttribute("tezheng"); //特征查询关键字
    String addTime = (String)request.getAttribute("addTime"); //发布时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>诊断查询</title>
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
  			<li><a href="<%=basePath %>Zhenduan/frontlist">诊断信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Zhenduan/zhenduan_frontAdd.jsp" style="display:none;">添加诊断</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<zhenduanList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Zhenduan zhenduan = zhenduanList.get(i); //获取到诊断对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>Zhenduan/<%=zhenduan.getZhenduanId() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=zhenduan.getZhenzhuangPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		诊断id:<%=zhenduan.getZhenduanId() %>
			     	</div>
			     	<div class="field">
	            		症状标题:<%=zhenduan.getZhengzhuang() %>
			     	</div>
			     	<!-- 
			     	<div class="field">
	            		病因:<%=zhenduan.getBingyin() %>
			     	</div>
			     	<div class="field">
	            		特征:<%=zhenduan.getTezheng() %>
			     	</div> -->
			       
			     	<div class="field">
	            		发布时间:<%=zhenduan.getAddTime() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Zhenduan/<%=zhenduan.getZhenduanId() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="zhenduanEdit('<%=zhenduan.getZhenduanId() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="zhenduanDelete('<%=zhenduan.getZhenduanId() %>');" style="display:none;">删除</a>
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
    		<h1>诊断查询</h1>
		</div>
		<form name="zhenduanQueryForm" id="zhenduanQueryForm" action="<%=basePath %>Zhenduan/frontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="zhengzhuang">症状标题:</label>
				<input type="text" id="zhengzhuang" name="zhengzhuang" value="<%=zhengzhuang %>" class="form-control" placeholder="请输入症状标题">
			</div>
			<div class="form-group">
				<label for="bingyin">病因:</label>
				<input type="text" id="bingyin" name="bingyin" value="<%=bingyin %>" class="form-control" placeholder="请输入病因">
			</div>
			<div class="form-group">
				<label for="tezheng">特征:</label>
				<input type="text" id="tezheng" name="tezheng" value="<%=tezheng %>" class="form-control" placeholder="请输入特征">
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
<div id="zhenduanEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;诊断信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="zhenduanEditForm" id="zhenduanEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="zhenduan_zhenduanId_edit" class="col-md-3 text-right">诊断id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="zhenduan_zhenduanId_edit" name="zhenduan.zhenduanId" class="form-control" placeholder="请输入诊断id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="zhenduan_zhengzhuang_edit" class="col-md-3 text-right">症状标题:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="zhenduan_zhengzhuang_edit" name="zhenduan.zhengzhuang" class="form-control" placeholder="请输入症状标题">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="zhenduan_zhenzhuangPhoto_edit" class="col-md-3 text-right">症状图片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="zhenduan_zhenzhuangPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="zhenduan_zhenzhuangPhoto" name="zhenduan.zhenzhuangPhoto"/>
			    <input id="zhenzhuangPhotoFile" name="zhenzhuangPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="zhenduan_bingyin_edit" class="col-md-3 text-right">病因:</label>
		  	 <div class="col-md-9">
			 	<textarea name="zhenduan.bingyin" id="zhenduan_bingyin_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="zhenduan_tezheng_edit" class="col-md-3 text-right">特征:</label>
		  	 <div class="col-md-9">
			 	<textarea name="zhenduan.tezheng" id="zhenduan_tezheng_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="zhenduan_fbtj_edit" class="col-md-3 text-right">发病条件:</label>
		  	 <div class="col-md-9">
			 	<textarea name="zhenduan.fbtj" id="zhenduan_fbtj_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="zhenduan_nyyf_edit" class="col-md-3 text-right">农业预防:</label>
		  	 <div class="col-md-9">
			 	<textarea name="zhenduan.nyyf" id="zhenduan_nyyf_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="zhenduan_zlff_edit" class="col-md-3 text-right">治疗方法:</label>
		  	 <div class="col-md-9">
			 	<textarea name="zhenduan.zlff" id="zhenduan_zlff_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="zhenduan_addTime_edit" class="col-md-3 text-right">发布时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date zhenduan_addTime_edit col-md-12" data-link-field="zhenduan_addTime_edit">
                    <input class="form-control" id="zhenduan_addTime_edit" name="zhenduan.addTime" size="16" type="text" value="" placeholder="请选择发布时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		</form> 
	    <style>#zhenduanEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxZhenduanModify();">提交</button>
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
var zhenduan_bingyin_edit = UE.getEditor('zhenduan_bingyin_edit'); //病因编辑器
var zhenduan_tezheng_edit = UE.getEditor('zhenduan_tezheng_edit'); //特征编辑器
var zhenduan_fbtj_edit = UE.getEditor('zhenduan_fbtj_edit'); //发病条件编辑器
var zhenduan_nyyf_edit = UE.getEditor('zhenduan_nyyf_edit'); //农业预防编辑器
var zhenduan_zlff_edit = UE.getEditor('zhenduan_zlff_edit'); //治疗方法编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.zhenduanQueryForm.currentPage.value = currentPage;
    document.zhenduanQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.zhenduanQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.zhenduanQueryForm.currentPage.value = pageValue;
    documentzhenduanQueryForm.submit();
}

/*弹出修改诊断界面并初始化数据*/
function zhenduanEdit(zhenduanId) {
	$.ajax({
		url :  basePath + "Zhenduan/" + zhenduanId + "/update",
		type : "get",
		dataType: "json",
		success : function (zhenduan, response, status) {
			if (zhenduan) {
				$("#zhenduan_zhenduanId_edit").val(zhenduan.zhenduanId);
				$("#zhenduan_zhengzhuang_edit").val(zhenduan.zhengzhuang);
				$("#zhenduan_zhenzhuangPhoto").val(zhenduan.zhenzhuangPhoto);
				$("#zhenduan_zhenzhuangPhotoImg").attr("src", basePath +　zhenduan.zhenzhuangPhoto);
				zhenduan_bingyin_edit.setContent(zhenduan.bingyin, false);
				zhenduan_tezheng_edit.setContent(zhenduan.tezheng, false);
				zhenduan_fbtj_edit.setContent(zhenduan.fbtj, false);
				zhenduan_nyyf_edit.setContent(zhenduan.nyyf, false);
				zhenduan_zlff_edit.setContent(zhenduan.zlff, false);
				$("#zhenduan_addTime_edit").val(zhenduan.addTime);
				$('#zhenduanEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除诊断信息*/
function zhenduanDelete(zhenduanId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Zhenduan/deletes",
			data : {
				zhenduanIds : zhenduanId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#zhenduanQueryForm").submit();
					//location.href= basePath + "Zhenduan/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交诊断信息表单给服务器端修改*/
function ajaxZhenduanModify() {
	$.ajax({
		url :  basePath + "Zhenduan/" + $("#zhenduan_zhenduanId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#zhenduanEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#zhenduanQueryForm").submit();
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
    $('.zhenduan_addTime_edit').datetimepicker({
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

