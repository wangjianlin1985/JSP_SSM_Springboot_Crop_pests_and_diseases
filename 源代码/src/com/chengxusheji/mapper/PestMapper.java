package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Pest;

public interface PestMapper {
	/*添加虫害信息*/
	public void addPest(Pest pest) throws Exception;

	/*按照查询条件分页查询虫害记录*/
	public ArrayList<Pest> queryPest(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有虫害记录*/
	public ArrayList<Pest> queryPestList(@Param("where") String where) throws Exception;

	/*按照查询条件的虫害记录数*/
	public int queryPestCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条虫害记录*/
	public Pest getPest(int pestId) throws Exception;

	/*更新虫害记录*/
	public void updatePest(Pest pest) throws Exception;

	/*删除虫害记录*/
	public void deletePest(int pestId) throws Exception;

}
