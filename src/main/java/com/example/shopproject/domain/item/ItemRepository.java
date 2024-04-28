package com.example.shopproject.domain.item;


import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {

    Optional<Item> findByItemCode(String itemCode);

    /**
     * PESSIMISTIC_WRITE : "Exclusive Lock" 하는 Pessmistic Lock의 종류이다. 다른 스레드가 Select, Update 불가능
     * PESSIMISTIC_READ : "Share Lock"라고 하는 Pessimistic Lock의 종류이다. 다른 스레드가 Select 가능, Update 불가능
     * PESSIMISTIC_FORCE_INCREMENT : version을 사용하는 Pessimistic Lock의 종류이다.
     * @param itemCode 조회할 물품 ItemCode
     * @return 조회할 물품
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)//Pessimistic Lock Exclusive Lock 설정
    @Query("select i from Item i where i.itemCode = :itemCode")
    Optional<Item> findByItemCodeForUpdate(@Param("itemCode") String itemCode);
}
