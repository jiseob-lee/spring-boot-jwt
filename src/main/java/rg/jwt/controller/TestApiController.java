package rg.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class TestApiController {

	@GetMapping("test1")
    public ResponseEntity<String> test1(HttpServletRequest request) {
		if (request.getParameter("test1") == null) {
			log.info("이메일이 존재하지 않습니다. test1.");
			throw new UsernameNotFoundException("이메일이 존재하지 않습니다. test1.");
		}
		return new ResponseEntity<String>("test1", HttpStatus.OK);
	}
	
	@GetMapping("test2")
    public ResponseEntity<String> test2(HttpServletRequest request) {
		if (request.getParameter("test2") == null) {
			log.info("비밀번호가 일치하지 않습니다. test2.");
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다. test2.");
		}
		return new ResponseEntity<String>("test2", HttpStatus.OK);
	}

	@GetMapping("test3")
    public ResponseEntity<String> test3(HttpServletRequest request) {
		if (request.getParameter("test3") == null) {
			log.info("항목이 없습니다. test3.");
            throw new NullPointerException("항목이 없습니다. test3.");
		}
		return new ResponseEntity<String>("test3", HttpStatus.OK);
	}
}
