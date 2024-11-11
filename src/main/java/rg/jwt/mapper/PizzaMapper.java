package rg.jwt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PizzaMapper {
	
	List<Map<String, Object>> selectPizzasList();
}
