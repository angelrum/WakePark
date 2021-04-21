package ru.project.wakepark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import ru.project.wakepark.service.UserService;
import ru.project.wakepark.util.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    public UserService userService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
//        http.httpBasic()
//                .and()
//                .authorizeRequests().antMatchers("/")
//                .permitAll()
//                .and()
//                .authorizeRequests().antMatchers("/users").hasRole("ADMIN")
//                .anyRequest().authenticated();
        http
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/users").hasRole("ADMIN")
                    .antMatchers("/login", "/users").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/main", true)
                    .failureUrl("/login?error=true")
                    //.successHandler(new AuthenticationSuccessHandler())
                    .loginProcessingUrl("/perform_login")
                .and()
                    .logout()
                    .logoutSuccessUrl("/login").deleteCookies("JSESSIONID");
    }
    //
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/webjars/**", "/ws-endpoint/**");
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());

//        auth.inMemoryAuthentication()
//                .withUser("user").password(passwordEncoder().encode("pass")).roles("USER")
//                .and()
//                .withUser("admin").password(passwordEncoder().encode("pass")).roles("ADMIN");
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.userService);
        authProvider.setPasswordEncoder(this.passwordEncoder);
        return authProvider;
    }

}
