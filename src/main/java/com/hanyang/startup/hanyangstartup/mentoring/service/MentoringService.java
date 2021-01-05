package com.hanyang.startup.hanyangstartup.mentoring.service;

import com.hanyang.startup.hanyangstartup.mentoring.dao.MentoringDao;
import com.hanyang.startup.hanyangstartup.mentoring.domain.*;
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
import java.util.jar.JarOutputStream;
import java.util.stream.Collectors;

@Service
public class MentoringService {

    @Autowired
    private MentoringDao mentoringDao;

    @Autowired
    private FileSaveService fileSaveService;

    public List<CounselFiledCode> getCounselFieldCode(){
        return mentoringDao.getCounselFieldCode();
    }
    public List<ProgressItem> getProgressItem(){
        return mentoringDao.getProgressItem();
    }
    public List<SortationItem> getSortationItem(){
        return mentoringDao.getSortationItem();
    }
    public List<WayItem> getWayItem(){
        return mentoringDao.getWayItem();
    }

    public List<Mentor> getMentor(Mentor mentor){

        List<Mentor> mentorList = mentoringDao.getMentor(mentor);

        mentorList.stream().map(item-> {
            List<Integer> counselFieldList  = mentoringDao.getCounselFieldMentor(item);
            item.setMentorFieldList(counselFieldList);
            return item;
        }).collect(Collectors.toList());

//        for (Mentor searchMentor :mentorList) {
//            List<Integer> counselFieldList  = mentoringDao.getCounselFieldMentor(searchMentor);
//            System.out.println("=====리스트");
//            System.out.println(counselFieldList);
//            mentor.setMentorFieldList(counselFieldList);
//            System.out.println(mentor);
//        }
        System.out.println(mentorList);
        return mentorList;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void applyMentor(Mentor mentor){

        String fieldStr = "";
        for (Integer field: mentor.getMentorFieldList()) {
            fieldStr += field.toString() + ";";
        }
        mentor.setMentorFieldStr(fieldStr);

        String keywordStr = "";
        for (String keyword: mentor.getMentorKeyword()) {
            keywordStr += keyword + ";";
        }
        mentor.setMentorKeywordStr(keywordStr);

        String careerStr = "";
        for (String career: mentor.getMentorCareer()) {
            careerStr += career + ";";
        }
        mentor.setMentorCareerStr(careerStr);

        mentoringDao.applyMentor(mentor);
        mentoringDao.addCounselFieldMentor(mentor);

        fileSaveService.fileSave(mentor.getProfileImg(),mentor.getMentorId(), FILE_DIVISION.MENTOR_PROFILE_IMG);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void applyCounsel(CounselApplyForm counselApplyForm){
        mentoringDao.applyCounsel(counselApplyForm);
        mentoringDao.addCounselFieldMentee(counselApplyForm);

        List<AttachFile> attachFileList = new ArrayList<>();

        for (Integer file: counselApplyForm.getUploadResultList()) {
            AttachFile attachFile = new AttachFile();
            attachFile.setContentId(counselApplyForm.getFormId());
            attachFile.setDivision(FILE_DIVISION.MENTORING_ATTACH);
            attachFile.setFileId(file);
            attachFileList.add(attachFile);
            System.out.println(attachFile);
        }
        if(attachFileList.size() > 0){
            System.out.println("업데이트 시");
            fileSaveService.updateAttachFile(attachFileList);
        }
    }

    public Map<String,Object> getCounselApplyList(CounselApplyForm counselApplyForm){
        counselApplyForm.setPageSize(10);
        counselApplyForm.setTotalCount(mentoringDao.getCounselApplyListCnt(counselApplyForm));

        Map<String,Object> map = new HashMap<>();

        map.put("page",counselApplyForm);
        map.put("list",mentoringDao.getCounselApplyList(counselApplyForm));

        return map;
    }

}
