package toyproject.qna.module.answer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.qna.global.utils.UriCreator;
import toyproject.qna.module.answer.dto.AnswerPatchDto;
import toyproject.qna.module.answer.dto.AnswerPostDto;
import toyproject.qna.module.answer.entity.Answer;
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

    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(@PathVariable("answer-id") Long answerId,
                                      @Valid @RequestBody AnswerPatchDto answerPatchDto) {

        answerService.updateAnswer(answerId,answerPatchDto.toEntity());

        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") Long answerId) {
        answerService.deleteAnswer(answerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
