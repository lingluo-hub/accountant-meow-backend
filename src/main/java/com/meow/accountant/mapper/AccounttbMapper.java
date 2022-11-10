package com.meow.accountant.mapper;
import org.apache.ibatis.annotations.Param;

import com.meow.accountant.entity.Accounttb;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 凌洛
* @description 针对表【accounttb】的数据库操作Mapper
* @createDate 2022-11-10 16:34:55
* @Entity com.meow.accountant.entity.Accounttb
*/

@Mapper
public interface AccounttbMapper extends BaseMapper<Accounttb> {

    int insertAll(Accounttb accounttb);

    int updateSelective(Accounttb accounttb);

    int getIdExsits(Integer Id);

    int deleteById(@Param("id") Integer id);

}




