package rg.jwt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
	
	List<String> selectBoardList();
}
