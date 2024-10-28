package org.example;

import org.example.vo.PublicData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class ParsingData {
    public HashMap<String, Object> getTagValue(NodeList items) {
        HashMap<String, Object> mapData = new HashMap<>();

        for (int i = 0; i < items.getLength(); i++) {
            PublicData publicData = new PublicData();
            Element item = (Element) items.item(i);
            publicData.setWgs84Lon(item.getElementsByTagName("wgs84Lon").item(0).getTextContent());
            publicData.setWgs84Lat(item.getElementsByTagName("wgs84Lat").item(0).getTextContent());
            publicData.setName(item.getElementsByTagName("dutyName").item(0).getTextContent());
            mapData.put(String.valueOf(i), publicData);
        }
        return mapData;
    }

    public void saveData(HashMap<String, Object> dataMap) throws IOException {
        Properties props = new Properties();
        try (InputStream is = getClass().getResourceAsStream("application.properties")) {
            props.load(is);
        }

        String url  = props.getProperty("spring.datasource.url");
        String username  = props.getProperty("spring.datasource.username");
        String password  = props.getProperty("spring.datasource.password");

        System.out.println(url);
        System.out.println(username);
        System.out.println(password);


    }

    public HashMap<String, Object> xmlParsing(String urlKey) throws Exception {
        ArrayList<PublicData> datalist = new ArrayList<>();

            String id = "10000695";
            String authKey = "83341d53c27445d0";

            /*URL*/
//            String urlBuilder =
//                    "http://api.data.go.kr/openapi/tn_pubr_public_cty_park_info_api" + // 공원
//                    "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire" + // 약국
//                    "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytBassInfoInqire" + // 응급실
//                    "http://apis.data.go.kr/B553881/Parking/PrkSttusInfo" + // 주차장(불가)
//                    "http://api.data.go.kr/openapi/tn_pubr_public_cltur_fstvl_api" + // 축제
//                    "https://www.safe182.go.kr/api/lcm/safeMap.do" + // 안전 지도 정보
//                            "?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + "key" + /*Service Key*/
//                            "&" + URLEncoder.encode("esntlId", "UTF-8") + "=" + id + /*id Key*/
//                            "&" + URLEncoder.encode("authKey", "UTF-8") + "=" + authKey; /*Service Key*/
//                            "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8") + /*한 페이지 결과 수*/
//                            "&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"); /*페이지번호*/
//                            "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"); /*XML 여부*/
//                            "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"); /*XML 여부*/

            URI uri = new URI(urlKey);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/xml");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new java.io.ByteArrayInputStream(response.toString().getBytes("UTF-8")));

            document.getDocumentElement().normalize();

            System.out.println(document.getDocumentElement().getNodeName());

            NodeList items = document.getElementsByTagName("item");

//            saveData(getTagValue(items));

//            for (PublicData data : datalist) {
//                System.out.println(data.getWgs84Lon() + ", " + data.getWgs84Lat());
//            }
        return getTagValue(items);
    }
}
