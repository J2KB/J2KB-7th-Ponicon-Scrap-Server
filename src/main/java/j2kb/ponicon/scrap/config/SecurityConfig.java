package j2kb.ponicon.scrap.config;

import j2kb.ponicon.scrap.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encoePwd() {
        return  new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/category/**", "/data/**").authenticated() // user가 접근할 수 있는 page
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 후에 관리자 진행할 때 관리자만 접근 가능
                .anyRequest().permitAll() // 누구나 접근 가능
                .and()
                .formLogin() //로그인에 관한 설정
                //.loginPage("/loginForm") // 로그인 페이지 링크 설정
                //.loginProcessingUrl("/login") // 로그인 주소가 호출되면 시큐리티가 낚아채서 대신 로그인 진행(자체로그인이 아니라 시큐리티 로그인 사용)
                //.defaultSuccessUrl("/") //로그인 성공 후 리다이렉트할 주소
                .and();
                //.oauth2Login()  // oauth 로그인 설정 하는 페이지
                //.loginPage("/loginForm")
                //.userInfoEndpoint();
                //.userService(userService);
    }
}
