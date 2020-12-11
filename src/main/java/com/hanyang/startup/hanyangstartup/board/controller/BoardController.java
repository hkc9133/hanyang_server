package com.hanyang.startup.hanyangstartup.board.controller;

import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.board.service.BoardService;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;;

@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Value(value = "${spring.profiles.active}")
    private String PROFILE;

    @Value(value = "${config.uploadPath}")
    private String UPLOAD_PATH;

    @Value(value = "${server.servlet.context-path}")
    private String CONTEXT_PATH;

    @PostMapping("/content/img")
    public Map<String,String> contentImageUpload(@RequestParam(name = "upload") MultipartFile file, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            String fileName = file.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf("."));

            UUID uuid = UUID.randomUUID();

            String path = UPLOAD_PATH+"/CONTENT_IMG"+"/"+uuid.toString()+extension;


            file.transferTo(new File(path));
            String fileUrl = req.getRequestURL().substring(0, req.getRequestURL().indexOf(CONTEXT_PATH)+CONTEXT_PATH.length()) +"/resource/CONTENT_IMG"+"/"+uuid.toString()+extension;
            Map<String,String> map = new HashMap<>();
            map.put("url", fileUrl);
            System.out.println(map);
            return map;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
