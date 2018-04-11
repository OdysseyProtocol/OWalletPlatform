package com.coinwallet.rechage.dao;

import com.coinwallet.rechage.entity.userCoinLog;
import com.coinwallet.rechage.entity.UserCoinLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserCoinLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(userCoinLog record);

    int insertSelective(userCoinLog record);

    List<userCoinLog> selectByExample(UserCoinLogExample example);

    userCoinLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") userCoinLog record, @Param("example") UserCoinLogExample example);

    int updateByExample(@Param("record") userCoinLog record, @Param("example") UserCoinLogExample example);

    int updateByPrimaryKeySelective(userCoinLog record);

    int updateByPrimaryKey(userCoinLog record);
}