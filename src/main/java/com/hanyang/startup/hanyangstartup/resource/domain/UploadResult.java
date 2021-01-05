package com.hanyang.startup.hanyangstartup.resource.domain;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UploadResult {
    private String url;
    private String uid;
    private String name;
    private String status;
    private Integer fileId;
}
