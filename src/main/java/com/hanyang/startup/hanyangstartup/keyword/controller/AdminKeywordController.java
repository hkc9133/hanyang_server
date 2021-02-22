package com.hanyang.startup.hanyangstartup.keyword.controller;

import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.keyword.domain.Keyword;
import com.hanyang.startup.hanyangstartup.keyword.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/admin/keyword")
public class AdminKeywordController {

    @Autowired
    private KeywordService keywordService;

    //키워드 조회
    @GetMapping("/{keywordId}")
    public ResponseEntity<Response> getKeyword(@PathVariable("keywordId") int keywordId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            Keyword keyword = new Keyword();
            keyword.setKeywordId(keywordId);
            Keyword result = keywordService.getKeyword(keyword);

            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //키워드 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<Response> getKeywordList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, @RequestParam(value = "searchValue", required = false) String searchValue, @RequestParam(value = "searchField", required = false) String searchField, @RequestParam(value="startDate", required = false) String startDate, @RequestParam(value="endDate", required = false) String endDate) {
        Response response;
        try {
            Keyword keyword = new Keyword();
            keyword.setPageNo(page);
            keyword.setStartDate(startDate);
            keyword.setEndDate(endDate);
            keyword.setSearchField(searchField);
            keyword.setSearchValue(searchValue);

            Map<String, Object>  result = keywordService.getKeywordList(keyword);

            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    //키워드 생성
    @PostMapping
    public ResponseEntity<Response> addKeyword(@RequestBody Keyword keyword, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            keywordService.addKeyword(keyword);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //키워드 수정
    @PostMapping("/edit")
    public ResponseEntity<Response> updateKeyword(@RequestBody Keyword keyword, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {

            keywordService.updateKeyword(keyword);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //키워드 삭제
    @DeleteMapping("/{keywordId}")
    public ResponseEntity<Response> deleteKeyword(@PathVariable("keywordId") int keywordId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            Keyword keyword = new Keyword();
            keyword.setKeywordId(keywordId);

            keywordService.deleteKeyword(keyword);

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
