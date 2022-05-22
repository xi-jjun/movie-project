package com.movie.community.controller;

import com.movie.community.controller.dto.request.MemberRequestDTO;
import com.movie.community.controller.dto.response.MemberResponseDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MemberController {
	public List<MemberResponseDTO> members();

	public MemberResponseDTO member(@PathVariable String id);

	public ResponseDTO signUp(@RequestBody MemberRequestDTO requestDTO);

	public ResponseDTO update(@RequestBody MemberRequestDTO requestDTO,
							  @PathVariable String memberId,
							  Authentication authentication);

	public ResponseDTO remove(@PathVariable String memberId,
							  Authentication authentication);
}
