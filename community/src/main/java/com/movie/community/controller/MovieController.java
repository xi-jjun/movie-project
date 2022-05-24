package com.movie.community.controller;

import com.movie.community.controller.dto.request.MovieRequestDTO;
import com.movie.community.controller.dto.response.MovieResponseDetailDTO;
import com.movie.community.controller.dto.response.MovieResponseListDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MovieController {
	public List<MovieResponseListDTO> movies();

	public MovieResponseDetailDTO movie(@PathVariable("movieId") String id);

	public ResponseDTO register(@RequestBody MovieRequestDTO requestDTO);

	public ResponseDTO update(@RequestBody MovieRequestDTO requestDTO);

	public ResponseDTO delete(@PathVariable("movieId") String id);
}
