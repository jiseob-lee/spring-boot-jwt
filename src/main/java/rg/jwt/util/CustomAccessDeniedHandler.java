package rg.jwt.util;

import java.io.IOException;

import org.springframework.http.HttpStatusCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.ErrorResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	
	private ObjectMapper mapper = new ObjectMapper();
	
    private String errorPage;

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {

    	
        String accept = httpServletRequest.getHeader("Accept");
        
        log.info("accept 1 : " + accept);
        
        if (accept.indexOf("application/json") > -1) {
        	ErrorResponse error = ErrorResponse.builder(e, HttpStatusCode.valueOf(403), "접근 권한이 없습니다.")
            	//.code("403")
                //.message("접근 권한이 없습니다.")
                .build();
                
            String result = mapper.writeValueAsString(error);
                
            httpServletResponse.setStatus(403);
            httpServletResponse.getWriter().write(result);
        
        } else {
        
	        String deniedUrl = errorPage + "?exception=" + e.getMessage();
	        httpServletResponse.sendRedirect(deniedUrl);
        }
    }

    public void setErrorPage(String errorPage){
        this.errorPage = errorPage;
    }
}
