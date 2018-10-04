package com.eai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.spel.spi.EvaluationContextExtension;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.eai.dto.Constants;
import com.eai.dto.MessageResponse;
import com.eai.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private String admin = "ADMIN";
    private String user = "USER";
	
	@Autowired
    private UserService userService;
		
	@Value("${server.session-timeout}")
    private Integer maxInactiveIntervalInSeconds;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            	.antMatchers("/detail/{\\d+}").hasAnyRole(admin, user)
            	.antMatchers("/profile").hasAnyRole(admin, user)
            	.antMatchers("/index").hasAnyRole(admin, user)
            	.antMatchers("/eainformation").hasAnyRole(admin)
        		.antMatchers("/systemparameters", "/searchsystemparameters").hasAnyRole(admin)
        		.antMatchers("/logerror", "/searchlogerror").hasAnyRole(admin);
        
        http
        	.authorizeRequests()
				.antMatchers("/signup", "/registration", "/langEN", "/langES").permitAll()
        		.anyRequest().authenticated()
				.and()
            .formLogin()
                .loginPage("/signin").permitAll()
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailureHandler());
        
        http
            .logout()
                .permitAll()
                .logoutSuccessUrl("/signin")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/404");
        
        http
            .sessionManagement()
            	.invalidSessionUrl("/invalid")
            	.maximumSessions(100)
            	.expiredUrl("/expired");
        
        http
        	.csrf();
           
    }

    public AuthenticationSuccessHandler loginSuccessHandler() {
        return (request, response, authentication) -> {
        	request.getSession().setAttribute("user", authentication.getName());
        	request.getSession().setMaxInactiveInterval(maxInactiveIntervalInSeconds*60);
        	response.sendRedirect("./index");
        };
    }

    public AuthenticationFailureHandler loginFailureHandler() {
        return (request, response, exception) -> {
        	MessageResponse message =  new MessageResponse();
        	message.setStatus(Constants.SINGINFAILURE.val());
        	
            request.getSession().setAttribute(Constants.MESSAGESRESPONSE.val(), message);
            response.sendRedirect("./signin");
        };
    }

    @Bean
    public EvaluationContextExtension securityExtension() {
        return new EvaluationContextExtension() {
            @Override
            public String getExtensionId() {
                return "security";
            }

            @Override
            public Object getRootObject() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new SecurityExpressionRoot(authentication) {};
            }
        };
    }
}
