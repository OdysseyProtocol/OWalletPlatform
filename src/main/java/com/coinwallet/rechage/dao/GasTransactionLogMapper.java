package com.coinwallet.rechage.dao;

import com.coinwallet.rechage.entity.GasTransactionLog;
import com.coinwallet.rechage.entity.GasTransactionLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GasTransactionLogMapper {
    int countByExample(GasTransactionLogExample example);

    int deleteByExample(GasTransactionLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GasTransactionLog record);

    int insertSelective(GasTransactionLog record);

    List<GasTransactionLog> selectByExample(GasTransactionLogExample example);

    GasTransactionLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GasTransactionLog record, @Param("example") GasTransactionLogExample example);

    int updateByExample(@Param("record") GasTransactionLog record, @Param("example") GasTransactionLogExample example);

    int updateByPrimaryKeySelective(GasTransactionLog record);

    int updateByPrimaryKey(GasTransactionLog record);
}