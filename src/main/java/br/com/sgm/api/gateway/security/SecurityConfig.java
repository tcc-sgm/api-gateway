package br.com.sgm.api.gateway.security;

import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .anonymous()
                .and()
                    .exceptionHandling().authenticationEntryPoint(((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                .and()
                    .addFilterAfter(new JwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/users/signin").permitAll()
                .antMatchers(HttpMethod.POST, "/cidadao/noticias").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated();


    }
}
