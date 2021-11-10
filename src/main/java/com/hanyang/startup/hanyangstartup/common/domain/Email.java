package com.hanyang.startup.hanyangstartup.common.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Email {
    private String title;
    private String desc;
    private String to;
    private String toName;
}
