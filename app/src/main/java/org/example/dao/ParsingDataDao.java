package org.example.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.vo.PublicData;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ParsingDataDao {
    void saveEmergencyData(PublicData publicData);
    void savePharmacyData(PublicData publicData);

    List<PublicData> getPharmacyList(HashMap<String, Double> wgs);
    List<PublicData> getEmergencyList();
}
