package com.movie.community.repository;

import com.movie.community.controller.dto.request.MemberRequestDTO;
import com.movie.community.domain.Member;

import java.util.List;

public interface MemberRepository {
	public void save(Member member);

	public void update(MemberRequestDTO memberRequestDTO, Long id);

	public void remove(Member member);

	public Member findById(Long id);

	public List<Member> findAll();

	public Member findByAccount(String account);
}
