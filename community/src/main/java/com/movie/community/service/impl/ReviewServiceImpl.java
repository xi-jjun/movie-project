package com.movie.community.service.impl;

import com.movie.community.controller.dto.request.ReviewRequestDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import com.movie.community.controller.dto.response.ReviewResponseDTO;
import com.movie.community.domain.Member;
import com.movie.community.domain.Movie;
import com.movie.community.domain.Review;
import com.movie.community.repository.MemberRepository;
import com.movie.community.repository.MovieRepository;
import com.movie.community.repository.ReviewRepository;
import com.movie.community.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
	private final ReviewRepository reviewRepository;
	private final MovieRepository movieRepository;
	private final MemberRepository memberRepository;

	@Override
	public List<ReviewResponseDTO> getReviewsByMovie(Long movieId) {
		Movie movie = movieRepository.findById(movieId);
		List<Review> reviews = movie.getReviews();
		return getReviewResponseDTOList(reviews);
	}

	@Override
	public List<ReviewResponseDTO> getAllReviews() {
		List<Review> reviews = reviewRepository.findAll();
		return getReviewResponseDTOList(reviews);
	}

	private List<ReviewResponseDTO> getReviewResponseDTOList(List<Review> reviews) {
		return reviews.stream()
				.map(ReviewResponseDTO::new)
				.collect(Collectors.toList());
	}

	@Override
	public ResponseDTO createReview(ReviewRequestDTO requestDTO, Long movieId, Authentication authentication) {
		if (invalidReviewData(requestDTO)) {
			return new ResponseDTO("Invalid client request", 400);
		}

		Movie movie = movieRepository.findById(movieId);
		if (movie == null) {
			return new ResponseDTO("No such movie id=" + movieId, 400);
		}

		// map movie and review
		Review review = requestDTO.toEntity();
		review.setReviewMovie(movie);

		// set review writer
		String account = authentication.getName();
		Member reviewer = memberRepository.findByAccount(account);
		review.setReviewer(reviewer);

		reviewRepository.save(review);
		return new ResponseDTO("success to save review", 200);
	}

	private boolean invalidReviewData(ReviewRequestDTO requestDTO) {
		if (requestDTO.getContent() == null || requestDTO.getContent().equals("")) {
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public ResponseDTO updateReview(ReviewRequestDTO requestDTO, Long id, Authentication authentication) {
		if (invalidReviewData(requestDTO) || id == null) {
			return new ResponseDTO("Invalid client request", 400);
		}

		Review review = reviewRepository.findById(id);
		if (review == null) {
			return new ResponseDTO("No such review id=" + id, 400);
		}

		String account = authentication.getName();
		String reviewerAccount = review.getMember().getAccount();
		if (account.equals(reviewerAccount)) {
			reviewRepository.update(requestDTO, id);
			return new ResponseDTO("success to update review", 200);
		}

		return new ResponseDTO("Has no authentication on this review id=" + id, 400);
	}

	@Transactional
	@Override
	public ResponseDTO removeReview(Long id, Authentication authentication) {
		if (id == null) {
			return new ResponseDTO("Invalid client request", 400);
		}

		Review review = reviewRepository.findById(id);
		String account = review.getMember().getAccount();
		String authenticatedMember = authentication.getName();
		if (account.equals(authenticatedMember)) {
			reviewRepository.remove(review);
			return new ResponseDTO("success to remove review", 200);
		}

		return new ResponseDTO("User authentication is not valid", 400);
	}
}
