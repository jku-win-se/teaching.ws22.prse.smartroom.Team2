package at.jku.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(auth ->
                auth
                        .mvcMatchers(HttpMethod.GET, "/rooms").permitAll()
                        .mvcMatchers(HttpMethod.POST, "/rooms").permitAll()
                        .mvcMatchers(HttpMethod.PUT, "/rooms").permitAll()
                        .mvcMatchers(HttpMethod.DELETE, "/rooms").permitAll()
                        .mvcMatchers(HttpMethod.GET, "/room").permitAll()
                        .mvcMatchers(HttpMethod.POST, "/room").permitAll()
                        .mvcMatchers(HttpMethod.PUT, "/room").permitAll()
                        .mvcMatchers(HttpMethod.DELETE, "/room").permitAll()
        ).httpBasic(withDefaults());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        return http.build();
    }

}
