package com.hanyang.startup.hanyangstartup.partner.service;

import com.hanyang.startup.hanyangstartup.partner.dao.PartnerDao;
import com.hanyang.startup.hanyangstartup.partner.domain.ContinentCode;
import com.hanyang.startup.hanyangstartup.partner.domain.Partner;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PartnerService {

    @Autowired
    private PartnerDao partnerDao;

    @Autowired
    private FileSaveService fileSaveService;


    public List<ContinentCode> getContinentList(){


        return partnerDao.getContinentList();

    }

    @Transactional(rollbackFor = {Exception.class})
    public void addPartner(Partner partner){

        partnerDao.addPartner(partner);


        fileSaveService.fileSave(partner.getCompanyLogo(),partner.getPartnerId(), FILE_DIVISION.PARTNER_LOGO);

    }
    @Transactional(rollbackFor = {Exception.class})
    public void updatePartner(Partner partner){


        //새로 추가한 이미지가 있으면
        if(partner.getCompanyLogo() != null){
            List<AttachFile> attachFileList = new ArrayList<>();
            AttachFile attachFile = new AttachFile();
            attachFile.setFileId(partner.getRemoveFileId());

            attachFileList.add(attachFile);
            fileSaveService.deleteAttachFile(attachFileList);

            fileSaveService.fileSave(partner.getCompanyLogo(),partner.getPartnerId(), FILE_DIVISION.PARTNER_LOGO);
        }

        partnerDao.updatePartner(partner);
    }

    public Partner getPartner(Partner partner){
        return partnerDao.getPartner(partner);
    }
    public Map<String, Object> getPartnerList(Partner partner){

        partner.setTotalCount(partnerDao.getPartnerListCnt(partner));
        List<Partner> partnerList = partnerDao.getPartnerList(partner);


        Map<String, Object> map = new HashMap<>();

        map.put("page", partner);
        map.put("list", partnerList);

        return map;

    }

    public void deletePartner(Partner partner){

        if(partner.getAttachFile() != null){
            List<AttachFile> attachFileList = new ArrayList<>();
            AttachFile attachFile = new AttachFile();
            attachFile.setFileId(partner.getAttachFile().getFileId());

            attachFileList.add(attachFile);
            fileSaveService.deleteAttachFile(attachFileList);
        }

        partnerDao.deletePartner(partner);
    }

}
