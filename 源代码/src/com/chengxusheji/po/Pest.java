package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Pest {
    /*虫害id*/
    private Integer pestId;
    public Integer getPestId(){
        return pestId;
    }
    public void setPestId(Integer pestId){
        this.pestId = pestId;
    }

    /*农作物*/
    private Crop cropObj;
    public Crop getCropObj() {
        return cropObj;
    }
    public void setCropObj(Crop cropObj) {
        this.cropObj = cropObj;
    }

    /*虫害名称*/
    @NotEmpty(message="虫害名称不能为空")
    private String pestName;
    public String getPestName() {
        return pestName;
    }
    public void setPestName(String pestName) {
        this.pestName = pestName;
    }

    /*虫害图片*/
    private String pestPhoto;
    public String getPestPhoto() {
        return pestPhoto;
    }
    public void setPestPhoto(String pestPhoto) {
        this.pestPhoto = pestPhoto;
    }

    /*虫害描述*/
    @NotEmpty(message="虫害描述不能为空")
    private String pestDesc;
    public String getPestDesc() {
        return pestDesc;
    }
    public void setPestDesc(String pestDesc) {
        this.pestDesc = pestDesc;
    }

    /*防治方法*/
    @NotEmpty(message="防治方法不能为空")
    private String cureDesc;
    public String getCureDesc() {
        return cureDesc;
    }
    public void setCureDesc(String cureDesc) {
        this.cureDesc = cureDesc;
    }

    /*发布时间*/
    @NotEmpty(message="发布时间不能为空")
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonPest=new JSONObject(); 
		jsonPest.accumulate("pestId", this.getPestId());
		jsonPest.accumulate("cropObj", this.getCropObj().getCropName());
		jsonPest.accumulate("cropObjPri", this.getCropObj().getCropId());
		jsonPest.accumulate("pestName", this.getPestName());
		jsonPest.accumulate("pestPhoto", this.getPestPhoto());
		jsonPest.accumulate("pestDesc", this.getPestDesc());
		jsonPest.accumulate("cureDesc", this.getCureDesc());
		jsonPest.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonPest;
    }}