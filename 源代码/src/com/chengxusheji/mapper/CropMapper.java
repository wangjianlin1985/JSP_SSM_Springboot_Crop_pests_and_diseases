package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Crop;

public interface CropMapper {
	/*添加农作物信息*/
	public void addCrop(Crop crop) throws Exception;

	/*按照查询条件分页查询农作物记录*/
	public ArrayList<Crop> queryCrop(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有农作物记录*/
	public ArrayList<Crop> queryCropList(@Param("where") String where) throws Exception;

	/*按照查询条件的农作物记录数*/
	public int queryCropCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条农作物记录*/
	public Crop getCrop(int cropId) throws Exception;

	/*更新农作物记录*/
	public void updateCrop(Crop crop) throws Exception;

	/*删除农作物记录*/
	public void deleteCrop(int cropId) throws Exception;

}
