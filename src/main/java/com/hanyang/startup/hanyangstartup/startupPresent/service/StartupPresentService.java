package com.hanyang.startup.hanyangstartup.startupPresent.service;

import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import com.hanyang.startup.hanyangstartup.startupPresent.dao.StartupPresentDao;
import com.hanyang.startup.hanyangstartup.startupPresent.domain.GubunCount;
import com.hanyang.startup.hanyangstartup.startupPresent.domain.StartupPresent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StartupPresentService {

    @Autowired
    private StartupPresentDao startupPresentDao;

    @Autowired
    private FileSaveService fileSaveService;


    public Map<String, Object> getFieldList(){


        Map<String, Object> map = new HashMap<>();

        map.put("business", startupPresentDao.getBusinessFieldList());
        map.put("tech", startupPresentDao.getTechFieldList());

        return map;

    }

    @Transactional(rollbackFor = {Exception.class})
    public void addStartupPresent(StartupPresent startupPresent){

        startupPresentDao.addStartupPresent(startupPresent);

        startupPresentDao.addBusinessFieldList(startupPresent);
        startupPresentDao.addTechFieldList(startupPresent);

        fileSaveService.fileSave(startupPresent.getCompanyLogo(),startupPresent.getStartupId(), FILE_DIVISION.STARTUP_LOGO);

    }
    @Transactional(rollbackFor = {Exception.class})
    public void updateStartupPresent(StartupPresent startupPresent){

        startupPresentDao.deleteBusinessFieldList(startupPresent);
        startupPresentDao.deleteTechFieldList(startupPresent);

        startupPresentDao.addBusinessFieldList(startupPresent);
        startupPresentDao.addTechFieldList(startupPresent);

        //새로 추가한 이미지가 있으면
        if(startupPresent.getCompanyLogo() != null){
            List<AttachFile> attachFileList = new ArrayList<>();
            AttachFile attachFile = new AttachFile();
            attachFile.setFileId(startupPresent.getRemoveFileId());

            attachFileList.add(attachFile);
            fileSaveService.deleteAttachFile(attachFileList);

            fileSaveService.fileSave(startupPresent.getCompanyLogo(),startupPresent.getStartupId(), FILE_DIVISION.STARTUP_LOGO);
        }

        startupPresentDao.updateStartupPresent(startupPresent);
    }

    public StartupPresent getStartupPresent(StartupPresent startupPresent){
        return startupPresentDao.getStartupPresent(startupPresent);
    }
    public Map<String, Object> getStartupPresentList(StartupPresent startupPresent){

        startupPresent.setTotalCount(startupPresentDao.getStartupPresentListCnt(startupPresent));
        List<StartupPresent> startupPresentList = startupPresentDao.getStartupPresentList(startupPresent);


        Map<String, Object> map = new HashMap<>();

        map.put("page", startupPresent);
        map.put("list", startupPresentList);

        return map;

    }

    public void deleteStartupPresent(StartupPresent startupPresent){
        startupPresentDao.deleteBusinessFieldList(startupPresent);
        startupPresentDao.deleteTechFieldList(startupPresent);


        if(startupPresent.getAttachFile() != null){
            List<AttachFile> attachFileList = new ArrayList<>();
            AttachFile attachFile = new AttachFile();
            attachFile.setFileId(startupPresent.getAttachFile().getFileId());

            attachFileList.add(attachFile);
            fileSaveService.deleteAttachFile(attachFileList);
        }

        startupPresentDao.deleteStartupPresent(startupPresent);
    }

    public Map<String, Object> getBestStartupList(){

        List<StartupPresent> startupPresentList = startupPresentDao.getBestStartupList();
        List<GubunCount> gubunCountList = startupPresentDao.getStartupGubunCnt();


        Map<String, Object> map = new HashMap<>();

        map.put("list", startupPresentList);
        map.put("count", gubunCountList);

        return map;

    }
}
