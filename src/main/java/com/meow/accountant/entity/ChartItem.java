package com.meow.accountant.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 凌洛
 */
@Getter
@Setter
@NoArgsConstructor
public class ChartItem implements BaseEntity {
    private int sImageId;
    private String typename;
    private float ratio;   //所占比例
    private float total;  //此项的总钱数

}
