package com.movie.community.repository;

import com.movie.community.controller.dto.request.MovieRequestDTO;
import com.movie.community.domain.Holiday;
import com.movie.community.domain.Movie;

import java.util.List;

public interface MovieRepository {
	public void save(Movie movie);

	public void update(MovieRequestDTO requestDTO);

	public void remove(Movie movie);

	public Movie findById(Long id);

	public List<Movie> findAll();
}
