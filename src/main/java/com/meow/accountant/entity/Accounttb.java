package com.meow.accountant.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ejlchina.searcher.bean.SearchBean;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 凌洛
 * @TableName accounttb
 * @Description 记账记录表
 */
@SearchBean(tables = "accounttb", autoMapTo = "accounttb")
@TableName(value ="accounttb")
@Data
public class Accounttb implements Serializable {
    /**
     * 记录标识ID
     */
    @TableId
    private Integer id;

    /**
     * 类型名
     */
    @Column
    private String typename;

    /**
     * 类型图片资源id
     */
    @Column
    private Integer simageid;

    /**
     * 备注
     */
    @Column
    private String beizhu;

    /**
     * 金额
     */
    @Column
    private Double money;

    /**
     * 记录时间
     */
    @Column
    private String time;

    /**
     * 记录年份
     */
    @Column
    private Integer year;

    /**
     * 记录月份
     */
    @Column
    private Integer month;

    /**
     * 记录日期
     */
    @Column
    private Integer day;

    /**
     * 记录类型
     */
    @Column
    private Integer kind;

    /**
     * 时间戳
     */
    @Column
    private Date timest;

    /**
     * 用户id
     */
    @Column
    private String userid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Accounttb other = (Accounttb) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTypename() == null ? other.getTypename() == null : this.getTypename().equals(other.getTypename()))
            && (this.getSimageid() == null ? other.getSimageid() == null : this.getSimageid().equals(other.getSimageid()))
            && (this.getBeizhu() == null ? other.getBeizhu() == null : this.getBeizhu().equals(other.getBeizhu()))
            && (this.getMoney() == null ? other.getMoney() == null : this.getMoney().equals(other.getMoney()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getYear() == null ? other.getYear() == null : this.getYear().equals(other.getYear()))
            && (this.getMonth() == null ? other.getMonth() == null : this.getMonth().equals(other.getMonth()))
            && (this.getDay() == null ? other.getDay() == null : this.getDay().equals(other.getDay()))
            && (this.getKind() == null ? other.getKind() == null : this.getKind().equals(other.getKind()))
            && (this.getTimest() == null ? other.getTimest() == null : this.getTimest().equals(other.getTimest()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTypename() == null) ? 0 : getTypename().hashCode());
        result = prime * result + ((getSimageid() == null) ? 0 : getSimageid().hashCode());
        result = prime * result + ((getBeizhu() == null) ? 0 : getBeizhu().hashCode());
        result = prime * result + ((getMoney() == null) ? 0 : getMoney().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getYear() == null) ? 0 : getYear().hashCode());
        result = prime * result + ((getMonth() == null) ? 0 : getMonth().hashCode());
        result = prime * result + ((getDay() == null) ? 0 : getDay().hashCode());
        result = prime * result + ((getKind() == null) ? 0 : getKind().hashCode());
        result = prime * result + ((getTimest() == null) ? 0 : getTimest().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", typename=").append(typename);
        sb.append(", simageid=").append(simageid);
        sb.append(", beizhu=").append(beizhu);
        sb.append(", money=").append(money);
        sb.append(", time=").append(time);
        sb.append(", year=").append(year);
        sb.append(", month=").append(month);
        sb.append(", day=").append(day);
        sb.append(", kind=").append(kind);
        sb.append(", timest=").append(timest);
        sb.append(", userid=").append(userid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}