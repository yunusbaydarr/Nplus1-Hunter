package com.nplus1_hunter.Nplus1_Hunter.autoconfigure;

import com.nplus1_hunter.Nplus1_Hunter.web.NPlusOneFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(NPlusOneProperties.class)
@ConditionalOnProperty(prefix = "nplus1", name = "enabled", havingValue = "true", matchIfMissing = true)
public class NPlusOneAutoConfiguration {

    @Bean
    public static DataSourceBeanPostProcessor dataSourceBeanPostProcessor(NPlusOneProperties properties) {
        return new DataSourceBeanPostProcessor(properties);
    }

    @Bean
    @ConditionalOnWebApplication
    public FilterRegistrationBean<NPlusOneFilter> nPlusOneFilter() {
        FilterRegistrationBean<NPlusOneFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new NPlusOneFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Integer.MIN_VALUE);

        return registrationBean;
    }
}