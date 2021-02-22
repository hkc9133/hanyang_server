package com.hanyang.startup.hanyangstartup.partner.controller;

import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.partner.domain.ContinentCode;
import com.hanyang.startup.hanyangstartup.partner.domain.Partner;
import com.hanyang.startup.hanyangstartup.partner.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/partner")
public class AdminPartnerController {

    @Autowired
    private PartnerService partnerService;

    @GetMapping("/continent")
    public ResponseEntity<Response> getContinentList(){
        Response response;
        try {
            List<ContinentCode> continentCodeList = partnerService.getContinentList();

            response = new Response("success", null, continentCodeList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/{partnerId}")
    public ResponseEntity<Response> getPartner(@PathVariable("partnerId") int partnerId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            Partner Partner = new Partner();
            Partner.setPartnerId(partnerId);
            Partner result = partnerService.getPartner(Partner);

            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/list")
    public ResponseEntity<Response> getPartnerList(@RequestBody Partner Partner) {
        Response response;
        try {

            Map<String, Object>  result = partnerService.getPartnerList(Partner);

            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Response> addPartner(Partner Partner, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {

            System.out.println("스타트업 추가");
            System.out.println(Partner);
            partnerService.addPartner(Partner);
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
    public ResponseEntity<Response> updatePartner(Partner Partner, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {

            System.out.println("파트너 업데이트");
            System.out.println(Partner);
            partnerService.updatePartner(Partner);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{partnerId}")
    public ResponseEntity<Response> deletePartner(@PathVariable("partnerId") int partnerId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            Partner Partner = new Partner();
            Partner.setPartnerId(partnerId);

            partnerService.deletePartner(Partner);

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
