package com.itkee.admin;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author rabbit
 */
@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean adminServlet() {
        //注解扫描上下文
        AnnotationConfigWebApplicationContext applicationContext
                = new AnnotationConfigWebApplicationContext();
        //base package
        applicationContext.scan("com.itkee");
        DispatcherServlet rest_dispatcherServlet
                = new DispatcherServlet(applicationContext);
        ServletRegistrationBean registrationBean
                = new ServletRegistrationBean(rest_dispatcherServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/admin/*");
        registrationBean.setName("admin");
        return registrationBean;
    }
}
