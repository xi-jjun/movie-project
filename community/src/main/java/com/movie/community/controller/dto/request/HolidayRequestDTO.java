package com.movie.community.controller.dto.request;

import com.movie.community.domain.Holiday;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class HolidayRequestDTO {
	private String holiday;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	public Holiday toEntity() {
		return new Holiday(this);
	}
}
