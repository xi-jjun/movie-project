package com.movie.community.controller;

import com.movie.community.controller.dto.request.HolidayRequestDTO;
import com.movie.community.controller.dto.response.HolidayResponseDTO;
import com.movie.community.controller.dto.response.MovieResponseListDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import com.movie.community.domain.Holiday;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface HolidayController {
	public List<MovieResponseListDTO> movies(@PathVariable("holidayId") String holidayId);

	public ResponseDTO create(@RequestBody HolidayRequestDTO holidayRequestDTO);

	public ResponseDTO update(@RequestBody HolidayRequestDTO holidayRequestDTO,
							  @PathVariable("holidayId") String holidayId);

	public ResponseDTO remove(@PathVariable("holidayId") String holidayId);

	public List<HolidayResponseDTO> holidayList();
}
