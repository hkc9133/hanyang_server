package com.hanyang.startup.hanyangstartup.spaceRental.domain;

import com.hanyang.startup.hanyangstartup.resource.domain.AttachFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentalPlace {
    private Integer placeId;
    private Boolean isActive = true;
    private String placeName;
    private String placeDesc;
    private List<RentalRoom> rentalRoomList;

    private List<Integer> removeFiles;

    //result 용도
    private List<AttachFile> placeAttachFileList;
    private MultipartFile[] addAttachFileList;
}
