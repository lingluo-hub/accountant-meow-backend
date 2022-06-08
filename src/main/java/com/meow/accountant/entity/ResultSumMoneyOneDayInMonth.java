package com.meow.accountant.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultSumMoneyOneDayInMonth {
    private int day;
    private float summoney;
}
