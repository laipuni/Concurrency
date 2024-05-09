package com.example.shopproject.domain.order;

import com.example.shopproject.domain.order.request.OrderCreateRequest;
import com.example.shopproject.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@RequiredArgsConstructor
@Component
public class OrderFacade {

    private final Map<String, OrderService> orderServiceMap;

    public void orderByThreadNum(OrderCreateRequest request) {
        OrderService orderService = orderServiceMap.get(request.getMode());
        CountDownLatch latch = new CountDownLatch(request.getThreadNum());
        ExecutorService executorService = Executors.newFixedThreadPool(request.getThreadNum());
        log.info("orderService = {}",orderService);
        for (int i = 0; i < request.getThreadNum(); i++) {
            executorService.execute(()->{
                try{
                    orderService.orderBy(request.getItemCode(), request.getItemCount());
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        await(latch);
        executorService.shutdown();
    }

    private static void await(final CountDownLatch latch) {
        try{
            latch.await();
        } catch (InterruptedException e){
            throw new RuntimeException(e.getMessage(),e.getCause());
        }
    }

}
