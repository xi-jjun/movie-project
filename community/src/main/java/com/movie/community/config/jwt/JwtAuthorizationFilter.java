package com.movie.community.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.movie.community.config.auth.PrincipalDetails;
import com.movie.community.domain.Member;
import com.movie.community.repository.MemberRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	private final JwtProperties jwtProperties;
	private final MemberRepository memberRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository, JwtProperties jwtProperties) {
		super(authenticationManager);
		this.memberRepository = memberRepository;
		this.jwtProperties = jwtProperties;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String jwtHeader = request.getHeader("Authorization");

		if (jwtHeader == null || !jwtHeader.startsWith(jwtProperties.getTOKEN_PREFIX())) {
			chain.doFilter(request, response);
			return;
		}

		String jwtToken = request.getHeader("Authorization").replace(jwtProperties.getTOKEN_PREFIX(), "");

		String account = JWT.require(Algorithm.HMAC512(jwtProperties.getHashKey())).build().verify(jwtToken)
				.getClaim("account")
				.asString();

		if (account != null) {
			Member memberEntity = memberRepository.findByAccount(account);

			PrincipalDetails principalDetails = new PrincipalDetails(memberEntity);
			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
	}
}
