﻿package com.chengxusheji.po;

 
import org.hibernate.validator.constraints.NotEmpty;

 
public class Admin {
	/*管理员用户名*/
	@NotEmpty(message="用户名不能为空")  
	private String username;
	/*登陆密码*/
	@NotEmpty(message="登陆密码不能为空") 
	private String password;
	
	private String identify; //用户身份 
	
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
