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
import com.chengxusheji.service.CropService;
import com.chengxusheji.po.Crop;
import com.chengxusheji.service.CropClassService;
import com.chengxusheji.po.CropClass;

//Crop管理控制层
@Controller
@RequestMapping("/Crop")
public class CropController extends BaseController {

    /*业务层对象*/
    @Resource CropService cropService;

    @Resource CropClassService cropClassService;
	@InitBinder("cropClassObj")
	public void initBindercropClassObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("cropClassObj.");
	}
	@InitBinder("crop")
	public void initBinderCrop(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("crop.");
	}
	/*跳转到添加Crop视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Crop());
		/*查询所有的CropClass信息*/
		List<CropClass> cropClassList = cropClassService.queryAllCropClass();
		request.setAttribute("cropClassList", cropClassList);
		return "Crop_add";
	}

	/*客户端ajax方式提交添加农作物信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Crop crop, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			crop.setCropPhoto(this.handlePhotoUpload(request, "cropPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        cropService.addCrop(crop);
        message = "农作物添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询农作物信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("cropClassObj") CropClass cropClassObj,String cropName,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (cropName == null) cropName = "";
		if (addTime == null) addTime = "";
		if(rows != 0)cropService.setRows(rows);
		List<Crop> cropList = cropService.queryCrop(cropClassObj, cropName, addTime, page);
	    /*计算总的页数和总的记录数*/
	    cropService.queryTotalPageAndRecordNumber(cropClassObj, cropName, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = cropService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = cropService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Crop crop:cropList) {
			JSONObject jsonCrop = crop.getJsonObject();
			jsonArray.put(jsonCrop);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询农作物信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Crop> cropList = cropService.queryAllCrop();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Crop crop:cropList) {
			JSONObject jsonCrop = new JSONObject();
			jsonCrop.accumulate("cropId", crop.getCropId());
			jsonCrop.accumulate("cropName", crop.getCropName());
			jsonArray.put(jsonCrop);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询农作物信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("cropClassObj") CropClass cropClassObj,String cropName,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (cropName == null) cropName = "";
		if (addTime == null) addTime = "";
		List<Crop> cropList = cropService.queryCrop(cropClassObj, cropName, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    cropService.queryTotalPageAndRecordNumber(cropClassObj, cropName, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = cropService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = cropService.getRecordNumber();
	    request.setAttribute("cropList",  cropList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("cropClassObj", cropClassObj);
	    request.setAttribute("cropName", cropName);
	    request.setAttribute("addTime", addTime);
	    List<CropClass> cropClassList = cropClassService.queryAllCropClass();
	    request.setAttribute("cropClassList", cropClassList);
		return "Crop/crop_frontquery_result"; 
	}

     /*前台查询Crop信息*/
	@RequestMapping(value="/{cropId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer cropId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键cropId获取Crop对象*/
        Crop crop = cropService.getCrop(cropId);

        List<CropClass> cropClassList = cropClassService.queryAllCropClass();
        request.setAttribute("cropClassList", cropClassList);
        request.setAttribute("crop",  crop);
        return "Crop/crop_frontshow";
	}

	/*ajax方式显示农作物修改jsp视图页*/
	@RequestMapping(value="/{cropId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer cropId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键cropId获取Crop对象*/
        Crop crop = cropService.getCrop(cropId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonCrop = crop.getJsonObject();
		out.println(jsonCrop.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新农作物信息*/
	@RequestMapping(value = "/{cropId}/update", method = RequestMethod.POST)
	public void update(@Validated Crop crop, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String cropPhotoFileName = this.handlePhotoUpload(request, "cropPhotoFile");
		if(!cropPhotoFileName.equals("upload/NoImage.jpg"))crop.setCropPhoto(cropPhotoFileName); 


		try {
			cropService.updateCrop(crop);
			message = "农作物更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "农作物更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除农作物信息*/
	@RequestMapping(value="/{cropId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer cropId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  cropService.deleteCrop(cropId);
	            request.setAttribute("message", "农作物删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "农作物删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条农作物记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String cropIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = cropService.deleteCrops(cropIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出农作物信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("cropClassObj") CropClass cropClassObj,String cropName,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(cropName == null) cropName = "";
        if(addTime == null) addTime = "";
        List<Crop> cropList = cropService.queryCrop(cropClassObj,cropName,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Crop信息记录"; 
        String[] headers = { "农作物id","农作物分类","农作物名称","农作物图片","添加时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<cropList.size();i++) {
        	Crop crop = cropList.get(i); 
        	dataset.add(new String[]{crop.getCropId() + "",crop.getCropClassObj().getCropClassName(),crop.getCropName(),crop.getCropPhoto(),crop.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Crop.xls");//filename是下载的xls的名，建议最好用英文 
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
