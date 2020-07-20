package com.tosok.user.DAO;

import java.util.List;
import java.util.Map;

import com.tosok.user.VO.MapVO;

public interface MapDAO {

	public List<MapVO> listMapDao(Map<String, Object> paramMap);
	public int listMapCnt();

	public int insertMapApi(MapVO m_vo);
	public int insertMapImg(MapVO m_vo);

}
