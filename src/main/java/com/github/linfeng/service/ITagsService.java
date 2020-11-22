package com.github.linfeng.service;

import java.util.List;
import com.github.linfeng.entity.Tags;

/**
 * 标签 服务类.
 *
 * @author 黄麟峰
 */
public interface ITagsService {

    List<Tags> list();

    Tags getById(Integer id);
}
