package com.hanyang.startup.hanyangstartup.startupPresent.dao;

import com.hanyang.startup.hanyangstartup.startupPresent.domain.BusinessFieldCode;
import com.hanyang.startup.hanyangstartup.startupPresent.domain.GubunCount;
import com.hanyang.startup.hanyangstartup.startupPresent.domain.StartupPresent;
import com.hanyang.startup.hanyangstartup.startupPresent.domain.TechFieldCode;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StartupPresentDao {
    @Autowired
    protected SqlSessionTemplate sqlSession;

    public List<BusinessFieldCode> getBusinessFieldList(){
        return sqlSession.selectList("startup_present.getBusinessFieldList");
    }

    public List<TechFieldCode> getTechFieldList(){
        return sqlSession.selectList("startup_present.getTechFieldList");
    }

    public void addStartupPresent(StartupPresent startupPresent){
        sqlSession.insert("startup_present.addStartupPresent", startupPresent);
    }

    public void addBusinessFieldList(StartupPresent startupPresent){
        sqlSession.insert("startup_present.addBusinessFieldList", startupPresent);
    }
    public void deleteBusinessFieldList(StartupPresent startupPresent){
        sqlSession.insert("startup_present.deleteBusinessFieldList", startupPresent);
    }

    public void addTechFieldList(StartupPresent startupPresent){
        sqlSession.insert("startup_present.addTechFieldList", startupPresent);
    }

    public void deleteTechFieldList(StartupPresent startupPresent){
        sqlSession.insert("startup_present.deleteTechFieldList", startupPresent);
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

    public List<StartupPresent> getBestStartupList(){
        return sqlSession.selectList("startup_present.getBestStartupList");
    }
    public List<GubunCount> getStartupGubunCnt(){
        return sqlSession.selectList("startup_present.getStartupGubunCnt");
    }

}
