package rg.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rg.jwt.util.JwtUtil;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class RefreshController {

    private final JwtUtil jwtService;
    
    @PreAuthorize("permitAll")
    @GetMapping("refresh")
    public ResponseEntity<String> validateRefreshToken(String refreshToken, 
    		HttpServletRequest request, HttpServletResponse response) {

        log.info("refresh controller 실행");
        String accessToken = jwtService.validateRefreshToken(refreshToken);

        if (accessToken == null) {
            log.info("RefreshController - Refresh Token이 만료.");
            //RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
            //response.sendRedirect("");
            return new ResponseEntity<String>("Refresh Token Expired", HttpStatus.UNAUTHORIZED);
        }

        log.info("RefreshController - Refresh Token이 유효.");
        //RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
        return new ResponseEntity<String>(accessToken, HttpStatus.OK);

    }
}
