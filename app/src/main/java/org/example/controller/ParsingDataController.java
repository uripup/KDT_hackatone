package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.ParsingDataService;
import org.example.vo.PublicData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/parsing")
@RequiredArgsConstructor
public class ParsingDataController {

    private final ParsingDataService parsingDataService;

    @GetMapping
    public void home(){}

    @GetMapping("/pharmacy")
    public String savePharmacyData() throws Exception {
        String key = "SdeVRGxeat0HFsYbWRArfdmvZr9D7A4%2FN1IDlh2HxrZcOrO4OX51OU%2FswL7U2GWnhJsKoUjz6OxrW05kc7TYlg%3D%3D";
        String urlBuild1 = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyBassInfoInqire" +
                "?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key +
                "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8");

        System.out.println("컨트롤러 진입");

        List<PublicData> data = parsingData(urlBuild1);
        System.out.println("끌어온 데이터:" + data.size());
        for (PublicData publicData : data) {
            parsingDataService.savePharmacyData(publicData);
        }
        System.out.println("데이터 저장 완료");

        return "redirect:/parsing";
    }

    @GetMapping("/emergency")
    public String saveEmergencyData() throws Exception {
        String key = "SdeVRGxeat0HFsYbWRArfdmvZr9D7A4%2FN1IDlh2HxrZcOrO4OX51OU%2FswL7U2GWnhJsKoUjz6OxrW05kc7TYlg%3D%3D";
        String urlBuild1 = "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytBassInfoInqire" +
                "?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key +
                "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8");

        System.out.println("컨트롤러 진입");

        List<PublicData> data = parsingData(urlBuild1);

        parsingData(urlBuild1);
        System.out.println("끌어온 데이터:" + data.size());
        for (PublicData publicData : data) {
            parsingDataService.saveEmergencyData(publicData);
        }
        System.out.println("데이터 저장 완료");

        return "redirect:/parsing";
    }

    public List<PublicData> parsingData(String url) throws Exception {
        System.out.println("Parsing data...");
        int pageNo = 1;
        int pageSize = 1000;
        List<PublicData> allData = new ArrayList<>();


        while (true) {
            System.out.println(pageNo);

            allData.addAll(fetchAllData(url + "&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNo), "UTF-8") /*페이지 번호*/));

            System.out.println(allData.size());

            if (allData.size() < pageNo * pageSize) {
                System.out.println("끝 " + allData.size());
                break;
            }

            pageNo++;
        }

        System.out.println("총 데이터 개수: " + allData.size());
        return allData;
    }

    public List<PublicData> fetchAllData(String urlKey) throws Exception {
        List<PublicData> list = new ArrayList<>();

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
        System.out.println(document.getElementsByTagName("totalCount").item(0).getTextContent());

        NodeList items = document.getElementsByTagName("item");

        for (int i = 0; i < items.getLength(); i++) {
            PublicData publicData = new PublicData();
            Element item = (Element) items.item(i);
//            System.out.println(item);


            NodeList test = item.getElementsByTagName("wgs84Lon");

            if (test.getLength() > 0){
                publicData.setWgs84Lon(item.getElementsByTagName("wgs84Lon").item(0).getTextContent());
                publicData.setWgs84Lat(item.getElementsByTagName("wgs84Lat").item(0).getTextContent());
                publicData.setName(item.getElementsByTagName("dutyName").item(0).getTextContent());
                publicData.setRnum(item.getElementsByTagName("hpid").item(0).getTextContent());
            }else {
                publicData.setWgs84Lon("0");
                publicData.setWgs84Lat("0");
                publicData.setName(item.getElementsByTagName("dutyName").item(0).getTextContent());
                publicData.setRnum(item.getElementsByTagName("hpid").item(0).getTextContent());
            }

            list.add(publicData);
        }
        return list;
    }

//
//    public HashMap<String, Object> getTagValue(NodeList items) {
//        HashMap<String, Object> mapData = new HashMap<>();
//
//        for (int i = 0; i < items.getLength(); i++) {
//            PublicData publicData = new PublicData();
//            Element item = (Element) items.item(i);
//            publicData.setWgs84Lon(item.getElementsByTagName("wgs84Lon").item(0).getTextContent());
//            publicData.setWgs84Lat(item.getElementsByTagName("wgs84Lat").item(0).getTextContent());
//            publicData.setName(item.getElementsByTagName("dutyName").item(0).getTextContent());
//            mapData.put(String.valueOf(i), publicData);
//        }
//        return mapData;
//    }
//
//    public void saveData(HashMap<String, Object> dataMap) throws IOException {
//        Properties props = new Properties();
//        try (InputStream is = getClass().getResourceAsStream("application.properties")) {
//            props.load(is);
//        }
//
//        String url = props.getProperty("spring.datasource.url");
//        String username = props.getProperty("spring.datasource.username");
//        String password = props.getProperty("spring.datasource.password");
//
//        System.out.println(url);
//        System.out.println(username);
//        System.out.println(password);
//
//
//    }
//
//    public HashMap<String, Object> xmlParsing(String urlKey) throws Exception {
//        ArrayList<PublicData> datalist = new ArrayList<>();
//
//        String id = "10000695";
//        String authKey = "83341d53c27445d0";
//
//        /*URL*/
////            String urlBuilder =
////                    "http://api.data.go.kr/openapi/tn_pubr_public_cty_park_info_api" + // 공원
////                    "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire" + // 약국
////                    "http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytBassInfoInqire" + // 응급실
////                    "http://apis.data.go.kr/B553881/Parking/PrkSttusInfo" + // 주차장(불가)
////                    "http://api.data.go.kr/openapi/tn_pubr_public_cltur_fstvl_api" + // 축제
////                    "https://www.safe182.go.kr/api/lcm/safeMap.do" + // 안전 지도 정보
////                            "?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + "key" + /*Service Key*/
////                            "&" + URLEncoder.encode("esntlId", "UTF-8") + "=" + id + /*id Key*/
////                            "&" + URLEncoder.encode("authKey", "UTF-8") + "=" + authKey; /*Service Key*/
////                            "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8") + /*한 페이지 결과 수*/
////                            "&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"); /*페이지번호*/
////                            "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"); /*XML 여부*/
////                            "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"); /*XML 여부*/
//
//        URI uri = new URI(urlKey);
//        URL url = uri.toURL();
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/xml");
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//        String inputLine;
//        StringBuilder response = new StringBuilder();
//        while ((inputLine = br.readLine()) != null) {
//            response.append(inputLine);
//        }
//        br.close();
//
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document document = builder.parse(new java.io.ByteArrayInputStream(response.toString().getBytes("UTF-8")));
//
//        document.getDocumentElement().normalize();
//
//        System.out.println(Integer.parseInt(document.getElementsByTagName("totalCount").item(0).getTextContent()));
//
//        NodeList items = document.getElementsByTagName("item");
//
////            saveData(getTagValue(items));
//
////            for (PublicData data : datalist) {
////                System.out.println(data.getWgs84Lon() + ", " + data.getWgs84Lat());
////            }
//        return getTagValue(items);
//    }

}
