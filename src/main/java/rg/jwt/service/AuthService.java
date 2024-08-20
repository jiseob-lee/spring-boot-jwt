package rg.jwt.service;

import rg.jwt.dto.LoginRequestDto;
import rg.jwt.dto.TokenDto;

public interface AuthService {
	public TokenDto login(LoginRequestDto dto);
}
