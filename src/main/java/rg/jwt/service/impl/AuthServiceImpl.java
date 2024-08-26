package rg.jwt.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rg.jwt.dto.CustomUserInfoDto;
import rg.jwt.dto.LoginRequestDto;
import rg.jwt.dto.TokenDto;
import rg.jwt.entity.Member;
import rg.jwt.mapper.TokenMapper;
import rg.jwt.repository.MemberRepository;
import rg.jwt.repository.RefreshTokenRepository;
import rg.jwt.service.AuthService;
import rg.jwt.util.JwtUtil;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;
    private final TokenMapper tokenMapper;
    private final RefreshTokenRepository refreshTokenRepository;
    
    @Override
    @Transactional
    public TokenDto login(LoginRequestDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();
        Member member = memberRepository.findMemberByEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
        }

        // 암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
        if (!encoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        
        //log.info("member : " + member.toString());
        
        //String encodedPwd = encoder.encode(password);
        //log.info("rawPwd : " + password);
        //log.info("encodedPwd : " + encodedPwd);
        //log.info("password : " + member.getPassword());
        
        CustomUserInfoDto info = modelMapper.map(member, CustomUserInfoDto.class);
        //CustomUserInfoDto info = new CustomUserInfoDto(member.getMemberId(), member.getEmail(), member.getName(), member.getPassword(), member.getRoles());
        TokenDto accessToken = jwtUtil.createAccessToken(info);
        //TokenDto accessToken = jwtUtil.createAccessToken(member);
        
        return accessToken;
    }
    
    @Override
    @Transactional
    public void logout(String email) {
    	//tokenMapper.deleteRefreshToken(email);
    	refreshTokenRepository.deleteByKeyEmail(email);
    }
}

