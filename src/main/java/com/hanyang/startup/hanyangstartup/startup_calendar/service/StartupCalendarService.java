package com.hanyang.startup.hanyangstartup.startup_calendar.service;

import com.hanyang.startup.hanyangstartup.startup_calendar.dao.StartupCalendarDao;
import com.hanyang.startup.hanyangstartup.startup_calendar.dao.StartupCalendarDao;
import com.hanyang.startup.hanyangstartup.startup_calendar.domain.StartupCalendar;
import com.hanyang.startup.hanyangstartup.startup_calendar.domain.StartupCalendarCategoryCode;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_STATUS;
import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StartupCalendarService {
    @Autowired
    private StartupCalendarDao startupCalendarDao;
    @Autowired
    private FileSaveService fileSaveService;

    //컨텐츠 조회
    public Map<String, Object> getStartupCalendar(StartupCalendar startupCalendar) {
        //조회수 업데이트
        startupCalendarDao.updateStartupCalendarCnt(startupCalendar);

        AttachFile attachFile = new AttachFile();
        attachFile.setContentId(startupCalendar.getStartupCalendarId());
        attachFile.setDivision(FILE_DIVISION.NOTICE_ATTACH);
        attachFile.setStatus(FILE_STATUS.A);
        Map<String, Object> map = new HashMap<>();
        StartupCalendar resultStartupCalendar = startupCalendarDao.getStartupCalendar(startupCalendar);

        map.put("startupCalendar", resultStartupCalendar);
        map.put("prev", startupCalendarDao.getStartupCalendarPrev(startupCalendar));
        map.put("next", startupCalendarDao.getStartupCalendarNext(startupCalendar));
        map.put("files", fileSaveService.getAttachFileList(attachFile));
        return map;
    }

    //컨텐츠 생성
    @Transactional(rollbackFor = {Exception.class})
    public void addStartupCalendar(StartupCalendar startupCalendar) {
        startupCalendarDao.addStartupCalendar(startupCalendar);

        if(startupCalendar.getFiles() != null){
            for (MultipartFile file : startupCalendar.getFiles()) {
                fileSaveService.fileSave(file, startupCalendar.getStartupCalendarId(), FILE_DIVISION.NOTICE_ATTACH);
            }
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void updateStartupCalendar(StartupCalendar startupCalendar) {
        List<Integer> removeFiles = startupCalendar.getRemoveFiles();
        if(removeFiles != null){
            List<AttachFile> attachFileList = new ArrayList<>();
            for (Integer file : removeFiles) {
                AttachFile attachFile = new AttachFile();
                attachFile.setFileId(file.intValue());
                attachFileList.add(attachFile);
            }
            fileSaveService.deleteAttachFile(attachFileList);
        }
        if(startupCalendar.getFiles() != null){
            for (MultipartFile file : startupCalendar.getFiles()) {
                fileSaveService.fileSave(file, startupCalendar.getStartupCalendarId(), FILE_DIVISION.BOARD_ATTACH);
            }
        }
        startupCalendarDao.updateStartupCalendar(startupCalendar);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteStartupCalendar(StartupCalendar startupCalendar) {
        startupCalendarDao.deleteStartupCalendar(startupCalendar);
    }


    //이벤트
    public Map<String,Object> getStartupCalendarList(StartupCalendar startupCalendar) {
        startupCalendar.setTotalCount(startupCalendarDao.getStartupCalendarListCnt(startupCalendar));

        System.out.println(startupCalendar);
        Map<String,Object> map = new HashMap<>();
        List<StartupCalendar> startupCalendarList = startupCalendarDao.getStartupCalendarList(startupCalendar);

        map.put("page", startupCalendar);
        map.put("list", startupCalendarList);
        map.put("cate",startupCalendarDao.getStartupCalendarCategoryCodeList());

        return map;
    }

    public List<StartupCalendarCategoryCode> getStartupCalendarCategoryCodeList() {
        return startupCalendarDao.getStartupCalendarCategoryCodeList();
    }

}
