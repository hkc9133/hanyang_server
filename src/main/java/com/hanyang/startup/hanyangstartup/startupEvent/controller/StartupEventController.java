package com.hanyang.startup.hanyangstartup.startupEvent.controller;

import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.startupEvent.domain.StartupEvent;
import com.hanyang.startup.hanyangstartup.startupEvent.service.StartupEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/startup_event")
public class StartupEventController {

    @Autowired
    private StartupEventService startupEventService;

    @GetMapping(value = "/list")
    public ResponseEntity<Response> getStartupEventList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "0") Integer pageSize, @RequestParam(value = "boardEnName", required = false) String boardEnName,  @RequestParam(value = "categoryCodeId", required = false) Integer categoryCodeId, @RequestParam(value = "searchValue", required = false) String searchValue, @RequestParam(value = "searchField", required = false) String searchField,@RequestParam(value = "date", required = false) String date) {

        Response response;
        try {
            StartupEvent startupEvent = new StartupEvent();

            startupEvent.setPageNo(page);
            startupEvent.setPageSize(pageSize);
            startupEvent.setCategoryCodeId(categoryCodeId);
            startupEvent.setSearchField(searchField);
            startupEvent.setSearchValue(searchValue);
            startupEvent.setDate(date);

            Map<String,Object> map = startupEventService.getStartupEventList(startupEvent);
            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
}
