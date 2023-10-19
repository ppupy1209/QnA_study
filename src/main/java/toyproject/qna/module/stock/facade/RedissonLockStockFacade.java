package toyproject.qna.module.stock.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import toyproject.qna.module.stock.service.StockService;

import java.util.concurrent.TimeUnit;

//@RequiredArgsConstructor
//public class RedissonLockStockFacade {
//
//    private final RedissonClient redissonClient;
//    private final StockService stockService;
//
//    public void decrease(Long key,Long quantity) {
//        RLock lock = redissonClient.getLock(key.toString());
//
//        try {
//            boolean available = lock.tryLock(15,1, TimeUnit.SECONDS);
//
//            if(!available) {
//                System.out.println("lock 획득 실패");
//                return;
//            }
//
//            stockService.decrease(key,quantity);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } finally {
//            lock.unlock();
//        }
//    }
//}
