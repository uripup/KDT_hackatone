package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.ParsingDataDao;
import org.example.service.ParsingDataService;
import org.example.vo.PublicData;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParsingDataServiceImpl implements ParsingDataService {

    private final ParsingDataDao parsingDataDao;

    @Override
    public void saveData(PublicData publicData) {
        parsingDataDao.saveData(publicData);
    }
}
