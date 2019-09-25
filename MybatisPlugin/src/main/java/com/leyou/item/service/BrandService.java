package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @Author: taft
 * @Date: 2018-8-16 17:54
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public List<Brand> queryBrandsByPage(Map map){
        return brandMapper.queryBrandsByPage(map);
    }
}
