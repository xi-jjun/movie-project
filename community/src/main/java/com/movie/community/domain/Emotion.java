package com.movie.community.domain;

import lombok.Getter;

@Getter
public enum Emotion {
	SADNESS("sadness"),
	ANGRY("angry"),
	HAPPINESS("happiness"),
	NEUTRAL("neutral"),
	DISGUST("disgust"),
	LONELINESS("loneliness"),
	;

	private final String value;

	Emotion(String value) {
		this.value = value;
	}
}
