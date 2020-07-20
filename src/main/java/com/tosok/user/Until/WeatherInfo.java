package com.tosok.user.Until;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component("weatherInfo")
public class WeatherInfo implements HttpSessionListener {

	public final static String URL = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4514042000";

	public String xmlParser() {
		String xml = "";
		StringBuilder sb = new StringBuilder();

		try {
			URL url = new URL(URL);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setConnectTimeout(10000);
            http.setUseCaches(false);

            BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));

            while(true) {
            	String line = br.readLine();
            	if (line == null) {
            		break;
            	}

            	sb.append(line);
            }

            xml = sb.toString();

            br.close();
            http.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return xml;
	}

	public Map<String, Object> weatherParam(Map<String, Object> result) {

		try {
			String xml = xmlParser();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentbuilder = factory.newDocumentBuilder();
			InputStream is = new ByteArrayInputStream(xml.getBytes());
			Document doc = documentbuilder.parse(is);
			Element element = doc.getDocumentElement();

			NodeList data = element.getElementsByTagName("data");

			for (Node node = data.item(0).getFirstChild(); node != null; node = node.getNextSibling()) {
				if (node.getNodeName().equals("temp")) { // 온도
					result.put("temp", node.getTextContent().toString());
				}
	 
				if (node.getNodeName().equals("reh")) { // 습도
					result.put("reh", node.getTextContent().toString());
				}
	 
				if (node.getNodeName().equals("wfKor")) { // 날씨 정보
					result.put("wfKor", node.getTextContent().toString());
				}
	                    
				if (node.getNodeName().equals("pop")) { // 강수 확률
					result.put("pop", node.getTextContent().toString());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		Map<String, Object> result = new HashMap<String, Object>();

		weatherParam(result);	// data
		session.setAttribute("weather", result);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
	}
}