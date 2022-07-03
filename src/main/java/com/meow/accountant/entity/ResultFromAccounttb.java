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
public class ResultFromAccounttb implements BaseEntity {
    private String typename;
    private int sImageId;
    private float total;
}
