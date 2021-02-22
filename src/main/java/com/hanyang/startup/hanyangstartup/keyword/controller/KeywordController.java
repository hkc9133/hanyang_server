package com.hanyang.startup.hanyangstartup.keyword.controller;

import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.keyword.domain.Keyword;
import com.hanyang.startup.hanyangstartup.keyword.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/popup")
public class KeywordController {

    @Autowired
    private KeywordService keywordService;

//
//    //팝업 리스트 조회
//    @GetMapping("/list")
//    public ResponseEntity<Response> getKeywordList() {
//        Response response;
//        try {
//
//            Keyword keyword = new Keyword();
//            keyword.setToday(LocalDateTime.now());
//            Map<String, Object>  result = keywordService.getKeywordList(keyword);
//
//            response = new Response("success", null, result, 200);
//            return new ResponseEntity(response, HttpStatus.OK);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            response = new Response("fail", null, null, 400);
//            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
//        }
//    }
}
