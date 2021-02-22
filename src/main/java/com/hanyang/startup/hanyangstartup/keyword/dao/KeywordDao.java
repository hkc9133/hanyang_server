package com.hanyang.startup.hanyangstartup.keyword.dao;

import com.hanyang.startup.hanyangstartup.keyword.domain.Keyword;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KeywordDao {
    @Autowired
    protected SqlSessionTemplate sqlSession;
    public void addKeyword(Keyword keyword){
        sqlSession.insert("keyword.addKeyword", keyword);
    }
    public void updateKeyword(Keyword keyword){
        sqlSession.update("keyword.updateKeyword", keyword);
    }

    public void updateKeywordSearch(Keyword keyword){
        sqlSession.insert("keyword.updateKeywordSearch", keyword);
    }

    public Keyword getKeyword(Keyword keyword){
        return sqlSession.selectOne("keyword.getKeyword", keyword);
    }
    public List<Keyword> getKeywordList(Keyword keyword){
        return sqlSession.selectList("keyword.getKeywordList", keyword);
    }

    public int getKeywordListCnt(Keyword keyword){
        return sqlSession.selectOne("keyword.getKeywordListCnt", keyword);
    }


    public int deleteKeyword(Keyword keyword){
        return sqlSession.delete("keyword.deleteKeyword", keyword);
    }
}
