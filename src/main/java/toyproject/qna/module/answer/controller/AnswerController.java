package toyproject.qna.module.answer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.qna.global.utils.UriCreator;
import toyproject.qna.module.answer.dto.AnswerPostDto;
import toyproject.qna.module.answer.service.AnswerService;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final static String ANSWER_DEFAULT_URL = "/answers";

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity postAnswer(@Valid @RequestBody AnswerPostDto answerPostDto) {

        Long answerId = answerService.createAnswer(answerPostDto.getMemberId(), answerPostDto.getQuestionId(), answerPostDto.toEntity());

        URI location = UriCreator.createUri(ANSWER_DEFAULT_URL,answerId);

        return ResponseEntity.created(location).build();
    }
}
