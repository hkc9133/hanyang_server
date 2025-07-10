package com.hanyang.startup.hanyangstartup.mentoring.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanyang.startup.hanyangstartup.auth.service.AuthService;
import com.hanyang.startup.hanyangstartup.board.domain.BoardContent;
import com.hanyang.startup.hanyangstartup.common.domain.Email;
import com.hanyang.startup.hanyangstartup.common.service.EmailService;
import com.hanyang.startup.hanyangstartup.mentoring.dao.MentoringDao;
import com.hanyang.startup.hanyangstartup.mentoring.domain.*;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_STATUS;
import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import com.hanyang.startup.hanyangstartup.startupPresent.domain.StartupPresent;
import io.jsonwebtoken.io.EncodingException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
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
    private EmailService emailService;
    @Autowired
    private TemplateEngine templateEngine;


    @Value(value = "${config.adminEmail}")
    private String ADMIN_EMAIL;

    public List<CounselFiledCode> getCounselFieldCode() {
        return mentoringDao.getCounselFieldCode();
    }

    public List<ProgressItem> getProgressItem() {
        return mentoringDao.getProgressItem();
    }

    public List<SortationItem> getSortationItem() {
        return mentoringDao.getSortationItem();
    }

    public List<WayItem> getWayItem() {
        return mentoringDao.getWayItem();
    }

    public Map<String, Object> getMentor(Mentor mentor) {

        Mentor resultMentor = mentoringDao.getMentor(mentor);

        resultMentor.setMentorFieldList(mentoringDao.getCounselFieldMentor(resultMentor));


        if (resultMentor.getMentorCareerStr() != null) {
            resultMentor.setMentorCareer(Arrays.asList(resultMentor.getMentorCareerStr().split(";").clone()));
        } else {
            resultMentor.setMentorCareer(new ArrayList<>());
        }

        if (resultMentor.getMentorKeywordStr() != null) {
            resultMentor.setMentorKeyword(Arrays.asList(resultMentor.getMentorKeywordStr().split(";").clone()));
        } else {
            resultMentor.setMentorKeyword(new ArrayList<>());
        }

        Map<String, Object> map = new HashMap<>();

        map.put("mentor", resultMentor);
        return map;
    }

    public List<Mentor> getBestMentor() {

//        Mentor mentor = mentoringDao.getBestMentor();
////
////        mentor.setMentorFieldList(mentoringDao.getCounselFieldMentor(mentor));
////
////
////        if(mentor.getMentorCareerStr() != null){
////            mentor.setMentorCareer(Arrays.asList(mentor.getMentorCareerStr().split(";").clone()));
////        }else{
////            mentor.setMentorCareer(new ArrayList<>());
////        }
////
////        if(mentor.getMentorKeywordStr() != null){
////            mentor.setMentorKeyword(Arrays.asList(mentor.getMentorKeywordStr().split(";").clone()));
////        }else{
////            mentor.setMentorKeyword(new ArrayList<>());
////        }
////
////        return mentor;

//        Map<String, Object> map = new HashMap<>();
//
//        map.put("mentor", mentor);
//        return map;

//        if(mentor.getPageNo() != null){
//            mentor.setTotalCount(mentoringDao.getMentorListCnt(mentor));
//        }
        Mentor mentor = new Mentor();
        mentor.setIsBest(true);
        mentor.setMentorStatus(MENTOR_STATUS.ACCEPT);
        List<Mentor> mentorList = mentoringDao.getMentorList(mentor);

        mentorList.stream().map(item -> {
            if (item.getMentorCareerStr() != null) {
                item.setMentorCareer(Arrays.asList(item.getMentorCareerStr().split(";").clone()));
            } else {
                item.setMentorCareer(new ArrayList<>());
            }

            if (item.getMentorKeywordStr() != null) {
                item.setMentorKeyword(Arrays.asList(item.getMentorKeywordStr().split(";").clone()));
            } else {
                item.setMentorKeyword(new ArrayList<>());
            }
            item.setMentorFieldList(mentoringDao.getCounselFieldMentor(item));
            return item;
        }).collect(Collectors.toList());

//        Map<String, Object> map = new HashMap<>();
//
//        map.put("list", mentorList);
        return mentorList;
    }

    public void updateMentor(Mentor mentor) {

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

    public Map<String, Object> getMentorList(Mentor mentor) {

        if (mentor.getPageNo() != null) {
            mentor.setTotalCount(mentoringDao.getMentorListCnt(mentor));
        }
        List<Mentor> mentorList = mentoringDao.getMentorList(mentor);

        mentorList.stream().map(item -> {
            if (item.getMentorCareerStr() != null) {
                item.setMentorCareer(Arrays.asList(item.getMentorCareerStr().split(";").clone()));
            } else {
                item.setMentorCareer(new ArrayList<>());
            }

            if (item.getMentorKeywordStr() != null) {
                item.setMentorKeyword(Arrays.asList(item.getMentorKeywordStr().split(";").clone()));
            } else {
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

    public Map<String, Object> getCounselApply(CounselApplyForm counselApplyForm) {

        CounselApplyForm resultCounselApplyForm = mentoringDao.getCounselApply(counselApplyForm);

        //신청 분야 리스트
//        resultCounselApplyForm.setCounselFieldList(mentoringDao.getCounselFieldMentee(counselApplyForm));
        resultCounselApplyForm.setSortationItemList(mentoringDao.getCounselSortationList(counselApplyForm));
        resultCounselApplyForm.setWayItemList(mentoringDao.getCounselWayList(counselApplyForm));

        AttachFile attachFile = new AttachFile();
        attachFile.setContentId(counselApplyForm.getFormId());
        attachFile.setDivision(FILE_DIVISION.MENTORING_ATTACH);
        attachFile.setStatus(FILE_STATUS.A);

        Map<String, Object> map = new HashMap<>();

        map.put("counselApply", resultCounselApplyForm);
        map.put("files", fileSaveService.getAttachFileList(attachFile));

        if (resultCounselApplyForm.getApplyStatus() == APPLY_STATUS.COMPLETED) {
            MentoringDiary searchDiary = new MentoringDiary();
            searchDiary.setFormId(resultCounselApplyForm.getFormId());
            MentoringDiary mentoringDiary = mentoringDao.getDiary(searchDiary);

            AttachFile answerAttachFile = new AttachFile();
            if (mentoringDiary != null) {
                answerAttachFile.setDivision(FILE_DIVISION.MENTORING_ANSWER_ATTACH);
                map.put("wayItemList", mentoringDao.getDiaryWayList(mentoringDiary));

                answerAttachFile.setContentId(mentoringDiary.getDiaryId());
                answerAttachFile.setStatus(FILE_STATUS.A);
                map.put("answerFiles", fileSaveService.getAttachFileList(answerAttachFile));
            }
        }
        return map;
    }

    public void updateCounselApply(CounselApplyForm counselApplyForm) {

//        ASSIGN,RETURN,PROCESS,COMPLETED
        CounselApplyForm resultCounselApplyForm = mentoringDao.getCounselApply(counselApplyForm);

        try {
            Mentor mentor = new Mentor();
            mentor.setMentorId(counselApplyForm.getAssignMentorId());
            if (counselApplyForm.getApplyStatus().equals(APPLY_STATUS.ASSIGN) && !resultCounselApplyForm.getApplyStatus().equals(APPLY_STATUS.ASSIGN)) {

                Email email = new Email();
                email.setToName(mentor.getMentorName());
                email.setTo(mentor.getMentorEmail());
                email.setTitle("멘토링 신청이 접수되었습니다.");
                email.setDesc("홈페이지를 확인해 주세요.");
                emailService.sendEmail(email);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public void updateCounselApplyStatus(CounselApplyForm counselApplyForm) {
        CounselApplyForm resultCounselApplyForm = mentoringDao.getCounselApply(counselApplyForm);
        try {
            if (counselApplyForm.getApplyStatus().equals(APPLY_STATUS.RETURN)) {

                Email email = new Email();
                email.setToName("한양대학교 창업지원단");
                email.setTo(ADMIN_EMAIL);
                email.setTitle("멘토가 신청을 반려하였습니다.");
                email.setDesc("멘토를 다시 배정해 주세요.");
                emailService.sendEmail(email);
            } else if (counselApplyForm.getApplyStatus().equals(APPLY_STATUS.PROCESS)) {

                Email email = new Email();
                email.setToName(resultCounselApplyForm.getMenteeName());
                email.setTo(resultCounselApplyForm.getMenteeEmail());
                email.setTitle("멘토배정이 완료되었습니다.");
                email.setDesc("멘토의 연락 후 멘토링이 진행됩니다.");
                emailService.sendEmail(email);

                Email email2 = new Email();
                email2.setToName("한양대학교 창업지원단");
                email2.setTo(ADMIN_EMAIL);
                email2.setTitle("멘토,멘티 매칭이 완료되었습니다.");
                emailService.sendEmail(email2);
            } else if (counselApplyForm.getApplyStatus().equals(APPLY_STATUS.CANCEL)) {

                Email email = new Email();
                email.setToName(resultCounselApplyForm.getMenteeName());
                email.setTo(resultCounselApplyForm.getMenteeEmail());
                email.setTitle("상담신청 취소");
                email.setDesc("상담신청이 취소되었습니다");
                emailService.sendEmail(email);

                Email email2 = new Email();
                email2.setToName("한양대학교 창업지원단");
                email2.setTo(ADMIN_EMAIL);
                email2.setTitle("상담신청이 취소되었습니다");
                emailService.sendEmail(email2);
            }

        } catch (Exception e) {

        }

        mentoringDao.updateCounselApplyStatus(counselApplyForm);
    }


    public Map<String, Object> getCounselApplyList(CounselApplyForm counselApplyForm) {

        if (counselApplyForm.getPageNo() != null) {
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
    public void applyMentor(Mentor mentor) {

//        String fieldStr = "";
//        for (Integer field: mentor.getMentorFieldList()) {
//            fieldStr += field.toString() + ";";
//        }
//        mentor.setMentorFieldStr(fieldStr);

        String keywordStr = "";
        for (String keyword : mentor.getMentorKeyword()) {
            keywordStr += keyword + ";";
        }
        mentor.setMentorKeywordStr(keywordStr);

        String careerStr = "";
        for (String career : mentor.getMentorCareer()) {
            careerStr += career + ";";
        }
        mentor.setMentorCareerStr(careerStr);

        mentoringDao.applyMentor(mentor);
        mentoringDao.addCounselFieldMentor(mentor);

        fileSaveService.fileSave(mentor.getProfileImg(), mentor.getMentorId(), FILE_DIVISION.MENTOR_PROFILE_IMG);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void updateMentorProfile(Mentor mentor) {

//        String fieldStr = "";
//        for (Integer field: mentor.getMentorFieldList()) {
//            fieldStr += field.toString() + ";";
//        }
//        mentor.setMentorFieldStr(fieldStr);

        String keywordStr = "";
        for (String keyword : mentor.getMentorKeyword()) {
            keywordStr += keyword + ";";
        }

        //빈문자열이 들어가서 초기화
        if (keywordStr == "") {
            keywordStr = null;
        }
        mentor.setMentorKeywordStr(keywordStr);

        String careerStr = "";
        for (String career : mentor.getMentorCareer()) {
            careerStr += career + ";";
        }

        //빈문자열이 들어가서 초기화
        if (careerStr == "") {
            careerStr = null;
        }
        mentor.setMentorCareerStr(careerStr);

        mentoringDao.updateMentorProfile(mentor);

        mentoringDao.removeCounselFieldMentor(mentor);

        if (mentor.getMentorFieldList().size() > 0) {
            mentoringDao.addCounselFieldMentor(mentor);
        }

        if (mentor.getProfileImg() != null) {
            List<AttachFile> attachFileList = new ArrayList<>();

            //이미지가 없었다면
            if (mentor.getFileId() != null) {
                AttachFile attachFile = new AttachFile();
                attachFile.setContentId(mentor.getMentorId());
                attachFile.setDivision(FILE_DIVISION.MENTOR_PROFILE_IMG);
                attachFileList.add(attachFile);

                fileSaveService.deleteAttachFileWithContentId(attachFileList);
            }

            fileSaveService.fileSave(mentor.getProfileImg(), mentor.getMentorId(), FILE_DIVISION.MENTOR_PROFILE_IMG);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void applyCounsel(CounselApplyForm counselApplyForm) throws Exception {
        mentoringDao.applyCounsel(counselApplyForm);
//        mentoringDao.addCounselFieldMentee(counselApplyForm);


        if (counselApplyForm.getSortationIdList().size() > 0) {
            List<SortationItem> sortationItemList = new ArrayList<>();

            for (int id : counselApplyForm.getSortationIdList()) {
                sortationItemList.add(new SortationItem(counselApplyForm.getFormId(), id));
            }

            mentoringDao.addCounselSortation(sortationItemList);
        }

        if (counselApplyForm.getWayIdList().size() > 0) {
            List<WayItem> wayItemList = new ArrayList<>();

            for (int id : counselApplyForm.getWayIdList()) {
                wayItemList.add(new WayItem(counselApplyForm.getFormId(), id));
            }
            mentoringDao.addCounselWay(wayItemList);
        }


        List<AttachFile> attachFileList = new ArrayList<>();

        for (Integer file : counselApplyForm.getUploadResultList()) {
            AttachFile attachFile = new AttachFile();
            attachFile.setContentId(counselApplyForm.getFormId());
            attachFile.setDivision(FILE_DIVISION.MENTORING_ATTACH);
            attachFile.setFileId(file);
            attachFileList.add(attachFile);
        }
        if (attachFileList.size() > 0) {
            fileSaveService.updateAttachFile(attachFileList);
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public void updateApplyCounsel(CounselApplyForm counselApplyForm) throws Exception {
        mentoringDao.updateApplyCounsel(counselApplyForm);
//        mentoringDao.addCounselFieldMentee(counselApplyForm);


        if (counselApplyForm.getSortationIdList().size() > 0) {
            List<SortationItem> sortationItemList = new ArrayList<>();

            for (int id : counselApplyForm.getSortationIdList()) {
                sortationItemList.add(new SortationItem(counselApplyForm.getFormId(), id));
            }

            mentoringDao.resetCounselSortation(counselApplyForm.getFormId());
            mentoringDao.addCounselSortation(sortationItemList);
        }

        if (counselApplyForm.getWayIdList().size() > 0) {
            List<WayItem> wayItemList = new ArrayList<>();

            for (int id : counselApplyForm.getWayIdList()) {
                wayItemList.add(new WayItem(counselApplyForm.getFormId(), id));
            }
            mentoringDao.resetCounselWay(counselApplyForm.getFormId());
            mentoringDao.addCounselWay(wayItemList);
        }


        List<AttachFile> attachFileList = new ArrayList<>();
        List<AttachFile> removeList = new ArrayList<>();

        for (Integer file : counselApplyForm.getUploadResultList()) {
            AttachFile attachFile = new AttachFile();
            attachFile.setContentId(counselApplyForm.getFormId());
            attachFile.setDivision(FILE_DIVISION.MENTORING_ATTACH);
            attachFile.setFileId(file);
            attachFileList.add(attachFile);
        }
        if (attachFileList.size() > 0) {
            fileSaveService.updateAttachFile(attachFileList);
        }

        for (Integer file : counselApplyForm.getRemoveFiles()) {
            AttachFile attachFile = new AttachFile();
            attachFile.setContentId(counselApplyForm.getFormId());
            attachFile.setDivision(FILE_DIVISION.MENTORING_ATTACH);
            attachFile.setFileId(file);
            removeList.add(attachFile);
        }

        if (removeList.size() > 0) {
            fileSaveService.deleteAttachFile(removeList);
        }

        if (counselApplyForm.getApplyStatus().equals(APPLY_STATUS.APPLY)) {
            Email email = new Email();
            email.setToName(counselApplyForm.getMenteeName());
            email.setTo(counselApplyForm.getMenteeEmail());
            email.setTitle("멘토링신청이 접수되었습니다");
            emailService.sendEmail(email);

            Email email2 = new Email();
            email2.setToName("한양대학교 창업지원단");
            email2.setTo(ADMIN_EMAIL);
            email2.setTitle("멘토링신청이 접수되었습니다");
            emailService.sendEmail(email2);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void addDiary(MentoringDiary mentoringDiary) {
        Mentor mentor = new Mentor();
        mentor.setUserId(mentoringDiary.getMentorUserId());
        Mentor searchMentor = mentoringDao.getMentor(mentor);

        mentoringDiary.setMentorId(searchMentor.getMentorId());
        mentoringDao.addDiary(mentoringDiary);

        CounselApplyForm counselApplyForm = new CounselApplyForm();
        counselApplyForm.setFormId(mentoringDiary.getFormId());
        counselApplyForm.setApplyStatus(APPLY_STATUS.COMPLETED);
        mentoringDao.updateCounselApplyStatus(counselApplyForm);

        if (mentoringDiary.getWayIdList().size() > 0) {
            List<WayItem> wayItemList = new ArrayList<>();

            for (int id : mentoringDiary.getWayIdList()) {
                wayItemList.add(new WayItem(mentoringDiary.getDiaryId(), id));
            }
            mentoringDao.addDiaryWay(wayItemList);
        }

        if (mentoringDiary.getFiles() != null) {
            for (MultipartFile file : mentoringDiary.getFiles()) {
                fileSaveService.fileSave(file, mentoringDiary.getDiaryId(), FILE_DIVISION.MENTORING_ANSWER_ATTACH);
            }
        }


        try {

            CounselApplyForm resultCounselApplyForm = mentoringDao.getCounselApply(counselApplyForm);

            Email email = new Email();
            email.setToName(resultCounselApplyForm.getMenteeName());
            email.setTo(resultCounselApplyForm.getMenteeEmail());
            email.setTitle("멘토링이 완료되었습니다.");
            email.setDesc("별점평가 후 멘토 의견 조회가 가능합니다.");
            emailService.sendEmail(email);


            Email email2 = new Email();
            email2.setToName("한양대학교 창업지원단");
            email2.setTo(ADMIN_EMAIL);
            email2.setTitle("멘토링 완료 후 멘토링 일지가 제출되었습니다.");
            emailService.sendEmail(email2);

            Email email3 = new Email();
            email3.setToName(searchMentor.getMentorName());
            email3.setTo(searchMentor.getMentorEmail());
            email3.setTitle("멘토링 일지 제출이 완료되었습니다.");
            emailService.sendEmail(email3);


        } catch (Exception e) {

        }
    }

    public void updateDiary(MentoringDiary mentoringDiary) {

        CounselApplyForm counselApplyForm = new CounselApplyForm();
        counselApplyForm.setFormId(mentoringDiary.getFormId());
        counselApplyForm.setApplyStatus(APPLY_STATUS.COMPLETED);
        counselApplyForm.setUserId(mentoringDiary.getMenteeUserId());

        if (mentoringDao.getCounselApply(counselApplyForm) != null) {
            mentoringDao.updateDiary(mentoringDiary);

        }
//        mentoringDao.updateCounselApplyStatus(counselApplyForm);

    }

    public HSSFWorkbook excelDownloadAll() {
        Mentor mentor = new Mentor();
        mentor.setTotalCount(mentoringDao.getMentorListCnt(mentor));

        mentor.setPageSize(mentor.getTotalCount());

        List<Mentor> mentorList = mentoringDao.getMentorList(mentor);

        mentorList.stream().map(item -> {
            if (item.getMentorCareerStr() != null) {
                item.setMentorCareer(Arrays.asList(item.getMentorCareerStr().split(";").clone()));
            } else {
                item.setMentorCareer(new ArrayList<>());
            }

            if (item.getMentorKeywordStr() != null) {
                item.setMentorKeyword(Arrays.asList(item.getMentorKeywordStr().split(";").clone()));
            } else {
                item.setMentorKeyword(new ArrayList<>());
            }
            item.setMentorFieldList(mentoringDao.getCounselFieldMentor(item));
            return item;
        }).collect(Collectors.toList());

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("멘토 현황");

        HSSFRow row = null;

        HSSFCell cell = null;

        row = sheet.createRow(0);
        String[] headerKey = {"아이디", "멘토 소개", "경력", "이름", "소속", "핸드폰", "키워드", "이메일", "현재 경력", "직책", "우수 멘토"};

        for (int i = 0; i < headerKey.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headerKey[i]);
        }

        for (int i = 0; i < mentorList.size(); i++) {
            row = sheet.createRow(i + 1);
            Mentor vo = mentorList.get(i);

            cell = row.createCell(0);
            cell.setCellValue(vo.getUserId());

            cell = row.createCell(1);
            cell.setCellValue(vo.getMentorIntroduction());

            cell = row.createCell(2);
            cell.setCellValue(vo.getMentorCareerStr());

            cell = row.createCell(3);
            cell.setCellValue(vo.getMentorName());

            cell = row.createCell(4);
            cell.setCellValue(vo.getMentorCompany());
            cell = row.createCell(5);
            cell.setCellValue(vo.getMentorPhoneNumber());
            cell = row.createCell(6);
            cell.setCellValue(vo.getMentorKeywordStr());
            cell = row.createCell(7);
            cell.setCellValue(vo.getMentorEmail());
            cell = row.createCell(8);
            cell.setCellValue(vo.getCurrentCareer());

            cell = row.createCell(9);
            cell.setCellValue(vo.getMentorPosition());

            cell = row.createCell(10);
            if (vo.getIsBest() != null) {
                cell.setCellValue(vo.getIsBest());
            } else {
                cell.setCellValue("");
            }


        }

        return workbook;

    }

    public HSSFWorkbook counselApplyDownloadAll() {

//        Mentor mentor = new Mentor();
//        mentor.setTotalCount(mentoringDao.getMentorListCnt(mentor));
//
//        mentor.setPageSize(mentor.getTotalCount());
//
//        List<Mentor> mentorList = mentoringDao.getMentorList(mentor);

        CounselApplyForm counselApplyForm = new CounselApplyForm();
        counselApplyForm.setTotalCount(mentoringDao.getCounselApplyListCnt(counselApplyForm));
        counselApplyForm.setPageSize(counselApplyForm.getTotalCount());

        List<CounselApplyForm> counselApplyList = mentoringDao.getCounselApplyList(counselApplyForm);
//
//        mentorList.stream().map(item-> {
//            if(item.getMentorCareerStr() != null){
//                item.setMentorCareer(Arrays.asList(item.getMentorCareerStr().split(";").clone()));
//            }else{
//                item.setMentorCareer(new ArrayList<>());
//            }
//
//            if(item.getMentorKeywordStr() != null){
//                item.setMentorKeyword(Arrays.asList(item.getMentorKeywordStr().split(";").clone()));
//            }else{
//                item.setMentorKeyword(new ArrayList<>());
//            }
//            item.setMentorFieldList(mentoringDao.getCounselFieldMentor(item));
//            return item;
//        }).collect(Collectors.toList());

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("멘토 현황");

        HSSFRow row = null;

        HSSFCell cell = null;

        row = sheet.createRow(0);
        String[] headerKey = {"번호", "아이디", "이름", "희망 분야", "구분", "창업 진행 상황", "상담 진행 방식", "상담 제목", "상담 내용", "핸드폰 번호", "E-MAIL", "승인 상태", "신청일", "멘토 아이디", "멘토 이름", "상담 장소", "답변", "상담 진행일", "답변 작성일", "평가"};

        for (int i = 0; i < headerKey.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headerKey[i]);
        }

        List<WayItem> wList = null;
        for (int i = 0; i < counselApplyList.size(); i++) {
            row = sheet.createRow(i + 1);
            CounselApplyForm vo = counselApplyList.get(i);

            CounselApplyForm apply = mentoringDao.getCounselApply(vo);
            apply.setSortationItemList(mentoringDao.getCounselSortationList(apply));
            apply.setWayItemList(mentoringDao.getCounselWayList(apply));

//            if(vo.getApplyStatus() == APPLY_STATUS.COMPLETED || vo.getApplyStatus() == APPLY_STATUS.ASSIGN || vo.getApplyStatus() == APPLY_STATUS.PROCESS){
//                MentoringDiary searchDiary = new MentoringDiary();
//                searchDiary.setFormId(apply.getFormId());
//                MentoringDiary mentoringDiary = mentoringDao.getDiary(searchDiary);
//
//                wList = mentoringDao.getDiaryWayList(mentoringDiary);
//            }

            cell = row.createCell(0);
            cell.setCellValue(vo.getRownum());

            cell = row.createCell(1);
            cell.setCellValue(apply.getUserId());

            cell = row.createCell(2);
            cell.setCellValue(apply.getMenteeName());
            cell = row.createCell(3);
            cell.setCellValue(apply.getFieldName());


            cell = row.createCell(4);
            if (apply.getSortationItemList() != null && apply.getSortationItemList().size() > 0) {
                StringBuilder s = new StringBuilder();
                apply.getSortationItemList().forEach(item -> {
                    s.append(item.getItem() + ", ");
                });
                cell.setCellValue(s.toString());
            } else {
                cell.setCellValue("");
            }

            cell = row.createCell(5);
            cell.setCellValue(apply.getFormProgressItemName());

            cell = row.createCell(6);
            if (apply.getWayItemList() != null && apply.getWayItemList().size() > 0) {
                StringBuilder s = new StringBuilder();
                apply.getWayItemList().forEach(item -> {
                    s.append(item.getItem() + ", ");
                });
                cell.setCellValue(s.toString());
            } else {
                cell.setCellValue("");
            }


            cell = row.createCell(7);
            cell.setCellValue(apply.getTitle());

            cell = row.createCell(8);
            cell.setCellValue(apply.getContent());

            cell = row.createCell(9);
            cell.setCellValue(apply.getMenteePhoneNumber());

            cell = row.createCell(10);
            cell.setCellValue(apply.getMenteeEmail());
            cell = row.createCell(11);
            cell.setCellValue(this.getStatus(apply.getApplyStatus().toString()));
            cell = row.createCell(12);
            cell.setCellValue(apply.getRegDate().toString());


            //멘토

//            private String mentorName;
//            private String mentorUserId;
//            private Integer mentorId;
//
//            //일지
//            private Integer diaryId;
//            private String answer;
//            private String answerWay;
//            private String place;
//            @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
//            private LocalDateTime answerDate;
//
//            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
//            private LocalDateTime start;
//            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
//            private LocalDateTime end;
//            private Integer score;

            cell = row.createCell(13);
            cell.setCellValue(apply.getMentorUserId());
            cell = row.createCell(14);
            cell.setCellValue(apply.getMentorName());
            cell = row.createCell(15);
            cell.setCellValue(apply.getPlace());

            cell = row.createCell(16);
            cell.setCellValue(apply.getAnswer());

            cell = row.createCell(17);
            if (apply.getStart() != null && apply.getEnd() != null) {
                cell.setCellValue(apply.getStart() + " ~ " + apply.getEnd());
            } else {
                cell.setCellValue("");
            }
            cell = row.createCell(18);

            if (apply.getAnswerDate() != null) {
                cell.setCellValue(apply.getAnswerDate().toString());
            } else {
                cell.setCellValue("");
            }
            cell = row.createCell(19);
            if (vo.getScore() != null) {
                cell.setCellValue(apply.getScore());
            } else {
                cell.setCellValue("");
            }
//            cell = row.createCell(8);
//            cell.setCellValue(vo.getMentorEmail());
//            cell = row.createCell(8);
//            cell.setCellValue(vo.getCurrentCareer());
//
//            cell = row.createCell(9);
//            cell.setCellValue(vo.getMentorPosition());
//
//            cell = row.createCell(10);
//            if(vo.getIsBest() != null){
//                cell.setCellValue(vo.getIsBest());
//            }else{
//                cell.setCellValue("");
//            }


        }

        return workbook;

    }

    public String getStatus(String st) {
        String status = "";
        if (st != null) {
            switch (st) {
                case "APPLY":
                    status = "신청";
                    break;
                case "ASSIGN":
                    status = "배정 완료";
                    break;
                case "RETURN":
                    status = "반려";
                    break;
                case "PROCESS":
                    status = "상담 진행중";
                    break;
                case "COMPLETED":
                    status = "상담 완료";
                    break;
            }
        }
        return status;

    }
}
