package com.tosok.user.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tosok.user.Bus.BusStopParser;
import com.tosok.user.service.GCBusService;

@Controller
public class GCBusController {
	protected Log log = LogFactory.getLog(GCBusController.class);

	public final static String KEY = "qTGclt%2B%2FIJm0f%2B5YeUwWbB4cgbfMPNcXXvb%2FDCCTgWyfsM027VSfner0C%2BblfLHIJSNcFZM5TS1w7z8aMZvxqQ%3D%3D";
	public final static String cityCode = "35030";
	public final static String numOfRows = "1400";

    @Autowired
    private GCBusService gcBusServ;

    @Autowired
    private BusStopParser busStopParser;

    @RequestMapping(value = "/stationName")
    @ResponseBody
    public String stationName(@RequestBody Map<String, Object> paramMap) throws Exception {
      	ObjectMapper mapper = new ObjectMapper();
    	List<Map<String, Object>> list = gcBusServ.selectStation(paramMap);

		String str = "";

		try {
			str = mapper.writeValueAsString(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		log.info("stationName : " + paramMap);
    	return str;
    }

    @RequestMapping(value = "/busList")
    @ResponseBody
    public String busList(@RequestBody Map<String, Object> paramMap) throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> list = gcBusServ.busList(paramMap);

		String str = "";

		try {
			str = mapper.writeValueAsString(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		log.info("busList : " + str);
		return str;
    }
    
    @RequestMapping(value = "/busRealTime")
    @ResponseBody
    public String busRealTime(@RequestBody Map<String, Object> paramMap) throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
 
		paramMap.put("KEY", KEY);
		paramMap.put("cityCode", cityCode);

    	List<Map<String, Object>> list = busStopParser.apiParserNodeRealTime(paramMap);

    	String str = "";

    	try {
    		str = mapper.writeValueAsString(list);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}

		log.info("busRealTime : " + str);
    	return str;
    }

    @RequestMapping(value = "/nodeToRoute")
    @ResponseBody
    public String nodeToRoute(@RequestBody Map<String, Object> paramMap) throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
 
    	List<Map<String, Object>> list = gcBusServ.nodeToRouteList(paramMap);

    	String str = "";

    	try {
    		str = mapper.writeValueAsString(list);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}

		log.info("nodeToRoute : " + str);
    	return str;
    }

    @RequestMapping(value = "/routeInfoPage")
    @ResponseBody
    public Map<String, Object> routeInfoPage(@RequestBody Map<String, Object> paramMap) throws Exception {
    	Map<String,Object> map = new HashMap<String,Object>();

    	map.put("info", gcBusServ.selectRouteId(paramMap));
    	map.put("path", gcBusServ.selectRoutePath(paramMap));

		log.info("routeInfoPage : " + map);
    	return map;
    }

	@RequestMapping("/map")
	public ModelAndView map(@RequestParam Map<String, Object> paramMap, Model model) throws Exception {
		ModelAndView mav = new ModelAndView();

		paramMap.put("KEY", KEY);
		paramMap.put("cityCode", cityCode);
		paramMap.put("numOfRows", numOfRows);

		/*
		
		gcBusServ.regNodeToRoute(paramMap); // 됨 트래픽 조심
		gcBusServ.regNodeInfo(paramMap); // 됨 트래픽 조심

		*/

		mav.addObject("map", paramMap);
		return mav;
	}

}
 