package com.example.shopproject.domain.item;

import com.example.shopproject.domain.item.request.ItemCreateRequest;
import com.example.shopproject.domain.item.response.ItemCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public ItemCreateResponse save(final ItemCreateRequest request){
        String itemCode = String.valueOf(UUID.randomUUID());
        Item item = itemRepository.save(request.toEntity(itemCode));
        return ItemCreateResponse.of(item);
    }

    @Transactional
    public void reductQuantityByPessimisticLock(String ItemCode,int quantity){
        //구매할 물품을 조회 by Mysql pessimistic Lock
        Item item = itemRepository.findByItemCodeForUpdate(ItemCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 물품입니다."));
        //상품의 주문 수만큼 제품의 수량 차감
        item.reductQuantity(quantity);
    }

    /**
     * Syncronized기능은 스프링에서 제공하는 @Transactional의해 동시성문제가 발생한다.
     * 그 이유는 @Transactional은 우리가 구현한 로직을 proxy로 감싼다.
     * proxy로 감싼다는 것은 try catch문으로 감싸는데 매번 언체크드 예외(언체크 예외도 롤백 발생 설정가능)에서 rollback하고
     * 아닐 경우 commit하는 로직을 작성하는 수고를 덜어준다.
     * Syncronized는 로짐을 담은 메소드에 다른 사용자가 접근할 때만 제한할 수 있다.
     * 하지만 로직이 끝나고 누군가 메소드에 접근 할 경우 막지 못한다.
     */
    @Transactional
    public synchronized void reductQuantitBySyncronized(String ItemCode,int quantity){
        //구매할 물품을 조회 by Mysql pessimistic Lock
        Item item = itemRepository.findByItemCode(ItemCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 물품입니다."));
        //상품의 주문 수만큼 제품의 수량 차감
        item.reductQuantity(quantity);
    }



}
