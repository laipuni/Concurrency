package com.example.shopproject.domain.item;

import com.example.shopproject.domain.IntegrationTestSupport;
import com.example.shopproject.domain.item.ItemRepository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class ItemRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("물품의 코드와 동일한 물품을 조회 한다.")
    @Test
    void findByItemCode(){
        //given
        String itemCode = String.valueOf(UUID.randomUUID());
        Item item = Item.builder()
                .itemCode(itemCode)
                .price(1700)
                .itemName("삼각김밥")
                .quantity(30)
                .build();
        itemRepository.save(item);

        //when
        Item findItem = itemRepository.findByItemCode(itemCode).get();

        //then
        assertThat(findItem.getItemCode()).isEqualTo(itemCode);
    }

    @DisplayName("물품의 코드와 동일한 물품을 조회 한다.")
    @Test
    void findByItemCodeForUpdate(){
        //given
        String itemCode = String.valueOf(UUID.randomUUID());
        Item item = Item.builder()
                .itemCode(itemCode)
                .price(1700)
                .itemName("삼각김밥")
                .quantity(30)
                .build();
        itemRepository.save(item);

        //when
        Item findItem = itemRepository.findByItemCode(itemCode).get();

        //then
        assertThat(findItem.getItemCode()).isEqualTo(itemCode);
    }

    @DisplayName("조회 시 Sql에 for Update를 같이 보내서 Lock을 걸기 때문에 다른 사용자들이 해당 데이터에 수정, 조회를 못한다.")
    @Test
    void createOrderWithPessimisticLock(){
        //given
        String itemCode = String.valueOf(UUID.randomUUID());
        Item item = Item.builder()
                .itemCode(itemCode)
                .price(1700)
                .itemName("삼각김밥")
                .quantity(30)
                .build();
        itemRepository.save(item);

        //when
        Item findItem = itemRepository.findByItemCodeForUpdate(itemCode).get();

        //then
        assertThat(findItem.getItemCode()).isEqualTo(itemCode);
    }
}