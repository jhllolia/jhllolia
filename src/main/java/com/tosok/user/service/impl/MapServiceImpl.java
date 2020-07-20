package com.tosok.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tosok.user.DAO.MapDAO;
import com.tosok.user.Until.WeatherInfo;
import com.tosok.user.VO.MapVO;
import com.tosok.user.service.MapService;

@Service("MapService")
public class MapServiceImpl implements MapService {

    @Resource(name="MapDAO")
    private MapDAO mapDao;

	@Override
	public int insertMapApi(MapVO m_vo) {
		return mapDao.insertMapApi(m_vo);
	}

	@Override
	public List<MapVO> listMapDao(Map<String, Object> paramMap) {
		return mapDao.listMapDao(paramMap);
	}

	@Override
	public int insertMapImg(MapVO m_vo) {
		return mapDao.insertMapImg(m_vo);
	}

	@Override
	public int listMapCnt() {
		return mapDao.listMapCnt();
	}

}
