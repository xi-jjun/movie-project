package com.movie.community.repository.impl;

import com.movie.community.controller.dto.request.ReviewRequestDTO;
import com.movie.community.domain.Movie;
import com.movie.community.domain.Review;
import com.movie.community.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
	private final EntityManager em;

	@Transactional
	@Override
	public void save(Review review) {
		em.persist(review);
	}

	@Transactional
	@Override
	public void update(ReviewRequestDTO requestDTO, Long id) {
		Review review = em.find(Review.class, id);
		review.update(requestDTO);
	}

	@Transactional
	@Override
	public void remove(Review review) {
		em.remove(review);
	}

	@Override
	public Review findById(Long id) {
		return em.find(Review.class, id);
	}

	@Override
	public List<Review> findAll() {
		return em.createQuery("select r from Review r", Review.class)
				.getResultList();
	}

}
