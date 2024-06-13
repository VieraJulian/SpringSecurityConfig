package com.curso.springsecurity.security.config;

import com.curso.springsecurity.security.config.filter.JwtTokenValidator;
import com.curso.springsecurity.service.UserDetailsServiceImp;
import com.curso.springsecurity.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults()) //se usa cuando solo vas a logear con usuarios y contraseñas
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(http -> {
//                    http.requestMatchers(HttpMethod.GET, "/helloNoSecured").permitAll();
//                    http.requestMatchers(HttpMethod.GET, "/helloSecured").hasAuthority("READ");
//                    http.anyRequest().denyAll();
//                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImp userDetailsServiceImp) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsServiceImp);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        List userDetailsList = new ArrayList<>();
//
//        userDetailsList.add(User.withUsername("ADMIN")
//                                .password("1234")
//                                .roles("ADMIN")
//                                .authorities("READ", "CREATE", "UPDATE", "DELETE")
//                                .build());
//
//        userDetailsList.add(User.withUsername("INVITED")
//                .password("1234")
//                .roles("INVITED")
//                .authorities("READ")
//                .build());
//
//        userDetailsList.add(User.withUsername("USER")
//                .password("1234")
//                .roles("USER")
//                .authorities("UPDATE")
//                .build());
//
//        return new InMemoryUserDetailsManager(userDetailsList);
//    }
}
