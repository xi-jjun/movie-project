package com.movie.community.controller.impl;

import com.movie.community.controller.ReviewController;
import com.movie.community.controller.dto.request.ReviewRequestDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import com.movie.community.controller.dto.response.ReviewResponseDTO;
import com.movie.community.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reviews")
@RestController
public class ReviewControllerImpl implements ReviewController {
	private final ReviewService reviewService;

	@Override
	@GetMapping("/movies/{movieId}")
	public List<ReviewResponseDTO> movieReviews(@PathVariable("movieId") String movieId) {
		Long id = Long.parseLong(movieId);
		List<ReviewResponseDTO> reviews = reviewService.getReviewsByMovie(id);
		return reviews;
	}

	@Override
	@GetMapping("")
	public List<ReviewResponseDTO> reviews() {
		List<ReviewResponseDTO> reviews = reviewService.getAllReviews();
		return reviews;
	}

	@Override
	@PostMapping("/movies/{movieId}")
	public ResponseDTO createReview(ReviewRequestDTO requestDTO,
									@PathVariable("movieId") String movieId,
									Authentication authentication) {
		log.info("user info : {}", authentication.getName()); // getName() : account
		Long id = Long.parseLong(movieId);
		ResponseDTO response = reviewService.createReview(requestDTO, id, authentication);
		return response;
	}

	@Override
	@PatchMapping("/{reviewId}")
	public ResponseDTO updateReview(ReviewRequestDTO requestDTO,
									@PathVariable("reviewId") String reviewId,
									Authentication authentication) {
		Long id = Long.parseLong(reviewId);
		ResponseDTO response = reviewService.updateReview(requestDTO, id, authentication);
		return response;
	}

	@Override
	@DeleteMapping("/{reviewId}")
	public ResponseDTO removeReview(@PathVariable("reviewId") String reviewId,
									Authentication authentication) {
		Long id = Long.parseLong(reviewId);
		ResponseDTO response = reviewService.removeReview(id, authentication);
		return response;
	}
}
