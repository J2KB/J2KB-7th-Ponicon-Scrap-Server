package j2kb.ponicon.scrap.config;


import j2kb.ponicon.scrap.filter.AuthorizationFilter;
import j2kb.ponicon.scrap.utils.CookieService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilterConfiguration implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilterRegistrationBean(){
        FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthorizationFilter());
        registrationBean.addUrlPatterns("/auth/*");

        return registrationBean;
    }
}
