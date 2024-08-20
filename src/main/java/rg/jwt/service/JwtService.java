package rg.jwt.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rg.jwt.dto.TokenDto;
import rg.jwt.entity.RefreshToken;
import rg.jwt.repository.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    
	//private final JwtTokenProvider jwtTokenProvider;
	
    private final RefreshTokenRepository refreshTokenRepository;
    
    @Transactional
    public void login(TokenDto tokenDto, String email) {
    	
        RefreshToken refreshToken = RefreshToken.builder().keyEmail(email).refreshToken(tokenDto.getRefreshToken()).build();
        String loginUserEmail = refreshToken.getKeyEmail();
        if (refreshTokenRepository.existsByKeyEmail(loginUserEmail)) {
            log.info("기존의 존재하는 refresh 토큰 삭제");
            refreshTokenRepository.deleteByKeyEmail(loginUserEmail);
        }
        refreshTokenRepository.save(refreshToken);

    }

}
