package com.hanyang.startup.hanyangstartup.startupPresent.dao;

import com.hanyang.startup.hanyangstartup.startupPresent.domain.StartupPresent;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StartupPresentDao {
    @Autowired
    protected SqlSessionTemplate sqlSession;
    public void addStartupPresent(StartupPresent startupPresent){
        sqlSession.insert("startup_present.addStartupPresent", startupPresent);
    }
    public void updateStartupPresent(StartupPresent startupPresent){
        sqlSession.update("startup_present.updateStartupPresent", startupPresent);
    }

    public StartupPresent getStartupPresent(StartupPresent startupPresent){
        return sqlSession.selectOne("startup_present.getStartupPresent", startupPresent);
    }
    public List<StartupPresent> getStartupPresentList(StartupPresent startupPresent){
        return sqlSession.selectList("startup_present.getStartupPresentList", startupPresent);
    }

    public int getStartupPresentListCnt(StartupPresent startupPresent){
        return sqlSession.selectOne("startup_present.getStartupPresentListCnt", startupPresent);
    }


    public int deleteStartupPresent(StartupPresent startupPresent){
        return sqlSession.delete("startup_present.deleteStartupPresent", startupPresent);
    }
}
