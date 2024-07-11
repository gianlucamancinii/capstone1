package EpicodeFS0124.core.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
//QUESTA ANNOTAZIONE SERVE A COMUNICARE A SPRING CHE QUESTA  CLASSE Ã¨ UTILIZZATA PER CONFIGURARE LA SECURITY
@EnableWebSecurity()
@EnableMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {

    @Bean
    PasswordEncoder stdPasswordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    AuthTokenFilter authenticationJwtToken() {
        return new AuthTokenFilter();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       PasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                                authorize
                                        .requestMatchers("/api/users/login").permitAll()
                                        .requestMatchers("/api/users/registerAdmin").permitAll() // DA CANCELLARE DOPO AVER CREATO L'ADMIN
                                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll() //ENDPOINT DI REGISTRAZIONE APERTO A TUTTI
                                        .requestMatchers(HttpMethod.GET, "/api/**").authenticated() //TUTTE GLI ENDPOINTS DI TIPO GET SONO RICHIAMABILI SOLO SE L'UTENTE E AUTENTICATO
                                        .requestMatchers(HttpMethod.POST, "/api/**").authenticated() //TUTTE LE POST POSSONO ESSERE FATTE SOLO DALL'ADMIN
//                                        .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ADMIN") //TUTTE LE POST POSSONO ESSERE FATTE SOLO DALL'ADMIN
                                        .requestMatchers(HttpMethod.PATCH, "/api/users/{id}").authenticated() //SOLO UN UTENTE AUTENTICATO PUO MODIFICARE I SUOI DATI
                                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ADMIN") //TUTTE LE PUT POSSONO ESSERE FATTE SOLO DALL'ADMIN
                                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN") //TUTTE LE DELETE POSSONO ESSERE FATTE SOLO DALL'ADMIN
                                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll() //TUTTE LE DELETE POSSONO ESSERE FATTE SOLO DALL'ADMIN
                                        .requestMatchers("/**").authenticated() //TUTTO CIO CHE PUO ESSERE SFUGGITO RICHIEDE L'AUTENTICAZIONE (SERVE A GESTIRE EVENTUALI DIMENTICANZE)
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //COMUNICA ALLA FILTERCHAIN QUALE FILTRO UTILIZZARE, SENZA QUESTA RIGA DI CODICE IL FILTRO NON VIENE RICHIAMATO
                .addFilterBefore(authenticationJwtToken(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}

