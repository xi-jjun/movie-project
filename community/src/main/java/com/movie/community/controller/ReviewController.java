package com.movie.community.controller;

import com.movie.community.controller.dto.request.ReviewRequestDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import com.movie.community.controller.dto.response.ReviewResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ReviewController {
	public List<ReviewResponseDTO> movieReviews(@PathVariable("movieId") String movieId);

	public List<ReviewResponseDTO> reviews();

	public ResponseDTO createReview(@RequestBody ReviewRequestDTO requestDTO,
									@PathVariable("movieId") String movieId,
									Authentication authentication);

	public ResponseDTO updateReview(@RequestBody ReviewRequestDTO requestDTO,
									@PathVariable("reviewId") String reviewId,
									Authentication authentication);

	public ResponseDTO removeReview(@PathVariable("reviewId") String reviewId,
									Authentication authentication);
}
