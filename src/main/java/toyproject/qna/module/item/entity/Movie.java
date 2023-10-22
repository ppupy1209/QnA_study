package toyproject.qna.module.item.entity;

import lombok.Getter;
import toyproject.qna.module.item.entity.Item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@Getter
public class Movie extends Item {

    private String director;
    private String actor;
}
