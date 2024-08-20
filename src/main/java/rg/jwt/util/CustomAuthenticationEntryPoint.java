package rg.jwt.util;

import java.io.IOException;

import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.ErrorResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private ObjectMapper mapper = new ObjectMapper();
	
	
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, 
    		AuthenticationException authException) throws IOException, ServletException {
    	
        String accept = request.getHeader("Accept");
        
        log.info("accept : " + accept);
        
        //if ("application/json".equals(accept)) {
        if (accept.indexOf("application/json") > -1) {
        	ErrorResponse error = ErrorResponse.builder(authException, HttpStatusCode.valueOf(401), "인증이 필요합니다.")
            	//.code("401")
                //.message("인증이 필요합니다.")
                .build();
                
            String result = mapper.writeValueAsString(error);
                
            response.setStatus(401);
            response.getWriter().write(result);
            
        } else {
	        //log.error("가입되지 않은 사용자 접근");
	        response.sendRedirect("/login");
        }
    }
}
