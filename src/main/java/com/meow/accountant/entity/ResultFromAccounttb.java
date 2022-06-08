package com.meow.accountant.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultFromAccounttb {
    private String typename;
    private int sImageId;
    private float total;
}
