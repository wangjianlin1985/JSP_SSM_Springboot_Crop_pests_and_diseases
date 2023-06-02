package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Zhenduan;

public interface ZhenduanMapper {
	/*添加诊断信息*/
	public void addZhenduan(Zhenduan zhenduan) throws Exception;

	/*按照查询条件分页查询诊断记录*/
	public ArrayList<Zhenduan> queryZhenduan(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有诊断记录*/
	public ArrayList<Zhenduan> queryZhenduanList(@Param("where") String where) throws Exception;

	/*按照查询条件的诊断记录数*/
	public int queryZhenduanCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条诊断记录*/
	public Zhenduan getZhenduan(int zhenduanId) throws Exception;

	/*更新诊断记录*/
	public void updateZhenduan(Zhenduan zhenduan) throws Exception;

	/*删除诊断记录*/
	public void deleteZhenduan(int zhenduanId) throws Exception;

}
