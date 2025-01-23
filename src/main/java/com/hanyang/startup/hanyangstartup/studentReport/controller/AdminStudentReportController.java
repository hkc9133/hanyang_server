package com.hanyang.startup.hanyangstartup.studentReport.controller;

import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.studentReport.domain.StudentReport;
import com.hanyang.startup.hanyangstartup.studentReport.service.StudentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/admin/student_report")
public class AdminStudentReportController {

    @Autowired
    private StudentReportService studentReportService;

    //팝업 조회
    @GetMapping("/{studentReportId}")
    public ResponseEntity<Response> getStudentReport(@PathVariable("studentReportId") int studentReportId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            StudentReport studentReport = new StudentReport();
            studentReport.setStudentReportId(studentReportId);
            StudentReport result = studentReportService.getStudentReport(studentReport);

            System.out.println("DFDFDFD");
            System.out.println(result);

            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //팝업 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<Response> getStudentReportList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, @RequestParam(value = "searchValue", required = false) String searchValue, @RequestParam(value = "searchField", required = false) String searchField, @RequestParam(value="startDate", required = false) String startDate, @RequestParam(value="endDate", required = false) String endDate) {
        Response response;
        try {
            StudentReport studentReport = new StudentReport();
            studentReport.setPageNo(page);
            studentReport.setStartDate(startDate);
            studentReport.setEndDate(endDate);
            studentReport.setSearchField(searchField);
            studentReport.setSearchValue(searchValue);

            Map<String, Object>  result = studentReportService.getStudentReportList(studentReport);

            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    //학생 창업 신고
    @PostMapping
    public ResponseEntity<Response> addStudentReport(@RequestBody StudentReport studentReport, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            studentReportService.addStudentReport(studentReport);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //팝업 수정
    @PostMapping("/edit")
    public ResponseEntity<Response> updateStudentReport(@RequestBody StudentReport studentReport, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {

            studentReportService.updateStudentReport(studentReport);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //팝업 삭제
    @DeleteMapping("/{studentReportId}")
    public ResponseEntity<Response> deleteStudentReport(@PathVariable("studentReportId") int studentReportId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            StudentReport studentReport = new StudentReport();
            studentReport.setStudentReportId(studentReportId);

            studentReportService.deleteStudentReport(studentReport);

            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
}
