package com.movie.community.service;

import com.movie.community.controller.dto.request.ReviewRequestDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import com.movie.community.controller.dto.response.ReviewResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReviewService {
	public List<ReviewResponseDTO> getReviewsByMovie(Long movieId);

	public List<ReviewResponseDTO> getAllReviews();

	public ResponseDTO createReview(ReviewRequestDTO requestDTO, Long movieId, Authentication authentication);

	public ResponseDTO updateReview(ReviewRequestDTO requestDTO, Long id, Authentication authentication);

	public ResponseDTO removeReview(Long id, Authentication authentication);
}
