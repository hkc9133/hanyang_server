package com.hanyang.startup.hanyangstartup.startup_calendar.dao;

import com.hanyang.startup.hanyangstartup.startup_calendar.domain.StartupCalendar;
import com.hanyang.startup.hanyangstartup.startup_calendar.domain.StartupCalendarCategoryCode;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StartupCalendarDao {
    @Autowired
    protected SqlSessionTemplate sqlSession;

    public StartupCalendar getStartupCalendar(StartupCalendar startupCalendar){
        return sqlSession.selectOne("startup_calendar.getStartupCalendar", startupCalendar);
    }

    public void addStartupCalendar(StartupCalendar startupCalendar){
        sqlSession.insert("startup_calendar.addStartupCalendar", startupCalendar);
    }

    public void updateStartupCalendar(StartupCalendar startupCalendar){
        sqlSession.update("startup_calendar.updateStartupCalendar", startupCalendar);
    }

    public void deleteStartupCalendar(StartupCalendar startupCalendar){
        sqlSession.delete("startup_calendar.deleteStartupCalendar", startupCalendar);
    }

    public void updateStartupCalendarCnt(StartupCalendar startupCalendar){
        sqlSession.update("startup_calendar.updateStartupCalendarCnt", startupCalendar);
    }

    public StartupCalendar getStartupCalendarPrev(StartupCalendar startupCalendar){
        return sqlSession.selectOne("startup_calendar.getStartupCalendarPrev", startupCalendar);
    }

    public StartupCalendar getStartupCalendarNext(StartupCalendar startupCalendar){
        return sqlSession.selectOne("startup_calendar.getStartupCalendarNext", startupCalendar);
    }

    public List<StartupCalendar> getStartupCalendarList(StartupCalendar startupCalendar){
        return sqlSession.selectList("startup_calendar.getStartupCalendarList", startupCalendar);
    }

    public int getStartupCalendarListCnt(StartupCalendar startupCalendar){
        return sqlSession.selectOne("startup_calendar.getStartupCalendarListCnt", startupCalendar);
    }

    public List<StartupCalendarCategoryCode> getStartupCalendarCategoryCodeList(){
        return sqlSession.selectList("startup_calendar.getStartupCalendarCategoryCodeList");
    }
}
