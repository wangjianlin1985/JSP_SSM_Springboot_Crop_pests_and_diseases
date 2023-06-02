package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class CropClass {
    /*农作物分类id*/
    private Integer cropClassId;
    public Integer getCropClassId(){
        return cropClassId;
    }
    public void setCropClassId(Integer cropClassId){
        this.cropClassId = cropClassId;
    }

    /*农作物分类名称*/
    @NotEmpty(message="农作物分类名称不能为空")
    private String cropClassName;
    public String getCropClassName() {
        return cropClassName;
    }
    public void setCropClassName(String cropClassName) {
        this.cropClassName = cropClassName;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonCropClass=new JSONObject(); 
		jsonCropClass.accumulate("cropClassId", this.getCropClassId());
		jsonCropClass.accumulate("cropClassName", this.getCropClassName());
		return jsonCropClass;
    }}