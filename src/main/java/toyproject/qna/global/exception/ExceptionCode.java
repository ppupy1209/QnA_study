package toyproject.qna.global.exception;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_NOT_FOUND(404,"Member Not Found"),
    QUESTION_NOT_FOUND(404,"Question Not Found"),
    ANSWER_NOT_FOUND(404,"Answer Not Found");


    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
