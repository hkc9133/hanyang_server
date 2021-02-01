package com.hanyang.startup.hanyangstartup.popup.service;

import com.hanyang.startup.hanyangstartup.popup.dao.PopupDao;
import com.hanyang.startup.hanyangstartup.popup.domain.Popup;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalPlace;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PopupService {

    @Autowired
    private PopupDao popupDao;

    @Autowired
    private FileSaveService fileSaveService;

    @Transactional(rollbackFor = {Exception.class})
    public void addPopup(Popup popup){

        popupDao.addPopup(popup);

    }
    @Transactional(rollbackFor = {Exception.class})
    public void updatePopup(Popup popup){
        popupDao.updatePopup(popup);
    }

    public Popup getPopup(Popup popup){
        return popupDao.getPopup(popup);
    }
    public Map<String, Object> getPopupList(Popup popup){

        popup.setTotalCount(popupDao.getPopupListCnt(popup));
        List<Popup> popupList = popupDao.getPopupList(popup);


        Map<String, Object> map = new HashMap<>();

        map.put("page", popup);
        map.put("list", popupList);

        return map;

    }

    public void deletePopup(Popup popup){
        popupDao.deletePopup(popup);
    }
}
