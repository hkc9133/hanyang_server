package com.hanyang.startup.hanyangstartup.mentoring.service;

import com.hanyang.startup.hanyangstartup.auth.service.AuthService;
import com.hanyang.startup.hanyangstartup.board.domain.BoardContent;
import com.hanyang.startup.hanyangstartup.mentoring.dao.MentoringDao;
import com.hanyang.startup.hanyangstartup.mentoring.domain.*;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_STATUS;
import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.jar.JarOutputStream;
import java.util.stream.Collectors;

@Service
public class MentoringService {

    @Autowired
    private MentoringDao mentoringDao;

    @Autowired
    private FileSaveService fileSaveService;
    @Autowired
    private AuthService authService;

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

    public Map<String, Object> getMentor(Mentor mentor){

        Mentor resultMentor = mentoringDao.getMentor(mentor);

        resultMentor.setMentorFieldList(mentoringDao.getCounselFieldMentor(mentor));


        if(resultMentor.getMentorCareerStr() != null){
            resultMentor.setMentorCareer(Arrays.asList(resultMentor.getMentorCareerStr().split(";").clone()));
        }else{
            resultMentor.setMentorCareer(new ArrayList<>());
        }

        if(resultMentor.getMentorKeywordStr() != null){
            resultMentor.setMentorKeyword(Arrays.asList(resultMentor.getMentorKeywordStr().split(";").clone()));
        }else{
            resultMentor.setMentorKeyword(new ArrayList<>());
        }

        Map<String, Object> map = new HashMap<>();

        map.put("mentor", resultMentor);
        return map;
    }

    public void updateMentor(Mentor mentor){

//        String keywordStr = "";
//        for (String keyword: mentor.getMentorKeyword()) {
//            keywordStr += keyword + ";";
//        }
//        mentor.setMentorKeywordStr(keywordStr);
//
//        String careerStr = "";
//        for (String career: mentor.getMentorCareer()) {
//            careerStr += career + ";";
//        }
//        mentor.setMentorCareerStr(careerStr);

        mentoringDao.updateMentor(mentor);
    }

    public Map<String, Object> getMentorList(Mentor mentor){

        if(mentor.getPageNo() != null){
            mentor.setTotalCount(mentoringDao.getMentorListCnt(mentor));
        }
        List<Mentor> mentorList = mentoringDao.getMentorList(mentor);

        mentorList.stream().map(item-> {
            if(item.getMentorCareerStr() != null){
                item.setMentorCareer(Arrays.asList(item.getMentorCareerStr().split(";").clone()));
            }else{
                item.setMentorCareer(new ArrayList<>());
            }

            if(item.getMentorKeywordStr() != null){
                item.setMentorKeyword(Arrays.asList(item.getMentorKeywordStr().split(";").clone()));
            }else{
                item.setMentorKeyword(new ArrayList<>());
            }
            item.setMentorFieldList(mentoringDao.getCounselFieldMentor(item));
            return item;
        }).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();

        map.put("page", mentor);
        map.put("list", mentorList);
        map.put("cate", mentoringDao.getCounselFieldCode());
        return map;
    }

    public Map<String, Object> getCounselApply(CounselApplyForm counselApplyForm){

        CounselApplyForm resultCounselApplyForm = mentoringDao.getCounselApply(counselApplyForm);

        //신청 분야 리스트
        resultCounselApplyForm.setCounselFieldList(mentoringDao.getCounselFieldMentee(counselApplyForm));

        AttachFile attachFile = new AttachFile();
        attachFile.setContentId(counselApplyForm.getFormId());
        attachFile.setDivision(FILE_DIVISION.MENTORING_ATTACH);
        attachFile.setStatus(FILE_STATUS.A);

        Map<String, Object> map = new HashMap<>();

        map.put("counselApply", resultCounselApplyForm);
        map.put("files", fileSaveService.getAttachFileList(attachFile));

        if(resultCounselApplyForm.getApplyStatus() == APPLY_STATUS.COMPLETED){
            MentoringDiary searchDiary = new MentoringDiary();
            searchDiary.setFormId(resultCounselApplyForm.getFormId());
            MentoringDiary mentoringDiary = mentoringDao.getDiary(searchDiary);

            AttachFile answerAttachFile = new AttachFile();
            answerAttachFile.setContentId(mentoringDiary.getDiaryId());
            answerAttachFile.setDivision(FILE_DIVISION.MENTORING_ANSWER_ATTACH);
            answerAttachFile.setStatus(FILE_STATUS.A);
            map.put("answerFiles", fileSaveService.getAttachFileList(attachFile));
        }
        return map;
    }

    public void updateCounselApply(CounselApplyForm counselApplyForm){

//        String keywordStr = "";
//        for (String keyword: mentor.getMentorKeyword()) {
//            keywordStr += keyword + ";";
//        }
//        mentor.setMentorKeywordStr(keywordStr);
//
//        String careerStr = "";
//        for (String career: mentor.getMentorCareer()) {
//            careerStr += career + ";";
//        }
//        mentor.setMentorCareerStr(careerStr);

        mentoringDao.updateCounselApply(counselApplyForm);
    }

