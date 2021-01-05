package com.hanyang.startup.hanyangstartup.resource.domain;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AttachFile {
    private int fileId;
    private int contentId;
    private FILE_STATUS status;
    private Long fileSize;
    private FILE_DIVISION division;
    private String filePath;
    private String fileName;
    private String fileOriginName;
    private String fileExtension;
}
