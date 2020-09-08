package com.jeerigger.core.activiti.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 执行sql语句
 * @return
 */
@MapperScan
public interface WorkFlowMapper {
	/**
	 * 执行sql语句
	 * @param sql
	 * @return
	 */
	@Select("${sql}")
	List<Map<String,Object>> executeSql(@Param("sql") String sql);
}
