package com.tosok.user.service;

import java.net.URL;
import java.util.List;
import java.util.Map;

public interface GCBusService {

	public List<Map<String, Object>> busList(Map<String, Object> paramMap);
	public List<Map<String, Object>> nodeToRouteList(Map<String, Object> paramMap);
	public List<Map<String, Object>> selectRoutePath(Map<String, Object> paramMap);
	public List<Map<String, Object>> selectStation(Map<String, Object> paramMap);

	public Map<String, Object> selectRouteId(Map<String, Object> paramMap);

	public int regNodeToRoute(Map<String,Object> map) throws Exception;
	public int regRouteToOrder(Map<String,Object> map, URL url) throws Exception;
	public int regRouteInfo(Map<String,Object> map, URL url) throws Exception;
	public int regNodeInfo(Map<String, Object> map) throws Exception;

}
