package toyproject.qna.module.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.global.dto.MultiResponseDto;
import toyproject.qna.module.question.dto.QuestionListResponseDto;
import toyproject.qna.module.question.dto.QuestionSearchCondition;
import toyproject.qna.module.question.entity.Question;
import toyproject.qna.module.question.repository.QuestionRepository;
import toyproject.qna.module.question.repository.QuestionTagRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QuestionQueryService {

      private final QuestionRepository questionRepository;

      private final QuestionTagRepository questionTagRepository;


      public MultiResponseDto findQuestionsQuery(int page,int size) {

          Page<Question> questions = questionRepository.findQuestionsWithMember(PageRequest.of(page, size, Sort.by("id").descending()));

          List<QuestionListResponseDto> questionListResponseDtos = QuestionListResponseDto.of(questions.getContent());

          return new MultiResponseDto<>(questionListResponseDtos,questions);
      }


    public MultiResponseDto searchQuestions(QuestionSearchCondition condition, int page, int size) {
          Page<Question> questions = questionRepository.searchQuestions(condition,PageRequest.of(page,size,Sort.by("id").descending()));

          List<QuestionListResponseDto> questionListResponseDtos = QuestionListResponseDto.of(questions.getContent());

          return new MultiResponseDto<>(questionListResponseDtos,questions);
    }
}






