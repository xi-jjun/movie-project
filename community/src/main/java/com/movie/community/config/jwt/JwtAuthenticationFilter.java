package com.movie.community.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.community.config.auth.PrincipalDetails;
import com.movie.community.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Setter
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final JwtProperties jwtProperties;
	private final AuthenticationManager authenticationManager;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		// login 시도
		ObjectMapper objectMapper = new ObjectMapper();
		try {
//			User user = objectMapper.readValue(request.getInputStream(), User.class);
			Member member = objectMapper.readValue(request.getInputStream(), Member.class);
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(member.getAccount(), member.getPassword());

			Authentication authentication = authenticationManager.authenticate(authenticationToken);

			return authentication;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// login 성공했을 때 처리해주는 method
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

		// JWT token 만들어주기
		String jwtToken = JWT.create()
				.withSubject(jwtProperties.getSubject())
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpireTime()))
				.withClaim("id", principalDetails.getMember().getId())
				.withClaim("account", principalDetails.getMember().getAccount())
				.sign(Algorithm.HMAC512(jwtProperties.getHashKey()));

		response.addHeader("Authorization", jwtProperties.getTOKEN_PREFIX() + jwtToken);
	}
}
