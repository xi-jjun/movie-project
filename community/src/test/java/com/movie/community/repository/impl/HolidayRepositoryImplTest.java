package com.movie.community.repository.impl;

import com.movie.community.controller.dto.request.HolidayRequestDTO;
import com.movie.community.domain.Holiday;
import com.movie.community.repository.HolidayRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional(readOnly = true)
@SpringBootTest
class HolidayRepositoryImplTest {
	@Autowired
	private EntityManager em;

	@Autowired
	private HolidayRepository holidayRepository;

	@Transactional
	@BeforeAll
	void generate() {
		final String[] HOLIDAYS = {"어린이날", "사귄지 100일", "생일", "화이트데이"};
		final LocalDate[] DATES = {
				LocalDate.of(2022, 5, 5),
				LocalDate.of(2022, 5, 23),
				LocalDate.of(2022, 3, 29),
				LocalDate.of(2022, 3, 14)
		};

		for (int i = 0; i < HOLIDAYS.length; i++) {
			HolidayRequestDTO requestDTO = new HolidayRequestDTO();
			requestDTO.setHoliday(HOLIDAYS[i]);
			requestDTO.setDate(DATES[i]);

			Holiday holiday = requestDTO.toEntity();
			holidayRepository.save(holiday);
		}
	}

	@Test
	void find() {
		// given
		final String FIND_NAME = "생일";

		// when
		Holiday holiday = holidayRepository.findByName("생일");

		// then
		assertEquals(LocalDate.of(2022, 3, 29), holiday.getDate());
	}

	@Test
	void findAll() {
		// given
		final int HOLIDAYS_SIZE = 4;

		// when
		List<Holiday> holidayList = holidayRepository.findAll();

		// then
		assertEquals(HOLIDAYS_SIZE, holidayList.size());

		Long id = 1L;
		for (Holiday holiday : holidayList) {
			assertEquals(id++, holiday.getId());
		}
	}

	@Transactional
	@Test
	void save() {
		// given
		final String HOLIDAY_NAME = "어른이날";
		final LocalDate DATE = LocalDate.of(2022, 5, 20);

		// when
		HolidayRequestDTO requestDTO = new HolidayRequestDTO();
		requestDTO.setHoliday(HOLIDAY_NAME);
		requestDTO.setDate(DATE);

		Holiday holiday = requestDTO.toEntity();
		holidayRepository.save(holiday);

		em.flush();
		em.clear();

		// then
		Holiday result = holidayRepository.findByName(HOLIDAY_NAME);
		assertEquals(HOLIDAY_NAME, result.getHoliday());
		assertEquals(DATE, result.getDate());
	}

	@Transactional
	@Test
	void update() {
		// given
		final String FROM_NAME = "어린이날";
		final String TO_NAME = "수정된 어린이날";
		final LocalDate TO_DATE = LocalDate.of(2100, 10, 22);

		// when
		HolidayRequestDTO requestDTO = new HolidayRequestDTO();
		requestDTO.setHoliday(TO_NAME);
		requestDTO.setDate(TO_DATE);

		Holiday holiday = holidayRepository.findByName(FROM_NAME); // 수정할 Holiday
		holidayRepository.update(requestDTO, holiday.getId());

		em.flush();
		em.clear();

		// then
		Holiday result = holidayRepository.findByName(TO_NAME);
		assertNotNull(result);
		assertNotEquals(FROM_NAME, result.getHoliday());
		assertEquals(TO_NAME, result.getHoliday());
		assertEquals(TO_DATE, result.getDate());
	}

	@Transactional
	@Test
	void remove() {
		// given
		final Long REMOVE_ID = 1L;
		final String REMOVE_NAME = "어린이날";

		// when
		Holiday findHoliday = holidayRepository.findById(REMOVE_ID);
		holidayRepository.remove(findHoliday);

		em.flush();
		em.clear();

		// then
		Holiday result = holidayRepository.findById(REMOVE_ID);
		assertNull(result);
	}
}