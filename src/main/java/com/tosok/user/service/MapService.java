package com.tosok.user.service;

import java.util.List;
import java.util.Map;

import com.tosok.user.VO.MapVO;

public interface MapService {

	public List<MapVO> listMapDao(Map<String, Object> paramMap);
	public int listMapCnt();

	public int insertMapApi(MapVO m_vo);
	public int insertMapImg(MapVO m_vo);

}
