package toyproject.qna.global.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageInfo {

    private int page;
    private int size;
    private Long totalElements;
    private int totalPages;
}
