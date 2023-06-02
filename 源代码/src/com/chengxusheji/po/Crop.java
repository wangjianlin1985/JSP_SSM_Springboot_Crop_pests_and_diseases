package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Crop {
    /*农作物id*/
    private Integer cropId;
    public Integer getCropId(){
        return cropId;
    }
    public void setCropId(Integer cropId){
        this.cropId = cropId;
    }

    /*农作物分类*/
    private CropClass cropClassObj;
    public CropClass getCropClassObj() {
        return cropClassObj;
    }
    public void setCropClassObj(CropClass cropClassObj) {
        this.cropClassObj = cropClassObj;
    }

    /*农作物名称*/
    @NotEmpty(message="农作物名称不能为空")
    private String cropName;
    public String getCropName() {
        return cropName;
    }
    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    /*农作物图片*/
    private String cropPhoto;
    public String getCropPhoto() {
        return cropPhoto;
    }
    public void setCropPhoto(String cropPhoto) {
        this.cropPhoto = cropPhoto;
    }

    /*农作物描述*/
    @NotEmpty(message="农作物描述不能为空")
    private String cropDesc;
    public String getCropDesc() {
        return cropDesc;
    }
    public void setCropDesc(String cropDesc) {
        this.cropDesc = cropDesc;
    }

    /*备注信息*/
    private String cropMemo;
    public String getCropMemo() {
        return cropMemo;
    }
    public void setCropMemo(String cropMemo) {
        this.cropMemo = cropMemo;
    }

    /*添加时间*/
    @NotEmpty(message="添加时间不能为空")
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonCrop=new JSONObject(); 
		jsonCrop.accumulate("cropId", this.getCropId());
		jsonCrop.accumulate("cropClassObj", this.getCropClassObj().getCropClassName());
		jsonCrop.accumulate("cropClassObjPri", this.getCropClassObj().getCropClassId());
		jsonCrop.accumulate("cropName", this.getCropName());
		jsonCrop.accumulate("cropPhoto", this.getCropPhoto());
		jsonCrop.accumulate("cropDesc", this.getCropDesc());
		jsonCrop.accumulate("cropMemo", this.getCropMemo());
		jsonCrop.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonCrop;
    }}