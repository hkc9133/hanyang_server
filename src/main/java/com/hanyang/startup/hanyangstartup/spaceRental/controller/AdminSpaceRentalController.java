package com.hanyang.startup.hanyangstartup.spaceRental.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.*;
import com.hanyang.startup.hanyangstartup.spaceRental.service.SpaceRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/space_rental")
public class AdminSpaceRentalController {

    @Autowired


    private SpaceRentalService spaceRentalService;


    @GetMapping("/status_count")
    public ResponseEntity<Response> getStatusCount(HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {

            RentalPlace rentalPlace = new RentalPlace();
            List<StatusCount> rentalPlaceList =  spaceRentalService.getStatusCount();

            response = new Response("success", null, rentalPlaceList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //공간, 룸, 시간까지 모두 조회
    @GetMapping("/all")
    public ResponseEntity<Response> getPlaceInfoAll(HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {

            RentalPlace rentalPlace = new RentalPlace();
            List<RentalPlace> rentalPlaceList =  spaceRentalService.getPlaceInfoAll(rentalPlace);

            response = new Response("success", null, rentalPlaceList, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //공간 조회
    @GetMapping("/place/{placeId}")
    public ResponseEntity<Response> getPlace(@PathVariable("placeId") int placeId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            RentalPlace rentalPlace = new RentalPlace();
            rentalPlace.setPlaceId(placeId);
            RentalPlace result = spaceRentalService.getPlace(rentalPlace);

            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    //공간 생성
    @PostMapping("/place")
    public ResponseEntity<Response> createPlace(@ModelAttribute  RentalPlace rentalPlace, BindingResult bindingResult, HttpServletRequest req, HttpServletResponse res){
        Response response;
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(v ->{
                System.out.println(v.toString());
            });
        }
        try {
            spaceRentalService.addPlace(rentalPlace);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //공간 수정
    @PostMapping("/place/edit")
    public ResponseEntity<Response> updatePlace(@ModelAttribute RentalPlace rentalPlace, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {

            System.out.println("=====place 업데이트");
            System.out.println(rentalPlace);

            spaceRentalService.updatePlace(rentalPlace);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //공간 삭제
    @DeleteMapping("/place/{placeId}")
    public ResponseEntity<Response> deletePlace(@PathVariable("placeId") int placeId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            RentalPlace rentalPlace = new RentalPlace();
            rentalPlace.setPlaceId(placeId);

            spaceRentalService.deletePlace(rentalPlace);

            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //룸 조회
    @GetMapping("/room/{roomId}")
    public ResponseEntity<Response> getRoom(@PathVariable("roomId") int roomId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            RentalRoom rentalRoom = new RentalRoom();
            rentalRoom.setRoomId(roomId);
            RentalRoom result = spaceRentalService.getRoom(rentalRoom);
            response = new Response("success", null, result, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //룸 생성
    @PostMapping("/room")
    public ResponseEntity<Response> createRoom(@ModelAttribute RentalRoom rentalRoom, HttpServletRequest req, HttpServletResponse res){
        Response response;

        try {

            System.out.println("룸 생성");
            System.out.println(rentalRoom);

            //multipart/form-data에서 json array로 받기 어려워서 string으로 받음
            if(rentalRoom.getAddRentalRoomTimeListStr() != null){
                List<RentalRoomTime> addRentalRoomTimeList = new ArrayList<>();
                for (String str: rentalRoom.getAddRentalRoomTimeListStr()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    RentalRoomTime rentalRoomTime = objectMapper.readValue(str, RentalRoomTime.class);
                    if(rentalRoomTime != null){
                        addRentalRoomTimeList.add(rentalRoomTime);
                    }
                }
                rentalRoom.setAddRentalRoomTimeList(addRentalRoomTimeList);
            }
            spaceRentalService.createRoom(rentalRoom);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //룸 수정
    @PostMapping("/room/edit")
    public ResponseEntity<Response> updateRoom(@ModelAttribute RentalRoom rentalRoom, HttpServletRequest req, HttpServletResponse res){
        Response response;

        System.out.println("룸 수정");
        System.out.println(rentalRoom);
        try {

            List<RentalRoomTime> rentalRoomTimeList = new ArrayList<>();
            //multipart/form-data에서 json array로 받기 어려워서 string으로 받음
            for (String str: rentalRoom.getRentalRoomTimeListStr()) {
                ObjectMapper objectMapper = new ObjectMapper();
                RentalRoomTime rentalRoomTime = objectMapper.readValue(str, RentalRoomTime.class);
                if(rentalRoomTime != null){
                    rentalRoomTimeList.add(rentalRoomTime);
                }
            }
            rentalRoom.setRentalRoomTimeList(rentalRoomTimeList);

            List<RentalRoomTime> addRentalRoomTimeList = new ArrayList<>();
            if(rentalRoom.getAddRentalRoomTimeListStr() != null){
                for (String str: rentalRoom.getAddRentalRoomTimeListStr()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    RentalRoomTime rentalRoomTime = objectMapper.readValue(str, RentalRoomTime.class);
                    if(rentalRoomTime != null){
                        addRentalRoomTimeList.add(rentalRoomTime);
                    }
                }
                rentalRoom.setAddRentalRoomTimeList(addRentalRoomTimeList);
            }
            spaceRentalService.updateRoom(rentalRoom);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //룸 삭제
    @DeleteMapping("/room/{roomId}")
    public Response deleteRoom(@PathVariable("roomId") int roomId,  HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            RentalRoom rentalRoom = new RentalRoom();
            rentalRoom.setRoomId(roomId);
            spaceRentalService.deleteRoom(rentalRoom);
            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    //룸 시간 생성
    @PostMapping("/place/room/{roomId}/time")
    public Response createRoomTime(@PathVariable("roomId") int roomId, @RequestBody RentalRoomTime rentalRoomTime, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            rentalRoomTime.setRoomId(roomId);
            spaceRentalService.createRoomTime(rentalRoomTime);
            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    //룸 시간 수정
    @PutMapping("/place/room/{roomId}/time/{timeId}")
    public ResponseEntity<Response> updateRoomTime(@PathVariable("roomId") int roomId, @RequestBody RentalRoomTime rentalRoomTime, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            rentalRoomTime.setRoomId(roomId);
//            spaceRentalService.updateRoomTime(rentalRoomTime);
            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response = new Response("fail", null, null, 400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    //룸 시간 삭제
    @DeleteMapping("/place/room/{roomId}/time/{timeId}")
    public Response deleteRoomTime(@PathVariable("roomId") int roomId,@PathVariable("timeId") int timeId,  HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            RentalRoomTime rentalRoomTime = new RentalRoomTime();
            rentalRoomTime.setRoomId(roomId);
            rentalRoomTime.setTimeId(timeId);
            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

//    스케쥴

    @GetMapping("/schedule")
    public Response getRentalScheduleList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "status",required = false) RENTAL_STATUS status, @RequestParam(value = "roomId",required = false) Integer roomId,
                                          @RequestParam(value = "regStartDate",required = false) String regStartDate,
                                          @RequestParam(value = "regEndDate",required = false) String regEndDate,
                                          @RequestParam(value = "rentalStartDate",required = false) String rentalStartDate,
                                          @RequestParam(value = "rentalEndDate",required = false) String rentalEndDate,
                                          @RequestParam(value = "date", required = false) String date,
                                          Principal principal, HttpServletRequest req, HttpServletResponse res){
        Response response;


        try {
            RentalSchedule rentalSchedule = new RentalSchedule();
            rentalSchedule.setPageNo(page);
            rentalSchedule.setRoomId(roomId);
            rentalSchedule.setStatus(status);
            rentalSchedule.setRegStartDate(regStartDate);
            rentalSchedule.setRegEndDate(regEndDate);
            rentalSchedule.setRentalStartDate(rentalStartDate);
            rentalSchedule.setRentalEndDate(rentalEndDate);
            rentalSchedule.setDate(date);

            Map<String, Object> map =  spaceRentalService.getRentalScheduleList(rentalSchedule);
            response = new Response("success", null, map, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }
    @PostMapping("/schedule/update/status")
    public ResponseEntity<Response>  updateRentalSchedule(@RequestBody RentalSchedule rentalSchedule, Principal principal, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            spaceRentalService.updateRentalSchedule(rentalSchedule);

            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

}
