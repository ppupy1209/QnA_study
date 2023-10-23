package toyproject.qna.global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import toyproject.qna.module.item.entity.Item;
import toyproject.qna.module.item.repository.ItemRepository;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.repository.MemberRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @PostConstruct
    public void init() {
        Member member1 = createMember("1번", 10);
        Member member2 = createMember("2번", 20);
        Member member3 = createMember("3번", 30);
        Member member4 = createMember("4번", 40);
        Member member5 = createMember("5번", 50);

        memberRepository.saveAll(List.of(member1,member2,member3,member4,member5));

        Item item1 = createItem("책",100,5000);
        Item item2 = createItem("영화",50,7000);
        Item item3 = createItem("음악",70,3000);

        itemRepository.saveAll(List.of(item1,item2,item3));
    }

    private Member createMember(String name, int age) {
        return Member.builder()
                .name(name)
                .age(age)
                .build();
    }

    private Item createItem(String name, int stockQuantity, int price) {
        return Item.builder()
                .name(name)
                .stockQuantity(stockQuantity)
                .price(price)
                .build();
    }
}
