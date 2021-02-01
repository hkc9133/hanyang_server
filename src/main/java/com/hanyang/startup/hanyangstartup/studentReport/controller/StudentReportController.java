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
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/student_report")
public class StudentReportController {

    @Autowired
    private StudentReportService studentReportService;

    //창업 신고 조회
    @GetMapping("/{studentReportId}")
    public ResponseEntity<Response> getStudentReport(@PathVariable("studentReportId") int studentReportId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            StudentReport studentReport = new StudentReport();
            studentReport.setStudentReportId(studentReportId);
            StudentReport result = studentReportService.getStudentReport(studentReport);

            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //창업 신고 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<Response> getStudentReportList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "group",required = false) String group, @RequestParam(value = "field",required = false) Integer fieldId,@RequestParam(value = "searchValue", required = false) String searchValue, @RequestParam(value = "searchField", required = false) String searchField) {
        Response response;
        try {

            StudentReport studentReport = new StudentReport();
            studentReport.setPageNo(page);
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

    @PostMapping
    public ResponseEntity<Response> addStudentReport(@RequestBody StudentReport studentReport, HttpServletRequest req, HttpServletResponse res, Principal principal){
        Response response;
        try {
            studentReport.setUserId(principal.getName());
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

    @PostMapping("/edit")
    public ResponseEntity<Response> updateStudentReport(@RequestBody StudentReport studentReport, HttpServletRequest req, HttpServletResponse res, Principal principal){
        Response response;
        try {

            studentReport.setUserId(principal.getName());
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

}
