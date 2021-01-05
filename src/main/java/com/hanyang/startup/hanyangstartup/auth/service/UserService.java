package com.hanyang.startup.hanyangstartup.auth.service;

import com.hanyang.startup.hanyangstartup.auth.dao.UserDao;
import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserDao userdao;

    //컨텐츠 리스트
    public Map<String,Object> getUserList(User user) {
        user.setPageSize(10);
        user.setTotalCount(userdao.getUserListCnt(user));

        Map<String,Object> map = new HashMap<>();

        map.put("page",user);
        map.put("list",userdao.getUserList(user));

        return map;
    }
}
