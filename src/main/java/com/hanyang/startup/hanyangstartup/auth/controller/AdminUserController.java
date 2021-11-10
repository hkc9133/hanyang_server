package com.hanyang.startup.hanyangstartup.auth.controller;

import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.auth.domain.UserType;
import com.hanyang.startup.hanyangstartup.auth.service.UserService;
import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.Principal;
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

    @GetMapping("/excel_download")
    public void excelDownloadAll(Principal principal, HttpServletRequest req, HttpServletResponse res) throws IOException {

        OutputStream out = null;

        try {
            HSSFWorkbook workbook = userService.excelDownloadAll();


            res.reset();
            String userAgent = req.getHeader("User-Agent");

            String fileName = "사용자 현황.xls";
            boolean ie = (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1);

            if(ie) {
                fileName = URLEncoder.encode( "fileName", "utf-8" ).replaceAll("\\+", "%20");
            } else {
                fileName = new String( String.valueOf(fileName).getBytes("utf-8"), "iso-8859-1");
            }

            res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
            res.setHeader("Content-Transfer-Encoding", "binary");

//            res.setHeader("Content-Disposition", "attachment;filename=단원리스트.xls");
            res.setContentType("application/vnd.ms-excel;");
            out = new BufferedOutputStream(res.getOutputStream());

            workbook.write(out);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(out != null) out.close();
        }
    }
}
