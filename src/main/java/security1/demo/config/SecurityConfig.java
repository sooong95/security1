package security1.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터('SecurityConfig')가 스프링 필터 체인에 등록이 된다.
public class SecurityConfig {

    @Bean // 해당 리턴되는 오브젝트를 IoC로 등록해준다.
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").authenticated() // user 라는 url 로 들어오면 인증이 필요하다.
                        .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") // manager 로 들어오면 manager 인증 또는 admin 인증이 필요하다는 뜻.
                        .requestMatchers("/admin/**").hasRole("ADMIN") // admin 은 admin 권한이 있는 이용자만 접근 가능.
                        .anyRequest().permitAll() // 그리고 나머지 url 은 권환을 허용.
                );
        http.formLogin(form -> form
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // login 주소가 호출이 되면 시큐리티가 낚아채서 대시 로그인을 진행 해준다.
                .defaultSuccessUrl("/") // 로그인 성공시 홈으로
        );
        return http.build();
    }

}
