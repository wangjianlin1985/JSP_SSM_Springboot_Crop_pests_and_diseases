package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Wxqk {
    /*记录id*/
    private Integer qkId;
    public Integer getQkId(){
        return qkId;
    }
    public void setQkId(Integer qkId){
        this.qkId = qkId;
    }

    /*文献期刊类别*/
    @NotEmpty(message="文献期刊类别不能为空")
    private String wxqkType;
    public String getWxqkType() {
        return wxqkType;
    }
    public void setWxqkType(String wxqkType) {
        this.wxqkType = wxqkType;
    }

    /*学科*/
    @NotEmpty(message="学科不能为空")
    private String xueke;
    public String getXueke() {
        return xueke;
    }
    public void setXueke(String xueke) {
        this.xueke = xueke;
    }

    /*篇名*/
    @NotEmpty(message="篇名不能为空")
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*文献期刊图片*/
    private String wxqkPhoto;
    public String getWxqkPhoto() {
        return wxqkPhoto;
    }
    public void setWxqkPhoto(String wxqkPhoto) {
        this.wxqkPhoto = wxqkPhoto;
    }

    /*作者*/
    @NotEmpty(message="作者不能为空")
    private String author;
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    /*刊名*/
    @NotEmpty(message="刊名不能为空")
    private String km;
    public String getKm() {
        return km;
    }
    public void setKm(String km) {
        this.km = km;
    }

    /*关键词*/
    @NotEmpty(message="关键词不能为空")
    private String keywordInfo;
    public String getKeywordInfo() {
        return keywordInfo;
    }
    public void setKeywordInfo(String keywordInfo) {
        this.keywordInfo = keywordInfo;
    }

    /*摘要*/
    @NotEmpty(message="摘要不能为空")
    private String zhaiyao;
    public String getZhaiyao() {
        return zhaiyao;
    }
    public void setZhaiyao(String zhaiyao) {
        this.zhaiyao = zhaiyao;
    }

    /*导师*/
    @NotEmpty(message="导师不能为空")
    private String daoshi;
    public String getDaoshi() {
        return daoshi;
    }
    public void setDaoshi(String daoshi) {
        this.daoshi = daoshi;
    }

    /*发布日期*/
    @NotEmpty(message="发布日期不能为空")
    private String publishDate;
    public String getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    /*文献期刊文件*/
    private String wxqkFile;
    public String getWxqkFile() {
        return wxqkFile;
    }
    public void setWxqkFile(String wxqkFile) {
        this.wxqkFile = wxqkFile;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonWxqk=new JSONObject(); 
		jsonWxqk.accumulate("qkId", this.getQkId());
		jsonWxqk.accumulate("wxqkType", this.getWxqkType());
		jsonWxqk.accumulate("xueke", this.getXueke());
		jsonWxqk.accumulate("title", this.getTitle());
		jsonWxqk.accumulate("wxqkPhoto", this.getWxqkPhoto());
		jsonWxqk.accumulate("author", this.getAuthor());
		jsonWxqk.accumulate("km", this.getKm());
		jsonWxqk.accumulate("keywordInfo", this.getKeywordInfo());
		jsonWxqk.accumulate("zhaiyao", this.getZhaiyao());
		jsonWxqk.accumulate("daoshi", this.getDaoshi());
		jsonWxqk.accumulate("publishDate", this.getPublishDate().length()>19?this.getPublishDate().substring(0,19):this.getPublishDate());
		jsonWxqk.accumulate("wxqkFile", this.getWxqkFile());
		return jsonWxqk;
    }}