package net.xiaodiclass.xdclass.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * mybatis分页插件配置
 */
@Configuration
public class MybatisConfig {
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper= new PageHelper();
        Properties p=new Properties();
        //设置为true时会将rowbounds第一个参数offset当成pagenum页码使用
        p.setProperty("offsetAsPageNum","true");
        //设置为true时使用RowBounds分页会进行count
        p.setProperty("rowBoundsWithCount","true");
        p.setProperty("reasonable","true");
        pageHelper.setProperties(p);
        return pageHelper;

    }
}
