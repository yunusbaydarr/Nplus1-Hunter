package com.nplus1_hunter.Nplus1_Hunter.autoconfigure;

import com.nplus1_hunter.Nplus1_Hunter.datasource.QueryCaptureListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import javax.sql.DataSource;

public class DataSourceBeanPostProcessor implements BeanPostProcessor {

    private final NPlusOneProperties properties;

    public DataSourceBeanPostProcessor(NPlusOneProperties properties) {
        this.properties = properties;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource) {
            return ProxyDataSourceBuilder
                    .create((DataSource) bean)
                    .listener(new QueryCaptureListener(properties))
                    .build();
        }
        return bean;
    }
}