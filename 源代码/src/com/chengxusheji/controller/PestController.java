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
import com.chengxusheji.service.PestService;
import com.chengxusheji.po.Pest;
import com.chengxusheji.service.CropService;
import com.chengxusheji.po.Crop;

//Pest管理控制层
@Controller
@RequestMapping("/Pest")
public class PestController extends BaseController {

    /*业务层对象*/
    @Resource PestService pestService;

    @Resource CropService cropService;
	@InitBinder("cropObj")
	public void initBindercropObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("cropObj.");
	}
	@InitBinder("pest")
	public void initBinderPest(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("pest.");
	}
	/*跳转到添加Pest视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Pest());
		/*查询所有的Crop信息*/
		List<Crop> cropList = cropService.queryAllCrop();
		request.setAttribute("cropList", cropList);
		return "Pest_add";
	}

	/*客户端ajax方式提交添加虫害信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Pest pest, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			pest.setPestPhoto(this.handlePhotoUpload(request, "pestPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        pestService.addPest(pest);
        message = "虫害添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询虫害信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("cropObj") Crop cropObj,String pestName,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (pestName == null) pestName = "";
		if (addTime == null) addTime = "";
		if(rows != 0)pestService.setRows(rows);
		List<Pest> pestList = pestService.queryPest(cropObj, pestName, addTime, page);
	    /*计算总的页数和总的记录数*/
	    pestService.queryTotalPageAndRecordNumber(cropObj, pestName, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = pestService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = pestService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Pest pest:pestList) {
			JSONObject jsonPest = pest.getJsonObject();
			jsonArray.put(jsonPest);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询虫害信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Pest> pestList = pestService.queryAllPest();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Pest pest:pestList) {
			JSONObject jsonPest = new JSONObject();
			jsonPest.accumulate("pestId", pest.getPestId());
			jsonPest.accumulate("pestName", pest.getPestName());
			jsonArray.put(jsonPest);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询虫害信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("cropObj") Crop cropObj,String pestName,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (pestName == null) pestName = "";
		if (addTime == null) addTime = "";
		List<Pest> pestList = pestService.queryPest(cropObj, pestName, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    pestService.queryTotalPageAndRecordNumber(cropObj, pestName, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = pestService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = pestService.getRecordNumber();
	    request.setAttribute("pestList",  pestList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("cropObj", cropObj);
	    request.setAttribute("pestName", pestName);
	    request.setAttribute("addTime", addTime);
	    List<Crop> cropList = cropService.queryAllCrop();
	    request.setAttribute("cropList", cropList);
		return "Pest/pest_frontquery_result"; 
	}

     /*前台查询Pest信息*/
	@RequestMapping(value="/{pestId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer pestId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键pestId获取Pest对象*/
        Pest pest = pestService.getPest(pestId);

        List<Crop> cropList = cropService.queryAllCrop();
        request.setAttribute("cropList", cropList);
        request.setAttribute("pest",  pest);
        return "Pest/pest_frontshow";
	}

	/*ajax方式显示虫害修改jsp视图页*/
	@RequestMapping(value="/{pestId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer pestId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键pestId获取Pest对象*/
        Pest pest = pestService.getPest(pestId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonPest = pest.getJsonObject();
		out.println(jsonPest.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新虫害信息*/
	@RequestMapping(value = "/{pestId}/update", method = RequestMethod.POST)
	public void update(@Validated Pest pest, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String pestPhotoFileName = this.handlePhotoUpload(request, "pestPhotoFile");
		if(!pestPhotoFileName.equals("upload/NoImage.jpg"))pest.setPestPhoto(pestPhotoFileName); 


		try {
			pestService.updatePest(pest);
			message = "虫害更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "虫害更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除虫害信息*/
	@RequestMapping(value="/{pestId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer pestId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  pestService.deletePest(pestId);
	            request.setAttribute("message", "虫害删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "虫害删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条虫害记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String pestIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = pestService.deletePests(pestIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出虫害信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("cropObj") Crop cropObj,String pestName,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(pestName == null) pestName = "";
        if(addTime == null) addTime = "";
        List<Pest> pestList = pestService.queryPest(cropObj,pestName,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Pest信息记录"; 
        String[] headers = { "虫害id","农作物","虫害名称","虫害图片","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<pestList.size();i++) {
        	Pest pest = pestList.get(i); 
        	dataset.add(new String[]{pest.getPestId() + "",pest.getCropObj().getCropName(),pest.getPestName(),pest.getPestPhoto(),pest.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Pest.xls");//filename是下载的xls的名，建议最好用英文 
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
