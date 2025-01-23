package com.hanyang.startup.hanyangstartup.main.service;

import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.board.domain.BoardContent;
import com.hanyang.startup.hanyangstartup.board.service.BoardService;
import com.hanyang.startup.hanyangstartup.keyword.domain.Keyword;
import com.hanyang.startup.hanyangstartup.keyword.service.KeywordService;
import com.hanyang.startup.hanyangstartup.startup_calendar.domain.StartupCalendar;
import com.hanyang.startup.hanyangstartup.startup_calendar.service.StartupCalendarService;
import com.hanyang.startup.hanyangstartup.popup.domain.Popup;
import com.hanyang.startup.hanyangstartup.popup.service.PopupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {

    @Autowired
    private BoardService boardService;

    @Autowired
    private StartupCalendarService startupCalendarService;

    @Autowired
    private PopupService popupService;

    @Autowired
    private KeywordService keywordService;

    public Map<String,Object> getMainDate(){
        Map<String, Object> map = new HashMap<>();

        //공지사항
        Map<String, Object> noticeList = boardService.getBoardContentMain();

        //공지사항 영문
        Map<String, Object> noticeEnList = boardService.getBoardContentEnMain();

        //핫이슈
        BoardConfig issue = new BoardConfig();
        issue.setBoardEnName("issue");
        issue.setPageNo(1);
        issue.setPageSize(10);
        Map<String, Object> issueList = boardService.getBoardContentList(issue);

        //창업 켈린더
        StartupCalendar calendar = new StartupCalendar();
        calendar.setPageNo(1);
        calendar.setPageSize(5);
        Map<String, Object> calendarList = startupCalendarService.getStartupCalendarList(calendar);

        //창업지원정보
        BoardConfig startupInfo = new BoardConfig();
        startupInfo.setBoardEnName("startup_info");
        startupInfo.setPageNo(1);
        startupInfo.setPageSize(10);
        Map<String, Object> startupInfoList = boardService.getBoardContentList(startupInfo);

        //팝업 리스트
        Popup popup = new Popup();
        popup.setToday(LocalDateTime.now());
        popup.setPageSize(100);
        Map<String, Object>  result = popupService.getPopupList(popup);

        //온라인 콘텐츠
        BoardConfig onlineContentInfo = new BoardConfig();
        onlineContentInfo.setBoardEnName("online_content");
        onlineContentInfo.setPageNo(1);
        onlineContentInfo.setPageSize(3);
        Map<String, Object> onlineContentList = boardService.getBoardContentList(onlineContentInfo);

        //허브
//        BoardConfig hub = new BoardConfig();
//        hub.setBoardEnName("hub");
//        hub.setPageNo(1);
//        hub.setPageSize(10);
//        Map<String, Object> hubList = boardService.getBoardContentList(hub);

        //키워드
        Keyword keyword = new Keyword();
        keyword.setPageSize(5);
        Map<String, Object>  keywordList = keywordService.getKeywordList(keyword);


        map.put("notice",noticeList.get("list"));
        map.put("notice_en",noticeEnList.get("list"));
        map.put("issue",issueList.get("list"));
        map.put("calendar",calendarList.get("list"));
//        map.put("hot",hotList.get("list"));

        map.put("startup_info",startupInfoList.get("list"));
        map.put("online_content",onlineContentList.get("list"));
        map.put("hub",null);
        map.put("popup",result.get("list"));
        map.put("keyword",keywordList.get("list"));
        return map;

    }

    public List<BoardContent> getMediaList(){
        Map<String, Object> map = new HashMap<>();

        //공지사항
        BoardConfig media = new BoardConfig();
        media.setBoardEnName("media_report");
        media.setPageNo(1);
        media.setPageSize(10);

        return (List<BoardContent>)boardService.getBoardContentList(media).get("list");

    }

}
