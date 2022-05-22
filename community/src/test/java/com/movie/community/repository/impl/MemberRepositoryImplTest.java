package com.movie.community.repository.impl;

import com.movie.community.controller.dto.request.MemberRequestDTO;
import com.movie.community.domain.Member;
import com.movie.community.domain.Rank;
import com.movie.community.domain.Role;
import com.movie.community.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional(readOnly = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class MemberRepositoryImplTest {
	@Autowired
	private EntityManager em;

	@Autowired
	private MemberRepository memberRepository;

	@Transactional
	@BeforeAll
	void generateData() {
		final String[] NAMES = {"김재준", "최승희", "김근호"};
		final String[] ACCOUNTS = {"rlawowns97", "shee73", "kkh332"};
		final String[] PASSWORDS = {"123", "456", "789"};
		final int[] AGES = {26, 26, 27};
		final String[] RANKS = {"입문자", "애기", "고인물"};
		final String[] ROLES = {"admin", "admin", "user"};

		for (int i = 0; i < NAMES.length; i++) {
			MemberRequestDTO requestDTO = new MemberRequestDTO();
			requestDTO.setName(NAMES[i]);
			requestDTO.setAccount(ACCOUNTS[i]);
			requestDTO.setPassword(PASSWORDS[i]);
			requestDTO.setAge(AGES[i]);
			requestDTO.setRank(RANKS[i]);
//			requestDTO.setRoles(ROLES[i]); // 문자열로 바뀜

			Member member = requestDTO.toEntity();
			memberRepository.save(member);
		}
	}

	@Test
	void checkMemberRankAndRole() {
		// given : 순서대로 저장되어 있다.
		final Rank[] RANKS = {Rank.BEGINNER, Rank.NEWBIE, Rank.SENIOR};
		final Role[] ROLES = {Role.ADMIN, Role.ADMIN, Role.USER};

		// when
		List<Member> members = memberRepository.findAll();

		// then
		int idx = 0;
		for (Member member : members) {
			assertEquals(RANKS[idx], member.getRank());
//			assertEquals(ROLES[idx], member.getRoles());
			idx++;
		}
	}

	@Transactional
	@Test
	void save() {
		// given
		final String NAME = "김광용";
		final int AGE = 26;
		final String RANK = "애기";
		final String ROLE = "user";
		final String ACCOUNT = "kkw97";
		final String PASSWORD = "123";

		MemberRequestDTO requestDTO = new MemberRequestDTO();
		requestDTO.setName(NAME);
		requestDTO.setAccount(ACCOUNT);
		requestDTO.setPassword(PASSWORD);
		requestDTO.setAge(AGE);
		requestDTO.setRank(RANK);
//		requestDTO.setRole(ROLE);

		// when
		Member member = requestDTO.toEntity();
		memberRepository.save(member);
		Long id = member.getId();

		em.flush();
		em.clear();

		// then
		Member findMember = memberRepository.findById(id);
		assertEquals(NAME, findMember.getName());
		assertEquals(AGE, findMember.getAge());
		assertEquals(RANK, findMember.getRank().getValue());
//		assertEquals(ROLE, findMember.getRole().getValue());
		assertEquals(ACCOUNT, findMember.getAccount());
		assertEquals(PASSWORD, findMember.getPassword());
	}
}