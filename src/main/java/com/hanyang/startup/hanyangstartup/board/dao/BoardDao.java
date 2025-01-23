package com.hanyang.startup.hanyangstartup.board.dao;

import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.board.domain.*;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class BoardDao {

    @Autowired
    protected SqlSessionTemplate sqlSession;

    public void createBoard(BoardConfig boardConfig){
        sqlSession.insert("board.createBoard",boardConfig);
    }
    public void updateBoard(BoardConfig boardConfig){
        sqlSession.update("board.updateBoard",boardConfig);
    }
    public BoardConfig getBoard(BoardConfig boardConfig){
        return sqlSession.selectOne("board.getBoard",boardConfig);
    }
    public List<BoardConfig> getBoardList(BoardConfig boardConfig){
        return sqlSession.selectList("board.getBoardList",boardConfig);
    }
    public int getBoardListCnt(BoardConfig boardConfig){
        return sqlSession.selectOne("board.getBoardListCnt",boardConfig);
    }

    public void createBoardCategory(BoardCategory boardCategory){
        sqlSession.insert("board.createBoardCategory",boardCategory);
    }
    public void updateBoardCategory(BoardCategory boardCategory){
        sqlSession.update("board.updateBoardCategory",boardCategory);
    }

    public BoardCategory getBoardCategory(BoardCategory boardCategory){
        return sqlSession.selectOne("board.getBoardCategory",boardCategory);
    }
    public List<BoardCategory> getBoardCategoryList(BoardConfig boardConfig){
        return sqlSession.selectList("board.getBoardCategoryList",boardConfig);
    }


    public void addBoardContent(BoardContent boardContent){
        sqlSession.insert("board.addBoardContent",boardContent);
    }
    public void updateBoardContent(BoardContent boardContent){
        sqlSession.update("board.updateBoardContent",boardContent);
    }
    public BoardContent getBoardContent(BoardContent boardContent){
        return sqlSession.selectOne("board.getBoardContent",boardContent);
    }

    public void updateBoardContentCnt(BoardContent boardContent){
        sqlSession.update("board.updateBoardContentCnt",boardContent);
    }

    public List<BoardContent> getBoardContentMain(){
        return sqlSession.selectList("board.getBoardContentMain");
    }
    public List<BoardContent> getBoardContentEnMain(){
        return sqlSession.selectList("board.getBoardContentEnMain");
    }
    public List<BoardContent> getBoardContentList(BoardConfig boardConfig){
        return sqlSession.selectList("board.getBoardContentList",boardConfig);
    }

    public BoardContent getBoardContentPrev(BoardContent boardContent){
        return sqlSession.selectOne("board.getBoardContentPrev",boardContent);
    }

    public BoardContent getBoardContentNext(BoardContent boardContent){
        return sqlSession.selectOne("board.getBoardContentNext",boardContent);
    }

    public int getBoardContentListCnt(BoardConfig boardConfig){
        return sqlSession.selectOne("board.getBoardContentListCnt",boardConfig);
    }

    public void deleteBoardContent(BoardContent boardContent){
        sqlSession.update("board.deleteBoardContent",boardContent);
    }


    public List<BoardCategoryCode> getBoardCategoryCodeList(BoardConfig boardConfig){
        return sqlSession.selectList("board.getBoardCategoryCodeList",boardConfig);
    }

    public void addReply(Reply reply){
        sqlSession.insert("board.addReply",reply);
    }

    public List<Reply> getReplyList(Reply reply){
        return sqlSession.selectList("board.getReplyList",reply);
    }

    public void updateReply(Reply reply){
        sqlSession.update("board.updateReply",reply);
    }

    public void deleteReply(Reply reply){
        sqlSession.update("board.deleteReply",reply);
    }

}
