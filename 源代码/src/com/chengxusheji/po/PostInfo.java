package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class PostInfo {
    /*帖子id*/
    private Integer postInfoId;
    public Integer getPostInfoId(){
        return postInfoId;
    }
    public void setPostInfoId(Integer postInfoId){
        this.postInfoId = postInfoId;
    }

    /*帖子标题*/
    @NotEmpty(message="帖子标题不能为空")
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*帖子内容*/
    @NotEmpty(message="帖子内容不能为空")
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*浏览量*/
    @NotNull(message="必须输入浏览量")
    private Integer hitNum;
    public Integer getHitNum() {
        return hitNum;
    }
    public void setHitNum(Integer hitNum) {
        this.hitNum = hitNum;
    }

    /*发帖人*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*发帖时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonPostInfo=new JSONObject(); 
		jsonPostInfo.accumulate("postInfoId", this.getPostInfoId());
		jsonPostInfo.accumulate("title", this.getTitle());
		jsonPostInfo.accumulate("content", this.getContent());
		jsonPostInfo.accumulate("hitNum", this.getHitNum());
		jsonPostInfo.accumulate("userObj", this.getUserObj().getName());
		jsonPostInfo.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonPostInfo.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonPostInfo;
    }}