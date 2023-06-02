package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.PostInfo;

import com.chengxusheji.mapper.PostInfoMapper;
@Service
public class PostInfoService {

	@Resource PostInfoMapper postInfoMapper;
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

    /*添加帖子记录*/
    public void addPostInfo(PostInfo postInfo) throws Exception {
    	postInfoMapper.addPostInfo(postInfo);
    }

    /*按照查询条件分页查询帖子记录*/
    public ArrayList<PostInfo> queryPostInfo(String title,UserInfo userObj,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_postInfo.title like '%" + title + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_postInfo.userObj='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) where = where + " and t_postInfo.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return postInfoMapper.queryPostInfo(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<PostInfo> queryPostInfo(String title,UserInfo userObj,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_postInfo.title like '%" + title + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_postInfo.userObj='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) where = where + " and t_postInfo.addTime like '%" + addTime + "%'";
    	return postInfoMapper.queryPostInfoList(where);
    }

    /*查询所有帖子记录*/
    public ArrayList<PostInfo> queryAllPostInfo()  throws Exception {
        return postInfoMapper.queryPostInfoList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String title,UserInfo userObj,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_postInfo.title like '%" + title + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_postInfo.userObj='" + userObj.getUser_name() + "'";
    	if(!addTime.equals("")) where = where + " and t_postInfo.addTime like '%" + addTime + "%'";
        recordNumber = postInfoMapper.queryPostInfoCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取帖子记录*/
    public PostInfo getPostInfo(int postInfoId) throws Exception  {
        PostInfo postInfo = postInfoMapper.getPostInfo(postInfoId);
        return postInfo;
    }

    /*更新帖子记录*/
    public void updatePostInfo(PostInfo postInfo) throws Exception {
        postInfoMapper.updatePostInfo(postInfo);
    }

    /*删除一条帖子记录*/
    public void deletePostInfo (int postInfoId) throws Exception {
        postInfoMapper.deletePostInfo(postInfoId);
    }

    /*删除多条帖子信息*/
    public int deletePostInfos (String postInfoIds) throws Exception {
    	String _postInfoIds[] = postInfoIds.split(",");
    	for(String _postInfoId: _postInfoIds) {
    		postInfoMapper.deletePostInfo(Integer.parseInt(_postInfoId));
    	}
    	return _postInfoIds.length;
    }
}
