package com.movie.community.repository.impl;

import com.movie.community.controller.dto.request.MemberRequestDTO;
import com.movie.community.domain.Member;
import com.movie.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {
	private final EntityManager em;

	@Transactional
	@Override
	public void save(Member member) {
		em.persist(member);
	}

	@Transactional
	@Override
	public void update(MemberRequestDTO memberRequestDTO, Long id) {
		Member member = em.find(Member.class, id);
		member.update(memberRequestDTO);
	}

	@Transactional
	@Override
	public void remove(Member member) {
		em.remove(member);
	}

	@Override
	public Member findById(Long id) {
		return em.find(Member.class, id);
	}

	@Override
	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class)
				.getResultList();
	}

	@Override
	public Member findByAccount(String account) {
		return em.createQuery("select m from Member m where m.account=:account", Member.class)
				.setParameter("account", account)
				.getSingleResult();
	}
}
