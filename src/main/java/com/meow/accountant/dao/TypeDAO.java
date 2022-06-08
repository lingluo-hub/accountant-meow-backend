package com.meow.accountant.dao;

import com.meow.accountant.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lihangqi
 */
@Mapper
@Repository
public interface TypeDAO {
    void insertType(String typename, int imageId, int sImageId, int kind);

    List<Type> queryTypeList();//返回值为list集合
    List<Type> getTypeByKind(int kind);
}
