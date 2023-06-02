package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Disease;

public interface DiseaseMapper {
	/*添加病害信息*/
	public void addDisease(Disease disease) throws Exception;

	/*按照查询条件分页查询病害记录*/
	public ArrayList<Disease> queryDisease(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有病害记录*/
	public ArrayList<Disease> queryDiseaseList(@Param("where") String where) throws Exception;

	/*按照查询条件的病害记录数*/
	public int queryDiseaseCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条病害记录*/
	public Disease getDisease(int diseaseId) throws Exception;

	/*更新病害记录*/
	public void updateDisease(Disease disease) throws Exception;

	/*删除病害记录*/
	public void deleteDisease(int diseaseId) throws Exception;

}
