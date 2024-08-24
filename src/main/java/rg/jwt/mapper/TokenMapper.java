package rg.jwt.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapper {
	public void deleteRefreshToken(String email);
}
