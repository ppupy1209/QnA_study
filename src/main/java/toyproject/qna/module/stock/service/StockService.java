package toyproject.qna.module.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.module.stock.entity.Stock;
import toyproject.qna.module.stock.repository.StockRepository;
@Transactional
@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;


    public void decrease(Long id,Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}
