package com.hanyang.startup.hanyangstartup.board.controller;

import com.hanyang.startup.hanyangstartup.auth.domain.RequestSocialData;
import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.board.domain.BoardCategory;
import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.board.service.BoardService;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.util.JwtUtil;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/board")
public class AdminBoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping
    public Response createBoard(@RequestBody BoardConfig boardConfig, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            boardService.createBoard(boardConfig);
            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    @GetMapping("/{boardName}")
    public Response getBoard(@PathVariable("boardName") String boardName){
        Response response;

        try {
            System.out.println("게시판 조회");

            BoardConfig boardConfig = new BoardConfig();

            boardConfig.setBoardEnName(boardName);
            boardConfig = boardService.getBoard(boardConfig);
            response = new Response("success", null, boardConfig, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    @GetMapping
    public Response getBoardList(){
        Response response;
        try {
            List<BoardConfig> boardConfigList = boardService.getBoardList();
            response = new Response("success", null, boardConfigList, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    @PostMapping("/{boardName}/category")
    public Response createBoardCategory(@PathVariable("boardName") String boardName, @RequestBody BoardCategory boardCategory, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            boardCategory.setBoardEnName(boardName);
            boardService.createBoardCategory(boardCategory);
            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    //카테고리 조회
    @GetMapping(value="/{boardName}/category/{categoryId}")
    public Response getBoardCategory(@PathVariable("boardName") String boardName,@PathVariable(value="categoryId") Integer categoryId){
        Response response;
        try {
            System.out.println("카테고리 조회");

            BoardCategory boardCategory = new BoardCategory();
            boardCategory.setBoardEnName(boardName);
            boardCategory.setCategoryId(categoryId);
            boardCategory = boardService.getBoardCategory(boardCategory);

            response = new Response("success", null, boardCategory, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    //카테고리 리스트 조회
    @GetMapping(value="/{boardName}/category")
    public Response getBoardCategoryList(@PathVariable("boardName") String boardName){
        System.out.println("카테고리 리스트 조회");
        Response response;
        try {
            BoardCategory boardCategory = new BoardCategory();
            boardCategory.setBoardEnName(boardName);
            List<BoardCategory> boardCategoryList = boardService.getBoardCategoryList(boardCategory);
            response = new Response("success", null, boardCategoryList, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

}
