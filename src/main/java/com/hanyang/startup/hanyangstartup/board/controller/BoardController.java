package com.hanyang.startup.hanyangstartup.board.controller;

import com.hanyang.startup.hanyangstartup.board.domain.*;
import com.hanyang.startup.hanyangstartup.board.service.BoardService;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;;

@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private FileSaveService fileSaveService;

    @Value(value = "${spring.profiles.active}")
    private String PROFILE;

    @Value(value = "${config.uploadPath}")
    private String UPLOAD_PATH;

    @Value(value = "${server.servlet.context-path}")
    private String CONTEXT_PATH;

    @PostMapping("/content/img")
    public Map<String,String> contentImageUpload(@RequestParam(name = "upload") MultipartFile file, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {

            AttachFile attachFile = fileSaveService.fileSave(file, null, FILE_DIVISION.BOARD_CONTENT_IMG);
            String path = attachFile.getFilePath()+"/"+attachFile.getFileName()+attachFile.getFileExtension();
            String fileUrl = req.getRequestURL().substring(0, req.getRequestURL().indexOf(CONTEXT_PATH)+CONTEXT_PATH.length()) +"/resource"+path;
            Map<String,String> map = new HashMap<>();
            map.put("url", fileUrl);
            return map;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{boardName}")
    public ResponseEntity<Response> getBoard(@PathVariable("boardName") String boardName) {
        Response response;

        try {
            Map<String, Object> map = new HashMap<>();

            BoardConfig boardConfig = new BoardConfig();

            boardConfig.setBoardEnName(boardName);
            boardConfig = boardService.getBoard(boardConfig);
            map.put("board", boardConfig);

            if(boardConfig.getCategoryId() != null){
                List<BoardCategoryCode> boardCategoryCodeList = boardService.getBoardCategoryCodeList(boardConfig);
                map.put("cate", boardCategoryCodeList);
            }

            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/content/{contentId}")
    public ResponseEntity<Response> getBoardContent(@PathVariable("contentId") Integer contentId) {

        Response response;
        try {
            BoardContent boardContent = new BoardContent();

            boardContent.setContentId(contentId);

            Map<String,Object> map = boardService.getBoardContent(boardContent);
            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/content")
    public ResponseEntity<Response> getBoardContentList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "0") Integer pageSize, @RequestParam(value = "boardEnName", required = false) String boardEnName, @RequestParam(value = "boardCategory", required = false) Integer boardCategory, @RequestParam(value = "categoryCodeId", required = false) Integer categoryCodeId, @RequestParam(value = "searchValue", required = false) String searchValue, @RequestParam(value = "searchField", required = false) String searchField) {

        Response response;
        try {
            BoardConfig boardConfig = new BoardConfig();

            boardConfig.setPageNo(page);
            boardConfig.setPageSize(pageSize);
            boardConfig.setBoardEnName(boardEnName);
            boardConfig.setCategoryId(boardCategory);
            boardConfig.setCategoryCodeId(categoryCodeId);
            boardConfig.setSearchField(searchField);
            boardConfig.setSearchValue(searchValue);

            Map<String,Object> map = boardService.getBoardContentList(boardConfig);
            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/content")
    public ResponseEntity<Response> addBoardContent(BoardContent boardContent, Principal principal) {

        Response response;
        try {
            System.out.println("게시글 추가");
//            boardContent.setWriterId(principal.getName());
            boardContent.setWriterId("admin");
            boardService.addBoardContent(boardContent);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/reply")
    public ResponseEntity<Response> addReply(@RequestBody Reply reply, Principal principal) {

        Response response;
        try {
            System.out.println("댓글 추가");
//            boardContent.setWriterId(principal.getName());
            reply.setReplyWriter("admin");
            boardService.addReply(reply);
            List<Reply> replyList = boardService.getReplyList(reply);
            response = new Response("success", null, replyList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            List<Reply> replyList = boardService.getReplyList(reply);
            response = new Response("error", null, replyList, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/reply")
    public ResponseEntity<Response> updateReply(@RequestBody Reply reply, Principal principal) {

        Response response;
        try {
            System.out.println("댓글 추가");
//            boardContent.setWriterId(principal.getName());
            reply.setReplyWriter("admin");
            boardService.updateReply(reply);
            List<Reply> replyList = boardService.getReplyList(reply);
            response = new Response("success", null, replyList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            List<Reply> replyList = boardService.getReplyList(reply);
            response = new Response("error", null, replyList, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/reply/delete")
    public ResponseEntity<Response> deleteReply(@RequestBody Reply reply, Principal principal) {

        Response response;
        try {
//            boardContent.setWriterId(principal.getName());
            reply.setReplyWriter("admin");
            boardService.deleteReply(reply);
            List<Reply> replyList = boardService.getReplyList(reply);
            response = new Response("success", null, replyList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            List<Reply> replyList = boardService.getReplyList(reply);
            response = new Response("error", null, replyList, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

}
