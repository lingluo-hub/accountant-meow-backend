package com.meow.accountant.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 凌洛
 */
@Getter
@Setter
@Entity
@Table(name = "budget")
public class Budget implements BaseEntity {
    @Id
    private String userid;

    @Column
    private float budget;

    @Column
    private String timest;

}
