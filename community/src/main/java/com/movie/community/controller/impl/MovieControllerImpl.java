package com.movie.community.controller.impl;

import com.movie.community.controller.MovieController;
import com.movie.community.controller.dto.request.MovieRequestDTO;
import com.movie.community.controller.dto.response.MovieResponseDetailDTO;
import com.movie.community.controller.dto.response.MovieResponseListDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import com.movie.community.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/movies")
@RestController
public class MovieControllerImpl implements MovieController {
	private final MovieService movieService;

	@Override
	@GetMapping("")
	public List<MovieResponseListDTO> movies() {
		List<MovieResponseListDTO> movies = movieService.getMovies();
		return movies;
	}

	@Override
	@GetMapping("/{movieId}")
	public MovieResponseDetailDTO movie(@PathVariable("movieId") String movieId) {
		Long id = Long.parseLong(movieId);
		MovieResponseDetailDTO movieDetail = movieService.getMovieDetail(id);
		return movieDetail;
	}

	@Override
	@PostMapping("")
	public ResponseDTO register(@RequestBody MovieRequestDTO requestDTO) {
		ResponseDTO response = movieService.addMovie(requestDTO);
		return response;
	}

	@Override
	@PatchMapping("/{movieId}")
	public ResponseDTO update(MovieRequestDTO requestDTO) {
		ResponseDTO response = movieService.updateMovie(requestDTO);
		return response;
	}

	@Override
	@DeleteMapping("/{movieId}")
	public ResponseDTO delete(@PathVariable("movieId") String movieId) {
		Long id = Long.parseLong(movieId);
		ResponseDTO response = movieService.deleteMovie(id);
		return response;
	}
}
