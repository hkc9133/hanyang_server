package com.hanyang.startup.hanyangstartup.mentoring.controller;

import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.mentoring.dao.MentoringDao;
import com.hanyang.startup.hanyangstartup.mentoring.domain.CounselApplyForm;
import com.hanyang.startup.hanyangstartup.mentoring.domain.CounselFiledCode;
import com.hanyang.startup.hanyangstartup.mentoring.domain.MENTOR_STATUS;
import com.hanyang.startup.hanyangstartup.mentoring.domain.Mentor;
import com.hanyang.startup.hanyangstartup.mentoring.service.MentoringService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/mentoring")
public class AdminMentoringController {

    @Autowired
    private MentoringService mentoringService;

    @Autowired
    private MentoringDao mentoringDao;

    @GetMapping("/counsel_field_code")
    public ResponseEntity<Response> getCounselFieldCode(){
        Response response;
        try {
            List<CounselFiledCode> counselFiledCodeList = mentoringService.getCounselFieldCode();

            response = new Response("success", null, counselFiledCodeList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

        }
    }


    @GetMapping("/mentor")
    public ResponseEntity<Response> getMentorList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, Mentor mentor){
        Response response;
        try {

            mentor.setPageNo(page);
            mentor.setPageSize(pageSize);
            Map<String, Object> map = mentoringService.getMentorList(mentor);

            response = new Response("success", null, map, 200);

            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/mentor/{mentorId}")
    public ResponseEntity<Response> getMentor(@PathVariable("mentorId") Integer mentorId){
        Response response;
        try {
            Mentor mentor = new Mentor();
            mentor.setMentorId(mentorId);
            Map<String, Object> map = mentoringService.getMentor(mentor);

            response = new Response("success", null, map, 200);

            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/mentor/update")
    @ResponseBody
    public ResponseEntity<Response> updateMentorProfile(@ModelAttribute Mentor mentor,BindingResult bindingResult,Principal principal){
        Response response;
//        if(bindingResult.hasErrors()){
//            bindingResult.getAllErrors().forEach(v ->{
//                System.out.println(v.toString());
//            });
//        }
        try {

            mentoringService.updateMentorProfile(mentor);

            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/counsel_apply/{formId}")
    public ResponseEntity<Response> getCounselApply(@PathVariable("formId") Integer formId){
        Response response;
        try {
            CounselApplyForm counselApplyForm = new CounselApplyForm();
            counselApplyForm.setFormId(formId);
            Map<String, Object> map = mentoringService.getCounselApply(counselApplyForm);

            response = new Response("success", null, map, 200);

            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/counsel_apply")
    public ResponseEntity<Response> updateCounselApply(@RequestBody CounselApplyForm counselApplyForm){
        Response response;
        try {

            mentoringService.updateCounselApply(counselApplyForm);

            response = new Response("success", null, null, 200);

            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/counsel_apply")
    public ResponseEntity<Response> getCounselApplyList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, CounselApplyForm counselApplyForm){
        Response response;
        try {

            counselApplyForm.setPageNo(page);
            counselApplyForm.setPageSize(pageSize);
            Map<String, Object> map = mentoringService.getCounselApplyList(counselApplyForm);

            response = new Response("success", null, map, 200);

            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/excel_download")
    public void excelDownloadAll(Principal principal, HttpServletRequest req, HttpServletResponse res) throws IOException {

        OutputStream out = null;

        try {
            HSSFWorkbook workbook = mentoringService.excelDownloadAll();


            res.reset();
            String userAgent = req.getHeader("User-Agent");

            String fileName = "멘토 현황.xls";
            boolean ie = (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1);

            if(ie) {
                fileName = URLEncoder.encode( "fileName", "utf-8" ).replaceAll("\\+", "%20");
            } else {
                fileName = new String( String.valueOf(fileName).getBytes("utf-8"), "iso-8859-1");
            }

            res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
            res.setHeader("Content-Transfer-Encoding", "binary");

            res.setContentType("application/vnd.ms-excel;");
            out = new BufferedOutputStream(res.getOutputStream());

            workbook.write(out);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(out != null) out.close();
        }
    }

    @GetMapping("/counsel_apply/excel_download")
    public void counselApplyDownloadAll(Principal principal, HttpServletRequest req, HttpServletResponse res) throws IOException {

        OutputStream out = null;

        try {
            HSSFWorkbook workbook = mentoringService.counselApplyDownloadAll();


            res.reset();
            String userAgent = req.getHeader("User-Agent");

            String fileName = "상담신청 현황.xls";
            boolean ie = (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1);

            if(ie) {
                fileName = URLEncoder.encode( "fileName", "utf-8" ).replaceAll("\\+", "%20");
            } else {
                fileName = new String( String.valueOf(fileName).getBytes("utf-8"), "iso-8859-1");
            }

            res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
            res.setHeader("Content-Transfer-Encoding", "binary");

            res.setContentType("application/vnd.ms-excel;");
            out = new BufferedOutputStream(res.getOutputStream());

            workbook.write(out);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(out != null) out.close();
        }
    }


}
