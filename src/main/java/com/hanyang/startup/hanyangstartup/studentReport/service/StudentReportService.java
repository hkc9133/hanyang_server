package com.hanyang.startup.hanyangstartup.studentReport.service;

import com.hanyang.startup.hanyangstartup.mentoring.domain.APPLY_STATUS;
import com.hanyang.startup.hanyangstartup.mentoring.domain.MentoringDiary;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_STATUS;
import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import com.hanyang.startup.hanyangstartup.studentReport.dao.StudentReportDao;
import com.hanyang.startup.hanyangstartup.studentReport.domain.StudentReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentReportService {

    @Autowired
    private StudentReportDao studentReportDao;

    @Autowired
    private FileSaveService fileSaveService;

    @Transactional(rollbackFor = {Exception.class})
    public void addStudentReport(StudentReport studentReport) {

        studentReportDao.addStudentReport(studentReport);

        List<AttachFile> attachFileList = new ArrayList<>();

        for (Integer file : studentReport.getUploadResultList()) {
            AttachFile attachFile = new AttachFile();
            attachFile.setContentId(studentReport.getStudentReportId());
            attachFile.setDivision(FILE_DIVISION.STUDENT_ATTACH);
            attachFile.setFileId(file);
            attachFileList.add(attachFile);
        }
        if (attachFileList.size() > 0) {
            fileSaveService.updateAttachFile(attachFileList);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public void updateStudentReport(StudentReport studentReport) {
        studentReportDao.updateStudentReport(studentReport);
    }

    public StudentReport getStudentReport(StudentReport studentReport) {

        StudentReport report = studentReportDao.getStudentReport(studentReport);

        Map<String, Object> map = new HashMap<>();

        AttachFile attachFile = new AttachFile();
        attachFile.setContentId(report.getStudentReportId());
        attachFile.setDivision(FILE_DIVISION.STUDENT_ATTACH);
        attachFile.setStatus(FILE_STATUS.A);

        report.setFiles(fileSaveService.getAttachFileList(attachFile));
//        map.put("files", fileSaveService.getAttachFileList(attachFile));


        return report;

//        return studentReportDao.getStudentReport(studentReport);
    }

    public Map<String, Object> getStudentReportList(StudentReport studentReport) {

        studentReport.setTotalCount(studentReportDao.getStudentReportListCnt(studentReport));
        List<StudentReport> studentReportList = studentReportDao.getStudentReportList(studentReport);


        Map<String, Object> map = new HashMap<>();

        map.put("page", studentReport);
        map.put("list", studentReportList);

        return map;

    }

    public void deleteStudentReport(StudentReport studentReport) {
        studentReportDao.deleteStudentReport(studentReport);
    }
}
