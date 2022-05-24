package com.movie.community.repository.impl;

import com.movie.community.controller.dto.request.HolidayRequestDTO;
import com.movie.community.domain.Holiday;
import com.movie.community.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Repository
public class HolidayRepositoryImpl implements HolidayRepository {
	private final EntityManager em;

	@Transactional
	@Override
	public void save(Holiday holiday) {
		em.persist(holiday);
	}

	@Transactional
	@Override
	public void update(HolidayRequestDTO requestData, Long id) {
		Holiday toBeUpdatedHoliday = em.find(Holiday.class, id);
		toBeUpdatedHoliday.update(requestData);
	}

	@Transactional
	@Override
	public void remove(Holiday holiday) {
		em.remove(holiday);
	}

	@Override
	public Holiday findById(Long id) {
		return em.find(Holiday.class, id);
	}

	@Override
	public List<Holiday> findAll() {
		return em.createQuery("select h from Holiday h", Holiday.class)
				.getResultList();
	}

	@Override
	public Holiday findByName(String name) {
		return em.createQuery("select h from Holiday h where h.holiday=:name", Holiday.class)
				.setParameter("name", name)
				.getSingleResult();
	}
}
