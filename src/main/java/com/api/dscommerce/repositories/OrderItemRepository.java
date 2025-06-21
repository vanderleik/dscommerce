package com.api.dscommerce.repositories;

import com.api.dscommerce.entities.OrderItem;
import com.api.dscommerce.entities.OrderItemPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPk> {

}
