package com.hanyang.startup.hanyangstartup.startupPresent.controller;

import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RENTAL_STATUS;
import com.hanyang.startup.hanyangstartup.startupPresent.domain.StartupPresent;
import com.hanyang.startup.hanyangstartup.startupPresent.service.StartupPresentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/startup_present")
public class StartupPresentController {

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


    @GetMapping("/{startupPresentId}")
    public ResponseEntity<Response> getStartupPresent(@PathVariable("startupPresentId") int startupPresentId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            StartupPresent startupPresent = new StartupPresent();
            startupPresent.setStartupId(startupPresentId);
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

    //팝업 리스트 조회
    @PostMapping("/list")
    public ResponseEntity<Response> getStartupPresentList(@RequestBody StartupPresent startupPresent) {
        Response response;
        try {

            Map<String, Object>  result = startupPresentService.getStartupPresentList(startupPresent);

            System.out.println("스타트업 리스트 <====");

            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/best")
    public ResponseEntity<Response> getBestStartupList() {
        Response response;
        try {

            Map<String, Object>  result = startupPresentService.getBestStartupList();

            System.out.println("스타트업 베스트 리스트 <====");

            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
}
