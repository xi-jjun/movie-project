package com.movie.community.controller.dto.request;

import com.movie.community.domain.Review;
import lombok.Data;

@Data
public class ReviewRequestDTO {
	private String content;
	private float score;

	public Review toEntity() {
		return new Review(this);
	}
}
