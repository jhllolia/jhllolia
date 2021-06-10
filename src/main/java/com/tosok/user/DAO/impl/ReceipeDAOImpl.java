package com.tosok.user.DAO.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tosok.user.DAO.ReceipeDAO;
import com.tosok.user.Until.Criteria;
import com.tosok.user.VO.CommentVO;
import com.tosok.user.VO.ImageVO;
import com.tosok.user.VO.ReceipeVO;

@Repository("ReceipeDAO")
public class ReceipeDAOImpl implements ReceipeDAO {

    @Autowired
    private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

	@Override
	public List<ReceipeVO> listReceipeDao(Criteria cri) {
		return sqlSession.selectList("listReceipeDao", cri);
	}

	@Override
	public int selectReceipeListCnt(Criteria cri) {
        return sqlSession.selectOne("selectReceipeListCnt", cri);
	}

	@Override
	public int writeReceipeDao(Map<String, Object> paramMap) {
        return sqlSession.insert("writeReceipeDao", paramMap);
	}

	@Override
	public int updateReceipeDao(Map<String, Object> paramMap) {
        return sqlSession.update("updateReceipeDao", paramMap);
	}

	@Override
	public ReceipeVO viewReceipeDao(Map<String, Object> paramMap) {
        return sqlSession.selectOne("viewReceipeDao", paramMap);
	}

	@Override
	public Object prevReceipeDao(String intSeq) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("intSeq", intSeq);
        return sqlSession.selectOne("prevReceipeDao", map); 
	}

	@Override
	public Object nextReceipeDao(String intSeq) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("intSeq", intSeq);
        return sqlSession.selectOne("nextReceipeDao", map); 
	}

	@Override
	public int deleteReceipeDao(Map<String, Object> paramMap) {
        return sqlSession.delete("deleteReceipeDao", paramMap);
	}

	@Override
	public int insertBlog(ReceipeVO vo) {
        return sqlSession.insert("insertBlog", vo);
	}

	@Override
	public int insertComment(Map<String, Object> paramMap) {
		return sqlSession.insert("insertComment", paramMap);
	}

	@Override
	public int deleteComment(Map<String, Object> paramMap) {
		return sqlSession.update("deleteComment", paramMap);
	}

	@Override
	public List<CommentVO> selectReceipeComment(Map<String, Object> paramMap) {
		return sqlSession.selectList("selectReceipeComment", paramMap);
	}

	@Override
	public int selectReceipeCommentCnt(String intSeq) {
		return sqlSession.selectOne("selectReceipeCommentCnt", intSeq);
	}

	@Override
	public int updateComment(Map<String, Object> paramMap) {
		return sqlSession.update("updateComment", paramMap);
	}

	@Override
	public List<CommentVO> selectDelComment(Map<String, Object> paramMap) {
		return sqlSession.selectList("selectDelComment", paramMap);
	}

	@Override
	public void deleteChildComment(int child) {
		sqlSession.update("deleteChildComment", child);
	}

	@Override
	public int insertGallaryData(ImageVO vo) {
		return sqlSession.insert("insertGallaryData", vo);
	}

	@Override
	public List<ImageVO> selectGallayTotalImage(ImageVO vo) {
		return sqlSession.selectList("selectGallayTotalImage", vo);
	}

	@Override
	public void updateGallaryData(ImageVO vo) {
		sqlSession.update("updateGallaryData", vo);
	}

	@Override
	public int deleteGallaryData(ImageVO vo) {
		return sqlSession.delete("updateGallaryData", vo);
	}

	@Override
	public int selectGallaryCnt(ImageVO vo) {
		return sqlSession.selectOne("selectGallaryCnt", vo);
	}

}