    public void updateCounselApplyStatus(CounselApplyForm counselApplyForm){

        mentoringDao.updateCounselApplyStatus(counselApplyForm);
    }


    public Map<String, Object> getCounselApplyList(CounselApplyForm counselApplyForm){

        if(counselApplyForm.getPageNo() != null){
            counselApplyForm.setTotalCount(mentoringDao.getCounselApplyListCnt(counselApplyForm));
        }
        List<CounselApplyForm> counselApplyList = mentoringDao.getCounselApplyList(counselApplyForm);

//        counselApplyList.stream().map(item-> {
//            List<Integer> counselFieldList  = mentoringDao.getCounselFieldMentee(item);
//            item.setCounselFieldIdList(counselFieldList);
//            return item;
//        }).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();

        map.put("page", counselApplyForm);
        map.put("list", counselApplyList);
//        map.put("cate", mentoringDao.getCounselFieldCode());
        return map;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void applyMentor(Mentor mentor){

//        String fieldStr = "";
//        for (Integer field: mentor.getMentorFieldList()) {
//            fieldStr += field.toString() + ";";
//        }
//        mentor.setMentorFieldStr(fieldStr);

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
    public void updateMentorProfile(Mentor mentor){

//        String fieldStr = "";
//        for (Integer field: mentor.getMentorFieldList()) {
//            fieldStr += field.toString() + ";";
//        }
//        mentor.setMentorFieldStr(fieldStr);

        String keywordStr = "";
        for (String keyword: mentor.getMentorKeyword()) {
            keywordStr += keyword + ";";
        }

        //빈문자열이 들어가서 초기화
        if(keywordStr == ""){
            keywordStr = null;
        }
        mentor.setMentorKeywordStr(keywordStr);

        String careerStr = "";
        for (String career: mentor.getMentorCareer()) {
            careerStr += career + ";";
        }

        //빈문자열이 들어가서 초기화
        if(careerStr == ""){
            careerStr = null;
        }
        mentor.setMentorCareerStr(careerStr);

        mentoringDao.updateMentorProfile(mentor);

        mentoringDao.removeCounselFieldMentor(mentor);

        if(mentor.getMentorFieldList().size() > 0){
            mentoringDao.addCounselFieldMentor(mentor);
        }

        if(mentor.getProfileImg() != null){
            List<AttachFile> attachFileList = new ArrayList<>();

            //이미지가 없었다면
            if(mentor.getFileId() != null){
                AttachFile attachFile = new AttachFile();
                attachFile.setFileId(mentor.getFileId());
                attachFileList.add(attachFile);

                fileSaveService.deleteAttachFile(attachFileList);
            }

            fileSaveService.fileSave(mentor.getProfileImg(),mentor.getMentorId(), FILE_DIVISION.MENTOR_PROFILE_IMG);
        }
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
            fileSaveService.updateAttachFile(attachFileList);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void addDiary(MentoringDiary mentoringDiary){
        Mentor mentor = new Mentor();
        mentor.setUserId(mentoringDiary.getMentorUserId());
        Mentor searchMentor = mentoringDao.getMentor(mentor);

        mentoringDiary.setMentorId(searchMentor.getMentorId());
        mentoringDao.addDiary(mentoringDiary);

        CounselApplyForm counselApplyForm = new CounselApplyForm();
        counselApplyForm.setFormId(mentoringDiary.getFormId());
        counselApplyForm.setApplyStatus(APPLY_STATUS.COMPLETED);
        mentoringDao.updateCounselApplyStatus(counselApplyForm);

        if(mentoringDiary.getFiles() != null){
            for (MultipartFile file : mentoringDiary.getFiles()) {
                fileSaveService.fileSave(file, mentoringDiary.getDiaryId(), FILE_DIVISION.MENTORING_ANSWER_ATTACH);
            }
        }
    }

    public void updateDiary(MentoringDiary mentoringDiary){

        CounselApplyForm counselApplyForm = new CounselApplyForm();
        counselApplyForm.setFormId(mentoringDiary.getFormId());
        counselApplyForm.setApplyStatus(APPLY_STATUS.COMPLETED);
        counselApplyForm.setUserId(mentoringDiary.getMenteeUserId());

        if(mentoringDao.getCounselApply(counselApplyForm) != null){
            mentoringDao.updateDiary(mentoringDiary);

        }
//        mentoringDao.updateCounselApplyStatus(counselApplyForm);

    }

//    public Map<String,Object> getCounselApplyList(CounselApplyForm counselApplyForm){
//        counselApplyForm.setPageSize(10);
//        counselApplyForm.setTotalCount(mentoringDao.getCounselApplyListCnt(counselApplyForm));
//
//        Map<String,Object> map = new HashMap<>();
//
//        map.put("page",counselApplyForm);
//        map.put("list",mentoringDao.getCounselApplyList(counselApplyForm));
//
//        return map;
//    }

}
