package com.tosok.user.DAO;

import java.util.List;
import java.util.Map;

public interface GCBusDAO {

	public List<Map<String, Object>> busList(Map<String, Object> paramMap);
	public List<Map<String, Object>> selectNodeId(Map<String, Object> paramMap);
	public List<Map<String, Object>> selectRoutePath(Map<String, Object> paramMap);
	public List<Map<String, Object>> selectStation(Map<String, Object> paramMap);

	public Map<String, Object> selectRouteId(Map<String, Object> paramMap);

	public void insertNodeRouteInfo(Map<String, Object> map);	// bus
	public void insertRouteOrder(Map<String, Object> map);		// bus
	public void insertRouteInfo(Map<String, Object> map);		// bus
	public void insertNodeInfo(Map<String, Object> map);		// bus

}
