package toyproject.qna.module.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.qna.global.dto.MultiResponseDto;
import toyproject.qna.global.dto.SingleResponseDto;
import toyproject.qna.global.utils.UriCreator;
import toyproject.qna.module.question.dto.QuestionListResponseDto;
import toyproject.qna.module.question.dto.QuestionPatchDto;
import toyproject.qna.module.question.dto.QuestionPostDto;
import toyproject.qna.module.question.dto.QuestionResponseDto;
import toyproject.qna.module.question.entity.Question;
import toyproject.qna.module.question.service.QuestionQueryService;
import toyproject.qna.module.question.service.QuestionService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

    private final static String QUESTION_DEFAULT_URL = "/questions";
    private final QuestionService questionService;
    private final QuestionQueryService questionQueryService;

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

    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") Long questionId) {

        QuestionResponseDto questionResponseDto = questionService.findQuestion(questionId);

        return new ResponseEntity<>(new SingleResponseDto<>(questionResponseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestions(@Positive @RequestParam int page,
                                       @Positive @RequestParam int size) {
       SingleResponseDto questionsQueryResponse = questionQueryService.findQuestionsQuery(page - 1, size);

        return new ResponseEntity<>(questionsQueryResponse,HttpStatus.OK);
    }

}


