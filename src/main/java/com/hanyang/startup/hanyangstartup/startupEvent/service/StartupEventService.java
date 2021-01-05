package com.hanyang.startup.hanyangstartup.startupEvent.service;

import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.board.domain.BoardContent;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import com.hanyang.startup.hanyangstartup.startupEvent.dao.StartupEventDao;
import com.hanyang.startup.hanyangstartup.startupEvent.domain.StartupEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StartupEventService {
    @Autowired
    private StartupEventDao startupEventDao;

    //이벤트
    public Map<String,Object> getStartupEventList(StartupEvent startupEvent) {
        startupEvent.setTotalCount(startupEventDao.getStartupEventListCnt(startupEvent));

        Map<String,Object> map = new HashMap<>();
        List<StartupEvent> startupEventList = startupEventDao.getStartupEventList(startupEvent);

        map.put("page",startupEvent);
        map.put("list",startupEventList);
        map.put("cate",startupEventDao.getStartupEventCategoryCodeList());

        return map;
    }
}
