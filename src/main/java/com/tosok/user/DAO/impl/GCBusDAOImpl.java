package com.tosok.user.DAO.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tosok.user.DAO.GCBusDAO;

@Repository("GCBusDAO")
public class GCBusDAOImpl implements GCBusDAO {

    @Autowired
    private SqlSession sqlSession;
  
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

	@Override
	public List<Map<String, Object>> busList(Map<String, Object> paramMap) {
		return sqlSession.selectList("selectBusList", paramMap);
	}

	@Override
	public List<Map<String, Object>> selectStation(Map<String, Object> paramMap) {
		return sqlSession.selectList("selectStation", paramMap);
	}

	@Override
	public Map<String, Object> selectRouteId(Map<String, Object> paramMap) {
		return sqlSession.selectOne("selectRouteId", paramMap);
	}
	
	@Override
	public List<Map<String, Object>> selectRoutePath(Map<String, Object> paramMap) {
		return sqlSession.selectList("selectRoutePath", paramMap);
	}
	
	@Override
	public List<Map<String, Object>> selectNodeId(Map<String, Object> paramMap) {
		Map<String,Object> map = (Map<String,Object>) paramMap;
		
		int nPage_No = 0;
		int nRow_Count = 10;
        String strPage_No = map.get("PAGE_NO").toString();
        String strRow_Count = (String) map.get("ROW_COUNT");

		if(StringUtils.isEmpty(strPage_No) == false) {
	        nPage_No = (Integer.parseInt(strPage_No) - 1) * 10;
		}
	
		if(StringUtils.isEmpty(strRow_Count) == false) {
	        nRow_Count = Integer.parseInt(strRow_Count);
		}

        map.put("PAGE_NO", nPage_No);
        map.put("ROW_COUNT", nRow_Count);

		return sqlSession.selectList("selectNodeId", map);
	}

	@Override
	public void insertNodeRouteInfo(Map<String, Object> map) {
		sqlSession.insert("insertNodeRouteInfo", map);
	}

	@Override
	public void insertRouteOrder(Map<String, Object> map) {
		sqlSession.insert("insertRouteOrder", map);
	}

	@Override
	public void insertRouteInfo(Map<String, Object> map) {
		sqlSession.insert("insertRouteInfo", map);
	}

	@Override
	public void insertNodeInfo(Map<String, Object> map) {
		sqlSession.insert("insertNodeInfo", map);
	}

}
