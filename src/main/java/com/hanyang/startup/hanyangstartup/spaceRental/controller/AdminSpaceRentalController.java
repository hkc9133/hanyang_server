package com.hanyang.startup.hanyangstartup.spaceRental.controller;

import com.hanyang.startup.hanyangstartup.board.domain.BoardConfig;
import com.hanyang.startup.hanyangstartup.common.domain.Response;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalPlace;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalRoom;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalRoomTime;
import com.hanyang.startup.hanyangstartup.spaceRental.service.SpaceRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/admin/space_rental")
public class AdminSpaceRentalController {

    @Autowired
    private SpaceRentalService spaceRentalService;

    //공간 생성
    @PostMapping("/place")
    public Response createPlace(@RequestBody RentalPlace rentalPlace, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            spaceRentalService.createPlace(rentalPlace);
            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    //공간 수정
    @PutMapping("/place/{placeId}")
    public Response updatePlace(@PathVariable("placeId") int placeId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {

            RentalPlace rentalPlace = new RentalPlace();
            rentalPlace.setPlaceId(placeId);

            spaceRentalService.updatePlace(rentalPlace);
            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    //공간 삭제
    @DeleteMapping("/place/{placeId}")
    public Response deletePlace(@PathVariable("placeId") int placeId, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            RentalPlace rentalPlace = new RentalPlace();
            rentalPlace.setPlaceId(placeId);

            spaceRentalService.deletePlace(rentalPlace);

            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }


    //룸 생성
    @PostMapping("/place/{placeId}/room")
    public Response createRoom(@PathVariable("placeId") int placeId,@RequestBody RentalRoom rentalRoom, HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {

            rentalRoom.setPlaceId(placeId);
            spaceRentalService.createRoom(rentalRoom);
            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    //룸 수정
    @PutMapping("/place/room/{roomId}")
    public Response updateRoom(@PathVariable("roomId") int roomId,@RequestBody RentalRoom rentalRoom,  HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            rentalRoom.setRoomId(roomId);
            spaceRentalService.updateRoom(rentalRoom);
            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
    }

    //룸 삭제
    @DeleteMapping("/place/room/{roomId}")
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
    public Response updateRoomTime(@PathVariable("roomId") int roomId,@RequestBody RentalRoomTime rentalRoomTime,  HttpServletRequest req, HttpServletResponse res){
        Response response;
        try {
            rentalRoomTime.setRoomId(roomId);
            spaceRentalService.updateRoomTime(rentalRoomTime);
            response = new Response("success", null, null, 200);
        }
        catch(Exception e){
            e.printStackTrace();
            response =  new Response("error", null, e.getMessage(),400);
        }
        return response;
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
}
