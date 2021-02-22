package com.hanyang.startup.hanyangstartup.startupPresent.controller;

import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.startupPresent.domain.StartupPresent;
import com.hanyang.startup.hanyangstartup.startupPresent.service.StartupPresentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/admin/startup_present")
public class AdminStartupPresentController {

    @Autowired
    private StartupPresentService startupPresentService;

    @GetMapping("/field")
    public ResponseEntity<Response> getFieldList(){
        Response response;
        try {
            Map<String, Object>  result = startupPresentService.getFieldList();

            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/{startupId}")
    public ResponseEntity<Response> getStartupPresent(@PathVariable("startupId") int startupId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            StartupPresent startupPresent = new StartupPresent();
            startupPresent.setStartupId(startupId);
            StartupPresent result = startupPresentService.getStartupPresent(startupPresent);

            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/list")
    public ResponseEntity<Response> getStartupPresentList(@RequestBody StartupPresent startupPresent) {
        Response response;
        try {

            Map<String, Object>  result = startupPresentService.getStartupPresentList(startupPresent);

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
    public ResponseEntity<Response> addStartupPresent(StartupPresent startupPresent, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {

            System.out.println("스타트업 추가");
            System.out.println(startupPresent);
            startupPresentService.addStartupPresent(startupPresent);
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
    public ResponseEntity<Response> updateStartupPresent(StartupPresent startupPresent, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {

            System.out.println("스타트업 업데이트");
            System.out.println(startupPresent);
            startupPresentService.updateStartupPresent(startupPresent);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{startupPresentId}")
    public ResponseEntity<Response> deleteStartupPresent(@PathVariable("startupPresentId") int startupPresentId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            StartupPresent startupPresent = new StartupPresent();
            startupPresent.setStartupId(startupPresentId);

            startupPresentService.deleteStartupPresent(startupPresent);

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
