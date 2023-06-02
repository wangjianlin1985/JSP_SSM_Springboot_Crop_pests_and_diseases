package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Wxqk;

public interface WxqkMapper {
	/*添加文献期刊信息*/
	public void addWxqk(Wxqk wxqk) throws Exception;

	/*按照查询条件分页查询文献期刊记录*/
	public ArrayList<Wxqk> queryWxqk(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有文献期刊记录*/
	public ArrayList<Wxqk> queryWxqkList(@Param("where") String where) throws Exception;

	/*按照查询条件的文献期刊记录数*/
	public int queryWxqkCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条文献期刊记录*/
	public Wxqk getWxqk(int qkId) throws Exception;

	/*更新文献期刊记录*/
	public void updateWxqk(Wxqk wxqk) throws Exception;

	/*删除文献期刊记录*/
	public void deleteWxqk(int qkId) throws Exception;

}
