package com.hanyang.startup.hanyangstartup.auth.controller;

import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.auth.domain.UserType;
import com.hanyang.startup.hanyangstartup.auth.service.UserService;
import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Response> getUserList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "searchValue", required = false) String searchValue, @RequestParam(value = "searchField", required = false) String searchField,@RequestParam(value = "type", required = false) UserType type) {
        Response response;
        try {
            User user = new User();

            user.setPageNo(page);
            user.setType(type);
            user.setSearchField(searchField);
            user.setSearchValue(searchValue);

            Map<String,Object> map = userService.getUserList(user);

            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = "/{userId}")
    public ResponseEntity<Response> getUser(@PathVariable("userId") String userId) {
        Response response;
        try {
            User user = new User();

            user.setUserId(userId);

            Map<String,Object> map = userService.getUser(user);

            response = new Response("success", null, map, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<Response> updateUser(@RequestBody User user) {
        Response response;
        try {


            userService.updateUser(user);

            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response("error", null, e.getMessage(), 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
}
