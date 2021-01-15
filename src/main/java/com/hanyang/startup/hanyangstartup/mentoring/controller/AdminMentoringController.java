package com.hanyang.startup.hanyangstartup.mentoring.controller;

import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.mentoring.domain.CounselApplyForm;
import com.hanyang.startup.hanyangstartup.mentoring.domain.CounselFiledCode;
import com.hanyang.startup.hanyangstartup.mentoring.domain.MENTOR_STATUS;
import com.hanyang.startup.hanyangstartup.mentoring.domain.Mentor;
import com.hanyang.startup.hanyangstartup.mentoring.service.MentoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/mentoring")
public class AdminMentoringController {

    @Autowired
    private MentoringService mentoringService;

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


    @PostMapping("/mentor")
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

    @PutMapping("/mentor")
    public ResponseEntity<Response> updateMentor(@RequestBody Mentor mentor){
        Response response;
        try {

            mentoringService.updateMentor(mentor);

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


}
