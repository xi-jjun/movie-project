package com.movie.community.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Comment {
	@Id
	@Column(name = "comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String comment;

	@Column
	private LocalDateTime createdDate;

	@Column
	private LocalDateTime updatedDate;

	@JsonIgnore
	@ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@JsonIgnore
	@ManyToOne(targetEntity = Posting.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "posting_id")
	private Posting posting;
}
