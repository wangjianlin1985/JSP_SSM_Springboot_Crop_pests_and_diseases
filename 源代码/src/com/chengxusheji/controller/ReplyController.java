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
import com.chengxusheji.service.ReplyService;
import com.chengxusheji.po.Reply;
import com.chengxusheji.service.PostInfoService;
import com.chengxusheji.po.PostInfo;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//Reply管理控制层
@Controller
@RequestMapping("/Reply")
public class ReplyController extends BaseController {

    /*业务层对象*/
    @Resource ReplyService replyService;

    @Resource PostInfoService postInfoService;
    @Resource UserInfoService userInfoService;
	@InitBinder("postInfoObj")
	public void initBinderpostInfoObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("postInfoObj.");
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("reply")
	public void initBinderReply(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("reply.");
	}
	/*跳转到添加Reply视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Reply());
		/*查询所有的PostInfo信息*/
		List<PostInfo> postInfoList = postInfoService.queryAllPostInfo();
		request.setAttribute("postInfoList", postInfoList);
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "Reply_add";
	}

	/*客户端ajax方式提交添加帖子回复信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Reply reply, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        replyService.addReply(reply);
        message = "帖子回复添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	/*客户端ajax方式提交添加帖子回复信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public void userAdd(Reply reply, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		 
		String user_name = (String) session.getAttribute("user_name");
		if(null == user_name) {
			message = "请先登录网站！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(user_name);
		reply.setUserObj(userObj);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		reply.setReplyTime(sdf.format(new java.util.Date()));
		 
        replyService.addReply(reply);
        message = "帖子回复添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*ajax方式按照查询条件分页查询帖子回复信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("postInfoObj") PostInfo postInfoObj,@ModelAttribute("userObj") UserInfo userObj,String replyTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (replyTime == null) replyTime = "";
		if(rows != 0)replyService.setRows(rows);
		List<Reply> replyList = replyService.queryReply(postInfoObj, userObj, replyTime, page);
	    /*计算总的页数和总的记录数*/
	    replyService.queryTotalPageAndRecordNumber(postInfoObj, userObj, replyTime);
	    /*获取到总的页码数目*/
	    int totalPage = replyService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = replyService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Reply reply:replyList) {
			JSONObject jsonReply = reply.getJsonObject();
			jsonArray.put(jsonReply);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询帖子回复信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Reply> replyList = replyService.queryAllReply();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Reply reply:replyList) {
			JSONObject jsonReply = new JSONObject();
			jsonReply.accumulate("replyId", reply.getReplyId());
			jsonArray.put(jsonReply);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询帖子回复信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("postInfoObj") PostInfo postInfoObj,@ModelAttribute("userObj") UserInfo userObj,String replyTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (replyTime == null) replyTime = "";
		List<Reply> replyList = replyService.queryReply(postInfoObj, userObj, replyTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    replyService.queryTotalPageAndRecordNumber(postInfoObj, userObj, replyTime);
	    /*获取到总的页码数目*/
	    int totalPage = replyService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = replyService.getRecordNumber();
	    request.setAttribute("replyList",  replyList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("postInfoObj", postInfoObj);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("replyTime", replyTime);
	    List<PostInfo> postInfoList = postInfoService.queryAllPostInfo();
	    request.setAttribute("postInfoList", postInfoList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Reply/reply_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询帖子回复信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(@ModelAttribute("postInfoObj") PostInfo postInfoObj,@ModelAttribute("userObj") UserInfo userObj,String replyTime,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (replyTime == null) replyTime = "";
		userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		
		List<Reply> replyList = replyService.queryReply(postInfoObj, userObj, replyTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    replyService.queryTotalPageAndRecordNumber(postInfoObj, userObj, replyTime);
	    /*获取到总的页码数目*/
	    int totalPage = replyService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = replyService.getRecordNumber();
	    request.setAttribute("replyList",  replyList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("postInfoObj", postInfoObj);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("replyTime", replyTime);
	    List<PostInfo> postInfoList = postInfoService.queryAllPostInfo();
	    request.setAttribute("postInfoList", postInfoList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Reply/reply_userFrontquery_result"; 
	}
	

     /*前台查询Reply信息*/
	@RequestMapping(value="/{replyId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer replyId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键replyId获取Reply对象*/
        Reply reply = replyService.getReply(replyId);

        List<PostInfo> postInfoList = postInfoService.queryAllPostInfo();
        request.setAttribute("postInfoList", postInfoList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("reply",  reply);
        return "Reply/reply_frontshow";
	}

	/*ajax方式显示帖子回复修改jsp视图页*/
	@RequestMapping(value="/{replyId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer replyId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键replyId获取Reply对象*/
        Reply reply = replyService.getReply(replyId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonReply = reply.getJsonObject();
		out.println(jsonReply.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新帖子回复信息*/
	@RequestMapping(value = "/{replyId}/update", method = RequestMethod.POST)
	public void update(@Validated Reply reply, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			replyService.updateReply(reply);
			message = "帖子回复更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "帖子回复更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除帖子回复信息*/
	@RequestMapping(value="/{replyId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer replyId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  replyService.deleteReply(replyId);
	            request.setAttribute("message", "帖子回复删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "帖子回复删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条帖子回复记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String replyIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = replyService.deleteReplys(replyIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出帖子回复信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("postInfoObj") PostInfo postInfoObj,@ModelAttribute("userObj") UserInfo userObj,String replyTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(replyTime == null) replyTime = "";
        List<Reply> replyList = replyService.queryReply(postInfoObj,userObj,replyTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Reply信息记录"; 
        String[] headers = { "回复id","被回帖子","回复内容","回复人","回复时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<replyList.size();i++) {
        	Reply reply = replyList.get(i); 
        	dataset.add(new String[]{reply.getReplyId() + "",reply.getPostInfoObj().getTitle(),reply.getContent(),reply.getUserObj().getName(),reply.getReplyTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Reply.xls");//filename是下载的xls的名，建议最好用英文 
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
