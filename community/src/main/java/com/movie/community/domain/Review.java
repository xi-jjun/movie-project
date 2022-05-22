package com.movie.community.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.movie.community.controller.dto.request.ReviewRequestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
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

	@JsonIgnore
	@ManyToOne(targetEntity = Movie.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@JsonIgnore
	@ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	public Review(ReviewRequestDTO requestDTO) {
		insertDTODataToEntity(requestDTO);
		this.createdDate = LocalDateTime.now();
	}

	public void update(ReviewRequestDTO requestDTO) {
		insertDTODataToEntity(requestDTO);
		this.updatedDate = LocalDateTime.now();
	}

	public void setReviewMovie(Movie movie) {
		this.movie = movie;
	}

	public void setReviewer(Member member) {
		this.member = member;
	}

	private void insertDTODataToEntity(ReviewRequestDTO requestDTO) {
		this.content = requestDTO.getContent();
		this.score = requestDTO.getScore();
	}
}
