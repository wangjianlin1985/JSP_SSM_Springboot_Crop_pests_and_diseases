package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.CropClass;
import com.chengxusheji.po.Crop;

import com.chengxusheji.mapper.CropMapper;
@Service
public class CropService {

	@Resource CropMapper cropMapper;
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

    /*添加农作物记录*/
    public void addCrop(Crop crop) throws Exception {
    	cropMapper.addCrop(crop);
    }

    /*按照查询条件分页查询农作物记录*/
    public ArrayList<Crop> queryCrop(CropClass cropClassObj,String cropName,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != cropClassObj && cropClassObj.getCropClassId()!= null && cropClassObj.getCropClassId()!= 0)  where += " and t_crop.cropClassObj=" + cropClassObj.getCropClassId();
    	if(!cropName.equals("")) where = where + " and t_crop.cropName like '%" + cropName + "%'";
    	if(!addTime.equals("")) where = where + " and t_crop.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return cropMapper.queryCrop(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Crop> queryCrop(CropClass cropClassObj,String cropName,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(null != cropClassObj && cropClassObj.getCropClassId()!= null && cropClassObj.getCropClassId()!= 0)  where += " and t_crop.cropClassObj=" + cropClassObj.getCropClassId();
    	if(!cropName.equals("")) where = where + " and t_crop.cropName like '%" + cropName + "%'";
    	if(!addTime.equals("")) where = where + " and t_crop.addTime like '%" + addTime + "%'";
    	return cropMapper.queryCropList(where);
    }

    /*查询所有农作物记录*/
    public ArrayList<Crop> queryAllCrop()  throws Exception {
        return cropMapper.queryCropList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(CropClass cropClassObj,String cropName,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(null != cropClassObj && cropClassObj.getCropClassId()!= null && cropClassObj.getCropClassId()!= 0)  where += " and t_crop.cropClassObj=" + cropClassObj.getCropClassId();
    	if(!cropName.equals("")) where = where + " and t_crop.cropName like '%" + cropName + "%'";
    	if(!addTime.equals("")) where = where + " and t_crop.addTime like '%" + addTime + "%'";
        recordNumber = cropMapper.queryCropCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取农作物记录*/
    public Crop getCrop(int cropId) throws Exception  {
        Crop crop = cropMapper.getCrop(cropId);
        return crop;
    }

    /*更新农作物记录*/
    public void updateCrop(Crop crop) throws Exception {
        cropMapper.updateCrop(crop);
    }

    /*删除一条农作物记录*/
    public void deleteCrop (int cropId) throws Exception {
        cropMapper.deleteCrop(cropId);
    }

    /*删除多条农作物信息*/
    public int deleteCrops (String cropIds) throws Exception {
    	String _cropIds[] = cropIds.split(",");
    	for(String _cropId: _cropIds) {
    		cropMapper.deleteCrop(Integer.parseInt(_cropId));
    	}
    	return _cropIds.length;
    }
}
