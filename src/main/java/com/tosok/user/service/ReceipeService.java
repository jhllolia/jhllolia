package com.tosok.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tosok.user.Until.Criteria;
import com.tosok.user.VO.CommentVO;
import com.tosok.user.VO.ImageVO;
import com.tosok.user.VO.ReceipeVO;

public interface ReceipeService {

	public List<ReceipeVO> listReceipeDao(Criteria cri);
	public List<CommentVO> selectReceipeComment(Map<String, Object> paramMap);
	public int selectReceipeListCnt(Criteria cri);

	public int writeReceipeDao(Map<String, Object> paramMap);
	public int updateReceipeDao(Map<String, Object> paramMap);
	public int deleteReceipeDao(Map<String, Object> paramMap);
	public ReceipeVO viewReceipeDao(Map<String, Object> paramMap);

	public Object prevReceipeDao(String intSeq);
	public Object nextReceipeDao(String intSeq);
	public int insertBlog(ReceipeVO vo);
	public int insertComment(Map<String, Object> paramMap);
	public int deleteComment(Map<String, Object> paramMap);
	public int selectReceipeCommentCnt(String intSeq);
	public int updateComment(Map<String, Object> paramMap);

	public List<CommentVO> selectDelComment(Map<String, Object> paramMap);
	public void deleteChildComment(int child);

	public int updateGallaryData(ImageVO vo);
	public List<ImageVO> selectGallayTotalImage(ImageVO vo);
	public void listGallaryOrdChange(HttpServletRequest req);

}
