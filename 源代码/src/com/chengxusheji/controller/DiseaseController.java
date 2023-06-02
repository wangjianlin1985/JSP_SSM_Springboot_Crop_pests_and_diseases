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
import com.chengxusheji.service.DiseaseService;
import com.chengxusheji.po.Disease;
import com.chengxusheji.service.CropService;
import com.chengxusheji.po.Crop;

//Disease管理控制层
@Controller
@RequestMapping("/Disease")
public class DiseaseController extends BaseController {

    /*业务层对象*/
    @Resource DiseaseService diseaseService;

    @Resource CropService cropService;
	@InitBinder("cropObj")
	public void initBindercropObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("cropObj.");
	}
	@InitBinder("disease")
	public void initBinderDisease(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("disease.");
	}
	/*跳转到添加Disease视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Disease());
		/*查询所有的Crop信息*/
		List<Crop> cropList = cropService.queryAllCrop();
		request.setAttribute("cropList", cropList);
		return "Disease_add";
	}

	/*客户端ajax方式提交添加病害信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Disease disease, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			disease.setDiseasePhoto(this.handlePhotoUpload(request, "diseasePhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        diseaseService.addDisease(disease);
        message = "病害添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询病害信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("cropObj") Crop cropObj,String diseaseName,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (diseaseName == null) diseaseName = "";
		if (addTime == null) addTime = "";
		if(rows != 0)diseaseService.setRows(rows);
		List<Disease> diseaseList = diseaseService.queryDisease(cropObj, diseaseName, addTime, page);
	    /*计算总的页数和总的记录数*/
	    diseaseService.queryTotalPageAndRecordNumber(cropObj, diseaseName, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = diseaseService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = diseaseService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Disease disease:diseaseList) {
			JSONObject jsonDisease = disease.getJsonObject();
			jsonArray.put(jsonDisease);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询病害信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Disease> diseaseList = diseaseService.queryAllDisease();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Disease disease:diseaseList) {
			JSONObject jsonDisease = new JSONObject();
			jsonDisease.accumulate("diseaseId", disease.getDiseaseId());
			jsonDisease.accumulate("diseaseName", disease.getDiseaseName());
			jsonArray.put(jsonDisease);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询病害信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("cropObj") Crop cropObj,String diseaseName,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (diseaseName == null) diseaseName = "";
		if (addTime == null) addTime = "";
		List<Disease> diseaseList = diseaseService.queryDisease(cropObj, diseaseName, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    diseaseService.queryTotalPageAndRecordNumber(cropObj, diseaseName, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = diseaseService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = diseaseService.getRecordNumber();
	    request.setAttribute("diseaseList",  diseaseList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("cropObj", cropObj);
	    request.setAttribute("diseaseName", diseaseName);
	    request.setAttribute("addTime", addTime);
	    List<Crop> cropList = cropService.queryAllCrop();
	    request.setAttribute("cropList", cropList);
		return "Disease/disease_frontquery_result"; 
	}

     /*前台查询Disease信息*/
	@RequestMapping(value="/{diseaseId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer diseaseId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键diseaseId获取Disease对象*/
        Disease disease = diseaseService.getDisease(diseaseId);

        List<Crop> cropList = cropService.queryAllCrop();
        request.setAttribute("cropList", cropList);
        request.setAttribute("disease",  disease);
        return "Disease/disease_frontshow";
	}

	/*ajax方式显示病害修改jsp视图页*/
	@RequestMapping(value="/{diseaseId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer diseaseId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键diseaseId获取Disease对象*/
        Disease disease = diseaseService.getDisease(diseaseId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonDisease = disease.getJsonObject();
		out.println(jsonDisease.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新病害信息*/
	@RequestMapping(value = "/{diseaseId}/update", method = RequestMethod.POST)
	public void update(@Validated Disease disease, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String diseasePhotoFileName = this.handlePhotoUpload(request, "diseasePhotoFile");
		if(!diseasePhotoFileName.equals("upload/NoImage.jpg"))disease.setDiseasePhoto(diseasePhotoFileName); 


		try {
			diseaseService.updateDisease(disease);
			message = "病害更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "病害更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除病害信息*/
	@RequestMapping(value="/{diseaseId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer diseaseId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  diseaseService.deleteDisease(diseaseId);
	            request.setAttribute("message", "病害删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "病害删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条病害记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String diseaseIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = diseaseService.deleteDiseases(diseaseIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出病害信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("cropObj") Crop cropObj,String diseaseName,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(diseaseName == null) diseaseName = "";
        if(addTime == null) addTime = "";
        List<Disease> diseaseList = diseaseService.queryDisease(cropObj,diseaseName,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Disease信息记录"; 
        String[] headers = { "病害id","农作物","病害名称","病害图片","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<diseaseList.size();i++) {
        	Disease disease = diseaseList.get(i); 
        	dataset.add(new String[]{disease.getDiseaseId() + "",disease.getCropObj().getCropName(),disease.getDiseaseName(),disease.getDiseasePhoto(),disease.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Disease.xls");//filename是下载的xls的名，建议最好用英文 
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
