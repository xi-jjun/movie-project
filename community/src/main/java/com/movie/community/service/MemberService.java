package com.movie.community.service;

import com.movie.community.controller.dto.request.MemberRequestDTO;
import com.movie.community.controller.dto.response.MemberResponseDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MemberService {
	public List<MemberResponseDTO> getMembers();

	public MemberResponseDTO getMember(Long id);

	public ResponseDTO join(MemberRequestDTO requestDTO);

	public ResponseDTO updateMemberInfo(MemberRequestDTO requestDTO, Long id, Authentication authentication);

	public ResponseDTO removeMemberInfo(Long id, Authentication authentication);
}
