package com.movie.community.service.impl;

import com.movie.community.controller.dto.request.MovieRequestDTO;
import com.movie.community.controller.dto.response.MovieResponseDetailDTO;
import com.movie.community.controller.dto.response.MovieResponseListDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import com.movie.community.domain.Movie;
import com.movie.community.repository.MovieRepository;
import com.movie.community.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
	private final MovieRepository movieRepository;

	@Override
	public List<MovieResponseListDTO> getMovies() {
		List<Movie> movies = movieRepository.findAll();
		return movies.stream()
				.map(MovieResponseListDTO::new)
				.collect(Collectors.toList());
	}

	@Override
	public MovieResponseDetailDTO getMovieDetail(Long id) {
		Movie movie = movieRepository.findById(id);
		if (movie == null) {
			throw new IllegalArgumentException("No Such Movie by id=" + id);
		}

		return new MovieResponseDetailDTO(movie);
	}

	/**
	 * 이미 존재하는 Movie 라면, 저장 안하도록 만든다.
	 * DTO 가 null 이거나, movie_id 가 null 이라면, IllegalArgumentException 발생시킨다.
	 *
	 * @param movieRequestDTO
	 */
	@Override
	public ResponseDTO addMovie(MovieRequestDTO movieRequestDTO) {
		validateMovieRequestDTO(movieRequestDTO);

		Movie findMovie = movieRepository.findById(movieRequestDTO.getId());
		if (findMovie == null) {
			Movie movie = movieRequestDTO.toEntity();
			movieRepository.save(movie);

			return new ResponseDTO("success to register movie", 200);
		}

		return new ResponseDTO("fail to register movie", 400);
	}

	@Override
	public ResponseDTO updateMovie(MovieRequestDTO movieRequestDTO) {
		validateMovieRequestDTO(movieRequestDTO);

		Movie movie = movieRepository.findById(movieRequestDTO.getId());
		if (movie != null) {
			movieRepository.update(movieRequestDTO);

			return new ResponseDTO("success to update movie", 200);
		}

		return new ResponseDTO("fail to update movie", 400);
	}

	private void validateMovieRequestDTO(MovieRequestDTO movieRequestDTO) {
		if (movieRequestDTO == null) {
			throw new IllegalArgumentException("Movie request dto can't be null");
		} else if (movieRequestDTO.getId() == null) {
			throw new IllegalArgumentException("Movie id can not be null");
		}
	}

	@Override
	public ResponseDTO deleteMovie(Long id) {
		Movie movie = movieRepository.findById(id);
		if (movie != null) {
			movieRepository.remove(movie);

			return new ResponseDTO("success to delete movie", 200);
		}

		return new ResponseDTO("fail to delete movie", 400);
	}
}
