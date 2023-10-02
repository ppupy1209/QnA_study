package toyproject.qna.module.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.qna.global.utils.UriCreator;
import toyproject.qna.module.question.dto.QuestionPatchDto;
import toyproject.qna.module.question.dto.QuestionPostDto;
import toyproject.qna.module.question.service.QuestionService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

    private final static String QUESTION_DEFAULT_URL = "/questions";
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionPostDto questionPostDto) {

        Long memberId = questionPostDto.getMemberId();

        Long questionId = questionService.createQuestion(memberId,questionPostDto.toEntity(),questionPostDto.getTags());

        URI location = UriCreator.createUri(QUESTION_DEFAULT_URL,questionId);

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@PathVariable("question-id") Long questionId,
                                        @Valid @RequestBody QuestionPatchDto questionPatchDto) {

        questionService.updateQuestion(questionId,questionPatchDto.toEntity(),questionPatchDto.getTags());

        return ResponseEntity.ok().build();
    }


}
