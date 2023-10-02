package toyproject.qna.module.tag.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tags")
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id",updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Builder
    public Tag(String name) {
        this.name = name;
    }

    public static Tag createTag(String name) {
        return Tag.builder()
                .name(name)
                .build() ;
    }

}
