package com.github.linfeng.service.impl;

import com.github.linfeng.entity.Tags;
import com.github.linfeng.mapper.TagsMapper;
import com.github.linfeng.service.ITagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 标签 服务实现类.
 *
 * @author 黄麟峰
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements ITagsService {

}
