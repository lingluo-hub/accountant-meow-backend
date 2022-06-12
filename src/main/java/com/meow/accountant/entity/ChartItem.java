package com.meow.accountant.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ChartItem {
    private int sImageId;
    private String typename;
    private float ratio;   //所占比例
    private float total;  //此项的总钱数

}
