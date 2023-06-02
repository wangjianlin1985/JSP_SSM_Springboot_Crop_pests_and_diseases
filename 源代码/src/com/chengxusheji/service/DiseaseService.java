package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Crop;
import com.chengxusheji.po.Disease;

import com.chengxusheji.mapper.DiseaseMapper;
@Service
public class DiseaseService {

	@Resource DiseaseMapper diseaseMapper;
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

    /*添加病害记录*/
    public void addDisease(Disease disease) throws Exception {
    	diseaseMapper.addDisease(disease);
    }

    /*按照查询条件分页查询病害记录*/
    public ArrayList<Disease> queryDisease(Crop cropObj,String diseaseName,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != cropObj && cropObj.getCropId()!= null && cropObj.getCropId()!= 0)  where += " and t_disease.cropObj=" + cropObj.getCropId();
    	if(!diseaseName.equals("")) where = where + " and t_disease.diseaseName like '%" + diseaseName + "%'";
    	if(!addTime.equals("")) where = where + " and t_disease.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return diseaseMapper.queryDisease(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Disease> queryDisease(Crop cropObj,String diseaseName,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != cropObj && cropObj.getCropId()!= null && cropObj.getCropId()!= 0)  where += " and t_disease.cropObj=" + cropObj.getCropId();
    	if(!diseaseName.equals("")) where = where + " and t_disease.diseaseName like '%" + diseaseName + "%'";
    	if(!addTime.equals("")) where = where + " and t_disease.addTime like '%" + addTime + "%'";
    	return diseaseMapper.queryDiseaseList(where);
    }

    /*查询所有病害记录*/
    public ArrayList<Disease> queryAllDisease()  throws Exception {
        return diseaseMapper.queryDiseaseList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Crop cropObj,String diseaseName,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(null != cropObj && cropObj.getCropId()!= null && cropObj.getCropId()!= 0)  where += " and t_disease.cropObj=" + cropObj.getCropId();
    	if(!diseaseName.equals("")) where = where + " and t_disease.diseaseName like '%" + diseaseName + "%'";
    	if(!addTime.equals("")) where = where + " and t_disease.addTime like '%" + addTime + "%'";
        recordNumber = diseaseMapper.queryDiseaseCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取病害记录*/
    public Disease getDisease(int diseaseId) throws Exception  {
        Disease disease = diseaseMapper.getDisease(diseaseId);
        return disease;
    }

    /*更新病害记录*/
    public void updateDisease(Disease disease) throws Exception {
        diseaseMapper.updateDisease(disease);
    }

    /*删除一条病害记录*/
    public void deleteDisease (int diseaseId) throws Exception {
        diseaseMapper.deleteDisease(diseaseId);
    }

    /*删除多条病害信息*/
    public int deleteDiseases (String diseaseIds) throws Exception {
    	String _diseaseIds[] = diseaseIds.split(",");
    	for(String _diseaseId: _diseaseIds) {
    		diseaseMapper.deleteDisease(Integer.parseInt(_diseaseId));
    	}
    	return _diseaseIds.length;
    }
}
