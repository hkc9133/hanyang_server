package com.hanyang.startup.hanyangstartup.auth.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HanyangResponse {
//    private HanyangItem item;
    private int totalCount;
    private List<HanyangItem> list;
}
