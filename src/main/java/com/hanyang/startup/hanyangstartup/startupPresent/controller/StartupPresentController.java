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


    //팝업 조회
    @GetMapping("/{startupPresentId}")
    public ResponseEntity<Response> getStartupPresent(@PathVariable("startupPresentId") int startupPresentId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            StartupPresent startupPresent = new StartupPresent();
            startupPresent.setStartupPresentId(startupPresentId);
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
    @GetMapping("/list")
    public ResponseEntity<Response> getStartupPresentList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "group",required = false) String group, @RequestParam(value = "field",required = false) Integer fieldId,@RequestParam(value = "searchValue", required = false) String searchValue, @RequestParam(value = "searchField", required = false) String searchField) {
        Response response;
        try {

            StartupPresent startupPresent = new StartupPresent();
            startupPresent.setPageNo(page);
            startupPresent.setGroup(group);
            startupPresent.setFieldId(fieldId);
            startupPresent.setSearchField(searchField);
            startupPresent.setSearchValue(searchValue);
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
}
