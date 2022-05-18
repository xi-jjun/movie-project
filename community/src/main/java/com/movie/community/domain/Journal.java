package com.movie.community.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Journal {
	@Id
	@Column(name = "journal_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column
	@Enumerated(EnumType.STRING)
	private Emotion emotion;

	@ManyToOne(targetEntity = Diary.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "diary_id")
	private Diary diary;
}
