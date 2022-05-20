package com.movie.community.repository.impl;

import com.movie.community.controller.dto.request.MovieRequestDTO;
import com.movie.community.domain.Movie;
import com.movie.community.repository.MovieRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
class MovieRepositoryImplTest {
	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private EntityManager em;

	@Transactional
	@BeforeAll
	void generateData() {
		final Long[] id = {335787L, 414906L, 453395L, 634649L, 628900L};
		final String[] titles = {"언차티드", "더 배트맨", "닥터 스트레인지: 대혼돈의 멀티버스", "스파이더맨: 노 웨이 홈", "더 컨트랙터"};
		final String[] descriptions = {
				"평범한 삶을 살던 네이선은 인생을 바꿀 뜻밖의 제안을 받는다. 그의 미션은 크루와 함께 사라진 형과 500년 전 잃어버린 천문학적인 가치를 지닌 트레져를 찾아내는 것. 그러나 몬카다의 위협 속, 누구보다 빠르게 미지의 세계에 닿기 위해 결단을 내려야만 하는데…",
				"지난 2년 간 고담시의 어둠 속에서 범법자들을 응징하며 배트맨으로 살아온 브루스 웨인. 알프레드와 제임스 고든 경위의 도움 아래 그는 도시의 부패한 공직자들과 고위 관료들 사이에서 복수의 화신으로 활약한다. 고담의 시장 선거를 앞두고 고담의 엘리트 집단을 목표로 잔악한 연쇄살인을 저지르는 수수께끼 킬러 리들러가 나타나자, 최고의 탐정 브루스 웨인이 수사에 나서고 남겨진 단서를 풀어가며 캣우먼, 펭귄, 카마인 팔코네, 리들러를 차례대로 만난다. 사이코 범인의 미스터리를 수사하면서 그 모든 증거가 자신을 향한 의도적인 메시지였음을 깨닫고,  리들러에게 농락 당한 배트맨은 광기에 사로잡힌다. 범인의 무자비한 계획을 막고 오랫동안 고담시를 썩게 만든 권력 부패의 고리를 끊어야 하지만, 부모님의 죽음에 얽힌 진실이 밝혀지자 복수와 정의 사이에서 갈등하는데...",
				"끝없이 균열되는 차원과 뒤엉킨 시공간의 멀티버스가 열리며 오랜 동료들, 그리고 차원을 넘어 들어온 새로운 존재들을 맞닥뜨리게 된 닥터 스트레인지. 대혼돈 속, 그는 예상치 못한 극한의 적과 맞서 싸워야만 하는데….",
				"‘미스테리오’의 계략으로 세상에 정체가 탄로난 스파이더맨 ‘피터 파커’는 하루 아침에 평범한 일상을 잃게 된다. 문제를 해결하기 위해 ‘닥터 스트레인지’를 찾아가 도움을 청하지만 뜻하지 않게 멀티버스가 열리면서 각기 다른 차원의 불청객들이 나타난다. ‘닥터 옥토퍼스’를 비롯해 스파이더맨에게 깊은 원한을 가진 숙적들의 강력한 공격에 ‘피터 파커’는 사상 최악의 위기를 맞게 되는데…",
				"특수부대 중사 출신 ‘제임스 하퍼’는 전역을 명 받고 법의 테두리 밖에서 국가에 충성하는 극비 조직에 합류한다. 그에게 주어진 첫번째 미션은, 전세계를 혼란에 빠뜨릴 바이러스 테러를 막는 것. 그러나, 미션 수행 도중 거대한 음모에 휘말리게 되고 충격과 위기를 겪게 되는데… 더 이상 물러설 곳은 없다.  모든 것을 건, 새로운 미션이 시작된다!"
		};
		final float[] scores = {7.2f, 7.8f, 7.5f, 8.1f, 6.5f};
		final int[] voteCounts = {1916, 4550, 1521, 12769, 225};
		final String[] imageUrls = {
				"/2R8smeSDkPx6TKIRveKPXi0JVI6.jpg",
				"/bCz71ysciwNL2xddSm25ufrgZ7V.jpg",
				"/vL5ktZauR0fZMDOHKjakb1idhuU.jpg",
				"/voddFVdjUoAtfoZZp2RUmuZILDI.jpg",
				"/fZid9LbFTZV7NjM0qMwL1mPjhqu.jpg"
		};
		final LocalDate[] dates = {
				LocalDate.of(2022, 2, 16),
				LocalDate.of(2022, 3, 1),
				LocalDate.of(2022, 5, 4),
				LocalDate.of(2021, 12, 15),
				LocalDate.of(2022, 4, 28)
		};
		final float[] popularity = {
				5338.804f,
				5095.615f,
				4641.408f,
				4160.324f,
				3691.191f
		};
		final String[] genres = {
				"'Action', 'Adventure', 'Comedy'"
		};
		for (int i = 0; i < 5; i++) {
			MovieRequestDTO requestDTO = new MovieRequestDTO();
			requestDTO.setId(id[i]);
			requestDTO.setTitle(titles[i]);
			requestDTO.setDescription(descriptions[i]);
			requestDTO.setScore(scores[i]);
			requestDTO.setVoteCount(voteCounts[i]);
			requestDTO.setImageUrl(imageUrls[i]);
			requestDTO.setReleasedDate(dates[i]);
			requestDTO.setPopularity(popularity[i]);

			Movie movie = requestDTO.toEntity();
			movieRepository.save(movie);
		}
	}

