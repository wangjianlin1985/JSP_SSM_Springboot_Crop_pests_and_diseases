package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Reply;

public interface ReplyMapper {
	/*添加帖子回复信息*/
	public void addReply(Reply reply) throws Exception;

	/*按照查询条件分页查询帖子回复记录*/
	public ArrayList<Reply> queryReply(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有帖子回复记录*/
	public ArrayList<Reply> queryReplyList(@Param("where") String where) throws Exception;

	/*按照查询条件的帖子回复记录数*/
	public int queryReplyCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条帖子回复记录*/
	public Reply getReply(int replyId) throws Exception;

	/*更新帖子回复记录*/
	public void updateReply(Reply reply) throws Exception;

	/*删除帖子回复记录*/
	public void deleteReply(int replyId) throws Exception;

}
