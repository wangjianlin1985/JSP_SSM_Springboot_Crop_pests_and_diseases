package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;

import com.chengxusheji.po.Admin;
import com.chengxusheji.po.Expert;

import com.chengxusheji.mapper.ExpertMapper;
@Service
public class ExpertService {

	@Resource ExpertMapper expertMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加专家记录*/
    public void addExpert(Expert expert) throws Exception {
    	expertMapper.addExpert(expert);
    }

    /*按照查询条件分页查询专家记录*/
    public ArrayList<Expert> queryExpert(String expertUserName,String name,String birthDate,String zhicheng,String telephone,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!expertUserName.equals("")) where = where + " and t_expert.expertUserName like '%" + expertUserName + "%'";
    	if(!name.equals("")) where = where + " and t_expert.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_expert.birthDate like '%" + birthDate + "%'";
    	if(!zhicheng.equals("")) where = where + " and t_expert.zhicheng like '%" + zhicheng + "%'";
    	if(!telephone.equals("")) where = where + " and t_expert.telephone like '%" + telephone + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return expertMapper.queryExpert(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Expert> queryExpert(String expertUserName,String name,String birthDate,String zhicheng,String telephone) throws Exception  { 
     	String where = "where 1=1";
    	if(!expertUserName.equals("")) where = where + " and t_expert.expertUserName like '%" + expertUserName + "%'";
    	if(!name.equals("")) where = where + " and t_expert.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_expert.birthDate like '%" + birthDate + "%'";
    	if(!zhicheng.equals("")) where = where + " and t_expert.zhicheng like '%" + zhicheng + "%'";
    	if(!telephone.equals("")) where = where + " and t_expert.telephone like '%" + telephone + "%'";
    	return expertMapper.queryExpertList(where);
    }

    /*查询所有专家记录*/
    public ArrayList<Expert> queryAllExpert()  throws Exception {
        return expertMapper.queryExpertList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String expertUserName,String name,String birthDate,String zhicheng,String telephone) throws Exception {
     	String where = "where 1=1";
    	if(!expertUserName.equals("")) where = where + " and t_expert.expertUserName like '%" + expertUserName + "%'";
    	if(!name.equals("")) where = where + " and t_expert.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_expert.birthDate like '%" + birthDate + "%'";
    	if(!zhicheng.equals("")) where = where + " and t_expert.zhicheng like '%" + zhicheng + "%'";
    	if(!telephone.equals("")) where = where + " and t_expert.telephone like '%" + telephone + "%'";
        recordNumber = expertMapper.queryExpertCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取专家记录*/
    public Expert getExpert(String expertUserName) throws Exception  {
        Expert expert = expertMapper.getExpert(expertUserName);
        return expert;
    }

    /*更新专家记录*/
    public void updateExpert(Expert expert) throws Exception {
        expertMapper.updateExpert(expert);
    }

    /*删除一条专家记录*/
    public void deleteExpert (String expertUserName) throws Exception {
        expertMapper.deleteExpert(expertUserName);
    }

    /*删除多条专家信息*/
    public int deleteExperts (String expertUserNames) throws Exception {
    	String _expertUserNames[] = expertUserNames.split(",");
    	for(String _expertUserName: _expertUserNames) {
    		expertMapper.deleteExpert(_expertUserName);
    	}
    	return _expertUserNames.length;
    }
	
	/*保存业务逻辑错误信息字段*/
	private String errMessage;
	public String getErrMessage() { return this.errMessage; }
	
	/*验证用户登录*/
	public boolean checkLogin(Admin admin) throws Exception { 
		Expert db_expert = (Expert) expertMapper.getExpert(admin.getUsername());
		if(db_expert == null) { 
			this.errMessage = " 账号不存在 ";
			System.out.print(this.errMessage);
			return false;
		} else if( !db_expert.getPassword().equals(admin.getPassword())) {
			this.errMessage = " 密码不正确! ";
			System.out.print(this.errMessage);
			return false;
		}
		
		return true;
	}
}
