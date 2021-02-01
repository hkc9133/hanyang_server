package com.hanyang.startup.hanyangstartup.spaceRental.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentalRoom {
    private Integer roomId;
    private Integer placeId;
    private String roomName;
    private Boolean isActive = false;
    private int capacity;
    private String possibleDay;
    private List<Integer> possibleDayArray;
    private String roomImg;
    private String roomDesc;
    private String rentalRole;
    private Boolean isHoliday;

    private List<Integer> removeFiles;

    //result 용도
    private List<AttachFile> roomAttachFileList;
    private MultipartFile[] addAttachFileList;

    private List<RentalRoomTime> rentalRoomTimeList;
    //multipart/form-data에서 json array로 받기 어려워서 string으로 받음
    private String[] rentalRoomTimeListStr;

    private List<RentalRoomTime> addRentalRoomTimeList;
    //multipart/form-data에서 json array로 받기 어려워서 string으로 받음
    private List<String> addRentalRoomTimeListStr;

    private List<Integer> removeRentalRoomTimeList;
}
