package com.movie.community.service;

import com.movie.community.controller.dto.request.HolidayRequestDTO;
import com.movie.community.controller.dto.response.HolidayResponseDTO;
import com.movie.community.controller.dto.response.MovieResponseListDTO;
import com.movie.community.controller.dto.response.ResponseDTO;

import java.util.List;

public interface HolidayService {
	public List<MovieResponseListDTO> getMoviesByHoliday(Long holidayId);

	public List<HolidayResponseDTO> getHolidayList();

	public ResponseDTO createHoliday(HolidayRequestDTO holidayRequestDTO);

	public ResponseDTO updateHoliday(HolidayRequestDTO holidayRequestDTO, Long id);

	public ResponseDTO removeHoliday(Long id);
}
