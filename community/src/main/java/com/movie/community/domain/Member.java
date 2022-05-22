package com.movie.community.domain;

import com.movie.community.controller.dto.request.MemberRequestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member {
	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private String account;

	@Column
	private String password;

	@Column
	private int age;

	@Column
	@Enumerated(EnumType.STRING)
	private Rank rank;

	@Column
	private String roles;
//	private Role role;

	@Column
	private LocalDateTime createdDate;

	public Member(MemberRequestDTO requestDTO) {
		insertDtoDataToEntity(requestDTO);
		this.roles = requestDTO.getRoles();
		this.createdDate = LocalDateTime.now();
	}

	public void update(MemberRequestDTO requestDTO) {
		insertDtoDataToEntity(requestDTO);
	}

	public void setRank(String rank) {
		for (Rank value : Rank.values()) {
			if (value.isEqual(rank)) {
				this.rank = value;
				return;
			}
		}
	}

	public List<String> getRoleList() {
		if (this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));
		} else {
			return new ArrayList<>();
		}
	}
//	public void setRole(String role) {
//		for (Role value : Role.values()) {
//			if (value.isEqual(role)) {
//				this.role = value;
//				return;
//			}
//		}
//	}

	private void insertDtoDataToEntity(MemberRequestDTO requestDTO) {
		this.name = requestDTO.getName();
		this.account = requestDTO.getAccount();
		this.password = requestDTO.getPassword();
		this.age = requestDTO.getAge();
		setRank(requestDTO.getRank());
	}
}
