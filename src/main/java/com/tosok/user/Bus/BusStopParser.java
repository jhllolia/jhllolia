package com.tosok.user.Bus;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

@Component("busStopParser")
public class BusStopParser {
	
	public List<Map<String,Object>> apiParserNodeRealTime(Map<String,Object> map) throws Exception {

		String key = (String) map.get("KEY");
		String nodeId = (String) map.get("NODEID");
		String cityCode = (String) map.get("cityCode");

		String apiUrl = "http://openapi.tago.go.kr/openapi/service/ArvlInfoInqireService/getSttnAcctoArvlPrearngeInfoList";
		URL url = new URL(apiUrl + "?ServiceKey=" + key + "&nodeId=" + nodeId + "&cityCode=" + cityCode);

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);

        XmlPullParser xpp = factory.newPullParser();
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        xpp.setInput(bis, "utf-8");

        String tag = null;
        int event_type = xpp.getEventType();

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> tempMap = null;

        while (event_type != XmlPullParser.END_DOCUMENT) {
        	if (event_type == XmlPullParser.START_TAG) {
        		tag = xpp.getName();

        		if(tag.equals("item")) {
        			tempMap = new HashMap<String,Object>();
        		}
        	} else if (event_type == XmlPullParser.TEXT) {
        		if(tag.equals("nodeid")) {
        			tempMap.put("NODEID", xpp.getText());
        		} else if(tag.equals("routeid")) {
        			tempMap.put("ROUTEID", xpp.getText());
        		} else if(tag.equals("routeno")) {
        			tempMap.put("ROUTENO", xpp.getText());
        		} else if(tag.equals("routetp")) {
        			tempMap.put("ROUTETP", xpp.getText());
        		} else if(tag.equals("arrprevstationcnt")) {
        			tempMap.put("ARRPREV", xpp.getText());
        		} else if(tag.equals("arrtime")) {
        			tempMap.put("ARRTIME", xpp.getText());
        		}
        	} else if (event_type == XmlPullParser.END_TAG) {
        		tag = xpp.getName();
        		if (tag.equals("item")) {
        			list.add(tempMap);
        		}
        	}
        	
        	event_type = xpp.next();
        }
        
        System.out.println(list);

        bis.close();
        return list;
	}
}
