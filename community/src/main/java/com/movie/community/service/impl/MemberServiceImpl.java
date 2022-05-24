package com.movie.community.service.impl;

import com.movie.community.controller.dto.request.MemberRequestDTO;
import com.movie.community.controller.dto.response.MemberResponseDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import com.movie.community.domain.Member;
import com.movie.community.repository.MemberRepository;
import com.movie.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public List<MemberResponseDTO> getMembers() {
		List<Member> members = memberRepository.findAll();
		return members.stream()
				.map(MemberResponseDTO::new)
				.collect(Collectors.toList());
	}

	@Override
	public MemberResponseDTO getMember(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Member id can't be null");
		}

		Member member = memberRepository.findById(id);
		if (member == null) {
			throw new IllegalArgumentException("No such member id=" + id);
		}

		return new MemberResponseDTO(member);
	}

	@Transactional
	@Override
	public ResponseDTO join(MemberRequestDTO requestDTO) {
		if (requestDTO == null) {
			return new ResponseDTO("Invalid client request", 400);
		} else if (invalidAccount(requestDTO)) {
			return new ResponseDTO("Invalid member data", 400);
		}

		String password = requestDTO.getPassword();
		String encodedPassword = bCryptPasswordEncoder.encode(password);
		requestDTO.setPassword(encodedPassword);
		Member member = requestDTO.toEntity();
		memberRepository.save(member);

		return new ResponseDTO("success to join member", 200);
	}

	private boolean invalidAccount(MemberRequestDTO requestDTO) {
		String account = requestDTO.getAccount();
		String password = requestDTO.getPassword();

		return account == null || password == null || duplicated(account);
	}

	private boolean duplicated(String account) {
		List<Member> members = memberRepository.findAll();

		return members.stream()
				.anyMatch(member -> member.getAccount().equals(account));
	}

	@Transactional
	@Override
	public ResponseDTO updateMemberInfo(MemberRequestDTO requestDTO, Long id,
										Authentication authentication) {
		if (id == null || requestDTO == null) {
			return new ResponseDTO("Invalid client request", 400);
		} else if (!isJoinedUser(id)) {
			return new ResponseDTO("No such member existed id=" + id, 400);
		}

		String authenticatedAccount = authentication.getName();
		Member memberEntity = memberRepository.findById(id);
		String account = memberEntity.getAccount();
		log.info("authentication user account : {}", authenticatedAccount);
		log.info("user id account : {}", account);
		if (!authenticatedAccount.equals(account)) {
			return new ResponseDTO("No authentication", 400);
		}

		String password = requestDTO.getPassword();
		String encodedPassword = bCryptPasswordEncoder.encode(password);
		requestDTO.setPassword(encodedPassword);
		memberRepository.update(requestDTO, id);
		return new ResponseDTO("success to update member info", 200);
	}

	private boolean isJoinedUser(Long id) {
		Member member = memberRepository.findById(id);

		return member != null;
	}

	@Transactional
	@Override
	public ResponseDTO removeMemberInfo(Long id, Authentication authentication) {
		if (id == null) {
			return new ResponseDTO("Invalid client request", 400);
		} else if (!isJoinedUser(id)) {
			return new ResponseDTO("No such member existed id=" + id, 400);
		}

		String authenticatedAccount = authentication.getName();
		Member memberEntity = memberRepository.findById(id);
		String account = memberEntity.getAccount();
		log.info("authentication user account : {}", authenticatedAccount);
		log.info("user id account : {}", account);
		if (!authenticatedAccount.equals(account)) {
			return new ResponseDTO("No authentication", 400);
		}

		memberRepository.remove(memberEntity);
		return new ResponseDTO("success to remove member", 200);
	}
}
