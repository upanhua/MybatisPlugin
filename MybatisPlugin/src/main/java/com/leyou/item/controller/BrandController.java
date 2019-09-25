package com.leyou.item.controller;

import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import com.leyou.item.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liuhao
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 根据多个id查询品牌
     * @return
     */
    @GetMapping("test")
    public ResponseEntity<List<Brand>> queryBrandByPage(int page,int limit){
        // 初始化map对象
        Map map = new HashMap();
        // 初始化PageUtil对象
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPage(page);
        pageUtil.setLimit(limit);
        // 传递pageUtil参数
        map.put("page",pageUtil);
        List<Brand> list = this.brandService.queryBrandsByPage(map);
        return ResponseEntity.ok(list);
    }
}
