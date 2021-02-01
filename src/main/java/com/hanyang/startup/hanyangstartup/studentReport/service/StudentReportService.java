package com.hanyang.startup.hanyangstartup.studentReport.service;

import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import com.hanyang.startup.hanyangstartup.studentReport.dao.StudentReportDao;
import com.hanyang.startup.hanyangstartup.studentReport.domain.StudentReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void addStudentReport(StudentReport studentReport){

        studentReportDao.addStudentReport(studentReport);

    }
    @Transactional(rollbackFor = {Exception.class})
    public void updateStudentReport(StudentReport studentReport){
        studentReportDao.updateStudentReport(studentReport);
    }

    public StudentReport getStudentReport(StudentReport studentReport){
        return studentReportDao.getStudentReport(studentReport);
    }
    public Map<String, Object> getStudentReportList(StudentReport studentReport){

        studentReport.setTotalCount(studentReportDao.getStudentReportListCnt(studentReport));
        List<StudentReport> studentReportList = studentReportDao.getStudentReportList(studentReport);


        Map<String, Object> map = new HashMap<>();

        map.put("page", studentReport);
        map.put("list", studentReportList);

        return map;

    }

    public void deleteStudentReport(StudentReport studentReport){
        studentReportDao.deleteStudentReport(studentReport);
    }
}
