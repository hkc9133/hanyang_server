package com.hanyang.startup.hanyangstartup.auth.dao;

import com.hanyang.startup.hanyangstartup.auth.domain.SocialData;
import com.hanyang.startup.hanyangstartup.auth.domain.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class AuthDao {

    @Autowired
    protected SqlSessionTemplate sqlSession;

    public User findByUserId(String userEmail){
        return sqlSession.selectOne("auth.findByUserId",userEmail);
    }

    public User findByIdAndType(HashMap<String,Object> map ){
        return sqlSession.selectOne("auth.findByIdAndType",map);
    }
    public void signUpSocialUser(User user){
        sqlSession.insert("auth.signUpSocialUser",user);
    }

    public void updateLastLogin(User user){
        sqlSession.update("auth.updateLastLogin",user);
    }

}
