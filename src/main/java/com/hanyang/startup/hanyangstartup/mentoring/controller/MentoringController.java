package com.hanyang.startup.hanyangstartup.mentoring.controller;

import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.common.exception.CustomException;
import com.hanyang.startup.hanyangstartup.mentoring.domain.*;
import com.hanyang.startup.hanyangstartup.mentoring.service.MentoringService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mentoring")
public class MentoringController {

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
    @GetMapping("/progress_item")
    public ResponseEntity<Response> getProgressItem(){
        Response response;
        try {
            List<ProgressItem> progressItemList = mentoringService.getProgressItem();

            response = new Response("success", null, progressItemList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/sortation_item")
    public ResponseEntity<Response> getSortationItem(){
        Response response;
        try {
            List<SortationItem> sortationItemList = mentoringService.getSortationItem();

            response = new Response("success", null, sortationItemList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/way_item")
    public ResponseEntity<Response> getWayItem(){
        Response response;
        try {
            List<WayItem> wayItemList = mentoringService.getWayItem();

            response = new Response("success", null, wayItemList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/mentor")
    public ResponseEntity<Response> getMentor(Principal principal){
        Response response;
        try {
            Mentor mentor = new Mentor();
//            mentor.setUserId(principal.getName());
            mentor.setUserId(principal.getName());

            if(mentoringService.getMentorList(mentor).size() > 0){
                response = new Response("success", null, null, 409);

            }else{
                response = new Response("success", null, null, 200);
            }

            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/mentor/list")
    public ResponseEntity<Response> getMentorList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "6") Integer pageSize, @RequestParam(value = "counselField", defaultValue = "") Integer counselField,Principal principal){
        Response response;
        try {

            Mentor mentor = new Mentor();
            mentor.setMentorStatus(MENTOR_STATUS.ACCEPT);
            mentor.setPageNo(page);
            mentor.setPageSize(pageSize);
            if(counselField != null){
                List<Integer> counselFieldList = new ArrayList<>();
                counselFieldList.add(counselField);
                mentor.setMentorFieldList(counselFieldList);
            }

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

    @PostMapping("/mentor/apply")
    public ResponseEntity<Response> applyMentor(Mentor mentor, Principal principal){
        Response response;
        try {

            mentor.setUserId(principal.getName());

            mentoringService.applyMentor(mentor);

            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

        }
    }


    @PostMapping("/counsel/apply")
    public ResponseEntity<Response> applyCounsel(CounselApplyForm counselApplyForm, Principal principal){
        Response response;
        try {

            counselApplyForm.setUserId(principal.getName());

            System.out.println("====>상담 신청");

            mentoringService.applyCounsel(counselApplyForm);

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
    public ResponseEntity<Response> getCounselApply(@PathVariable("formId") Integer formId, Principal principal){
        Response response;
        try {
            CounselApplyForm counselApplyForm = new CounselApplyForm();
            counselApplyForm.setUserId(principal.getName());
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

    @PutMapping("/counsel_apply/status")
    public ResponseEntity<Response> updateCounselApplyStatus(@RequestBody CounselApplyForm counselApplyForm){
        Response response;
        try {

            mentoringService.updateCounselApplyStatus(counselApplyForm);

            response = new Response("success", null, counselApplyForm.getApplyStatus(), 200);

            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/counsel_apply/mentor/{formId}")
    public ResponseEntity<Response> getMentorCounselApply(@PathVariable("formId") Integer formId, Principal principal){
        Response response;
        try {
            CounselApplyForm counselApplyForm = new CounselApplyForm();
            counselApplyForm.setMentorUserId(principal.getName());
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

    @GetMapping("/counsel/apply")
    public ResponseEntity<Response> getCounselApplyList(@RequestParam(value = "page", defaultValue = "1") Integer page, Principal principal){
        Response response;
        try {
            CounselApplyForm counselApplyForm = new CounselApplyForm();
            counselApplyForm.setPageNo(page);
//            counselApplyForm.setUserId(principal.getName());

            counselApplyForm.setUserId(principal.getName());

            Map<String,Object> map = mentoringService.getCounselApplyList(counselApplyForm);

            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/counsel_apply/mentor")
    public ResponseEntity<Response> getMentorCounselApplyList(@RequestParam(value = "page", defaultValue = "1") Integer page, Principal principal){
        Response response;
        try {
            CounselApplyForm counselApplyForm = new CounselApplyForm();
            counselApplyForm.setPageNo(page);
//            counselApplyForm.setUserId(principal.getName());

            counselApplyForm.setMentorUserId(principal.getName());

            Map<String,Object> map = mentoringService.getCounselApplyList(counselApplyForm);

            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/counsel_apply/diary")
    public ResponseEntity<Response> addDiary(MentoringDiary mentoringDiary, Principal principal){
        Response response;
        try {

            mentoringDiary.setMentorUserId(principal.getName());

            System.out.println("====>답변 작성");

            mentoringService.addDiary(mentoringDiary);

            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping("/counsel_apply/diary")
    public ResponseEntity<Response> updateDiary(@RequestBody MentoringDiary mentoringDiary, Principal principal){
        Response response;
        try {

            mentoringDiary.setMenteeUserId(principal.getName());

            System.out.println("====>답변 수정");

            mentoringService.updateDiary(mentoringDiary);

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
