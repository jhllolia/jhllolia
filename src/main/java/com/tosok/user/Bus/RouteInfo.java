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

@Component("routeInfo")
public class RouteInfo {
	// 노선 정보

	public List<Map<String,Object>> insertInfo(Map<String,Object> map, URL routeUrl) throws Exception {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);

		XmlPullParser xpp = factory.newPullParser();
		BufferedInputStream bis = new BufferedInputStream(routeUrl.openStream());
		xpp.setInput(bis, "utf-8");
		        
		String tag = null;
		int event_type = xpp.getEventType();
		        
		Map<String,Object> tempMap = null;
		while (event_type != XmlPullParser.END_DOCUMENT) {
			if (event_type == XmlPullParser.START_TAG) {
				tag = xpp.getName();
				if(tag.equals("item")) {
        			tempMap = new HashMap<String,Object>();
        		}
	        } else if (event_type == XmlPullParser.TEXT) {
				if(tag.equals("routeid")) {
					tempMap.put("ROUTEID", xpp.getText());
				} else if(tag.equals("routeno")) {
					tempMap.put("ROUTENO", xpp.getText());
				} else if(tag.equals("startnodenm")) {
					tempMap.put("STARTNODENM", xpp.getText());
				} else if(tag.equals("endnodenm")){
					tempMap.put("ENDNODENM", xpp.getText());
				} else if(tag.equals("startvehicletime")) {
					tempMap.put("STARTVEHICLETIME", xpp.getText());
				} else if(tag.equals("endvehicletime")) {
					tempMap.put("ENDVEHICLETIME", xpp.getText());
				}
	        } else if (event_type == XmlPullParser.END_TAG) {
	        	tag = xpp.getName();
	        	if (tag.equals("item")) {
	        		list.add(tempMap);
	        	}
	        }

        	event_type = xpp.next();
        }

        bis.close();		
        return list;
	}
}
