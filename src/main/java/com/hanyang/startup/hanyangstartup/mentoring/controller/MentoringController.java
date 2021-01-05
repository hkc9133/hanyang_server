package com.hanyang.startup.hanyangstartup.mentoring.controller;

import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.common.exception.CustomException;
import com.hanyang.startup.hanyangstartup.mentoring.domain.*;
import com.hanyang.startup.hanyangstartup.mentoring.service.MentoringService;
import com.hanyang.startup.hanyangstartup.resource.domain.UploadResult;
import com.sun.javaws.progress.Progress;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
            mentor.setUserId("admin");

            if(mentoringService.getMentor(mentor).size() > 0){
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
    public ResponseEntity<Response> getMentorList(Principal principal){
        Response response;
        try {

            Mentor mentor = new Mentor();
            mentor.setMentorStatus(MENTOR_STATUS.ACCEPT);
            List<Mentor> mentorList = mentoringService.getMentor(mentor);

            response = new Response("success", null, mentorList, 200);

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

            mentor.setUserId("admin");

            System.out.println("====>멘토 신청");
            System.out.println(mentor);
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

            counselApplyForm.setUserId("admin");

            System.out.println("====>상담 신청");

            System.out.println(counselApplyForm);
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

    @GetMapping("/counsel/apply")
    public ResponseEntity<Response> getCounselApplyList(@RequestParam(value = "page", defaultValue = "1") Integer page, Principal principal){
        Response response;
        try {
            CounselApplyForm counselApplyForm = new CounselApplyForm();
            counselApplyForm.setPageNo(page);
//            counselApplyForm.setUserId(principal.getName());

            counselApplyForm.setUserId("admin");

            System.out.println("====>상담 리스트");

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

}
