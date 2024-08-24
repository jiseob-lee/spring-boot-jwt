package rg.jwt.util;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import rg.jwt.dto.CustomUserInfoDto;
import rg.jwt.dto.TokenDto;
import rg.jwt.entity.RefreshToken;
import rg.jwt.entity.UserRoles;
import rg.jwt.repository.RefreshTokenRepository;

/**
 * [JWT 관련 메서드를 제공하는 클래스]
 */
@Slf4j
@Component
public class JwtUtil {

    private final Key key;
    private final long accessTokenExpTime;
    private final long refreshExpireTime;
    
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration_time}") long accessTokenExpTime,
            @Value("${jwt.refresh_expiration_time}") long refreshExpireTime
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
        this.refreshExpireTime = refreshExpireTime;
    }

    /**
     * Access Token 생성
     * @param member
     * @return Access Token String
     */
    public TokenDto createAccessToken(CustomUserInfoDto member) {
        return createToken(member, accessTokenExpTime, refreshExpireTime);
    }


    /**
     * JWT 생성
     * @param member
     * @param expireTime
     * @return JWT String
     */
    private TokenDto createToken(CustomUserInfoDto member, long expireTime, long refreshExpireTime) {
    	
        Claims claims = Jwts.claims();
        claims.put("memberId", member.getMemberId());
        claims.put("email", member.getEmail());
        //claims.put("role", member.getRole());

        //log.info("getRole : " + member.getRole().toString());
        log.info("getRoles : " + member.getRoles().toString());
        List<UserRoles> userRoles = member.getRoles();
        List<RoleType> roleTypes = new ArrayList<>();
        if (userRoles != null && userRoles.size() > 0) {
        	
        	for (int i=0; i < userRoles.size(); i++) {
        		log.info("UserRole : " + userRoles.get(i).getUserRole());
        		roleTypes.add(userRoles.get(i).getUserRole());
        	}
        	log.info(roleTypes.toString());
        	claims.put("role", roleTypes);
        }
        
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);


        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        //long now1 = (new Date()).getTime();
        ZonedDateTime now1 = ZonedDateTime.now();
        ZonedDateTime tokenValidity1 = now.plusSeconds(refreshExpireTime);

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now1.toInstant()))
                .setExpiration(Date.from(tokenValidity1.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        
        // Refresh Token 생성
        //String refreshToken = Jwts.builder()
        		//.setClaims(claims)
                //.setExpiration(new Date(now1 + 86400000))
                //.signWith(key, SignatureAlgorithm.HS256)
                //.compact();
        
        return TokenDto.builder()        		
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    /**
     * Token에서 User ID 추출
     * @param token
     * @return User ID
     */
    public String getUserId(String token) {
        return String.valueOf(parseClaims(token).get("memberId"));
    }


    /**
     * JWT 검증
     * @param token
     * @return IsValidate
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            log.info("token valid.");
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }


    /**
     * JWT Claims 추출
     * @param accessToken
     * @return JWT Claims
     */
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }


    
    public String validateRefreshToken(String refreshToken) {


        // refresh 객체에서 refreshToken 추출
        //String refreshToken = refreshTokenObj.getRefreshToken();

    	Optional<RefreshToken> refreshTokenCheck = refreshTokenRepository.findByRefreshToken(refreshToken);

    	if (!refreshTokenCheck.isPresent()) {
    		return null;
    	}
    	
        try {
            // 검증
            //Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(refreshToken);
            Claims claims = parseClaims(refreshToken);

            //refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성합니다.
            if (!claims.getExpiration().before(new Date())) {
                return recreationAccessToken(claims.get("memberId").toString(), 
                		claims.get("email").toString(), claims.get("roles"));
            }
        }catch (Exception e) {

            //refresh 토큰이 만료되었을 경우, 로그인이 필요합니다.
            return null;

        }

        return null;
    }
    
    
    public String recreationAccessToken(String memberId, String email, Object roles){

        //Claims claims = Jwts.claims().setSubject(userEmail); // JWT payload 에 저장되는 정보단위
        //claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
    	
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId);
        claims.put("email", email);
        claims.put("role", roles);

        //Date now = new Date();

        
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(accessTokenExpTime);


        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        //Access Token
        //String accessToken = Jwts.builder()
                //.setClaims(claims) // 정보 저장
                //.setIssuedAt(now) // 토큰 발행 시간 정보
                //.setExpiration(new Date(now.getTime() + accessTokenExpTime)) // set Expire Time
                //.signWith(key, SignatureAlgorithm.HS256)
                // signature 에 들어갈 secret값 세팅
                //.compact();

        return accessToken;
    }
    
    public void checkClaims(String token) {
        
    	Claims claims = parseClaims(token);

        log.info("memberId : " + claims.get("memberId").toString()); 
        log.info("email : " + claims.get("email").toString());
        log.info("role : " + claims.get("role"));
            	
    }
    
}
