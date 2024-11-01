package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.ParsingDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {

    private final ParsingDataService parsingDataService;

    // 데이터 파싱 및 세션에 저장 후 리다이렉트
    @GetMapping("/data")
    @ResponseBody
    public HashMap<String, Object> getDataAndRedirect(
            HttpSession session,
            @RequestParam("wgsLat") double wgsLat,
            @RequestParam("wgsLon") double wgsLon) {

        HashMap<String, Object> data = new HashMap<>();

        data.put("data", parsingDataService.getPharmacyList(calculateCoordinateRange(wgsLat, wgsLon, 200)));

        session.setAttribute("data", data);

        return data;
    }

    public HashMap<String, Double> calculateCoordinateRange(double lat, double lon, double radius) {
        final double latDiff = radius / 111320.0; // 위도 차이
        final double lonDiff = radius / (111320.0 * Math.cos(Math.toRadians(lat))); // 경도 차이
        HashMap<String, Double> radiusMap = new HashMap<>();

        radiusMap.put("minLat", lat - latDiff);
        radiusMap.put("maxLat", lat + latDiff);
        radiusMap.put("minLon", lon - lonDiff);
        radiusMap.put("maxLon", lon + lonDiff);

        return radiusMap;
    }


    // 세션에 저장된 데이터를 가져와 화면에 전달
    @GetMapping
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