	@Test
	void find() {
		// given
		final Long FIND_ID = 634649L;
		final String TITLE = "스파이더맨: 노 웨이 홈";
		final float SCORE = 8.1f;

		// when
		Movie findMovie = movieRepository.findById(FIND_ID);

		// then
		assertEquals(FIND_ID, findMovie.getId());
		assertEquals(TITLE, findMovie.getTitle());
		assertEquals(SCORE, findMovie.getScore());
	}

	@Test
	void findAll() {
		// given

		// when
		List<Movie> movies = movieRepository.findAll();

		// then
		assertEquals(5, movies.size());
	}

	@Transactional
	@Test
	void save() {
		// given
		MovieRequestDTO requestDTO = new MovieRequestDTO();
		requestDTO.setId(526896L);
		requestDTO.setTitle("모비우스");
		requestDTO.setDescription("희귀혈액병을 앓고 있는 생화학자 모비우스는 동료인 마르틴과 함께 치료제 개발에 몰두한다. 흡혈 박쥐를 연구하던 중 마침내 치료제 개발에 성공한 모비우스는 새 생명과 강력한 힘을 얻게 되지만, 동시에 흡혈을 하지 않고는 생명을 유지할 수 없게 된다. 그러던 중 모비우스와 같은 병을 앓고 있던 그의 친구 마일로도 모비우스와 같은 힘을 얻게 되는데…");
		requestDTO.setScore(6f);
		requestDTO.setVoteCount(678);
		requestDTO.setImageUrl("/8uiV88A5eB7PjMTtY6lQBUZ0Cpl.jpg");
		requestDTO.setReleasedDate(LocalDate.of(2022, 3, 30));
		requestDTO.setPopularity(1175.843f);

		// when
		Movie movie = requestDTO.toEntity();
		movieRepository.save(movie);

		em.flush();
		em.clear();

		// then
		Movie findMovie = movieRepository.findById(movie.getId());
		assertEquals("모비우스", findMovie.getTitle());
		assertEquals("/8uiV88A5eB7PjMTtY6lQBUZ0Cpl.jpg", findMovie.getImageUrl());
	}

	@Transactional
	@DisplayName("모비우스 영화에 관한 DB의 row 데이터의 score 점수를 0점으로 바꾸고 확인하기")
	@Test
	void update() {
		// given : update 하기 위한 정보를 DTO 에 담아서 서버로 가지고 온다.
		final Long MOVIE_ID = 628900L;
		final float UPDATE_SCORE = 0f;
		MovieRequestDTO updateDTO = new MovieRequestDTO();
		updateDTO.setId(MOVIE_ID);
		updateDTO.setScore(UPDATE_SCORE); // Update score


		// when
		movieRepository.update(updateDTO);

		em.flush();
		em.clear();


		// then
		Movie updatedMovie = movieRepository.findById(MOVIE_ID);
		assertEquals(UPDATE_SCORE, updatedMovie.getScore());
	}

	@Transactional
	@Test
	void remove() {
		// given : 삭제할 ID
		final Long MOVIE_ID = 628900L;

		// when
		Movie findMovie = movieRepository.findById(MOVIE_ID);
		movieRepository.remove(findMovie);

		em.flush();
		em.clear();

		// then
		Movie result = movieRepository.findById(MOVIE_ID);
		assertNull(result);
	}
}