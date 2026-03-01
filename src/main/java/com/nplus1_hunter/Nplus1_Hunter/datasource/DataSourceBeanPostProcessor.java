package com.nplus1_hunter.Nplus1_Hunter.datasource;

import com.nplus1_hunter.Nplus1_Hunter.autoconfigure.NPlusOneProperties;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

public class DataSourceBeanPostProcessor implements BeanPostProcessor {

    private final NPlusOneProperties properties;

    public DataSourceBeanPostProcessor(Environment environment) {
        this.properties = Binder.get(environment)
                .bind("nplus1", NPlusOneProperties.class)
                .orElseGet(NPlusOneProperties::new);
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
