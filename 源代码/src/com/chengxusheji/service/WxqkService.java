package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Wxqk;

import com.chengxusheji.mapper.WxqkMapper;
@Service
public class WxqkService {

	@Resource WxqkMapper wxqkMapper;
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

    /*添加文献期刊记录*/
    public void addWxqk(Wxqk wxqk) throws Exception {
    	wxqkMapper.addWxqk(wxqk);
    }

    /*按照查询条件分页查询文献期刊记录*/
    public ArrayList<Wxqk> queryWxqk(String wxqkType,String xueke,String title,String author,String km,String daoshi,String publishDate,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!wxqkType.equals("")) where = where + " and t_wxqk.wxqkType like '%" + wxqkType + "%'";
    	if(!xueke.equals("")) where = where + " and t_wxqk.xueke like '%" + xueke + "%'";
    	if(!title.equals("")) where = where + " and t_wxqk.title like '%" + title + "%'";
    	if(!author.equals("")) where = where + " and t_wxqk.author like '%" + author + "%'";
    	if(!km.equals("")) where = where + " and t_wxqk.km like '%" + km + "%'";
    	if(!daoshi.equals("")) where = where + " and t_wxqk.daoshi like '%" + daoshi + "%'";
    	if(!publishDate.equals("")) where = where + " and t_wxqk.publishDate like '%" + publishDate + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return wxqkMapper.queryWxqk(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Wxqk> queryWxqk(String wxqkType,String xueke,String title,String author,String km,String daoshi,String publishDate) throws Exception  { 
     	String where = "where 1=1";
    	if(!wxqkType.equals("")) where = where + " and t_wxqk.wxqkType like '%" + wxqkType + "%'";
    	if(!xueke.equals("")) where = where + " and t_wxqk.xueke like '%" + xueke + "%'";
    	if(!title.equals("")) where = where + " and t_wxqk.title like '%" + title + "%'";
    	if(!author.equals("")) where = where + " and t_wxqk.author like '%" + author + "%'";
    	if(!km.equals("")) where = where + " and t_wxqk.km like '%" + km + "%'";
    	if(!daoshi.equals("")) where = where + " and t_wxqk.daoshi like '%" + daoshi + "%'";
    	if(!publishDate.equals("")) where = where + " and t_wxqk.publishDate like '%" + publishDate + "%'";
    	return wxqkMapper.queryWxqkList(where);
    }

    /*查询所有文献期刊记录*/
    public ArrayList<Wxqk> queryAllWxqk()  throws Exception {
        return wxqkMapper.queryWxqkList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String wxqkType,String xueke,String title,String author,String km,String daoshi,String publishDate) throws Exception {
     	String where = "where 1=1";
    	if(!wxqkType.equals("")) where = where + " and t_wxqk.wxqkType like '%" + wxqkType + "%'";
    	if(!xueke.equals("")) where = where + " and t_wxqk.xueke like '%" + xueke + "%'";
    	if(!title.equals("")) where = where + " and t_wxqk.title like '%" + title + "%'";
    	if(!author.equals("")) where = where + " and t_wxqk.author like '%" + author + "%'";
    	if(!km.equals("")) where = where + " and t_wxqk.km like '%" + km + "%'";
    	if(!daoshi.equals("")) where = where + " and t_wxqk.daoshi like '%" + daoshi + "%'";
    	if(!publishDate.equals("")) where = where + " and t_wxqk.publishDate like '%" + publishDate + "%'";
        recordNumber = wxqkMapper.queryWxqkCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取文献期刊记录*/
    public Wxqk getWxqk(int qkId) throws Exception  {
        Wxqk wxqk = wxqkMapper.getWxqk(qkId);
        return wxqk;
    }

    /*更新文献期刊记录*/
    public void updateWxqk(Wxqk wxqk) throws Exception {
        wxqkMapper.updateWxqk(wxqk);
    }

    /*删除一条文献期刊记录*/
    public void deleteWxqk (int qkId) throws Exception {
        wxqkMapper.deleteWxqk(qkId);
    }

    /*删除多条文献期刊信息*/
    public int deleteWxqks (String qkIds) throws Exception {
    	String _qkIds[] = qkIds.split(",");
    	for(String _qkId: _qkIds) {
    		wxqkMapper.deleteWxqk(Integer.parseInt(_qkId));
    	}
    	return _qkIds.length;
    }
}
