package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.PostInfoService;
import com.chengxusheji.service.ReplyService;
import com.chengxusheji.po.PostInfo;
import com.chengxusheji.po.Reply;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//PostInfo管理控制层
@Controller
@RequestMapping("/PostInfo")
public class PostInfoController extends BaseController {

    /*业务层对象*/
    @Resource PostInfoService postInfoService;
    @Resource ReplyService replyService;

    @Resource UserInfoService userInfoService;
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("postInfo")
	public void initBinderPostInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("postInfo.");
	}
	/*跳转到添加PostInfo视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new PostInfo());
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "PostInfo_add";
	}

	/*客户端ajax方式提交添加帖子信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated PostInfo postInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        postInfoService.addPostInfo(postInfo);
        message = "帖子添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加帖子信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public void userAdd(PostInfo postInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
	 
		
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		postInfo.setUserObj(userObj);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		postInfo.setAddTime(sdf.format(new java.util.Date()));
		
        postInfoService.addPostInfo(postInfo);
        message = "帖子添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	
	/*ajax方式按照查询条件分页查询帖子信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String title,@ModelAttribute("userObj") UserInfo userObj,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (title == null) title = "";
		if (addTime == null) addTime = "";
		if(rows != 0)postInfoService.setRows(rows);
		List<PostInfo> postInfoList = postInfoService.queryPostInfo(title, userObj, addTime, page);
	    /*计算总的页数和总的记录数*/
	    postInfoService.queryTotalPageAndRecordNumber(title, userObj, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = postInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = postInfoService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(PostInfo postInfo:postInfoList) {
			JSONObject jsonPostInfo = postInfo.getJsonObject();
			jsonArray.put(jsonPostInfo);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询帖子信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<PostInfo> postInfoList = postInfoService.queryAllPostInfo();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(PostInfo postInfo:postInfoList) {
			JSONObject jsonPostInfo = new JSONObject();
			jsonPostInfo.accumulate("postInfoId", postInfo.getPostInfoId());
			jsonPostInfo.accumulate("title", postInfo.getTitle());
			jsonArray.put(jsonPostInfo);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询帖子信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String title,@ModelAttribute("userObj") UserInfo userObj,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (addTime == null) addTime = "";
		List<PostInfo> postInfoList = postInfoService.queryPostInfo(title, userObj, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    postInfoService.queryTotalPageAndRecordNumber(title, userObj, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = postInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = postInfoService.getRecordNumber();
	    request.setAttribute("postInfoList",  postInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("title", title);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("addTime", addTime);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "PostInfo/postInfo_frontquery_result"; 
	}

	
	/*前台按照查询条件分页查询帖子信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(String title,@ModelAttribute("userObj") UserInfo userObj,String addTime,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (addTime == null) addTime = "";
		
		userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		
		List<PostInfo> postInfoList = postInfoService.queryPostInfo(title, userObj, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    postInfoService.queryTotalPageAndRecordNumber(title, userObj, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = postInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = postInfoService.getRecordNumber();
	    request.setAttribute("postInfoList",  postInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("title", title);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("addTime", addTime);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "PostInfo/postInfo_userFrontquery_result"; 
	}
	
	
     /*前台查询PostInfo信息*/
	@RequestMapping(value="/{postInfoId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer postInfoId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键postInfoId获取PostInfo对象*/
        PostInfo postInfo = postInfoService.getPostInfo(postInfoId);
        postInfo.setHitNum(postInfo.getHitNum() + 1);
        postInfoService.updatePostInfo(postInfo);
        
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("postInfo",  postInfo);
        
        ArrayList<Reply> replyList = replyService.queryReply(postInfo, null, "");
        request.setAttribute("replyList", replyList);
        
        return "PostInfo/postInfo_frontshow";
	}

	/*ajax方式显示帖子修改jsp视图页*/
	@RequestMapping(value="/{postInfoId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer postInfoId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键postInfoId获取PostInfo对象*/
        PostInfo postInfo = postInfoService.getPostInfo(postInfoId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonPostInfo = postInfo.getJsonObject();
		out.println(jsonPostInfo.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新帖子信息*/
	@RequestMapping(value = "/{postInfoId}/update", method = RequestMethod.POST)
	public void update(@Validated PostInfo postInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			postInfoService.updatePostInfo(postInfo);
			message = "帖子更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "帖子更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除帖子信息*/
	@RequestMapping(value="/{postInfoId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer postInfoId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  postInfoService.deletePostInfo(postInfoId);
	            request.setAttribute("message", "帖子删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "帖子删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条帖子记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String postInfoIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = postInfoService.deletePostInfos(postInfoIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出帖子信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String title,@ModelAttribute("userObj") UserInfo userObj,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(title == null) title = "";
        if(addTime == null) addTime = "";
        List<PostInfo> postInfoList = postInfoService.queryPostInfo(title,userObj,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "PostInfo信息记录"; 
        String[] headers = { "帖子id","帖子标题","浏览量","发帖人","发帖时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<postInfoList.size();i++) {
        	PostInfo postInfo = postInfoList.get(i); 
        	dataset.add(new String[]{postInfo.getPostInfoId() + "",postInfo.getTitle(),postInfo.getHitNum() + "",postInfo.getUserObj().getName(),postInfo.getAddTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"PostInfo.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
