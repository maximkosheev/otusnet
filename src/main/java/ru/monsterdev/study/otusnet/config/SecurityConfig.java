package ru.monsterdev.study.otusnet.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.monsterdev.study.otusnet.auth.AuthorizationFilter;
import ru.monsterdev.study.otusnet.models.AuthenticationInfo;
import ru.monsterdev.study.otusnet.models.SocialUser;
import ru.monsterdev.study.otusnet.services.JwtService;
import ru.monsterdev.study.otusnet.services.UserService;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SecurityProperties securityProperties;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);

        AuthenticationManager authenticationManager = authManagerBuilder.build();
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/login", "/users/register").permitAll()
                .antMatchers("/test/unsecured").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(getAuthenticationFilter(authenticationManager))
                .addFilter(new AuthorizationFilter(authenticationManager, securityProperties))
                .authenticationManager(authenticationManager)
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    protected UsernamePasswordAuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        final UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter(authenticationManager);
        filter.setFilterProcessesUrl("/auth/login");
        filter.setAuthenticationSuccessHandler((request, response, authentication) ->
        {
            SocialUser user = (SocialUser) authentication.getPrincipal();
            AuthenticationInfo authenticationInfo = new AuthenticationInfo(user.getLogin());
            objectMapper.writeValue(response.getWriter(), jwtService.buildAuthenticationToken(authenticationInfo));
        });
        return filter;
    }
}
