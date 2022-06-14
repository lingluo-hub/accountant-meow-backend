package com.meow.accountant.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ResultFromAccounttb implements Serializable {
    private String typename;
    private int sImageId;
    private float total;
}
