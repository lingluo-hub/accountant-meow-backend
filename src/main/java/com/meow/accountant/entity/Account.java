package com.meow.accountant.entity;

import com.ejlchina.searcher.bean.DbField;
import com.ejlchina.searcher.bean.SearchBean;
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
@SearchBean(tables = "accounttb", autoMapTo = "accounttb")
@Table(name = "accounttb")
public class Account implements BaseEntity {

    @Id
    private int id;

    @Column
    private String typename;

    @Column
    @DbField("accounttb.simageid")
    private int sImageId;

    @Column
    private String beizhu;

    @Column
    private float money;

    @Column
    private String time;

    @Column
    private int year;

    @Column
    private int month;

    @Column
    private int day;

    @Column
    private int kind;

    @Column
    private String userid;

}
