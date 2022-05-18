package com.movie.community.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Movie {
	@Id
	@Column(name = "movie_id")
	private Integer id;

	@Column
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column
	private float score;

	@Column
	private int voteCount;

	@Column
	private String imageUrl;

	@Column
	private LocalDateTime releasedDate;

	@Column
	private float popularity;

	@ManyToOne(targetEntity = Holiday.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "holiday_id")
	private Holiday holiday;
}
