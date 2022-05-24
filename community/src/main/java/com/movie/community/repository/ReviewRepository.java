package com.movie.community.repository;

import com.movie.community.controller.dto.request.ReviewRequestDTO;
import com.movie.community.domain.Movie;
import com.movie.community.domain.Review;

import java.util.List;

public interface ReviewRepository {
	public void save(Review review);

	public void update(ReviewRequestDTO requestDTO, Long id);

	public void remove(Review review);
	
	public Review findById(Long id);

	public List<Review> findAll();
}
