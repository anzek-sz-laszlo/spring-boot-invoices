/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.anzek.backend.invoce.security;


import hu.anzek.backend.invoce.datalayer.model.InvUser;
import hu.anzek.backend.invoce.datalayer.repository.InvUserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author User
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final InvUserRepo userRepository;

    public SecurityConfig(InvUserRepo userRepository) {
        this.userRepository = userRepository;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) // Csak tesztelésre! Production környezetben fontos a CSRF védelem!
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login","/applogin", "/resources/**", "/static/**", "/css/**", "/js/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
            )                
            //.authorizeHttpRequests(authz -> authz
            //    //.requestMatchers("/login","/applogin", "/resources/**", "/static/**", "/css/**", "/js/**").permitAll()    
            //    // RESTful API védelme (lehet, hogy elég lenne ennyi is .requestMatchers("/api/**").authenticated() :    
            //    .requestMatchers(HttpMethod.GET, "/api/partners/**").authenticated()
            //    .requestMatchers(HttpMethod.POST, "/api/partners/**").authenticated()
            //    .requestMatchers(HttpMethod.PUT, "/api/partners/**").authenticated()
            //    .requestMatchers(HttpMethod.DELETE, "/api/partners/**").authenticated()
            //    .requestMatchers(HttpMethod.GET, "/api/cimadat/**").authenticated()
            //    .requestMatchers(HttpMethod.POST, "/api/cimadat/**").authenticated()
            //    .requestMatchers(HttpMethod.PUT, "/api/cimadat/**").authenticated()
            //    .requestMatchers(HttpMethod.DELETE, "/api/cimadat/**").authenticated()       
            //    .requestMatchers(HttpMethod.DELETE, "/api/megrendelesek/**").authenticated()  
            //    .requestMatchers(HttpMethod.DELETE, "/api/szamlak/**").authenticated()  
            //    .anyRequest().permitAll()
            //)   
            // a beléptető "login.html" form összemeppelése a beléptető URL-el:    
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )                
            // az alapvető hitelesítés RESTful API-k számára    
            .httpBasic(withDefaults());
        return http.build();                        
    }

    //    @Bean
    //    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    //        UserDetails user = User.builder().username("anzek")
    //                                         .password(passwordEncoder.encode("176Backend"))
    //                                         .roles("USER") // ez egy USER szerepkör
    //                                         .authorities("user") // ez pedig egy USER típusú jogoslutság                                 
    //                                         .build();
    //        return new InMemoryUserDetailsManager(user); // stb, felsorolhatunk még jónéhány hasonlót
    //    }
    //
    //    @Bean
    //    public UserDetailsService userDetailsService() {
    //        return new UserDetailsService() {
    //                                            @Override
    //                                            public UserDetails loadUserByUsername(String username) 
    //                                                                                   throws UsernameNotFoundException {
    //                                                InvUser user = userRepository.findByUserName(username);
    //                                                if (user == null) {
    //                                                    throw new UsernameNotFoundException("User not found");
    //                                                }
    //                                                return user;
    //                                             }
    //                                        };
    //    }
    //
    // FONTOS:
    // Ez a három Bean (a fenti két kikommentelt is és az alábbi is) nem valamiféle adatot ad vissza, hanem egy metódus implementációt! 
    // amikor erre hivatkozunk "userDetailsService()" akkor -> az absztrakt
    // emlékszünk még a másik projektben a funkcionális interfészeknél vettük
    // a VevoCikk<T> generikus interfészt például a Cikk-, illetve Vevo törzs implementálásánál alkalmaztuk!
    //    @Bean
    //    public UserDetailsService userDetailsServiceSimple() {
    //        return (username)-> {
    //                                InvUser user = userRepository.findByUserName(username);
    //                                if (user == null) {
    //                                    throw new UsernameNotFoundException("Nem talalhato ilyen user");
    //                                }
    //                                return user;
    //                            };
    //    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        return (username) -> {
            InvUser user = userRepository.findByUserName(username);            
            if (user == null) {
                throw new UsernameNotFoundException("Nem található ilyen user: " + username);
            }
            // Megadjuk a megkapott felhatalmazások listáját az egyes rendszer-összetvő funkciók eléréséhez:
            User userSpring = new User(user.getUsername(), user.getPassword(), user.getAuthorities());           
            return userSpring;
        };
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }    
}
