package com.hanyang.startup.hanyangstartup.startupEvent.dao;

import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.startupEvent.domain.StartupEvent;
import com.hanyang.startup.hanyangstartup.startupEvent.domain.StartupEventCategoryCode;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StartupEventDao {
    @Autowired
    protected SqlSessionTemplate sqlSession;

    public List<StartupEvent> getStartupEventList(StartupEvent startupEvent){
        return sqlSession.selectList("startup_event.getStartupEventList",startupEvent);
    }

    public int getStartupEventListCnt(StartupEvent startupEvent){
        return sqlSession.selectOne("startup_event.getStartupEventListCnt",startupEvent);
    }

    public List<StartupEventCategoryCode> getStartupEventCategoryCodeList(){
        return sqlSession.selectList("startup_event.getStartupEventCategoryCodeList");
    }
}
