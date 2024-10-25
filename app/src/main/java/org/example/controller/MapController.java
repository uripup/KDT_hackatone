package org.example.controller;

import org.example.ParsingData;
import org.example.vo.PublicData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MapController {
    @GetMapping("/map")
    public void map(Model model, String latitude, String longitude) throws Exception {

        String key = "SdeVRGxeat0HFsYbWRArfdmvZr9D7A4%2FN1IDlh2HxrZcOrO4OX51OU%2FswL7U2GWnhJsKoUjz6OxrW05kc7TYlg%3D%3D";

        String urlBuild1 = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyBassInfoInqire" +
                "?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key +
                "&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8") + /*한 페이지 결과 수*/
                "&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"); /*페이지번호*/
        ParsingData data = new ParsingData();

        model.addAttribute("list", data.xmlParsing(urlBuild1));
    }
}
