package com.movie.community.domain;

import com.movie.community.controller.dto.request.HolidayRequestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Holiday {
	@Id
	@Column(name = "holiday_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String holiday;

	@Column
	private LocalDate date;

	@OneToMany(mappedBy = "holiday")
	private List<Movie> movies;

	public Holiday(HolidayRequestDTO requestDTO) {
		this.holiday = requestDTO.getHoliday();
		this.date = requestDTO.getDate();
	}

	public void update(HolidayRequestDTO requestDTO) {
		this.holiday = requestDTO.getHoliday();
		this.date = requestDTO.getDate();
	}
}
