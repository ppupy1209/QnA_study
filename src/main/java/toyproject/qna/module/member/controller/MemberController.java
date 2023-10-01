package toyproject.qna.module.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.qna.global.utils.UriCreator;
import toyproject.qna.module.member.dto.MemberPatchDto;
import toyproject.qna.module.member.dto.MemberPostDto;
import toyproject.qna.module.member.service.MemberService;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final static  String MEMBER_DEFAULT_URL = "/members";
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberPostDto) {
        Long memberId = memberService.createMember(memberPostDto.toEntity());

        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, memberId);

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") Long memberId,
                                      @Valid @RequestBody MemberPatchDto memberPatchDto) {

        memberService.updateMember(memberPatchDto.toEntity(),memberId);

        return ResponseEntity.ok().build();
    }
}
