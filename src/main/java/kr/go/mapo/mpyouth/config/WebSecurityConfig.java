package kr.go.mapo.mpyouth.config;


import kr.go.mapo.mpyouth.security.jwt.AuthEntryPointJwt;
import kr.go.mapo.mpyouth.security.jwt.AuthTokenFilter;
import kr.go.mapo.mpyouth.security.jwt.CustomAuthenticationProvider;
import kr.go.mapo.mpyouth.security.jwt.ExceptionHandlerFilter;
import kr.go.mapo.mpyouth.service.RoleHierarchyService;
import kr.go.mapo.mpyouth.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleHierarchyService roleHierarchyService;

    @Autowired
    private ExceptionHandlerFilter exceptionHandlerFilter;


    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

//	@Override
//	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	}

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//        web.ignoring().antMatchers("/v2/api-docs","cpnfigure/ui",
//                "/swagger-resoruces","configuration/security","/swagger-ui.html","/webjars/**","/swagger/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()

                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/test/**").hasAnyAuthority("ROLE_ADMIN","ROLE_MANAGER")
                .antMatchers(HttpMethod.GET,"/file/**").permitAll()
                .antMatchers("/api/user/**").permitAll()
                .antMatchers("/api/program/**").permitAll()
                .antMatchers("/api/organization/**").permitAll()
                .antMatchers("/api/category/**").permitAll()
                .antMatchers("/api/donation/**").permitAll()
                .mvcMatchers("/v3/**",
                        "/configuration/**",
                        "/swagger*/**",
                        "/webjars/**",
                        "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler);

//        권한 필터 예시
//                .antMatchers(HttpMethod.POST,"/api/**").authenticated() // /api 하위 모든 POST요청
//                .antMatchers(HttpMethod.PUT,"/api/**").authenticated() // /api 하위 모든 PUT요청
//                .antMatchers(HttpMethod.DELETE,"/api/**").authenticated() // /api 하위 모든 DELETE요청
//                .antMatchers("/manage/**").hasAuthority("ROLE_ADMIN") // /manage 하위 모든 요청은 관리자만
//                .antMatchers("/","/login","/profile","/api/**").permitAll() // 이외 접근가능한 경로

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter, AuthTokenFilter.class);
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy(){
        String allHierarchy = roleHierarchyService.findAllHierarchy();
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(allHierarchy);
        return roleHierarchy;
    }
    @Bean
    public AccessDecisionVoter<? extends Object> roleVoter(){
        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
        return roleHierarchyVoter;
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder);
    }




}
