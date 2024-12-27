package dcc.tp2.gatewayservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebFluxSecurity
public class SecConfig {

    private RsaConfig rsaConfig;

    public SecConfig(RsaConfig rsaConfig) {
        this.rsaConfig = rsaConfig;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(auth -> auth
                        .pathMatchers("/COURSE-SERVICE/Course/**").hasAnyAuthority("SCOPE_Teacher", "SCOPE_Student")
                        .pathMatchers("/VIDEO-SERVICE/Video/**").hasAnyAuthority("SCOPE_Teacher", "SCOPE_Student")
                        .pathMatchers("/TEACHER-SERVICE/Teachers/email/{id}").permitAll()
                        .pathMatchers("/TEACHER-SERVICE/Teachers/**").hasAuthority("SCOPE_Teacher")
                        .pathMatchers("/STUDENT-SERVICE/Students/email/{id}").permitAll()
                        .pathMatchers("/STUDENT-SERVICE/Students/save").permitAll()
                        .pathMatchers("/STUDENT-SERVICE/Students/**").hasAuthority("SCOPE_Student")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt())
                .cors().configurationSource(corsConfigurationSource());

        return http.build();
    }


    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withPublicKey(rsaConfig.publicKey()).build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
