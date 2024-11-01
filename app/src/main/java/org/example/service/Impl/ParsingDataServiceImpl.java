package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.ParsingDataDao;
import org.example.service.ParsingDataService;
import org.example.vo.PublicData;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParsingDataServiceImpl implements ParsingDataService {

    private final ParsingDataDao parsingDataDao;

    @Override
    public void saveEmergencyData(PublicData publicData) {
        parsingDataDao.saveEmergencyData(publicData);
    }

    @Override
    public void savePharmacyData(PublicData publicData) {
        parsingDataDao.savePharmacyData(publicData);
    }

    @Override
    public List<PublicData> getPharmacyList(HashMap<String, Double> wgs) {
        return parsingDataDao.getPharmacyList(wgs);
    }

    @Override
    public List<PublicData> getEmergencyList() {
        return parsingDataDao.getEmergencyList();
    }
}
