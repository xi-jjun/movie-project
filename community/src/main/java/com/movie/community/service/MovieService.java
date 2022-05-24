package com.movie.community.service;

import com.movie.community.controller.dto.request.MovieRequestDTO;
import com.movie.community.controller.dto.response.MovieResponseDetailDTO;
import com.movie.community.controller.dto.response.MovieResponseListDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import com.movie.community.domain.Movie;

import java.util.List;

public interface MovieService {
	public List<MovieResponseListDTO> getMovies();

	public MovieResponseDetailDTO getMovieDetail(Long id);

	public ResponseDTO addMovie(MovieRequestDTO movieRequestDTO);

	public ResponseDTO updateMovie(MovieRequestDTO movieRequestDTO);

	public ResponseDTO deleteMovie(Long id);
}
