package com.hanyang.startup.hanyangstartup.spaceRental.service;

import com.hanyang.startup.hanyangstartup.common.exception.CustomException;
import com.hanyang.startup.hanyangstartup.spaceRental.dao.SpaceRentalDao;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalPlace;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalRoom;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalRoomTime;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpaceRentalService {

    @Autowired
    private SpaceRentalDao spaceRentalDao;

    public Map<String,Object> getSpaceRentalInfoList(){
        Map<String,Object> map = new HashMap<>();

        map.put("place", spaceRentalDao.getPlaceList(null));
        map.put("room", spaceRentalDao.getRoomList(null));


        return map;

    }

    //    공간 시작
    public void createPlace(RentalPlace rentalPlace){
        spaceRentalDao.createPlace(rentalPlace);
    }
    public void updatePlace(RentalPlace boardCategory){
        spaceRentalDao.updatePlace(boardCategory);
    }

    public RentalPlace getPlace(RentalPlace rentalPlace){
        return spaceRentalDao.getPlace(rentalPlace);
    }
    public List<RentalPlace> getPlaceList(RentalPlace rentalPlace){
        return spaceRentalDao.getPlaceList(rentalPlace);
    }

    public int deletePlace(RentalPlace rentalPlace){
        return spaceRentalDao.deletePlace(rentalPlace);
    }
    //    공간 끝

    //    룸 시작
    public void createRoom(RentalRoom rentalRoom){
        String possibleDayStr = "";
        int index = 0;
        List<Integer> possibleDayList = rentalRoom.getPossibleDayArray();
        for (Integer possibleDay:possibleDayList) {
            possibleDayStr += possibleDay.toString();
            if(!(possibleDayList.size()-1 == index)){
                possibleDayStr += ";";
            }
            index++;
        }
        rentalRoom.setPossibleDay(possibleDayStr);
        spaceRentalDao.createRoom(rentalRoom);
    }
    public void updateRoom(RentalRoom rentalRoom){
        spaceRentalDao.updateRoom(rentalRoom);
    }

    public RentalRoom getRoom(RentalRoom rentalRoom){
        return spaceRentalDao.getRoom(rentalRoom);
    }
    public List<RentalRoom> getRoomList(RentalRoom rentalRoom){
        return spaceRentalDao.getRoomList(rentalRoom);
    }

    public int deleteRoom(RentalRoom rentalRoom){
        return spaceRentalDao.deleteRoom(rentalRoom);
    }
    //    룸 끝

    //    룸 시간 시작
    public void createRoomTime(RentalRoomTime rentalRoomTime){
        if(spaceRentalDao.getRoomTimeDuplicateCheck(rentalRoomTime).size() > 0){
            throw new CustomException.RentalTimeDuplicate("중복된 대여 시간이 있습니다");
        }
        spaceRentalDao.createRoomTime(rentalRoomTime);
    }
    public void updateRoomTime(RentalRoomTime rentalRoomTime){
        spaceRentalDao.updateRoomTime(rentalRoomTime);
    }
    public List<RentalRoomTime> getRoomTimeList(RentalRoomTime rentalRoomTime){
        List<RentalRoomTime> rentalRoomTimeList = spaceRentalDao.getRoomTimeList(rentalRoomTime);
        return rentalRoomTimeList;
    }
    public List<RentalRoomTime> getAvailableRoomTimeList(RentalRoomTime rentalRoomTime){
        List<RentalRoomTime> rentalRoomTimeList = spaceRentalDao.getAvailableRoomTimeList(rentalRoomTime);
        return rentalRoomTimeList;
    }

    public int deleteRoomTime(RentalRoomTime rentalRoomTime){
        return spaceRentalDao.deleteRoomTime(rentalRoomTime);
    }
//    룸 시간 끝

    //스케쥴 시작
    public void addRentalSchedule(RentalSchedule rentalSchedule){
        if(spaceRentalDao.getScheduleDuplicateCheck(rentalSchedule).size() > 0){
            throw new CustomException.RentalScheduleDuplicate("이미 예약된 일정입니다");
        }

        spaceRentalDao.addRentalSchedule(rentalSchedule);

    }
    //스케쥴 끝
}
