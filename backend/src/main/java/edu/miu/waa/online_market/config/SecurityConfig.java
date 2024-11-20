package edu.miu.waa.online_market.config;
import edu.miu.waa.online_market.filter.JwtFilter;
import edu.miu.waa.online_market.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import edu.miu.waa.online_market.service.CustomUserDetailsService;
import edu.miu.waa.online_market.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import edu.miu.waa.online_market.filter.JwtFilter;
import edu.miu.waa.online_market.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfig{
    private final JwtFilter jwtFilter;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                       // .requestMatchers("/api/v1/posts/**").permitAll()
                       // .requestMatchers("/api/v1/comments/**").permitAll()
                 //       .requestMatchers("/api/v1/reviews/**").permitAll()
                        //.requestMatchers("/api/v1/categories/**").permitAll()
                        //.requestMatchers("/api/v1/products/**").permitAll()
                       // .requestMatchers("/api/v1/users/**").permitAll()
                        .requestMatchers("/api/v1/users/register").permitAll()
                        .requestMatchers("/api/v1/authenticate/**").permitAll()
                        //      .requestMatchers("/api/v1/products").hasAnyAuthority(roles.toArray(new String[0])) // Use dynamic authorities
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Set stateless session policy
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
