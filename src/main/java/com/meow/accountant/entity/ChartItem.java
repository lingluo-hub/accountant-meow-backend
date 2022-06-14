package com.meow.accountant.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ChartItem implements Serializable {
    private int sImageId;
    private String typename;
    private float ratio;   //所占比例
    private float total;  //此项的总钱数

}
