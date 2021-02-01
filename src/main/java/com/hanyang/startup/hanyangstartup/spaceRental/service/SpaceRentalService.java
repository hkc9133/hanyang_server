package com.hanyang.startup.hanyangstartup.spaceRental.service;

import com.hanyang.startup.hanyangstartup.common.exception.CustomException;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import com.hanyang.startup.hanyangstartup.resource.domain.FILE_DIVISION;
import com.hanyang.startup.hanyangstartup.resource.service.FileSaveService;
import com.hanyang.startup.hanyangstartup.spaceRental.dao.SpaceRentalDao;
import com.hanyang.startup.hanyangstartup.spaceRental.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpaceRentalService {

    @Autowired
    private SpaceRentalDao spaceRentalDao;

    @Autowired
    private FileSaveService fileSaveService;



    public List<StatusCount> getStatusCount(){
        return spaceRentalDao.getStatusCount();
    }
    public List<RentalPlace> getPlaceInfoAll(RentalPlace rentalPlace){
        return spaceRentalDao.getPlaceInfoAll(rentalPlace);
    }
    public Map<String,Object> getSpaceRentalInfoList(RentalRoom rentalRoom){
        Map<String,Object> map = new HashMap<>();

        map.put("place", spaceRentalDao.getPlaceList(null));
        map.put("room", spaceRentalDao.getRoomList(rentalRoom));


        return map;

    }

    //    공간 시작
    @Transactional(rollbackFor = {Exception.class})
    public void addPlace(RentalPlace rentalPlace){

        spaceRentalDao.addPlace(rentalPlace);

        if(rentalPlace.getAddAttachFileList() != null){
            for (MultipartFile newFile: rentalPlace.getAddAttachFileList()) {
                fileSaveService.fileSave(newFile, rentalPlace.getPlaceId(), FILE_DIVISION.PLACE_IMG);
            }
        }
    }
    @Transactional(rollbackFor = {Exception.class})
    public void updatePlace(RentalPlace rentalPlace){

        if(rentalPlace.getRemoveFiles() != null){
            List<AttachFile> attachFileList = new ArrayList<>();

            //파일 삭제
            for (int fileId: rentalPlace.getRemoveFiles()) {
                AttachFile attachFile = new AttachFile();
                attachFile.setFileId(fileId);
                attachFileList.add(attachFile);

            }
            fileSaveService.deleteAttachFile(attachFileList);
        }

        //파일 추가
        if(rentalPlace.getAddAttachFileList() != null){
            for (MultipartFile newFile: rentalPlace.getAddAttachFileList()) {
                fileSaveService.fileSave(newFile, rentalPlace.getPlaceId(), FILE_DIVISION.PLACE_IMG);
            }
        }
        spaceRentalDao.updatePlace(rentalPlace);
    }

    public RentalPlace getPlace(RentalPlace rentalPlace){
        return spaceRentalDao.getPlace(rentalPlace);
    }
    public List<RentalPlace> getPlaceList(RentalPlace rentalPlace){
        return spaceRentalDao.getPlaceList(rentalPlace);
    }

    public void deletePlace(RentalPlace rentalPlace){
        spaceRentalDao.deletePlace(rentalPlace);
    }
    //    공간 끝

    //    룸 시작
    @Transactional(rollbackFor = {Exception.class})
    public void createRoom(RentalRoom rentalRoom){

        spaceRentalDao.createRoom(rentalRoom);

        //파일 추가
        if(rentalRoom.getAddAttachFileList() != null){
            for (MultipartFile newFile: rentalRoom.getAddAttachFileList()) {
                fileSaveService.fileSave(newFile, rentalRoom.getRoomId(), FILE_DIVISION.ROOM_IMG);
            }
        }


        //타임 추가
        if(rentalRoom.getAddRentalRoomTimeList() != null){
            for (RentalRoomTime rentalRoomTime: rentalRoom.getAddRentalRoomTimeList()) {
                rentalRoomTime.setRoomId(rentalRoom.getRoomId());
                spaceRentalDao.createRoomTime(rentalRoomTime);
            }
        }

    }

    @Transactional(rollbackFor = {Exception.class})
    public void updateRoom(RentalRoom rentalRoom){

        if(rentalRoom.getRemoveFiles() != null){
            List<AttachFile> attachFileList = new ArrayList<>();

            //파일 삭제
            for (int fileId: rentalRoom.getRemoveFiles()) {
                AttachFile attachFile = new AttachFile();
                attachFile.setFileId(fileId);
                attachFileList.add(attachFile);

            }
            fileSaveService.deleteAttachFile(attachFileList);
        }

        //파일 추가
        if(rentalRoom.getAddAttachFileList() != null){
            for (MultipartFile newFile: rentalRoom.getAddAttachFileList()) {
                fileSaveService.fileSave(newFile, rentalRoom.getRoomId(), FILE_DIVISION.ROOM_IMG);
            }
        }

        //타임 삭제
        if(rentalRoom.getRemoveRentalRoomTimeList() != null){
            for (Integer timeId: rentalRoom.getRemoveRentalRoomTimeList()) {
                RentalRoomTime rentalRoomTime = new RentalRoomTime();
                rentalRoomTime.setTimeId(timeId);
                spaceRentalDao.deleteRoomTime(rentalRoomTime);
            }
        }

        //타임 추가
        if(rentalRoom.getAddRentalRoomTimeList() != null){
            for (RentalRoomTime rentalRoomTime: rentalRoom.getAddRentalRoomTimeList()) {
                spaceRentalDao.createRoomTime(rentalRoomTime);
            }
        }

        spaceRentalDao.updateRoomTime(rentalRoom.getRentalRoomTimeList());
        spaceRentalDao.updateRoom(rentalRoom);
    }

    public RentalRoom getRoom(RentalRoom rentalRoom){
        return spaceRentalDao.getRoom(rentalRoom);
    }
    public List<RentalRoom> getRoomList(RentalRoom rentalRoom){
        return spaceRentalDao.getRoomList(rentalRoom);
    }

    public void deleteRoom(RentalRoom rentalRoom){
        spaceRentalDao.deleteRoom(rentalRoom);
    }
    //    룸 끝

    //    룸 시간 시작
    public void createRoomTime(RentalRoomTime rentalRoomTime){
        if(spaceRentalDao.getRoomTimeDuplicateCheck(rentalRoomTime).size() > 0){
            throw new CustomException.RentalTimeDuplicate("중복된 대여 시간이 있습니다");
        }
        spaceRentalDao.createRoomTime(rentalRoomTime);
    }
    public void updateRoomTime(List<RentalRoomTime> rentalRoomTimeList){
        spaceRentalDao.updateRoomTime(rentalRoomTimeList);
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


    public Map<String, Object> getRentalScheduleList(RentalSchedule rentalSchedule){
        rentalSchedule.setTotalCount(spaceRentalDao.getRentalScheduleListCnt(rentalSchedule));
        List<RentalSchedule> rentalScheduleList = spaceRentalDao.getRentalScheduleList(rentalSchedule);


        Map<String, Object> map = new HashMap<>();

        map.put("page", rentalSchedule);
        map.put("list", rentalScheduleList);

        return map;
    }

    public RentalSchedule getRentalSchedule(RentalSchedule rentalSchedule){
        return spaceRentalDao.getRentalSchedule(rentalSchedule);
    }

    public void addRentalSchedule(RentalSchedule rentalSchedule){
        if(spaceRentalDao.getScheduleDuplicateCheck(rentalSchedule).size() > 0){
            throw new CustomException.RentalScheduleDuplicate("이미 예약된 일정입니다");
        }
        spaceRentalDao.addRentalSchedule(rentalSchedule);
    }
    public void updateRentalSchedule(RentalSchedule rentalSchedule){
        spaceRentalDao.updateRentalSchedule(rentalSchedule);
    }
    //스케쥴 끝
}
