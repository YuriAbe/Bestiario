package br.com.yuriabe.Bestiario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.authorizeHttpRequests(auth -> auth
                                .requestMatchers("/", "/login", "/register", "/forgot-password", "/css/**", "/js/**", "/img/**").permitAll()
                                .requestMatchers("/jogos/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login") // página de login
                                                .loginProcessingUrl("/login") // POST do formulário
                                                .defaultSuccessUrl("/", true) // redireciona após o login OK
                                                .failureUrl("/login?error=true") // parâmetro de erro
                                )
                                .logout(logout -> logout
                                                .logoutUrl("/logout") // url para logout
                                                .logoutSuccessUrl("/login?logout=true") // após fazer o logout
                                                .invalidateHttpSession(true) // invalida a sessão
                                                .deleteCookies("JSESSIONID") // delete os cookies
                                ).exceptionHandling(ex -> ex.accessDeniedHandler(
                                                ((request, response, accessDeniedException) -> {
                                                        response.sendRedirect(request.getContextPath()
                                                                        + "/access-denied?from="
                                                                        + request.getRequestURI());
                                                })));

                return httpSecurity.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // Traz usuários padrões para o sistema
        // @Bean
        // public UserDetailsService inMemoryUsers(PasswordEncoder passwordEncoder) {
        //         UserDetails admin = User.withUsername("admin")
        //                         .password(passwordEncoder.encode("123456"))
        //                         .roles("ADMIN")
        //                         .build();
        //         UserDetails user = User.withUsername("user")
        //                         .password(passwordEncoder.encode("123456"))
        //                         .roles("USER")
        //                         .build();
        //         return new InMemoryUserDetailsManager(admin, user);
        // }
}