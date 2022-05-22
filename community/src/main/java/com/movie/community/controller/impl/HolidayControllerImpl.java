package com.movie.community.controller.impl;

import com.movie.community.controller.HolidayController;
import com.movie.community.controller.dto.request.HolidayRequestDTO;
import com.movie.community.controller.dto.response.HolidayResponseDTO;
import com.movie.community.controller.dto.response.MovieResponseListDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import com.movie.community.service.HolidayService;
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
@RequestMapping("/holiday")
@RestController
public class HolidayControllerImpl implements HolidayController {
	private final HolidayService holidayService;

	@Override
	@GetMapping("/{holidayId}/movies")
	public List<MovieResponseListDTO> movies(@PathVariable("holidayId") String holidayId) {
		Long id = Long.parseLong(holidayId);
		List<MovieResponseListDTO> movies = holidayService.getMoviesByHoliday(id);
		return movies;
	}

	@Override
	@GetMapping("")
	public List<HolidayResponseDTO> holidayList() {
		List<HolidayResponseDTO> holidayList = holidayService.getHolidayList();
		return holidayList;
	}

	@Override
	@PostMapping("")
	public ResponseDTO create(@RequestBody HolidayRequestDTO holidayRequestDTO) {
		ResponseDTO response = holidayService.createHoliday(holidayRequestDTO);
		if (response == null) {
			return new ResponseDTO("fail to create new holiday", 400);
		}

		return response;
	}

	@Override
	@PatchMapping("/{holidayId}")
	public ResponseDTO update(@RequestBody HolidayRequestDTO holidayRequestDTO,
							  @PathVariable("holidayId") String holidayId) {
		Long id = Long.parseLong(holidayId);
		ResponseDTO response = holidayService.updateHoliday(holidayRequestDTO, id);
		if (response == null) {
			return new ResponseDTO("fail to update holiday", 400);
		}

		return response;
	}

	@Override
	@DeleteMapping("/{holidayId}")
	public ResponseDTO remove(@PathVariable("holidayId") String holidayId) {
		Long id = Long.parseLong(holidayId);
		ResponseDTO response = holidayService.removeHoliday(id);
		if (response == null) {
			return new ResponseDTO("fail to remove holiday", 400);
		}

		return response;
	}
}
