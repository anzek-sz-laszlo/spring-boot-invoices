/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author User
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // így állítsuk be a Basic Authentication-t, ez a használata:
        // 1.                http.authorizeRequests( authorizeRequests -> authorizeRequests.anyRequest().authenticated() )
        //                    .httpBasic();         
        //                return http.build();
        // 2. return http.httpBasic(Customizer.withDefaults()).build();
        // 3. RESTFul esetében:
        http
            .csrf(csrf -> csrf.disable()) // Csak tesztelésre! Production környezetben fontos a CSRF védelem!
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.GET, "/api/partners/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/partners/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/partners/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/partners/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/cimadat/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/cimadat/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/cimadat/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/cimadat/**").authenticated()                    
                .anyRequest().permitAll()
            )                
            .httpBasic(withDefaults());
        return http.build();                        
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // UserBuilder felhasznalok = User.builder();
        UserDetails user = User.builder().username("anzek")
                                         .password(passwordEncoder.encode("176Backend"))
                                         .roles("USER") // ez egy USER szerepkör
                                         .authorities("user") // ez pedig egy USER típusú jogoslutság                                 
                                         .build();
        //        UserDetails admin = User.builder().username("anzek")
        //                                         .password(passwordEncoder.encode("176backend"))
        //                                         .authorities("admin")                                    
        //                                         .build();
        return new InMemoryUserDetailsManager(user); // stb, felsorolhatunk még jónéhány hasonlót
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }    
}
