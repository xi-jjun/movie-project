package com.movie.community.domain;

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
public class Review {
	@Id
	@Column(name = "review_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String content;

	@Column
	private float score;

	@Column
	private LocalDateTime createdDate;

	@Column
	private LocalDateTime updatedDate;

	@ManyToOne(targetEntity = Movie.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
}
