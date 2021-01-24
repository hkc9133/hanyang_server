package com.hanyang.startup.hanyangstartup.spaceRental.dao;

import com.hanyang.startup.hanyangstartup.board.domain.BoardCategory;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalPlace;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalRoom;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalRoomTime;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.RentalSchedule;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpaceRentalDao {
    @Autowired
    protected SqlSessionTemplate sqlSession;

    public List<RentalPlace> getPlaceInfoAll(RentalPlace rentalPlace){
        return sqlSession.selectList("spaceRental.getPlaceInfoAll",rentalPlace);
    }

//    공간 시작
    public void addPlace(RentalPlace rentalPlace){
        sqlSession.insert("spaceRental.addPlace",rentalPlace);
    }
    public void updatePlace(RentalPlace boardCategory){
        sqlSession.update("spaceRental.updatePlace",boardCategory);
    }

    public RentalPlace getPlace(RentalPlace rentalPlace){
        return sqlSession.selectOne("spaceRental.getPlace",rentalPlace);
    }
    public List<RentalPlace> getPlaceList(RentalPlace rentalPlace){
        return sqlSession.selectList("spaceRental.getPlaceList",rentalPlace);
    }

    public int deletePlace(RentalPlace rentalPlace){
        return sqlSession.delete("spaceRental.deletePlace",rentalPlace);
    }


//    공간 끝

//    룸 시작
    public void createRoom(RentalRoom rentalRoom){
        sqlSession.insert("spaceRental.createRoom",rentalRoom);
    }
    public void updateRoom(RentalRoom rentalRoom){
        sqlSession.update("spaceRental.updateRoom",rentalRoom);
    }

    public RentalRoom getRoom(RentalRoom rentalRoom){
        return sqlSession.selectOne("spaceRental.getRoom",rentalRoom);
    }
    public List<RentalRoom> getRoomList(RentalRoom rentalRoom){
        return sqlSession.selectList("spaceRental.getRoomList",rentalRoom);
    }

    public int deleteRoom(RentalRoom rentalRoom){
        return sqlSession.delete("spaceRental.deleteRoom",rentalRoom);
    }
//    룸 끝

//    룸 시간 시작

    public List<RentalRoomTime> getRoomTimeDuplicateCheck(RentalRoomTime rentalRoomTime){
        return sqlSession.selectList("spaceRental.getRoomTimeDuplicateCheck",rentalRoomTime);
    }
    public void createRoomTime(RentalRoomTime rentalRoomTime){
        sqlSession.insert("spaceRental.createRoomTime",rentalRoomTime);
    }
    public void updateRoomTime(List<RentalRoomTime> rentalRoomTimeList){
        sqlSession.update("spaceRental.updateRoomTime",rentalRoomTimeList);
    }
    public List<RentalRoomTime> getRoomTimeList(RentalRoomTime rentalRoomTime){
        return sqlSession.selectList("spaceRental.getRoomTimeList",rentalRoomTime);
    }

    public List<RentalRoomTime> getAvailableRoomTimeList(RentalRoomTime rentalRoomTime){
        return sqlSession.selectList("spaceRental.getAvailableRoomTimeList",rentalRoomTime);
    }

    public int deleteRoomTime(RentalRoomTime rentalRoomTime){
        return sqlSession.delete("spaceRental.deleteRoomTime",rentalRoomTime);
    }
//    public void deleteRoomTimeWithRoomId(RentalRoomTime rentalRoomTime){
//        sqlSession.delete("spaceRental.deleteRoomTimeWithRoomId",rentalRoomTime);
//    }
//    룸 시간 끝

    //스케쥴 시작
    public List<RentalSchedule> getScheduleDuplicateCheck(RentalSchedule rentalSchedule){
        return sqlSession.selectList("spaceRental.getScheduleDuplicateCheck",rentalSchedule);
    }
    public void addRentalSchedule(RentalSchedule rentalSchedule){
        sqlSession.insert("spaceRental.addRentalSchedule",rentalSchedule);
    }

    public void updateRentalSchedule(RentalSchedule rentalSchedule){
        sqlSession.update("spaceRental.updateRentalSchedule",rentalSchedule);
    }

    public int getRentalScheduleListCnt(RentalSchedule rentalSchedule){
        return sqlSession.selectOne("spaceRental.getRentalScheduleListCnt",rentalSchedule);
    }

    public List<RentalSchedule> getRentalScheduleList(RentalSchedule rentalSchedule){
        return sqlSession.selectList("spaceRental.getRentalScheduleList",rentalSchedule);
    }

    public RentalSchedule getRentalSchedule(RentalSchedule rentalSchedule){
        return sqlSession.selectOne("spaceRental.getRentalSchedule",rentalSchedule);
    }


    //스케쥴 끝

}
