package com.hanyang.startup.hanyangstartup.board.controller;

import com.hanyang.startup.hanyangstartup.auth.domain.RequestSocialData;
import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.board.domain.*;
import com.hanyang.startup.hanyangstartup.board.service.BoardService;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.util.JwtUtil;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/board")
public class AdminBoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping
    public Response createBoard(@RequestBody BoardConfig boardConfig, HttpServletRequest req, HttpServletResponse res) {
        Response response;
        try {
            boardService.createBoard(boardConfig);
            response = new Response("success", null, null, 200);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
        }
        return response;
    }

    @GetMapping("/{boardName}")
    public ResponseEntity<Response> getBoard(@PathVariable("boardName") String boardName) {
        Response response;

        try {
            Map<String, Object> map = new HashMap<>();

            BoardConfig boardConfig = new BoardConfig();

            boardConfig.setBoardEnName(boardName);
            boardConfig = boardService.getBoard(boardConfig);
            List<BoardCategory> boardCategoryList = boardService.getBoardCategoryList(new BoardConfig());
            List<BoardCategoryCode> boardCategoryCodeList = boardService.getBoardCategoryCodeList(boardConfig);

            map.put("board", boardConfig);
            map.put("category", boardCategoryList);
            map.put("categoryCode", boardCategoryCodeList);

            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{boardName}")
    public ResponseEntity<Response> updateBoard(@PathVariable("boardName") String boardName, @RequestBody BoardConfig boardConfig) {
        Response response;

        try {
            boardService.updateBoard(boardConfig);

            response = new Response("success", "수정 완료", null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", "수정 중 에러 발생", e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Response> getBoardList(@RequestParam(value = "page", defaultValue = "1") Integer page,@RequestParam(value = "searchValue", required = false) String searchValue, @RequestParam(value = "searchField", required = false) String searchField) {
        Response response;
        try {
            BoardConfig boardConfig = new BoardConfig();
            boardConfig.setPageNo(page);
            boardConfig.setSearchValue(searchValue);
            boardConfig.setSearchField(searchField);

            Map<String,Object> map = boardService.getBoardList(boardConfig);

            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/board_info")
    public ResponseEntity<Response> getBoardInfoAll() {
        Response response;

        try {

            BoardConfig boardConfig = new BoardConfig();
            boardConfig.setPageSize(100);
            boardConfig.setPageNo(1);
            Map<String,Object> map = boardService.getBoardList(boardConfig);
            List<BoardCategory> boardCategoryList = boardService.getBoardCategoryList(boardConfig);
            List<BoardCategoryCode> boardCategoryCodeList = boardService.getBoardCategoryCodeList(boardConfig);

            map.put("boardList", map.get("list"));
            map.put("categoryList", boardCategoryList);
            map.put("categoryCodeList", boardCategoryCodeList);

            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/{boardName}/category")
    public Response createBoardCategory(@PathVariable("boardName") String boardName, @RequestBody BoardCategory boardCategory, HttpServletRequest req, HttpServletResponse res) {
        Response response;
        try {
//            boardCategory.setBoardEnName(boardName);
            boardService.createBoardCategory(boardCategory);
            response = new Response("success", null, null, 200);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
        }
        return response;
    }

    //카테고리 조회
    @GetMapping(value = "/{boardName}/category/{categoryId}")
    public Response getBoardCategory(@PathVariable("boardName") String boardName, @PathVariable(value = "categoryId") Integer categoryId) {
        Response response;
        try {
            System.out.println("카테고리 조회");

            BoardCategory boardCategory = new BoardCategory();
//            boardCategory.setBoardEnName(boardName);
            boardCategory.setCategoryId(categoryId);
            boardCategory = boardService.getBoardCategory(boardCategory);

            response = new Response("success", null, boardCategory, 200);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
        }
        return response;
    }

    //카테고리 리스트 조회
    @GetMapping(value = "/{boardName}/category")
    public Response getBoardCategoryList(@PathVariable("boardName") String boardName) {
        System.out.println("카테고리 리스트 조회");
        Response response;
        try {
            BoardCategory boardCategory = new BoardCategory();
//            boardCategory.setBoardEnName(boardName);
//            List<BoardCategory> boardCategoryList = boardService.getBoardCategoryList(boardCategory);
            response = new Response("success", null, null, 200);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
        }
        return response;
    }

    @GetMapping(value = "/content")
    public ResponseEntity<Response> getBoardContentList(@RequestParam(value = "page", defaultValue = "1") Integer page,@RequestParam(value = "pageSize",defaultValue = "0") Integer pageSize, @RequestParam(value = "boardEnName", required = false) String boardEnName, @RequestParam(value = "boardCategory", required = false) Integer boardCategory, @RequestParam(value = "categoryCodeId", required = false) Integer categoryCodeId, @RequestParam(value = "searchValue", required = false) String searchValue, @RequestParam(value = "searchField", required = false) String searchField) {

        System.out.println("콘텐츠 리스트");
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
//        return response;
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

    @PostMapping(value = "/content")
    public ResponseEntity<Response> addBoardContent(BoardContent boardContent, Principal principal) {

        Response response;
        try {
            System.out.println("게시글 추가");
            boardContent.setWriterId(principal.getName());
            boardService.addBoardContent(boardContent);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/content/edit")
    public ResponseEntity<Response> updateBoardContent(BoardContent boardContent, Principal principal) {

        Response response;
        try {
            System.out.println("게시글 수정");
//            boardContent.setWriterId(principal.getName());
            boardContent.setWriterId(principal.getName());
            boardService.updateBoardContent(boardContent);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/content/delete/{contentId}")
    public ResponseEntity<Response> deleteBoardContent(@PathVariable("contentId") Integer contentId,Principal principal) {

        Response response;
        try {
            System.out.println("게시글 삭제");
            BoardContent boardContent = new BoardContent();
            boardContent.setContentId(contentId);
            boardService.deleteBoardContent(boardContent);
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
            reply.setReplyWriter(principal.getName());
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
            System.out.println("댓글 수정");
//            boardContent.setWriterId(principal.getName());
            reply.setReplyWriter(principal.getName());
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
            reply.setReplyWriter(principal.getName());
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
