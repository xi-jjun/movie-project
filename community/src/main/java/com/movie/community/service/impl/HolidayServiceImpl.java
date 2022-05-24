package com.movie.community.service.impl;

import com.movie.community.controller.dto.request.HolidayRequestDTO;
import com.movie.community.controller.dto.response.HolidayResponseDTO;
import com.movie.community.controller.dto.response.MovieResponseListDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import com.movie.community.domain.Holiday;
import com.movie.community.repository.HolidayRepository;
import com.movie.community.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class HolidayServiceImpl implements HolidayService {
	private final HolidayRepository holidayRepository;

	@Override
	public List<MovieResponseListDTO> getMoviesByHoliday(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Holiday id can't be null");
		}

		Holiday holiday = holidayRepository.findById(id);
		if (holiday == null) {
			throw new IllegalArgumentException("No such element in Holiday");
		}

		return holiday.getMovies().stream()
				.map(MovieResponseListDTO::new)
				.collect(Collectors.toList());
	}

	@Override
	public List<HolidayResponseDTO> getHolidayList() {
		List<Holiday> holidayList = holidayRepository.findAll();
		return holidayList.stream()
				.map(HolidayResponseDTO::new)
				.collect(Collectors.toList());
	}

	@Override
	public ResponseDTO createHoliday(HolidayRequestDTO holidayRequestDTO) {
		if (holidayRequestDTO == null) {
			throw new IllegalArgumentException("Invalid client request"); // ResponseDTO or throw 둘 중 하나 골라야 함.
		}

		String name = holidayRequestDTO.getHoliday();
		if (name == null) {
			throw new IllegalArgumentException("Holiday name is null");
		}

		checkDuplicated(name);

		Holiday holiday = holidayRequestDTO.toEntity();
		holidayRepository.save(holiday);

		return new ResponseDTO("succes to create holiday", 200);
	}

	/**
	 * 시간 남으면 stream 써서 refactoring
	 * @param name
	 */
	private void checkDuplicated(String name) {
		List<Holiday> holidayList = holidayRepository.findAll();
		for (Holiday holiday : holidayList) {
			if (holiday.getHoliday().equals(name)) {
				throw new IllegalArgumentException("Holiday name can't be duplicated");
			}
		}
	}

	@Transactional
	@Override
	public ResponseDTO updateHoliday(HolidayRequestDTO holidayRequestDTO, Long id) {
		if (holidayRequestDTO == null || id == null) {
			throw new IllegalArgumentException("Invalid client request");
		}

		Holiday findHoliday = holidayRepository.findById(id);
		if (findHoliday == null) {
			throw new IllegalArgumentException("No such holiday");
		}

		holidayRepository.update(holidayRequestDTO, id);

		return new ResponseDTO("success to update holiday", 200);
	}

	@Transactional
	@Override
	public ResponseDTO removeHoliday(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Invalid client request");
		}
		Holiday holiday = holidayRepository.findById(id);
		if (holiday == null) {
			throw new IllegalArgumentException("Can't remove null object");
		}

		holidayRepository.remove(holiday);

		return new ResponseDTO("success to remove holiday", 200);
	}
}
