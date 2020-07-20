package com.tosok.user.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.tosok.user.Until.SearchCriteria;
import com.tosok.user.VO.VisitCountVO;

public class VisitCountDAO {

	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public List<VisitCountVO> listVisitDao(SearchCriteria scri) {
		return sqlSession.selectList("listVisitDao", scri);
	}

	public int getVisitCnt(SearchCriteria scri) {
		return sqlSession.selectOne("getVisitCnt", scri);
	}

	public void insertVisitor(VisitCountVO vo) {
		sqlSession.insert("insertVisitor", vo);
	}

	public int getVisitTodayCount() {
		return sqlSession.selectOne("getVisitTodayCount");
	}
	
	public int getVisitTotalCount() {
		return sqlSession.selectOne("getVisitTotalCount");
	}

	public List<VisitCountVO> selectExcelList(Map<String, Object> paramMap) {
		return sqlSession.selectList("selectExcelList", paramMap);
	}

	public int selectVisitCount(SearchCriteria scri) {
		return sqlSession.selectOne("selectVisitCount", scri);
	}

}