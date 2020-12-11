package com.hanyang.startup.hanyangstartup.board.service;

import com.hanyang.startup.hanyangstartup.board.dao.BoardDao;
import com.hanyang.startup.hanyangstartup.board.domain.BoardCategory;
import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.board.domain.BoardContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardDao boardDao;

    //게시판 생성
    public void createBoard(BoardConfig boardConfig) {
        boardDao.createBoard(boardConfig);
    }

    //게시판 수정
    public void updateBoard(BoardConfig boardConfig) {
        boardDao.updateBoard(boardConfig);

    }

    //게시판 조회
    public BoardConfig getBoard(BoardConfig boardConfig) {
        return boardDao.getBoard(boardConfig);
    }

    //게시판 리스트
    public List<BoardConfig> getBoardList() {
        return boardDao.getBoardList();
    }


    //카테고리 생성
    public void createBoardCategory(BoardCategory boardCategory) {
        boardDao.createBoardCategory(boardCategory);
    }

    //카테고리 수정
    public void updateBoardCategory(BoardCategory boardCategory) {
        boardDao.updateBoardCategory(boardCategory);
    }

    //카테고리 조회
    public BoardCategory getBoardCategory(BoardCategory boardCategory) {
        return boardDao.getBoardCategory(boardCategory);
    }

    //카테고리 리스트
    public List<BoardCategory> getBoardCategoryList(BoardCategory boardCategory) {
        return boardDao.getBoardCategoryList(boardCategory);
    }


    //컨텐츠 생성
    public void createBoardContent(BoardContent boardContent) {
        boardDao.createBoardContent(boardContent);
    }

    //컨텐츠 수정
    public void updateBoardContent(BoardContent boardContent) {
        boardDao.updateBoardContent(boardContent);
    }

    //컨텐츠 조회
    public BoardContent getBoardContent(BoardContent boardContent) {
        //조회수 업데이트
        boardDao.updateBoardContentCnt(boardContent);
        return boardDao.getBoardContent(boardContent);
    }

    //컨텐츠 리스트
    public List<BoardCategory> getBoardContentList(BoardContent boardContent) {
        return boardDao.getBoardContentList(boardContent);
    }
}
