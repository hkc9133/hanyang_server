package com.hanyang.startup.hanyangstartup.startupPresent.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TechFieldCode {
    //분야
    private Integer startupId;
    private Integer techId;
    private String techName;
}
