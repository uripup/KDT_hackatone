package org.example;

import org.w3c.dom.Document;
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
    public ArrayList<HashMap<String, String>> getTagValue(Document document, String tag) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        NodeList nodeList = document.getElementsByTagName(tag);
        for (int i = 0; i < nodeList.getLength(); i++) {
            HashMap<String, String> map = new HashMap<>();
            Node node = nodeList.item(i);
            for (int j = 0; j < node.getChildNodes().getLength(); j++) {
                map.put(node.getChildNodes().item(j).getNodeName(), node.getChildNodes().item(j).getTextContent());
            }
            list.add(map);
        }

        return list;
    }

    public void xmlParsing(String urlKey, String tag) throws Exception {
        ArrayList<HashMap<String, String>> mapList = new ArrayList<>();
        for (int page = 1; page <= 10; page++) {
            String key = "SdeVRGxeat0HFsYbWRArfdmvZr9D7A4%2FN1IDlh2HxrZcOrO4OX51OU%2FswL7U2GWnhJsKoUjz6OxrW05kc7TYlg%3D%3D";

            String id = "10000695";
            String authKey = "83341d53c27445d0";

            /*URL*/
            String urlBuilder =
//                    "http://api.data.go.kr/openapi/tn_pubr_public_cty_park_info_api" + // 공원
//                    "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire" + // 약국
//                    "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytBassInfoInqire" + // 응급실
//                    "http://apis.data.go.kr/B553881/Parking/PrkSttusInfo" + // 주차장(불가)
//                    "http://api.data.go.kr/openapi/tn_pubr_public_cltur_fstvl_api" + // 축제
                    "https://www.safe182.go.kr/api/lcm/safeMap.do" + // 안전 지도 정보
//                            "?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key + /*Service Key*/
                            "&" + URLEncoder.encode("esntlId", "UTF-8") + "=" + id + /*id Key*/
                            "&" + URLEncoder.encode("authKey", "UTF-8") + "=" + authKey; /*Service Key*/
//                            "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8") + /*한 페이지 결과 수*/
//                            "&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(page), "UTF-8"); /*페이지번호*/
//                            "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"); /*XML 여부*/
//                            "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"); /*XML 여부*/


            URI uri = new URI(urlBuilder);
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
//            mapList = getTagValue(document, "item");
            mapList = getTagValue(document, "item");
        }

        for (Map<String, String> map : mapList) {
            System.out.println(map);
        }
    }

    public void jsonParsing() throws Exception {
        String key = "SdeVRGxeat0HFsYbWRArfdmvZr9D7A4%2FN1IDlh2HxrZcOrO4OX51OU%2FswL7U2GWnhJsKoUjz6OxrW05kc7TYlg%3D%3D";

        String urlBuilder =
//                "https://apis.data.go.kr/1741000/pfc2/pfc/getPfctInfo2" + // 전국 어린이 놀이시설
                        "?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key + /*Service Key*/
                        "&" + URLEncoder.encode("recordCountPerPage", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8") + /*한 페이지 결과 수*/
                        "&" + URLEncoder.encode("pageIndex", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"); /*페이지번호*/

        URI uri = new URI(urlBuilder);
        URL url = uri.toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");  // GET 요청
        conn.setRequestProperty("Content-type", "application/json");  // 요청 헤더 설정

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 응답 데이터 읽기
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 응답 결과 출력
            System.out.println("Response: " + response.toString());
        } else {
            System.out.println("API 호출 실패: " + responseCode);
        }
    }
}
