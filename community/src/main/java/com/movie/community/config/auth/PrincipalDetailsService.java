package com.movie.community.config.auth;

import com.movie.community.domain.Member;
import com.movie.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		Member memberEntity = memberRepository.findByAccount(account);

		if (memberEntity == null) {
			throw new UsernameNotFoundException("UsernameNotFoundException");
		}

		return new PrincipalDetails(memberEntity);
	}
}
