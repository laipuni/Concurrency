package com.example.shopproject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class MultiThreadTest {

    @DisplayName("CountDownLatch를 사용해서 쓰레드의 작업이 모두 끝나고 검증하면 예상값이 나온다.")
    @Test
    void test() throws InterruptedException {
        //given
        int threadNum = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        CountDownLatch latch = new CountDownLatch(threadNum);

        AtomicInteger sum = new AtomicInteger();
        //when
        for (int i = 0; i < threadNum; i++) {
            executorService.execute(()->{
                sum.incrementAndGet();
                latch.countDown();
            });
        }

        //모든 쓰레드 작업이 끝날 때 까지 기다림
        //latch의 숫자를 0일 때까지 countDown해야 기다림이 끝남
        latch.await();
        executorService.shutdown();
        //then
        assertThat(sum.get()).isEqualTo(20);
    }

    @DisplayName("CountDownLatch를 사용하지 않으면 쓰레드가 모두 끝나고 검증 하지 않아서 예상값보다 작은 수가 나온다.")
    @Test
    void test2() throws InterruptedException {
        //given
        int threadNum = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

        AtomicInteger sum = new AtomicInteger();
        //when
        for (int i = 0; i < threadNum; i++) {
            executorService.execute(sum::incrementAndGet);
        }
        executorService.shutdown();
        //then
        assertThat(sum.get()).isLessThan(20);
    }

}
