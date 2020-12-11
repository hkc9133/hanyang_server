package com.hanyang.startup.hanyangstartup.board.dao;

import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.board.domain.BoardCategory;
import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.board.domain.BoardContent;
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
    public List<BoardConfig> getBoardList(){
        return sqlSession.selectList("board.getBoardList");
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
    public List<BoardCategory> getBoardCategoryList(BoardCategory boardCategory){
        return sqlSession.selectList("board.getBoardCategoryList",boardCategory);
    }


    public void createBoardContent(BoardContent boardContent){
        sqlSession.insert("board.createBoardContent",boardContent);
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

    public List<BoardCategory> getBoardContentList(BoardContent boardContent){
        return sqlSession.selectList("board.getBoardContentList",boardContent);
    }

}
