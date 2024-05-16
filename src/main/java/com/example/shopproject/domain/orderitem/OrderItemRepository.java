package com.example.shopproject.domain.orderitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    @Query("select oi from OrderItem oi join fetch oi.order join fetch oi.item where oi.itemCode = :itemCode order by oi.id DESC ")
    List<OrderItem> findAllByItemCodeDesc(@Param("itemCode") String itemCode);

}
