package rg.jwt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rg.jwt.service.PizzaService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class PizzaApiController {
	
	private final PizzaService pizzaService;
	

	//@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
	//@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_NORMAL')")
	@PreAuthorize("permitAll")
	@GetMapping("pizzasList")
    public ResponseEntity<List<Map<String, Object>>> getPizzasList() {
		
		log.info("#### getPizzasList ####");
		
		List<Map<String, Object>> list = pizzaService.selectPizzasList();
		
		return new ResponseEntity<List<Map<String, Object>>>(list, HttpStatus.OK);
	}
	
}
