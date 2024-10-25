package org.example;

import org.example.vo.PublicData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParsingData {
    public ArrayList<PublicData> getTagValue(NodeList items) {
        ArrayList<PublicData> list = new ArrayList<>();

        for (int i = 0; i < items.getLength(); i++) {
            PublicData publicData = new PublicData();
            Element item = (Element) items.item(i);
            publicData.setWgs84Lon(item.getElementsByTagName("wgs84Lon").item(0).getTextContent());
            publicData.setWgs84Lat(item.getElementsByTagName("wgs84Lat").item(0).getTextContent());
            publicData.setName(item.getElementsByTagName("dutyName").item(0).getTextContent());
            list.add(publicData);
        }
        return list;
    }

    public ArrayList<PublicData> xmlParsing(String urlKey) throws Exception {
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

            datalist = getTagValue(items);

//            for (PublicData data : datalist) {
//                System.out.println(data.getWgs84Lon() + ", " + data.getWgs84Lat());
//            }
        return datalist;
    }

//    public void jsonParsing() throws Exception {
//        String key = "SdeVRGxeat0HFsYbWRArfdmvZr9D7A4%2FN1IDlh2HxrZcOrO4OX51OU%2FswL7U2GWnhJsKoUjz6OxrW05kc7TYlg%3D%3D";
//
//        String urlBuilder =
////                "https://apis.data.go.kr/1741000/pfc2/pfc/getPfctInfo2" + // 전국 어린이 놀이시설
//                        "?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key + /*Service Key*/
//                        "&" + URLEncoder.encode("recordCountPerPage", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8") + /*한 페이지 결과 수*/
//                        "&" + URLEncoder.encode("pageIndex", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"); /*페이지번호*/
//
//        URI uri = new URI(urlBuilder);
//        URL url = uri.toURL();
//
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");  // GET 요청
//        conn.setRequestProperty("Content-type", "application/json");  // 요청 헤더 설정
//
//        int responseCode = conn.getResponseCode();
//        System.out.println("Response Code: " + responseCode);
//
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            // 응답 데이터 읽기
//            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            // 응답 결과 출력
//            System.out.println("Response: " + response.toString());
//        } else {
//            System.out.println("API 호출 실패: " + responseCode);
//        }
//    }
}
