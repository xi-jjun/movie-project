package com.movie.community.domain;

import lombok.Getter;

@Getter
public enum Rank {
	NEWBIE("애기"),
	BEGINNER("입문자"),
	JUNIOR("초심자"),
	SENIOR("고인물"),
	KING("썩은물");

	private final String value;

	Rank(String value) {
		this.value = value;
	}

	public boolean isEqual(String rank) {
		return this.value.equals(rank);
	}
}
