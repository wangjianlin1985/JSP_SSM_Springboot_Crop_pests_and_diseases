package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Disease {
    /*病害id*/
    private Integer diseaseId;
    public Integer getDiseaseId(){
        return diseaseId;
    }
    public void setDiseaseId(Integer diseaseId){
        this.diseaseId = diseaseId;
    }

    /*农作物*/
    private Crop cropObj;
    public Crop getCropObj() {
        return cropObj;
    }
    public void setCropObj(Crop cropObj) {
        this.cropObj = cropObj;
    }

    /*病害名称*/
    @NotEmpty(message="病害名称不能为空")
    private String diseaseName;
    public String getDiseaseName() {
        return diseaseName;
    }
    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    /*病害图片*/
    private String diseasePhoto;
    public String getDiseasePhoto() {
        return diseasePhoto;
    }
    public void setDiseasePhoto(String diseasePhoto) {
        this.diseasePhoto = diseasePhoto;
    }

    /*病害描述*/
    @NotEmpty(message="病害描述不能为空")
    private String diseaseDesc;
    public String getDiseaseDesc() {
        return diseaseDesc;
    }
    public void setDiseaseDesc(String diseaseDesc) {
        this.diseaseDesc = diseaseDesc;
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
    	JSONObject jsonDisease=new JSONObject(); 
		jsonDisease.accumulate("diseaseId", this.getDiseaseId());
		jsonDisease.accumulate("cropObj", this.getCropObj().getCropName());
		jsonDisease.accumulate("cropObjPri", this.getCropObj().getCropId());
		jsonDisease.accumulate("diseaseName", this.getDiseaseName());
		jsonDisease.accumulate("diseasePhoto", this.getDiseasePhoto());
		jsonDisease.accumulate("diseaseDesc", this.getDiseaseDesc());
		jsonDisease.accumulate("cureDesc", this.getCureDesc());
		jsonDisease.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonDisease;
    }}