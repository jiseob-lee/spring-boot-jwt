package rg.jwt.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rg.jwt.service.CustomUserDetailsService;
import rg.jwt.util.JwtUtil;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter { // OncePerRequestFilter -> 한 번 실행 보장

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    /**
     * JWT 토큰 검증 필터 수행
     */
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    	String authorizationHeader = request.getHeader("Authorization");

    	//log.info("authorizationHeader : " + authorizationHeader);
    	//log.info("RequestURI : " + request.getRequestURI());
    	
    	if (!request.getRequestURI().equals("/api/v1/auth/login")) {
	        //JWT가 헤더에 있는 경우
	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            
	        	String token = authorizationHeader.substring(7);
	        	
	        	//log.info("token : " + token);
	            
	            //JWT 유효성 검증
	            if (jwtUtil.validateToken(token)) {
	                String userId = jwtUtil.getUserId(token);
	
	                //HttpSession session = request.getSession();
	                //session.setAttribute("userId", userId);
	                
	                log.info("userId 222 : " + userId);
	                
	                //유저와 토큰 일치 시 userDetails 생성
	                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId.toString());
	
	                if (userDetails != null) {
	                    //UserDetsils, Password, Role -> 접근권한 인증 Token 생성
	                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
	                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	
	                    log.info("userDetails != null");
	                    log.info("Username : " + userDetails.getUsername());
	                    log.info("getAuthorities : " + userDetails.getAuthorities());
	                    
	                    //현재 Request의 Security Context에 접근권한 설정
	                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	                }
	            }
	        }
    	}

        filterChain.doFilter(request, response); // 다음 필터로 넘기기
    }
}

