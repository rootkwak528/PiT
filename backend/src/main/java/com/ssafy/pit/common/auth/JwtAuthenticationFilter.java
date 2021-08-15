package com.ssafy.pit.common.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafy.pit.common.util.JwtTokenUtil;
import com.ssafy.pit.entity.User;
import com.ssafy.pit.service.UserService;

// 요청헤더에 jwt 토큰이 있는 경우, 토큰 검증 및 인증 처리 로직 정의
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
	
	private UserService userService;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
		super(authenticationManager);
		this.userService = userService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
				throws ServletException, IOException	{
		
		// request header에 Authorization(key) : Bearer Token(Value) 형태로 날아옴
		String header = request.getHeader(JwtTokenUtil.HEADER_STRING);
		System.out.println("$ " + header);
		
		
		// 헤더가 Bearer로 시작하지 않거나 null인 경우 filter 적용
		if(header == null || !header.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		try {
			// authorization 수행
			Authentication authentication = getAuthentication(request);
			// jwt 토큰으로부터 획득한 인증 정보(authentication) 설정
			SecurityContextHolder.getContext().setAuthentication(authentication);;
		}
		catch (Exception ex) {
			filterChain.doFilter(request, response);
			return;
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	@Transactional(readOnly = true)
	public Authentication getAuthentication(HttpServletRequest request) throws Exception {
		// String token = Bearer token(header.payload.signature)
		// 요청 헤더에 Authorization 키값에 jwt 토큰이 포함된 경우에만, 토큰 검증 및 인증 처리 로직 실행.
		String token = request.getHeader(JwtTokenUtil.HEADER_STRING);
		System.out.println("> " + token.length() + "  " + token);
		
		if (token != null) {
			System.out.println(">> " + token.length() + "  " + token);
			
			// token을 parse, validate 수행
			JWTVerifier verifier = JwtTokenUtil.getVerifier();
			System.out.println(">>> " + token.length() + "  " + token);
			
			JwtTokenUtil.handleError(verifier, token);
			System.out.println(">>>> " + token.length() + "  " + token);
			
			// token decoding
			DecodedJWT decodedJWT = verifier.verify(token.replace(JwtTokenUtil.TOKEN_PREFIX, ""));
			String userEmail = decodedJWT.getSubject();
			System.out.println(">>>>> " + userEmail);
			
			
			// JWT 토큰에서 얻은 유저이메일로 DB에서 그 유저가 있는지 확인
			if(userEmail != null) {
				User user = userService.getUserByUserEmail(userEmail);
				if(user != null) {
					// 식별된 정상 유저인 경우, 요청 context 내에서 참조가능한 인증정보(jwtAuthentication) 생성
					PitUserDetails pitUserDetails = new PitUserDetails(user);
					UsernamePasswordAuthenticationToken jwtAuthentication = new UsernamePasswordAuthenticationToken(userEmail, null, pitUserDetails.getAuthorities());
					jwtAuthentication.setDetails(pitUserDetails);
					return jwtAuthentication;
				}		
			}
			return null;
		}
		return null;
	}
}
