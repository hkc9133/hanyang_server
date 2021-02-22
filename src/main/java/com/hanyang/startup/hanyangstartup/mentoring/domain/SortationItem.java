package com.hanyang.startup.hanyangstartup.mentoring.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SortationItem {
    private int formId;
    private int itemId;
    private String item;

    public SortationItem (int formId, int itemId){
        this.formId = formId;
        this.itemId = itemId;
    }
}
