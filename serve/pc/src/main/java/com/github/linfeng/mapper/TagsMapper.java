package com.github.linfeng.mapper;

import java.util.List;
import com.github.linfeng.entity.Tags;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 标签 Mapper 接口.
 *
 * @author 黄麟峰
 */
@Repository
public interface TagsMapper {

    List<Tags> list();

    Tags getById(@Param("id") Integer id);
}
