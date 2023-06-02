package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.CropClass;

public interface CropClassMapper {
	/*添加农作物分类信息*/
	public void addCropClass(CropClass cropClass) throws Exception;

	/*按照查询条件分页查询农作物分类记录*/
	public ArrayList<CropClass> queryCropClass(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有农作物分类记录*/
	public ArrayList<CropClass> queryCropClassList(@Param("where") String where) throws Exception;

	/*按照查询条件的农作物分类记录数*/
	public int queryCropClassCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条农作物分类记录*/
	public CropClass getCropClass(int cropClassId) throws Exception;

	/*更新农作物分类记录*/
	public void updateCropClass(CropClass cropClass) throws Exception;

	/*删除农作物分类记录*/
	public void deleteCropClass(int cropClassId) throws Exception;

}
