package com.meow.accountant.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BarCharItem {
    private int year;
    private int month;
    private int day;
    private float summoney;
}
