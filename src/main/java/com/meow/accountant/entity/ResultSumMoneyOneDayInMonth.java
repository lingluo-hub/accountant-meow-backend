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
public class ResultSumMoneyOneDayInMonth implements BaseEntity {
    private int day;
    private float summoney;
}
