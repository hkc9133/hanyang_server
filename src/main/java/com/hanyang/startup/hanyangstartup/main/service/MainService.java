package com.hanyang.startup.hanyangstartup.main.service;

import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.board.domain.BoardContent;
import com.hanyang.startup.hanyangstartup.board.service.BoardService;
import com.hanyang.startup.hanyangstartup.notice.domain.Notice;
import com.hanyang.startup.hanyangstartup.notice.service.NoticeService;
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
    private NoticeService noticeService;

    @Autowired
    private PopupService popupService;

    public Map<String,Object> getMainDate(){
        Map<String, Object> map = new HashMap<>();

        //공지사항
        Notice notice = new Notice();
        notice.setPageNo(1);
        notice.setPageSize(10);
        Map<String, Object> noticeList = noticeService.getNoticeList(notice);

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





        map.put("notice",noticeList.get("list"));
        map.put("startup_info",startupInfoList.get("list"));
        map.put("popup",result.get("list"));
        return map;

    }

}
