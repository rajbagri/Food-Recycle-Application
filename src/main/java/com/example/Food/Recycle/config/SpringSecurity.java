//package config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//public class SpringSecurity {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authz -> authz
//                        .anyRequest().permitAll()
//                )
//                .httpBasic(Customizer.withDefaults());
//        http.csrf(csrf -> csrf.disable());
//        return http.build();
//    }
//}
