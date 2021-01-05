package com.hanyang.startup.hanyangstartup.mentoring.dao;

import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.board.domain.BoardContent;
import com.hanyang.startup.hanyangstartup.mentoring.domain.*;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class MentoringDao {
    @Autowired
    protected SqlSessionTemplate sqlSession;

    public List<CounselFiledCode> getCounselFieldCode(){
        return sqlSession.selectList("mentoring.getCounselFieldCode");
    }

    public List<ProgressItem> getProgressItem(){
        return sqlSession.selectList("mentoring.getProgressItem");
    }
    public List<SortationItem> getSortationItem(){
        return sqlSession.selectList("mentoring.getSortationItem");
    }
    public List<WayItem> getWayItem(){
        return sqlSession.selectList("mentoring.getWayItem");
    }
    public List<Mentor> getMentor(Mentor mentor){
        return sqlSession.selectList("mentoring.getMentor",mentor);
    }


    public void applyMentor(Mentor mentor){
        sqlSession.insert("mentoring.applyMentor",mentor);
    }

    public List<Integer> getCounselFieldMentor(Mentor mentor){
        return sqlSession.selectList("mentoring.getCounselFieldMentor",mentor);
    }
    public void addCounselFieldMentor(Mentor mentor){
        sqlSession.insert("mentoring.addCounselFieldMentor",mentor);
    }

    public void applyCounsel(CounselApplyForm counselApplyForm){
        sqlSession.insert("mentoring.applyCounsel",counselApplyForm);
    }
    public void addCounselFieldMentee(CounselApplyForm counselApplyForm){
        sqlSession.insert("mentoring.addCounselFieldMentee",counselApplyForm);
    }

    public List<CounselApplyForm> getCounselApplyList(CounselApplyForm counselApplyForm){
        return sqlSession.selectList("mentoring.getCounselApplyList",counselApplyForm);
    }

    public int getCounselApplyListCnt(CounselApplyForm counselApplyForm){
        return sqlSession.selectOne("mentoring.getCounselApplyListCnt",counselApplyForm);
    }
}
