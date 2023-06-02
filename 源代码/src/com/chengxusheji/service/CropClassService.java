package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.CropClass;

import com.chengxusheji.mapper.CropClassMapper;
@Service
public class CropClassService {

	@Resource CropClassMapper cropClassMapper;
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

    /*添加农作物分类记录*/
    public void addCropClass(CropClass cropClass) throws Exception {
    	cropClassMapper.addCropClass(cropClass);
    }

    /*按照查询条件分页查询农作物分类记录*/
    public ArrayList<CropClass> queryCropClass(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return cropClassMapper.queryCropClass(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<CropClass> queryCropClass() throws Exception  { 
     	String where = "where 1=1";
    	return cropClassMapper.queryCropClassList(where);
    }

    /*查询所有农作物分类记录*/
    public ArrayList<CropClass> queryAllCropClass()  throws Exception {
        return cropClassMapper.queryCropClassList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = cropClassMapper.queryCropClassCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取农作物分类记录*/
    public CropClass getCropClass(int cropClassId) throws Exception  {
        CropClass cropClass = cropClassMapper.getCropClass(cropClassId);
        return cropClass;
    }

    /*更新农作物分类记录*/
    public void updateCropClass(CropClass cropClass) throws Exception {
        cropClassMapper.updateCropClass(cropClass);
    }

    /*删除一条农作物分类记录*/
    public void deleteCropClass (int cropClassId) throws Exception {
        cropClassMapper.deleteCropClass(cropClassId);
    }

    /*删除多条农作物分类信息*/
    public int deleteCropClasss (String cropClassIds) throws Exception {
    	String _cropClassIds[] = cropClassIds.split(",");
    	for(String _cropClassId: _cropClassIds) {
    		cropClassMapper.deleteCropClass(Integer.parseInt(_cropClassId));
    	}
    	return _cropClassIds.length;
    }
}
