package com.hanyang.startup.hanyangstartup.board.service;

import com.hanyang.startup.hanyangstartup.board.dao.BoardDao;
import com.hanyang.startup.hanyangstartup.board.domain.*;
import com.hanyang.startup.hanyangstartup.keyword.domain.Keyword;
import com.hanyang.startup.hanyangstartup.keyword.service.KeywordService;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_STATUS;
import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BoardService {
    @Autowired
    private BoardDao boardDao;
    @Autowired
    private FileSaveService fileSaveService;
    @Autowired
    private KeywordService keywordService;

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
    public Map<String, Object> getBoardList(BoardConfig boardConfig) {
//        boardConfig.setPageSize(10);
        boardConfig.setTotalCount(boardDao.getBoardListCnt(boardConfig));

        Map<String, Object> map = new HashMap<>();

        map.put("page", boardConfig);
        map.put("list", boardDao.getBoardList(boardConfig));

        return map;
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
    public List<BoardCategory> getBoardCategoryList(BoardConfig boardConfig) {
        return boardDao.getBoardCategoryList(boardConfig);
    }


    //컨텐츠 생성
    @Transactional(rollbackFor = {Exception.class})
    public void addBoardContent(BoardContent boardContent) {
        boardDao.addBoardContent(boardContent);

        if(boardContent.getFiles() != null){
            for (MultipartFile file : boardContent.getFiles()) {
                fileSaveService.fileSave(file, boardContent.getContentId(), FILE_DIVISION.BOARD_ATTACH);
            }
        }
        if(boardContent.getThumb() != null){
            for (MultipartFile file : boardContent.getThumb()) {
                fileSaveService.fileSave(file, boardContent.getContentId(), FILE_DIVISION.BOARD_THUMB_IMG);
            }
        }
    }

    //컨텐츠 수정
    @Transactional(rollbackFor = {Exception.class})
    public void updateBoardContent(BoardContent boardContent) {
        List<Integer> removeFiles = boardContent.getRemoveFiles();
        if(removeFiles != null){
            List<AttachFile> attachFileList = new ArrayList<>();
            for (Integer file : removeFiles) {
                AttachFile attachFile = new AttachFile();
                attachFile.setFileId(file.intValue());
                attachFileList.add(attachFile);
            }
            fileSaveService.deleteAttachFile(attachFileList);
        }
        if(boardContent.getFiles() != null){
            for (MultipartFile file : boardContent.getFiles()) {
                fileSaveService.fileSave(file, boardContent.getContentId(), FILE_DIVISION.BOARD_ATTACH);
            }
        }
        if(boardContent.getThumb() != null){
            for (MultipartFile file : boardContent.getThumb()) {
                fileSaveService.fileSave(file, boardContent.getContentId(), FILE_DIVISION.BOARD_THUMB_IMG);
            }
        }
        boardDao.updateBoardContent(boardContent);
    }

    //컨텐츠 조회
    public Map<String, Object> getBoardContent(BoardContent boardContent) {
        //조회수 업데이트
        boardDao.updateBoardContentCnt(boardContent);

        AttachFile attachFile = new AttachFile();
        attachFile.setContentId(boardContent.getContentId());
        attachFile.setDivision(FILE_DIVISION.BOARD_ATTACH);
        attachFile.setStatus(FILE_STATUS.A);

        AttachFile thumb = new AttachFile();
        thumb.setContentId(boardContent.getContentId());
        thumb.setDivision(FILE_DIVISION.BOARD_THUMB_IMG);
        thumb.setStatus(FILE_STATUS.A);

        Map<String, Object> map = new HashMap<>();
        BoardContent resultContent = boardDao.getBoardContent(boardContent);

        Reply reply = new Reply();
        reply.setContentId(resultContent.getContentId());

        List<Reply> replyList = boardDao.getReplyList(reply);
        map.put("content", resultContent);
        map.put("reply", replyList);
        map.put("prev", boardDao.getBoardContentPrev(resultContent));
        map.put("next", boardDao.getBoardContentNext(resultContent));
        map.put("files", fileSaveService.getAttachFileList(attachFile));
        map.put("thumb", fileSaveService.getAttachFileList(thumb));
        return map;
    }

    //컨텐츠 리스트
    public Map<String, Object> getBoardContentList(BoardConfig boardConfig) {
        boardConfig.setTotalCount(boardDao.getBoardContentListCnt(boardConfig));

        Map<String, Object> map = new HashMap<>();
        List<BoardContent> boardContentList = boardDao.getBoardContentList(boardConfig);

        boardContentList.stream().map(boardContent -> {

            Reply reply = new Reply();
            reply.setContentId(boardContent.getContentId());

            List<Reply> replyList = boardDao.getReplyList(reply);
            boardContent.setReplyCount(replyList.size());

            AttachFile attachFile = new AttachFile();
            attachFile.setContentId(boardContent.getContentId());
            attachFile.setDivision(FILE_DIVISION.BOARD_ATTACH);
            attachFile.setStatus(FILE_STATUS.A);

            AttachFile thumb = new AttachFile();
            thumb.setContentId(boardContent.getContentId());
            thumb.setDivision(FILE_DIVISION.BOARD_THUMB_IMG);
            thumb.setStatus(FILE_STATUS.A);

            boardContent.setAttachFileList(fileSaveService.getAttachFileList(attachFile));
            boardContent.setThumbList(fileSaveService.getAttachFileList(thumb));
            return boardContent;
        }).collect(Collectors.toList());

        map.put("page", boardConfig);
        map.put("list", boardContentList);
        map.put("cate", boardDao.getBoardCategoryCodeList(boardConfig));

        return map;
    }

    public Map<String, Object> getBoardContentSearch(BoardConfig boardConfig) {
        if(boardConfig.getSearchValue() != null && !boardConfig.getSearchValue().equals("")){
            Keyword keyword = new Keyword();
            keyword.setKeyword(boardConfig.getSearchValue());
            keywordService.updateKeywordSearch(keyword);
        }
        boardConfig.setTotalCount(boardDao.getBoardContentListCnt(boardConfig));

        Map<String, Object> map = new HashMap<>();
        List<BoardContent> boardContentList = boardDao.getBoardContentList(boardConfig);
//
//        boardContentList.stream().map(boardContent -> {
//            AttachFile attachFile = new AttachFile();
//            attachFile.setContentId(boardContent.getContentId());
//            attachFile.setDivision(FILE_DIVISION.BOARD_ATTACH);
//            attachFile.setStatus(FILE_STATUS.A);
//
//            boardContent.setAttachFileList(fileSaveService.getAttachFileList(attachFile));
//            return boardContent;
//        }).collect(Collectors.toList());

        BoardConfig cateBoardConfig = new BoardConfig();
        cateBoardConfig.setPageSize(100);
        cateBoardConfig.setPageNo(1);
        map.put("boardList", this.getBoardList(cateBoardConfig).get("list"));
        map.put("page", boardConfig);
        map.put("list", boardContentList);
        map.put("cate", boardDao.getBoardCategoryCodeList(boardConfig));

        return map;
    }

    public void deleteBoardContent(BoardContent boardContent) {
        boardDao.deleteBoardContent(boardContent);
    }


    public List<BoardCategoryCode> getBoardCategoryCodeList(BoardConfig boardConfig) {
        return boardDao.getBoardCategoryCodeList(boardConfig);
    }

    public void addReply(Reply reply) {
        boardDao.addReply(reply);
    }

    public List<Reply> getReplyList(Reply reply) {
        return boardDao.getReplyList(reply);
    }

    public void updateReply(Reply reply) {
        boardDao.updateReply(reply);
    }

    public void deleteReply(Reply reply) {
        boardDao.deleteReply(reply);
    }
}
