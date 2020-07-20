package com.tosok.user.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tosok.user.Bus.NodeInfo;
import com.tosok.user.Bus.NodeToRoute;
import com.tosok.user.Bus.RouteInfo;
import com.tosok.user.Bus.RouteToOrder;
import com.tosok.user.DAO.GCBusDAO;
import com.tosok.user.service.GCBusService;

import java.net.URL;

@Service("GCBusService")
public class GCBusServiceImpl implements GCBusService {

    @Resource(name="GCBusDAO")
    private GCBusDAO gcBusDAO;
    
    @Resource(name="nodeToRoute")
    private NodeToRoute nodeToRoute;

    @Resource(name="routeToOrder")
    private RouteToOrder routeToOrder;
     
    @Resource(name="routeInfo")
    private RouteInfo routeInfo;

    @Resource(name="nodeInfo")
    private NodeInfo nodeInfo;

	@Override
	public List<Map<String, Object>> busList(Map<String, Object> paramMap) {
		return gcBusDAO.busList(paramMap);
	}

	@Override
	public List<Map<String, Object>> nodeToRouteList(Map<String, Object> paramMap) {
		return gcBusDAO.selectNodeId(paramMap);
	}

	@Override
	public int regNodeInfo(Map<String,Object> map) throws Exception {
		List<Map<String,Object>> list = nodeInfo.insertInfo(map);
		Iterator<Map<String,Object>> iterator = list.iterator();
		Map<String,Object> tmap = null;

		System.out.println("regNodeInfo : " + list);

		while(iterator.hasNext()) {
			tmap = iterator.next();
	        gcBusDAO.insertNodeInfo(tmap);
		}

		return Integer.valueOf(list.size());
	}

	@Override
	public int regRouteInfo(Map<String,Object> map, URL url) throws Exception {
		List<Map<String,Object>> list = routeInfo.insertInfo(map, url);
		Iterator<Map<String,Object>> iterator = list.iterator();
		Map<String,Object> tmap = null;

		System.out.println("regRouteInfo : " + list);

		while(iterator.hasNext()) {
			tmap = iterator.next();
	        gcBusDAO.insertRouteInfo(tmap);
		}

		return Integer.valueOf(list.size());
	}

	@Override
	public int regRouteToOrder(Map<String,Object> map, URL url) throws Exception {
		List<Map<String,Object>> list = routeToOrder.insertInfo(map, url);
		Iterator<Map<String,Object>> iterator = list.iterator();
		Map<String,Object> tmap = null;

		System.out.println("regRouteToOrder : " + list);

		while(iterator.hasNext()) {
			tmap = iterator.next();
	        gcBusDAO.insertRouteOrder(tmap);
		}

		return Integer.valueOf(list.size());
	}

	@Override
	public int regNodeToRoute(Map<String,Object> map) throws Exception {
		List<Map<String,Object>> list = nodeToRoute.insertInfo(map);
		Iterator<Map<String,Object>> iterator = list.iterator();
		Map<String,Object> tmap = null;

		/* System.out.println("regNodeToRoute : " + list); */

		while(iterator.hasNext()) {
			tmap = iterator.next();
	        gcBusDAO.insertNodeRouteInfo(tmap);
		}
		
		return Integer.valueOf(list.size());
	}

	@Override
	public Map<String, Object> selectRouteId(Map<String, Object> paramMap) {
		return gcBusDAO.selectRouteId(paramMap);
	}

	@Override
	public List<Map<String, Object>> selectRoutePath(Map<String, Object> paramMap) {
		return gcBusDAO.selectRoutePath(paramMap);
	}

	@Override
	public List<Map<String, Object>> selectStation(Map<String, Object> paramMap) {
		return gcBusDAO.selectStation(paramMap);
	}

}
