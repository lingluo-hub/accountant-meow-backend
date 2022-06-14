package com.meow.accountant.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class BarCharItem implements Serializable {
    private int year;
    private int month;
    private int day;
    private float summoney;
}
