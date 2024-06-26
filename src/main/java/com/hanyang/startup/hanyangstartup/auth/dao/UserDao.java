package com.hanyang.startup.hanyangstartup.auth.dao;

import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {
    @Autowired
    protected SqlSessionTemplate sqlSession;

    public User getUser(User user){
        return sqlSession.selectOne("user.getUser",user);
    }
    public void updateUser(User user){
        sqlSession.update("user.updateUser",user);
    }

    public List<User> getUserList(User user){
        return sqlSession.selectList("user.getUserList",user);
    }
    public int getUserListCnt(User user){
        return sqlSession.selectOne("user.getUserListCnt",user);
    }

}
