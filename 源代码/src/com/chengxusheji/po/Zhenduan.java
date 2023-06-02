package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Zhenduan {
    /*诊断id*/
    private Integer zhenduanId;
    public Integer getZhenduanId(){
        return zhenduanId;
    }
    public void setZhenduanId(Integer zhenduanId){
        this.zhenduanId = zhenduanId;
    }

    /*症状标题*/
    @NotEmpty(message="症状标题不能为空")
    private String zhengzhuang;
    public String getZhengzhuang() {
        return zhengzhuang;
    }
    public void setZhengzhuang(String zhengzhuang) {
        this.zhengzhuang = zhengzhuang;
    }

    /*症状图片*/
    private String zhenzhuangPhoto;
    public String getZhenzhuangPhoto() {
        return zhenzhuangPhoto;
    }
    public void setZhenzhuangPhoto(String zhenzhuangPhoto) {
        this.zhenzhuangPhoto = zhenzhuangPhoto;
    }

    /*病因*/
    @NotEmpty(message="病因不能为空")
    private String bingyin;
    public String getBingyin() {
        return bingyin;
    }
    public void setBingyin(String bingyin) {
        this.bingyin = bingyin;
    }

    /*特征*/
    @NotEmpty(message="特征不能为空")
    private String tezheng;
    public String getTezheng() {
        return tezheng;
    }
    public void setTezheng(String tezheng) {
        this.tezheng = tezheng;
    }

    /*发病条件*/
    @NotEmpty(message="发病条件不能为空")
    private String fbtj;
    public String getFbtj() {
        return fbtj;
    }
    public void setFbtj(String fbtj) {
        this.fbtj = fbtj;
    }

    /*农业预防*/
    @NotEmpty(message="农业预防不能为空")
    private String nyyf;
    public String getNyyf() {
        return nyyf;
    }
    public void setNyyf(String nyyf) {
        this.nyyf = nyyf;
    }

    /*治疗方法*/
    @NotEmpty(message="治疗方法不能为空")
    private String zlff;
    public String getZlff() {
        return zlff;
    }
    public void setZlff(String zlff) {
        this.zlff = zlff;
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
    	JSONObject jsonZhenduan=new JSONObject(); 
		jsonZhenduan.accumulate("zhenduanId", this.getZhenduanId());
		jsonZhenduan.accumulate("zhengzhuang", this.getZhengzhuang());
		jsonZhenduan.accumulate("zhenzhuangPhoto", this.getZhenzhuangPhoto());
		jsonZhenduan.accumulate("bingyin", this.getBingyin());
		jsonZhenduan.accumulate("tezheng", this.getTezheng());
		jsonZhenduan.accumulate("fbtj", this.getFbtj());
		jsonZhenduan.accumulate("nyyf", this.getNyyf());
		jsonZhenduan.accumulate("zlff", this.getZlff());
		jsonZhenduan.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonZhenduan;
    }}