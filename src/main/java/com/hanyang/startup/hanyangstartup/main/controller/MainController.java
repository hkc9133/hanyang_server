package com.hanyang.startup.hanyangstartup.main.controller;

import com.hanyang.startup.hanyangstartup.board.domain.BoardCategoryCode;
import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.main.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/main")
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("/data")
    public ResponseEntity<Response> getMainData() {
        Response response;

        try {

            Map<String, Object> map = mainService.getMainDate();

            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
}
