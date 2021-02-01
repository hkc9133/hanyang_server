package com.hanyang.startup.hanyangstartup.popup.controller;

import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.popup.domain.Popup;
import com.hanyang.startup.hanyangstartup.popup.service.PopupService;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalPlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/popup")
public class AdminPopupController {

    @Autowired
    private PopupService popupService;

    //팝업 조회
    @GetMapping("/{popupId}")
    public ResponseEntity<Response> getPopup(@PathVariable("popupId") int popupId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            Popup popup = new Popup();
            popup.setPopupId(popupId);
            Popup result = popupService.getPopup(popup);

            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //팝업 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<Response> getPopupList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, @RequestParam(value = "searchValue", required = false) String searchValue, @RequestParam(value = "searchField", required = false) String searchField, @RequestParam(value="startDate", required = false) String startDate, @RequestParam(value="endDate", required = false) String endDate) {
        Response response;
        try {
            Popup popup = new Popup();
            popup.setPageNo(page);
            popup.setStartDate(startDate);
            popup.setEndDate(endDate);
            popup.setSearchField(searchField);
            popup.setSearchValue(searchValue);

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
    //팝업 생성
    @PostMapping
    public ResponseEntity<Response> addPopup(@RequestBody  Popup popup, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            popupService.addPopup(popup);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //팝업 수정
    @PostMapping("/edit")
    public ResponseEntity<Response> updatePopup(@RequestBody  Popup popup, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {

            popupService.updatePopup(popup);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //팝업 삭제
    @DeleteMapping("/{popupId}")
    public ResponseEntity<Response> deletePopup(@PathVariable("popupId") int popupId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            Popup popup = new Popup();
            popup.setPopupId(popupId);

            popupService.deletePopup(popup);

            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
}
