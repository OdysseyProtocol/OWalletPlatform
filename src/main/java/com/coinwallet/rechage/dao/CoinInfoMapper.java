package com.coinwallet.rechage.dao;

import com.coinwallet.rechage.entity.CoinInfo;
import com.coinwallet.rechage.entity.CoinInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CoinInfoMapper {
    int countByExample(CoinInfoExample example);

    int deleteByExample(CoinInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CoinInfo record);

    int insertSelective(CoinInfo record);

    List<CoinInfo> selectByExample(CoinInfoExample example);

    CoinInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CoinInfo record, @Param("example") CoinInfoExample example);

    int updateByExample(@Param("record") CoinInfo record, @Param("example") CoinInfoExample example);

    int updateByPrimaryKeySelective(CoinInfo record);

    int updateByPrimaryKey(CoinInfo record);

    CoinInfo selectByContractAddress(String contractAddress);

    List<String> selectContractAddress();
}