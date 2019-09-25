package com.leyou.item.interceptor;

import com.leyou.item.util.PageUtil;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

/**
 * sql语句拦截类
 *
 * @author lh
 * @version 1.0
 * @date 2019-09-25 14:51
 */

// 代理StatementHandler，代理的方法为StatementHandler类的prepare，参数类型为Connection、Integer
@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class MapperInterceptor implements Interceptor {

    // 拦截规则
    private String end;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取代理对象
        StatementHandler target = (StatementHandler) invocation.getTarget();
        // 获取参数类型列表
        Object[] args = invocation.getArgs();
        // 获取sql语句的id(id取法直接通过getter方法获取，因此这里通过反射进行获取)
        MetaObject metaObject = MetaObject.forObject(target, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        String id = (String) metaObject.getValue("delegate.mappedStatement.id");
        // 按照拦截规则进行拦截
        if (id.matches(end)) {
            // 获取sql语句
            String sql = target.getBoundSql().getSql();
            System.out.println("原始的sql=" + sql);
            // 获取connection
            Connection connection = (Connection) args[0];
            // 新建count的sql(查询获取总的记录数)
            String newsql = "select count(0) from (" + sql + ") a";
            System.out.println("新建count的sql=" + newsql);
            // 获取sql语句执行对象
            PreparedStatement preparedStatement = connection.prepareStatement(newsql);
            // 如果有条件查询，那么需要将条件带上
            // 获取参数操作对象
            ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
            // 将参数操作对象设置到parameterHandler
            parameterHandler.setParameters(preparedStatement);
            // 获取传入的参数
            HashMap<String, Object> parameterObject = (HashMap<String, Object>) parameterHandler.getParameterObject();
            PageUtil pageUtil = (PageUtil) parameterObject.get("page");
            // 执行sql语句
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int count = resultSet.getInt(1);
                pageUtil.setCount(count);
            }
            preparedStatement.close();
            resultSet.close();
            // 拼接sql语句
            String addsql = sql + " limit " + pageUtil.getStart() + "," + pageUtil.getLimit();
            System.out.println("拼接sql语句=" + addsql);
            metaObject.setValue("delegate.boundSql.sql", addsql);
        }
        // 放行
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // target:就是需要代理的目标对象  this:就是让当前的类进行代理
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 加载配置文件
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
