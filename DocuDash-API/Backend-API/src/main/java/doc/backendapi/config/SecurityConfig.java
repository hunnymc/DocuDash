package doc.backendapi.config;

import doc.backendapi.hadlers.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(argon2PasswordEncoder());
    }

//    @Bean
//    public HttpFirewall defaultHttpFirewall() {
//        return new DefaultHttpFirewall();
//    }

    @Bean
    public Argon2PasswordEncoder argon2PasswordEncoder() {
        return new Argon2PasswordEncoder(16, 29, 1, 16, 2);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*")); // replace with your domain
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.httpFirewall(defaultHttpFirewall());
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // CORS and CSRF
                .cors().and().csrf().disable()
                .headers().frameOptions().disable().and()
                .authorizeRequests()

                // Websocket
                .antMatchers("/ws/**").permitAll()
                .antMatchers("/api/kw2-websocket", "/api/kw2-websocket/**").permitAll()

                // Authentication
                .antMatchers(HttpMethod.POST,"/api/auth/login").permitAll()
                .antMatchers(HttpMethod.GET,"/api/auth/refresh").hasAnyRole("ADMIN", "USER", "MANAGER")
                .antMatchers(HttpMethod.POST,"/api/auth/user-info").hasAnyRole("ADMIN", "USER", "MANAGER")
                .antMatchers(HttpMethod.POST,"/api/auth/register").hasRole("ADMIN")

                // File store
                .antMatchers("/api/files/**").permitAll()

                // Graph Info
                .antMatchers("/api/approve/graph/**").permitAll()

                // Notification Service
                .antMatchers("/api/n/**").permitAll()

                // Document Service
                .antMatchers(HttpMethod.GET,"/api/doc/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/doc/newdocid").hasAnyRole("ADMIN", "USER", "MANAGER")
                .antMatchers(HttpMethod.GET,"/api/doc/user/{id}").hasAnyRole("ADMIN", "USER", "MANAGER")
                .antMatchers(HttpMethod.POST,"/api/doc/user/email").hasAnyRole("ADMIN", "USER", "MANAGER")
                .antMatchers(HttpMethod.GET, "/api/doc/{id}").hasAnyRole("ADMIN", "USER", "MANAGER")
                .antMatchers(HttpMethod.POST, "/api/doc/").hasAnyRole("ADMIN", "USER", "MANAGER")
                .antMatchers(HttpMethod.PATCH, "/api/doc/").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/doc/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/doc/check/{id}").hasAnyRole("ADMIN", "USER", "MANAGER")
                .antMatchers(HttpMethod.GET, "/api/doc/user").hasAnyRole("ADMIN", "USER", "MANAGER")

                // user document service
                .antMatchers(HttpMethod.POST, "/api/userdoc/").hasAnyRole("ADMIN", "USER", "MANAGER")

                // Approval Service
                .antMatchers(HttpMethod.GET, "/api/approve/mg/{managerId}").hasAnyRole("ADMIN", "USER", "MANAGER")
                .antMatchers(HttpMethod.GET, "/api/approve/info/{userId}").hasAnyRole("ADMIN", "USER", "MANAGER")
                .antMatchers(HttpMethod.GET, "/api/approve/doc/{documentId}").hasAnyRole("ADMIN", "USER", "MANAGER")
                .antMatchers(HttpMethod.GET, "/api/approve/info/all").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers(HttpMethod.POST, "/api/approve/admin/check-approve").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/approve/mg/check-approve").hasRole("MANAGER")
                .antMatchers(HttpMethod.POST, "/api/approve/add-manager").hasAnyRole("ADMIN", "USER", "MANAGER")
                .antMatchers(HttpMethod.POST, "/api/approve/sent-doc").hasAnyRole("ADMIN", "USER", "MANAGER")

                // User Service
                .antMatchers(HttpMethod.GET, "/api/user/all-users").hasAnyRole("ADMIN", "USER", "MANAGER")
                .antMatchers(HttpMethod.GET, "/api/user/check-token").hasAnyRole("ADMIN", "USER", "MANAGER")

                // Default
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler()).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}