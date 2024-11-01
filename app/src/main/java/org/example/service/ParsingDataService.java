package org.example.service;

import org.example.vo.PublicData;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

public interface ParsingDataService {
    void saveEmergencyData(PublicData publicData);
    void savePharmacyData(PublicData publicData);

    List<PublicData> getPharmacyList(HashMap<String,Double> wgs);
    List<PublicData> getEmergencyList();
}
