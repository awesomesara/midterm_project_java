package kg.alatoo.demooauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception{

        security
                .formLogin().
                loginPage("/login").permitAll().and()
                .formLogin()
                .failureForwardUrl("/register")
                .defaultSuccessUrl("/", true)
                .permitAll().and()
                .authorizeHttpRequests((requests) ->requests
                        .requestMatchers("/", "/register","/login").permitAll()
                        /*.requestMatchers("/admin").hasRole("ADMIN")*/
                        /*.requestMatchers("/borrowers/*","book_register").hasRole("ADMIN")
                         * not working, also 'hasAuthority("ADMIN")*/
                        .anyRequest().authenticated())
                .logout(LogoutConfigurer::permitAll);
        return security.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder(7);}

}