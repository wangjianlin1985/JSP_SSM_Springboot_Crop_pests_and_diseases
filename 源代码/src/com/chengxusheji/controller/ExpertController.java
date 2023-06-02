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
import com.chengxusheji.service.ExpertService;
import com.chengxusheji.po.Expert;

//Expert管理控制层
@Controller
@RequestMapping("/Expert")
public class ExpertController extends BaseController {

    /*业务层对象*/
    @Resource ExpertService expertService;

	@InitBinder("expert")
	public void initBinderExpert(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("expert.");
	}
	/*跳转到添加Expert视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Expert());
		return "Expert_add";
	}

	/*客户端ajax方式提交添加专家信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Expert expert, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		if(expertService.getExpert(expert.getExpertUserName()) != null) {
			message = "专家用户名已经存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			expert.setExpertPhoto(this.handlePhotoUpload(request, "expertPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        expertService.addExpert(expert);
        message = "专家添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询专家信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String expertUserName,String name,String birthDate,String zhicheng,String telephone,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (expertUserName == null) expertUserName = "";
		if (name == null) name = "";
		if (birthDate == null) birthDate = "";
		if (zhicheng == null) zhicheng = "";
		if (telephone == null) telephone = "";
		if(rows != 0)expertService.setRows(rows);
		List<Expert> expertList = expertService.queryExpert(expertUserName, name, birthDate, zhicheng, telephone, page);
	    /*计算总的页数和总的记录数*/
	    expertService.queryTotalPageAndRecordNumber(expertUserName, name, birthDate, zhicheng, telephone);
	    /*获取到总的页码数目*/
	    int totalPage = expertService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = expertService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Expert expert:expertList) {
			JSONObject jsonExpert = expert.getJsonObject();
			jsonArray.put(jsonExpert);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询专家信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Expert> expertList = expertService.queryAllExpert();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Expert expert:expertList) {
			JSONObject jsonExpert = new JSONObject();
			jsonExpert.accumulate("expertUserName", expert.getExpertUserName());
			jsonExpert.accumulate("name", expert.getName());
			jsonArray.put(jsonExpert);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询专家信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String expertUserName,String name,String birthDate,String zhicheng,String telephone,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (expertUserName == null) expertUserName = "";
		if (name == null) name = "";
		if (birthDate == null) birthDate = "";
		if (zhicheng == null) zhicheng = "";
		if (telephone == null) telephone = "";
		List<Expert> expertList = expertService.queryExpert(expertUserName, name, birthDate, zhicheng, telephone, currentPage);
	    /*计算总的页数和总的记录数*/
	    expertService.queryTotalPageAndRecordNumber(expertUserName, name, birthDate, zhicheng, telephone);
	    /*获取到总的页码数目*/
	    int totalPage = expertService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = expertService.getRecordNumber();
	    request.setAttribute("expertList",  expertList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("expertUserName", expertUserName);
	    request.setAttribute("name", name);
	    request.setAttribute("birthDate", birthDate);
	    request.setAttribute("zhicheng", zhicheng);
	    request.setAttribute("telephone", telephone);
		return "Expert/expert_frontquery_result"; 
	}

     /*前台查询Expert信息*/
	@RequestMapping(value="/{expertUserName}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable String expertUserName,Model model,HttpServletRequest request) throws Exception {
		/*根据主键expertUserName获取Expert对象*/
        Expert expert = expertService.getExpert(expertUserName);

        request.setAttribute("expert",  expert);
        return "Expert/expert_frontshow";
	}

	/*ajax方式显示专家修改jsp视图页*/
	@RequestMapping(value="/{expertUserName}/update",method=RequestMethod.GET)
	public void update(@PathVariable String expertUserName,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键expertUserName获取Expert对象*/
        Expert expert = expertService.getExpert(expertUserName);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonExpert = expert.getJsonObject();
		out.println(jsonExpert.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新专家信息*/
	@RequestMapping(value = "/{expertUserName}/update", method = RequestMethod.POST)
	public void update(@Validated Expert expert, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String expertPhotoFileName = this.handlePhotoUpload(request, "expertPhotoFile");
		if(!expertPhotoFileName.equals("upload/NoImage.jpg"))expert.setExpertPhoto(expertPhotoFileName); 


		try {
			expertService.updateExpert(expert);
			message = "专家更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "专家更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除专家信息*/
	@RequestMapping(value="/{expertUserName}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String expertUserName,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  expertService.deleteExpert(expertUserName);
	            request.setAttribute("message", "专家删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "专家删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条专家记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String expertUserNames,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = expertService.deleteExperts(expertUserNames);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出专家信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String expertUserName,String name,String birthDate,String zhicheng,String telephone, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(expertUserName == null) expertUserName = "";
        if(name == null) name = "";
        if(birthDate == null) birthDate = "";
        if(zhicheng == null) zhicheng = "";
        if(telephone == null) telephone = "";
        List<Expert> expertList = expertService.queryExpert(expertUserName,name,birthDate,zhicheng,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Expert信息记录"; 
        String[] headers = { "专家用户名","姓名","性别","专家照片","出生日期","职称","联系电话"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<expertList.size();i++) {
        	Expert expert = expertList.get(i); 
        	dataset.add(new String[]{expert.getExpertUserName(),expert.getName(),expert.getGender(),expert.getExpertPhoto(),expert.getBirthDate(),expert.getZhicheng(),expert.getTelephone()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Expert.xls");//filename是下载的xls的名，建议最好用英文 
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
