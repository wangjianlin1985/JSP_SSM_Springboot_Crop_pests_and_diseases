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
import com.chengxusheji.service.WxqkService;
import com.chengxusheji.po.Wxqk;

//Wxqk管理控制层
@Controller
@RequestMapping("/Wxqk")
public class WxqkController extends BaseController {

    /*业务层对象*/
    @Resource WxqkService wxqkService;

	@InitBinder("wxqk")
	public void initBinderWxqk(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("wxqk.");
	}
	/*跳转到添加Wxqk视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Wxqk());
		return "Wxqk_add";
	}

	/*客户端ajax方式提交添加文献期刊信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Wxqk wxqk, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			wxqk.setWxqkPhoto(this.handlePhotoUpload(request, "wxqkPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
		wxqk.setWxqkFile(this.handleFileUpload(request, "wxqkFileFile"));
        wxqkService.addWxqk(wxqk);
        message = "文献期刊添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询文献期刊信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String wxqkType,String xueke,String title,String author,String km,String daoshi,String publishDate,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (wxqkType == null) wxqkType = "";
		if (xueke == null) xueke = "";
		if (title == null) title = "";
		if (author == null) author = "";
		if (km == null) km = "";
		if (daoshi == null) daoshi = "";
		if (publishDate == null) publishDate = "";
		if(rows != 0)wxqkService.setRows(rows);
		List<Wxqk> wxqkList = wxqkService.queryWxqk(wxqkType, xueke, title, author, km, daoshi, publishDate, page);
	    /*计算总的页数和总的记录数*/
	    wxqkService.queryTotalPageAndRecordNumber(wxqkType, xueke, title, author, km, daoshi, publishDate);
	    /*获取到总的页码数目*/
	    int totalPage = wxqkService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = wxqkService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Wxqk wxqk:wxqkList) {
			JSONObject jsonWxqk = wxqk.getJsonObject();
			jsonArray.put(jsonWxqk);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询文献期刊信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Wxqk> wxqkList = wxqkService.queryAllWxqk();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Wxqk wxqk:wxqkList) {
			JSONObject jsonWxqk = new JSONObject();
			jsonWxqk.accumulate("qkId", wxqk.getQkId());
			jsonWxqk.accumulate("title", wxqk.getTitle());
			jsonArray.put(jsonWxqk);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询文献期刊信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String wxqkType,String xueke,String title,String author,String km,String daoshi,String publishDate,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (wxqkType == null) wxqkType = "";
		if (xueke == null) xueke = "";
		if (title == null) title = "";
		if (author == null) author = "";
		if (km == null) km = "";
		if (daoshi == null) daoshi = "";
		if (publishDate == null) publishDate = "";
		List<Wxqk> wxqkList = wxqkService.queryWxqk(wxqkType, xueke, title, author, km, daoshi, publishDate, currentPage);
	    /*计算总的页数和总的记录数*/
	    wxqkService.queryTotalPageAndRecordNumber(wxqkType, xueke, title, author, km, daoshi, publishDate);
	    /*获取到总的页码数目*/
	    int totalPage = wxqkService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = wxqkService.getRecordNumber();
	    request.setAttribute("wxqkList",  wxqkList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("wxqkType", wxqkType);
	    request.setAttribute("xueke", xueke);
	    request.setAttribute("title", title);
	    request.setAttribute("author", author);
	    request.setAttribute("km", km);
	    request.setAttribute("daoshi", daoshi);
	    request.setAttribute("publishDate", publishDate);
		return "Wxqk/wxqk_frontquery_result"; 
	}

     /*前台查询Wxqk信息*/
	@RequestMapping(value="/{qkId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer qkId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键qkId获取Wxqk对象*/
        Wxqk wxqk = wxqkService.getWxqk(qkId);

        request.setAttribute("wxqk",  wxqk);
        return "Wxqk/wxqk_frontshow";
	}

	/*ajax方式显示文献期刊修改jsp视图页*/
	@RequestMapping(value="/{qkId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer qkId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键qkId获取Wxqk对象*/
        Wxqk wxqk = wxqkService.getWxqk(qkId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonWxqk = wxqk.getJsonObject();
		out.println(jsonWxqk.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新文献期刊信息*/
	@RequestMapping(value = "/{qkId}/update", method = RequestMethod.POST)
	public void update(@Validated Wxqk wxqk, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String wxqkPhotoFileName = this.handlePhotoUpload(request, "wxqkPhotoFile");
		if(!wxqkPhotoFileName.equals("upload/NoImage.jpg"))wxqk.setWxqkPhoto(wxqkPhotoFileName); 


		String wxqkFileFileName = this.handleFileUpload(request, "wxqkFileFile");
		if(!wxqkFileFileName.equals(""))wxqk.setWxqkFile(wxqkFileFileName);
		try {
			wxqkService.updateWxqk(wxqk);
			message = "文献期刊更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "文献期刊更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除文献期刊信息*/
	@RequestMapping(value="/{qkId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer qkId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  wxqkService.deleteWxqk(qkId);
	            request.setAttribute("message", "文献期刊删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "文献期刊删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条文献期刊记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String qkIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = wxqkService.deleteWxqks(qkIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出文献期刊信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String wxqkType,String xueke,String title,String author,String km,String daoshi,String publishDate, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(wxqkType == null) wxqkType = "";
        if(xueke == null) xueke = "";
        if(title == null) title = "";
        if(author == null) author = "";
        if(km == null) km = "";
        if(daoshi == null) daoshi = "";
        if(publishDate == null) publishDate = "";
        List<Wxqk> wxqkList = wxqkService.queryWxqk(wxqkType,xueke,title,author,km,daoshi,publishDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Wxqk信息记录"; 
        String[] headers = { "记录id","文献期刊类别","学科","篇名","文献期刊图片","作者","刊名","关键词","导师","发布日期"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<wxqkList.size();i++) {
        	Wxqk wxqk = wxqkList.get(i); 
        	dataset.add(new String[]{wxqk.getQkId() + "",wxqk.getWxqkType(),wxqk.getXueke(),wxqk.getTitle(),wxqk.getWxqkPhoto(),wxqk.getAuthor(),wxqk.getKm(),wxqk.getKeywordInfo(),wxqk.getDaoshi(),wxqk.getPublishDate()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Wxqk.xls");//filename是下载的xls的名，建议最好用英文 
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
