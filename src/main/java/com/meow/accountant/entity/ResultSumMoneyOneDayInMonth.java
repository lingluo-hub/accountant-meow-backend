package com.meow.accountant.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ResultSumMoneyOneDayInMonth implements Serializable {
    private int day;
    private float summoney;
}
