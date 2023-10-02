package toyproject.qna.module.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.qna.global.utils.UriCreator;
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


}
