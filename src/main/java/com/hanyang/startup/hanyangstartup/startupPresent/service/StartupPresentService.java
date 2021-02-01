package com.hanyang.startup.hanyangstartup.startupPresent.service;

import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import com.hanyang.startup.hanyangstartup.startupPresent.dao.StartupPresentDao;
import com.hanyang.startup.hanyangstartup.startupPresent.domain.StartupPresent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StartupPresentService {

    @Autowired
    private StartupPresentDao startupPresentDao;

    @Autowired
    private FileSaveService fileSaveService;

    @Transactional(rollbackFor = {Exception.class})
    public void addStartupPresent(StartupPresent startupPresent){

        startupPresentDao.addStartupPresent(startupPresent);

    }
    @Transactional(rollbackFor = {Exception.class})
    public void updateStartupPresent(StartupPresent startupPresent){
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
        startupPresentDao.deleteStartupPresent(startupPresent);
    }
}
