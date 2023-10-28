package toyproject.qna.module.item.servcie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.global.exception.BusinessLogicException;
import toyproject.qna.global.exception.ExceptionCode;
import toyproject.qna.module.item.entity.Item;
import toyproject.qna.module.item.repository.ItemRepository;


import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;


    // 아이템 조회 메서드
    public Item findItem(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        Item item = optionalItem.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ITEM_NOT_FOUND));

        return item;
    }
}
