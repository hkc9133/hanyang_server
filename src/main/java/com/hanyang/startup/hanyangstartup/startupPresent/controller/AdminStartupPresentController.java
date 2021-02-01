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
    public ResponseEntity<Response> getStartupPresentList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, @RequestParam(value = "searchValue", required = false) String searchValue, @RequestParam(value = "searchField", required = false) String searchField, @RequestParam(value="startDate", required = false) String startDate, @RequestParam(value="endDate", required = false) String endDate) {
        Response response;
        try {
            StartupPresent startupPresent = new StartupPresent();
            startupPresent.setPageNo(page);
            startupPresent.setStartDate(startDate);
            startupPresent.setEndDate(endDate);
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
    //팝업 생성
    @PostMapping
    public ResponseEntity<Response> addStartupPresent(@RequestBody StartupPresent startupPresent, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
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
    public ResponseEntity<Response> updateStartupPresent(@RequestBody StartupPresent startupPresent, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {

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

    //팝업 삭제
    @DeleteMapping("/{startupPresentId}")
    public ResponseEntity<Response> deleteStartupPresent(@PathVariable("startupPresentId") int startupPresentId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            StartupPresent startupPresent = new StartupPresent();
            startupPresent.setStartupPresentId(startupPresentId);

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
