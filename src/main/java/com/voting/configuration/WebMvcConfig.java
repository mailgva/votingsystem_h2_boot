package com.voting.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voting.web.converter.DateTimeFormatters;
import com.voting.web.converter.IdToRestoConverter;
import com.voting.web.json.JacksonObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@EnableWebMvc
@Configuration
@ComponentScan("com.voting.web")
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ApplicationContext context;


    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(JacksonObjectMapper.getMapper());
        converter.setSupportedMediaTypes(
                Arrays.asList(
                        new MediaType("text", "plain", Charset.forName("UTF-8")),
                        new MediaType("text", "html", Charset.forName("UTF-8")),
                        new MediaType("application", "json", Charset.forName("UTF-8"))
                        ));
        converters.add(converter);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalArgumentResolver());
    }

    @Bean
    public FormattingConversionService conversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);
        conversionService.addConverter(new IdToRestoConverter());

        conversionService.addFormatter(new DateTimeFormatters.LocalTimeFormatter());
        conversionService.addFormatter(new DateTimeFormatters.LocalDateFormatter());
        conversionService.addFormatter(new DateTimeFormatters.LocalDateTimeFormatter());

        return conversionService;
    }

    @Bean
    public IdToRestoConverter idToRestoConverter() {
        return new IdToRestoConverter();
    }


    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        final String IMAGES_PATH = "file:///" + System.getenv("VOTING_ROOT").replace("\\","/") + "/images/";
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .addResourceLocations("/webjars/","classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/pictures/**")
                .addResourceLocations(IMAGES_PATH);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonObjectMapper.getMapper();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        registry.viewResolver(resolver);
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver=new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxUploadSize(5242880);
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        registry.addInterceptor(localeInterceptor).addPathPatterns("/*");
    }

    @Bean
    public LocaleResolver localeResolver () {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("ru"));
        return resolver;
    }

    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/app");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(5);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public JCacheManagerFactoryBean getSpringCacheManager() throws URISyntaxException {
        JCacheManagerFactoryBean jCacheCacheManager = new JCacheManagerFactoryBean();
        URI uri = new URI("classpath:cache/ehcache.xml");
        jCacheCacheManager.setCacheManagerUri(uri);
        return jCacheCacheManager;
    }



    // Configuring Jetty web server
    /*@Bean
    public ConfigurableServletWebServerFactory webServerFactory()
    {
        JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
        factory.setPort(8081);
        factory.setContextPath("/voting");
        return factory;
    }*/

}
