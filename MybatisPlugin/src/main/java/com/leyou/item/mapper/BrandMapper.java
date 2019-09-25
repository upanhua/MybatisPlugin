package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: taft
 * @Date: 2018-8-16 17:54
 */
public interface BrandMapper extends Mapper<Brand>,SelectByIdListMapper<Brand,Long> {

    @Select("SELECT b.* FROM tb_brand b")
    List<Brand> queryBrandsByPage(Map map);
}
