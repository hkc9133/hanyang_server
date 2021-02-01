package com.hanyang.startup.hanyangstartup.popup.controller;

import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.popup.domain.Popup;
import com.hanyang.startup.hanyangstartup.popup.service.PopupService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/popup")
public class PopupController {

    @Autowired
    private PopupService popupService;


    //팝업 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<Response> getPopupList() {
        Response response;
        try {

            Popup popup = new Popup();
            popup.setToday(LocalDateTime.now());
            Map<String, Object>  result = popupService.getPopupList(popup);

            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
}
