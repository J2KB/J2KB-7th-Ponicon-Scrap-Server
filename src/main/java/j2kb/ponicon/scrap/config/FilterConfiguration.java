package j2kb.ponicon.scrap.config;


import j2kb.ponicon.scrap.config.filter.AuthorizationFilter;
import j2kb.ponicon.scrap.config.filter.ExceptionCatchFilter;
import j2kb.ponicon.scrap.utils.ICookieService;
import j2kb.ponicon.scrap.utils.IJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class FilterConfiguration implements WebMvcConfigurer {

    private final IJwtService jwtService;
    private final ICookieService cookieService;

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilterRegistrationBean(){
        FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
        AuthorizationFilter authorizationFilter = new AuthorizationFilter(jwtService, cookieService);
        registrationBean.setFilter(authorizationFilter);
        registrationBean.addUrlPatterns("/auth/*");
        registrationBean.setOrder(2);
//        registrationBean.

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ExceptionCatchFilter> exceptionCatchFilterRegistrationBean(){
        FilterRegistrationBean<ExceptionCatchFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ExceptionCatchFilter());
        registrationBean.addUrlPatterns("/auth/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
