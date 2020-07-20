package com.tosok.user.DAO.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tosok.user.DAO.MapDAO;
import com.tosok.user.VO.MapVO;

@Repository("MapDAO")
public class MapDAOImpl implements MapDAO {

    @Autowired
    private SqlSession sqlSession;
  
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

	@Override
	public int insertMapApi(MapVO m_vo) {
		return sqlSession.insert("insertMapApi", m_vo);
	}

	@Override
	public List<MapVO> listMapDao(Map<String, Object> paramMap) {
		return sqlSession.selectList("listMapDao", paramMap);
	}

	@Override
	public int insertMapImg(MapVO m_vo) {
		return sqlSession.update("insertMapImg", m_vo);
	}

	@Override
	public int listMapCnt() {
		return sqlSession.selectOne("listMapCnt");
	}

}
