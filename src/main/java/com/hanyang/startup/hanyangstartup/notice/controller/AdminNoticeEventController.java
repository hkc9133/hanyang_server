package com.hanyang.startup.hanyangstartup.notice.controller;

import com.hanyang.startup.hanyangstartup.board.domain.BoardContent;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.notice.domain.Notice;
import com.hanyang.startup.hanyangstartup.notice.domain.NoticeCategoryCode;
import com.hanyang.startup.hanyangstartup.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/notice")
public class AdminNoticeEventController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping(value = "/cate")
    public ResponseEntity<Response> getNoticeCategoryCodeList() {

        Response response;
        try {

            List<NoticeCategoryCode> noticeCategoryCodeList = noticeService.getNoticeCategoryCodeList();
            response = new Response("success", null, noticeCategoryCodeList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{noticeId}")
    public ResponseEntity<Response> getNotice(@PathVariable("noticeId") Integer noticeId) {

        Response response;
        try {
            Notice notice = new Notice();
            notice.setNoticeId(noticeId);

            Map<String,Object> map = noticeService.getNotice(notice);
            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/content")
    public ResponseEntity<Response> addNotice(Notice notice, Principal principal) {

        Response response;
        try {
            System.out.println("게시글 추가");
            notice.setWriterId(principal.getName());
            noticeService.addNotice(notice);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/content/edit")
    public ResponseEntity<Response> updateNotice(Notice notice, Principal principal) {

        Response response;
        try {
            System.out.println("게시글 수정");
            notice.setWriterId(principal.getName());
            noticeService.updateNotice(notice);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Response> getNoticeList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "0") Integer pageSize, @RequestParam(value = "categoryCodeId", required = false) Integer categoryCodeId, @RequestParam(value = "searchValue", required = false) String searchValue, @RequestParam(value = "searchField", required = false) String searchField, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {


        Response response;
        try {
            Notice notice = new Notice();

            notice.setPageNo(page);
            notice.setPageSize(pageSize);
            notice.setCategoryCodeId(categoryCodeId);
            notice.setSearchField(searchField);
            notice.setSearchValue(searchValue);
            notice.setStartDate(startDate);
            notice.setEndDate(endDate);

            Map<String,Object> map = noticeService.getNoticeList(notice);
            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
}
