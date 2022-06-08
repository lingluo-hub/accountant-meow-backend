package com.meow.accountant.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "budget")
public class Budget {
    @Id
    private String userid;

    @Column
    private float budget;

    @Column
    private String timest;

}
