package com.github.linfeng.service.impl;

import java.util.List;
import com.github.linfeng.entity.Tags;
import com.github.linfeng.mapper.TagsMapper;
import com.github.linfeng.service.ITagsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 标签 服务实现类.
 *
 * @author 黄麟峰
 */
@Service
public class TagsServiceImpl implements ITagsService {

    @Autowired
    private TagsMapper tagsMapper;

    @Override
    public List<Tags> list() {
        return tagsMapper.list();
    }

    @Override
    public Tags getById(Integer id) {
        return tagsMapper.getById(id);
    }
}
