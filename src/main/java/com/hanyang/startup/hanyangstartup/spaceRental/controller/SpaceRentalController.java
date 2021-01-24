package com.hanyang.startup.hanyangstartup.spaceRental.controller;

import com.hanyang.startup.hanyangstartup.auth.domain.User;
import com.hanyang.startup.hanyangstartup.auth.service.AuthService;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.common.exception.CustomException;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalRoom;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalRoomTime;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalSchedule;
import com.hanyang.startup.hanyangstartup.spaceRental.service.SpaceRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/space_rental")
public class SpaceRentalController {

    @Autowired
    private SpaceRentalService spaceRentalService;
    @Autowired
    private AuthService authService;


    //공간 예약 관련 모든 정보 조회
    @GetMapping
    public Response getSpaceRentalInfoAll(HttpServletRequest req, HttpServletResponse res,Principal principal){
        Response response;
        try {

            User user = authService.findByUserId(principal.getName());
            RentalRoom rentalRoom = new RentalRoom();
            rentalRoom.setRentalRole(user.getRole().toString());
            Map<String, Object> map = spaceRentalService.getSpaceRentalInfoList(rentalRoom);

            response = new Response("success", null, map, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }


    //공간 조회
    @GetMapping("/place/{placeId}")
    public Response getPlace(@PathVariable("placeId") int placeId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    //공간 리스트 조회
    @GetMapping("/place")
    public Response getPlaceList(HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    //룸 조회
    @GetMapping("/place/{placeId}/room/{roomId}")
    public Response getRoom(@PathVariable("placeId") int placeId,@PathVariable("roomId") int roomId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    //룸 리스트 조회
    @GetMapping("/place/{placeId}/room")
    public Response getRoomList(@PathVariable("placeId") int placeId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    //룸 시간 리스트 조회
//    @GetMapping("/place/{placeId}/room/{roomId}/time")
    @GetMapping("/place/room/{roomId}/time")
    public Response getRoomTimeList(@PathVariable("roomId") int roomId, @RequestParam("date") String date, @RequestParam("type") String type, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            RentalRoomTime rentalRoomTime = new RentalRoomTime();
            rentalRoomTime.setRoomId(roomId);

            List<RentalRoomTime> rentalRoomTimeList = null;

            if(type.equals("available")){
                rentalRoomTime.setSearchDate(LocalDate.parse(date, DateTimeFormatter.ISO_DATE));
                rentalRoomTimeList = spaceRentalService.getAvailableRoomTimeList(rentalRoomTime);
            }else{
                rentalRoomTimeList = spaceRentalService.getRoomTimeList(rentalRoomTime);
            }
            response = new Response("success", null, rentalRoomTimeList, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    @PostMapping("/schedule/apply")
    public ResponseEntity<Response>  addRentalSchedule(@RequestBody RentalSchedule rentalSchedule, Principal principal, HttpServletRequest req, HttpServletResponse res)throws Exception{
        Response response;
        try {
            rentalSchedule.setUserId(principal.getName());
            spaceRentalService.addRentalSchedule(rentalSchedule);

            response = new Response("success", null, null, 200);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch (CustomException.RentalScheduleDuplicate e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),409);
            return new ResponseEntity(response, HttpStatus.CONFLICT);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/schedule/apply/update")
    public ResponseEntity<Response>  updateRentalSchedule(@RequestBody RentalSchedule rentalSchedule, Principal principal, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            rentalSchedule.setUserId(principal.getName());
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

    @GetMapping("/schedule/apply")
    public Response getRentalScheduleList(@RequestParam(value = "page", defaultValue = "1") Integer page,Principal principal, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            RentalSchedule rentalSchedule = new RentalSchedule();
            rentalSchedule.setPageNo(page);
            rentalSchedule.setUserId(principal.getName());
            Map<String, Object> map =  spaceRentalService.getRentalScheduleList(rentalSchedule);
            response = new Response("success", null, map, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }
    @GetMapping("/schedule/apply/{scheduleId}")
    public Response getRentalSchedule(@PathVariable("scheduleId") int scheduleId,Principal principal, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            RentalSchedule rentalSchedule = new RentalSchedule();
            rentalSchedule.setScheduleId(scheduleId);
            rentalSchedule.setUserId(principal.getName());
            RentalSchedule result =  spaceRentalService.getRentalSchedule(rentalSchedule);
            response = new Response("success", null, result, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }


//    //사용 가능한 룸 시간 리스트 조회
//    @GetMapping("/place/room/{roomId}/time/available/{date}")
//    public Response getAvailableRoomTimeList(@PathVariable("roomId") int roomId, @PathVariable("date") LocalDate date, HttpServletRequest req, HttpServletResponse res){
//        Response response;
//        try {
//            RentalRoomTime rentalRoomTime = new RentalRoomTime();
//            rentalRoomTime.setRoomId(roomId);
//            List<RentalRoomTime> rentalRoomTimeList = spaceRentalService.getRoomTimeList(rentalRoomTime);
//            response = new Response("success", null, rentalRoomTimeList, 200);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            response =  new Response("error", null, e.getMessage(),400);
//        }
//        return response;
//    }


}
