package rg.jwt.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rg.jwt.mapper.PizzaMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class PizzaService {
 
	private final PizzaMapper pizzaMapper;

    public List<Map<String, Object>> selectPizzasList() {
    	
    	log.info("#### selectPizzasList ####");
    	
        return pizzaMapper.selectPizzasList();
    }
}