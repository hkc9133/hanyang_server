package com.hanyang.startup.hanyangstartup.keyword.service;

import com.hanyang.startup.hanyangstartup.keyword.dao.KeywordDao;
import com.hanyang.startup.hanyangstartup.keyword.domain.Keyword;
import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeywordService {

    @Autowired
    private KeywordDao keywordDao;

    @Autowired
    private FileSaveService fileSaveService;

    @Transactional(rollbackFor = {Exception.class})
    public void addKeyword(Keyword keyword){

        keywordDao.addKeyword(keyword);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void updateKeyword(Keyword keyword){
        keywordDao.updateKeyword(keyword);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void updateKeywordSearch(Keyword keyword){

        keywordDao.updateKeywordSearch(keyword);
    }

    public Keyword getKeyword(Keyword keyword){
        return keywordDao.getKeyword(keyword);
    }
    public Map<String, Object> getKeywordList(Keyword keyword){

        keyword.setTotalCount(keywordDao.getKeywordListCnt(keyword));
        List<Keyword> keywordList = keywordDao.getKeywordList(keyword);


        Map<String, Object> map = new HashMap<>();

        map.put("page", keyword);
        map.put("list", keywordList);

        return map;

    }

    public void deleteKeyword(Keyword keyword){
        keywordDao.deleteKeyword(keyword);
    }
}
