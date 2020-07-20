package com.tosok.user.Bus;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.tosok.user.service.GCBusService;

@Component("nodeToRoute")
public class NodeToRoute {
	// 정류장을 거쳐가는 노선

	@Autowired
	private GCBusService GCBusService;

	public List<Map<String,Object>> insertInfo(Map<String,Object> map) throws Exception {

		int ROUTE1_Num = 30;
		int ROUTE2_Num = 10;

		String key = (String) map.get("KEY");
		String cityCode = (String) map.get("cityCode");
		String numOfRows = (String) map.get("numOfRows");

		String ROUTE1_URL = "http://openapi.tago.go.kr/openapi/service/BusRouteInfoInqireService/getRouteNoList?ServiceKey=" + key + "&cityCode=" + cityCode + "&numOfRows=" + ROUTE1_Num + "&routeId=";
		String ROUTE2_URL = "http://openapi.tago.go.kr/openapi/service/BusRouteInfoInqireService/getRouteAcctoThrghSttnList?ServiceKey=" + key + "&cityCode=" + cityCode + "&numOfRows=" + ROUTE2_Num + "&routeId=";

		URL url = new URL(ROUTE1_URL);

		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);

		XmlPullParser xpp = factory.newPullParser();
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		xpp.setInput(bis, "utf-8");

		String tag = null;
        int event_type = xpp.getEventType();
        
        List<Map<String,Object>> routeList = new ArrayList<Map<String,Object>>();
        Map<String,Object> routeMap = null;
        
        while (event_type != XmlPullParser.END_DOCUMENT) {
        	if (event_type == XmlPullParser.START_TAG) {
        		tag = xpp.getName();
        		if(tag.equals("item")) {
        			routeMap = new HashMap<String,Object>();
        		}
        	} else if (event_type == XmlPullParser.TEXT) {
        		if(tag.equals("routeid")){
        			routeMap.put("routeid",xpp.getText());
        		} else if(tag.equals("routeno")) {
        			routeMap.put("routeno",xpp.getText());
        		}
        	} else if (event_type == XmlPullParser.END_TAG) {
        		tag = xpp.getName();
        		if(tag.equals("item")) {
        			routeList.add(routeMap);
        		}
        	}

        	event_type = xpp.next();
       }

       bis.close();

       Iterator<Map<String,Object>> iterator = routeList.iterator();
       List<Map<String,Object>> info = new ArrayList<Map<String,Object>>();

       Map<String,Object> nodeMap = null;
       Map<String,Object> temp = null;
       
       while(iterator.hasNext()) {
    	   temp = iterator.next();

    	   bis = new BufferedInputStream(url.openStream());
    	   xpp.setInput(bis, "utf-8");
    	   tag = null;
    	   event_type = xpp.getEventType();
    	   
    	   while (event_type != XmlPullParser.END_DOCUMENT) {
    		   if (event_type == XmlPullParser.START_TAG) {
    			   tag = xpp.getName();
    			   if(tag.equals("item")) {
    				   nodeMap = new HashMap<String,Object>();
    				   nodeMap.put("ROUTENO", temp.get("routeno"));
    			   }
    		   } else if (event_type == XmlPullParser.TEXT) {
    			   if(tag.equals("nodeid")) {
    				   nodeMap.put("NODEID", xpp.getText());
    			   } else if(tag.equals("nodenm")) {
    				   nodeMap.put("NODENAME", xpp.getText());
                   } else if(tag.equals("routeid")) {
                	   nodeMap.put("ROUTEID", xpp.getText());
                   }
    		   } else if (event_type == XmlPullParser.END_TAG) {
    			   tag = xpp.getName();
    			   if (tag.equals("item")) {
    				   info.add(nodeMap);
    			   }
    		   }

               event_type = xpp.next();
    	   }

    	   url = new URL(getURLParam(ROUTE2_URL, (String)temp.get("routeid")));
    	   GCBusService.regRouteToOrder(map, url);		// regRouteToOrder Service 로 이동

    	   System.out.println("regRouteToOrder : " + url);

    	   url = new URL(getRouteParam(ROUTE1_URL, (String)temp.get("routeid")));
    	   GCBusService.regRouteInfo(map, url);		// regRouteToOrder Service 로 이동

    	   System.out.println("regRouteInfo : " + url);
       }

       bis.close();
       return info;
	}

	private String getRouteParam(String ROUTE1_URL, String object) {
		String url = ROUTE1_URL + object;
		return url;
	}

	private String getURLParam(String ROUTE2_URL, String object) {
		String url = ROUTE2_URL + object;
		return url;
	}


}
