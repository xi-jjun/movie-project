package com.movie.community.controller.dto.response;

import lombok.Data;

@Data
public class ResponseDTO {
	private String message;
	private int status;

	public ResponseDTO(String message, int status) {
		this.message = message;
		this.status = status;
	}
}
