package com.itkee.core.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.itkee.core.annotation.CurrentUserHandlerMethodArgResolver;
import com.itkee.core.filter.JwtFilter;
import com.itkee.core.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rabbit
 */
@Configuration
public class HttpConverterConfig extends WebMvcConfigurationSupport {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private long expire;

    @Value("${jwt.authorised-urls}")
    private String[] authorisedUrls;

    @Bean
    public JwtHelper jwtHelperBean() {
        return new JwtHelper(secret, expire);
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                // 是否输出值为null的字段,默认为false,我们将它打开
                SerializerFeature.WriteMapNullValue,
                // 将Collection类型字段的字段空值输出为[]
                SerializerFeature.WriteNullListAsEmpty,
                // 将字符串类型字段的空值输出为空字符串
                SerializerFeature.WriteNullStringAsEmpty,
                // 将数值类型字段的空值输出为0
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteNonStringKeyAsString,
                // 禁用循环引用
                SerializerFeature.DisableCircularReferenceDetect
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);

        converters.add(fastConverter);

        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converters.add(stringHttpMessageConverter);
    }


    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        //注册@CurrentUser注解的实现类
        argumentResolvers.add(new CurrentUserHandlerMethodArgResolver());
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(filter())
                .addPathPatterns("/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/static/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Bean
    public JwtFilter filter(){
        return new JwtFilter(jwtHelperBean(), authorisedUrls);
    }
}
