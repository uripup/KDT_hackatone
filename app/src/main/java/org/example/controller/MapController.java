package org.example.controller;

import org.example.ParsingData;
import org.example.vo.PublicData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("data") // 변경: @RestController -> @Controller
public class MapController {

    // 데이터 파싱 및 세션에 저장 후 리다이렉트
    @GetMapping("/map/data")
    @ResponseBody
    public HashMap<String, Object> getDataAndRedirect(HttpSession session) throws Exception {
        HashMap<String, Object> data = new HashMap<>();

        String key = "SdeVRGxeat0HFsYbWRArfdmvZr9D7A4%2FN1IDlh2HxrZcOrO4OX51OU%2FswL7U2GWnhJsKoUjz6OxrW05kc7TYlg%3D%3D";
        String urlBuild1 = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyBassInfoInqire" +
                "?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key +
                "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8") +
                "&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");

        ParsingData parsingData = new ParsingData();
        data.put("data", parsingData.xmlParsing(urlBuild1));

        return data;
    }

    // 세션에 저장된 데이터를 가져와 화면에 전달
    @GetMapping("/map")
    public String showMap(Model model, HttpSession session) {
        // 세션에서 데이터 가져오기
        Map<String, Object> parsedData = (Map<String, Object>) session.getAttribute("data");
        if (parsedData != null) {
            model.addAttribute("parsedData", parsedData);
        }

        // map.html로 이동
        return "map";
    }
}