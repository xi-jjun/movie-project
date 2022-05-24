package com.movie.community.repository.impl;

import com.movie.community.controller.dto.request.MovieRequestDTO;
import com.movie.community.domain.Holiday;
import com.movie.community.domain.Movie;
import com.movie.community.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Repository
public class MovieRepositoryImpl implements MovieRepository {
	private final EntityManager em;
	
	@Transactional
	@Override
	public void save(Movie movie) {
		em.persist(movie);
	}

	@Transactional
	@Override
	public void update(MovieRequestDTO requestDTO) {
		Movie findMovie = em.find(Movie.class, requestDTO.getId());
		findMovie.update(requestDTO);
	}

	@Transactional
	@Override
	public void remove(Movie movie) {
		em.remove(movie);
	}

	@Override
	public Movie findById(Long id) {
		return em.find(Movie.class, id);
	}

	@Override
	public List<Movie> findAll() {
		return em.createQuery("select m from Movie m", Movie.class)
				.getResultList();
	}
}
