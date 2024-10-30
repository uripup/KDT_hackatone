package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

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

        data.put("data", null);

        session.setAttribute("data", data);

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