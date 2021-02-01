package com.hanyang.startup.hanyangstartup.studentReport.dao;

import com.hanyang.startup.hanyangstartup.studentReport.domain.StudentReport;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentReportDao {
    @Autowired
    protected SqlSessionTemplate sqlSession;
    public void addStudentReport(StudentReport studentReport){
        sqlSession.insert("student_report.addStudentReport", studentReport);
    }
    public void updateStudentReport(StudentReport studentReport){
        sqlSession.update("student_report.updateStudentReport", studentReport);
    }

    public StudentReport getStudentReport(StudentReport studentReport){
        return sqlSession.selectOne("student_report.getStudentReport", studentReport);
    }
    public List<StudentReport> getStudentReportList(StudentReport studentReport){
        return sqlSession.selectList("student_report.getStudentReportList", studentReport);
    }

    public int getStudentReportListCnt(StudentReport studentReport){
        return sqlSession.selectOne("student_report.getStudentReportListCnt", studentReport);
    }


    public int deleteStudentReport(StudentReport studentReport){
        return sqlSession.delete("student_report.deleteStudentReport", studentReport);
    }
}
