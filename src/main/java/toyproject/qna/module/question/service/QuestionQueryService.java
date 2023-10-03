package toyproject.qna.module.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.global.dto.MultiResponseDto;
import toyproject.qna.module.question.dto.QuestionListResponseDto;
import toyproject.qna.module.question.entity.Question;
import toyproject.qna.module.question.repository.QuestionRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QuestionQueryService {

      private final QuestionRepository questionRepository;
      public MultiResponseDto findQuestionsQuery(int page,int size) {

          Page<Question> questions = questionRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));


          List<QuestionListResponseDto> questionListResponseDtos = questions.getContent().stream()
                  .map(question -> QuestionListResponseDto.of(question))
                  .collect(Collectors.toList());

          return new MultiResponseDto<>(questionListResponseDtos,questions);
      }
}






