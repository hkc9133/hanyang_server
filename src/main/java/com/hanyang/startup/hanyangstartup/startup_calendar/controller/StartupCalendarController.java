package com.hanyang.startup.hanyangstartup.startup_calendar.controller;

import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.startup_calendar.domain.StartupCalendar;
import com.hanyang.startup.hanyangstartup.startup_calendar.service.StartupCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/startup_calendar")
public class StartupCalendarController {

    @Autowired
    private StartupCalendarService startupCalendarService;


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
            response = new Response("error", "에러가 발생했습니다", "", 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Response> getStartupCalendarList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "0") Integer pageSize, @RequestParam(value = "categoryCodeId", required = false) Integer categoryCodeId, @RequestParam(value = "searchValue", required = false) String searchValue, @RequestParam(value = "searchField", required = false) String searchField,@RequestParam(value = "date", required = false) String date) {

        Response response;
        try {
            StartupCalendar startupCalendar = new StartupCalendar();

            startupCalendar.setPageNo(page);
            startupCalendar.setPageSize(pageSize);
            startupCalendar.setCategoryCodeId(categoryCodeId);
            startupCalendar.setSearchField(searchField);
            startupCalendar.setSearchValue(searchValue);
            startupCalendar.setDate(date);

//            System.out.println("캘린더");
//            System.out.println(startupCalendar);

            Map<String,Object> map = startupCalendarService.getStartupCalendarList(startupCalendar);
            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", "에러가 발생했습니다", "", 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
}
