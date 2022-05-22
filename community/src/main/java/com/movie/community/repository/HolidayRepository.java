package com.movie.community.repository;

import com.movie.community.controller.dto.request.HolidayRequestDTO;
import com.movie.community.domain.Holiday;

import java.util.List;

public interface HolidayRepository {
	public void save(Holiday holiday);

	public void update(HolidayRequestDTO holidayRequestDTO, Long id);

	public void remove(Holiday holiday);

	public Holiday findById(Long id);

	public List<Holiday> findAll();

	public Holiday findByName(String name);
}
