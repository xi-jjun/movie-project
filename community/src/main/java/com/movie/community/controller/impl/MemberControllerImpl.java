package com.movie.community.controller.impl;

import com.movie.community.controller.MemberController;
import com.movie.community.controller.dto.request.MemberRequestDTO;
import com.movie.community.controller.dto.response.MemberResponseDTO;
import com.movie.community.controller.dto.response.ResponseDTO;
import com.movie.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberControllerImpl implements MemberController {
	private final MemberService memberService;

	@Override
	@GetMapping("")
	public List<MemberResponseDTO> members() {
		List<MemberResponseDTO> members = memberService.getMembers();
		return members;
	}

	@Override
	@GetMapping("/{memberId}")
	public MemberResponseDTO member(@PathVariable("memberId") String memberId) {
		Long id = Long.parseLong(memberId);
		MemberResponseDTO member = memberService.getMember(id);
		return member;
	}

	/**
	 * 인증이 안된 누군가도 접근할 수 있다.
	 * @param requestDTO
	 * @return
	 */
	@Override
	@PostMapping("")
	public ResponseDTO signUp(MemberRequestDTO requestDTO) {
		ResponseDTO response = memberService.join(requestDTO);
		return response;
	}

	@Override
	@PatchMapping("/{memberId}")
	public ResponseDTO update(@RequestBody MemberRequestDTO requestDTO,
							  @PathVariable("memberId") String memberId,
							  Authentication authentication) {
		Long id = Long.parseLong(memberId);
		ResponseDTO response = memberService.updateMemberInfo(requestDTO, id, authentication);
		return response;
	}

	@Override
	@DeleteMapping("/{memberId}")
	public ResponseDTO remove(@PathVariable("memberId") String memberId,
							  Authentication authentication) {
		Long id = Long.parseLong(memberId);
		ResponseDTO response = memberService.removeMemberInfo(id, authentication);
		return response;
	}
}
