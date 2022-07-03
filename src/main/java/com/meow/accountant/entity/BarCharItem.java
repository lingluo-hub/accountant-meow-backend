package com.meow.accountant.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 凌洛
 */
@Getter
@Setter
public class BarCharItem implements BaseEntity {
    private int year;
    private int month;
    private int day;
    private float summoney;
}
