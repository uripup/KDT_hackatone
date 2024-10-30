package org.example.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.vo.PublicData;

@Mapper
public interface ParsingDataDao {
    void saveData(PublicData publicData);
}
