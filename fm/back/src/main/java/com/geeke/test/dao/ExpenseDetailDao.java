package com.geeke.test.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.geeke.wf.persistence.WfCrudDao;

import com.geeke.test.entity.ExpenseDetail;

/**
 * 请假管理DAO接口
 * @author lys
 * @version 2020-05-31
 */
@Mapper
public interface ExpenseDetailDao extends WfCrudDao<ExpenseDetail> {
}