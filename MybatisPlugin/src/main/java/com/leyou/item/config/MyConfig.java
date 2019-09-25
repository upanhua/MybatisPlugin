package com.leyou.item.config;

import com.leyou.item.interceptor.MapperInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 配置类
 * @author lh
 * @version 1.0
 * @date 2019-09-25 15:14
 */
@Configuration
public class MyConfig {

    /**
     * 初始化SqlSessionFactoryBean参数
     * @param dataSource
     * @return
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Autowired DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 设置配置文件
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{getPlugins()});
        return sqlSessionFactoryBean;
    }

    /**
     * 拦截规则
     * @return
     */
    public Interceptor getPlugins(){
        MapperInterceptor mapperInterceptor = new MapperInterceptor();
        mapperInterceptor.setEnd(".*ByPage$");
        return mapperInterceptor;
    }


}
