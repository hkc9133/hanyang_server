package com.hanyang.startup.hanyangstartup.startup_calendar.controller;

import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.startup_calendar.domain.PROGRESS_STATUS;
import com.hanyang.startup.hanyangstartup.startup_calendar.domain.StartupCalendar;
import com.hanyang.startup.hanyangstartup.startup_calendar.domain.StartupCalendarCategoryCode;
import com.hanyang.startup.hanyangstartup.startup_calendar.service.StartupCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/startup_calendar")
public class AdminStartupCalendarController {

    @Autowired
    private StartupCalendarService startupCalendarService;

    @GetMapping(value = "/cate")
    public ResponseEntity<Response> getStartupCalendarCategoryCodeList() {

        Response response;
        try {

            List<StartupCalendarCategoryCode> startupCalendarCategoryCodeList = startupCalendarService.getStartupCalendarCategoryCodeList();
            response = new Response("success", null, startupCalendarCategoryCodeList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{startupCalendarId}")
    public ResponseEntity<Response> getStartupCalendar(@PathVariable("startupCalendarId") Integer startupCalendarId) {

        Response response;
        try {
            StartupCalendar startupCalendar = new StartupCalendar();
            startupCalendar.setStartupCalendarId(startupCalendarId);

            Map<String,Object> map = startupCalendarService.getStartupCalendar(startupCalendar);
            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/content")
    public ResponseEntity<Response> addStartupCalendar(StartupCalendar startupCalendar, Principal principal) {

        Response response;
        try {
            System.out.println("게시글 추가");
            startupCalendar.setWriterId(principal.getName());
            startupCalendarService.addStartupCalendar(startupCalendar);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/content/edit")
    public ResponseEntity<Response> updateStartupCalendar(StartupCalendar startupCalendar, Principal principal) {

        Response response;
        try {
            System.out.println("게시글 수정");
            startupCalendar.setWriterId(principal.getName());
            startupCalendarService.updateStartupCalendar(startupCalendar);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/content/delete/{startupCalendarId}")
    public ResponseEntity<Response> deleteStartupCalendar(@PathVariable("startupCalendarId") Integer startupCalendarId,Principal principal) {

        Response response;
        try {
            System.out.println("게시글 삭제");
            StartupCalendar startupCalendar = new StartupCalendar();
            startupCalendar.setStartupCalendarId(startupCalendarId);
            startupCalendarService.deleteStartupCalendar(startupCalendar);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Response> getStartupCalendarList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "0") Integer pageSize, @RequestParam(value = "categoryCodeId", required = false) Integer categoryCodeId, @RequestParam(value = "searchValue", required = false) String searchValue, @RequestParam(value = "searchField", required = false) String searchField, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,@RequestParam("progressStatus") PROGRESS_STATUS progressStatus) {


        Response response;
        try {
            StartupCalendar startupCalendar = new StartupCalendar();

            startupCalendar.setPageNo(page);
            startupCalendar.setPageSize(pageSize);
            startupCalendar.setCategoryCodeId(categoryCodeId);
            startupCalendar.setSearchField(searchField);
            startupCalendar.setSearchValue(searchValue);
            startupCalendar.setStartDate(startDate);
            startupCalendar.setEndDate(endDate);
            startupCalendar.setProgressStatus(progressStatus);


            Map<String,Object> map = startupCalendarService.getStartupCalendarList(startupCalendar);
            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
}
