package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Crop;
import com.chengxusheji.po.Pest;

import com.chengxusheji.mapper.PestMapper;
@Service
public class PestService {

	@Resource PestMapper pestMapper;
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

    /*添加虫害记录*/
    public void addPest(Pest pest) throws Exception {
    	pestMapper.addPest(pest);
    }

    /*按照查询条件分页查询虫害记录*/
    public ArrayList<Pest> queryPest(Crop cropObj,String pestName,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != cropObj && cropObj.getCropId()!= null && cropObj.getCropId()!= 0)  where += " and t_pest.cropObj=" + cropObj.getCropId();
    	if(!pestName.equals("")) where = where + " and t_pest.pestName like '%" + pestName + "%'";
    	if(!addTime.equals("")) where = where + " and t_pest.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return pestMapper.queryPest(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Pest> queryPest(Crop cropObj,String pestName,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != cropObj && cropObj.getCropId()!= null && cropObj.getCropId()!= 0)  where += " and t_pest.cropObj=" + cropObj.getCropId();
    	if(!pestName.equals("")) where = where + " and t_pest.pestName like '%" + pestName + "%'";
    	if(!addTime.equals("")) where = where + " and t_pest.addTime like '%" + addTime + "%'";
    	return pestMapper.queryPestList(where);
    }

    /*查询所有虫害记录*/
    public ArrayList<Pest> queryAllPest()  throws Exception {
        return pestMapper.queryPestList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Crop cropObj,String pestName,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(null != cropObj && cropObj.getCropId()!= null && cropObj.getCropId()!= 0)  where += " and t_pest.cropObj=" + cropObj.getCropId();
    	if(!pestName.equals("")) where = where + " and t_pest.pestName like '%" + pestName + "%'";
    	if(!addTime.equals("")) where = where + " and t_pest.addTime like '%" + addTime + "%'";
        recordNumber = pestMapper.queryPestCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取虫害记录*/
    public Pest getPest(int pestId) throws Exception  {
        Pest pest = pestMapper.getPest(pestId);
        return pest;
    }

    /*更新虫害记录*/
    public void updatePest(Pest pest) throws Exception {
        pestMapper.updatePest(pest);
    }

    /*删除一条虫害记录*/
    public void deletePest (int pestId) throws Exception {
        pestMapper.deletePest(pestId);
    }

    /*删除多条虫害信息*/
    public int deletePests (String pestIds) throws Exception {
    	String _pestIds[] = pestIds.split(",");
    	for(String _pestId: _pestIds) {
    		pestMapper.deletePest(Integer.parseInt(_pestId));
    	}
    	return _pestIds.length;
    }
}
