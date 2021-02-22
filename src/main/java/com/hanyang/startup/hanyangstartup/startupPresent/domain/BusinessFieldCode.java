package com.hanyang.startup.hanyangstartup.startupPresent.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BusinessFieldCode {
    //분야
    private Integer startupId;
    private Integer businessId;
    private String businessName;
}
