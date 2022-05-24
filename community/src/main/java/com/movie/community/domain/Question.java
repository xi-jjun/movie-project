package com.movie.community.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Question {
	@Id
	@Column(name = "question_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String question;

	@Column
	private String select1;

	@Column
	private String select2;

	@Column
	private String select3;

	@Column
	private String select4;

	@Column
	private String answer;

	@ManyToOne(targetEntity = Exam.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_id")
	private Exam exam;
}
