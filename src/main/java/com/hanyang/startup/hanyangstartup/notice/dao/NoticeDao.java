package com.hanyang.startup.hanyangstartup.notice.dao;

import com.hanyang.startup.hanyangstartup.board.domain.BoardContent;
import com.hanyang.startup.hanyangstartup.notice.domain.Notice;
import com.hanyang.startup.hanyangstartup.notice.domain.NoticeCategoryCode;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoticeDao {
    @Autowired
    protected SqlSessionTemplate sqlSession;

    public Notice getNotice(Notice notice){
        return sqlSession.selectOne("notice.getNotice",notice);
    }

    public void addNotice(Notice notice){
        sqlSession.insert("notice.addNotice",notice);
    }

    public void updateNotice(Notice notice){
        sqlSession.update("notice.updateNotice",notice);
    }

    public void deleteNotice(Notice notice){
        sqlSession.delete("notice.deleteNotice",notice);
    }

    public void updateNoticeCnt(Notice notice){
        sqlSession.update("notice.updateNoticeCnt",notice);
    }

    public Notice getNoticePrev(Notice notice){
        return sqlSession.selectOne("notice.getNoticePrev",notice);
    }

    public Notice getNoticeNext(Notice notice){
        return sqlSession.selectOne("notice.getNoticeNext",notice);
    }

    public List<Notice> getNoticeList(Notice notice){
        return sqlSession.selectList("notice.getNoticeList", notice);
    }

    public int getNoticeListCnt(Notice notice){
        return sqlSession.selectOne("notice.getNoticeListCnt", notice);
    }

    public List<NoticeCategoryCode> getNoticeCategoryCodeList(){
        return sqlSession.selectList("notice.getNoticeCategoryCodeList");
    }
}
