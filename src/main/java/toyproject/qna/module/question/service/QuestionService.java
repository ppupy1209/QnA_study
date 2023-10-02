package toyproject.qna.module.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.service.MemberService;
import toyproject.qna.module.question.entity.Question;
import toyproject.qna.module.question.entity.QuestionTag;
import toyproject.qna.module.question.repository.QuestionRepository;
import toyproject.qna.module.question.repository.QuestionTagRepository;
import toyproject.qna.module.tag.entity.Tag;
import toyproject.qna.module.tag.repository.TagRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionTagRepository questionTagRepository;
    private final TagRepository tagRepository;
    private final MemberService memberService;

    public Long createQuestion(Long memberId, Question question, String[] tags) {

        Member member = memberService.findMember(memberId);

        question.setMember(member);

        Question savedQuestion = questionRepository.save(question);

        // tag 가 포함되어 있다면 tag 저장
        if(tags.length!=0) saveTag(question, tags);

        return savedQuestion.getId();

    }

    // Tag 저장 메서드
    private void saveTag(Question question, String[] tags) {
        QuestionTag questionTag;

        for (String tag : tags) {
             Optional<Tag> findTag = tagRepository.findByName(tag);

             //  기존에 Tag 가 없다면
             if(findTag.isEmpty()) {
                 Tag createdTag = Tag.createTag(tag);
                 tagRepository.save(createdTag);
                 questionTag = QuestionTag.createQuestionTag(question, createdTag);
             }
             // 기존에 Tag 가 있다면
             else {
                 questionTag = QuestionTag.createQuestionTag(question,findTag.get());
             }
             questionTagRepository.save(questionTag);
        }
    }
}
